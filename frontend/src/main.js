/**
 * 应用入口文件
 *
 * 功能说明：
 * - 创建 Vue 应用实例
 * - 注册路由插件
 * - 引入全局样式（Bootstrap + 业务样式）
 * - 挂载应用到 DOM
 *
 * @author AI Assistant
 */

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import './styles/main.css'
import './styles/drone-management.css'

/**
 * 创建 Vue 应用实例
 */
const app = createApp(App)

/**
 * 注册路由插件
 * 使整个应用可以使用 Vue Router
 */
app.use(router)

/**
 * 挂载应用到 index.html 中的 #app 元素
 */
app.mount('#app')
