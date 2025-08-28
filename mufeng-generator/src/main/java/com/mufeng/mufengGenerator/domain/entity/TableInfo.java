package com.mufeng.mufengGenerator.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo {
    /** 表名 */
    private String tableName;
    /** 表类型 */
    private String tableType;
    /** 表注释 */
    private String tableRemarks;

    /** 包名 */
    private String packageName;
    /** 数据类型 */
    private String dataType;

    private List<ColumnInfo> columns;

}
