package com.mufeng.mufengGenerator.controller;

import com.mufeng.mufengCommon.entity.RespResult;
import com.mufeng.mufengGenerator.domain.dto.PageRequest;
import com.mufeng.mufengGenerator.domain.entity.DatabaseConfig;
import com.mufeng.mufengGenerator.service.DatabaseMetadataService;
import com.mufeng.mufengGenerator.service.factory.DatabaseMetadataServiceFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/generator")
public class CodeGeneratorController {

    private final DatabaseMetadataServiceFactory serviceFactory;

    @GetMapping("/connection")
    public RespResult createConnection(@Valid @RequestBody DatabaseConfig databaseConfig) {
        DatabaseMetadataService service = serviceFactory.getService(databaseConfig.getDataType());
        return service.createConnection(databaseConfig);
    }
    /**
     * 数据库表信息列表
     * @param dbType 数据库类型
     * @param pageRequest 表名
     * @return
     */
    @GetMapping("/tables/{dbType}")
    public Page<String> getTables(
            @PathVariable String dbType ,
            @Valid PageRequest pageRequest) {
//        return codeGeneratorService.getTables(dbType, pageRequest);
        return null;
    }

    /**
     * 数据库表字段信息列表
     * @param dbType 数据库类型
     * @param tableName 表名
     * @return
     */
    @GetMapping("/columns/{dbType}/{tableName}")
    public List<Map<String, Object>> getTableColumns(
            @PathVariable String dbType,
            @PathVariable String tableName) {
//        return codeGeneratorService.getTableColumns(dbType, tableName);
        return null;
    }


//    /**
//     * 代码生成
//     *
//     * @param dto
//     * @return
//     * @throws IOException
//     */
//    @PostMapping("/generate")
//    public ResponseEntity<InputStreamResource> generateCode(@Valid @RequestBody CodeGenerateDto dto) throws IOException {
//        String zipFilePath = codeGeneratorService.generateCode(dto);
//
//        File zipFile = new File(zipFilePath);
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated-code.zip")
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .contentLength(zipFile.length())
//                .body(resource);
//    }

//    /**
//     * 代码预览
//     *
//     * @param dto
//     * @return
//     */
//    @PostMapping("/preview")
//    public Map<String, String> previewCode(@Valid @RequestBody CodeGenerateDto dto) {
//        return codeGeneratorService.previewCode(dto);
//    }
}
