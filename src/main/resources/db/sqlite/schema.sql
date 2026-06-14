-- 编号唯一性仅约束「未删除」行：软删除后可重用同一 uav_code（见 uk_t_uav_uav_code_active）
CREATE TABLE IF NOT EXISTS t_uav (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  uav_code VARCHAR(64) NOT NULL,
  model VARCHAR(100) NOT NULL,
  manufacturer VARCHAR(100),
  max_payload DOUBLE,
  max_altitude INTEGER,
  max_flight_time INTEGER,
  max_speed DOUBLE,
  wingspan DOUBLE,
  weight DOUBLE,
  status INTEGER NOT NULL DEFAULT 1,
  remark VARCHAR(500),
  ai_generated INTEGER NOT NULL DEFAULT 0,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted INTEGER NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_t_uav_uav_code_active ON t_uav(uav_code) WHERE deleted = 0;

-- 操作日志表
CREATE TABLE IF NOT EXISTS t_operation_log (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  username VARCHAR(64),
  operation_type VARCHAR(50),
  description VARCHAR(255),
  request_method VARCHAR(10),
  request_url VARCHAR(255),
  request_params VARCHAR(500),
  ip_address VARCHAR(64),
  execution_time BIGINT,
  status INTEGER NOT NULL DEFAULT 1,
  error_msg VARCHAR(500),
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT OR IGNORE INTO t_uav (id, uav_code, model, manufacturer, max_payload, max_altitude, max_flight_time, max_speed, wingspan, weight, status, remark, ai_generated, deleted)
VALUES
  (1, 'UAV-2026-001', 'DJI Mini 3 Pro', 'DJI', 0.5, 4000, 34, 16.0, 25.0, 0.249, 1, '消费级迷你无人机', 0, 0),
  (2, 'UAV-2026-002', 'Matrice 300 RTK', 'DJI', 2.7, 5000, 55, 23.0, 895.0, 6.3, 1, '工业级巡检无人机', 0, 0),
  (3, 'UAV-2026-003', '消费级四旋翼-AI', 'AI生成', 1.2, 2000, 28, 14.5, 35.0, 0.8, 1, '由AI规则引擎自动生成', 1, 0);
