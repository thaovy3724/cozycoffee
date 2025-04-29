package DAO;

import java.util.ArrayList;
import DTO.CT_HoaDonDTO;
import java.util.List;
import java.sql.*;

public class CT_HoaDonDAO extends BaseDAO<CT_HoaDonDTO>{
	public CT_HoaDonDAO() {
		super(
		"ct_hoadon", 
		List.of(
		 "idSP",
		 "idHD",
		 "soluong",
		 "gialucdat"
		));
	}

	@Override
	protected CT_HoaDonDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new CT_HoaDonDTO(
                rs.getInt("idSP"),
                rs.getInt("idHD"),
                rs.getInt("soluong"),
                rs.getInt("gialucdat")
        );
    }
	
	public List<CT_HoaDonDTO> getAllByIdHD(int idHD) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CT_HoaDonDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE idHD = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1,idHD);
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}
}
