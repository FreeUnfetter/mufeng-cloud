package com.mufeng.mufengGenerator.util;

import com.mufeng.mufengGenerator.database.MySQLDruidConnectionManager;
import com.mufeng.mufengGenerator.domain.entity.ColumnInfo;
import com.mufeng.mufengGenerator.domain.entity.TableInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MysqlUtils {
    private final VelocityEngine velocityEngine;

    @Value("${codegen.output-dir}")
    private String outputDir;

    /**
     * 类型转换
     *
     * @param dataType
     * @return
     */
    public String getJavaType(String dataType) {
        if (dataType == null) return "Object";

        String type = dataType.toLowerCase();
        if (type.contains("int") || type.contains("integer") || type.contains("tinyint") || type.contains("smallint")) {
            return "Integer";
        } else if (type.contains("bigint")) {
            return "Long";
        } else if (type.contains("decimal") || type.contains("numeric") || type.contains("float") || type.contains("double")) {
            return "BigDecimal";
        } else if (type.contains("char") || type.contains("text") || type.contains("varchar") || type.contains("enum")) {
            return "String";
        } else if (type.contains("date") || type.contains("time") || type.contains("year") || type.contains("timestamp")) {
            return "LocalDateTime";
        } else if (type.contains("boolean") || type.contains("bit")) {
            return "Boolean";
        } else if (type.contains("blob") || type.contains("binary")) {
            return "byte[]";
        } else {
            return "Object";
        }
    }

    /**
     * 创建写入模版内容
     *
     * @param tableInfo 表信息
     * @return
     */
    public Map<String, Object> getContext(TableInfo tableInfo) throws Exception {
        Map<String, Object> context = new HashMap<>();
        context.put("date", LocalDate.now().format(DateTimeFormatter.ISO_DATE));

        //处理表明驼峰
        String tableNameLower = NameUtils.underscoreToCamelCase(tableInfo.getTableName());
        String tableNameHigher = NameUtils.underscoreToPascalCase(tableInfo.getTableName());
        context.put("tableNameLower", tableNameLower);
        context.put("tableNameHigher", tableNameHigher);

        // 预处理列信息
        MySQLDruidConnectionManager instance = MySQLDruidConnectionManager.getInstance();
        List<ColumnInfo> columnInfos = instance.getTableColumns(tableInfo.getTableName());

        for (ColumnInfo column : columnInfos) {

            column.setColumnType(getJavaType(column.getColumnType()));
            column.setColumnName(NameUtils.underscoreToCamelCase(column.getColumnName()));
        }

        tableInfo.setColumns(columnInfos);

        context.put("table", tableInfo);
        context.put("packageName", tableInfo.getPackageName());

        return context;
    }

    /**
     * 生成文件
     *
     * @param template 模版
     * @param subDir   文件夹
     * @param fileName 文件名称
     * @param context  写入内容
     */
    public void generateFile(String template, String subDir, String fileName, Map<String, Object> context) {
        try {
            String dirPath = outputDir + context.get("tableNameLower") + "/" + subDir + "/";
            Files.createDirectories(Paths.get(dirPath));

            Template tpl = velocityEngine.getTemplate("templates/mysql/" + template);
            try (FileWriter writer = new FileWriter(dirPath + fileName)) {
                tpl.merge(new VelocityContext(context), writer);
            }
        } catch (Exception e) {
            throw new RuntimeException("生成文件失败: " + template, e);
        }
    }

    /**
     * 打包下载
     *
     * @param response
     * @param fileName 文件名称
     * @throws IOException
     */
    public void downLoadFile(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".zip");

        try {
            ZipUtils.zipFolder(outputDir, response.getOutputStream());
        } finally {
            // 清理临时文件
            FileUtils.deleteDirectory(new File(outputDir));
        }
    }
}
