package DAO;

import DTO.HoaDonDTO;

import java.sql.*;
import java.util.ArrayList;
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

    protected String getTableName() {
        return "hoadon";
    }

    protected List<String> getTableColumns() {
        return List.of("idHD", "ngaytao", "idTK");
    }


    @Override
    protected HoaDonDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new HoaDonDTO(
                rs.getInt(getTableColumns().get(0)),
                rs.getDate(getTableColumns().get(1)).toLocalDate(),
                rs.getInt(getTableColumns().get(2))
        );
    }

//    @Override
//    public List<Object> mapDTOToParams(HoaDonDTO hoaDonDTO) {
//        return List.of(
//                hoaDonDTO.getIdHD(),
//                hoaDonDTO.getNgaytao(),
//                hoaDonDTO.getIdTK()
//        );
//    }

    public boolean add(HoaDonDTO hoadon) {
        List<Object> params = new ArrayList<>();
        params.add(hoadon.getIdHD());
        params.add(hoadon.getNgaytao());
        params.add(hoadon.getIdTK());
        params.add(hoadon.getCTHoaDon());
        return super.add(params);
    }

    public HoaDonDTO findByID(int idHD) {
        Connection link = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            HoaDonDTO result = null;

            try {
                String sql = "SELECT * FROM hoadon WHERE idHD = ?";
                link = db.connectDB();
                pstmt = link.prepareStatement(sql);
                pstmt.setInt(1, idHD);
                rs = pstmt.executeQuery();
                if (rs.next()) result = mapResultSetToDTO(rs);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }

        return result;
    }

    public List<HoaDonDTO> searchByDate(Date start, Date end) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<HoaDonDTO> result = new ArrayList<>();
        List<Date> params = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM hoadon WHERE 1=1 ");
            if (start != null) {
                sql.append("AND (ngaytao >= ?) ");
                params.add(start);
            }
            if (end != null) {
                sql.append("AND (ngaytao <= ?) ");
                params.add(end);
            }

            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            for (int i=0; i<params.size(); i++) {
                pstmt.setDate(i+1, params.get(i));
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(mapResultSetToDTO(rs));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }

        return result;
    }

    public List<HoaDonDTO> searchByIdNV(int idTK) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<HoaDonDTO> result = new ArrayList<>();

        try {
            String sql = "SELECT * FROM hoadon WHERE idTK = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idTK);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(mapResultSetToDTO(rs));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }

        return result;
    }

    public List<HoaDonDTO> searchByTongTien(int giaBD, int giaKT) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<HoaDonDTO> result = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT h.* FROM hoadon h " +
                            "INNER JOIN CT_hoadon ct ON h.idHD = ct.idHD " +
                            "GROUP BY h.idHD, h.ngaytao, h.idTK"
            );
            List<Integer> params = new ArrayList<>();

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

            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());

            for (int i=0; i<params.size(); i++) {
                pstmt.setInt(i+1, params.get(i));
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(mapResultSetToDTO(rs));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }

        return result;
    }

}
