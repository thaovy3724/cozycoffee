package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseDAO<T> {
    protected String table;
    protected List<String> tableColumns;
    protected DatabaseConnect db;

    public BaseDAO(String table, List<String> tableColumns) {
        this.table = table;
        this.tableColumns = tableColumns;
        this.db = new DatabaseConnect();
    }

    // Lấy tất cả bản ghi
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + table;
        ResultSet rs = null;
        try {
            rs = db.getAll(sql, null);
            while (rs.next()) {
                T entity = mapResultSetToDTO(rs);
                list.add(entity);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                db.close(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // Tìm theo ID
    public Optional<T> findById(String column, int id) {
        String sql = "SELECT * FROM " + table + " WHERE " + column + " = ?";
        List<Object> params = Collections.singletonList(id);
        ResultSet rs = null;
        try {
            rs = db.getAll(sql, params);
            if (rs.next()) {
                T entity = mapResultSetToDTO(rs);
                return Optional.of(entity);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                db.close(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    // Kiểm tra tồn tại
    public boolean isExist(String column, int id) {
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE " + column + " = ?";
        List<Object> params = Collections.singletonList(id);
        ResultSet rs = null;
        try {
            rs = db.getAll(sql, params);
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                db.close(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Thêm bản ghi
    public boolean add(List<Object> params) {
        if (params == null || params.size() != tableColumns.size()) {
            return false;
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(table).append(" (");
        sql.append(String.join(",", tableColumns));
        sql.append(") VALUES (");
        sql.append(String.join(",", Collections.nCopies(params.size(), "?")));
        sql.append(")");
        return db.execute(sql.toString(), params);
    }

    // Sửa bản ghi (theo cột khóa chính)
    public boolean update(List<Object> params, String keyColumn, Object keyValue) {
        if (params == null || params.size() != tableColumns.size()) {
            return false;
        }
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(table).append(" SET ");
        List<String> setClauses = new ArrayList<>();
        for (String column : tableColumns) {
            setClauses.add(column + " = ?");
        }
        sql.append(String.join(",", setClauses));
        sql.append(" WHERE ").append(keyColumn).append(" = ?");
        List<Object> allParams = new ArrayList<>(params);
        allParams.add(keyValue);
        return db.execute(sql.toString(), allParams);
    }

    // Xóa bản ghi
    public boolean delete(String column, int id) {
        String sql = "DELETE FROM " + table + " WHERE " + column + " = ?";
        List<Object> params = Collections.singletonList(id);
        return db.execute(sql, params);
    }

    // Đóng kết nối
    public void closeConnection() {
        try {
            db.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức trừu tượng
    protected abstract T mapResultSetToDTO(ResultSet rs) throws SQLException;
}