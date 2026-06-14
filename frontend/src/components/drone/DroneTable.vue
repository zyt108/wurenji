<!--
  无人机数据表格组件

  功能说明：
  - 展示无人机列表，支持行选中、全选
  - 点击行或复选框切换选中状态
  - 提供编辑、删除操作链接
  - 无数据时显示空状态

  Props：
  - drones：当前页无人机数组
  - selectedIds：已选中的 id 列表
  - allSelected：是否全选

  事件：
  - toggle：切换单行选中（参数为 drone.id）
  - toggle-all：切换全选
  - edit：编辑（参数为 drone.id）
  - delete：删除（参数为 drone.id）
-->
<template>
  <div class="table-container">
    <table v-if="drones.length > 0" class="data-table">
      <thead>
        <tr>
          <th class="checkbox-col">
            <input type="checkbox" :checked="allSelected" @change="$emit('toggle-all')">
          </th>
          <th>序号</th>
          <th>编号</th>
          <th>型号</th>
          <th>厂商</th>
          <th>载重(kg)</th>
          <th>高度(m)</th>
          <th>续航(min)</th>
          <th>速度(m/s)</th>
          <th>状态</th>
          <th>备注</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="(drone, index) in drones"
          :key="drone.id"
          :class="{ selected: selectedIds.includes(drone.id) }"
          @click="$emit('toggle', drone.id)"
        >
          <!-- 复选框列：阻止冒泡避免与行点击重复触发 -->
          <td class="checkbox-col" @click.stop>
            <input
              type="checkbox"
              :checked="selectedIds.includes(drone.id)"
              @change="$emit('toggle', drone.id)"
            >
          </td>
          <td>{{ index + 1 }}</td>
          <td>{{ drone.uavCode }}</td>
          <td>{{ drone.model }}</td>
          <td>{{ drone.manufacturer }}</td>
          <td>{{ drone.maxPayload }}</td>
          <td>{{ drone.maxAltitude }}</td>
          <td>{{ drone.maxFlightTime }}</td>
          <td>{{ drone.maxSpeed }}</td>
          <td>
            <span class="status-tag" :class="drone.status === 1 ? 'status-normal' : 'status-disabled'">
              {{ drone.status === 1 ? '正常' : '停用' }}
            </span>
          </td>
          <td class="remark-col" :title="drone.remark">{{ drone.remark }}</td>
          <!-- 操作列：阻止冒泡，避免触发行选中 -->
          <td class="action-col" @click.stop>
            <a href="javascript:void(0)" class="link-edit" @click="$emit('edit', drone.id)">编辑</a>
            <a href="javascript:void(0)" class="link-delete" @click="$emit('delete', drone.id)">删除</a>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-else class="empty-state">
      <i class="bi bi-inbox"></i>
      <p>暂无无人机数据</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DroneTable',
  props: {
    /** 当前页无人机列表 */
    drones: {
      type: Array,
      default: () => []
    },
    /** 已勾选的无人机 id 数组 */
    selectedIds: {
      type: Array,
      default: () => []
    },
    /** 当前页是否已全部选中 */
    allSelected: {
      type: Boolean,
      default: false
    }
  },
  emits: ['toggle', 'toggle-all', 'edit', 'delete']
}
</script>
