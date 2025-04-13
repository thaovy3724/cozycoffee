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
        "email",
        "trangthai",
        "idNQ"
    	)
        );
    }
    
    protected TaiKhoanDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
    	return new TaiKhoanDTO(
                rs.getInt("idTK"),
                rs.getString("tenTK"),
                rs.getString("matkhau"),
                rs.getString("email"),
                rs.getInt("trangthai"),
                rs.getInt("idNQ")
    			);
    }

//    @Override
//    public List<Object> mapDTOToParams(TaiKhoanDTO taiKhoanDTO) {
//        return List.of(
//                taiKhoanDTO.getIdTK(),
//                taiKhoanDTO.getTenTK(),
//                taiKhoanDTO.getMatkhau(),
//                taiKhoanDTO.getEmail(),
//                taiKhoanDTO.getTrangthai(),
//                taiKhoanDTO.getIdNQ()
//        );
//    }

//    public List<TaiKhoanDTO> getAllByIDNQ(int idNQ) {
//        DatabaseConnection db = new DatabaseConnection();
//        StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName + " WHERE idNQ = ?");
//        List<Object> params = new ArrayList<>();
//        params.add(idNQ);
//        List<TaiKhoanDTO> list = new ArrayList<>();
//        try {
//            db.prepareStatement(sql.toString());
//            try (ResultSet rs = db.getAll(params)) {
//                while (rs.next()) {
//                    TaiKhoanDTO item = mapResultSetToDTO(rs);
//                    list.add(item);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            db.close();
//        }
//
//        return list;
//    }
//
//    // Tìm kiếm tài khoản theo từ khóa
//    public List<TaiKhoanDTO> search(String kyw) {
//        DatabaseConnection db = new DatabaseConnection();
//        List<TaiKhoanDTO> list = new ArrayList<>();
//        List<Object> params = new ArrayList<>();
//        StringBuilder sql = new StringBuilder("SELECT * FROM "+getTableName()+" WHERE 1=1 ");
//
//        if (!kyw.isEmpty()) {
//            sql.append("AND (tenTK LIKE ? OR hoten LIKE ? OR email LIKE ? OR dienthoai LIKE ?) ");
//            String pattern = "%" + kyw + "%";
//            for (int i = 0; i < 4; i++) {
//                params.add(pattern);
//            }
//        }
//
//        try {
//            db.prepareStatement(sql.toString());
//            try (ResultSet rs = db.getAll(params)) {
//                while (rs.next()) {
//                    TaiKhoanDTO tk = mapResultSetToDTO(rs);
//                    list.add(tk);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            db.close();
//        }
//
//        return list;
//    }
//
//    // Cập nhật tài khoản
//    public boolean update(TaiKhoanDTO taiKhoan) {
//        DatabaseConnection db = new DatabaseConnection();
//        /*
//        * .stream(): phân rã List thành một luồng dữ liệu để thao tác chức năng như lọc, gộp, biến đổi, ...
//        * .filter(): lọc qua luồng dữ liệu dựa trên điều kiện được đưa vào
//        * .collect(): thu thập dữ liệu từ luồng dữ sau khi đã được phân rã thành một List mới
//        * */
//        List<String> columns = getTableColumns().stream()
//                .filter(column -> !column.equals("idTK")) //Chỉ lấy những cột khác "idTK"
//                .filter(column -> {
//                    // Nếu là "matkhau" thì chỉ giữ lại khi giá trị không rỗng
//                    if (column.equals("matkhau")) {
//                        return taiKhoan.getMatkhau() != null && !taiKhoan.getMatkhau().isEmpty();
//                    }
//                    return true; // Các cột khác giữ lại bình thường
//                })
//                .collect(Collectors.toList());
//
//        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " " +
//                "SET ");
//        for (int i = 0; i < columns.size(); i++) {
//            sql.append(columns.get(i) + " = COALESCE(?, "+columns.get(i)+")");
//            if (i < columns.size() - 1) {
//                sql.append(", ");
//            }
//        }
//        sql.append(" WHERE idTK = ?");
//        List<Object> params = new ArrayList<>();
//        for (String col : columns) {
//            switch (col) {
//                case "tenTK": params.add(taiKhoan.getTenTK()); break;
//                case "matkhau":
//                    params.add(taiKhoan.getMatkhau());
//                    break;
//                case "hoten": params.add(taiKhoan.getHoten()); break;
//                case "email": params.add(taiKhoan.getEmail()); break;
//                case "dienthoai": params.add(taiKhoan.getDienthoai()); break;
//                case "trangthai": params.add(taiKhoan.getTrangthai()); break;
//                case "idNQ": params.add(taiKhoan.getIdNQ()); break;
//            }
//        }
//        params.add(taiKhoan.getIdTK()); //Tham số truyền vào WHERE
//
//        try {
//            db.prepareStatement(sql.toString());
//            return db.execute(params);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            db.close();
//        }
//
//        return false;
//    }
//
//    // Khóa tài khoản (trangthai = false)
//    public boolean lock(TaiKhoanDTO taikhoanDTO) {
//        DatabaseConnection db = new DatabaseConnection();
//        String sql = "UPDATE taikhoan SET trangthai = ? WHERE idTK = ?";
//        List<Object> params = new ArrayList<>();
//        params.add(false);
//        params.add(taikhoanDTO.getIdTK());
//
//        try {
//            db.prepareStatement(sql);
//            return db.execute(params);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            db.close();
//        }
//
//        return false;
//    }
//
//    // Mở khóa tài khoản (trangthai = true)
//    public boolean unlock(TaiKhoanDTO taiKhoanDTO) {
//        DatabaseConnection db = new DatabaseConnection();
//        String sql = "UPDATE taikhoan SET trangthai = ? WHERE idTK = ?";
//        List<Object> params = new ArrayList<>();
//        params.add(true);
//        params.add(taiKhoanDTO.getIdTK());
//
//        try {
//            db.prepareStatement(sql);
//            return db.execute(params);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            db.close();
//        }
//
//        return false;
//    }
//
//    public TaiKhoanDTO checkLogin(String tenTK, String matKhau) {
//        DatabaseConnection db = new DatabaseConnection();
//        StringBuilder sql = new StringBuilder(
//                "SELECT * FROM " + getTableName() + " WHERE tenTK = ? AND matkhau = ?"
//        );
//        List<Object> params = new ArrayList<>();
//        params.add(tenTK);
//        params.add(matKhau);
//
//        try {
//            db.prepareStatement(sql.toString());
//            try (ResultSet rs = db.getAll(params)) {
//                if (rs.next()) {
//                    return mapResultSetToDTO(rs);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            db.close();
//        }
//
//        return null;
//    }
}