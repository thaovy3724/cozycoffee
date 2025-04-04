package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseDAO<T> implements IBaseDAO<T> {
    protected final DatabaseConnection db = new DatabaseConnection();

    @Override
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        ResultSet rs = db.getAll(sql, null);
        try {
            if (rs != null) {
                while (rs.next()) {
                    T entity = mapResultSetToDTO(rs);
                    list.add(entity);
                }
                return list;
            }
           return new ArrayList<>();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            rs.close();
        }
       return new ArrayList<>();
    }

    @Override
    public Optional<T> findById(String column, int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + column + " = ?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        ResultSet rs = db.getAll(sql, params);
        try {
            if (rs != null && rs.next()) {
                T entity = mapResultSetToDTO(rs);
                return Optional.ofNullable(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isExist(String column, int id) {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE " + column + " = ?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        DatabaseConnection.QueryResult qr = db.getOne(sql, params);
        try {
            if (qr.resultSet != null) {
                boolean exists = qr.resultSet.getInt(1) > 0;
                return exists;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            qr.close();
        }
        return false;
    }

    @Override
    public boolean add(List<Object> params) {

        //Đảm bảo phải có tham số truyền vào
        //và số lượng tham số bằng với số cột để tránh gọi truy vấn sql không cần thiết
        if (params == null || params.size() != getTableColumns().size()) {
            return false;
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");

        //Tên table
        sql.append(getTableName());
        sql.append(" (");
        //Danh sách cột của table
        sql.append(String.join(",", getTableColumns()));
        sql.append(") VALUES (");
        //Với mỗi tham số, thêm một character "?"
        sql.append(String.join(",", Collections.nCopies(params.size(), "?")));
        sql.append(")");

        return db.execute(sql.toString(), params);
    }

    @Override
    public void closeConnection() {
        db.close();
    }

    // *** Các phương thức trừu tượng mà lớp con phải triển khai
    protected abstract String getTableName();
    protected abstract List<String> getTableColumns();
    protected abstract T mapResultSetToDTO(ResultSet rs) throws SQLException;
    // ***
}
