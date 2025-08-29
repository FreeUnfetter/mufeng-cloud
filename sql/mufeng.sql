CREATE TABLE database_config
(
    id           VARCHAR(100) PRIMARY KEY,
    user_id      VARCHAR(100) NOT NULL COMMENT '用户id',
    db_host      VARCHAR(100) NOT NULL COMMENT '数据库ip',
    db_user_name VARCHAR(100) NOT NULL COMMENT '数据库用户名',
    db_password  VARCHAR(100) NOT NULL COMMENT '数据库密码',
    db_port      VARCHAR(100) NOT NULL COMMENT '数据库端口',
    db_name      VARCHAR(100) NOT NULL COMMENT '数据库名称',
    db_comment   VARCHAR(100) COMMENT '数据库备注',
    data_type    VARCHAR(100) NOT NULL COMMENT '数据库类型',
    create_name  VARCHAR(100) COMMENT '创建人',
    create_date  datetime COMMENT '创建时间',
    update_name  VARCHAR(100) COMMENT '更新人',
    update_date  datetime COMMENT '更新时间',
    version      INT COMMENT '版本控制'
) DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO database_config (id, user_id, db_host, db_userName, db_password, db_port, db_name, db_comment, data_type)
VALUES (1, 1, 'localhost', 'root', '123456', '3306', 'mufeng-cloud', '数据库', 'mysql'),
       (2, 2, 'localhost', 'root', '123456', '3306', 'shop_goods', '数据库', 'mysql');

SELECT *
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'mufeng-cloud';