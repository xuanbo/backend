package tk.fishfish.simple.domain;

import java.io.Serializable;

/**
 * 统一 API 接口返回
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public class ApiResult<T> implements Serializable {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 描述
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    private ApiResult() {
    }

    private ApiResult(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功
     *
     * @param <T>  类型
     * @return ApiResult
     */
    public static <T> ApiResult<T> ok() {
        return ok(null, null);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  类型
     * @return ApiResult
     */
    public static <T> ApiResult<T> ok(T data) {
        return ok(null, data);
    }

    /**
     * 成功
     *
     * @param message 信息
     * @param data    数据
     * @param <T>     类型
     * @return ApiResult
     */
    public static <T> ApiResult<T> ok(String message, T data) {
        return new ApiResult<>(true, message, data);
    }

    /**
     * 失败
     *
     * @param message 信息
     * @param data    数据
     * @param <T>     类型
     * @return ApiResult
     */
    public static <T> ApiResult<T> fail(String message, T data) {
        return new ApiResult<>(false, message, data);
    }

    /**
     * 失败
     *
     * @param message 信息
     * @param <T>     类型
     * @return ApiResult
     */
    public static <T> ApiResult<T> fail(String message) {
        return fail(message, null);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
