# API 文档

## 无人机管理 API

### 1. 创建无人机

**接口**: `POST /drones`

**请求体**:
```json
{
  "uavCode": "UAV-2026-001",
  "model": "DJI Mavic 3",
  "manufacturer": "DJI",
  "maxPayload": 0.5,
  "maxAltitude": 6000,
  "maxFlightTime": 46,
  "maxSpeed": 21,
  "wingspan": 0.347,
  "weight": 0.895,
  "status": 1,
  "remark": "测试无人机"
}
```

**响应**:
```json
{
  "success": true,
  "message": "操作成功",
  "data": {
    "id": 1,
    "uavCode": "UAV-2026-001",
    "model": "DJI Mavic 3",
    "manufacturer": "DJI",
    "maxPayload": 0.5,
    "maxAltitude": 6000,
    "maxFlightTime": 46,
    "maxSpeed": 21,
    "wingspan": 0.347,
    "weight": 0.895,
    "status": 1,
    "remark": "测试无人机",
    "aiGenerated": 0,
    "createdAt": "2026-06-05T10:00:00",
    "updatedAt": "2026-06-05T10:00:00",
    "deleted": 0
  }
}
```

### 2. 查询无人机列表

**接口**: `GET /drones/api?keyword={keyword}&pageNum={pageNum}&pageSize={pageSize}`

**参数**:
- `keyword`: 搜索关键词（可选）
- `pageNum`: 页码（默认1）
- `pageSize`: 每页大小（默认10）

**响应**:
```json
{
  "success": true,
  "message": "操作成功",
  "data": {
    "list": [
      {
        "id": 1,
        "uavCode": "UAV-2026-001",
        "model": "DJI Mavic 3",
        "manufacturer": "DJI",
        "maxPayload": 0.5,
        "maxAltitude": 6000,
        "maxFlightTime": 46,
        "maxSpeed": 21,
        "wingspan": 0.347,
        "weight": 0.895,
        "status": 1,
        "remark": "测试无人机",
        "aiGenerated": 0,
        "createdAt": "2026-06-05T10:00:00",
        "updatedAt": "2026-06-05T10:00:00",
        "deleted": 0
      }
    ],
    "total": 1,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 1
  }
}
```

### 3. 查询单个无人机

**接口**: `GET /drones/{id}`

**响应**:
```json
{
  "success": true,
  "message": "操作成功",
  "data": {
    "id": 1,
    "uavCode": "UAV-2026-001",
    "model": "DJI Mavic 3",
    "manufacturer": "DJI",
    "maxPayload": 0.5,
    "maxAltitude": 6000,
    "maxFlightTime": 46,
    "maxSpeed": 21,
    "wingspan": 0.347,
    "weight": 0.895,
    "status": 1,
    "remark": "测试无人机",
    "aiGenerated": 0,
    "createdAt": "2026-06-05T10:00:00",
    "updatedAt": "2026-06-05T10:00:00",
    "deleted": 0
  }
}
```

### 4. 更新无人机

**接口**: `POST /drones/{id}`

**请求体**:
```json
{
  "uavCode": "UAV-2026-001",
  "model": "DJI Mavic 3 Pro",
  "manufacturer": "DJI",
  "maxPayload": 0.5,
  "maxAltitude": 6000,
  "maxFlightTime": 46,
  "maxSpeed": 21,
  "wingspan": 0.347,
  "weight": 0.895,
  "status": 1,
  "remark": "测试无人机"
}
```

**响应**:
```json
{
  "success": true,
  "message": "操作成功",
  "data": {
    "id": 1,
    "uavCode": "UAV-2026-001",
    "model": "DJI Mavic 3 Pro",
    "manufacturer": "DJI",
    "maxPayload": 0.5,
    "maxAltitude": 6000,
    "maxFlightTime": 46,
    "maxSpeed": 21,
    "wingspan": 0.347,
    "weight": 0.895,
    "status": 1,
    "remark": "测试无人机",
    "aiGenerated": 0,
    "createdAt": "2026-06-05T10:00:00",
    "updatedAt": "2026-06-05T11:00:00",
    "deleted": 0
  }
}
```

### 5. 删除无人机

**接口**: `DELETE /drones/{id}`

**响应**:
```json
{
  "success": true,
  "message": "操作成功",
  "data": null
}
```

### 6. 无人机统计

**接口**: `GET /drones/stats`

**响应**:
```json
{
  "success": true,
  "message": "操作成功",
  "data": {
    "total": 10,
    "enabled": 8,
    "disabled": 2
  }
}
```

## 认证 API

### 1. 用户登录

**接口**: `POST /login`

**请求体**: `application/x-www-form-urlencoded`
```
username=admin&password=admin
```

**响应**:
```json
{
  "success": true,
  "message": "登录成功",
  "data": {
    "username": "admin"
  }
}
```

### 2. 用户登出

**接口**: `GET /logout`

**响应**: 重定向到登录页面

### 3. 获取当前用户信息

**接口**: `GET /auth/current`

**响应**:
```json
{
  "success": true,
  "message": "操作成功",
  "data": {
    "username": "admin"
  }
}
```

## 通用响应格式

所有API响应都遵循以下格式：

```json
{
  "success": true/false,
  "message": "操作描述",
  "data": {}
}
```
## 错误码

| 错误码 | 说明 |
|--------|------|
| 400 | 参数错误 |
| 401 | 未认证 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务器错误 |
