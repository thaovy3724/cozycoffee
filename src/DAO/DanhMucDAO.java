package DAO;

import java.util.ArrayList;
import DTO.DanhMucDTO;
import java.util.List;
import java.sql.*;

public class DanhMucDAO extends BaseDAO<DanhMucDTO>{
	public DanhMucDAO() {
		super(
		"danhmuc", 
		List.of(
		 "idDM",
		 "tenDM",
		 "trangthai",
		 "idDMCha"
		));
	}

	@Override
	protected DanhMucDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new DanhMucDTO(
                rs.getInt("idDM"),
                rs.getString("tenDM"),
                rs.getInt("trangthai"),
                rs.getInt("idDMCha")
        );
    }
	
	public boolean add(DanhMucDTO danhMuc) {
		List<Object> params = new ArrayList<>();
		params.add(danhMuc.getIdDM());
		params.add(danhMuc.getTenDM());
		params.add(danhMuc.getTrangthai());
		params.add(danhMuc.getIdDMCha() == 0 ? null : danhMuc.getIdDMCha());
		return super.add(params);
	}
	
	public boolean update(DanhMucDTO danhMuc) {
		List<Object> params = new ArrayList<>();
		params.add(danhMuc.getIdDM());
		params.add(danhMuc.getTenDM());
		params.add(danhMuc.getTrangthai());
		params.add(danhMuc.getIdDMCha()== 0 ? null : danhMuc.getIdDMCha());
		String condition = "idDM = "+danhMuc.getIdDM();
		return super.update(params, condition);
	}
	
	public boolean isParentCategory(int idDM) {
    	Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isParent = false;
        try {
            String sql = "SELECT * FROM " + table + " WHERE idDMCha = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idDM);
            rs = pstmt.executeQuery();
            isParent = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return isParent;
	}
	
	public boolean hasProductInCategory(int idDM) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean hasProduct = false;
        try {
            String sql = "SELECT * FROM sanpham WHERE idDM = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idDM);
            rs = pstmt.executeQuery();
            hasProduct = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return hasProduct;
	}
	
	public boolean delete(int idDM) {
		String col = "idDM";
		return super.delete(col, idDM);
    }
	
	public List<DanhMucDTO> search(String keyWord){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		List<DanhMucDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE idDM LIKE ? OR tenDM LIKE ?";
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
	
	public DanhMucDTO findByIdDM(int idDM) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        DanhMucDTO result = null;
        try {
            String sql = "SELECT * FROM " + table + " WHERE idDM = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1,idDM);
            rs = pstmt.executeQuery();
            if (rs.next()) result = mapResultSetToDTO(rs);
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}
	
	public boolean isExist(DanhMucDTO danhMuc) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isExist = false;
        try {
			StringBuilder sql = new StringBuilder("SELECT * FROM ");
			sql.append(table);
			sql.append(" WHERE tenDM LIKE ?");
			if(danhMuc.getIdDM() != 0) 
				sql.append(" AND idDM != ?");
           
            // noi param
            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            pstmt.setString(1, danhMuc.getTenDM());
			if(danhMuc.getIdDM() != 0) 
	            pstmt.setInt(2, danhMuc.getIdDM());
			
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
	
	public List<DanhMucDTO> getAllActiveEdit(int idDMCon, int idDMCha){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		List<DanhMucDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table
            		+ " WHERE idDM = ? "
            		+ " OR(idDM != ? AND trangthai = 1)";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idDMCha);
            pstmt.setInt(2, idDMCon);
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}
	
	public List<DanhMucDTO> getAllActive(){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		List<DanhMucDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE trangthai = 1";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}

    public List<DanhMucDTO> getAllActiveExcept(int idDM){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		List<DanhMucDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table
            		+ " WHERE idDM != ? AND trangthai = 1)";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idDM);
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}
}
