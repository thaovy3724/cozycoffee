package DAO;

import CONFIG.Config;
import java.sql.*;
import java.util.List;

public class DatabaseConnect {
    private String host = Config.DB_HOST;
    private String user = Config.DB_USER;
    private String pass = Config.DB_PASS;
    private String dbname = Config.DB_NAME;
    private Connection link;
    private PreparedStatement pstmt;

    public DatabaseConnect() {
        try {
            this.connectDB();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void connectDB() throws SQLException, ClassNotFoundException {
        // Đóng kết nối cũ nếu đang mở
        if (this.link != null && !this.link.isClosed()) {
            this.link.close();
        }

        // Load driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Cấu hình URL
        String url = "jdbc:mysql://" + this.host + "/" + this.dbname;

        // Kết nối database
        this.link = DriverManager.getConnection(url, this.user, this.pass);
    }

    // Kiểm tra và mở lại kết nối nếu cần
    private void ensureConnection() throws SQLException, ClassNotFoundException {
        if (this.link == null || this.link.isClosed()) {
            connectDB();
        }
    }

    public boolean execute(String sql, List<Object> params) {
        try {
            ensureConnection();
            pstmt = this.link.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(i + 1, params.get(i));
                }
            }
            return pstmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getAll(String sql, List<Object> params) throws SQLException, ClassNotFoundException {
        ensureConnection();
        pstmt = this.link.prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
        }
        return pstmt.executeQuery();
    }

    public void close(ResultSet rs) throws SQLException {
        // Đóng ResultSet nếu có
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }

        // Đóng PreparedStatement nếu có
        if (pstmt != null && !pstmt.isClosed()) {
            pstmt.close();
        }

        // Không đóng Connection ở đây để tái sử dụng
        // Connection sẽ được đóng trong DAO nếu cần
    }

    // Phương thức để đóng Connection khi cần
    public void closeConnection() throws SQLException {
        if (link != null && !link.isClosed()) {
            link.close();
            link = null;
        }
    }
}