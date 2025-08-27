package com.mufeng.mufengCommon.advice;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalBinderAdvice {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 注册一个自定义的日期格式化器
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(LocalDateTime.class, new CustomDateEditor(dateFormat, false));
    }
}
