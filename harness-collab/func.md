# basePlatform 功能文档

## 项目概述

无人机管理系统，提供无人机的增删改查、搜索、统计等功能。

## 功能模块

### 1. 无人机管理

#### 1.1 新增无人机
- **接口**: `POST /drones`
- **描述**: 创建新的无人机记录
- **参数**: `DroneForm` 对象
  - `uavCode`: 无人机编号（必填，唯一）
  - `model`: 型号（必填）
  - `manufacturer`: 厂商（必填）
  - `maxPayload`: 最大载重（kg）
  - `maxAltitude`: 最大飞行高度（m）
  - `maxFlightTime`: 最大续航时间（min）
  - `maxSpeed`: 最大速度（m/s）
  - `wingspan`: 翼展（m）
  - `weight`: 自重（kg）
  - `status`: 状态（1启用，0停用）
  - `remark`: 备注
- **返回**: 创建的无人机对象

#### 1.2 查询无人机列表
- **接口**: `GET /drones/api`
- **描述**: 分页查询无人机列表，支持关键词搜索
- **参数**:
  - `keyword`: 搜索关键词（编号/型号/厂商/备注）
  - `pageNum`: 页码（默认1）
  - `pageSize`: 每页大小（默认10）
- **返回**: 分页结果 `PageResult<Drone>`

#### 1.3 查询单个无人机
- **接口**: `GET /drones/{id}`
- **描述**: 根据ID查询单个无人机详情
- **参数**: `id` - 无人机ID
- **返回**: 无人机对象

#### 1.4 更新无人机
- **接口**: `POST /drones/{id}`
- **描述**: 更新指定ID的无人机信息
- **参数**:
  - `id`: 无人机ID
  - `DroneForm`: 表单数据（同新增）
- **返回**: 更新后的无人机对象

#### 1.5 删除无人机
- **接口**: `DELETE /drones/{id}`
- **描述**: 逻辑删除指定ID的无人机
- **参数**: `id` - 无人机ID
- **返回**: 删除结果

#### 1.6 无人机统计
- **接口**: `GET /drones/stats`
- **描述**: 获取无人机统计信息
- **返回**: 统计数据
  - `total`: 总数量
  - `enabled`: 已启用数量
  - `disabled`: 已禁用数量

### 2. 认证授权

#### 2.1 用户登录
- **接口**: `POST /login`
- **描述**: 用户登录认证
- **参数**:
  - `username`: 用户名
  - `password`: 密码
- **返回**: 登录结果，包含用户名

#### 2.2 用户登出
- **接口**: `GET /logout`
- **描述**: 用户登出

#### 2.3 获取当前用户信息
- **接口**: `GET /auth/current`
- **描述**: 获取当前登录用户信息
- **返回**: 当前用户信息

## 技术栈

- **后端**: Spring Boot 2.2.13, MyBatis 3.5.x, Apache Shiro 1.7
- **数据库**: SQLite
- **前端**: HTML5, JavaScript (原生)
- **构建工具**: Maven
- **测试**: JUnit 5, Mockito

## 数据库表结构

### t_uav (无人机表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INTEGER | 主键，自增 |
| uav_code | VARCHAR(64) | 无人机编号，唯一 |
| model | VARCHAR(100) | 型号 |
| manufacturer | VARCHAR(100) | 厂商 |
| max_payload | DOUBLE | 最大载重(kg) |
| max_altitude | INTEGER | 最大飞行高度(m) |
| max_flight_time | INTEGER | 最大续航时间(min) |
| max_speed | DOUBLE | 最大速度(m/s) |
| wingspan | DOUBLE | 翼展(m) |
| weight | DOUBLE | 自重(kg) |
| status | INTEGER | 状态(1启用,0停用) |
| remark | VARCHAR(500) | 备注 |
| ai_generated | INTEGER | AI生成标记 |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |
| deleted | INTEGER | 删除标记(0未删除,1已删除) |

## API 文档

项目集成了 Swagger/OpenAPI 3.0，启动后访问 `/swagger-ui.html` 查看在线API文档。

## 测试

运行测试：
```bash
mvn clean verify -Pharness-new
```
覆盖率要求：行覆盖率 >= 80%
