package com.mufeng.mufengGenerator.service.impl;

import com.mufeng.mufengCommon.entity.RespResult;
import com.mufeng.mufengGenerator.domain.dto.PageRequest;
import com.mufeng.mufengGenerator.domain.dto.PageResult;
import com.mufeng.mufengGenerator.domain.entity.ColumnInfo;
import com.mufeng.mufengGenerator.domain.entity.DatabaseConfig;
import com.mufeng.mufengGenerator.domain.entity.TableInfo;
import com.mufeng.mufengGenerator.service.DatabaseMetadataService;

public class MongoDBDatabaseMetadataServiceImpl implements DatabaseMetadataService {
    @Override
    public RespResult createConnection(DatabaseConfig databaseConfig) throws Exception {
        return null;
    }

    @Override
    public PageResult<TableInfo> getTables(PageRequest pageRequest) throws Exception {
        return null;
    }

    @Override
    public PageResult<ColumnInfo> getTableColumns(PageRequest pageRequest) {
        return null;
    }

    @Override
    public boolean supports(String dbType) {
        return false;
    }
}
