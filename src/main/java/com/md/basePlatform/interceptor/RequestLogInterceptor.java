package com.md.basePlatform.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 请求日志拦截器
 * <p>
 * 在请求开始和结束时记录日志，包含请求方法、路径、查询参数、响应状态和耗时。
 * </p>
 */
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(RequestLogInterceptor.class);

    /**
     * 请求开始时间属性名
     */
    private static final String START_TIME = "start_time";

    /**
     * 请求处理前调用
     * <p>
     * 记录请求开始时间和请求信息。
     * </p>
     *
     * @param request HTTP请求
     * @param response HTTP响应
     * @param handler 处理器
     * @return true（继续处理）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 记录请求开始时间
        request.setAttribute(START_TIME, System.currentTimeMillis());
        // 记录请求信息
        log.info("request start {} {} query={}", 
                request.getMethod(), 
                request.getRequestURI(), 
                request.getQueryString());
        return true;
    }

    /**
     * 请求完成后调用
     * <p>
     * 记录请求结束时间、响应状态和耗时。
     * </p>
     *
     * @param request HTTP请求
     * @param response HTTP响应
     * @param handler 处理器
     * @param ex 异常（如果有）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 获取请求开始时间
        Long startTime = (Long) request.getAttribute(START_TIME);
        // 计算耗时
        long cost = startTime == null ? -1L : (System.currentTimeMillis() - startTime);
        // 记录请求结束信息
        log.info("request end {} {} status={} cost={}ms", 
                request.getMethod(), 
                request.getRequestURI(), 
                response.getStatus(), 
                cost);
    }
}
