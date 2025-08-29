package com.mufeng.mufengCommon.exception;

import com.mufeng.mufengConstants.enums.ErrorCodeEnum;
import lombok.Getter;

/**
 * 自定义业务异常类
 * 继承 RuntimeException 便于在业务层直接抛出
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;
    private final Object data;

    /**
     * 构造函数 - 仅包含消息
     * @param message 错误信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500; // 默认500错误码
        this.data = null;
    }

    /**
     * 构造函数 - 包含消息和错误码
     * @param message 错误信息
     * @param code 错误码
     */
    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
        this.data = null;
    }

    /**
     * 构造函数 - 包含消息、错误码和数据
     * @param message 错误信息
     * @param code 错误码
     * @param data 错误数据
     */
    public BusinessException(String message, Integer code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    /**
     * 构造函数 - 使用预定义的错误枚举
     * @param errorCode 错误枚举类型，推荐使用
     */
    public BusinessException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.data = null;
    }

    /**
     * 构造函数 - 使用预定义的错误枚举并附加数据
     * @param errorCode 错误枚举
     * @param data 附加数据
     */
    public BusinessException(ErrorCodeEnum errorCode, Object data) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.data = data;
    }
}