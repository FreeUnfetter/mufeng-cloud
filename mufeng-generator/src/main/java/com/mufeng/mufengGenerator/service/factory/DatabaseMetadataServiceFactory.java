package com.mufeng.mufengGenerator.service.factory;

import com.mufeng.mufengGenerator.service.DatabaseMetadataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseMetadataServiceFactory {
    private final List<DatabaseMetadataService> services;

    public DatabaseMetadataServiceFactory(List<DatabaseMetadataService> services) {
        this.services = services;
    }

    public DatabaseMetadataService getService(String dbType) {
        return services.stream()
                .filter(service -> service.supports(dbType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("不支持的数据库类型: " + dbType));
    }
}
