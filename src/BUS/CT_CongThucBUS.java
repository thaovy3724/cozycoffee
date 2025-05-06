package BUS;

import DAO.CT_CongThucDAO;
import DTO.CT_CongThucDTO;
import java.util.List;

public class CT_CongThucBUS {
    private final CT_CongThucDAO ctCongThucDao = new CT_CongThucDAO();

    public List<CT_CongThucDTO> getChiTietCongThuc(int idCT) {
        return ctCongThucDao.getChiTietCongThuc(idCT);
    }
}