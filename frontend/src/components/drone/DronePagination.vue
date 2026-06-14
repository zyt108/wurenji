<!--
  无人机列表分页组件

  功能说明：
  - 展示总条数、每页条数选择、上一页/下一页
  - 仅当 total > 0 时显示

  Props：
  - total：总记录数
  - pageNum：当前页码
  - pages：总页数
  - pageSize：每页条数

  事件：
  - prev：上一页
  - next：下一页
  - update:pageSize：切换每页条数
-->


<!--
-v-if="total > 0"：条件渲染，总数据条数大于 0 才展示分页栏，无数据时直接隐藏。
-pagination-bar：样式类，控制分页栏整体布局。
-<select>：下拉选择框，用来切换每页展示条数。
-:value="pageSize"：单向绑定，让下拉框默认选中当前的每页条数。
-@change="onPageSizeChange"：切换选项时，触发onPageSizeChange方法。
-<option :value="数值">：选项绑定对应条数，选中后会拿到该数值。
-->


<template>

<!--外层容器，数据总数展示-->

  <div v-if="total > 0" class="pagination-bar">
    <span>共 {{ total }} 条</span>

<!--每页显示条数-->

    <select class="page-size" :value="pageSize" @change="onPageSizeChange">
      <option :value="10">10条/页</option>
      <option :value="20">20条/页</option>
      <option :value="50">50条/页</option>
    </select>

<!--翻页区域-->

    <div class="page-nav">
      <button class="page-btn" :disabled="pageNum <= 1" @click="$emit('prev')">&lt;</button>
      <span>{{ pageNum }} / {{ pages }}</span>
      <button class="page-btn" :disabled="pageNum >= pages" @click="$emit('next')">&gt;</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DronePagination',
  props: {
    /** 符合条件的总记录数 */
    total: { type: Number, default: 0 },
    /** 当前页码（从 1 开始） */
    pageNum: { type: Number, default: 1 },
    /** 总页数 */
    pages: { type: Number, default: 1 },
    /** 每页显示条数 */
    pageSize: { type: Number, default: 10 }
  },
  emits: ['prev', 'next', 'update:pageSize'],
  methods: {
    /**
     * 每页条数下拉变更
     * @param {Event} event change 事件
     */
    onPageSizeChange(event) {
      this.$emit('update:pageSize', Number(event.target.value))
    }
  }
}
</script>
