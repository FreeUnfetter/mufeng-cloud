package com.mufeng.mufengGenerator.service;

import com.mufeng.mufengGenerator.domain.entity.DatabaseConfig;

import java.util.List;

public interface DatabaseConfigService {

    List<DatabaseConfig> findAll();

    DatabaseConfig findById(String id);

    DatabaseConfig save(DatabaseConfig databaseConfig);

    DatabaseConfig update(DatabaseConfig databaseConfig);

    void delete(String id);
}
