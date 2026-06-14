# 无人机管理系统需求文档

## 项目概述

开发一个无人机管理系统，用于管理无人机的基本信息、状态和属性。

## 功能需求

### 1. 无人机管理
- [x] 新增无人机
- [x] 查询无人机列表（分页、搜索）
- [x] 查询单个无人机详情
- [x] 更新无人机信息
- [x] 删除无人机（逻辑删除）
- [x] 无人机统计（总数、启用数、禁用数）

### 2. 认证授权
- [x] 用户登录
- [x] 用户登出
- [x] 获取当前用户信息

### 3. 界面功能
- [x] 登录页面
- [x] 无人机列表页面
- [x] 新增无人机页面（新窗口）
- [x] 编辑无人机页面（新窗口）
- [x] 统计信息展示

## 非功能需求

### 1. 性能
- 分页查询，每页默认10条
- 响应时间 < 2s

### 2. 安全
- 用户认证（Shiro）
- SQL注入防护（MyBatis）
- XSS防护

### 3. 可维护性
- 代码分层清晰（Controller-Service-Repository）
- 完整的注释文档
- 测试覆盖率 >= 80%

## 技术栈

- **后端**: Spring Boot 2.2.13, MyBatis 3.5.x, Apache Shiro 1.7
- **数据库**: SQLite
- **前端**: HTML5, JavaScript (原生)
- **构建工具**: Maven
- **测试**: JUnit 5, Mockito

## 数据模型

### 无人机 (Drone)
- id: 主键
- uavCode: 编号（唯一）
- model: 型号
- manufacturer: 厂商
- maxPayload: 最大载重
- maxAltitude: 最大高度
- maxFlightTime: 最大续航
- maxSpeed: 最大速度
- wingspan: 翼展
- weight: 自重
- status: 状态
- remark: 备注
- aiGenerated: AI生成标记
- createdAt: 创建时间
- updatedAt: 更新时间
- deleted: 删除标记

## 验收标准

1. 用户可以成功登录系统
2. 可以新增、编辑、删除无人机
3. 可以搜索无人机
4. 统计信息正确显示
5. 测试覆盖率 >= 80%
6. 代码符合Checkstyle和SpotBugs规范