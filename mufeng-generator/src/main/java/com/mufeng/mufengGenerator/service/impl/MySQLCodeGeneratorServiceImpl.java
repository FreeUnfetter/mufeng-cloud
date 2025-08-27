package com.mufeng.mufengGenerator.service.impl;

import com.mufeng.mufengGenerator.service.CodeGeneratorService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service("MySQLCodeGeneratorService")
public class MySQLCodeGeneratorServiceImpl implements CodeGeneratorService {

    private final Configuration freemarkerConfig;

    public MySQLCodeGeneratorServiceImpl(
            @Qualifier("freemarkerConfiguration") Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public String generateCode() {
        // 获取表字段信息
//        List<Map<String, Object>> columns = getTableColumns(request.getDbType(), request.getTableName());
//
//        // 准备数据模型
//        Map<String, Object> dataModel = prepareDataModel(request, columns);
//
//        // 生成代码文件
//        String zipFilePath = generateZipFile(request, dataModel);

        return null;
    }

    @Override
    public Map<String, String> previewCode() {
        return null;
    }


//    @Override
//    public Map<String, String> previewCode() {
//        // 获取表字段信息
////        List<Map<String, Object>> columns = getTableColumns(request.getDbType(), request.getTableName());
//
//        List<Map<String, Object>> columns = null;
//        // 准备数据模型
//        Map<String, Object> dataModel = prepareDataModel(request, columns);
//
//        // 生成预览代码
//        Map<String, String> previews = new HashMap<>();
//
//        try {
//            if (request.getGenerateEntity()) {
//                Template template = freemarkerConfig.getTemplate("mysql/entity.ftl");
//                StringWriter writer = new StringWriter();
//                template.process(dataModel, writer);
//                previews.put("Entity", writer.toString());
//            }
//
//            if (request.getGenerateMapper()) {
//                Template template = freemarkerConfig.getTemplate("mysql/mapper.ftl");
//                StringWriter writer = new StringWriter();
//                template.process(dataModel, writer);
//                previews.put("Mapper", writer.toString());
//            }
//
//            if (request.getGenerateService()) {
//                Template template = freemarkerConfig.getTemplate("mysql/service.ftl");
//                StringWriter writer = new StringWriter();
//                template.process(dataModel, writer);
//                previews.put("Service", writer.toString());
//
//                template = freemarkerConfig.getTemplate("mysql/serviceImpl.ftl");
//                writer = new StringWriter();
//                template.process(dataModel, writer);
//                previews.put("ServiceImpl", writer.toString());
//            }
//
//            if (request.getGenerateController()) {
//                Template template = freemarkerConfig.getTemplate("mysql/controller.ftl");
//                StringWriter writer = new StringWriter();
//                template.process(dataModel, writer);
//                previews.put("Controller", writer.toString());
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("代码预览失败", e);
//        }
//
//        return previews;
//    }
//
//    private Map<String, Object> prepareDataModel(CodeGenerateDto request, List<Map<String, Object>> columns) {
//        Map<String, Object> dataModel = new HashMap<>();
//
//        // 基础信息
//        dataModel.put("packageName", request.getPackageName());
//        dataModel.put("author", request.getAuthor() != null ? request.getAuthor() : "Code Generator");
//        dataModel.put("version", request.getVersion() != null ? request.getVersion() : "1.0");
//        dataModel.put("date", new Date());
//
//        // 表信息
//        String tableName = request.getTableName();
//        String className = convertToCamelCase(tableName, true);
//        dataModel.put("tableName", tableName);
//        dataModel.put("className", className);
//        dataModel.put("variableName", convertToCamelCase(tableName, false));
//
//        // 字段信息处理
//        List<Map<String, Object>> processedColumns = new ArrayList<>();
//        for (Map<String, Object> column : columns) {
//            Map<String, Object> processedColumn = new HashMap<>(column);
//
//            // 转换字段名为Java属性名
//            String columnName = (String) column.get("COLUMN_NAME");
//            String fieldName = convertToCamelCase(columnName, false);
//            processedColumn.put("fieldName", fieldName);
//
//            // 转换数据类型
//            String dataType = (String) column.get("DATA_TYPE");
//            String javaType = convertToJavaType(dataType);
//            processedColumn.put("javaType", javaType);
//
//            processedColumns.add(processedColumn);
//        }
//        dataModel.put("columns", processedColumns);
//
//        return dataModel;
//    }
//
//    private String generateZipFile(CodeGenerateDto request, Map<String, Object> dataModel) {
//        String tempDir = System.getProperty("java.io.tmpdir");
//        String zipFileName = tempDir + "/code-" + System.currentTimeMillis() + ".zip";
//
//        try (FileOutputStream fos = new FileOutputStream(zipFileName);
//             ZipOutputStream zos = new ZipOutputStream(fos)) {
//
//            String basePackagePath = request.getPackageName().replace('.', '/');
//            String className = (String) dataModel.get("className");
//
//            if (request.getGenerateEntity()) {
//                Template template = freemarkerConfig.getTemplate("mysql/entity.ftl");
//                StringWriter writer = new StringWriter();
//                template.process(dataModel, writer);
//
//                zos.putNextEntry(new ZipEntry(basePackagePath + "/entity/" + className + ".java"));
//                zos.write(writer.toString().getBytes());
//                zos.closeEntry();
//            }
//
//            if (request.getGenerateMapper()) {
//                Template template = freemarkerConfig.getTemplate("mysql/mapper.ftl");
//                StringWriter writer = new StringWriter();
//                template.process(dataModel, writer);
//
//                zos.putNextEntry(new ZipEntry(basePackagePath + "/mapper/" + className + "Mapper.java"));
//                zos.write(writer.toString().getBytes());
//                zos.closeEntry();
//            }
//
//            if (request.getGenerateService()) {
//                Template template = freemarkerConfig.getTemplate("mysql/service.ftl");
//                StringWriter writer = new StringWriter();
//                template.process(dataModel, writer);
//
//                zos.putNextEntry(new ZipEntry(basePackagePath + "/service/" + className + "Service.java"));
//                zos.write(writer.toString().getBytes());
//                zos.closeEntry();
//
//                template = freemarkerConfig.getTemplate("mysql/serviceImpl.ftl");
//                writer = new StringWriter();
//                template.process(dataModel, writer);
//
//                zos.putNextEntry(new ZipEntry(basePackagePath + "/service/impl/" + className + "ServiceImpl.java"));
//                zos.write(writer.toString().getBytes());
//                zos.closeEntry();
//            }
//
//            if (request.getGenerateController()) {
//                Template template = freemarkerConfig.getTemplate("mysql/controller.ftl");
//                StringWriter writer = new StringWriter();
//                template.process(dataModel, writer);
//
//                zos.putNextEntry(new ZipEntry(basePackagePath + "/controller/" + className + "Controller.java"));
//                zos.write(writer.toString().getBytes());
//                zos.closeEntry();
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("代码生成失败", e);
//        }
//
//        return zipFileName;
//    }

    private String convertToCamelCase(String name, boolean firstLetterUpperCase) {
        StringBuilder result = new StringBuilder();
        boolean nextUpperCase = firstLetterUpperCase;

        for (char c : name.toCharArray()) {
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    result.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }

        return result.toString();
    }

    private String convertToJavaType(String dbType) {
        switch (dbType.toLowerCase()) {
            case "varchar":
            case "char":
            case "text":
            case "longtext":
            case "mediumtext":
                return "String";
            case "int":
            case "integer":
            case "tinyint":
            case "smallint":
            case "mediumint":
                return "Integer";
            case "bigint":
                return "Long";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "decimal":
                return "java.math.BigDecimal";
            case "boolean":
            case "bit":
                return "Boolean";
            case "date":
            case "time":
            case "datetime":
            case "timestamp":
                return "java.util.Date";
            default:
                return "Object";
        }
    }
}