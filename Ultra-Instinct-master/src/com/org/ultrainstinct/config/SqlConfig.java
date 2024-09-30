package com.org.ultrainstinct.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class SqlConfig {
    private static Connection connection = null;
    private SqlConfig() {}
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String sqlHost = "localhost";
            String sqlPort = "1433";
            String sqlUsername = "sa";
            String sqlPassword = "123";
            String sqlDatabase = "DuAn1_QuanLyCuaHang";
            String url = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=true;trustServerCertificate=true;", sqlHost, sqlPort, sqlDatabase);
            try {
                connection = DriverManager.getConnection(url, sqlUsername, sqlPassword);
                System.out.println("Connected to the database successfully.");
            } catch (SQLException e) {
                System.out.println("Failed to connect to the database.");
                throw e;
            }
        }
        return connection;
    }
    public static PreparedStatement prepareStatement(String sql, Object... args) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        if (sql.trim().startsWith("{")) {
            pstmt = connection.prepareCall(sql);
        } else {
            pstmt = connection.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }
    public static void executeUpdate(String sql, Object... args) {
        try (PreparedStatement stmt = prepareStatement(sql, args)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ResultSet executeQuery(String sql, Object... args) throws SQLException {
        PreparedStatement stmt = prepareStatement(sql, args);
        return stmt.executeQuery();
    }
}
