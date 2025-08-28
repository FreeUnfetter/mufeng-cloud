package com.mufeng.mufengGenerator.service.impl;

import com.mufeng.mufengGenerator.domain.entity.TableInfo;
import com.mufeng.mufengGenerator.service.CodeGeneratorService;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public class MongoDBCodeGeneratorServiceImpl implements CodeGeneratorService {

    @Override
    public String generateCode(List<TableInfo> tableInfos, HttpServletResponse response) {
        return "";
    }

    @Override
    public Map<String, String> previewCode() {
        return null;
    }

    @Override
    public boolean supports(String dbType) {
        return false;
    }

}
