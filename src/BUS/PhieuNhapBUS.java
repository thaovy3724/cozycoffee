package BUS;

import DAO.Lo_NguyenLieuDAO;
import DAO.PhieuNhapDAO;
import DTO.Lo_NguyenLieuDTO;
import DTO.PhieuNhapDTO;

import java.sql.Date;
import java.util.List;

public class PhieuNhapBUS {
    private final PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();
    private final Lo_NguyenLieuDAO lo_nguyenLieuDAO = new Lo_NguyenLieuDAO();

    public List<PhieuNhapDTO> getAllPhieuNhap() {
        return phieuNhapDAO.getAll();
    }

    public List<Lo_NguyenLieuDTO> getAllLo_NLByIDPN(int idPN) {
        return lo_nguyenLieuDAO.getAllByIDPN(idPN);
    }

    public List<Lo_NguyenLieuDTO> getAllLo_nguyenLieuByDate(Date start, Date end) {
        return lo_nguyenLieuDAO.getAllByDate(start, end);
    }

    public long getAllPNTongTien(List<PhieuNhapDTO> phieuNhapDTOList) {
        long tongtien = 0;
        for (PhieuNhapDTO pn : phieuNhapDTOList) {
            tongtien += phieuNhapDAO.getTongTienByIDPN(pn.getIdPN());
        }

        return tongtien;
    }

    public PhieuNhapDTO findPNByID(int idPN) {
        return phieuNhapDAO.findByID(idPN);
    }

    public List<PhieuNhapDTO> searchPhieuNhapByDate(Date start, Date end) {
        return phieuNhapDAO.searchByDate(start, end);
    }
}
