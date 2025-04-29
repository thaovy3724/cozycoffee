package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import DTO.CT_HoaDonDTO;
import DTO.HoaDonDTO;

public class HoaDonDAO extends BaseDAO<HoaDonDTO>{
    public HoaDonDAO() {
		super(
		"hoadon", 
		List.of(
		 "idHD",
		 "ngaytao",
		 "idTK"
		));
	}

	@Override
	protected HoaDonDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new HoaDonDTO(
                rs.getInt("idHD"),
                rs.getDate("ngaytao"),
                rs.getInt("idTK")
        );
    }
	
	public int add(HoaDonDTO hoaDon, List<CT_HoaDonDTO> danhSachChiTiet) {
		Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		int newIdHD = -1;
		String sql;
		try{
			link = db.connectDB();
			// Bắt đầu transaction
			link.setAutoCommit(false);

			// Thêm hóa đơn
			sql = "INSERT INTO hoadon (ngaytao, idTK) VALUES (?, ?)";
			pstmt = link.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDate(1, Date.valueOf(hoaDon.getNgaytao()));
			pstmt.setInt(2, hoaDon.getIdTK());
			pstmt.executeUpdate();

			// Thêm chi tiết hóa đơn
			generatedKeys = pstmt.getGeneratedKeys();
			if(generatedKeys.next()) 
				newIdHD = generatedKeys.getInt(1);
			sql = "INSERT INTO ct_hoadon (idHD, idSP, soluong, gialucdat) VALUES (?, ?, ?, ?)";
			pstmt = link.prepareStatement(sql);
			for(CT_HoaDonDTO chitiet : danhSachChiTiet){
				pstmt.setInt(1, newIdHD);
				pstmt.setInt(2, chitiet.getIdSP());
				pstmt.setInt(3, chitiet.getSoluong());
				pstmt.setInt(4, chitiet.getGialucdat());
				pstmt.executeUpdate();
			}

			// Thành công
			link.commit();
		}catch(ClassNotFoundException | SQLException e){
			try{
				if(link != null) link.rollback();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			e.printStackTrace();
        }finally {
            db.close(link);
        }
		return newIdHD;
	}
}
