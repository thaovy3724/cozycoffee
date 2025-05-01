package BUS;

import java.util.List;

import DAO.CT_CongThucDAO;
import DAO.NguyenLieuDAO;
import DTO.CT_CongThucDTO;

public class CT_CongThucBUS {
    private final CT_CongThucDAO ctCongThucDao = new CT_CongThucDAO();
    private final NguyenLieuDAO nguyenLieuDao = new NguyenLieuDAO();

    public String add(CT_CongThucDTO ctDetail) {
        if (ctDetail.getIdCT() <= 0) {
            return "ID công thức không hợp lệ";
        }
        if (ctDetail.getIdNL() <= 0) {
            return "ID nguyên liệu không hợp lệ";
        }
        if (ctDetail.getSoluong() <= 0) {
            return "Số lượng phải lớn hơn 0";
        }
        if (nguyenLieuDao.findByIdNL(ctDetail.getIdNL()) == null) {
            return "Nguyên liệu không tồn tại";
        }
        return ctCongThucDao.add(ctDetail);
    }

    public boolean deleteByCongThuc(int idCT) {
        return ctCongThucDao.deleteByCongThuc(idCT);
    }

    public List<CT_CongThucDTO> getChiTietCongThuc(int idCT) {
        return ctCongThucDao.getChiTietCongThuc(idCT);
    }
}