package BUS;

import java.util.List;

import DAO.CongThucDAO;
import DTO.CT_CongThucDTO;
import DTO.CongThucDTO;

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
        String error = "";
        List<CongThucDTO> congThucList = congThucDao.findByIdSP(ct.getIdSP());
        if (congThucList != null && !congThucList.isEmpty()) {
            error = "Sản phẩm đã có công thức, không thể thêm công thức mới";
        }else if(congThucDao.add(ct, chiTietList) == -1){
            error = "Xảy ra lỗi trong quá trình thêm mới";
        }
        return error;
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