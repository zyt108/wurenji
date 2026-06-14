package com.md.basePlatform.domain;

import java.time.LocalDateTime;

/**
 * 无人机实体类
 * <p>
 * 映射数据库表 t_uav，存储无人机的基本信息和属性数据。
 * </p>
 */
public class Drone {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 无人机编号（唯一标识）
     */
    private String uavCode;

    /**
     * 无人机型号
     */
    private String model;

    /**
     * 制造商
     */
    private String manufacturer;

    /**
     * 最大载重（kg）
     */
    private Double maxPayload;

    /**
     * 最大飞行高度（m）
     */
    private Integer maxAltitude;

    /**
     * 最大飞行时间（min）
     */
    private Integer maxFlightTime;

    /**
     * 最大飞行速度（m/s）
     */
    private Double maxSpeed;

    /**
     * 翼展（m）
     */
    private Double wingspan;

    /**
     * 自重（kg）
     */
    private Double weight;

    /**
     * 状态：0-停用，1-启用
     */
    private Integer status;

    /**
     * 备注/AI生成建议
     */
    private String remark;

    /**
     * AI属性是否已生成：0-否，1-是
     */
    private Integer aiGenerated;

    /**
     * 删除标记：0-未删除，1-已删除（逻辑删除）
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUavCode() {
        return uavCode;
    }

    public void setUavCode(String uavCode) {
        this.uavCode = uavCode;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getMaxPayload() {
        return maxPayload;
    }

    public void setMaxPayload(Double maxPayload) {
        this.maxPayload = maxPayload;
    }

    public Integer getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(Integer maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    public Integer getMaxFlightTime() {
        return maxFlightTime;
    }

    public void setMaxFlightTime(Integer maxFlightTime) {
        this.maxFlightTime = maxFlightTime;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Double getWingspan() {
        return wingspan;
    }

    public void setWingspan(Double wingspan) {
        this.wingspan = wingspan;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAiGenerated() {
        return aiGenerated;
    }

    public void setAiGenerated(Integer aiGenerated) {
        this.aiGenerated = aiGenerated;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
