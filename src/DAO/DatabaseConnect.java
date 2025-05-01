package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public void close(Connection link){
        // Đóng Connection (link) nếu có
        if(link != null) {
			try {
        		link.close();
        	}catch (SQLException e) {
            	e.printStackTrace();
            }
		}
    }

    public String getDBName() {
    	return DB_NAME;
    }

}