package com.md.basePlatform.exception;

import com.md.basePlatform.common.ApiResponse;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * <p>
 * 统一捕获并处理所有异常，返回友好的错误响应。
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param ex 业务异常
     * @return 错误响应
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusiness(BusinessException ex) {
        return ApiResponse.fail(ex.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param ex 参数校验异常
     * @return 错误响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidation(MethodArgumentNotValidException ex) {
        return ApiResponse.fail(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 处理未认证异常
     *
     * @param ex 未认证异常
     * @return 错误响应
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public ApiResponse<Void> handleUnauthenticated(UnauthenticatedException ex) {
        return ApiResponse.fail("未登录或登录已过期");
    }

    /**
     * 处理其他所有异常
     *
     * @param ex 异常
     * @return 错误响应
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception ex) {
        return ApiResponse.fail("系统错误: " + ex.getMessage());
    }
}
