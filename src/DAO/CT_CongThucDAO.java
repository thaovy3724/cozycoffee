package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.CT_CongThucDTO;

public class CT_CongThucDAO extends BaseDAO<CT_CongThucDTO> {
    public CT_CongThucDAO() {
        super(
            "ct_congthuc",
            List.of(
                "idCT",
                "idNL",
                "soluong"
            ));
    }

    @Override
    protected CT_CongThucDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new CT_CongThucDTO(
                rs.getInt("idCT"),
                rs.getInt("idNL"),
                rs.getFloat("soluong")
        );
    }

    public String add(CT_CongThucDTO ctDetail) {
        List<Object> params = new ArrayList<>();
        params.add(ctDetail.getIdCT());
        params.add(ctDetail.getIdNL());
        params.add(ctDetail.getSoluong());
        Connection link = null;
        PreparedStatement pstmt = null;
        try {
            link = db.connectDB();
            boolean success = super.add(params);
            return success ? "" : "Không thể thêm chi tiết công thức vào cơ sở dữ liệu";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getSQLState().startsWith("23")) { // Ràng buộc khóa ngoại hoặc khóa chính
                return "Lỗi ràng buộc cơ sở dữ liệu: ID công thức hoặc nguyên liệu không hợp lệ";
            }
            return "Lỗi cơ sở dữ liệu: " + e.getMessage();
        } finally {
            if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            db.close(link);
        }
    }

    public boolean deleteByCongThuc(int idCT) {
        String col = "idCT";
        Connection link = null;
        PreparedStatement pstmt = null;
        try {
            link = db.connectDB();
            return super.delete(col, idCT);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            db.close(link);
        }
    }

    public List<CT_CongThucDTO> getChiTietCongThuc(int idCT) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CT_CongThucDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE idCT = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idCT);
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