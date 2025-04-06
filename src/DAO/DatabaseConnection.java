package DAO;

import java.sql.*;
import java.util.List;

import static config.Config.DB_HOST;
import static config.Config.DB_USER;
import static config.Config.DB_PASS;
import static config.Config.DB_NAME;

public class DatabaseConnection {
    private String host = DB_HOST;
    private String user = DB_USER;
    private String pass = DB_PASS;
    private String dbname = DB_NAME;

    private Connection link;
    private PreparedStatement pstmt;
    private String error;

    public DatabaseConnection() {
        this.connectDB();
    }

    private void connectDB() {
        try {
            String url = "jdbc:mysql://" + this.host + "/" + this.dbname;
            this.link = DriverManager.getConnection(url, this.user, this.pass);
        } catch (SQLException e) {
            this.error = "Connection fail: " + e.getMessage();
            this.link = null;
        }
    }

    public Connection getLink() {
        return this.link;
    }

    // Chuẩn bị PreparedStatement với một câu SQL
    public void prepareStatement(String sql) throws SQLException {
        if (this.link == null || this.link.isClosed()) {
            throw new SQLException("Database connection is not available");
        }
        closePreparedStatement(); // Đóng pstmt cũ nếu tồn tại
        this.pstmt = this.link.prepareStatement(sql);
    }

    // Đóng PreparedStatement hiện tại
    public void closePreparedStatement() throws SQLException {
        if (this.pstmt != null && !this.pstmt.isClosed()) {
            this.pstmt.close();
            this.pstmt = null;
        }
    }

    public boolean execute(List<Object> params) throws SQLException {
        if (this.pstmt == null) {
            throw new SQLException("PreparedStatement is not initialized. Call prepareStatement first.");
        }
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                this.pstmt.setObject(i + 1, params.get(i));
            }
        }
        boolean result = this.pstmt.executeUpdate() > 0;
        this.pstmt.close();
        return result;
    }

    // Trả về tất cả các dòng dưới dạng ResultSet
    public ResultSet getAll(List<Object> params) throws SQLException {
        if (this.pstmt == null) {
            throw new SQLException("PreparedStatement is not initialized. Call prepareStatement first.");
        }
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                this.pstmt.setObject(i + 1, params.get(i));
            }
        }
        //Vì executeQuery() không bao giờ trả về null => getAll() không bao giờ trả về null
        //=> không cần xử lý trường hợp rs = null khi dùng getAll()
        ResultSet rs = this.pstmt.executeQuery();
        return rs; // Người dùng phải đóng ResultSet
    }

    public int getNewAutoIncrementNumber(String tableName) {
        String sql = "SELECT AUTO_INCREMENT as newID " +
                "FROM information_schema.TABLES " +
                "WHERE TABLE_SCHEMA = ? " +
                "AND TABLE_NAME = ?";

        try {
            if (this.link != null) {
                try (PreparedStatement pstmt = this.link.prepareStatement(sql)) {
                    pstmt.setString(1, DB_NAME);
                    pstmt.setString(2, tableName);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        int newID = rs.getInt("newID");
                        rs.close();
                        return newID;
                    }
                    return -1;
                }
            }
            return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    // Method để đóng kết nối
    public void close() {
        try {
            closePreparedStatement();// Đóng PreparedStatement
            if (this.link != null && !this.link.isClosed()) {
                this.link.close();
            }
        } catch (SQLException e) {
            this.error = "Error closing connection: " + e.getMessage();
        }
    }
}