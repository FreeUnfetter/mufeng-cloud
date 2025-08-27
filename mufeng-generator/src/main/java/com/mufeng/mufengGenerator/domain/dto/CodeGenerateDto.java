package com.mufeng.mufengGenerator.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CodeGenerateDto {
    @NotBlank(message = "数据库类型不能为空")
    private String dbType; // mysql or mongodb

    @NotBlank(message = "表名不能为空")
    private String tableName;

    @NotBlank(message = "包名不能为空")
    private String packageName;

    private String author;
    private String version;

    @NotNull(message = "是否生成Controller不能为空")
    private Boolean generateController;

    @NotNull(message = "是否生成Service不能为空")
    private Boolean generateService;

    @NotNull(message = "是否生成Entity不能为空")
    private Boolean generateEntity;

    @NotNull(message = "是否生成Mapper不能为空")
    private Boolean generateMapper;
}
