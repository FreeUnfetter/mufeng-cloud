package com.mufeng.mufengGenerator.controller;

import com.mufeng.mufengCommon.entity.RespResult;
import com.mufeng.mufengGenerator.domain.dto.PageRequest;
import com.mufeng.mufengGenerator.domain.dto.PageResult;
import com.mufeng.mufengGenerator.domain.entity.ColumnInfo;
import com.mufeng.mufengGenerator.domain.entity.DatabaseConfig;
import com.mufeng.mufengGenerator.domain.entity.TableInfo;
import com.mufeng.mufengGenerator.service.CodeGeneratorService;
import com.mufeng.mufengGenerator.service.DatabaseMetadataService;
import com.mufeng.mufengGenerator.service.factory.DatabaseMetadataServiceFactory;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/generator")
public class CodeGeneratorController {

    private final DatabaseMetadataServiceFactory serviceFactory;

    /**
     * 数据库连接测试
     *
     * @param databaseConfig 数据库连接信息
     * @return
     * @throws Exception
     */
    @PostMapping("/connection")
    public RespResult createConnection(@Valid @RequestBody DatabaseConfig databaseConfig) throws Exception {
        DatabaseMetadataService service = serviceFactory.getMetaService(databaseConfig.getDataType());
        return service.createConnection(databaseConfig);
    }

    /**
     * 数据库表信息列表
     *
     */
    @PostMapping("/tables")
    public PageResult<TableInfo> getTables(@Valid @RequestBody PageRequest pageRequest) throws Exception {
        DatabaseMetadataService service = serviceFactory.getMetaService(pageRequest.getDataType());
        return service.getTables(pageRequest);
    }

    /**
     * 数据库表字段信息列表
     *
     */
    @PostMapping("/columns")
    public PageResult<ColumnInfo> getTableColumns(@Valid @RequestBody PageRequest pageRequest){
        DatabaseMetadataService service = serviceFactory.getMetaService(pageRequest.getDataType());
        return service.getTableColumns(pageRequest);
    }


    /**
     * 生成代码并打包
     *
     * @param tableInfos
     * @param response
     * @throws SQLException
     * @throws IOException
     */
    @PostMapping("/generate")
    public void generateCode(@RequestBody List<TableInfo> tableInfos, HttpServletResponse response) throws Exception {
        CodeGeneratorService service = serviceFactory.getCodeService(tableInfos.get(0).getDataType());
        service.generateCode(tableInfos, response);
    }

    /**
     * 代码预览
     *
     * @return
     */
    @PostMapping("/preview")
    public Map<String, String> previewCode() {
        //TODO
        return null;
    }
}
