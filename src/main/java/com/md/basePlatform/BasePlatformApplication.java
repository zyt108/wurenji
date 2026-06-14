package com.md.basePlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 无人机管理平台启动类
 * <p>
 * 这是 Spring Boot 应用的入口点，负责启动整个应用程序。
 * 该平台提供无人机数据的增删改查功能，并集成 AI 属性生成服务。
 * </p>
 */
@SpringBootApplication
public class BasePlatformApplication {

	/**
	 * 应用程序主入口方法
	 *
	 * @param args 命令行参数
	 */
	public static void main(String[] args) {
		SpringApplication.run(BasePlatformApplication.class, args);
	}

}
