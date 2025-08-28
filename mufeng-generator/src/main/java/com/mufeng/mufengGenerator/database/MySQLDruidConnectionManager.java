package com.mufeng.mufengGenerator.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mufeng.mufengGenerator.domain.entity.ColumnInfo;
import com.mufeng.mufengGenerator.domain.entity.TableInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;


public class MySQLDruidConnectionManager {
    // Druid数据源
    private DruidDataSource dataSource;
    private final ReentrantLock lock = new ReentrantLock();

    // 当前连接配置
    private String currentHost;
    private String currentPort;
    private String currentDatabase;
    private String currentUsername;
    private String currentPassword;

    // 单例实例
    private static volatile MySQLDruidConnectionManager instance;

    // 私有构造函数
    private MySQLDruidConnectionManager() {}

    /**
     * 获取单例实例
     */
    public static MySQLDruidConnectionManager getInstance() {
        if (instance == null) {
            synchronized (MySQLDruidConnectionManager.class) {
                if (instance == null) {
                    instance = new MySQLDruidConnectionManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化数据连接池
     *
     * @param host 主机
     * @param port 端口
     * @param database 数据库名称
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public void initializeConnection(String host, String port, String database,
                                     String username, String password) throws Exception {
        lock.lock();
        try {
            // 如果连接信息相同且连接池有效，则不重新创建
            if (isSameConnection(host, port, database, username, password) && isPoolValid()) {
                System.out.println("连接信息未变化，继续使用现有连接池");
                return;
            }

            // 关闭旧连接池
            closeDataSource();

            // 创建新的连接池
            createDataSource(host, port, database, username, password);

            // 保存当前连接配置
            this.currentHost = host;
            this.currentPort = port;
            this.currentDatabase = database;
            this.currentUsername = username;
            this.currentPassword = password;

            System.out.println("数据库连接池初始化成功");
        } finally {
            lock.unlock();
        }
    }

    /**
     * 查询数据库所有表信息
     */
    public List<TableInfo> getTableInfo() throws Exception{
        DruidPooledConnection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});

        List<TableInfo> tableInfos = new ArrayList<>();
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            String tableType = rs.getString("TABLE_TYPE");
            String remarks = rs.getString("REMARKS");

            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(tableName);
            tableInfo.setTableType(tableType);
            tableInfo.setTableRemarks(remarks);

            tableInfos.add(tableInfo);
        }

        return tableInfos;
    }

    /**
     * 查询表所有字段信息
     */
    public List<ColumnInfo> getTableColumns(String dbName) {
        List<ColumnInfo> columns = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            DatabaseMetaData metaData = conn.getMetaData();
            String catalog = conn.getCatalog();

            // 获取列信息
            ResultSet columnRs = metaData.getColumns(catalog, null, dbName, null);

            while (columnRs.next()) {
                ColumnInfo column = new ColumnInfo();
                column.setColumnName(columnRs.getString("COLUMN_NAME"));
                column.setDataType(columnRs.getString("TYPE_NAME"));

                // 处理备注信息
                String remarks = columnRs.getString("REMARKS");
                if (remarks == null) {
                    remarks = columnRs.getString("COLUMN_COMMENT");
                }
                column.setColumnComment(remarks);

                columns.add(column);
            }

            // 获取主键
            ResultSet pkRs = metaData.getPrimaryKeys(catalog, null, dbName);
            while (pkRs.next()) {
                String pkColumn = pkRs.getString("COLUMN_NAME");
                columns.stream()
                        .filter(c -> c.getColumnName().equals(pkColumn))
                        .forEach(c -> c.setColumnKey("PRI"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return columns;
    }

    /**
     * 创建Druid数据源
     */
    private void createDataSource(String host, String port, String database,
                                  String username, String password) throws Exception {
        Properties properties = new Properties();
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        // 基本连接配置
        properties.put(DruidDataSourceFactory.PROP_URL, url);
        properties.put(DruidDataSourceFactory.PROP_USERNAME, username);
        properties.put(DruidDataSourceFactory.PROP_PASSWORD, password);
        properties.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, "com.mysql.cj.jdbc.Driver");

        // 连接池配置
        properties.put(DruidDataSourceFactory.PROP_INITIALSIZE, "5");     // 初始连接数
        properties.put(DruidDataSourceFactory.PROP_MINIDLE, "5");         // 最小空闲连接数
        properties.put(DruidDataSourceFactory.PROP_MAXACTIVE, "20");      // 最大连接数
        properties.put(DruidDataSourceFactory.PROP_MAXWAIT, "60000");     // 获取连接超时时间(毫秒)

        // 连接有效性检查配置
        properties.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY, "SELECT 1");
        properties.put(DruidDataSourceFactory.PROP_TESTWHILEIDLE, "true");
        properties.put(DruidDataSourceFactory.PROP_TESTONBORROW, "false");
        properties.put(DruidDataSourceFactory.PROP_TESTONRETURN, "false");

        // 连接泄漏检测
        properties.put(DruidDataSourceFactory.PROP_REMOVEABANDONED, "true");
        properties.put(DruidDataSourceFactory.PROP_REMOVEABANDONEDTIMEOUT, "1800");
        properties.put(DruidDataSourceFactory.PROP_LOGABANDONED, "true");

        // 创建数据源
        dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
    }



    /** 检查连接池是否有效 */
    private boolean isPoolValid() {
        return dataSource != null && !dataSource.isClosed();
    }

    /** 检查连接信息是否相同 */
    private boolean isSameConnection(String host, String port, String database,
                                     String username, String password) {
        return host.equals(currentHost) &&
                port == currentPort &&
                database.equals(currentDatabase) &&
                username.equals(currentUsername) &&
                password.equals(currentPassword);
    }

    /** 获取数据库连接 */
    public Connection getConnection() throws SQLException {
        lock.lock();
        try {
            if (dataSource == null || dataSource.isClosed()) {
                throw new SQLException("数据库连接池未初始化或已关闭");
            }
            return dataSource.getConnection();
        } finally {
            lock.unlock();
        }
    }

    /** 关闭数据源 */
    public void closeDataSource() {
        lock.lock();
        try {
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
                System.out.println("数据库连接池已关闭");
            }
        } catch (Exception e) {
            System.err.println("关闭数据库连接池时出错: " + e.getMessage());
        } finally {
            dataSource = null;
            lock.unlock();
        }
    }

    /** 测试连接 */
    public boolean testConnection() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1");
             ResultSet rs = stmt.executeQuery()) {
            return rs.next();
        } catch (SQLException e) {
            System.err.println("数据库连接测试失败: " + e.getMessage());
            return false;
        }
    }

    /** 获取连接池状态信息 */
    public String getPoolStatus() {
        if (dataSource == null) {
            return "连接池未初始化";
        }

        return String.format(
                "连接池状态: 活动连接数=%d, 空闲连接数=%d, 等待线程数=%d, 总创建连接数=%d",
                dataSource.getActiveCount(),
                dataSource.getPoolingCount(),
                dataSource.getWaitThreadCount(),
                dataSource.getCreateCount()
        );
    }

    /** 重置连接管理器 */
    public void reset() {
        lock.lock();
        try {
            closeDataSource();
            currentHost = null;
            currentUsername = null;
            currentPassword = null;
            currentDatabase = null;
            currentPort = null;
        } finally {
            lock.unlock();
        }
    }
}
