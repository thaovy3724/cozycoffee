package DAO;

import DTO.TaiKhoanDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class BaseDAO<T> implements IBaseDAO<T> {
    protected String tableName;
    protected List<String> tableColumns;
    public BaseDAO(String tableName, List<String> tableColumns) {
        this.tableName = tableName;
        this.tableColumns = tableColumns;
    }

    @Override
    public List<T> getAll() {
        DatabaseConnection db = new DatabaseConnection();
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try {
            db.prepareStatement(sql);
            try (ResultSet rs = db.getAll(null)) {
                while (rs.next()) {
                    T entity = mapResultSetToDTO(rs);
                    list.add(entity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return list;
    }

    @Override
    public T findById(String column, int id) {
        DatabaseConnection db = new DatabaseConnection();
        String sql = "SELECT * FROM " + tableName + " WHERE " + column + " = ?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        T entity = null;

        try {
            db.prepareStatement(sql);
            try (ResultSet rs = db.getAll(params)) {
                if (rs.next()) {
                    entity = mapResultSetToDTO(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return entity;
    }

    @Override
    public boolean isExist(String column, Object value) {
        DatabaseConnection db = new DatabaseConnection();
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + column + " = ?";
        List<Object> params = new ArrayList<>();
        params.add(value);
        boolean exists = false;

        try {
            db.prepareStatement(sql);
            try (ResultSet rs = db.getAll(params)) {
                if (rs.next()) {
                     exists= rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return exists;
    }

    @Override
    public boolean isExist(String column, Object value, String excludedColumn, Object excludedValue) {
        DatabaseConnection db = new DatabaseConnection();
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + column + " = ? AND " + excludedColumn + " != ?" ;
        List<Object> params = new ArrayList<>();
        params.add(value);
        params.add(excludedValue);
        boolean exists = false;

        try {
            db.prepareStatement(sql);
            try (ResultSet rs = db.getAll(params)) {
                if (rs.next()) {
                    exists= rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return exists;
    }

//    @Override
//    public boolean isExist(Map<String, Object> equals, Map<String, Object> notEquals) {
//        DatabaseConnection db = new DatabaseConnection();
//        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM " + tableName + " WHERE ");
//        List<Object> params = new ArrayList<>();
//
//        List<String> conditions = new ArrayList<>();
//        for(Map.Entry<String, Object> eCondition: equals.entrySet()) {
//            conditions.add(eCondition.getKey() + " = ?");
//            params.add(eCondition.getValue());
//        }
//        for(Map.Entry<String, Object> neCondition: notEquals.entrySet()) {
//            conditions.add(neCondition.getKey() + " != ?");
//            params.add(neCondition.getValue());
//        }
//
//        try {
//            db.prepareStatement(sql);
//            ResultSet rs = db.getAll(params);
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            db.close();
//        }
//        return false;
//
//        sql.append(String.join(" AND ", conditions));
//    }

    @Override
    public boolean add(T entity) {
        DatabaseConnection db = new DatabaseConnection();
        List<Object> params = mapDTOToParams(entity);
        //Đảm bảo phải có tham số truyền vào
        //và số lượng tham số bằng với số cột để tránh gọi truy vấn sql không cần thiết
        if (params == null || params.size() != tableColumns.size()) {
            return false;
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");

        //Tên table
        sql.append(tableName);
        sql.append(" (");
        //Danh sách cột của table
        sql.append(String.join(",", tableColumns));
        sql.append(") VALUES (");
        //Với mỗi tham số, thêm một character "?"
        sql.append(String.join(",", Collections.nCopies(params.size(), "?")));
        sql.append(")");

        try {
            db.prepareStatement(sql.toString());
            return db.execute(params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false;
    }

    // *** Các phương thức trừu tượng mà lớp con phải triển khai
//    protected abstract String getTableName();
//    protected abstract List<String> getTableColumns();
    protected abstract T mapResultSetToDTO(ResultSet rs) throws SQLException;
    public abstract List<Object> mapDTOToParams(T entity);
    // ***
}
