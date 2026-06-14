@echo off
REM 一键启动前后端（各开一个命令行窗口）
cd /d "%~dp0.."

echo [1/2] 启动后端 Spring Boot (端口 8081)...
start "basePlatform-Backend" cmd /k "cd /d %~dp0.. && call mvnw.cmd spring-boot:run"

echo 等待后端启动...
timeout /t 8 /nobreak >nul

echo [2/2] 启动前端 Vite (端口 5173)...
start "basePlatform-Frontend" cmd /k "cd /d %~dp0..\frontend && npm run dev"

echo.
echo ========================================
echo  请在浏览器打开: http://localhost:5173
echo  默认账号: admin / admin123
echo ========================================
echo.
pause
