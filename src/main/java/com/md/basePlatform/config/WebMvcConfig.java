package com.md.basePlatform.config;

import com.md.basePlatform.interceptor.RequestLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置类
 * <p>
 * 配置拦截器、CORS 跨域等 MVC 相关设置。
 * </p>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 请求日志拦截器
     */
    private final RequestLogInterceptor requestLogInterceptor;

    /**
     * 构造函数，依赖注入
     *
     * @param requestLogInterceptor 请求日志拦截器
     */
    public WebMvcConfig(RequestLogInterceptor requestLogInterceptor) {
        this.requestLogInterceptor = requestLogInterceptor;
    }

    /**
     * 注册拦截器
     * <p>
     * 将请求日志拦截器注册到 MVC 框架，排除静态资源和登录页面。
     * </p>
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/images/**", "/webjars/**",
                        "/v3/api-docs/**", "/swagger-ui/**"); // 排除静态资源、登录页
    }

    /**
     * 配置 CORS 跨域支持
     * <p>
     * 允许前端 Vue 项目跨域访问 API。
     * </p>
     *
     * @param registry CORS 注册表
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
