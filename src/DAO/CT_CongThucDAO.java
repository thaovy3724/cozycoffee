package DAO;

import DTO.CT_CongThucDTO;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

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

    public boolean add(CT_CongThucDTO ctDetail) {
        List<Object> params = new ArrayList<>();
        params.add(ctDetail.getIdCT());
        params.add(ctDetail.getIdNL());
        params.add(ctDetail.getSoluong());
        return super.add(params);
    }

    public boolean deleteByCongThuc(int idCT) {
        String col = "idCT";
        return super.delete(col, idCT);
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