package com.mufeng.mufengConstants.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    // 通用错误码
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    // 业务错误码 (可根据模块划分)
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_ALREADY_EXISTS(1002, "用户已存在"),
    INVALID_CREDENTIALS(1003, "无效的凭证"),
    PRODUCT_OUT_OF_STOCK(2001, "商品库存不足"),
    ORDER_CREATE_FAILED(3001, "订单创建失败");

    // 错误码
    private final Integer code;
    // 错误信息
    private final String message;

    /**
     * 枚举构造函数
     * @param code 错误码数字
     * @param message 错误信息
     */
    ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
