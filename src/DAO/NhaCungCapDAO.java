package DAO;

import java.util.ArrayList;

import DTO.NhaCungCapDTO;
import java.util.List;
import java.sql.*;

public class NhaCungCapDAO extends BaseDAO<NhaCungCapDTO>{
	public NhaCungCapDAO() {
		super(
		"nhacungcap", 
		List.of(
		 "idNCC",
		 "tenNCC",
         "diachi",
         "sdt",
         "email",
		 "trangthai"
		));
	}

	@Override
	protected NhaCungCapDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new NhaCungCapDTO(
                rs.getInt("idNCC"),
                rs.getString("tenNCC"),
                rs.getString("diachi"),
                rs.getString("sdt"),
                rs.getString("email"),
                rs.getInt("trangthai")
        );
    }
	
	public boolean add(NhaCungCapDTO ncc) {
		List<Object> params = new ArrayList<>();
		params.add(ncc.getIdNCC());
		params.add(ncc.getTenNCC());
		params.add(ncc.getDiachi());
		params.add(ncc.getSdt());
		params.add(ncc.getEmail());
		params.add(ncc.getTrangthai());
		return super.add(params);
	}
	
	public boolean update(NhaCungCapDTO ncc) {
		List<Object> params = new ArrayList<>();
		params.add(ncc.getIdNCC());
		params.add(ncc.getTenNCC());
		params.add(ncc.getDiachi());
		params.add(ncc.getSdt());
		params.add(ncc.getEmail());
		params.add(ncc.getTrangthai());
		String condition = "idNCC = "+ncc.getIdNCC();
		return super.update(params, condition);
	}
	
    public boolean isExist(NhaCungCapDTO ncc) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isExist = false;
        try {
			StringBuilder sql = new StringBuilder("SELECT * FROM ");
			sql.append(table);
			sql.append(" WHERE tenNCC LIKE ?");
			sql.append(" OR email = ?");
			sql.append(" OR sdt = ?");
			if(ncc.getIdNCC() != 0) 
				sql.append(" AND idNCC != ?");
           
            // noi param
            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            pstmt.setString(1, ncc.getTenNCC());
            pstmt.setString(2, ncc.getEmail());
            pstmt.setString(3, ncc.getSdt());
			if(ncc.getIdNCC() != 0) 
	            pstmt.setInt(4, ncc.getIdNCC());
			
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

    public boolean isSupplierInInvoice(int idNCC){
        Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean inInvoice = false;
        try {
            String sql = "SELECT * FROM phieunhap WHERE idNCC = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idNCC);
            rs = pstmt.executeQuery();
            inInvoice = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return inInvoice;
    }
	
	public boolean delete(int idNCC) {
		String col = "idNCC";
		return super.delete(col, idNCC);
    }
	
	public List<NhaCungCapDTO> search(String keyWord){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		List<NhaCungCapDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE idNCC LIKE ? OR tenNCC LIKE ?";
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
	
	public NhaCungCapDTO findByIdNCC(int idNCC) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        NhaCungCapDTO result = null;
        try {
            String sql = "SELECT * FROM " + table + " WHERE idNCC = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1,idNCC);
            rs = pstmt.executeQuery();
            if (rs.next()) result = mapResultSetToDTO(rs);
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}

    public boolean exists(int idNCC) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            String sql = "SELECT * FROM " + table + " WHERE idNCC = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idNCC);
            rs = pstmt.executeQuery();
            exists = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }
        return exists;
    }

    // HUONGNGUYEN 29/4
    public List<NhaCungCapDTO> getAllActive() {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<NhaCungCapDTO> result = new ArrayList<>();

        try {
            String sql = "SELECT * FROM " + table + " WHERE trangthai = 1";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                result.add(mapResultSetToDTO(rs));
            }
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }
        return result;
    }

//    HUONGNGUYEN 2/5
    public List<NhaCungCapDTO> getAllActiveExcept(int idNCC) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<NhaCungCapDTO> result = new ArrayList<>();

        try {
            link = db.connectDB();
            String sql = "SELECT * FROM " + table + " WHERE idNCC = ? OR " +
                    "trangthai = 1";
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idNCC);
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
