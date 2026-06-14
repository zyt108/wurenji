/**
 * Axios HTTP 请求封装
 *Axios：前端最常用的 HTTP 请求库，专门用来调用后端接口
 * 功能说明：
 * - 统一配置 axios 实例，设置 baseURL 和超时时间
 * - 配置 withCredentials 支持跨域 Cookie 传递
 * - 添加请求/响应拦截器处理统一逻辑
 *
 * @author AI Assistant
 */

import axios from 'axios'

/**
 * 创建 axios 实例
 * baseURL: /api - 代理到后端服务器
 * timeout: 10秒 - 请求超时时间
 * withCredentials: true - 允许跨域携带 Cookie
 */
const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
  withCredentials: true  // 允许携带Cookie
})

/**
 * 请求拦截器
 * 在请求发送前执行，可用于添加 token 等操作
 * config
代表当前请求的所有配置对象：请求地址、请求头、请求参数、请求方式等。直接返回config对象，不做任何处理。
 */
request.interceptors.request.use(config => {
  return config
})

/**
 * 响应拦截器
 * 统一处理响应数据和错误
 */
request.interceptors.response.use(
  /**
   * 成功响应处理
   * 检查业务状态码 success，若为 false 则提示错误信息
   * response.data：后端返回的响应数据，通常是 JSON 格式。
   * 直接返回response.data，不做任何处理。
   
   */
  response => {
    const data = response.data
    if (!data.success) {
      alert(data.message || '请求失败')
      return Promise.reject(new Error(data.message))
    }
    return data
  },
  /**
   * 错误响应处理
   * 401 未授权跳转到登录页，其他错误提示网络问题
   */
  error => {
    if (error.response && error.response.status === 401) {
      window.location.href = '/login'
    } else {
      alert('网络错误: ' + (error.message || '未知错误'))
    }
    return Promise.reject(error)
  }
)

/**
 * 导出封装的请求实例
 * 供其他模块使用
 */
export default request
