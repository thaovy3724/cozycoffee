package DAO;

import java.sql.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

			sql = "UPDATE " + table + " SET ngaycapnhat = ?, idTK = ?, idNCC = ?, idTT = ? WHERE idPN = ?";
			pstmt = link.prepareStatement(sql);
			pstmt.setDate(1, Date.valueOf(pn.getNgaycapnhat()));
			pstmt.setInt(2, pn.getIdTK());
			pstmt.setInt(3, pn.getIdNCC());
			pstmt.setInt(4, pn.getIdTT());
			pstmt.setInt(5, pn.getIdPN());
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

	//TrongHiuuu 4/5/2025
	public List<Long> getTongTienEachInvoiceByYear(int year) {
		Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Long> result = new ArrayList<>();
		try {
			link = db.connectDB();
			String sql = """
            WITH RECURSIVE months(month) AS (
                SELECT 1
                UNION ALL SELECT 2
                UNION ALL SELECT 3
                UNION ALL SELECT 4
                UNION ALL SELECT 5
                UNION ALL SELECT 6
                UNION ALL SELECT 7
                UNION ALL SELECT 8
                UNION ALL SELECT 9
                UNION ALL SELECT 10
                UNION ALL SELECT 11
                UNION ALL SELECT 12
            ),
            chi_phi AS (
                SELECT
                    MONTH(pn.ngaytao) AS thang,
                    COALESCE(SUM(lnl.soluongnhap * lnl.dongia), 0) AS chiphi
                FROM phieunhap pn
                LEFT JOIN lo_nguyenlieu lnl
                    ON lnl.idPN = pn.idPN
                WHERE YEAR(pn.ngaytao) = ?
                    AND pn.idTT = 2
                GROUP BY MONTH(pn.ngaytao)
            )
            SELECT
                months.month AS thang,
                COALESCE(chi_phi.chiphi, 0) AS chiphi
            FROM months
            LEFT JOIN chi_phi
                ON months.month = chi_phi.thang
            ORDER BY months.month ASC;""";
			pstmt = link.prepareStatement(sql);
			pstmt.setInt(1, year);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(rs.getLong("chiphi"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.close(link);
		}

		return result;
	}

	public List<Long> getTongTienEachInvoiceByMonth(int month, int year) {
		Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Long> result = new ArrayList<>();
		try {
			link = db.connectDB();
			String setYearSql = "SET @year = ?;";
			String setMonthSql = "SET @month = ?;";
			String sql = """
            WITH RECURSIVE days(day) AS (
                SELECT 1
                UNION ALL
                SELECT day + 1
                FROM days
                WHERE day < DAY(LAST_DAY(CONCAT(@year, '-', @month, '-01')))
            ),
            chi_phi AS (
                SELECT
                    DATE(pn.ngaytao) AS ngay,
                    COALESCE(SUM(lnl.soluongnhap * lnl.dongia), 0) AS chiphi
                FROM phieunhap pn
                LEFT JOIN lo_nguyenlieu lnl
                    ON lnl.idPN = pn.idPN
                WHERE YEAR(pn.ngaytao) = @year
                    AND MONTH(pn.ngaytao) = @month
                    AND pn.idTT = 2
                GROUP BY DATE(pn.ngaytao)
            )
            SELECT
                DATE(CONCAT(@year, '-', @month, '-', days.day)) AS ngay,
                COALESCE(chi_phi.chiphi, 0) AS chiphi
            FROM days
            LEFT JOIN chi_phi
                ON DATE(CONCAT(@year, '-', @month, '-', days.day)) = chi_phi.ngay
            ORDER BY days.day ASC;""";
			PreparedStatement yearPstmt = link.prepareStatement(setYearSql);
			PreparedStatement monthPstmt = link.prepareStatement(setMonthSql);
			pstmt = link.prepareStatement(sql);

			yearPstmt.setInt(1, year);
			monthPstmt.setInt(1, month);

			yearPstmt.execute();
			monthPstmt.execute();

			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(rs.getLong("chiphi"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.close(link);
		}

		return result;
	}

	public List<Map<String, List<Long>>> getIngredientsCostStatisticByYear(int year) {
		Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String, List<Long>>> result = new ArrayList<>();

		// Dùng LinkedHashMap để giữ thứ tự thêm vào
		Map<String, List<Long>> ingredientMap = new LinkedHashMap<>();

		try {
			link = db.connectDB();
			String sql = """
            WITH RECURSIVE months(month) AS (
                SELECT 1
                UNION ALL
                SELECT month + 1 FROM months WHERE month < 12
            ),
            chi_phi AS (
                SELECT
                    MONTH(pn.ngaytao) AS thang,
                    nl.tenNL,
                    COALESCE(SUM(lnl.soluongnhap * lnl.dongia), 0) AS chiphi
                FROM nguyenlieu nl
                LEFT JOIN lo_nguyenlieu lnl ON nl.idNL = lnl.idNL
                LEFT JOIN phieunhap pn ON lnl.idPN = pn.idPN
                WHERE YEAR(pn.ngaytao) = ? AND pn.idTT = 2
                GROUP BY MONTH(pn.ngaytao), nl.idNL, nl.tenNL
            )
            SELECT
                months.month AS thang,
                nl.tenNL,
                COALESCE(chi_phi.chiphi, 0) AS chiphi
            FROM months
            CROSS JOIN nguyenlieu nl
            LEFT JOIN chi_phi ON months.month = chi_phi.thang AND chi_phi.tenNL = nl.tenNL
            ORDER BY nl.tenNL ASC, months.month ASC;
        """;

			pstmt = link.prepareStatement(sql);
			pstmt.setInt(1, year);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int month = rs.getInt("thang"); // từ 1 đến 12
				String tenNL = rs.getString("tenNL");
				long chiPhi = rs.getLong("chiphi");

				// Nếu nguyên liệu chưa có trong map → khởi tạo list 12 tháng = 0
				ingredientMap.putIfAbsent(tenNL, new ArrayList<>(Collections.nCopies(12, 0L)));

				// Ghi đè chi phí vào đúng vị trí tháng-1
				ingredientMap.get(tenNL).set(month - 1, chiPhi);
			}

			// Chuyển từng entry thành map đơn để thêm vào list
			for (Map.Entry<String, List<Long>> entry : ingredientMap.entrySet()) {
				Map<String, List<Long>> singleMap = new HashMap<>();
				singleMap.put(entry.getKey(), entry.getValue());
				result.add(singleMap);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.close(link);
		}

		return result;
	}


	public List<Map<String, List<Long>>> getIngredientsCostStatisticByMonth(int month, int year) {
		Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String, List<Long>>> result = new ArrayList<>();

		// Dùng LinkedHashMap để giữ thứ tự thêm vào
		Map<String, List<Long>> ingredientMap = new LinkedHashMap<>();
		int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();
		int numberOfWeeks = (int) Math.ceil(lastDayOfMonth / 7.0);
		try {
			link = db.connectDB();
			String setYearSql = "SET @year = ?;";
			String setMonthSql = "SET @month = ?;";
			String sql = """
                WITH RECURSIVE days(day) AS (
                    SELECT 1
                    UNION ALL
                    SELECT day + 1
                    FROM days
                    WHERE day < DAY(LAST_DAY(CONCAT(@year, '-', @month, '-01')))
                ),
                chi_phi AS (
                    SELECT
                        DAY(pn.ngaytao) AS ngay,
                        nl.tenNL,
                        COALESCE(SUM(lnl.soluongnhap * lnl.dongia), 0) AS chiphi
                    FROM nguyenlieu nl
                    LEFT JOIN lo_nguyenlieu lnl ON nl.idNL = lnl.idNL
                    LEFT JOIN phieunhap pn ON lnl.idPN = pn.idPN
                    WHERE YEAR(pn.ngaytao) = @year AND MONTH(pn.ngaytao) = @month AND pn.idTT = 2
                    GROUP BY DAY(pn.ngaytao), nl.idNL, nl.tenNL
                )
                SELECT
                    days.day AS ngay,
                    nl.tenNL,
                    COALESCE(chi_phi.chiphi, 0) AS chiphi
                FROM days
                CROSS JOIN nguyenlieu nl
                LEFT JOIN chi_phi ON days.day = chi_phi.ngay AND chi_phi.tenNL = nl.tenNL
                ORDER BY days.day ASC, nl.tenNL ASC;
                """;
			PreparedStatement yearPstmt = link.prepareStatement(setYearSql);
			PreparedStatement monthPstmt = link.prepareStatement(setMonthSql);
			pstmt = link.prepareStatement(sql);

			yearPstmt.setInt(1, year);
			monthPstmt.setInt(1, month);

			yearPstmt.execute();
			monthPstmt.execute();

			rs = pstmt.executeQuery();
			while (rs.next()) {
				int day = rs.getInt("ngay");
				String tenNL = rs.getString("tenNL");
				long chiPhi = rs.getLong("chiphi");

				// Nếu nguyên liệu chưa có trong map → khởi tạo list có số phần tử bằng số ngày trong tháng, mỗi phần tử = 0
				ingredientMap.putIfAbsent(tenNL, new ArrayList<>(Collections.nCopies(lastDayOfMonth, 0L)));

				// Ghi đè chi phí vào đúng vị trí tháng-1
				ingredientMap.get(tenNL).set(day - 1, chiPhi);
			}

			// Chuyển từng entry thành map đơn để thêm vào list
			for (Map.Entry<String, List<Long>> entry : ingredientMap.entrySet()) {
				Map<String, List<Long>> singleMap = new HashMap<>();
				singleMap.put(entry.getKey(), entry.getValue());
				result.add(singleMap);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.close(link);
		}
		return result;
	}
}