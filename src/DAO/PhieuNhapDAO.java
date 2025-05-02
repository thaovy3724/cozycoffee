package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTO.Lo_NguyenLieuDTO;
import DTO.PhieuNhapDTO;
import DTO.TrangThai_PnDTO;

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

	// Hiếu
	public List<PhieuNhapDTO> searchCompleteByDate(Date start, Date end) {
		Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PhieuNhapDTO> result = new ArrayList<>();
		List<Date> params = new ArrayList<>();

		try {
			StringBuilder sql = new StringBuilder("SELECT * FROM phieunhap WHERE idTT = 2 ");
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

	public long getTotalAmount(int idPN) {
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

//	HUONGNGUYEN 2/5
	public List<PhieuNhapDTO> search(Date dateStart, Date dateEnd,
									 TrangThai_PnDTO ttpn) {
		Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PhieuNhapDTO> result = new ArrayList<>();
		try {
			link = db.connectDB();
			String sql = "SELECT " + table + ".* FROM " + table + " WHERE 1 = 1";
			List<Object> params = new ArrayList<>();
			if (ttpn.getIdTT() != 0) {
				sql += " AND idTT = ?";
				params.add(ttpn.getIdTT());
			}
			if (dateStart != null && dateEnd != null) {
				sql += " AND ngaytao BETWEEN ? AND ?";
				params.add(dateStart);
				params.add(dateEnd);
			}

			pstmt = link.prepareStatement(sql);
			// Gán tham số động
			for (int i = 0; i < params.size(); i++) {
				Object param = params.get(i);

				if (param instanceof Integer) {
					pstmt.setInt(i + 1, (Integer) param);
				} else if (param instanceof Date) {
					pstmt.setDate(i + 1, (Date) param);
				}
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
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