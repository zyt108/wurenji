<!--
  无人机表单组件（新增/编辑复用）

  功能说明：
  - 包含无人机全部字段：编号、型号、厂商、性能参数、状态、备注
  - mode=create：底部显示「保存」「取消」
  - mode=edit：底部显示「返回」「保存」
  - form 为父组件传入的 reactive 对象，v-model 直接修改父级数据

  Props：
  - form：表单数据对象（必填）
  - title：表单标题
  - iconClass：标题图标 Bootstrap Icons 类名
  - mode：create | edit
  - showBack：是否在标题栏显示返回列表按钮
  - error：服务端或客户端错误提示

  事件：
  - submit：表单校验通过后提交
  - cancel：取消或返回
-->
<template>
  <div class="form-card">
    <!-- 表单标题栏 -->
    <div v-if="title" class="form-header">
      <h5>
        <i :class="iconClass"></i>
        {{ title }}
      </h5>
      <button v-if="showBack" type="button" class="btn-back" @click="$emit('cancel')">
        <i class="bi bi-arrow-left"></i> 返回列表
      </button>
    </div>

    <div class="form-body">
      <!-- 错误提示 -->
      <div v-if="error" class="form-error">{{ error }}</div>


<!--form-row：样式类，实现两个输入框并排一行。
-form-group：单个输入项容器，包裹标签 + 输入框。
-<label>：文本说明；
-<span class="required">*</span>：红色星号，标记必填项。
-v-model="form.uavCode" / v-model="form.model"：Vue 双向绑定：输入框内容 和 form 对象里的字段实时同步。
-type="text"：文本输入框。
-placeholder：输入框默认提示文字。
-required：原生 HTML 校验，浏览器会强制该项不能为空
-->


      <form @submit.prevent="$emit('submit')">
        <!-- 编号 + 型号 -->
        <div class="form-row">
          <div class="form-group">
            <label>编号 <span class="required">*</span></label>
            <input v-model="form.uavCode" type="text" placeholder="请输入编号" required>
          </div>
          <div class="form-group">
            <label>型号 <span class="required">*</span></label>
            <input v-model="form.model" type="text" placeholder="请输入型号" required>
          </div>
        </div>

        <!-- 厂商 + 状态 -->
        <div class="form-row">
          <div class="form-group">
            <label>厂商</label>
            <input v-model="form.manufacturer" type="text" placeholder="请输入厂商">
          </div>
          <div class="form-group">
            <label>状态</label>
            <select v-model.number="form.status">
              <option :value="1">启用</option>
              <option :value="0">停用</option>
            </select>
          </div>
        </div>

        <!-- 载重 + 最大高度 -->
        <div class="form-row">
          <div class="form-group">
            <label>载重(kg) <span class="required">*</span></label>
            <input v-model.number="form.maxPayload" type="number" step="0.1" min="0.1" max="10000" required>
          </div>
          <div class="form-group">
            <label>最大高度(m) <span class="required">*</span></label>
            <input v-model.number="form.maxAltitude" type="number" min="1" max="10000" required>
          </div>
        </div>

        <!-- 续航 + 最大速度 -->
        <div class="form-row">
          <div class="form-group">
            <label>续航时间(min) <span class="required">*</span></label>
            <input v-model.number="form.maxFlightTime" type="number" min="1" max="1440" required>
          </div>
          <div class="form-group">
            <label>最大速度(m/s) <span class="required">*</span></label>
            <input v-model.number="form.maxSpeed" type="number" step="0.1" min="0.1" max="340" required>
          </div>
        </div>

        <!-- 翼展 + 自重 -->
        <div class="form-row">
          <div class="form-group">
            <label>翼展(m) <span class="required">*</span></label>
            <input v-model.number="form.wingspan" type="number" step="0.1" min="0.1" max="100" required>
          </div>
          <div class="form-group">
            <label>自重(kg) <span class="required">*</span></label>
            <input v-model.number="form.weight" type="number" step="0.001" min="0.001" max="10000" required>
          </div>
        </div>

        <!-- 备注 -->
        <div class="form-row">
          <div class="form-group full-width">
            <label>备注</label>
            <textarea v-model="form.remark" rows="3" placeholder="请输入备注"></textarea>
          </div>
        </div>

        <!-- 新增模式：保存 + 取消 -->
        <div v-if="mode === 'create'" class="form-actions">
          <button type="submit" class="btn-submit">
            <i class="bi bi-check-circle"></i> 保存
          </button>
          <button type="button" class="btn-cancel" @click="$emit('cancel')">
            <i class="bi bi-x-circle"></i> 取消
          </button>
        </div>

        <!-- 编辑模式：返回 + 保存 -->
        <div v-else class="form-footer">
          <button type="button" class="btn-back" @click="$emit('cancel')">
            <i class="bi bi-arrow-left"></i> 返回
          </button>
          <button type="submit" class="btn-submit">
            <i class="bi bi-check-circle"></i> 保存
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DroneForm',
  props: {
    /** 父组件 reactive 表单对象，字段双向绑定 */
    form: {
      type: Object,
      required: true
    },
    /** 卡片标题文案 */
    title: {
      type: String,
      default: ''
    },
    /** 标题左侧图标 class */
    iconClass: {
      type: String,
      default: 'bi bi-plus-circle'
    },
    /** 表单模式：create 新增 / edit 编辑 */
    mode: {
      type: String,
      default: 'create',
      validator: (value) => ['create', 'edit'].includes(value)
    },
    /** 是否在标题栏显示「返回列表」 */
    showBack: {
      type: Boolean,
      default: false
    },
    /** 提交失败时的错误信息 */
    error: {
      type: String,
      default: ''
    }
  },
  emits: ['submit', 'cancel']
}
</script>
