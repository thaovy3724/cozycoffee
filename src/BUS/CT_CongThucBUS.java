package BUS;

import DTO.CT_CongThucDTO;
import DAO.CT_CongThucDAO;
import java.util.List;

public class CT_CongThucBUS {
    private final CT_CongThucDAO ctCongThucDao = new CT_CongThucDAO();

    public boolean add(CT_CongThucDTO ctDetail) {
        if (ctDetail.getIdCT() <= 0) {
            System.out.println("Lỗi: idCT không hợp lệ: " + ctDetail.getIdCT());
            return false;
        }
        if (ctDetail.getIdNL() <= 0) {
            System.out.println("Lỗi: idNL không hợp lệ: " + ctDetail.getIdNL());
            return false;
        }
        if (ctDetail.getSoluong() <= 0) {
            System.out.println("Lỗi: Số lượng không hợp lệ: " + ctDetail.getSoluong());
            return false;
        }
        boolean success = ctCongThucDao.add(ctDetail);
        if (!success) {
            System.out.println("Lỗi: Không thể thêm chi tiết công thức cho idCT: " + ctDetail.getIdCT() + ", idNL: " + ctDetail.getIdNL());
        }
        return success;
    }

    public boolean deleteByCongThuc(int idCT) {
        return ctCongThucDao.deleteByCongThuc(idCT);
    }

    public List<CT_CongThucDTO> getChiTietCongThuc(int idCT) {
        return ctCongThucDao.getChiTietCongThuc(idCT);
    }
}