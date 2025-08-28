package com.mufeng.mufengGenerator.domain.entity;

import lombok.Data;

@Data
public class ColumnInfo {
    private String columnName;
    private String dataType;
    private String columnComment;
    private String columnKey; // PRI表示主键
}
