package com.mufeng.mufengCommon.advice;

import com.alibaba.fastjson2.JSON;
import com.mufeng.mufengCommon.annotation.SkipFeign;
import com.mufeng.mufengCommon.entity.RespResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 判断哪些请求需要被拦截并进行封装
     *
     * @return true: 执行 beforeBodyWrite; false: 不执行
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 1. 检查方法或类上是否有跳过封装的注解,例如OpenFeign调用、Swagger文档接口等
        if (returnType.hasMethodAnnotation(SkipFeign.class) ||
                returnType.getContainingClass().isAnnotationPresent(SkipFeign.class)) {
            return false;
        }

        // 2. 检查返回值类型本身已经是 RespResult，避免重复封装
        if (returnType.getParameterType().equals(RespResult.class)) {
            return false;
        }

        // 3. 通过请求路径判断（需要获取HttpServletRequest）
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra != null) {
            HttpServletRequest request = sra.getRequest();
            String path = request.getRequestURI();
            if (path.contains("/feign/") || path.contains("/actuator/")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对响应体进行实际封装操作
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        // 如果已经是RespResult类型，直接返回
        if (body instanceof RespResult) {
            return body;
        }

        // 字符串需要特殊处理
        if (body instanceof String) {
            return JSON.toJSONString(RespResult.success(body));
        }

        return RespResult.success(body);
    }
}
