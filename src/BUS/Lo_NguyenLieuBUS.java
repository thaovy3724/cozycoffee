package BUS;


import java.util.List;

import DAO.Lo_NguyenLieuDAO;
import DTO.Lo_NguyenLieuDTO;

public class Lo_NguyenLieuBUS {
    private Lo_NguyenLieuDAO loNguyenLieuDao = new Lo_NguyenLieuDAO();

    public List<Lo_NguyenLieuDTO> getChiTietPhieuNhap(int idPN) {
        return loNguyenLieuDao.getChiTietPhieuNhap(idPN);
    }
}
