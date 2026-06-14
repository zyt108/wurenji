/**
 * Vue Router 路由配置模块
 *
 * 路由 = 地址与页面的对应关系，实现单页应用导航。
 *
 * 功能说明：
 * - 定义应用的所有路由规则
 * - 实现路由守卫进行登录验证
 * - 配置路由元信息
 *
 * @module router
 * @see https://router.vuejs.org/
 */

import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import DroneList from '../views/DroneList.vue'
import DroneEdit from '../views/DroneEdit.vue'
import { getCurrentUser } from '../api/auth.js'

/**
 * 路由配置数组
 *
 * 路由说明：
 * - / : 根路径重定向到 /drones
 * - /login : 登录页面（无需认证）
 * - /drones : 无人机列表页（需认证）
 * - /drones/:id/edit : 编辑无人机页面（需认证）
 * component = 组件名
 */
const routes = [
  // 根路径重定向到无人机列表
  { path: '/', redirect: '/drones' },
  // 登录页面
  { path: '/login', component: Login },
  // 无人机列表页
  { path: '/drones', component: DroneList },
  // 编辑无人机页面（接收 id 作为 props）
  { path: '/drones/:id/edit', component: DroneEdit, props: true }
]

/**
 * 创建 Vue Router 实例
 *
 * history 模式：使用 HTML5 History API
 * 优点：URL 格式美观（无 # 号）
 * 缺点：需要服务器配置支持 fallback
 */
const router = createRouter({
  history: createWebHistory(),
  routes
})

/**
 * 路由守卫（全局前置守卫）
 *
 * 功能：在每次路由跳转前检查用户是否已登录
 * - 登录页面直接放行
 * - 其他页面需要验证身份，未登录则跳转到登录页
 *
 * @param {Route} to - 目标路由对象
 * @param {Route} from - 来源路由对象
 * @param {Function} next - 继续导航的函数
 */
router.beforeEach(async (to, from, next) => {
  // 登录页面直接放行，不进行认证检查
  if (to.path === '/login') {
    next()
    return
  }

  try {
    // 检查是否已登录
    const data = await getCurrentUser()

    // 如果用户已认证，允许访问
    if (data.success && data.data.authenticated) {
      next()
    } else {
      // 用户未认证，跳转到登录页
      next('/login')
    }
  } catch (e) {
    // 请求失败（可能未登录），跳转到登录页
    next('/login')
  }
})

/**
 * 导出路由实例
 * 在 main.js 中通过 app.use(router) 注册
 */
export default router
