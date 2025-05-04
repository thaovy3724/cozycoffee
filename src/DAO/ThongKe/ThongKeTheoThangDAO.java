package DAO.ThongKe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DAO.BaseDAO;
import DTO.ThongKe.ThongKeTheoThangDTO;

/**
 * @author: huonglamcoder
 */

// HUONGNGUYEN 3/5
public class ThongKeTheoThangDAO extends BaseDAO<ThongKeTheoThangDTO> {
    public ThongKeTheoThangDAO() {
        super(
            "dummy",
                Collections.emptyList()
        );
    }

    @Override
    public ThongKeTheoThangDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new ThongKeTheoThangDTO(
            rs.getDate("ngay"),
            rs.getLong("chiphi"),
            rs.getLong("doanhthu"),
    rs.getLong("doanhthu") - rs.getLong("chiphi")
        );
    }

    public List<ThongKeTheoThangDTO> getThongKeTheoThang(int nam, int thang) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<ThongKeTheoThangDTO> result = new ArrayList<>();
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
                COALESCE(doanh_thu.doanhthu, 0) AS doanhthu,
                COALESCE(chi_phi.chiphi, 0) AS chiphi
            FROM days
            LEFT JOIN chi_phi
                ON DATE(CONCAT(@year, '-', @month, '-', days.day)) = chi_phi.ngay
            LEFT JOIN doanh_thu
                ON DATE(CONCAT(@year, '-', @month, '-', days.day)) = doanh_thu.ngay
            ORDER BY days.day ASC;""";
            PreparedStatement yearPstmt = link.prepareStatement(setYearSql);
            PreparedStatement monthPstmt = link.prepareStatement(setMonthSql);
            pstmt = link.prepareStatement(sql);

            yearPstmt.setInt(1, nam);
            monthPstmt.setInt(1, thang);

            yearPstmt.execute();
            monthPstmt.execute();

            rs = pstmt.executeQuery(sql);
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
