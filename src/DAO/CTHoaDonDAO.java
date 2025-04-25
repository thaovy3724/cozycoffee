package DAO;

import DTO.CTHoaDonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CTHoaDonDAO extends BaseDAO<CTHoaDonDTO> {
    public CTHoaDonDAO() {
        super(
                "ct_hoadon",
                List.of(
                        "idSP",
                        "idHD",
                        "soluong",
                        "gialucdat"
                )
            );
    }

    protected CTHoaDonDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new CTHoaDonDTO(
                rs.getInt( "idSP"),
                rs.getInt("idHD"),
                rs.getInt("soluong"),
                rs.getInt("gialucdat")
        );
    }

    public List<Object> mapDTOToParams(CTHoaDonDTO ctHoaDonDTO) {
        return List.of(
            ctHoaDonDTO.getIdSP(),
            ctHoaDonDTO.getIdHD(),
            ctHoaDonDTO.getSoluong(),
            ctHoaDonDTO.getGialucdat()
        );
    }

    public List<CTHoaDonDTO> getAllByIDHD(int idHD) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CTHoaDonDTO> result = new ArrayList<>();

        try {
            String sql = "SELECT * FROM " + table + " WHERE idHD = ?";

            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            pstmt.setInt(1, idHD);
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

    public boolean add(CTHoaDonDTO ctHoaDon) {
        List<Object> params = new ArrayList<>();
        params.add(ctHoaDon.getIdSP());
        params.add(ctHoaDon.getIdHD());
        params.add(ctHoaDon.getGialucdat());
        params.add(ctHoaDon.getSoluong());
        return super.add(params);
    }
}
