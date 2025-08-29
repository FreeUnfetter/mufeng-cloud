package com.mufeng.mufengCommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public static <T> RespResult<T> success(T data) {
        return new RespResult<>(200, "成功", data);
    }

    public static <T> RespResult<T> success(String message) {
        return new RespResult<>(200, message, null);
    }

    public static <T> RespResult<T> error(Integer code, String message) {
        return new RespResult<>(code, message, null);
    }

    public static <T> RespResult<T> error(Integer code, String message, T data) {
        return new RespResult<>(code, message, data);
    }
}
