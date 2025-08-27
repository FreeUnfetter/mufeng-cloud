package com.mufeng.mufengGenerator.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

public class MySQLDruidConnectionManager {
    // Druid数据源
    private DruidDataSource dataSource;
    private final ReentrantLock lock = new ReentrantLock();

    // 当前连接配置
    private String currentHost;
    private int currentPort;
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
     * 初始化数据库连接池
     */
    public void initializeConnection(String host, int port, String database,
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
     * 创建Druid数据源
     */
    private void createDataSource(String host, int port, String database,
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

    /**
     * 检查连接池是否有效
     */
    private boolean isPoolValid() {
        return dataSource != null && !dataSource.isClosed();
    }

    /**
     * 检查连接信息是否相同
     */
    private boolean isSameConnection(String host, int port, String database,
                                     String username, String password) {
        return host.equals(currentHost) &&
                port == currentPort &&
                database.equals(currentDatabase) &&
                username.equals(currentUsername) &&
                password.equals(currentPassword);
    }

    /**
     * 获取数据库连接
     */
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

    /**
     * 关闭数据源
     */
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

    /**
     * 测试连接
     */
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

    /**
     * 获取连接池状态信息
     */
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

    /**
     * 重置连接管理器
     */
    public void reset() {
        lock.lock();
        try {
            closeDataSource();
            currentHost = null;
            currentUsername = null;
            currentPassword = null;
            currentDatabase = null;
            currentPort = 0;
        } finally {
            lock.unlock();
        }
    }

}
