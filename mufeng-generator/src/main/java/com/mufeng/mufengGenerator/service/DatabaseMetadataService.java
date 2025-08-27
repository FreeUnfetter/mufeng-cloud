package com.mufeng.mufengGenerator.service;

import com.mufeng.mufengGenerator.domain.dto.PageRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DatabaseMetadataService {

    /**
     * 获取数据库表列表
     *
     * @param pageRequest
     * @return
     */
    Page<String> getTables(PageRequest pageRequest);


    /**
     * 获取表字段信息
     *
     * @param tableName
     * @return
     */
    List<Map<String, Object>> getTableColumns(String tableName);

    /**
     * 校验
     *
     * @param dbType
     * @return
     */
    boolean supports(String dbType);
}
