package com.mufeng.mufengGenerator.controller;

import com.mufeng.mufengGenerator.domain.entity.DatabaseConfig;
import com.mufeng.mufengGenerator.service.DatabaseConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/database")
public class DatabaseConfigController {

    private final DatabaseConfigService databaseConfigService;

    @GetMapping("/list")
    public List<DatabaseConfig> getDatabaseConfigList() {
        return databaseConfigService.getDatabaseConfigList();
    }
}
