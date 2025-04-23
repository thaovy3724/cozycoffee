package DAO;

import java.util.ArrayList;

import DTO.SanPhamDTO;
import java.util.List;
import java.sql.*;

public class SanPhamDAO extends BaseDAO<SanPhamDTO>{
	public SanPhamDAO() {
		super(
		"sanpham", 
		List.of(
		 "idSP",
		 "tenSP",
         "giaban",
         "hinhanh",
         "trangthai",
		 "idDM"
		));
	}

	@Override
	protected SanPhamDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new SanPhamDTO(
                rs.getInt("idSP"),
                rs.getString("tenSP"),
                rs.getInt("giaban"),
                rs.getString("hinhanh"),
                rs.getInt("trangthai"),
                rs.getInt("idDM")
        );
    }
	
	public boolean add(SanPhamDTO sp) {
		List<Object> params = new ArrayList<>();
		params.add(sp.getIdSP());
		params.add(sp.getTenSP());
		params.add(sp.getGiaban());
		params.add(sp.getHinhanh());
		params.add(sp.getTrangthai());
		params.add(sp.getIdDM());
		return super.add(params);
	}
	
	public boolean update(SanPhamDTO sp) {
		List<Object> params = new ArrayList<>();
		params.add(sp.getIdSP());
		params.add(sp.getTenSP());
		params.add(sp.getGiaban());
		params.add(sp.getHinhanh());
		params.add(sp.getTrangthai());
		params.add(sp.getIdDM());
		String condition = "idSP = "+sp.getIdSP();
		return super.update(params, condition);
	}
	
    public boolean isExist(SanPhamDTO sp) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isExist = false;
        try {
			StringBuilder sql = new StringBuilder("SELECT * FROM ");
			sql.append(table);
			sql.append(" WHERE tenSP LIKE ?");
			if(sp.getIdSP() != 0) 
				sql.append(" AND idSP != ?");
           
            // noi param
            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            pstmt.setString(1, sp.getTenSP());
			if(sp.getIdSP() != 0) 
	            pstmt.setInt(2, sp.getIdSP());
			
			// thuc thi
            rs = pstmt.executeQuery();
            isExist = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return isExist;
	}

    public boolean isProductInRecipe(int idSP){
        Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean inRecipe = false;
        try {
            String sql = "SELECT * FROM congthuc WHERE idSP = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idSP);
            rs = pstmt.executeQuery();
            inRecipe = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return inRecipe;
    }

    public boolean isProductInReceipt(int idSP){
        Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean inRecipe = false;
        try {
            String sql = "SELECT * FROM ct_hoadon WHERE idSP = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idSP);
            rs = pstmt.executeQuery();
            inRecipe = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return inRecipe;
    }
	
	public boolean delete(int idSP) {
		String col = "idSP";
		return super.delete(col, idSP);
    }
	
	public List<SanPhamDTO> search(String keyWord){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		List<SanPhamDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE idSP LIKE ? OR tenSP LIKE ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setString(1, "%" + keyWord + "%");
            pstmt.setString(2, "%" + keyWord + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}
	
	public SanPhamDTO findByIdSP(int idSP) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        SanPhamDTO result = null;
        try {
            String sql = "SELECT * FROM " + table + " WHERE idSP = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1,idSP);
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
