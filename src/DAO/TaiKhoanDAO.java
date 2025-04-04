package DAO;

import DTO.TaiKhoanDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO extends BaseDAO<TaiKhoanDTO> {
    public TaiKhoanDAO() {
        super(
                "taikhoan",
                List.of(
                        "idTK",
                        "tenTK",
                        "matkhau",
                        "hoten",
                        "email",
                        "dienthoai",
                        "trangthai"
                        , "idNQ"
                )
        );
    }

    protected String getTableName() {
        return "taikhoan";
    }

    protected List<String> getTableColumns() {
        return List.of("idTK", "tenTK", "matkhau", "hoten", "email", "dienthoai", "trangthai", "idNQ");
    }

    protected TaiKhoanDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new TaiKhoanDTO(
                rs.getInt("idTK"),
                rs.getString("tenTK"),
                rs.getString("matkhau"),
                rs.getString("hoten"),
                rs.getString("email"),
                rs.getString("dienthoai"),
                rs.getBoolean("trangthai"),
                rs.getString("idNQ")
        );
    }

    // Tìm kiếm tài khoản theo từ khóa
    public List<TaiKhoanDTO> search(String kyw) {
        DatabaseConnection db = new DatabaseConnection();
        List<TaiKhoanDTO> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM "+getTableName()+" WHERE 1=1 ");

        if (!kyw.isEmpty()) {
            sql.append("AND (tenTK LIKE ? OR hoten LIKE ? OR email LIKE ? OR dienthoai LIKE ?) ");
            String pattern = "%" + kyw + "%";
            for (int i = 0; i< 4; i++) {
                params.add(pattern);
            }
        }

        ResultSet rs = null;

        try {
            db.prepareStatement(sql.toString());
            rs = db.getAll(params);
            if (rs != null) {
                while (rs.next()) {
                    TaiKhoanDTO tk = mapResultSetToDTO(rs);
                    list.add(tk);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.close();
        }

        return list.isEmpty() ? null : list;
    }

    // Cập nhật tài khoản
    public boolean update(TaiKhoanDTO taiKhoan) {
        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " " +
                "SET ");
        for (int i = 0; i < getTableColumns().size(); i++) {
            sql.append(getTableColumns().get(i)).append(" = ?");
            if (i < getTableColumns().size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE idTK = ?");
        List<Object> params = List.of(
                taiKhoan.getTenTK(),
                taiKhoan.getMatkhau(),
                taiKhoan.getHoten(),
                taiKhoan.getEmail(),
                taiKhoan.getDienthoai(),
                taiKhoan.getTrangthai(),
                taiKhoan.getIdNQ(),
                taiKhoan.getIdTK() // Giá trị cho WHERE
        );

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

    // Khóa tài khoản (trangthai = false)
    public boolean lock(int idTK) {
        DatabaseConnection db = new DatabaseConnection();
        String sql = "UPDATE taikhoan SET trangthai = ? WHERE idTK = ?";
        List<Object> params = new ArrayList<>();
        params.add(false);
        params.add(idTK);

        try {
            db.prepareStatement(sql);
            return db.execute(params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false;
    }

    // Mở khóa tài khoản (trangthai = true)
    public boolean unlock(int idTK) {
        DatabaseConnection db = new DatabaseConnection();
        String sql = "UPDATE taikhoan SET trangthai = ? WHERE idTK = ?";
        List<Object> params = new ArrayList<>();
        params.add(true);
        params.add(idTK);

        try {
            db.prepareStatement(sql);
            return db.execute(params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false;
    }

    public TaiKhoanDTO checkLogin(String tenTK, String matKhau) {
        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM " + getTableName() + " WHERE tenTK = ? AND matkhau = ?"
        );
        List<Object> params = new ArrayList<>();
        params.add(tenTK);
        params.add(matKhau);

        ResultSet rs = null;
        try {
            db.prepareStatement(sql.toString());
            db.getAll(params);
            if (rs != null && rs.next()) {
                return mapResultSetToDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.close();
        }

        return null;
    }
}
