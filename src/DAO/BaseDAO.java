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
		}
		db.close(link, pstmt, rs);
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
		}
		db.close(link, pstmt, rs);
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
		        
		        if (params != null) 
		            for (int i = 0; i < params.size(); i++) 
		                pstmt.setObject(i + 1, params.get(i));
		        
		        // thuc thi
		        success = pstmt.executeUpdate() > 0 ? true : false;
			}
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		db.close(link, pstmt, null);
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
		        
		        if (params != null) 
		            for (int i = 0; i < params.size(); i++) 
		                pstmt.setObject(i + 1, params.get(i));
		        
		        // thuc thi
		        success = pstmt.executeUpdate() > 0 ? true : false;
			}
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		db.close(link, pstmt, null);
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
		}
		db.close(link, pstmt, null);
		return success;
    }
    
//  public boolean isExist(List<String> conditions, List<Object> paramsCond, String refs, int paramsRef) {
//	DatabaseConnect db = new DatabaseConnect();
//	boolean exist = false;
//    if (paramsCond != null && conditions != null
//    		&& paramsCond.size() == conditions.size()) {
//    	
//    	StringBuilder sql = new StringBuilder("SELECT * FROM "+ table + " WHERE ");
//    	// điều kiện xảy ra trùng
//    	int conditionsSize = conditions.size();
//        for(int i=0; i<conditionsSize; i++) {
//        	sql.append(conditions.get(i)).append(" = ? ");
//        	if(i<conditionsSize-1) sql.append("AND ");
//        }
//        
//        // điều kiện tham chiếu
//        if(refs != "") 
//        	sql.append("AND ").append(refs).append(" != "+paramsRef);
//        System.out.println(sql.toString());
//        ResultSet rs = null;
//        try {
//        	rs = db.getAll(sql.toString(), paramsCond);
//            if (rs.next()) exist = true;
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//        	try {
//        		db.close(rs);
//        	}catch(SQLException e) {
//        		e.printStackTrace();
//        	}	
//        }
//    }
//    return exist;
//}

//public T findOne(List<String> conditions, List<Object> params) {
//	DatabaseConnect db = new DatabaseConnect();
//    T entity = null;
//    if (params != null && conditions != null && params.size() == conditions.size()) {
//    	StringBuilder sql = new StringBuilder("SELECT * FROM "+table + " WHERE ");
//    	int conditionSize = conditions.size();
//        for(int i=0; i<conditionSize; i++) {
//        	sql.append(conditions.get(i)).append(" = ? ");
//        	if(i<conditionSize - 1) sql.append("OR ");
//        }
//        
//        ResultSet rs = null;
//        int count = 0;
//        try {
//        	rs = db.getAll(sql.toString(), params);
//        	while (rs.next() && ++count < 2) 	                	
//        		entity = mapResultSetToDTO(rs);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//        	try {
//        		db.close(rs);
//        	}catch(SQLException e) {
//        		e.printStackTrace();
//        	}	
//        }
//    }
//    return entity;
//}

//public List<T> search(List<String> conditions, List<Object> params){
//	DatabaseConnect db = new DatabaseConnect();
//    List<T> list = new ArrayList<>();
//    if (params != null && conditions != null && params.size() == conditions.size()) {
//    	StringBuilder sql = new StringBuilder("SELECT * FROM "+ table + " WHERE ");
//    	int conditionSize = conditions.size();
//        for(int i=0; i<conditionSize; i++) {
//        	sql.append("CAST(").append(conditions.get(i)).append(" AS CHAR) LIKE CONCAT('%', ?, '%')");
//        	if(i<conditionSize - 1) sql.append("OR ");
//        }
//        
//        System.out.println(sql.toString());
//        ResultSet rs = null;
//        try {
//        	rs = db.getAll(sql.toString(), params);
//            while (rs.next()) list.add(mapResultSetToDTO(rs));
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//        	try {
//        		db.close(rs);
//        	}catch(SQLException e) {
//        		e.printStackTrace();
//        	}	
//        }
//    }
//    return list;
//}
//@Override
//public Optional<T> findById(String column, int id) {
//    String sql = "SELECT * FROM " + getTableName() + " WHERE " + column + " = ?";
//    List<Object> params = new ArrayList<>();
//    params.add(id);
//    ResultSet rs = db.getAll(sql, params);
//    try {
//        if (rs != null && rs.next()) {
//            T entity = mapResultSetToDTO(rs);
//            return Optional.ofNullable(entity);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    finally {
//        try{
//            rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    return Optional.empty();
//}
//
//@Override
//public boolean isExist(String column, int id) {
//    String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE " + column + " = ?";
//    List<Object> params = new ArrayList<>();
//    params.add(id);
//    DatabaseConnection.QueryResult qr = db.getOne(sql, params);
//    try {
//        if (qr.resultSet != null) {
//            boolean exists = qr.resultSet.getInt(1) > 0;
//            return exists;
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    finally {
//        qr.close();
//    }
//    return false;
//}
//
//@Override

    // *** Các phương thức trừu tượng mà lớp con phải triển khai
    //protected abstract String getTableName();
    //protected abstract List<String> getTableColumns();
    protected abstract T mapResultSetToDTO(ResultSet rs) throws SQLException;
    // ***
}