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
    	} catch(SQLException e) {
    		e.printStackTrace();
    	} catch(ClassNotFoundException e) {
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

//    public Connection getLink() {
//        return this.link;
//    }

    public boolean execute(String sql, List<Object> params) {
        if (this.link == null) return false;
       
        try {
        	PreparedStatement pstmt = this.link.prepareStatement(sql);
        	if (params != null) 
                for (int i = 0; i < params.size(); i++) 
                    pstmt.setObject(i + 1, params.get(i));
        	boolean result = pstmt.executeUpdate() > 0;
            close(null);
            return result;
        }catch (SQLException e) {
        	e.printStackTrace(); // Hoặc dùng Logger thay vì in ra console
            return false;
        }
    
    }
    
    // Trả về tất cả các dòng dưới dạng ResultSet
    public ResultSet getAll(String sql, List<Object> params) throws SQLException{
        if (this.link == null) throw new SQLException("Database connection is null");
        try {
        PreparedStatement pstmt = this.link.prepareStatement(sql);
        if (params != null) 
            for (int i = 0; i < params.size(); i++) 
                pstmt.setObject(i + 1, params.get(i));
        return pstmt.executeQuery();
        } catch (SQLException e) {
            throw e;
        }
    }

//    public ResultSet getAll(String sql, Object... params) throws SQLException {
//        if (this.link == null) throw new SQLException("Database connection is null");
//
//        PreparedStatement pstmt = this.link.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//        try {
//            for (int i = 0; i < params.length; i++) {
//                if (params[i] instanceof String) {
//                    pstmt.setString(i + 1, (String) params[i]);
//                } else if (params[i] instanceof Integer) {
//                    pstmt.setInt(i + 1, (Integer) params[i]);
//                } else if (params[i] instanceof Double) {
//                    pstmt.setDouble(i + 1, (Double) params[i]);
//                } else if (params[i] instanceof Long) {
//                    pstmt.setLong(i + 1, (Long) params[i]);
//                } else if (params[i] instanceof Boolean) {
//                    pstmt.setBoolean(i + 1, (Boolean) params[i]);
//                } else if (params[i] instanceof java.sql.Timestamp) {
//                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) params[i]);
//                } else {
//                    pstmt.setObject(i + 1, params[i]); // Cho các kiểu khác
//                }
//            }
//            return pstmt.executeQuery(); // Gọi xong thì người gọi phải đóng ResultSet và PreparedStatement
//        } catch (SQLException e) {
//            throw e; // Đẩy lỗi lên để người gọi xử lý
//        } 
//    }

    public void close(ResultSet rs) throws SQLException {
        // Đóng ResultSet nếu có
        if (rs != null && !rs.isClosed()) {
            try {
                rs.close();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }

        // Đóng PreparedStatement nếu có
        if (pstmt != null && !pstmt.isClosed()) {
            try {
                pstmt.close();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }

        // Đóng Connection (link) nếu có
        if (link != null && !link.isClosed()) {
            try {
                link.close();
            } catch (SQLException e) {
            	e.printStackTrace();
            } finally {
                link = null; // Đặt lại link về null sau khi đóng
            }
        }
    }

}