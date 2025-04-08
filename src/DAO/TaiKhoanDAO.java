package DAO;

import DTO.TaiKhoanDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                rs.getInt("idNQ")
        );
    }

    @Override
    public List<Object> mapDTOToParams(TaiKhoanDTO taiKhoanDTO) {
        return List.of(
                taiKhoanDTO.getIdTK(),
                taiKhoanDTO.getTenTK(),
                taiKhoanDTO.getMatkhau(),
                taiKhoanDTO.getHoten(),
                taiKhoanDTO.getEmail(),
                taiKhoanDTO.getDienthoai(),
                taiKhoanDTO.getTrangthai(),
                taiKhoanDTO.getIdNQ()
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

        try {
            db.prepareStatement(sql.toString());
            try (ResultSet rs = db.getAll(params)) {
                while (rs.next()) {
                    TaiKhoanDTO tk = mapResultSetToDTO(rs);
                    list.add(tk);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }

        return list;
    }

    // Cập nhật tài khoản
    public boolean update(TaiKhoanDTO taiKhoan) {
        DatabaseConnection db = new DatabaseConnection();
        /*
        * .stream(): phân rã List thành một luồng dữ liệu để thao tác chức năng như lọc, gộp, biến đổi, ...
        * .filter(): lọc qua luồng dữ liệu dựa trên điều kiện được đưa vào
        * .collect(): thu thập dữ liệu từ luồng dữ sau khi đã được phân rã thành một List mới
        * */
        List<String> columns = getTableColumns().stream()
                .filter(column -> !column.equals("idTK")) //Chỉ lấy những cột khác "idTK"
                .filter(column -> {
                    // Nếu là "matkhau" thì chỉ giữ lại khi giá trị không rỗng
                    if (column.equals("matkhau")) {
                        return taiKhoan.getMatkhau() != null && !taiKhoan.getMatkhau().isEmpty();
                    }
                    return true; // Các cột khác giữ lại bình thường
                })
                .collect(Collectors.toList());

        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " " +
                "SET ");
        for (int i = 0; i < columns.size(); i++) {
            sql.append(columns.get(i) + " = COALESCE(?, "+columns.get(i)+")");
            if (i < columns.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE idTK = ?");
        List<Object> params = new ArrayList<>();
        for (String col : columns) {
            switch (col) {
                case "tenTK": params.add(taiKhoan.getTenTK()); break;
                case "matkhau":
                    params.add(taiKhoan.getMatkhau());
                    break;
                case "hoten": params.add(taiKhoan.getHoten()); break;
                case "email": params.add(taiKhoan.getEmail()); break;
                case "dienthoai": params.add(taiKhoan.getDienthoai()); break;
                case "trangthai": params.add(taiKhoan.getTrangthai()); break;
                case "idNQ": params.add(taiKhoan.getIdNQ()); break;
            }
        }
        params.add(taiKhoan.getIdTK()); //Tham số truyền vào WHERE

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
    public boolean lock(TaiKhoanDTO taikhoanDTO) {
        DatabaseConnection db = new DatabaseConnection();
        String sql = "UPDATE taikhoan SET trangthai = ? WHERE idTK = ?";
        List<Object> params = new ArrayList<>();
        params.add(false);
        params.add(taikhoanDTO.getIdTK());

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
    public boolean unlock(TaiKhoanDTO taiKhoanDTO) {
        DatabaseConnection db = new DatabaseConnection();
        String sql = "UPDATE taikhoan SET trangthai = ? WHERE idTK = ?";
        List<Object> params = new ArrayList<>();
        params.add(true);
        params.add(taiKhoanDTO.getIdTK());

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
            rs = db.getAll(params);
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
