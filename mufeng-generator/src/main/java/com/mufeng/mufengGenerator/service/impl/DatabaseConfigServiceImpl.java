package com.mufeng.mufengGenerator.service.impl;

import com.mufeng.mufengGenerator.domain.entity.DatabaseConfig;
import com.mufeng.mufengGenerator.mapper.DatabaseConfigRepository;
import com.mufeng.mufengGenerator.service.DatabaseConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseConfigServiceImpl implements DatabaseConfigService {

    private final DatabaseConfigRepository databaseConfigRepository;


    @Override
    public List<DatabaseConfig> getDatabaseConfigList() {
        return databaseConfigRepository.findAll();
    }
}
