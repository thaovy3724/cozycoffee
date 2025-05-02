package DAO;

import DTO.PhieuNhapDTO;
import DTO.TrangThai_PnDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: huonglamcoder
 */

public class TrangThai_PnDAO extends BaseDAO<TrangThai_PnDTO> {
    public TrangThai_PnDAO() {
        super(
                "trangthai_pn",
                List.of(
                    "idTT",
                    "tenTT"
                )
        );
    }

    @Override
    protected TrangThai_PnDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new TrangThai_PnDTO(
                rs.getInt("idTT"),
                rs.getString("tenTT")
        );
    }
}
