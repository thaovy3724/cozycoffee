package DAO;

import DTO.Lo_NguyenLieuDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lo_NguyenLieuDAO extends BaseDAO<Lo_NguyenLieuDTO> {
    public Lo_NguyenLieuDAO() {
        super(
                "lo_nguyenlieu",
                List.of(
                        "idNL",
                        "idPN",
                        "soluongnhap",
                        "tonkho",
                        "dongia",
                        "hsd"
                ));
    }

    @Override
    protected Lo_NguyenLieuDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new Lo_NguyenLieuDTO(
                rs.getInt("idNL"),
                rs.getInt("idPN"),
                rs.getFloat("soluongnhap"),
                rs.getFloat("tonkho"),
                rs.getInt("dongia"),
                rs.getDate("hsd").toLocalDate()
        );
    }

    public List<Lo_NguyenLieuDTO> getAllByIDPN(int idPN) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Lo_NguyenLieuDTO> result = new ArrayList<>();

        try {
            String sql = "SELECT * FROM " + table + " WHERE idPN = ?";

            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            pstmt.setInt(1, idPN);
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

    public List<Lo_NguyenLieuDTO> getAllByDate(java.sql.Date start, java.sql.Date end) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Lo_NguyenLieuDTO> result = new ArrayList<>();
        List<java.sql.Date> params = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT * FROM lo_nguyenlieu lnl " +
                    "INNER JOIN phieunhap pn ON lnl.idPN = pn.idPN" +
                    " WHERE 1=1 "
            );
            if (start != null) {
                sql.append("AND (pn.ngaycapnhat >= ?) ");
                params.add(start);
            }
            if (end != null) {
                sql.append("AND (pn.ngaycapnhat <= ?) ");
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
