package DAO;

import java.sql.*;

public class DatabaseConnect {
	private final String DB_HOST = "localhost:3306";
	private final String DB_USER= "root";
	private final String DB_PASS = "";
	private final String DB_NAME = "coffeeshop";
    
    public Connection connectDB() throws ClassNotFoundException, SQLException{
        // Load driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Cấu hình URL
        String url = "jdbc:mysql://" + DB_HOST + "/" + DB_NAME;

        // Kết nối database
        return DriverManager.getConnection(url, DB_USER, DB_PASS);
    }

    public void close(Connection link, PreparedStatement pstmt, ResultSet rs){
        // Đóng ResultSet nếu có
        if (rs != null) 
	        try {
	        	rs.close();
	        } catch (SQLException e) {
	        	e.printStackTrace();
	        }
        

        // Đóng PreparedStatement nếu có
        if(pstmt != null) 
        	try {
        		pstmt.close();
        	}catch (SQLException e) {
            	e.printStackTrace();
            }

        // Đóng Connection (link) nếu có
        if(link != null) 
        	try {
        		link.close();
        	}catch (SQLException e) {
            	e.printStackTrace();
            }
    }
    
    public String getDBName() {
    	return DB_NAME;
    }

}