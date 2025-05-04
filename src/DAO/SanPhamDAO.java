package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.SanPhamDTO;

public class SanPhamDAO extends BaseDAO<SanPhamDTO>{
	public SanPhamDAO() {
		super(
		"sanpham",
		List.of(
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

    public List<SanPhamDTO> getAllActive() {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<SanPhamDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM "+table+" WHERE trangthai = 1";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
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

	public boolean add(SanPhamDTO sp) {
		List<Object> params = new ArrayList<>();
		params.add(sp.getTenSP());
		params.add(sp.getGiaban());
		params.add(sp.getHinhanh());
		params.add(sp.getTrangthai());
		params.add(sp.getIdDM());
		return super.add(params);
	}

	public boolean update(SanPhamDTO sp) {
		List<Object> params = new ArrayList<>();
		params.add(sp.getTenSP());
		params.add(sp.getGiaban());
		params.add(sp.getHinhanh());
		params.add(sp.getTrangthai());
		params.add(sp.getIdDM());
		String condition = "idSP = "+sp.getIdSP();
		return super.update(params, condition);
	}

    public boolean updateNotImage(SanPhamDTO sp){
        Connection link = null;
		PreparedStatement pstmt = null;
		boolean success = false;
		try {
			// tao sql

            //Tên table
            String sql = "UPDATE " + table +
                    " SET" +
                    " tenSP = ?," +
                    " giaban = ?," +
                    " trangthai = ?," +
                    " idDM = ?" +
                    " WHERE idSP = ?";

            // noi param
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);

            pstmt.setString(1, sp.getTenSP());
            pstmt.setInt(2, sp.getGiaban());
            pstmt.setInt(3, sp.getTrangthai());
            pstmt.setInt(4, sp.getIdDM());
            pstmt.setInt(5, sp.getIdSP());

            // thuc thi
            success = pstmt.executeUpdate() > 0;

		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			db.close(link);
		}
		return success;
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
			if(sp.getIdSP() != 0) {
				sql.append(" AND idSP != ?");
			}

            // noi param
            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            pstmt.setString(1, sp.getTenSP());
			if(sp.getIdSP() != 0) {
				pstmt.setInt(2, sp.getIdSP());
			}

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
            while (rs.next()) {
				result.add(mapResultSetToDTO(rs));
			}
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}

    public List<SanPhamDTO> searchByKeyWord(String keyWord){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		List<SanPhamDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE (idSP LIKE ? OR tenSP LIKE ?) AND trangthai = 1";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setString(1, "%" + keyWord + "%");
            pstmt.setString(2, "%" + keyWord + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
				result.add(mapResultSetToDTO(rs));
			}
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}

    public List<SanPhamDTO> searchByCategory(int idDM){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		List<SanPhamDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE idDM = ? AND trangthai = 1";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idDM);
            rs = pstmt.executeQuery();
            while (rs.next()) {
				result.add(mapResultSetToDTO(rs));
			}
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}

    public List<SanPhamDTO> search(String keyWord, int idDM){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		List<SanPhamDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE (idSP LIKE ? OR tenSP LIKE ?) AND idDM = ? AND trangthai = 1";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setString(1, "%" + keyWord + "%");
            pstmt.setString(2, "%" + keyWord + "%");
            pstmt.setInt(3, idDM);
            rs = pstmt.executeQuery();
            while (rs.next()) {
				result.add(mapResultSetToDTO(rs));
			}
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
            if (rs.next()) {
				result = mapResultSetToDTO(rs);
			}
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}
}
