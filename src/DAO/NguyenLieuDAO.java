
package DAO;

import CONFIG.Config;
import DTO.NguyenLieuDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NguyenLieuDAO {
    private DatabaseConnect db;

    public NguyenLieuDAO() {
        db = new DatabaseConnect();
    }

    public boolean addNguyenLieu(NguyenLieuDTO nl) {
        String sql = "INSERT INTO NguyenLieu (idNL, donvi, tenNL) VALUES (?, ?, ?)";
        List<Object> params = Arrays.asList(nl.getIdNL(), nl.getDonvi(), nl.getTenNL());
        return db.execute(sql, params);
    }

    public boolean updateNguyenLieu(NguyenLieuDTO nl) {
        String sql = "UPDATE NguyenLieu SET donvi = ?, tenNL = ? WHERE idNL = ?";
        List<Object> params = Arrays.asList(nl.getDonvi(), nl.getTenNL(), nl.getIdNL());
        return db.execute(sql, params);
    }

    public boolean deleteNguyenLieu(int idNL) {
        String sql = "DELETE FROM NguyenLieu WHERE idNL = ?";
        List<Object> params = Arrays.asList(idNL);
        try {
            return db.execute(sql, params);
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa NguyenLieu idNL=" + idNL + ": " + e.getMessage());
            return false;
        }
    }

    public List<NguyenLieuDTO> getAllNguyenLieu() {
        List<NguyenLieuDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NguyenLieu";
        try {
            ResultSet rs = db.getAll(sql, null);
            while (rs.next()) {
                NguyenLieuDTO nl = new NguyenLieuDTO();
                nl.setIdNL(rs.getInt("idNL"));
                nl.setDonvi(rs.getString("donvi"));
                nl.setTenNL(rs.getString("tenNL"));
                list.add(nl);
            }
            db.close(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getNextIdNL() {
        String sql = "SELECT MAX(idNL) + 1 AS nextId FROM NguyenLieu";
        try {
            ResultSet rs = db.getAll(sql, null);
            if (rs.next()) {
                int nextId = rs.getInt("nextId");
                db.close(rs);
                return nextId > 0 ? nextId : 1;
            }
            db.close(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public boolean resetData() {
        String sql = "DELETE FROM NguyenLieu";
        try {
            return db.execute(sql, null);
        } catch (Exception e) {
            System.err.println("Lỗi khi đặt lại dữ liệu NguyenLieu: " + e.getMessage());
            return false;
        }
    }
}
