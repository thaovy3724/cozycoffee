package DAO;

import DTO.HoaDonDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

//Dùng Integer thay vì int là bởi Integer là kiểu đối tượng, còn int là kiểu nguyên thủy
//Các tham số kiểu (T, K, ...) trong generic chỉ chấp nhận các lớp hoặc interface
public class HoaDonDAO extends BaseDAO<HoaDonDTO> {

    public HoaDonDAO() {
        super(
                "hoadon",
                List.of("idHD", "ngaytao", "idTK")
        );
    }

//    @Override
//    protected String getTableName() {
//        return "hoadon";
//    }
//
//    @Override
//    protected List<String> getTableColumns() {
//        return List.of("idHD", "ngaytao", "idTK");
//    }


    @Override
    protected HoaDonDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new HoaDonDTO(
                rs.getInt("idHD"),
                rs.getDate("ngaytao").toLocalDate(),
                rs.getInt("idTK")
        );
    }

    @Override
    public List<Object> mapDTOToParams(HoaDonDTO hoaDonDTO) {
        return List.of(
                hoaDonDTO.getIdHD(),
                hoaDonDTO.getNgaytao(),
                hoaDonDTO.getIdTK()
        );
    }

    public List<HoaDonDTO> searchByDate(Date start, Date end) {
        DatabaseConnection db = new DatabaseConnection();
        List<HoaDonDTO> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM hoadon WHERE 1=1 ");
        if (start != null) {
            sql.append("AND (ngaytao >= ?) ");
            params.add(start);
        }
        if (end != null) {
            sql.append("AND (ngaytao <= ?) ");
            params.add(end);
        }

        try {
            db.prepareStatement(sql.toString());
            try (ResultSet rs = db.getAll(params);) {
                while (rs.next()) {
                    HoaDonDTO hd = mapResultSetToDTO(rs);
                    list.add(hd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return list;
    }

    public List<HoaDonDTO> searchByIdNV(int idTK) {
        DatabaseConnection db = new DatabaseConnection();
        List<HoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM hoadon WHERE idTK = ?";
        List<Object> params = new ArrayList<>();
        params.add(idTK);

        try {
            db.prepareStatement(sql);
            try ( ResultSet rs = db.getAll(params)) {
                while (rs.next()) {
                    HoaDonDTO hd = mapResultSetToDTO(rs);
                    list.add(hd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return list;
    }

    public List<HoaDonDTO> searchByTongTien(int giaBD, int giaKT) {
        DatabaseConnection db = new DatabaseConnection();
        List<HoaDonDTO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT h.* FROM hoadon h " +
                        "INNER JOIN CT_hoadon ct ON h.idHD = ct.idHD " +
                        "GROUP BY h.idHD, h.ngaytao, h.idTK"
        );
        List<Object> params = new ArrayList<>();

        boolean hasGiaBD = giaBD != 0;
        boolean hasGiaKT = giaKT != 0;

        if (hasGiaBD || hasGiaKT) {
            sql.append(" HAVING ");
            if (hasGiaBD) {
                sql.append("SUM(ct.soluong * ct.gialucdat) >= ?");
                params.add(giaBD);
            }
            if (hasGiaKT) {
                if (hasGiaBD) {
                    sql.append(" AND ");
                }
                sql.append("SUM(ct.soluong * ct.gialucdat) <= ?");
                params.add(giaKT);
            }
        }

        try {
            db.prepareStatement(sql.toString());
            try (ResultSet rs = db.getAll(params)) {
                while (rs != null && rs.next()) {
                    HoaDonDTO hd = mapResultSetToDTO(rs);
                    list.add(hd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return list;
    }

}
