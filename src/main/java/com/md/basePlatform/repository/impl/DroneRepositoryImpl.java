package com.md.basePlatform.repository.impl;

import com.md.basePlatform.domain.Drone;
import com.md.basePlatform.repository.DroneRepository;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 无人机数据访问实现接口
 * <p>
 * MyBatis Mapper 接口，继承自 DroneRepository。
 * SQL 语句定义在 DroneMapper.xml 中。
 * </p>
 */
@Mapper
public interface DroneRepositoryImpl extends DroneRepository {

    /**
     * 插入无人机数据
     *
     * @param drone 无人机实体
     * @return 受影响的行数
     */
    @Override
    int insert(Drone drone);

    /**
     * 更新无人机数据
     *
     * @param drone 无人机实体
     * @return 受影响的行数
     */
    @Override
    int update(Drone drone);

    /**
     * 根据ID删除无人机（逻辑删除）
     *
     * @param id 无人机ID
     * @return 受影响的行数
     */
    @Override
    int deleteById(@Param("id") Long id);

    /**
     * 根据ID查询无人机
     *
     * @param id 无人机ID
     * @return 无人机实体（可选）
     */
    @Override
    Optional<Drone> findById(@Param("id") Long id);

    /**
     * 查询无人机列表（支持关键词搜索）
     *
     * @param keyword 搜索关键词
     * @return 无人机列表
     */
    @Override
    List<Drone> findAll(@Param("keyword") String keyword);

    /**
     * 统计未删除记录中与给定编号冲突的数量
     *
     * @param uavCode 无人机编号
     * @return 冲突数量
     */
    @Override
    int countByUavCode(@Param("uavCode") String uavCode);

    /**
     * 统计排除指定ID外，与给定编号冲突的数量
     *
     * @param uavCode 无人机编号
     * @param id 排除的ID
     * @return 冲突数量
     */
    @Override
    int countByUavCodeExcludeId(@Param("uavCode") String uavCode, @Param("id") Long id);
}
