
package BUS;

import DAO.NguyenLieuDAO;
import DTO.NguyenLieuDTO;
import java.util.List;

public class NguyenLieuBUS {
    private NguyenLieuDAO dao;
    private Lo_NguyenLieuBUS loBus;

    public NguyenLieuBUS() {
        dao = new NguyenLieuDAO();
        loBus = new Lo_NguyenLieuBUS();
    }

    public boolean addNguyenLieu(NguyenLieuDTO nl) {
        if (nl.getIdNL() <= 0 || nl.getTenNL() == null || nl.getTenNL().trim().isEmpty() || nl.getDonvi() == null || nl.getDonvi().trim().isEmpty()) {
            System.out.println("Dữ liệu nguyên liệu không hợp lệ");
            return false;
        }
        return dao.addNguyenLieu(nl);
    }

    public boolean updateNguyenLieu(NguyenLieuDTO nl) {
        if (nl.getIdNL() <= 0 || nl.getTenNL() == null || nl.getTenNL().trim().isEmpty() || nl.getDonvi() == null || nl.getDonvi().trim().isEmpty()) {
            System.out.println("Dữ liệu nguyên liệu không hợp lệ");
            return false;
        }
        return dao.updateNguyenLieu(nl);
    }

    public boolean deleteNguyenLieu(int idNL) {
        if (idNL <= 0) {
            System.out.println("ID nguyên liệu không hợp lệ");
            return false;
        }
        boolean loDeleted = loBus.deleteByIdNL(idNL);
        if (!loDeleted) {
            System.out.println("Không thể xóa lô nguyên liệu cho idNL: " + idNL);
        }
        return dao.deleteNguyenLieu(idNL);
    }

    public List<NguyenLieuDTO> getAllNguyenLieu() {
        return dao.getAllNguyenLieu();
    }

    public int getNextIdNL() {
        return dao.getNextIdNL();
    }

    public boolean resetData() {
        // Xóa tất cả lô nguyên liệu trước
        boolean loReset = loBus.resetData();
        if (!loReset) {
            System.out.println("Không thể đặt lại dữ liệu lô nguyên liệu");
        }
        // Xóa tất cả nguyên liệu
        boolean nlReset = dao.resetData();
        if (!nlReset) {
            System.out.println("Không thể đặt lại dữ liệu nguyên liệu");
            return false;
        }
        return true;
    }
}
