package com.mufeng.mufengGenerator.service;

import java.util.Map;

public interface CodeGeneratorService {
    /**
     * 生成代码
     *
     * @return
     */
    String generateCode();



    /**
     * 预览生成的代码
     *
     * @return
     */
    Map<String, String> previewCode();
}