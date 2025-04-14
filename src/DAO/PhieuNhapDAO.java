package DAO;

import CONFIG.Config;
import DTO.PhieuNhapDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhieuNhapDAO {
    private DatabaseConnect db;

    public PhieuNhapDAO() {
        db = new DatabaseConnect();
    }

    // Thêm phiếu nhập
    public boolean addPhieuNhap(PhieuNhapDTO pn) {
        String sql = "INSERT INTO phieunhap (idPN, ngaytao, ngaycapnhat, idTK, idNCC, idTT) VALUES (?, ?, ?, ?, ?, ?)";
        List<Object> params = Arrays.asList(pn.getIdPN(), pn.getNgaytao(), pn.getNgaycapnhat(), pn.getIdTK(), pn.getIdNCC(), pn.getIdTT());
        return db.execute(sql, params);
    }

    // Sửa phiếu nhập
    public boolean updatePhieuNhap(PhieuNhapDTO pn) {
        String sql = "UPDATE phieunhap SET ngaytao = ?, ngaycapnhat = ?, idTK = ?, idNCC = ?, idTT = ? WHERE idPN = ?";
        List<Object> params = Arrays.asList(pn.getNgaytao(), pn.getNgaycapnhat(), pn.getIdTK(), pn.getIdNCC(), pn.getIdTT(), pn.getIdPN());
        return db.execute(sql, params);
    }

    // Xóa phiếu nhập
    public boolean deletePhieuNhap(int idPN) {
        String sql = "DELETE FROM phieunhap WHERE idPN = ?";
        List<Object> params = Arrays.asList(idPN);
        return db.execute(sql, params);
    }

    // Lấy tất cả phiếu nhập
    public List<PhieuNhapDTO> getAll() {
        List<PhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM phieunhap";
        try {
            ResultSet rs = db.getAll(sql, null);
            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO();
                pn.setIdPN(rs.getInt("idPN"));
                pn.setNgaytao(rs.getString("ngaytao"));
                pn.setNgaycapnhat(rs.getString("ngaycapnhat"));
                pn.setIdTK(rs.getInt("idTK"));
                pn.setIdNCC(rs.getInt("idNCC"));
                pn.setIdTT(rs.getInt("idTT"));
                list.add(pn);
            }
            db.close(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy idPN lớn nhất
    public int getMaxIdPN() {
        String sql = "SELECT MAX(idPN) AS max_id FROM phieunhap";
        try {
            ResultSet rs = db.getAll(sql, null);
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                db.close(rs);
                return maxId;
            }
            db.close(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0; // Nếu bảng rỗng
    }
}