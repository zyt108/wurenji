<!--
  Vue 应用根组件

  功能说明：
  - 作为整个 Vue 应用的根组件
  - 只负责渲染路由视图

  路由说明：
  - router-view 会根据当前 URL 匹配路由规则
  - 显示对应路径的页面组件
-->
<template>
  <!-- 路由视图：显示当前路由对应的组件 -->
  <router-view />
</template>

<script>
/**
 * 根组件脚本
 */
export default {
  name: 'App'
}
</script>
