package com.mufeng.mufengGenerator.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PageRequest {
    @NotNull
    private Integer pageNum;
    @NotNull
    private Integer pageSize;
    @NotBlank(message = "数据库类型不能为空")
    private String dataType;
    /** 数据库名称 */
    private String dbName;
    /** 表名称 */
    private String tableName;
}
