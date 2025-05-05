package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DTO.CT_HoaDonDTO;
import DTO.HoaDonDTO;

public class HoaDonDAO extends BaseDAO<HoaDonDTO>{
    public HoaDonDAO() {
		super(
		"hoadon", 
		List.of(
		 "ngaytao",
		 "idTK"
		));
	}

	@Override
	protected HoaDonDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new HoaDonDTO(
                rs.getInt("idHD"),
                rs.getDate("ngaytao"),
                rs.getInt("idTK")
        );
    }
	
	public int add(HoaDonDTO hoaDon, List<CT_HoaDonDTO> danhSachChiTiet) {
		Connection link = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		int newIdHD = -1;
		String sql;
		try{
			link = db.connectDB();
			// Bắt đầu transaction
			link.setAutoCommit(false);

			// Thêm hóa đơn
			sql = "INSERT INTO hoadon (ngaytao, idTK) VALUES (?, ?)";
			pstmt = link.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDate(1, Date.valueOf(hoaDon.getNgaytao()));
			pstmt.setInt(2, hoaDon.getIdTK());
			pstmt.executeUpdate();

			// Thêm chi tiết hóa đơn
			generatedKeys = pstmt.getGeneratedKeys();
			if(generatedKeys.next()) 
				newIdHD = generatedKeys.getInt(1);
			sql = "INSERT INTO ct_hoadon (idHD, idSP, soluong, gialucdat) VALUES (?, ?, ?, ?)";
			pstmt = link.prepareStatement(sql);
			for(CT_HoaDonDTO chitiet : danhSachChiTiet){
				pstmt.setInt(1, newIdHD);
				pstmt.setInt(2, chitiet.getIdSP());
				pstmt.setInt(3, chitiet.getSoluong());
				pstmt.setInt(4, chitiet.getGialucdat());
				pstmt.executeUpdate();
			}

			// Thành công
			link.commit();
		}catch(ClassNotFoundException | SQLException e){
			try{
				link.rollback();
				newIdHD = -1;
			}catch(SQLException ex){
				ex.printStackTrace();
			}
        }finally {
            db.close(link);
        }
		return newIdHD;
	}

	public void updateInventory(int idHD){
		Connection link = null;
		PreparedStatement pstmt = null;
        try {
            String sql = "{CALL xuLyDonHang(?)}";
            link = db.connectDB();
            pstmt = link.prepareCall(sql);
            pstmt.setInt(1,idHD);
            pstmt.executeUpdate();
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
	}

	public int getTotalAmount(int idHD){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            String sql = "SELECT SUM(soluong * gialucdat) AS tongtien"+
			" FROM ct_hoadon"+ 
			" WHERE idHD = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1,idHD);
            rs = pstmt.executeQuery();
            if (rs.next()) result = rs.getInt("tongtien");
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}

	public List<HoaDonDTO> search(Date dateStart, Date dateEnd, Integer minPrice, Integer maxPrice) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<HoaDonDTO> result = new ArrayList<>();
        try {
			String sql = "SELECT hd.idHD, ngaytao, idTK, SUM(soluong * gialucdat) AS tongtien " +
						 "FROM hoadon hd " +
						 "INNER JOIN ct_hoadon ct ON hd.idHD = ct.idHD " +
						 "WHERE 1=1";
			List<Object> params = new ArrayList<>();
	
			if (dateStart != null) {
				sql += " AND ngaytao >= ?";
				params.add(new java.sql.Date(dateStart.getTime()));
			}
	
			if (dateEnd != null) {
				sql += " AND ngaytao <= ?";
				params.add(new java.sql.Date(dateEnd.getTime()));
			}
	
			sql += " GROUP BY hd.idHD, ngaytao, idTK HAVING 1=1";
	
			if (minPrice != null) {
				sql += " AND SUM(soluong * gialucdat) >= ?";
				params.add(minPrice);
			}
	
			if (maxPrice != null) {
				sql += " AND SUM(soluong * gialucdat) <= ?";
				params.add(maxPrice);
			}
	
			link = db.connectDB();
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
			while (rs.next()) {
				result.add(new HoaDonDTO(
					rs.getInt("idHD"),
					rs.getDate("ngaytao"),
					rs.getInt("idTK")
				));
			}
	
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }
        return result;
    }

	public HoaDonDTO findByIdHD(int idHD){
		Connection link = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        HoaDonDTO result = null;
        try {
            String sql = "SELECT * FROM " + table + " WHERE idHD = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1,idHD);
            rs = pstmt.executeQuery();
            if (rs.next()) result = mapResultSetToDTO(rs);
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
	}

	public List<HoaDonDTO> searchByDate(Date start, Date end) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<HoaDonDTO> result = new ArrayList<>();
        List<Date> params = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM hoadon WHERE 1=1 ");
            if (start != null) {
                sql.append("AND (ngaytao >= ?) ");
                params.add(start);
            }
            if (end != null) {
                sql.append("AND (ngaytao <= ?) ");
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
	public List<Long> getTongTienByYear(int year) {
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
            doanh_thu AS (
                SELECT
                    MONTH(hd.ngaytao) AS thang,
                    COALESCE(SUM(cthd.soluong * cthd.gialucdat), 0) AS doanhthu
                FROM hoadon hd
                LEFT JOIN ct_hoadon cthd
                    ON cthd.idHD = hd.idHD
                WHERE YEAR(hd.ngaytao) = ?
                GROUP BY MONTH(hd.ngaytao)
            )
            SELECT
                months.month AS thang,
                COALESCE(doanh_thu.doanhthu, 0) AS doanhthu
            FROM months
            LEFT JOIN doanh_thu
                ON months.month = doanh_thu.thang
            ORDER BY months.month ASC;""";
			pstmt = link.prepareStatement(sql);
			pstmt.setInt(1, year);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(rs.getLong("doanhthu"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.close(link);
		}

		return result;
	}

	public List<Long> getTongTienByMonth(int month, int year) {
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
            doanh_thu AS (
                SELECT
                    DATE(hd.ngaytao) AS ngay,
                    COALESCE(SUM(cthd.soluong * cthd.gialucdat), 0) AS doanhthu
                FROM hoadon hd
                LEFT JOIN ct_hoadon cthd
                    ON cthd.idHD = hd.idHD
                WHERE YEAR(hd.ngaytao) = @year
                    AND MONTH(hd.ngaytao) = @month
                GROUP BY DATE(hd.ngaytao)
            )
            SELECT
                DATE(CONCAT(@year, '-', @month, '-', days.day)) AS ngay,
                COALESCE(doanh_thu.doanhthu, 0) AS doanhthu
            FROM days
            LEFT JOIN doanh_thu
                ON DATE(CONCAT(@year, '-', @month, '-', days.day)) = doanh_thu.ngay
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
				result.add(rs.getLong("doanhthu"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.close(link);
		}

		return result;
	}
}
