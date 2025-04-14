package DAO;

import CONFIG.Config;
import DTO.Lo_NguyenLieuDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lo_NguyenLieuDAO {
    private DatabaseConnect db;

    public Lo_NguyenLieuDAO() {
        db = new DatabaseConnect();
    }

    public boolean addLoNguyenLieu(Lo_NguyenLieuDTO lo) {
        String sql = "INSERT INTO Lo_NguyenLieu (idNL, idPN, soluongnhap, tonkho, dongia, hsd) VALUES (?, ?, ?, ?, ?, ?)";
        List<Object> params = Arrays.asList(lo.getIdNL(), lo.getIdPN(), lo.getSoluongnhap(), lo.getTonkho(), lo.getDongia(), lo.getHsd());
        return db.execute(sql, params);
    }

    public boolean updateLoNguyenLieu(Lo_NguyenLieuDTO lo) {
        String sql = "UPDATE Lo_NguyenLieu SET idPN = ?, soluongnhap = ?, tonkho = ?, dongia = ?, hsd = ? WHERE idNL = ?";
        List<Object> params = Arrays.asList(lo.getIdPN(), lo.getSoluongnhap(), lo.getTonkho(), lo.getDongia(), lo.getHsd(), lo.getIdNL());
        return db.execute(sql, params);
    }

    public boolean deleteLoNguyenLieu(int idNL) {
        String sql = "DELETE FROM Lo_NguyenLieu WHERE idNL = ?";
        List<Object> params = Arrays.asList(idNL);
        return db.execute(sql, params);
    }

    public boolean deleteByIdNL(int idNL) {
        String sql = "DELETE FROM Lo_NguyenLieu WHERE idNL = ?";
        List<Object> params = Arrays.asList(idNL);
        try {
            return db.execute(sql, params);
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa Lo_NguyenLieu theo idNL: " + e.getMessage());
            return false;
        }
    }

    public boolean updateTonKho(int idNL, float tonKho) {
        try {
            String query = "SELECT idPN FROM Lo_NguyenLieu WHERE idNL = ? ORDER BY idPN DESC LIMIT 1";
            ResultSet rs = db.getAll(query, Arrays.asList(idNL));
            if (rs != null && rs.next()) {
                int idPN = rs.getInt("idPN");
                String updateSql = "UPDATE Lo_NguyenLieu SET tonkho = ? WHERE idNL = ? AND idPN = ?";
                List<Object> params = Arrays.asList(tonKho, idNL, idPN);
                boolean success = db.execute(updateSql, params);
                db.close(rs);
                return success;
            } else {
                System.out.println("Không tìm thấy bản ghi Lo_NguyenLieu cho idNL: " + idNL);
                db.close(rs);
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi trong updateTonKho: " + e.getMessage());
            return false;
        }
    }

    public boolean updateHSD(int idNL, String hsd) {
        try {
            String query = "SELECT idPN FROM Lo_NguyenLieu WHERE idNL = ? ORDER BY idPN DESC LIMIT 1";
            ResultSet rs = db.getAll(query, Arrays.asList(idNL));
            if (rs != null && rs.next()) {
                int idPN = rs.getInt("idPN");
                String updateSql = "UPDATE Lo_NguyenLieu SET hsd = ? WHERE idNL = ? AND idPN = ?";
                List<Object> params = Arrays.asList(hsd, idNL, idPN);
                boolean success = db.execute(updateSql, params);
                db.close(rs);
                return success;
            } else {
                System.out.println("Không tìm thấy bản ghi Lo_NguyenLieu cho idNL: " + idNL);
                db.close(rs);
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi trong updateHSD: " + e.getMessage());
            return false;
        }
    }

    public List<Lo_NguyenLieuDTO> getAllLoNguyenLieu() {
        List<Lo_NguyenLieuDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Lo_NguyenLieu";
        try {
            ResultSet rs = db.getAll(sql, null);
            while (rs.next()) {
                Lo_NguyenLieuDTO lo = new Lo_NguyenLieuDTO();
                lo.setIdNL(rs.getInt("idNL"));
                lo.setIdPN(rs.getInt("idPN"));
                lo.setSoluongnhap(rs.getFloat("soluongnhap"));
                lo.setTonkho(rs.getFloat("tonkho"));
                lo.setDongia(rs.getInt("dongia"));
                lo.setHsd(rs.getString("hsd"));
                list.add(lo);
            }
            db.close(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean resetData() {
        String sql = "DELETE FROM Lo_NguyenLieu";
        try {
            return db.execute(sql, null);
        } catch (Exception e) {
            System.err.println("Lỗi khi đặt lại dữ liệu Lo_NguyenLieu: " + e.getMessage());
            return false;
        }
    }
}