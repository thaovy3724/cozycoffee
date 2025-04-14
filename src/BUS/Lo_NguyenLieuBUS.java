package BUS;

import DAO.Lo_NguyenLieuDAO;
import DTO.Lo_NguyenLieuDTO;
import java.util.List;

public class Lo_NguyenLieuBUS {
    private Lo_NguyenLieuDAO dao;

    public Lo_NguyenLieuBUS() {
        dao = new Lo_NguyenLieuDAO();
    }

    public boolean addLoNguyenLieu(Lo_NguyenLieuDTO lo) {
        if (lo.getIdNL() <= 0 || lo.getIdPN() <= 0 || lo.getSoluongnhap() <= 0 || lo.getTonkho() < 0 || lo.getDongia() <= 0 || lo.getHsd() == null || lo.getHsd().trim().isEmpty()) {
            System.out.println("Dữ liệu lô nguyên liệu không hợp lệ");
            return false;
        }
        return dao.addLoNguyenLieu(lo);
    }

    public boolean updateLoNguyenLieu(Lo_NguyenLieuDTO lo) {
        if (lo.getIdNL() <= 0 || lo.getIdPN() <= 0 || lo.getSoluongnhap() <= 0 || lo.getTonkho() < 0 || lo.getDongia() <= 0 || lo.getHsd() == null || lo.getHsd().trim().isEmpty()) {
            System.out.println("Dữ liệu lô nguyên liệu không hợp lệ");
            return false;
        }
        return dao.updateLoNguyenLieu(lo);
    }

    public boolean deleteLoNguyenLieu(int idNL) {
        if (idNL <= 0) {
            System.out.println("ID nguyên liệu không hợp lệ");
            return false;
        }
        return dao.deleteLoNguyenLieu(idNL);
    }

    public boolean deleteByIdNL(int idNL) {
        if (idNL <= 0) {
            System.out.println("ID nguyên liệu không hợp lệ");
            return false;
        }
        return dao.deleteByIdNL(idNL);
    }

    public boolean updateTonKho(int idNL, float tonKho) {
        if (idNL <= 0 || tonKho < 0) {
            System.out.println("ID hoặc tồn kho không hợp lệ");
            return false;
        }
        return dao.updateTonKho(idNL, tonKho);
    }

    public boolean updateHSD(int idNL, String hsd) {
        if (idNL <= 0 || hsd == null || hsd.trim().isEmpty()) {
            System.out.println("ID hoặc HSD không hợp lệ");
            return false;
        }
        return dao.updateHSD(idNL, hsd);
    }

    public List<Lo_NguyenLieuDTO> getAllLoNguyenLieu() {
        List<Lo_NguyenLieuDTO> list = dao.getAllLoNguyenLieu();
        System.out.println("getAllLoNguyenLieu: Retrieved " + (list != null ? list.size() : 0) + " records");
        return list;
    }

    public boolean resetData() {
        return dao.resetData();
    }
}