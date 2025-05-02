package DAO;

import java.sql.*;
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
				rs.getDate("ngaytao"),
				rs.getDate("ngaycapnhat"),
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
			pstmt.setDate(1, Date.valueOf(pn.getNgaytao()));
			pstmt.setDate(2, Date.valueOf(pn.getNgaycapnhat()));
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
				pstmt.setFloat(4, chitiet.getTonkho());
				pstmt.setInt(5, chitiet.getDongia());
				pstmt.setDate(6, Date.valueOf(chitiet.getHsd()));

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

	public boolean update(PhieuNhapDTO pn, List<Lo_NguyenLieuDTO> danhSachChiTiet) {
		String sql;
		Connection link = null;
		PreparedStatement pstmt = null;

		try {
			link = db.connectDB();
			link.setAutoCommit(false);

			sql = "UPDATE " + table + " SET ngaycapnhat = ?, idTK = ?, idNCC = ?, idTT = ?";
			pstmt = link.prepareStatement(sql);
			pstmt.setDate(1, Date.valueOf(pn.getNgaycapnhat()));
			pstmt.setInt(2, pn.getIdTK());
			pstmt.setInt(3, pn.getIdNCC());
			pstmt.setInt(4, pn.getIdTT());
			pstmt.executeUpdate();

			Lo_NguyenLieuDAO lo_NguyenLieuDao = new Lo_NguyenLieuDAO();
			if (danhSachChiTiet != null && !danhSachChiTiet.isEmpty()) {
				for (Lo_NguyenLieuDTO ctpn : danhSachChiTiet) {
					float tonKho = (pn.getIdTT() == 2) ?
							ctpn.getSoluongnhap() : ctpn.getTonkho();
					// Cập nhật hoặc chèn bản ghi trong lo_nguyenlieu
					sql = "UPDATE lo_nguyenlieu SET soluongnhap = ?, tonkho = ?, dongia = ?, hsd = ? WHERE idPN = ? AND idNL = ?";
					pstmt = link.prepareStatement(sql);
					pstmt.setFloat(1, ctpn.getSoluongnhap());
					pstmt.setFloat(2, tonKho);
					pstmt.setInt(3, ctpn.getDongia());
					pstmt.setDate(4, Date.valueOf(ctpn.getHsd()));
					pstmt.setInt(5, pn.getIdPN());
					pstmt.setInt(6, ctpn.getIdNL());
					int updatedRows = pstmt.executeUpdate();

					// Nếu không có bản ghi nào được cập nhật, thêm bản ghi mới
					if (updatedRows == 0) {
						sql = "INSERT INTO lo_nguyenlieu (idPN, idNL, soluongnhap, tonkho, dongia, hsd) VALUES (?, ?, ?, ?, ?, ?)";
						pstmt = link.prepareStatement(sql);
						pstmt.setInt(1, pn.getIdPN());
						pstmt.setInt(2, ctpn.getIdNL());
						pstmt.setFloat(3, ctpn.getSoluongnhap());
						pstmt.setFloat(4, tonKho); // Sử dụng tonkho đã xử lý
						pstmt.setInt(5, ctpn.getDongia());
						pstmt.setDate(6, Date.valueOf(ctpn.getHsd()));
						pstmt.executeUpdate();
					}
				}
			} else {
				throw new SQLException("Xóa chi tiết phiếu nhập thất bại!");
			}

			// Commit giao dịch
			link.commit();
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			try {
				if (link != null)
					link.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			db.close(link);
		}
	}

	public PhieuNhapDTO findByIdPN(int idPN) {
		Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PhieuNhapDTO result = null;

		try {
			String sql = "SELECT * FROM phieunhap WHERE idPN = ?";
			link = db.connectDB();
			pstmt = link.prepareStatement(sql);
			pstmt.setInt(1, idPN);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = mapResultSetToDTO(rs);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			db.close(link);
		}
		return result;
	}

	public boolean delete(int idPN) {
		String col = "idPN";
		return super.delete(col, idPN);
	}
}
