# 无人机管理系统设计文档

## 系统架构

前后端分离：Vue 3 前端 + Spring Boot REST API 后端。

```
Controller → Service → Repository → SQLite/MySQL
```

### 包结构

```
com.md.basePlatform
├── controller/     DroneController, AuthController
├── service/        DroneService, AiAttributeService
├── repository/     DroneRepository
├── domain/         Drone, DroneForm
├── config/         ShiroConfig, WebMvcConfig
├── common/         ApiResponse, PageResult
├── exception/      BusinessException, GlobalExceptionHandler
└── interceptor/    RequestLogInterceptor
```

## API 设计

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /drones | 查询列表（分页） |
| GET | /drones/{id} | 查询详情 |
| POST | /drones | 新增 |
| POST | /drones/{id} | 更新 |
| DELETE | /drones/{id} | 删除 |
| POST | /login | 登录 |
| GET | /logout | 登出 |
| GET | /current-user | 当前用户 |

## 安全设计

- Apache Shiro 用户名密码认证
- 匿名访问：`/login`, `/logout`, `/current-user`, Swagger
- 其他 API 需登录后访问
