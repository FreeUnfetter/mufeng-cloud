package com.mufeng.mufengGenerator.service.impl;

import com.mufeng.mufengGenerator.domain.entity.TableInfo;
import com.mufeng.mufengGenerator.service.CodeGeneratorService;
import com.mufeng.mufengGenerator.util.MysqlUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MySQLCodeGeneratorServiceImpl implements CodeGeneratorService {

    private final MysqlUtils mysqlUtils;

    @Override
    public void generateCode(List<TableInfo> tableInfos, HttpServletResponse response) throws Exception {
        for (TableInfo tableInfo : tableInfos) {
            //获取写入内容
            Map<String, Object> context = mysqlUtils.getContext(tableInfo);

            try {
                // 生成各类文件
                mysqlUtils.generateFile("controller.java.vm", "controller", context.get("tableNameHigher")
                        + "Controller.java", context);
                mysqlUtils.generateFile("service.java.vm", "service", "I" + context.get("tableNameHigher")
                        + "Service.java", context);
                mysqlUtils.generateFile("serviceImpl.java.vm", "service/impl", context.get("tableNameHigher")
                        + "ServiceImpl.java", context);
                mysqlUtils.generateFile("entity.java.vm", "entity", context.get("tableNameHigher")
                        + ".java", context);
                mysqlUtils.generateFile("repository.java.vm", "repository", context.get("tableNameHigher")
                        + "Mapper.java", context);
            } catch (Exception e) {
                throw new RuntimeException("代码生成失败", e);
            }
        }

        // 打包下载
        String fileName = tableInfos.size() > 1 ? "batch-package" : tableInfos.get(0).getTableName();
        mysqlUtils.downLoadFile(response, fileName);
    }

    public boolean supports(String dbType) {
        return "mysql".equalsIgnoreCase(dbType);
    }

    @Override
    public Map<String, String> previewCode() {
        return null;
    }
}