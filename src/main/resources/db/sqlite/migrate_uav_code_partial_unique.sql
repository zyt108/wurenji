-- 从「列级 UNIQUE」迁移到「仅 deleted=0 时 uav_code 唯一」（软删除后可重用编号）。
-- 适用：此前已用旧版 schema 创建过库文件（列上带 UNIQUE）的情况。
-- 新建库直接用 schema.sql 即可，无需执行本脚本。
--
-- 执行示例（先停应用）：
--   sqlite3 ./data/baseplatform.db < src/main/resources/db/sqlite/migrate_uav_code_partial_unique.sql
-- Windows PowerShell 可先 copy 脚本到项目根再执行 sqlite3。

BEGIN TRANSACTION;

CREATE TABLE t_uav_migrate (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  uav_code VARCHAR(64) NOT NULL,
  model VARCHAR(100) NOT NULL,
  manufacturer VARCHAR(100),
  max_payload DOUBLE,
  max_altitude INTEGER,
  max_flight_time INTEGER,
  max_speed DOUBLE,
  wingspan DOUBLE,
  weight DOUBLE,
  status INTEGER NOT NULL DEFAULT 1,
  remark VARCHAR(500),
  ai_generated INTEGER NOT NULL DEFAULT 0,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted INTEGER NOT NULL DEFAULT 0
);

INSERT INTO t_uav_migrate (
  id, uav_code, model, manufacturer, max_payload, max_altitude, max_flight_time,
  max_speed, wingspan, weight, status, remark, ai_generated, created_at, updated_at, deleted
)
SELECT
  id, uav_code, model, manufacturer, max_payload, max_altitude, max_flight_time,
  max_speed, wingspan, weight, status, remark, ai_generated, created_at, updated_at, deleted
FROM t_uav;

DROP TABLE t_uav;
ALTER TABLE t_uav_migrate RENAME TO t_uav;

CREATE UNIQUE INDEX uk_t_uav_uav_code_active ON t_uav(uav_code) WHERE deleted = 0;

-- 保留 AUTOINCREMENT 序列（DROP 表后会丢失）
DELETE FROM sqlite_sequence WHERE name = 't_uav';
INSERT INTO sqlite_sequence (name, seq) VALUES ('t_uav', (SELECT COALESCE(MAX(id), 0) FROM t_uav));

COMMIT;
