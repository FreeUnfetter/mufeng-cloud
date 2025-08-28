package com.mufeng.mufengGenerator.controller;

import com.mufeng.mufengCommon.entity.RespResult;
import com.mufeng.mufengGenerator.domain.entity.DatabaseConfig;
import com.mufeng.mufengGenerator.service.DatabaseConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/database")
public class DatabaseConfigController {

    private final DatabaseConfigService databaseConfigService;

    @GetMapping
    public RespResult<List<DatabaseConfig>> findList() {
        return RespResult.success(databaseConfigService.findAll());
    }

    @GetMapping("{id}")
    public RespResult<DatabaseConfig> findById(@PathVariable String id) {
        return RespResult.success(databaseConfigService.findById(id));
    }

    @PostMapping
    public RespResult<DatabaseConfig> save(@RequestBody DatabaseConfig databaseConfig) {
        return RespResult.success(databaseConfigService.save(databaseConfig));
    }

    @PutMapping
    public RespResult<DatabaseConfig> update(@RequestBody DatabaseConfig databaseConfig) {
        return RespResult.success(databaseConfigService.update(databaseConfig));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        databaseConfigService.delete(id);
        RespResult.success("删除");
    }
}
