CREATE TABLE IF NOT EXISTS t_uav (
  id BIGINT NOT NULL AUTO_INCREMENT,
  uav_code VARCHAR(64) NOT NULL,
  model VARCHAR(100) NOT NULL,
  manufacturer VARCHAR(100),
  max_payload DOUBLE,
  max_altitude INT,
  max_flight_time INT,
  max_speed DOUBLE,
  wingspan DOUBLE,
  weight DOUBLE,
  status TINYINT NOT NULL DEFAULT 1,
  remark VARCHAR(500),
  ai_generated TINYINT NOT NULL DEFAULT 0,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE KEY uk_uav_code (uav_code),
  KEY idx_model (model),
  KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='无人机信息表';

CREATE TABLE IF NOT EXISTS t_operation_log (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(64),
  operation_type VARCHAR(50),
  description VARCHAR(255),
  request_method VARCHAR(10),
  request_url VARCHAR(255),
  request_params VARCHAR(500),
  ip_address VARCHAR(64),
  execution_time BIGINT,
  status TINYINT NOT NULL DEFAULT 1,
  error_msg VARCHAR(500),
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_username (username),
  KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

INSERT INTO t_uav (uav_code, model, manufacturer, max_payload, max_altitude, max_flight_time, max_speed, wingspan, weight, status, remark, ai_generated, deleted)
VALUES
  ('UAV-2026-001', 'DJI Mini 3 Pro', 'DJI', 0.5, 4000, 34, 16.0, 25.0, 0.249, 1, '消费级迷你无人机', 0, 0),
  ('UAV-2026-002', 'Matrice 300 RTK', 'DJI', 2.7, 5000, 55, 23.0, 895.0, 6.3, 1, '工业级巡检无人机', 0, 0),
  ('UAV-2026-003', '消费级四旋翼-AI', 'AI生成', 1.2, 2000, 28, 14.5, 35.0, 0.8, 1, '由AI规则引擎自动生成', 1, 0);
