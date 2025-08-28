package com.mufeng.mufengGenerator.service;

import com.mufeng.mufengGenerator.domain.entity.TableInfo;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public interface CodeGeneratorService {
    /**
     * 生成代码
     *
     * @return
     */
    String generateCode(List<TableInfo> tableInfos, HttpServletResponse response) throws Exception;


    /**
     * 校验
     *
     * @param dbType
     * @return
     */
    boolean supports(String dbType);

    /**
     * 预览生成的代码
     *
     * @return
     */
    Map<String, String> previewCode();
}