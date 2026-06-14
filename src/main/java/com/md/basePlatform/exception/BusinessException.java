package com.md.basePlatform.exception;

/**
 * 业务异常类
 * <p>
 * 用于封装业务逻辑中的异常情况，如数据重复、不存在等。
 * </p>
 */
public class BusinessException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param message 异常消息
     */
    public BusinessException(String message) {
        super(message);
    }
}
