package com.md.basePlatform.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.DecimalMax;

/**
 * 无人机表单数据传输对象
 * <p>
 * 用于接收前端表单输入，包含数据校验规则。
 * 与实体类分离，提高安全性和灵活性。
 * </p>
 */
public class DroneForm {
    /**
     * 主键ID（编辑时使用）
     */
    private Long id;

    /**
     * 无人机编号（必填）
     */
    @NotBlank(message = "编号不能为空")
    @Size(max = 64)
    private String uavCode;

    /**
     * 无人机型号（必填，最大64字符）
     */
    @NotBlank(message = "型号不能为空")
    @Size(max = 64)
    private String model;

    /**
     * 制造商（可选，最大64字符）
     */
    @Size(max = 64)
    private String manufacturer;

    /**
     * 最大载重（kg，必填，范围：0.1-10000）
     */
    @NotNull(message = "载重不能为空")
    @DecimalMin(value = "0.1", message = "载重必须大于0")
    @DecimalMax(value = "10000", message = "载重不能超过10000kg")
    private Double maxPayload;

    /**
     * 最大飞行高度（m，必填，范围：1-10000）
     */
    @NotNull(message = "飞行高度不能为空")
    @Min(value = 1, message = "飞行高度必须大于0")
    @Max(value = 10000, message = "飞行高度不能超过10000m")
    private Integer maxAltitude;

    /**
     * 最大飞行时间（min，必填，范围：1-1440）
     */
    @NotNull(message = "飞行时间不能为空")
    @Min(value = 1, message = "飞行时间必须大于0")
    @Max(value = 1440, message = "飞行时间不能超过1440分钟（24小时）")
    private Integer maxFlightTime;

    /**
     * 最大飞行速度（m/s，必填，范围：0.1-340）
     */
    @NotNull(message = "飞行速度不能为空")
    @DecimalMin(value = "0.1", message = "飞行速度必须大于0")
    @DecimalMax(value = "340", message = "飞行速度不能超过340m/s（音速）")
    private Double maxSpeed;

    /**
     * 翼展（m，必填，范围：0.1-100）
     */
    @NotNull(message = "翼展不能为空")
    @DecimalMin(value = "0.1", message = "翼展必须大于0")
    @DecimalMax(value = "100", message = "翼展不能超过100m")
    private Double wingspan;

    /**
     * 自重（kg，必填，范围：0.001-10000）
     */
    @NotNull(message = "自重不能为空")
    @DecimalMin(value = "0.001", message = "自重必须大于0")
    @DecimalMax(value = "10000", message = "自重不能超过10000kg")
    private Double weight;

    /**
     * 状态（必填，0-停用，1-启用）
     */
    @NotNull(message = "状态不能为空")
    @Min(value = 0, message = "状态值无效")
    @Max(value = 1, message = "状态值无效")
    private Integer status;

    /**
     * 备注说明（可选，最大500字符）
     */
    @Size(max = 500, message = "备注不能超过500字符")
    private String remark;

    /** @return 主键 ID（编辑时使用） */
    public Long getId() {
        return id;
    }

    /** @param id 主键 ID（编辑时使用） */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return 无人机编号 */
    public String getUavCode() {
        return uavCode;
    }

    /** @param uavCode 无人机编号 */
    public void setUavCode(String uavCode) {
        this.uavCode = uavCode;
    }

    /** @return 无人机型号 */
    public String getModel() {
        return model;
    }

    /** @param model 无人机型号 */
    public void setModel(String model) {
        this.model = model;
    }

    /** @return 制造商 */
    public String getManufacturer() {
        return manufacturer;
    }

    /** @param manufacturer 制造商 */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /** @return 最大载重（kg） */
    public Double getMaxPayload() {
        return maxPayload;
    }

    /** @param maxPayload 最大载重（kg） */
    public void setMaxPayload(Double maxPayload) {
        this.maxPayload = maxPayload;
    }

    /** @return 最大飞行高度（m） */
    public Integer getMaxAltitude() {
        return maxAltitude;
    }

    /** @param maxAltitude 最大飞行高度（m） */
    public void setMaxAltitude(Integer maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    /** @return 最大飞行时间（min） */
    public Integer getMaxFlightTime() {
        return maxFlightTime;
    }

    /** @param maxFlightTime 最大飞行时间（min） */
    public void setMaxFlightTime(Integer maxFlightTime) {
        this.maxFlightTime = maxFlightTime;
    }

    /** @return 最大飞行速度（m/s） */
    public Double getMaxSpeed() {
        return maxSpeed;
    }

    /** @param maxSpeed 最大飞行速度（m/s） */
    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /** @return 翼展（m） */
    public Double getWingspan() {
        return wingspan;
    }

    /** @param wingspan 翼展（m） */
    public void setWingspan(Double wingspan) {
        this.wingspan = wingspan;
    }

    /** @return 自重（kg） */
    public Double getWeight() {
        return weight;
    }

    /** @param weight 自重（kg） */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /** @return 状态（0-停用，1-启用） */
    public Integer getStatus() {
        return status;
    }

    /** @param status 状态（0-停用，1-启用） */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** @return 备注说明 */
    public String getRemark() {
        return remark;
    }

    /** @param remark 备注说明 */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
