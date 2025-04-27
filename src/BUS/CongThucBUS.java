package BUS;

import DTO.CongThucDTO;
import DTO.CT_CongThucDTO;
import DAO.CongThucDAO;
import DAO.SanPhamDAO;
import java.util.List;

public class CongThucBUS {
    private final CongThucDAO congThucDao = new CongThucDAO();
    private final SanPhamDAO sanPhamDao = new SanPhamDAO();
    private final CT_CongThucBUS ctCongThucBus = new CT_CongThucBUS();
    private static int nextIdCT = 1; // Biến tĩnh để tự động tăng idCT

    public List<CongThucDTO> getAll() {
        return congThucDao.getAll();
    }

    public List<CongThucDTO> getAllActiveEdit(int idCT, int idSP) {
        return congThucDao.getAllActiveEdit(idCT, idSP);
    }

    public List<CongThucDTO> getAllActive() {
        return congThucDao.getAllActive();
    }

    public CongThucDTO findByIdCT(int idCT) {
        return congThucDao.findByIdCT(idCT);
    }

    public String add(CongThucDTO ct, List<CT_CongThucDTO> chiTietList) {
        if (ct.getMota() == null || ct.getMota().trim().isEmpty()) {
            return "Mô tả không được để trống";
        }

        if (!sanPhamDao.exists(ct.getIdSP())) {
            return "Sản phẩm không tồn tại";
        }

        List<CongThucDTO> congThucList = congThucDao.findByIdSP(ct.getIdSP());
        if (congThucList != null && !congThucList.isEmpty()) {
            return "Sản phẩm đã có công thức, không thể thêm công thức mới";
        }

        // Gán idCT tự động
        ct.setIdCT(nextIdCT++);

        boolean success = congThucDao.add(ct);
        if (success) {
            CongThucDTO addedCongThuc = congThucDao.findByIdSP(ct.getIdSP()).get(0);
            for (CT_CongThucDTO chiTiet : chiTietList) {
                chiTiet.setIdCT(addedCongThuc.getIdCT());
                ctCongThucBus.add(chiTiet);
            }
        }
        return success ? "" : "Không thể thêm công thức";
    }

    public String update(CongThucDTO ct, List<CT_CongThucDTO> chiTietList) {
        if (ct.getIdCT() <= 0 || congThucDao.findByIdCT(ct.getIdCT()) == null) {
            return "Công thức không tồn tại";
        }

        if (ct.getMota() == null || ct.getMota().trim().isEmpty()) {
            return "Mô tả không được để trống";
        }

        if (!sanPhamDao.exists(ct.getIdSP())) {
            return "Sản phẩm không tồn tại";
        }

        boolean success = congThucDao.update(ct);
        if (success) {
            ctCongThucBus.deleteByCongThuc(ct.getIdCT());
            for (CT_CongThucDTO chiTiet : chiTietList) {
                chiTiet.setIdCT(ct.getIdCT());
                ctCongThucBus.add(chiTiet);
            }
        }
        return success ? "" : "Không thể cập nhật công thức";
    }

    public boolean delete(int idCT) {
        CongThucDTO ct = congThucDao.findByIdCT(idCT);
        if (ct == null) {
            return false; // Công thức không tồn tại
        }
        int idSP = ct.getIdSP();
        // Kiểm tra idSP có tồn tại trong bảng sanpham
        if (!sanPhamDao.exists(idSP)) {
            return false; // Sản phẩm không tồn tại
        }
        // Kiểm tra xem idSP có công thức nào khác ngoài idCT hiện tại
        List<CongThucDTO> congThucList = congThucDao.findByIdSP(idSP);
        if (congThucList != null && congThucList.size() > 1) {
            // Có công thức khác liên kết với idSP, không cho phép xóa
            return false;
        }

        ctCongThucBus.deleteByCongThuc(idCT);
        return congThucDao.delete(idCT);
    }

    public List<CongThucDTO> search(String keyWord) {
        return congThucDao.search(keyWord);
    }
}