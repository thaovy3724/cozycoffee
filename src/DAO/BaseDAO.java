package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseDAO<T>{
    protected String table;
    protected List<String> tableColumns;
    
    public BaseDAO(String table, List<String> tableColumns) {
    	this.table = table;
    	this.tableColumns = tableColumns;
    }
    
    public List<T> getAll() {
    	DatabaseConnect db = new DatabaseConnect();
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + table;
        ResultSet rs = null;
        try {
        	rs = db.getAll(sql, null);
            while (rs.next()) list.add(mapResultSetToDTO(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
        	try {
        		db.close(rs);
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return list;
    }

    public int getNewAutoIncrementNumber() {
    	DatabaseConnect db = new DatabaseConnect();
    	String sql = "SELECT AUTO_INCREMENT as newID FROM information_schema.TABLES " +
                "WHERE TABLE_SCHEMA = '" + db.getDBName() + "' " + 
                "AND TABLE_NAME = '" + table + "'";

        ResultSet rs = null;
        int newID = -1;
        try {
        	rs = db.getAll(sql, null);
            while (rs.next()) newID = rs.getInt("newID");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
        	try {
            db.close(rs);
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return newID;
    }
    
    public boolean isExist(List<String> conditions, List<Object> paramsCond, String refs, int paramsRef) {
    	DatabaseConnect db = new DatabaseConnect();
    	boolean exist = false;
        if (paramsCond != null && conditions != null
        		&& paramsCond.size() == conditions.size()) {
        	
        	StringBuilder sql = new StringBuilder("SELECT * FROM "+ table + " WHERE ");
        	// điều kiện xảy ra trùng
        	int conditionsSize = conditions.size();
	        for(int i=0; i<conditionsSize; i++) {
	        	sql.append(conditions.get(i)).append(" = ? ");
	        	if(i<conditionsSize-1) sql.append("AND ");
	        }
	        
	        // điều kiện tham chiếu
	        if(refs != "") 
	        	sql.append("AND ").append(refs).append(" != "+paramsRef);
	        System.out.println(sql.toString());
	        ResultSet rs = null;
	        try {
	        	rs = db.getAll(sql.toString(), paramsCond);
	            if (rs.next()) exist = true;
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        finally {
	        	try {
	        		db.close(rs);
	        	}catch(SQLException e) {
	        		e.printStackTrace();
	        	}	
	        }
        }
        return exist;
    }
    
    public T findOne(List<String> conditions, List<Object> params) {
    	DatabaseConnect db = new DatabaseConnect();
        T entity = null;
        if (params != null && conditions != null && params.size() == conditions.size()) {
        	StringBuilder sql = new StringBuilder("SELECT * FROM "+table + " WHERE ");
        	int conditionSize = conditions.size();
	        for(int i=0; i<conditionSize; i++) {
	        	sql.append(conditions.get(i)).append(" = ? ");
	        	if(i<conditionSize - 1) sql.append("OR ");
	        }
	        
	        ResultSet rs = null;
	        int count = 0;
	        try {
	        	rs = db.getAll(sql.toString(), params);
	        	while (rs.next() && ++count < 2) 	                	
	        		entity = mapResultSetToDTO(rs);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        finally {
	        	try {
	        		db.close(rs);
	        	}catch(SQLException e) {
	        		e.printStackTrace();
	        	}	
	        }
        }
        return entity;
    }
    
    public List<T> search(List<String> conditions, List<Object> params){
    	DatabaseConnect db = new DatabaseConnect();
        List<T> list = new ArrayList<>();
        if (params != null && conditions != null && params.size() == conditions.size()) {
        	StringBuilder sql = new StringBuilder("SELECT * FROM "+ table + " WHERE ");
        	int conditionSize = conditions.size();
	        for(int i=0; i<conditionSize; i++) {
	        	sql.append("CAST(").append(conditions.get(i)).append(" AS CHAR) LIKE CONCAT('%', ?, '%')");
	        	if(i<conditionSize - 1) sql.append("OR ");
	        }
	        
	        System.out.println(sql.toString());
	        ResultSet rs = null;
	        try {
	        	rs = db.getAll(sql.toString(), params);
                while (rs.next()) list.add(mapResultSetToDTO(rs));
                
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        finally {
	        	try {
	        		db.close(rs);
	        	}catch(SQLException e) {
	        		e.printStackTrace();
	        	}	
	        }
        }
        return list;
    }
//    @Override
//    public Optional<T> findById(String column, int id) {
//        String sql = "SELECT * FROM " + getTableName() + " WHERE " + column + " = ?";
//        List<Object> params = new ArrayList<>();
//        params.add(id);
//        ResultSet rs = db.getAll(sql, params);
//        try {
//            if (rs != null && rs.next()) {
//                T entity = mapResultSetToDTO(rs);
//                return Optional.ofNullable(entity);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean isExist(String column, int id) {
//        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE " + column + " = ?";
//        List<Object> params = new ArrayList<>();
//        params.add(id);
//        DatabaseConnection.QueryResult qr = db.getOne(sql, params);
//        try {
//            if (qr.resultSet != null) {
//                boolean exists = qr.resultSet.getInt(1) > 0;
//                return exists;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            qr.close();
//        }
//        return false;
//    }
//
//    @Override
    public int add(List<Object> params) {

        //Đảm bảo phải có tham số truyền vào
        //và số lượng tham số bằng với số cột để tránh gọi truy vấn sql không cần thiết
    	int newID = -1;
        if (params != null && params.size() == tableColumns.size()) {
	        
	        DatabaseConnect db = new DatabaseConnect();
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
	
	        if(db.execute(sql.toString(), params))
	        	newID = getNewAutoIncrementNumber();
        }
        return newID;
    }

    public boolean update(List<Object> params, String condition) {
    	//Đảm bảo phải có tham số truyền vào
        //và số lượng tham số bằng với số cột để tránh gọi truy vấn sql không cần thiết
    	boolean success = false;
        if (params != null && params.size() == tableColumns.size()) {
	        
	        DatabaseConnect db = new DatabaseConnect();
	        StringBuilder sql = new StringBuilder("UPDATE ");
	
	        //Tên table
	        sql.append(table);
	        sql.append(" SET ");
	        
	     // Generate SET clause with column names and placeholders
	        for (int i = 0; i < tableColumns.size(); i++) {
	            sql.append(tableColumns.get(i)).append(" = ?");
	            if (i < tableColumns.size() - 1) {
	                sql.append(", ");
	            }
	        }
	        
	        // Add condition (WHERE clause)
	        sql.append(" WHERE ").append(condition);
	        System.out.println(sql);
	        if(db.execute(sql.toString(), params)) success = true;
        }
        return success;
    }

    // *** Các phương thức trừu tượng mà lớp con phải triển khai
    //protected abstract String getTableName();
    //protected abstract List<String> getTableColumns();
    protected abstract T mapResultSetToDTO(ResultSet rs) throws SQLException;
    // ***
}