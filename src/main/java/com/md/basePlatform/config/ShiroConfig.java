package com.md.basePlatform.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Apache Shiro 安全配置类
 * <p>
 * 配置 Shiro 安全框架，包括 Realm、SecurityManager 和过滤器链。
 * </p>
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置 IniRealm
     * <p>
     * 从 shiro-users.ini 文件加载用户认证信息。
     * </p>
     *
     * @return IniRealm 实例
     */
    @Bean
    public IniRealm iniRealm() {
        return new IniRealm("classpath:shiro-users.ini");
    }

    /**
     * 配置 SecurityManager
     * <p>
     * 创建 Web 安全管理器，并设置 Realm。
     * </p>
     *
     * @param iniRealm IniRealm 实例
     * @return SecurityManager 实例
     */
    @Bean
    public SecurityManager securityManager(IniRealm iniRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(iniRealm);
        return manager;
    }

    /**
     * 配置 ShiroFilterFactoryBean
     * <p>
     * 设置登录页面和过滤器链规则。
     * </p>
     *
     * @param securityManager SecurityManager 实例
     * @return ShiroFilterFactoryBean 实例
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        // 设置登录页面
        bean.setLoginUrl("/login");

        // 配置过滤器链
        Map<String, String> chain = new LinkedHashMap<String, String>();
        chain.put("/login.html", "anon"); // 登录页面匿名访问
        chain.put("/index.html", "anon"); // 首页匿名访问
        chain.put("/add.html", "anon"); // 新增页面匿名访问
        chain.put("/edit.html", "anon"); // 编辑页面匿名访问
        chain.put("/login", "anon"); // 登录接口匿名访问
        chain.put("/api/login", "anon"); // API登录接口匿名访问
        chain.put("/api/logout", "anon"); // API登出接口匿名访问
        chain.put("/api/current-user", "anon"); // API当前用户接口匿名访问
        chain.put("/logout", "anon"); // 登出匿名访问
        chain.put("/swagger-ui.html", "anon"); // Swagger UI 匿名访问
        chain.put("/webjars/**", "anon"); // Webjars 资源匿名访问
        chain.put("/v3/api-docs/**", "anon"); // API文档匿名访问
        chain.put("/drones/**", "anon"); // 无人机API匿名访问（临时）
        chain.put("/**", "authc"); // 其他所有请求需要认证

        bean.setFilterChainDefinitionMap(chain);
        return bean;
    }
}
