package com.md.basePlatform.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * SQLite 无人机编号唯一性迁移器
 * <p>
 * 旧版 SQLite 在 uav_code 列上建了 UNIQUE 约束，软删除后仍无法再次使用该编号。
 * 启动时检测 DDL：若列级 UNIQUE 仍存在则重建表并改为「仅 deleted=0 时唯一」的部分索引。
 * </p>
 * <p>
 * 仅在 sqlite profile 激活时生效，执行顺序为 0（最先执行）。
 * </p>
 */
@Component
@Profile("sqlite")
@Order(0)
public class SqliteUavCodeUniqueMigrator implements ApplicationRunner {

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(SqliteUavCodeUniqueMigrator.class);

    /**
     * 部分唯一索引名称
     */
    private static final String PARTIAL_INDEX = "uk_t_uav_uav_code_active";

    /**
     * 匹配列定义中包含 UNIQUE 的正则表达式
     * <p>
     * 与部分唯一索引并存时仍能识别旧表。
     * </p>
     */
    private static final Pattern UAV_CODE_COLUMN_UNIQUE = Pattern
            .compile("(?is)uav_code\\s+varchar\\([^)]*\\)\\s+not\\s+null\\s+unique\\b");

    /**
     * 匹配表级 UNIQUE 约束的正则表达式
     */
    private static final Pattern UAV_CODE_TABLE_UNIQUE = Pattern.compile("(?is)\\bunique\\s*\\(\\s*uav_code\\s*\\)");

    /**
     * 数据源
     */
    private final DataSource dataSource;

