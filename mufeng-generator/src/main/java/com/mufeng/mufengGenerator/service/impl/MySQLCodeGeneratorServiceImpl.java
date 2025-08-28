package com.mufeng.mufengGenerator.service.impl;

import com.mufeng.mufengGenerator.database.MySQLDruidConnectionManager;
import com.mufeng.mufengGenerator.domain.entity.ColumnInfo;
import com.mufeng.mufengGenerator.domain.entity.TableInfo;
import com.mufeng.mufengGenerator.service.CodeGeneratorService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class MySQLCodeGeneratorServiceImpl implements CodeGeneratorService {

    private final Configuration freemarkerConfig;

    public MySQLCodeGeneratorServiceImpl(
            @Qualifier("freemarkerConfiguration") Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public String generateCode(List<TableInfo> tableInfos, HttpServletResponse response) throws Exception {

        for (TableInfo tableInfo : tableInfos) {
            MySQLDruidConnectionManager connectionManager = MySQLDruidConnectionManager.getInstance();
            List<ColumnInfo> columns = connectionManager.getTableColumns(tableInfo.getTableName());
            tableInfo.setColumns(columns);
        }

        Path tempDir = Files.createTempDirectory("codegen");

        for (TableInfo table : tableInfos) {
            generateFiles(table, table.getTableName(), tempDir.toFile());
        }

        createZipFile(tempDir.toFile(), "/***/");

        return "";
    }

    private void generateFiles(TableInfo table, String packageName, File outputDir) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("table", table);
        data.put("package", packageName);

        // 创建包目录
        String packagePath = packageName.replace('.', '/');
        File entityDir = new File(outputDir, "entity/" + packagePath);
        File mapperDir = new File(outputDir, "mapper/" + packagePath);
        File serviceDir = new File(outputDir, "service/" + packagePath);
        File controllerDir = new File(outputDir, "controller/" + packagePath);

        entityDir.mkdirs();
        mapperDir.mkdirs();
        serviceDir.mkdirs();
        controllerDir.mkdirs();

        // 生成Entity
        generateFile("mysql/entity.ftl", data,
                new File(entityDir, table.getTableName() + ".java"));

        // 生成Mapper
        generateFile("mysql/mapper.ftl", data,
                new File(mapperDir, table.getTableName() + "Mapper.java"));

        // 生成Service
        generateFile("mysql/service.ftl", data,
                new File(serviceDir, table.getTableName() + "Service.java"));

        // 生成Controller
        generateFile("mysql/controller.ftl", data,
                new File(controllerDir, table.getTableName() + "Controller.java"));
    }

    private void generateFile(String templateName, Map<String, Object> data, File outputFile)
            throws Exception {
        Template template = freemarkerConfig.getTemplate(templateName);
        try (Writer out = new FileWriter(outputFile)) {
            template.process(data, out);
        }
    }

    private File createZipFile(File sourceDir, String outputPath) throws IOException {
        File zipFile = new File(outputPath);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
             var files = Files.walk(sourceDir.toPath())) {

            files.filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        try {
                            ZipEntry zipEntry = new ZipEntry(sourceDir.toPath().relativize(path).toString());
                            zos.putNextEntry(zipEntry);
                            Files.copy(path, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        return zipFile;
    }


    public boolean supports(String dbType) {
        return "mysql".equalsIgnoreCase(dbType);
    }

    @Override
    public Map<String, String> previewCode() {
        return null;
    }
}