package DAO;

import DTO.SanPhamDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO extends BaseDAO<SanPhamDTO> {
    public SanPhamDAO() {
        super(
                "sanpham",
                List.of(
                        "idSP",
                        "tenSP",
                        "giaban",
                        "hinhanh",
                        "trangthai",
                        "idDM"
                )
        );
    }

    protected String getTableName() {
        return "sanpham";
    }

    protected List<String> getTableColumns() {
        return List.of(
                "idSP",
                "tenSP",
                "giaban",
                "hinhanh",
                "trangthai",
                "idDM"
        );
    }



    @Override
    protected SanPhamDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new SanPhamDTO(
                rs.getInt(getTableColumns().get(0)),
                rs.getString(getTableColumns().get(1)),
                rs.getInt(getTableColumns().get(2)),
                rs.getString(getTableColumns().get(3)),
                rs.getBoolean(getTableColumns().get(4)),
                rs.getInt(getTableColumns().get(5))
        );
    }

    @Override
    public List<Object> mapDTOToParams(SanPhamDTO sanPhamDTO) {
        return List.of(
                sanPhamDTO.getIdSP(),
                sanPhamDTO.getTenSP(),
                sanPhamDTO.getGiaban(),
                sanPhamDTO.getHinhanh(),
                sanPhamDTO.getTrangthai(),
                sanPhamDTO.getIdDM()
        );
    }

    public SanPhamDTO getSPByID(String column, int id) {
        DatabaseConnection db = new DatabaseConnection();
        SanPhamDTO sanPham = new SanPhamDTO();

        StringBuilder sql = new StringBuilder("SELECT * FROM "+ getTableName() +" WHERE "+ column +" = ?");
        List<Object> params = new ArrayList<>();
        params.add(id);

        try{
            db.prepareStatement(sql.toString());
            try (ResultSet rs = db.getAll(params)) {
               if (rs.next()) {
                   sanPham = mapResultSetToDTO(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return sanPham;
    }
}
