package com.mufeng.mufengCommon.handler;

import com.mufeng.mufengCommon.entity.RespResult;
import com.mufeng.mufengCommon.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     *
     * @param ex 业务异常
     * @return 统一格式的错误响应
     */
    @ExceptionHandler(BusinessException.class)
    public RespResult handleBusinessException(BusinessException ex) {
        log.error(ex.getMessage());
        return RespResult.error(ex.getCode(), ex.getMessage(), ex.getData());
    }

    /**
     * 处理参数校验异常
     * 当使用 @Valid 注解校验请求参数失败时会抛出此异常
     *
     * @param ex 参数校验异常
     * @return 包含校验失败详情的错误响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespResult handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return RespResult.error(400, "参数校验失败", errors);
    }

    /**
     * 处理空指针异常
     *
     * @param ex 空指针异常
     * @return 统一格式的错误响应
     */
    @ExceptionHandler(NullPointerException.class)
    public RespResult handleNullPointerException(NullPointerException ex) {
        return RespResult.error(500, "服务器内部错误: 空指针异常", null);
    }

    /**
     * 处理所有未明确处理的异常
     *
     * @param ex 异常
     * @return 统一格式的错误响应
     */
    @ExceptionHandler(Exception.class)
    public RespResult handleAllExceptions(Exception ex) {
        return RespResult.error(500, "服务器内部错误", null);
    }

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
}
