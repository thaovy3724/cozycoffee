package BUS;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import java.util.List;

public class PhieuNhapBUS {
    private PhieuNhapDAO dao;

    public PhieuNhapBUS() {
        dao = new PhieuNhapDAO();
    }

    public boolean addPhieuNhap(PhieuNhapDTO pn) {
        // Kiểm tra dữ liệu đầu vào
        if (pn.getIdPN() <= 0 || pn.getNgaytao() == null || pn.getNgaytao().trim().isEmpty() ||
            pn.getIdTK() <= 0 || pn.getIdNCC() <= 0 || pn.getIdTT() <= 0) {
            System.out.println("Dữ liệu phiếu nhập không hợp lệ");
            return false;
        }
        return dao.addPhieuNhap(pn);
    }

    public boolean updatePhieuNhap(PhieuNhapDTO pn) {
        // Kiểm tra dữ liệu đầu vào
        if (pn.getIdPN() <= 0 || pn.getNgaytao() == null || pn.getNgaytao().trim().isEmpty() ||
            pn.getIdTK() <= 0 || pn.getIdNCC() <= 0 || pn.getIdTT() <= 0) {
            System.out.println("Dữ liệu phiếu nhập không hợp lệ");
            return false;
        }
        return dao.updatePhieuNhap(pn);
    }

    public boolean deletePhieuNhap(int idPN) {
        if (idPN <= 0) {
            System.out.println("ID phiếu nhập không hợp lệ");
            return false;
        }
        return dao.deletePhieuNhap(idPN);
    }

    public List<PhieuNhapDTO> getAllPhieuNhap() {
        return dao.getAll();
    }

    public int getNextIdPN() {
        return dao.getMaxIdPN() + 1;
    }
}