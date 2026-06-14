@echo off
REM 启动 Vite 前端开发服务器（端口 5173）
cd /d "%~dp0..\frontend"
call npm run dev
