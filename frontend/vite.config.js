/**
 * Vite 构建与开发服务器配置
 *
 * 功能说明：
 * - 启用 Vue 3 单文件组件编译
 * - 开发时将 /api 代理到 Spring Boot 后端，解决跨域
 *
 * 代理规则：
 * 前端请求 /api/drones → 转发为 http://localhost:8081/drones
 */

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  /** Vue 单文件组件支持 */
  plugins: [vue()],

  server: {
    /** 开发服务器端口，访问 http://localhost:5173 */
    port: 5173,

    /**
     * API 反向代理
     * - changeOrigin: 修改请求头 Host，避免后端校验失败
     * - rewrite: 去掉 /api 前缀，与后端路径对齐
     */
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
