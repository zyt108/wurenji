<!--
  无人机列表页

  功能说明：
  - 列表模式：搜索、表格、分页、批量删除、跳转编辑
  - 新增模式：内嵌 DroneForm，不跳转新路由
  - 业务逻辑在本页编排，UI 拆分到子组件

  路由：/drones
-->
<template>
  <AppLayout current-page="无人机管理" @logout="handleLogout">
    <!-- 列表视图 -->
    <template v-if="!showCreateForm">
      <DroneSearchBar
        v-model="searchForm"
        @search="handleSearch"
        @reset="handleReset"
      />

      <!-- 工具栏：新增、批量删除 -->
      <div class="action-bar">
        <button type="button" class="btn-add" @click="showCreateForm = true">
          <i class="bi bi-plus"></i> 新增
        </button>
        <button
          type="button"
          class="btn-delete"
          :disabled="selectedIds.length === 0"
          @click="handleBatchDelete"
        >
          <i class="bi bi-trash"></i> 删除
        </button>
      </div>

      <DroneTable
        :drones="drones"
        :selected-ids="selectedIds"
        :all-selected="isAllSelected"
        @toggle="toggleSelect"
        @toggle-all="toggleSelectAll"
        @edit="handleEditItem"
        @delete="handleDelete"
      />

      <DronePagination
        :total="total"
        :page-num="pageNum"
        :pages="pages"
        :page-size="pageSize"
        @prev="goPrevPage"
        @next="goNextPage"
        @update:page-size="onPageSizeChange"
      />
    </template>

    <!-- 新增表单视图 -->
    <DroneForm
      v-else
      :form="form"
      title="新增无人机"
      icon-class="bi bi-plus-circle"
      mode="create"
      show-back
      @submit="handleCreate"
      @cancel="showCreateForm = false"
    />
  </AppLayout>
</template>

<script>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import AppLayout from '../components/layout/AppLayout.vue'
import DroneSearchBar from '../components/drone/DroneSearchBar.vue'
import DroneTable from '../components/drone/DroneTable.vue'
import DronePagination from '../components/drone/DronePagination.vue'
import DroneForm from '../components/drone/DroneForm.vue'
import { getDroneList, createDrone, deleteDrone } from '../api/drone.js'
import { createEmptyDroneForm } from '../utils/droneForm.js'
import { useLogout } from '../composables/useLogout.js'

export default {
  name: 'DroneList',
  components: {
    AppLayout,
    DroneSearchBar,
    DroneTable,
    DronePagination,
    DroneForm
  },
  setup() {
    const router = useRouter()
    const { handleLogout } = useLogout()

    /** 当前页列表数据 */
    const drones = ref([])
    /** 是否显示内嵌新增表单 */
    const showCreateForm = ref(false)
    /** 表格勾选的 id 列表 */
    const selectedIds = ref([])
    /** 分页：当前页、每页条数、总数、总页数 */
    const pageNum = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    const pages = ref(1)

    /** 搜索条件 */
    const searchForm = reactive({
      uavCode: '',
      model: '',
      status: null
    })

    /** 新增表单数据 */
    const form = reactive(createEmptyDroneForm())

    /** 当前页是否已全选 */
    const isAllSelected = computed(() => {
      return drones.value.length > 0 && selectedIds.value.length === drones.value.length
    })

    /**
     * 加载列表数据
     * 编号与型号同时存在时，优先用编号作为 keyword
     */
    const loadData = async () => {
      try {
        const params = {
          pageNum: pageNum.value,
          pageSize: pageSize.value
        }
        if (searchForm.uavCode) {
          params.keyword = searchForm.uavCode
        } else if (searchForm.model) {
          params.keyword = searchForm.model
        }
        if (searchForm.status !== null) {
          params.status = searchForm.status
        }

        const res = await getDroneList(params)
        drones.value = res.data.list || []
        total.value = res.data.total || 0
        pages.value = res.data.pages || 1
      } catch (e) {
        console.error(e)
      }
    }

    /** 查询：重置到第 1 页后拉取 */
    const handleSearch = () => {
      pageNum.value = 1
      loadData()
    }

    /** 重置搜索条件并刷新 */
    const handleReset = () => {
      searchForm.uavCode = ''
      searchForm.model = ''
      searchForm.status = null
      pageNum.value = 1
      loadData()
    }

    /** 切换单行选中 */
    const toggleSelect = (id) => {
      const index = selectedIds.value.indexOf(id)
      if (index === -1) {
        selectedIds.value.push(id)
      } else {
        selectedIds.value.splice(index, 1)
      }
    }

    /** 全选 / 取消全选当前页 */
    const toggleSelectAll = () => {
      if (isAllSelected.value) {
        selectedIds.value = []
      } else {
        selectedIds.value = drones.value.map((d) => d.id)
      }
    }

    /** 提交新增 */
    const handleCreate = async () => {
      try {
        await createDrone(form)
        alert('创建成功')
        Object.assign(form, createEmptyDroneForm())
        showCreateForm.value = false
        selectedIds.value = []
        loadData()
      } catch (e) {
        alert('创建失败: ' + (e.message || '未知错误'))
      }
    }

    /** 跳转编辑页 */
    const handleEditItem = (id) => {
      router.push(`/drones/${id}/edit`)
    }

    /** 删除单条 */
    const handleDelete = async (id) => {
      if (!confirm('确认删除该无人机？')) return
      try {
        await deleteDrone(id)
        loadData()
      } catch (e) {
        alert('删除失败: ' + e.message)
      }
    }

    /** 批量删除已勾选记录 */
    const handleBatchDelete = async () => {
      if (selectedIds.value.length === 0) return
      if (!confirm(`确认删除选中的 ${selectedIds.value.length} 个无人机？`)) return
      try {
        for (const id of selectedIds.value) {
          await deleteDrone(id)
        }
        selectedIds.value = []
        loadData()
        alert('批量删除成功')
      } catch (e) {
        alert('删除失败: ' + e.message)
      }
    }

    const goPrevPage = () => {
      if (pageNum.value <= 1) return
      pageNum.value -= 1
      loadData()
    }

    const goNextPage = () => {
      if (pageNum.value >= pages.value) return
      pageNum.value += 1
      loadData()
    }

    const onPageSizeChange = (size) => {
      pageSize.value = size
      loadData()
    }

    onMounted(loadData)

    return {
      drones,
      showCreateForm,
      selectedIds,
      pageNum,
      pageSize,
      total,
      pages,
      searchForm,
      form,
      isAllSelected,
      handleSearch,
      handleReset,
      handleCreate,
      handleEditItem,
      handleDelete,
      handleBatchDelete,
      toggleSelect,
      toggleSelectAll,
      handleLogout,
      goPrevPage,
      goNextPage,
      onPageSizeChange
    }
  }
}
</script>
