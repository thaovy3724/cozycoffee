package DAO;

import java.util.ArrayList;

import DTO.CT_CongThucDTO;
import DTO.CongThucDTO;
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
                rs.getFloat("soluong"));
    }

    public boolean add(CT_CongThucDTO ct) {
        List<Object> params = new ArrayList<>();
        params.add(ct.getIdCT());
        params.add(ct.getIdNL());
        params.add(ct.getSoluong());
        return super.add(params);
    }

    public boolean deleteByCongThuc(int idCT) {
        return super.delete("idCT", idCT);
    }

    // public boolean update(CongThucDTO ct) {
    //     List<Object> params = new ArrayList<>();
    //     params.add(ct.getIdCT());
    //     params.add(ct.getMota());
    //     params.add(ct.getIdSP() == 0 ? null : ct.getIdSP());
    //     String condition = "idCT = " + ct.getIdCT();
    //     return super.update(params, condition);
    // }

    public List<CT_CongThucDTO> getByCongThuc(int idCT) {
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

