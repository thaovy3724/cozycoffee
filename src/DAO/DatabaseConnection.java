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
    

    public boolean execute(String sql, List<Object> params) {
        try {
            if (this.link != null) {
                PreparedStatement pstmt = this.link.prepareStatement(sql);
                for (int i = 0; i < params.size(); i++) {
                    //Tham số sql bắt đầu từ 1, nhưng tham số trong list params bắt đầu từ 0
                    pstmt.setObject(i + 1, params.get(i));
                }
                boolean result = pstmt.executeUpdate() > 0;
                pstmt.close();
                return result;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    // Trả về tất cả các dòng dưới dạng ResultSet
    public ResultSet getAll(String sql, List<Object> params) {
        try {
            if (this.link != null) {
                PreparedStatement pstmt = this.link.prepareStatement(sql);
                if (params != null) {
                    for (int i = 0; i < params.size(); i++) {
                        pstmt.setObject(i + 1, params.get(i));
                    }
                }
                return pstmt.executeQuery();
                // Lưu ý: Người dùng cần đóng ResultSet và pstmt sau khi sử dụng
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    // Trả về một dòng dưới dạng ResultSet
//    public ResultSet getOne(String sql, List<Object> params) {
//        try {
//            if (this.link != null) {
//                PreparedStatement pstmt = this.link.prepareStatement(sql);
//                // Gán các tham số từ params vào PreparedStatement
//                if (params != null) {
//                    for (int i = 0; i < params.size(); i++) {
//                        pstmt.setObject(i + 1, params.get(i));
//                    }
//                }
//                ResultSet rs = pstmt.executeQuery();
//                rs.next();
//                return new QueryResult(pstmt, rs);
//            }
//            return null;
//        } catch (SQLException e) {
//            return null;
//        }
//    }

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
                        close(pstmt, rs);
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
    public void close(PreparedStatement pstmt, ResultSet rs) throws SQLException {
        // Đóng ResultSet nếu có
        if (rs != null && !rs.isClosed()) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
            }
        }

        // Đóng PreparedStatement nếu có
        if (pstmt != null && !pstmt.isClosed()) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing PreparedStatement: " + e.getMessage());
            }
        }

        // Đóng Connection (link) nếu có
        if (this.link != null && !this.link.isClosed()) {
            try {
                this.link.close();
            } catch (SQLException e) {
                System.err.println("Error closing Connection: " + e.getMessage());
            } finally {
                this.link = null; // Đặt lại link về null sau khi đóng
            }
        }
    }
}