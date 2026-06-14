/**
 * 无人机管理 API 调用模块
 *
 * 功能说明：
 * - 提供无人机的增删改查接口
 * - 支持分页查询和状态筛选
 *
 * @see request.js 底层 HTTP 请求封装
 */

import request from './request'

/**
 * 获取无人机列表
 *
 * 说明：
 * - 支持两种参数格式：字符串（原始查询字符串）或对象
 * - 字符串格式直接拼接到 URL
 * - 对象格式会自动转换为查询参数
 *
 * @param {Object|string} params - 查询参数
 * @param {string} [params.keyword] - 搜索关键词（无人机编号、型号、厂商）
 * @param {number|null} [params.status] - 状态筛选（1-启用，0-停用，null-全部）
 * @param {number} [params.pageNum] - 页码（默认1）
 * @param {number} [params.pageSize] - 每页条数（默认10）
 * @returns {Promise} 返回分页后的无人机列表
 */
export function getDroneList(params) {
  // 如果传入的是字符串，直接使用；如果是对象，转换为查询参数
  if (typeof params === 'string') {
    return request.get('/drones/api?' + params)
  }
  return request.get('/drones/api', { params })
}

/**
 * 创建无人机
 *
 * @param {Object} data - 无人机信息
 * @param {string} data.uavCode - 无人机编号
 * @param {string} data.model - 型号
 * @param {string} data.manufacturer - 厂商
 * @param {number} data.maxPayload - 最大载荷(kg)
 * @param {number} data.maxAltitude - 最大飞行高度(m)
 * @param {number} data.maxFlightTime - 最大飞行时间(min)
 * @param {number} data.maxSpeed - 最大飞行速度(km/h)
 * @param {number} data.wingspan - 翼展(m)
 * @param {number} data.weight - 重量(kg)
 * @param {number} data.status - 状态（1-启用，0-停用）
 * @param {string} [data.remark] - 备注
 * @returns {Promise} 返回创建后的无人机信息
 */
export function createDrone(data) {
  return request.post('/drones', data)
}

/**
 * 更新无人机
 *
 * @param {number} id - 无人机ID
 * @param {Object} data - 无人机信息（同 createDrone）
 * @returns {Promise} 返回更新后的无人机信息
 */
export function updateDrone(id, data) {
  return request.post(`/drones/${id}`, data)
}

/**
 * 删除无人机（逻辑删除）
 *
 * @param {number} id - 无人机ID
 * @returns {Promise} 返回删除操作结果
 */
export function deleteDrone(id) {
  return request.delete(`/drones/${id}`)
}

/**
 * 获取单个无人机详情
 *
 * @param {number} id - 无人机ID
 * @returns {Promise} 返回无人机详细信息
 */
export function getDrone(id) {
  return request.get(`/drones/${id}`)
}
