package DAO;

import DTO.CTHoaDonDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CTHoaDonDAO {
    protected String getTableName() {
        return "ct_hoadon";
    }

    protected List<String> getTableColumns() {
        return List.of(
                "idSP",
                "idHD",
                "soluong",
                "gialucdat"
                );
    }

    protected CTHoaDonDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new CTHoaDonDTO(
                rs.getInt(getTableColumns().get(0)),
                rs.getInt(getTableColumns().get(1)),
                rs.getInt(getTableColumns().get(2)),
                rs.getInt(getTableColumns().get(3))
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

    public List<CTHoaDonDTO> getAllByID(String column, int id) {
        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(getTableName());
        sql.append(" WHERE ");
        sql.append(column + " = ?");
        List<Object> params = new ArrayList<>();
        params.add(id);

        List<CTHoaDonDTO> list = new ArrayList<>();

        try {
            db.prepareStatement(sql.toString());
            try (ResultSet rs = db.getAll(params)) {
                while (rs.next()) {
                    CTHoaDonDTO item = mapResultSetToDTO(rs);
                    list.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return list;
    }

    public boolean add(CTHoaDonDTO ctHoaDonDTO) {
        DatabaseConnection db = new DatabaseConnection();
        List<Object> params = mapDTOToParams(ctHoaDonDTO);

        if (params == null || params.size() != getTableColumns().size()) {
            return false;
        }

        StringBuilder sql = new StringBuilder("INSERT INTO ");

        //Tên table
        sql.append(getTableName());
        sql.append(" (");
        //Danh sách cột của table
        sql.append(String.join(",", getTableColumns()));
        sql.append(") VALUES (");
        //Với mỗi tham số, thêm một character "?"
        sql.append(String.join(",", Collections.nCopies(params.size(), "?")));
        sql.append(")");

        try {
            db.prepareStatement(sql.toString());
            return db.execute(params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false;

    }
}
