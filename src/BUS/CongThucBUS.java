package BUS;

import DTO.CongThucDTO;
import DTO.CT_CongThucDTO;
import DAO.CongThucDAO;
import DAO.SanPhamDAO;
import java.util.List;

public class CongThucBUS {
    private final CongThucDAO congThucDao = new CongThucDAO();
    private final CT_CongThucBUS ctCongThucBus = new CT_CongThucBUS();

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
        List<CongThucDTO> congThucList = congThucDao.findByIdSP(ct.getIdSP());
        if (congThucList != null && !congThucList.isEmpty()) {
            return "Sản phẩm đã có công thức, không thể thêm công thức mới";
        }

        // Gọi CongThucDAO.add và lấy idCT được sinh tự động
        int newIdCT = congThucDao.add(ct);
        if (newIdCT == -1) {
            return "Không thể thêm công thức";
        }

        // Cập nhật idCT cho chi tiết công thức và thêm vào ct_congthuc
        for (CT_CongThucDTO chiTiet : chiTietList) {
            chiTiet.setIdCT(newIdCT);
            String error = ctCongThucBus.add(chiTiet);
            if (!error.isEmpty()) {
                // Xóa công thức đã thêm để đảm bảo tính toàn vẹn
                congThucDao.delete(newIdCT);
                return error;
            }
        }
        return "";
    }

    public String update(CongThucDTO ct, List<CT_CongThucDTO> chiTietList) {
        if (ct.getIdCT() <= 0 || congThucDao.findByIdCT(ct.getIdCT()) == null) {
            return "Công thức không tồn tại";
        }

        boolean success = congThucDao.update(ct);
        if (success) {
            ctCongThucBus.deleteByCongThuc(ct.getIdCT());
            for (CT_CongThucDTO chiTiet : chiTietList) {
                chiTiet.setIdCT(ct.getIdCT());
                String error = ctCongThucBus.add(chiTiet);
                if (!error.isEmpty()) {
                    return error;
                }
            }
        }
        return success ? "" : "Không thể cập nhật công thức";
    }

    public boolean delete(int idCT) {
        return congThucDao.delete(idCT);
    }

    public List<CongThucDTO> search(String keyWord) {
        return congThucDao.search(keyWord);
    }
}