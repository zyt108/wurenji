package com.md.basePlatform.controller;

import com.md.basePlatform.common.ApiResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证 REST API 控制器。
 * <p>
 * 处理用户登录、登出和当前用户信息查询，基于 Apache Shiro 完成会话管理。
 * </p>
 */
@RestController
public class AuthController {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果响应
     */
    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(@RequestParam String username, @RequestParam String password) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            Map<String, String> data = new HashMap<>();
            data.put("username", (String) subject.getPrincipal());
            return ApiResponse.ok(data);
        } catch (AuthenticationException ex) {
            // 认证失败时不向上抛出，返回友好错误信息
            return ApiResponse.fail("用户名或密码错误");
        }
    }

    /**
     * 用户登出
     *
     * @return 登出结果响应
     */
    @GetMapping("/logout")
    public ApiResponse<Void> logout() {
        Subject subject = SecurityUtils.getSubject();
        // 已登录时才执行 logout，避免重复调用
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return ApiResponse.ok(null);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息响应
     */
    @GetMapping("/current-user")
    public ApiResponse<Map<String, Object>> getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        Map<String, Object> data = new HashMap<>();
        data.put("authenticated", subject.isAuthenticated());
        if (subject.isAuthenticated()) {
            data.put("username", subject.getPrincipal());
        }
        return ApiResponse.ok(data);
    }
}
