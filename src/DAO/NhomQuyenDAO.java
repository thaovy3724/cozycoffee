package DAO;

import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhomQuyenDAO {
    private String tableName = "nhomquyen";
    private List<String> tableColumns = List.of(
            "idNQ",
            "tenNQ"
    );

    public List<NhomQuyenDTO> getAll() {
        DatabaseConnection db = new DatabaseConnection();
        String sql = "SELECT * FROM " + tableName;
        List<NhomQuyenDTO> list = new ArrayList<>();

        try {
            db.prepareStatement(sql);
            try (ResultSet rs = db.getAll(null)) {
                while (rs.next()) {
                    NhomQuyenDTO nhomQuyen = mapResultSetToDTO(rs);
                    list.add(nhomQuyen);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return list;
    }

    public NhomQuyenDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new NhomQuyenDTO(
                rs.getInt("idNQ"),
                rs.getString("tenNQ")
        );
    }
}
