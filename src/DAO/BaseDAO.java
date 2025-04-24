package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseDAO<T>{
    protected String table;
    protected List<String> tableColumns;
	protected DatabaseConnect db;
	
    public BaseDAO(String table, List<String> tableColumns) {
    	this.table = table;
    	this.tableColumns = tableColumns;
    	db = new DatabaseConnect();
    }
    
    public List<T> getAll() {
		List<T> list = new ArrayList<>();
		Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			link = db.connectDB();
			String sql = "SELECT * FROM " + table;
			pstmt = link.prepareStatement(sql);
			rs = pstmt.executeQuery();
            while (rs.next()) list.add(mapResultSetToDTO(rs));
		}catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}finally {
			db.close(link);
		}
		return list;
    }

    public int getNewAutoIncrementNumber() {
    	Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int newID = -1;
		try {
			link = db.connectDB();
			String sql = "SELECT AUTO_INCREMENT as newID FROM information_schema.TABLES " +
	                "WHERE TABLE_SCHEMA = '" + db.getDBName() + "' " + 
	                "AND TABLE_NAME = '" + table + "'";
			pstmt = link.prepareStatement(sql);
			rs = pstmt.executeQuery();
            if(rs.next()) newID = rs.getInt("newID");
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			db.close(link);
		}
		return newID;
    }
    
    public boolean add(List<Object> params) {
    	Connection link = null;
		PreparedStatement pstmt = null;
		boolean success = false;
		try {
			if (params != null && params.size() == tableColumns.size()) {
				// tao sql
				StringBuilder sql = new StringBuilder("INSERT INTO ");
				
		        //Tên table
		        sql.append(table);
		        sql.append(" (");
		        //Danh sách cột của table
		        sql.append(String.join(",", tableColumns));
		        sql.append(") VALUES (");
		        //Với mỗi tham số, thêm một character "?"
		        sql.append(String.join(",", Collections.nCopies(params.size(), "?")));
		        sql.append(")");
		        
		        // noi param
		        link = db.connectDB();
		        pstmt = link.prepareStatement(sql.toString());
		        
	            for (int i = 0; i < params.size(); i++) 
	                pstmt.setObject(i + 1, params.get(i));
		        
		        // thuc thi
		        success = pstmt.executeUpdate() > 0 ? true : false;
			}
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			db.close(link);
		}
		return success;
    }

    public boolean update(List<Object> params, String condition) {
    	Connection link = null;
		PreparedStatement pstmt = null;
		boolean success = false;
		try {
			if (params != null && params.size() == tableColumns.size()) {
			// tao sql
				StringBuilder sql = new StringBuilder("UPDATE ");
				
		        //Tên table
		        sql.append(table);
		        sql.append(" SET ");
		        
		        // Generate SET clause with column names
		        for (int i = 0; i < tableColumns.size(); i++) {
		            sql.append(tableColumns.get(i)).append(" = ?");
		            if (i < tableColumns.size() - 1) {
		                sql.append(", ");
		            }
		        }
		        // Add condition (WHERE clause) -> where id = ...
		        sql.append(" WHERE ").append(condition);
		        
		        // noi param
		        link = db.connectDB();
		        pstmt = link.prepareStatement(sql.toString());
		        
	            for (int i = 0; i < params.size(); i++) 
	                pstmt.setObject(i + 1, params.get(i));
		        
		        // thuc thi
		        success = pstmt.executeUpdate() > 0 ? true : false;
			}
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			db.close(link);
		}
		return success;
    }
		
	public boolean delete(String column, Object value) {
    	Connection link = null;
		PreparedStatement pstmt = null;
		boolean success = false;
		try {
			// tao sql
				StringBuilder sql = new StringBuilder("DELETE FROM ");
				
		        //Tên table
		        sql.append(table);
		        
		        // Add condition (WHERE clause) -> where id = ...
		        sql.append(" WHERE ");
		        sql.append(column);
		        sql.append(" = ?");
		        
		        // noi param
		        link = db.connectDB();
		        pstmt = link.prepareStatement(sql.toString());
		        pstmt.setObject(1, value);
		        
		        // thuc thi
		        success = pstmt.executeUpdate() > 0 ? true : false;
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			db.close(link);
		}
		return success;
    }

    // *** Các phương thức trừu tượng mà lớp con phải triển khai
    protected abstract T mapResultSetToDTO(ResultSet rs) throws SQLException;
    // ***
}