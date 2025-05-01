package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DTO.Lo_NguyenLieuDTO;
import DTO.PhieuNhapDTO;

public class PhieuNhapDAO extends BaseDAO<PhieuNhapDTO> {
	public PhieuNhapDAO() {
		super("phieunhap",
				List.of(
						"idPN",
						"ngaytao",
						"ngaycapnhat",
						"idTK",
						"idNCC",
						"idTT"));
	}

	@Override
	protected PhieuNhapDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
		return new PhieuNhapDTO(
				rs.getInt("idPN"),
				rs.getString("ngaytao"),
				rs.getString("ngaycapnhat"),
				rs.getInt("idTK"),
				rs.getInt("idNCC"),
				rs.getInt("idTT"));
	}

	public int add(PhieuNhapDTO pn, List<Lo_NguyenLieuDTO> danhSachChiTiet) {
		String sql;
		Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		int newIdPN = -1;
		try {
			link = db.connectDB();
			link.setAutoCommit(false);

			sql = "INSERT INTO phieunhap (ngaytao, ngaycapnhat, idTK, idNCC, idTT) VALUES (?, ?, ?, ?, ?)";
			pstmt = link.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, pn.getNgaytao());
			pstmt.setString(2, pn.getNgaycapnhat());
			pstmt.setInt(3, pn.getIdTK());
			pstmt.setInt(4, pn.getIdNCC());
			pstmt.setInt(5, pn.getIdTT());
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException("Không thể chèn phiếu nhập!");
			}
			generatedKeys = pstmt.getGeneratedKeys();
			if (!generatedKeys.next()) {
				throw new SQLException("Không lấy được ID phiếu nhập!");
			}
			newIdPN = generatedKeys.getInt(1);

			// Đóng pstmt trước khi tạo mới
			if (pstmt != null) {
				pstmt.close();
			}

			sql = "INSERT INTO lo_nguyenlieu (idNL, idPN, soluongnhap, tonkho, dongia, hsd) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = link.prepareStatement(sql);
			for (Lo_NguyenLieuDTO chitiet : danhSachChiTiet) {
				pstmt.setInt(1, chitiet.getIdNL());
				pstmt.setInt(2, newIdPN);
				pstmt.setFloat(3, chitiet.getSoluongnhap());
				pstmt.setFloat(4, chitiet.getSoluongnhap());
				pstmt.setInt(5, chitiet.getDongia());
				pstmt.setString(6, chitiet.getHsd());

				rowsAffected = pstmt.executeUpdate();
				if (rowsAffected == 0) {
					throw new SQLException("Không thể chèn lô nguyên liệu tại ID NL: " + chitiet.getIdNL());
				}
			}
			// Thành công
			link.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				if (link != null)
					link.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			db.close(link);
		}
		return newIdPN;
	}

    public long getTongTienByIDPN(int idPN) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long tongtien = 0;

        try {
            String sql = "SELECT sum(lnl.dongia * lnl.soluongnhap) AS tongtien " +
                    "FROM phieunhap pn " +
                    "INNER JOIN lo_nguyenlieu lnl on pn.idPN = lnl.idPN " +
                    "WHERE pn.idPN = ?";

            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idPN);
            rs = pstmt.executeQuery();
            if (rs.next()) tongtien = rs.getLong("tongtien");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close(link);
        }

        return tongtien;
    }

    public List<PhieuNhapDTO> searchByDate(Date start, Date end) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PhieuNhapDTO> result = new ArrayList<>();
        List<Date> params = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM phieunhap WHERE 1=1 ");
            if (start != null) {
                sql.append("AND (ngaycapnhat >= ?) ");
                params.add(start);
            }
            if (end != null) {
                sql.append("AND (ngaycapnhat <= ?) ");
                params.add(end);
            }

            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            for (int i=0; i<params.size(); i++) {
                pstmt.setDate(i+1, params.get(i));
            }
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