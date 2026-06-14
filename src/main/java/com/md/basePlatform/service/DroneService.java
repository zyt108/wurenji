package com.md.basePlatform.service;

import com.md.basePlatform.domain.Drone;
import com.md.basePlatform.domain.DroneForm;
import com.md.basePlatform.exception.BusinessException;
import com.md.basePlatform.repository.DroneRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 无人机业务服务。
 * <p>
 * 提供无人机的增删改查业务逻辑，包含事务管理和 AI 属性生成。
 * </p>
 */
@Service
public class DroneService {

    /** 无人机数据访问层 */
    private final DroneRepository droneRepository;

    /** AI 属性生成服务 */
    private final AiAttributeService aiAttributeService;

    /**
     * 构造函数，依赖注入。
     *
     * @param droneRepository    无人机数据访问层
     * @param aiAttributeService AI 属性生成服务
     */
    public DroneService(DroneRepository droneRepository, AiAttributeService aiAttributeService) {
        this.droneRepository = droneRepository;
        this.aiAttributeService = aiAttributeService;
    }

    /**
     * 创建无人机。
     *
     * @param form 表单参数
     * @return 创建后的无人机实体
     * @throws BusinessException 编号已被未删除记录占用时
     */
    @Transactional
    public Drone create(DroneForm form) {
        if (droneRepository.countByUavCode(form.getUavCode()) > 0) {
            throw new BusinessException("编号已被未删除的记录占用，请更换无人机编号");
        }
        Drone drone = map(form);
        aiAttributeService.enrich(drone);
        droneRepository.insert(drone);
        return drone;
    }

    /**
     * 更新无人机。
     *
     * @param id   无人机主键 ID
     * @param form 表单参数
     * @return 更新后的无人机实体
     * @throws BusinessException 记录不存在或编号被其他记录占用时
     */
    @Transactional
    public Drone update(Long id, DroneForm form) {
        if (droneRepository.countByUavCodeExcludeId(form.getUavCode(), id) > 0) {
            throw new BusinessException("编号已被其他记录占用");
        }
        Drone existing = get(id);
        Drone drone = map(form);
        drone.setId(existing.getId());
        aiAttributeService.enrich(drone);
        droneRepository.update(drone);
        return drone;
    }

    /**
     * 逻辑删除无人机。
     *
     * @param id 无人机主键 ID
     * @throws BusinessException 记录不存在时
     */
    @Transactional
    public void delete(Long id) {
        if (droneRepository.deleteById(id) <= 0) {
            throw new BusinessException("无人机不存在");
        }
    }

    /**
     * 根据 ID 查询无人机。
     *
     * @param id 无人机主键 ID
     * @return 无人机实体
     * @throws BusinessException 记录不存在时
     */
    public Drone get(Long id) {
        return droneRepository.findById(id).orElseThrow(() -> new BusinessException("无人机不存在"));
    }

    /**
     * 条件查询无人机列表（不分页，分页由 Controller 层 PageHelper 处理）。
     *
     * @param keyword 关键词（可选）
     * @param status  状态（可选）
     * @return 符合条件的无人机列表
     */
    public List<Drone> list(String keyword, Integer status) {
        return droneRepository.findAll(keyword, status);
    }

    /**
     * 将表单对象映射为实体对象，并设置默认 deleted=0。
     *
     * @param form 表单数据
     * @return 无人机实体
     */
    private Drone map(DroneForm form) {
        Drone drone = new Drone();
        drone.setUavCode(form.getUavCode());
        drone.setModel(form.getModel());
        drone.setManufacturer(form.getManufacturer());
        drone.setMaxPayload(form.getMaxPayload());
        drone.setMaxAltitude(form.getMaxAltitude());
        drone.setMaxFlightTime(form.getMaxFlightTime());
        drone.setMaxSpeed(form.getMaxSpeed());
        drone.setWingspan(form.getWingspan());
        drone.setWeight(form.getWeight());
        drone.setStatus(form.getStatus());
        drone.setRemark(form.getRemark());
        drone.setDeleted(0);
        return drone;
    }
}
