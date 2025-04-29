package DAO;

import DTO.HoaDonDTO;
import DTO.PhieuNhapDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO extends BaseDAO{
    public PhieuNhapDAO() {
        super(
                "phieunhap",
                List.of(
                        "idPN",
                        "ngaytao",
                        "ngaycapnhat",
                        "idTK",
                        "idNCC",
                        "idTT"
                ));
    }

    @Override
    protected PhieuNhapDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new PhieuNhapDTO(
                rs.getInt("idPN"),
                rs.getDate("ngaytao"),
                rs.getDate("ngaycapnhat"),
                rs.getInt("idTK"),
                rs.getInt("idNCC"),
                rs.getInt("idTT")
        );
    }

    public long getTongTienByIDPN(int idPN) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long tongtien = 0;

        try {
            String sql = "SELECT sum(lnl.dongia * lnl.soluongnhap) AS tongtien " +
                    "FROM phieunhap pn " +
                    "INNER JOIN lo_nguyenlieu lnl on pn.idPN = lnl.idPN " +
                    "WHERE pn.idPN = ?";

            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idPN);
            rs = pstmt.executeQuery();
            if (rs.next()) tongtien = rs.getLong("tongtien");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close(link);
        }

        return tongtien;
    }

    public List<PhieuNhapDTO> searchByDate(Date start, Date end) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PhieuNhapDTO> result = new ArrayList<>();
        List<Date> params = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM phieunhap WHERE 1=1 ");
            if (start != null) {
                sql.append("AND (ngaycapnhat >= ?) ");
                params.add(start);
            }
            if (end != null) {
                sql.append("AND (ngaycapnhat <= ?) ");
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

}
