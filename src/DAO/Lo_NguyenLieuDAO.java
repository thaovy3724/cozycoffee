package DAO;

import java.util.ArrayList;
import DTO.Lo_NguyenLieuDTO;

import java.util.List;
import java.sql.*;

public class Lo_NguyenLieuDAO extends BaseDAO<Lo_NguyenLieuDTO>{
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
                rs.getInt("soluongnhap"),
                rs.getInt("tonkho"),
                rs.getInt("dongia"),
                rs.getDate("hsd")
        );
    }
	
	public List<Lo_NguyenLieuDTO> getAllByIdPN(int idPN) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Lo_NguyenLieuDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE idPN = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1,idPN);
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}

    public boolean delete(int idPN) {
        String col = "idPN";
        return super.delete(col, idPN);
    }
}
