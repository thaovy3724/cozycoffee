package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.Lo_NguyenLieuDTO;

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
    public Lo_NguyenLieuDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new Lo_NguyenLieuDTO(
            rs.getInt("idNL"), 
            rs.getInt("idPN"), 
            rs.getFloat("soluongnhap"), 
            rs.getFloat("tonkho"), 
            rs.getInt("dongia"), 
            rs.getDate("hsd")
        );
    }

    public List<Lo_NguyenLieuDTO> getChiTietPhieuNhap(int idPN) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Lo_NguyenLieuDTO> result = new ArrayList<>();

        try {
            String sql = "SELECT * FROM lo_nguyenlieu WHERE idPN = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idPN);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(mapResultSetToDTO(rs));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
				try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            db.close(link);
        }
        return result;
    }

    public boolean delete(int idPN) {
        String col = "idPN";
        return super.delete(col, idPN);
    }
}
