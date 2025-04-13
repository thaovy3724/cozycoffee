package DAO;

import DTO.NhomQuyenDTO;
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
}