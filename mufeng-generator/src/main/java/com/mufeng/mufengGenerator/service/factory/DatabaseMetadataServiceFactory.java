package com.mufeng.mufengGenerator.service.factory;

import com.mufeng.mufengGenerator.service.CodeGeneratorService;
import com.mufeng.mufengGenerator.service.DatabaseMetadataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseMetadataServiceFactory {

    private final List<DatabaseMetadataService> metadataServices;
    private final List<CodeGeneratorService> generatorServices;

    public DatabaseMetadataServiceFactory(List<DatabaseMetadataService> metadataServices,
                                          List<CodeGeneratorService> generatorServices) {
        this.metadataServices = metadataServices;
        this.generatorServices = generatorServices;
    }

    public DatabaseMetadataService getMetaService(String dbType) {
        return metadataServices.stream()
                .filter(service -> service.supports(dbType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("不支持的数据库类型: " + dbType));
    }

    public CodeGeneratorService getCodeService(String dbType) {
        return generatorServices.stream()
                .filter(service -> service.supports(dbType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("不支持的数据库类型: " + dbType));
    }
}
