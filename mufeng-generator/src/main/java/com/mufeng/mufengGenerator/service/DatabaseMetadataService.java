package com.mufeng.mufengGenerator.service;

import com.mufeng.mufengCommon.entity.RespResult;
import com.mufeng.mufengGenerator.domain.dto.PageRequest;
import com.mufeng.mufengGenerator.domain.dto.PageResult;
import com.mufeng.mufengGenerator.domain.entity.ColumnInfo;
import com.mufeng.mufengGenerator.domain.entity.DatabaseConfig;
import com.mufeng.mufengGenerator.domain.entity.TableInfo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DatabaseMetadataService {

    /**
     * 测试连接
     *
     * @param databaseConfig
     * @return
     */
    RespResult createConnection(DatabaseConfig databaseConfig) throws Exception;

    /**
     * 获取数据库表列表
     *
     * @param pageRequest
     * @return
     */
    PageResult<TableInfo> getTables(PageRequest pageRequest) throws Exception;

    /**
     * 获取表字段信息
     *
     * @param pageRequest
     * @return
     */
    PageResult<ColumnInfo> getTableColumns(PageRequest pageRequest);

    /**
     * 校验
     *
     * @param dbType
     * @return
     */
    boolean supports(String dbType);
}
