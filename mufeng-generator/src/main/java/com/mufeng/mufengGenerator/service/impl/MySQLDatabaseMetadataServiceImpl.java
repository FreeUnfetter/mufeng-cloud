package com.mufeng.mufengGenerator.service.impl;

import com.mufeng.mufengGenerator.domain.dto.PageRequest;
import com.mufeng.mufengGenerator.domain.entity.DatabaseConfig;
import com.mufeng.mufengGenerator.service.DatabaseMetadataService;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MySQLDatabaseMetadataServiceImpl implements DatabaseMetadataService {

//    private final DatabaseConfig databaseConfig;
    private JdbcTemplate jdbcTemplate;

//    public MySQLDatabaseMetadataServiceImpl(DatabaseConfig databaseConfig) {
//        this.databaseConfig = databaseConfig;
//        initializeJdbcTemplate();
//    }

    private void initializeJdbcTemplate() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl(databaseConfig.getMysqlUrl());
//        dataSource.setUsername(databaseConfig.getMysqlUsername());
//        dataSource.setPassword(databaseConfig.getMysqlPassword());
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean supports(String dbType) {
        return "mysql".equalsIgnoreCase(dbType);
    }

    @Override
    public Page<String> getTables(PageRequest pageRequest) {

        return null;
    }

    @Override
    public List<Map<String, Object>> getTableColumns(String tableName) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl(databaseConfig.getMysqlUrl());
//        dataSource.setUsername(databaseConfig.getMysqlUsername());
//        dataSource.setPassword(databaseConfig.getMysqlPassword());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_KEY, COLUMN_COMMENT " +
                "FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? " +
                "ORDER BY ORDINAL_POSITION";

        return jdbcTemplate.queryForList(sql, tableName);
    }


}