    /**
     * 构造函数，依赖注入
     *
     * @param dataSource 数据源
     */
    public SqliteUavCodeUniqueMigrator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 应用启动时执行迁移
     * <p>
     * 检测表结构，判断是否需要迁移：
     * 1. 如果表不存在，直接返回
     * 2. 如果存在旧版 UNIQUE 约束，重建表并创建部分唯一索引
     * 3. 如果缺少部分唯一索引，创建索引
     * </p>
     *
     * @param args 应用参数
     * @throws Exception 迁移异常
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Connection c = dataSource.getConnection()) {
            // 如果表不存在，无需迁移
            if (!tableExists(c, "t_uav")) {
                return;
            }

            // 读取表的 CREATE TABLE 语句
            String ddl = readCreateTableSql(c, "t_uav");

            // 判断是否存在旧版 UNIQUE 约束
            boolean legacyUnique = ddl != null
                    && (UAV_CODE_COLUMN_UNIQUE.matcher(ddl).find() || UAV_CODE_TABLE_UNIQUE.matcher(ddl).find());

            // 判断部分唯一索引是否存在
            boolean partialOk = indexExists(c, PARTIAL_INDEX);

            // 如果没有旧版约束且已有部分索引，无需操作
            if (!legacyUnique && partialOk) {
                return;
            }

            // 如果存在旧版约束，重建表
            if (legacyUnique) {
                log.warn("SQLite t_uav: 检测到 uav_code 列级 UNIQUE，正在迁移为软删除后可重用编号（部分唯一索引）…");
                rebuildTable(c);
                log.info("SQLite t_uav: 迁移完成，已建立索引 {}", PARTIAL_INDEX);
                return;
            }

            // 如果缺少部分唯一索引，创建索引
            if (!partialOk) {
                log.warn("SQLite t_uav: 缺少部分唯一索引 {}，正在创建…", PARTIAL_INDEX);
                exec(c, "CREATE UNIQUE INDEX IF NOT EXISTS " + PARTIAL_INDEX + " ON t_uav(uav_code) WHERE deleted = 0");
                log.info("SQLite t_uav: 已创建 {}", PARTIAL_INDEX);
            }
        }
    }

    /**
     * 检查表是否存在
     *
     * @param c    数据库连接
     * @param name 表名
     * @return 是否存在
     * @throws SQLException 数据库异常
     */
    private static boolean tableExists(Connection c, String name) throws SQLException {
        try (Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                        "SELECT 1 FROM sqlite_master WHERE type='table' AND name='" + name + "'")) {
            return rs.next();
        }
    }

    /**
     * 读取表的 CREATE TABLE SQL 语句
     *
     * @param c     数据库连接
     * @param table 表名
     * @return CREATE TABLE SQL，不存在返回 null
     * @throws SQLException 数据库异常
     */
    private static String readCreateTableSql(Connection c, String table) throws SQLException {
        try (Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                        "SELECT sql FROM sqlite_master WHERE type='table' AND name='" + table + "'")) {
            return rs.next() ? rs.getString(1) : null;
        }
    }

    /**
     * 检查索引是否存在
     *
     * @param c         数据库连接
     * @param indexName 索引名
     * @return 是否存在
     * @throws SQLException 数据库异常
     */
    private static boolean indexExists(Connection c, String indexName) throws SQLException {
        try (Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                        "SELECT 1 FROM sqlite_master WHERE type='index' AND name='" + indexName + "'")) {
            return rs.next();
        }
    }

    /**
     * 重建表结构
     * <p>
     * 创建临时表 -> 复制数据 -> 删除原表 -> 重命名临时表 -> 创建部分唯一索引
     * </p>
     *
     * @param c 数据库连接
     * @throws SQLException 数据库异常
     */
    private static void rebuildTable(Connection c) throws SQLException {
        c.setAutoCommit(false);
        try {
            // 创建临时表（不含 UNIQUE 约束）
            exec(c, "CREATE TABLE t_uav_migrate ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "uav_code VARCHAR(64) NOT NULL,"
                    + "model VARCHAR(100) NOT NULL,"
                    + "manufacturer VARCHAR(100),"
                    + "max_payload DOUBLE,"
                    + "max_altitude INTEGER,"
                    + "max_flight_time INTEGER,"
                    + "max_speed DOUBLE,"
                    + "wingspan DOUBLE,"
                    + "weight DOUBLE,"
                    + "status INTEGER NOT NULL DEFAULT 1,"
                    + "remark VARCHAR(500),"
                    + "ai_generated INTEGER NOT NULL DEFAULT 0,"
                    + "created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "deleted INTEGER NOT NULL DEFAULT 0"
                    + ")");

            // 复制数据到临时表
            exec(c,
                    "INSERT INTO t_uav_migrate (id, uav_code, model, manufacturer, max_payload, max_altitude, "
                            + "max_flight_time, max_speed, wingspan, weight, status, remark, ai_generated, "
                            + "created_at, updated_at, deleted) "
                            + "SELECT id, uav_code, model, manufacturer, max_payload, max_altitude, max_flight_time, "
                            + "max_speed, wingspan, weight, status, remark, ai_generated, created_at, updated_at, "
                            + "deleted FROM t_uav");

            // 删除原表
            exec(c, "DROP TABLE t_uav");

            // 重命名临时表为原表名
            exec(c, "ALTER TABLE t_uav_migrate RENAME TO t_uav");

            // 创建部分唯一索引（仅 deleted=0 时唯一）
            exec(c, "CREATE UNIQUE INDEX " + PARTIAL_INDEX + " ON t_uav(uav_code) WHERE deleted = 0");

            // 更新 sqlite_sequence 以保持自增序列正确
            if (sqliteSequenceTableExists(c)) {
                exec(c, "DELETE FROM sqlite_sequence WHERE name = 't_uav'");
                exec(c, "INSERT INTO sqlite_sequence (name, seq) SELECT 't_uav', COALESCE(MAX(id), 0) FROM t_uav");
            }

            c.commit();
        } catch (SQLException e) {
            c.rollback();
            throw e;
        } finally {
            c.setAutoCommit(true);
        }
    }

    /**
     * 检查 sqlite_sequence 表是否存在
     *
     * @param c 数据库连接
     * @return 是否存在
     * @throws SQLException 数据库异常
     */
    private static boolean sqliteSequenceTableExists(Connection c) throws SQLException {
        try (Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                        "SELECT 1 FROM sqlite_master WHERE type='table' AND name='sqlite_sequence'")) {
            return rs.next();
        }
    }

    /**
     * 执行 SQL 语句
     *
     * @param c   数据库连接
     * @param sql SQL 语句
     * @throws SQLException 数据库异常
     */
    private static void exec(Connection c, String sql) throws SQLException {
        try (Statement st = c.createStatement()) {
            st.executeUpdate(sql);
        }
    }
}
