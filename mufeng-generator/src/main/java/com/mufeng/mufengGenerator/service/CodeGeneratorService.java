package com.mufeng.mufengGenerator.service;

import com.mufeng.mufengGenerator.domain.dto.CodeGenerateDto;
import com.mufeng.mufengGenerator.domain.dto.PageRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CodeGeneratorService {
    /**
     * 生成代码
     *
     * @param request
     * @return
     */
    String generateCode(CodeGenerateDto request);



    /**
     * 预览生成的代码
     *
     * @param request
     * @return
     */
    Map<String, String> previewCode(CodeGenerateDto request);
}