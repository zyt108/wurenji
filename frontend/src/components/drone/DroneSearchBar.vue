<!--
  无人机搜索栏组件

  功能说明：
  - 提供编号、型号、状态三个筛选条件
  - 使用 v-model 双向绑定父组件的 searchForm 对象
  - 点击「查询」「重置」时通过事件通知父组件拉取数据

  Props：
  - modelValue：搜索表单对象 { uavCode, model, status }

  事件：
  - update:modelValue：表单字段变更时同步到父组件
  - search：点击查询
  - reset：点击重置
-->
<template>
  <div class="search-bar">
    <!-- 编号筛选 -->
    <div class="search-item">
      <label>编号</label>
      <input v-model="localForm.uavCode" type="text" placeholder="请输入无人机编号">
    </div>
    <!-- 型号筛选 -->
    <div class="search-item">
      <label>型号</label>
      <input v-model="localForm.model" type="text" placeholder="请输入型号">
    </div>
    <!-- 状态筛选 -->
    <div class="search-item">
      <label>状态</label>
      <select v-model.number="localForm.status">
        <option :value="null">全部</option>
        <option :value="1">启用</option>
        <option :value="0">停用</option>
      </select>
    </div>
    <!-- 操作按钮 -->
    <div class="search-buttons">
      <button type="button" class="btn-search" @click="$emit('search')">
        <i class="bi bi-search"></i> 查询
      </button>
      <button type="button" class="btn-reset" @click="$emit('reset')">
        <i class="bi bi-arrow-clockwise"></i> 重置
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DroneSearchBar',
  props: {
    /** 父组件传入的搜索条件对象（v-model） */
    modelValue: {
      type: Object,
      required: true
    }
  },
  emits: ['update:modelValue', 'search', 'reset'],
  computed: {
    /**
     * 计算属性代理 v-model
     * get 读取父级数据，set 通过 emit 回写
     */
    localForm: {
      get() {
        return this.modelValue
      },
      set(value) {
        this.$emit('update:modelValue', value)
      }
    }
  }
}
</script>
