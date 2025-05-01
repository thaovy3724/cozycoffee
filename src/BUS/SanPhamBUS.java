package BUS;

import java.util.List;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

public class SanPhamBUS {
    private final SanPhamDAO sanPhamDao = new SanPhamDAO();

    public List<SanPhamDTO> getAll() {
        return sanPhamDao.getAll();
    }

    public List<SanPhamDTO> getAllActive() {
        return sanPhamDao.getAllActive();
    }

    public SanPhamDTO findByIdSP(int idSP) {
        return sanPhamDao.findByIdSP(idSP);
    }

    public String add(SanPhamDTO sp) {
        if (sp.getTenSP() == null || sp.getTenSP().trim().isEmpty()) {
            return "Tên sản phẩm không được để trống";
        }

        if (sp.getGiaban() <= 0) {
            return "Giá bán phải lớn hơn 0";
        }

        if (sp.getHinhanh() == null || sp.getHinhanh().trim().isEmpty()) {
            return "Hình ảnh không được để trống";
        }

        if (sp.getIdDM() <= 0) {
            return "Danh mục không hợp lệ";
        }

        boolean success = sanPhamDao.add(sp);
        return success ? "" : "Không thể thêm sản phẩm";
    }

    public String update(SanPhamDTO sp) {
        if (sanPhamDao.findByIdSP(sp.getIdSP()) == null) {
            return "Sản phẩm không tồn tại";
        }

        if (sp.getTenSP() == null || sp.getTenSP().trim().isEmpty()) {
            return "Tên sản phẩm không được để trống";
        }

        if (sp.getGiaban() <= 0) {
            return "Giá bán phải lớn hơn 0";
        }

        if (sp.getHinhanh() == null || sp.getHinhanh().trim().isEmpty()) {
            return "Hình ảnh không được để trống";
        }

        if (sp.getIdDM() <= 0) {
            return "Danh mục không hợp lệ";
        }

        boolean success = sanPhamDao.update(sp);
        return success ? "" : "Không thể cập nhật sản phẩm";
    }

    public boolean delete(int idSP) {
        if (sanPhamDao.findByIdSP(idSP) == null) {
            return false;
        }
        return sanPhamDao.delete(idSP);
    }

    public List<SanPhamDTO> search(String keyword) {
        return sanPhamDao.search(keyword);
    }

    public boolean exists(int idSP) {
        return sanPhamDao.exists(idSP);
    }
}
