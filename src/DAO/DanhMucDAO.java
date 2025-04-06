package DAO;

import DTO.DanhMucDTO;
import java.util.List;
import java.sql.*;

public class DanhMucDAO extends BaseDAO<DanhMucDTO>{
    public DanhMucDAO() {
        super("danhmuc",
                List.of(
                        "idDM",
                        "tenDM",
                        "trangthai",
                        "idDMCha"
                ));
    }

    @Override
    protected DanhMucDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new DanhMucDTO(
                rs.getInt("idDM"),
                rs.getString("tenDM"),
                rs.getInt("trangthai"),
                rs.getInt("idDMCha")
        );
    }

    @Override
    public List<Object> mapDTOToParams(DanhMucDTO danhMucDTO) {
        return List.of(
                danhMucDTO.getIdDM(),
                danhMucDTO.getTenDM(),
                danhMucDTO.getTrangthai(),
                danhMucDTO.getIdDMCha()
        );
    }
}