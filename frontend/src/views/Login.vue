<!--
  登录页面组件

  功能说明：
  - 提供用户登录界面
  - 表单包含用户名和密码输入
  - 登录成功后自动跳转到无人机列表页

  使用方式：
  - 访问 /login 路径即可显示此页面
  - 路由守卫会自动拦截未登录用户跳转至此
-->
<template>
  <div class="container mt-5">
    <!-- 居中显示的登录卡片 -->
    <div class="row justify-content-center">
      <div class="col-md-4">
        <div class="card">
          <!-- 登录卡片头部 - 平台标题 -->
          <div class="card-header text-center">
            <h4>无人机管理平台</h4>
          </div>
          <!-- 登录表单 -->
          <div class="card-body">
            <form @submit.prevent="handleLogin">
              <!-- 用户名输入框 -->
              <div class="form-group">
                <label>用户名</label>
                <input
                  v-model="form.username"
                  type="text"
                  class="form-control"
                  required
                >
              </div>
              <!-- 密码输入框 -->
              <div class="form-group">
                <label>密码</label>
                <input
                  v-model="form.password"
                  type="password"
                  class="form-control"
                  required
                >
              </div>
              <!-- 错误提示信息 -->
              <div v-if="error" class="alert alert-danger">{{ error }}</div>
              <!-- 登录按钮 -->
              <button
                type="submit"
                class="btn btn-primary btn-block"
                :disabled="loading"
              >
                {{ loading ? '登录中...' : '登录' }}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/**
 * Vue Composition API setup 函数
 * 使用说明：
 * - reactive: 创建响应式表单对象
 * - ref: 创建响应式状态变量
 * - useRouter: 获取路由实例进行页面跳转
 */
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../api/auth.js'

export default {
  // 组件名称
  name: 'Login',

  /**
   * Composition API setup 函数
   * @returns {Object} 返回模板中使用的响应式数据和方法
   */
  setup() {
    // 获取路由实例，用于登录成功后跳转
    const router = useRouter()

    // 登录表单数据 - 使用 reactive 创建响应式对象
    // 预设默认账号密码（仅用于演示）
    const form = reactive({
      username: 'admin',
      password: 'admin123'
    })

    // 错误提示信息
    const error = ref('')

    // 加载状态 - 防止重复提交
    const loading = ref(false)

    /**
     * 处理登录提交
     *
     * 处理流程：
     * 1. 设置加载状态
     * 2. 调用登录 API
     * 3. 登录成功则跳转到列表页
     * 4. 登录失败显示错误信息
     * 5. 最后关闭加载状态
     */
    const handleLogin = async () => {
      loading.value = true
      try {
        // 调用登录 API
        await login(form)
        // 登录成功后，跳转到列表页
        router.push('/drones')
      } catch (e) {
        // 登录失败，显示错误提示
        error.value = '用户名或密码错误'
        console.error('登录失败:', e)
      } finally {
        // 无论成功失败，都要关闭加载状态
        loading.value = false
      }
    }

    // 返回模板中使用的响应式数据和方法
    return { form, error, loading, handleLogin }
  }
}
</script>
