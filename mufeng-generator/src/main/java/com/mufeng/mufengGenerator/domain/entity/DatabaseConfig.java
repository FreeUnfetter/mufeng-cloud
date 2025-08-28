package com.mufeng.mufengGenerator.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "database_config")
public class DatabaseConfig {
    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    /** 用户id */
    private String userId;
    /** 数据库ip */
    @NotBlank(message = "数据库ip不能为空")
    private String dbHost;
    /** 数据库用户名 */
    @NotBlank(message = "数据库用户名不能为空")
    private String dbUserName;
    /** 数据库密码 */
    @NotBlank(message = "数据库密码不能为空")
    private String dbPassword;
    /** 数据库端口 */
    @NotBlank(message = "数据库端口不能为空")
    private String dbPort;
    /** 数据库名称 */
    @NotBlank(message = "数据库名称不能为空")
    private String dbName;
    /** 数据库备注 */
    private String dbComment;
    /** 数据库类型 */
    private String dataType;
    /** 创建人 */
    private String createName;
    /** 创建时间 */
    private LocalDateTime createDate;
    /** 更新人 */
    private String updateName;
    /** 更新时间 */
    private LocalDateTime updateDate;

    // 在持久化之前自动执行的方法
    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    // 在更新之前自动执行的方法
    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDateTime.now();
    }
}
