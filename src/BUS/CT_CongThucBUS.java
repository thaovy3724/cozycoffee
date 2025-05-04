package BUS;

import java.util.List;

import DAO.CT_CongThucDAO;
import DTO.CT_CongThucDTO;

public class CT_CongThucBUS {
    private final CT_CongThucDAO ctCongThucDao = new CT_CongThucDAO();

    public String add(CT_CongThucDTO ctDetail) {
        return ctCongThucDao.add(ctDetail);
    }

    public boolean deleteByCongThuc(int idCT) {
        return ctCongThucDao.deleteByCongThuc(idCT);
    }

    public List<CT_CongThucDTO> getChiTietCongThuc(int idCT) {
        return ctCongThucDao.getChiTietCongThuc(idCT);
    }
}