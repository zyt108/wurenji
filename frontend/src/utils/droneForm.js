/**
 * 无人机表单工具模块
 *
 * 功能说明：
 * - 提供空表单对象的工厂函数
 * - 新增、编辑页面共用，保证字段初始值一致
 * - 创建成功后可用同一函数重置表单
 */

/**
 * 创建空的无人机表单对象
 *
 * 字段说明：
 * - 字符串字段默认为空字符串
 * - 数值字段默认为 null（便于 v-model.number 与必填校验）
 * - status 默认为 1（启用）
 *
 * @returns {Object} 可放入 reactive() 的表单初始数据
 */
export function createEmptyDroneForm() {
  return {
    uavCode: '',
    model: '',
    manufacturer: '',
    maxPayload: null,
    maxAltitude: null,
    maxFlightTime: null,
    maxSpeed: null,
    wingspan: null,
    weight: null,
    status: 1,
    remark: ''
  }
}
