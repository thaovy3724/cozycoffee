package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.TaiKhoanDTO;

public class TaiKhoanDAO extends BaseDAO<TaiKhoanDTO> {
    public TaiKhoanDAO() {
        super(
        "taikhoan",
        List.of(
        "tenTK",
        "matkhau",
        "email",
        "trangthai",
        "idNQ"
    	)
        );
    }

    protected TaiKhoanDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
    	return new TaiKhoanDTO(
                rs.getInt("idTK"),
                rs.getString("tenTK"),
                rs.getString("matkhau"),
                rs.getString("email"),
                rs.getInt("trangthai"),
                rs.getInt("idNQ")
    		);
    }

    	public boolean add(TaiKhoanDTO taiKhoan) {
		List<Object> params = new ArrayList<>();
		params.add(taiKhoan.getTenTK());
		params.add(taiKhoan.getMatkhau());
		params.add(taiKhoan.getEmail());
		params.add(taiKhoan.getTrangthai());
		params.add(taiKhoan.getIdNQ());
		return super.add(params);
	}

    public boolean updateInfo(TaiKhoanDTO taiKhoan) {
        Connection link = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        try {
            List<Object> params = new ArrayList<>();
            params.add(taiKhoan.getTenTK());
            params.add(taiKhoan.getEmail());
            params.add(taiKhoan.getTrangthai());
            params.add(taiKhoan.getIdNQ());
            params.add(taiKhoan.getIdTK());
            String sql =
                    "UPDATE taikhoan " +
                        "SET " +
                            "tenTK = ?, " +
                            "email = ?, " +
                            "trangthai = ?, " +
                            "idNQ = ? " +
                        "WHERE idTK = ?";

            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i+1, params.get(i));
            }
            success = pstmt.executeUpdate() > 0;

        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }

        return success;
    }

    public boolean updatePassword(TaiKhoanDTO taikhoan) {
        Connection link = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        try {
            List<Object> params = new ArrayList<>();
            params.add(taikhoan.getMatkhau());
            params.add(taikhoan.getIdTK());
            String sql = "UPDATE taikhoan SET matkhau = ? WHERE idTK = ?";

            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            for (int i = 0; i < params.size() ; i++) {
                pstmt.setObject(i+1, params.get(i));
            }

            success = pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }

        return success;
    }

    public boolean isExist(TaiKhoanDTO taiKhoan) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isExist = false;
        try {
			StringBuilder sql = new StringBuilder("SELECT * FROM ");
			sql.append(table);
			sql.append(" WHERE (email = ? OR tenTK = ?)");
			if(taiKhoan.getIdTK() != 0) {
				sql.append(" AND idTK != ?");
			}

            // noi param
            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            pstmt.setString(1, taiKhoan.getEmail());
            pstmt.setString(2, taiKhoan.getTenTK());
			if(taiKhoan.getIdTK() != 0) {
				pstmt.setInt(3, taiKhoan.getIdTK());
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

    public boolean isEmployeeInReceipt(int idTK){
        Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean inReceipt = false;
        try {
            String sql = "SELECT * FROM hoadon WHERE idTK = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idTK);
            rs = pstmt.executeQuery();
            inReceipt = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return inReceipt;
    }

    public boolean delete(int idTK) {
		String col = "idTK";
		return super.delete(col, idTK);
    }

    public List<TaiKhoanDTO> search(String keyWord){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		List<TaiKhoanDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE idTK LIKE ? OR tenTK LIKE ? OR email LIKE ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setString(1, "%" + keyWord + "%");
            pstmt.setString(2, "%" + keyWord + "%");
            pstmt.setString(3, "%" + keyWord + "%");
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

    public TaiKhoanDTO findByIdTK(int idTK) {
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        TaiKhoanDTO result = null;
        try {
            String sql = "SELECT * FROM " + table + " WHERE idTK = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1,idTK);
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

    public List<TaiKhoanDTO> getAllByIDNQ(int idNQ) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<TaiKhoanDTO> result = new ArrayList<>();

        try {
            String sql = "SELECT * FROM " + table + " WHERE idNQ = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idNQ);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(mapResultSetToDTO(rs));
            }
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }

        return result;
    }

    public TaiKhoanDTO findByTenTK(String tenTK) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TaiKhoanDTO result = null;
        try {
            String sql = "SELECT * FROM " + table + " WHERE tenTK = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setString(1, tenTK);
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

    public TaiKhoanDTO checkLogin(String tenTK, String matKhau) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TaiKhoanDTO result = null;

        try {
            String sql = "SELECT * FROM " + table + " WHERE tenTK = ? AND matkhau = ?";

            List<Object> params = new ArrayList<>();
            params.add(tenTK);
            params.add(matKhau);

            // Nối params
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setString(1, tenTK);
            pstmt.setString(2, matKhau);

            // Thực thi
            rs = pstmt.executeQuery();
            if (rs.next()) {
				result = mapResultSetToDTO(rs);
			}
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }

        return result;
    }
}