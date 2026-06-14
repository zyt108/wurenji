<!--
  编辑无人机页面

  功能说明：
  - 根据路由参数 id 加载无人机详情
  - 复用 AppLayout 布局与 DroneForm 表单
  - 保存成功后返回列表页

  路由：/drones/:id/edit
  路由参数：id（通过 props: true 注入）
-->
<template>
  <AppLayout current-page="编辑无人机" @logout="handleLogout">
    <DroneForm
      :form="form"
      title="编辑无人机"
      icon-class="bi bi-pencil-square"
      mode="edit"
      :error="formError"
      @submit="handleUpdate"
      @cancel="router.push('/drones')"
    />
  </AppLayout>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import AppLayout from '../components/layout/AppLayout.vue'
import DroneForm from '../components/drone/DroneForm.vue'
import { getDrone, updateDrone } from '../api/drone.js'
import { createEmptyDroneForm } from '../utils/droneForm.js'
import { useLogout } from '../composables/useLogout.js'

export default {
  name: 'DroneEdit',
  components: {
    AppLayout,
    DroneForm
  },
  /** 路由动态参数：无人机主键 id */
  props: ['id'],
  setup(props) {
    const router = useRouter()
    const { handleLogout } = useLogout()
    const formError = ref('')
    const form = reactive(createEmptyDroneForm())

    /**
     * 根据 id 拉取详情并填充表单
     */
    const loadData = async () => {
      try {
        const res = await getDrone(props.id)
        Object.assign(form, res.data)
      } catch (e) {
        console.error(e)
        alert('加载数据失败')
      }
    }

    /**
     * 提交更新请求
     */
    const handleUpdate = async () => {
      formError.value = ''
      try {
        await updateDrone(props.id, form)
        router.push('/drones')
      } catch (e) {
        formError.value = e.message || '更新失败'
      }
    }

    onMounted(loadData)

    return {
      form,
      formError,
      handleUpdate,
      handleLogout,
      router
    }
  }
}
</script>
