/**
 * 认证相关 API 模块
 *
 * 说明：
 * - 封装登录、登出、当前用户查询
 * - 登录使用 application/x-www-form-urlencoded，兼容 Apache Shiro 表单认证
 * - 所有请求经 request.js 统一代理到 /api 前缀
 */

import request from './request'

/**
 * 用户登录
 * data：登录凭据，包含用户名和密码。
 * @param {Object} data 登录凭据
 * @param {string} data.username 用户名
 * @param {string} data.password 密码
 * @returns {Promise<Object>} 统一响应体，data 含 username
 */
export function login(data) {
  const params = new URLSearchParams()
  params.append('username', data.username)
  params.append('password', data.password)
  return request.post('/login', params, {
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
  })
}

/**
 * 用户登出
 * 清除服务端 Shiro Session
 *
 * @returns {Promise<Object>} 统一响应体
 */
export function logout() {
  return request.get('/logout')
}

/**
 * 查询当前登录状态
 * 路由守卫在每次导航前调用，判断是否 authenticated
 *
 * @returns {Promise<Object>} data.authenticated 为 true 表示已登录
 */
export function getCurrentUser() {
  return request.get('/current-user')
}
