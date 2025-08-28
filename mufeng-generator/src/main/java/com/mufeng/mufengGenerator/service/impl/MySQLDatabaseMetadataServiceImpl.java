package com.mufeng.mufengGenerator.service.impl;

import com.mufeng.mufengCommon.entity.RespResult;
import com.mufeng.mufengGenerator.database.MySQLDruidConnectionManager;
import com.mufeng.mufengGenerator.domain.dto.PageRequest;
import com.mufeng.mufengGenerator.domain.dto.PageResult;
import com.mufeng.mufengGenerator.domain.entity.ColumnInfo;
import com.mufeng.mufengGenerator.domain.entity.DatabaseConfig;
import com.mufeng.mufengGenerator.domain.entity.TableInfo;
import com.mufeng.mufengGenerator.service.DatabaseMetadataService;
import com.mufeng.mufengGenerator.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.Map;

@Service
public class MySQLDatabaseMetadataServiceImpl implements DatabaseMetadataService {

    private final MySQLDruidConnectionManager connectionManager;

    public MySQLDatabaseMetadataServiceImpl() {
        this.connectionManager = MySQLDruidConnectionManager.getInstance();
    }


    @Override
    public RespResult createConnection(DatabaseConfig databaseConfig) throws Exception {
        connectionManager.initializeConnection(databaseConfig.getDbHost(),
                databaseConfig.getDbPort(),
                databaseConfig.getDbName(),
                databaseConfig.getDbUserName(),
                databaseConfig.getDbPassword()
        );
        return connectionManager.testConnection()
                ? RespResult.success("数据库连接成功")
                : RespResult.error(501, "数据库连接失败");
    }

    @Override
    public boolean supports(String dbType) {
        return "mysql".equalsIgnoreCase(dbType);
    }

    @Override
    public PageResult<TableInfo> getTables(PageRequest pageRequest) throws Exception {
        List<TableInfo> tableInfo = connectionManager.getTableInfo();
        return PageUtils.paginate(tableInfo, pageRequest.getPageNum(), pageRequest.getPageSize());
    }

    @Override
    public PageResult<ColumnInfo> getTableColumns(PageRequest pageRequest) {
        List<ColumnInfo> columnInfos = connectionManager.getTableColumns(pageRequest.getDbName());
        return PageUtils.paginate(columnInfos, pageRequest.getPageNum(), pageRequest.getPageSize());
    }


}
