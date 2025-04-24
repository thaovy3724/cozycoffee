package DAO;

import DTO.DanhMucDTO;
import DTO.NhomQuyenDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NhomQuyenDAO extends BaseDAO<NhomQuyenDTO>{
	public NhomQuyenDAO() {
		super(
		"nhomquyen",
		List.of(
        "idNQ",
        "tenNQ"));
	}


    public NhomQuyenDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new NhomQuyenDTO(
                rs.getInt("idNQ"),
                rs.getString("tenNQ")
        );
    }

    public NhomQuyenDTO findByIdNQ(int idNQ) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        NhomQuyenDTO result = null;
        try {
            String sql = "SELECT * FROM " + table + " WHERE idNQ = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1,idNQ);
            rs = pstmt.executeQuery();
            if (rs.next()) result = mapResultSetToDTO(rs);
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}
}