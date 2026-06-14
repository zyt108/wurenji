# 无人机管理系统

基于 Spring Boot 的无人机管理系统，提供无人机的增删改查、搜索、统计等功能。

## 快速开始

### 环境要求

- JDK 8+
- Maven 3.6+

### 运行项目

```bash
# 克隆项目
git clone <repository-url>
cd basePlatform

# 运行项目
mvn spring-boot:run

# 访问系统
浏览器打开: http://localhost:8081/login.html
默认账号: admin / admin
```

## 项目结构

```
basePlatform/
├── src/main/java/com/md/basePlatform/
│   ├── controller/          # 控制器层
│   ├── service/             # 业务逻辑层
│   ├── repository/          # 数据访问层
│   ├── domain/              # 领域模型
│   ├── config/              # 配置类
│   ├── common/              # 通用类
│   ├── exception/           # 异常处理
│   └── interceptor/         # 拦截器
├── src/main/resources/
│   ├── mapper/              # MyBatis映射文件
│   ├── static/              # 静态资源
│   └── application.yml      # 配置文件
├── harness-collab/          # 协作文档
│   ├── 01-product-specs/    # 产品需求
│   ├── 02-design-docs/      # 设计文档
│   ├── 03-implementation/   # 实现文档
│   ├── 04-api-docs/         # API文档
│   └── func.md              # 功能文档
├── .github/workflows/       # CI/CD工作流
├── config/                  # 配置文件
│   ├── checkstyle/          # Checkstyle配置
│   └── spotbugs/            # SpotBugs配置
└── .trae/                   # AI协作协议
```

## 功能特性

- ✅ 用户认证授权
- ✅ 无人机增删改查
- ✅ 分页查询和搜索
- ✅ 统计信息展示
- ✅ 操作日志记录
- ✅ API文档集成

## 测试

### 运行测试

```bash
# 运行所有测试
mvn test

# 运行测试并生成覆盖率报告（新代码）
mvn clean verify -Pharness-new

# 运行测试并生成覆盖率报告（遗留代码）
mvn clean verify -Pharness-legacy
```

### 覆盖率要求

- **harness-new**: 行覆盖率 >= 80%
- **harness-legacy**: 仅生成报告，不设阈值

### 静态代码检查

```bash
# Checkstyle检查
mvn checkstyle:check

# SpotBugs检查
mvn spotbugs:check
```

## API文档

启动项目后访问: http://localhost:8081/swagger-ui.html

详细API文档请查看: [harness-collab/04-api-docs/api.md](harness-collab/04-api-docs/api.md)

## 开发规范

### 代码分层

- **Controller**: 处理HTTP请求，参数校验
- **Service**: 业务逻辑处理
- **Repository**: 数据访问
- **Domain**: 领域模型

### 命名规范

- 类名: 大驼峰（PascalCase）
- 方法名: 小驼峰（camelCase）
- 常量: 全大写下划线分隔（UPPER_SNAKE_CASE）

### 注释规范

- 所有公共方法必须添加Javadoc注释
- 包含 @param、@return、@throws 标签

### 依赖注入

- 统一使用构造器注入
- 禁止使用 @Autowired 字段注入

## CI/CD

项目使用 GitHub Actions 进行持续集成：

- **触发条件**: Push/PR 到 main/master/develop 分支
- **测试环境**: JDK 17/21
- **验证步骤**:
  1. 代码编译
  2. 单元测试
  3. 覆盖率检查
  4. 静态代码分析
  5. 安全扫描

详细配置请查看: [.github/workflows/ci-verify.yml](.github/workflows/ci-verify.yml)

## 配置说明

### Maven Profiles

- **harness-new**: 新代码验证，包含覆盖率检查
- **harness-legacy**: 遗留代码验证，仅生成报告
- **security-scan**: 安全扫描

### Checkstyle配置

配置文件: `config/checkstyle/checkstyle.xml`

### SpotBugs配置

配置文件: `config/spotbugs/exclude.xml`

## 文档同步

根据 [AI协作协议](.trae/rules/ai-collaboration-protocol.md)：

- 业务代码变更前需确认需求文档存在
- 架构代码变更前需确认设计文档存在
- API变更需同步更新API文档
- 交付时需标记文档同步状态

## 常见问题

### 端口被占用

```bash
# Windows
netstat -ano | findstr :8081
taskkill /F /PID <PID>

# Linux/Mac
lsof -i :8081
kill -9 <PID>
```

### 数据库文件位置

数据库文件: `data/baseplatform.db`

## 许可证

[MIT License](LICENSE)
## 联系方式

- 项目地址: [GitHub Repository]
- 问题反馈: [Issues]
