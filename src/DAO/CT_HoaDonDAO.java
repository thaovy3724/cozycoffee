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

    public int quantityAvailable(CT_HoaDonDTO ct){
        int quantityAvailable = 0;
        Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Tính tổng tồn kho khả dụng cho từng idNL
            // Tính số sản phẩm tối đa cho từng nguyên liệu
            String sql = 
            "WITH TonKhoTheoHSD AS (" +
            " SELECT lo.idNL, SUM(lo.tonkho) AS tonkho_kha_dung" +
            " FROM lo_nguyenlieu lo" +
            " GROUP BY lo.idNL)," +
            " SoLuongSanPhamToiDa AS ("+
            " SELECT FLOOR(COALESCE(tkh.tonkho_kha_dung, 0) / ct.soluong) AS so_san_pham_toi_da" +
            " FROM congthuc c" +
                " JOIN ct_congthuc ct ON c.idct = ct.idct" +
                " LEFT JOIN TonKhoTheoHSD tkh ON ct.idNL = tkh.idNL" +
            " WHERE c.idSP = ?)" +
            " SELECT" +
                " CASE" +
                    " WHEN MIN(so_san_pham_toi_da) IS NULL THEN 0" +
                    " ELSE MIN(so_san_pham_toi_da)" +
                " END AS so_luong_san_pham_available" +
            " FROM SoLuongSanPhamToiDa";

            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1,ct.getSoluong());
            pstmt.setInt(2,ct.getIdSP());
            rs = pstmt.executeQuery();
            while (rs.next()) quantityAvailable = rs.getInt("so_luong_san_pham_available");
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return quantityAvailable;
    }
}
