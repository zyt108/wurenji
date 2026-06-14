package com.md.basePlatform.repository;

import com.md.basePlatform.domain.Drone;
import java.util.List;
import java.util.Optional;

/**
 * 无人机数据访问接口
 * <p>
 * 定义无人机数据的增删改查操作，由 MyBatis 实现类实现。
 * </p>
 */
public interface DroneRepository {

    /**
     * 插入无人机数据
     *
     * @param drone 无人机实体
     * @return 受影响的行数
     */
    int insert(Drone drone);

    /**
     * 更新无人机数据
     *
     * @param drone 无人机实体
     * @return 受影响的行数
     */
    int update(Drone drone);

    /**
     * 根据ID删除无人机（逻辑删除）
     *
     * @param id 无人机ID
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 根据ID查询无人机
     *
     * @param id 无人机ID
     * @return 无人机实体（可选）
     */
    Optional<Drone> findById(Long id);

    /**
     * 查询无人机列表（支持关键词搜索）
     *
     * @param keyword 搜索关键词（可为null，null时查询全部）
     * @return 无人机列表
     */
    List<Drone> findAll(String keyword);

    /**
     * 查询无人机列表（支持关键词搜索和状态筛选）
     *
     * @param keyword 搜索关键词（可为null）
     * @param status  状态筛选（可为null）
     * @return 无人机列表
     */
    List<Drone> findAll(String keyword, Integer status);

    /**
     * 统计未删除记录中与给定编号冲突的数量
     * <p>
     * 用于验证编号唯一性，与 SQLite 部分唯一索引一致。
     * </p>
     *
     * @param uavCode 无人机编号
     * @return 冲突数量
     */
    int countByUavCode(String uavCode);

    /**
     * 统计排除指定ID外，与给定编号冲突的数量
     * <p>
     * 用于更新时验证编号唯一性。
     * </p>
     *
     * @param uavCode 无人机编号
     * @param id 排除的ID
     * @return 冲突数量
     */
    int countByUavCodeExcludeId(String uavCode, Long id);

    /**
     * 统计所有未删除的无人机数量
     *
     * @return 无人机总数
     */
    Long countAll();

    /**
     * 按状态统计无人机数量
     *
     * @param status 状态（1启用，0禁用）
     * @return 对应状态的无人机数量
     */
    Long countByStatus(int status);
}
