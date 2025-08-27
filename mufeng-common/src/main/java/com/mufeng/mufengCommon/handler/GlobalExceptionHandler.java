package com.mufeng.mufengCommon.handler;

import com.mufeng.mufengCommon.entity.RespResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 捕获特定异常
//    @ExceptionHandler(UserNotFoundException.class)
//    public RespResult<Void> handleUserNotFoundException(UserNotFoundException ex) {
//        // 记录日志等操作...
//        return RespResult.error(404, "用户不存在: " + ex.getMessage());
//    }

    // 捕获特定异常
    @ExceptionHandler(SQLException.class)
    public RespResult<Void> handleUserNotFoundException(SQLException ex) {
        // 记录日志等操作...
        return RespResult.error(501, "数据库错误" + ex.getMessage());
    }

    // 捕获更通用的异常
    @ExceptionHandler(Exception.class)
    public RespResult<Void> handleAllException(Exception ex) {
        // 记录日志等操作...
        return RespResult.error(500, "服务器内部错误: " + ex.getMessage());
    }

    // 捕获 Spring MVC 自身的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespResult<Void> handleValidationException(MethodArgumentNotValidException ex) {
        // 处理参数校验失败的错误
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return RespResult.error(400, "参数校验失败: " + errorMsg);
    }
}
