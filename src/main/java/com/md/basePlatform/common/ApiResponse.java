package com.md.basePlatform.common;

/**
 * 统一 API 响应封装类
 * <p>
 * 用于封装 REST API 的返回结果，统一响应格式。
 * </p>
 *
 * @param <T> 数据类型
 */
public class ApiResponse<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 创建成功响应
     *
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功响应对象
     */
    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.success = true;
        response.data = data;
        response.message = "OK";
        return response;
    }

    /**
     * 创建失败响应
     *
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 失败响应对象
     */
    public static <T> ApiResponse<T> fail(String message) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.success = false;
        response.message = message;
        return response;
    }

    /**
     * 获取是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() { return success; }

    /**
     * 设置是否成功
     *
     * @param success 是否成功
     */
    public void setSuccess(boolean success) { this.success = success; }

    /**
     * 获取响应消息
     *
     * @return 响应消息
     */
    public String getMessage() { return message; }

    /**
     * 设置响应消息
     *
     * @param message 响应消息
     */
    public void setMessage(String message) { this.message = message; }

    /**
     * 获取响应数据
     *
     * @return 响应数据
     */
    public T getData() { return data; }

    /**
     * 设置响应数据
     *
     * @param data 响应数据
     */
    public void setData(T data) { this.data = data; }
}
