<!--
  应用主布局组件

  功能说明：
  - 登录后页面的统一框架：左侧边栏 + 顶部导航 + 内容区
  - 侧边栏展示系统名称与首页导航
  - 顶栏展示面包屑与退出按钮
  - 主内容通过默认插槽 <slot /> 注入

  Props：
  - currentPage：面包屑中显示的当前页面名称

  事件：
  - logout：用户点击退出时触发，由父组件处理登出逻辑
-->
<template>
  <div class="app-container">
    <!-- 左侧边栏 -->
    <aside class="sidebar">
      <div class="sidebar-header">
        <i class="bi bi-airplane"></i>
        <span>无人机管理</span>
      </div>
      <nav class="sidebar-nav">
        <div class="nav-item active">
          <i class="bi bi-house-door"></i>
          <span>首页</span>
        </div>
      </nav>
    </aside>

    <!-- 右侧主区域 -->
    <div class="main-content">
      <!-- 顶部导航栏 -->
      <header class="top-header">
        <div class="breadcrumb">
          <i class="bi bi-list"></i>
          <span>首页</span>
          <span class="separator">/</span>
          <span class="current">{{ currentPage }}</span>
        </div>
        <div class="user-info" @click="$emit('logout')">
          <i class="bi bi-box-arrow-right"></i>
          <span>退出</span>
        </div>
      </header>

      <!-- 页面主体内容插槽 -->
      <div class="content-wrapper">
        <slot />
      </div>
    </div>
  </div>
</template>

<script>
/**
 * 主布局组件脚本
 */
export default {
  name: 'AppLayout',
  props: {
    /** 顶栏面包屑显示的当前页面标题 */
    currentPage: {
      type: String,
      default: '无人机管理'
    }
  },
  emits: ['logout']
}
</script>
