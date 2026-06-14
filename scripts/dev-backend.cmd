@echo off
REM 启动 Spring Boot 后端（端口 8081）
cd /d "%~dp0.."
call mvnw.cmd spring-boot:run
