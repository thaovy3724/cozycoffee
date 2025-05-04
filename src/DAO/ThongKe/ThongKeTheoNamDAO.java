package DAO.ThongKe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DAO.BaseDAO;
import DTO.ThongKe.ThongKeTheoNamDTO;

/**
 * @author: huonglamcoder
 */

// HUONGNGUYEN 3/5
public class ThongKeTheoNamDAO extends BaseDAO<ThongKeTheoNamDTO> {
    public ThongKeTheoNamDAO() {
        super(
            "dummy",
                Collections.emptyList()
        );
    }

    @Override
    public ThongKeTheoNamDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new ThongKeTheoNamDTO(
                rs.getInt("thang"),
                rs.getLong("chiphi"),
                rs.getLong("doanhthu"),
        rs.getLong("doanhthu") - rs.getLong("chiphi")
        );
    }

    public List<ThongKeTheoNamDTO> getThongKeTheoNam(int nam) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<ThongKeTheoNamDTO> result = new ArrayList<>();
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
                COALESCE(doanh_thu.doanhthu, 0) AS doanhthu,
                COALESCE(chi_phi.chiphi, 0) AS chiphi
            FROM months
            LEFT JOIN chi_phi
                ON months.month = chi_phi.thang
            LEFT JOIN doanh_thu
                ON months.month = doanh_thu.thang
            ORDER BY months.month ASC;""";
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, nam);
            pstmt.setInt(2, nam);
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
