/**
 * 退出登录组合式函数
 *
 * 功能说明：
 * - 封装「确认 → 调用登出 API → 跳转登录页」流程
 * - 供 DroneList、DroneEdit 等已登录页面复用
 *
 * 流程：
 * 1. 确认退出登录
 * 2. 调用登出 API
 * 3. 跳转登录页
 * 
 * 参数：
 * - confirmMessage：确认框提示文案
 * 
 * 返回值：
 * - handleLogout：处理用户退出方法
 *  
 * @module composables/useLogout
 */

import { useRouter } from 'vue-router'
import { logout } from '../api/auth.js'

/**
 * 提供退出登录方法
 *
 * @returns {{ handleLogout: (confirmMessage?: string) => Promise<void> }}
 */
/**把函数暴露出去，供其他文件 import 导入使用*/
export function useLogout() {
  const router = useRouter()

  /**
   * 处理用户退出
   *
   * @param {string} [confirmMessage='确认退出登录？'] 确认框提示文案
   */
  const handleLogout = async (confirmMessage = '确认退出登录？') => {
    if (!confirm(confirmMessage)) return
    try {
      await logout()
    } catch (e) {
      console.error('登出失败', e)
    }
    router.push('/login')
  }

  return { handleLogout }
}
