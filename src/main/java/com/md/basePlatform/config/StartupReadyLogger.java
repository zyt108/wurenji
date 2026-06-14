package com.md.basePlatform.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 启动就绪日志记录器
 * <p>
 * 在 Web 容器就绪后输出固定提示，便于在终端中识别「可以访问浏览器」的时机。
 * </p>
 */
@Component
public class StartupReadyLogger {

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(StartupReadyLogger.class);

    /**
     * 应用就绪后打印访问地址
     * <p>
     * 端口取自配置属性 server.port，默认为8080。
     * </p>
     *
     * @param event 应用就绪事件
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onReady(ApplicationReadyEvent event) {
        // 获取环境配置
        Environment env = event.getApplicationContext().getEnvironment();
        // 获取端口（默认8080）
        String port = env.getProperty("server.port", "8080");
        // 获取上下文路径（默认为空）
        String contextPath = env.getProperty("server.servlet.context-path", "");
        if (contextPath == null || contextPath.isEmpty()) {
            contextPath = "";
        }
        // 输出访问地址提示
        log.warn("");
        log.warn("======== basePlatform 已就绪，浏览器访问: http://127.0.0.1:{}{}/login ========", port, contextPath);
        log.warn("");
    }
}
