package com.mufeng.mufengGenerator.service.impl;

import com.mufeng.mufengGenerator.domain.dto.CodeGenerateDto;
import com.mufeng.mufengGenerator.service.CodeGeneratorService;

import java.util.Map;

public class MongoDBCodeGeneratorServiceImpl implements CodeGeneratorService {

    @Override
    public String generateCode(CodeGenerateDto request) {
        return "";
    }

    @Override
    public Map<String, String> previewCode(CodeGenerateDto request) {
        return null;
    }

}
