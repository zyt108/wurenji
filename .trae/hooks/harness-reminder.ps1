<#
.SYNOPSIS
Harness 代码变更提醒脚本

.DESCRIPTION
此 PowerShell 脚本作为 Git 钩子执行，用于检测代码变更类型并给出相应的提醒建议。
当检测到特定类型的文件变更时，会输出对应的操作建议，帮助开发者遵循项目规范。

功能特点：
- 检测 Controller 文件变更，提醒更新 API 文档
- 检测 Service/Domain 文件变更，提醒同步测试和覆盖率检查
- 检测 pom.xml 变更，提醒确认 Maven 配置
- 检测 Java 文件变更，提醒检查架构约束

.INPUTS
通过标准输入接收钩子负载（JSON 格式）

.OUTPUTS
JSON 格式的附加上下文信息

.NOTES
此脚本作为 Trae IDE 的钩子集成，确保代码变更符合项目规范
#>

# 设置错误处理策略为停止（遇到错误立即终止）
$ErrorActionPreference = 'Stop'

# 从标准输入读取完整的钩子负载
$inputText = [Console]::In.ReadToEnd()

# 检查输入是否为空
if ([string]::IsNullOrWhiteSpace($inputText)) {
    # 如果没有收到钩子负载，输出提示信息
    @{ additional_context = 'No hook payload received.' } | ConvertTo-Json -Compress
    exit 0
}

# 将输入文本转换为小写，便于不区分大小写的匹配
$rawLower = $inputText.ToLowerInvariant()

# 初始化提醒消息变量
$message = $null

# 检测 Controller 文件变更
# 当变更涉及 Controller 层时，需要更新 API 文档和验证注解
if ($rawLower.Contains('controller') -and $rawLower.Contains('.java')) {
    $message = 'Controller file changed. Update harness-collab/04-api-docs and harness-collab/func.md, and verify @Operation annotations.'

# 检测 Service 或 Domain 层文件变更
# 业务代码变更需要同步测试并运行覆盖率检查
} elseif (($rawLower.Contains('service') -or $rawLower.Contains('domain')) -and $rawLower.Contains('.java')) {
    $message = 'Business code changed. Sync related tests and run mvn clean verify -Pharness-new for coverage checks.'

# 检测 pom.xml 文件变更
# Maven 配置变更需要确认配置文件和静态分析设置
} elseif ($rawLower.Contains('pom.xml')) {
    $message = 'pom.xml changed. Confirm Harness Maven profiles (harness-new/harness-legacy) and static analysis config.'

# 检测其他 Java 文件变更
# Java 文件变更需要检查架构分层、依赖方向和构造器注入约束
} elseif ($rawLower.Contains('.java')) {
    $message = 'Java file changed. Check layer architecture, dependency direction, and constructor injection constraints.'
}

# 如果没有匹配到任何变更类型
if ($null -eq $message) {
    # 输出未触发提醒的信息
    @{ additional_context = 'No harness reminder triggered.' } | ConvertTo-Json -Compress
    exit 0
}

# 输出检测到的提醒消息
@{ additional_context = $message } | ConvertTo-Json -Compress
