package BUS;

import java.sql.Date;
import java.util.List;

import DAO.PhieuNhapDAO;
import DTO.Lo_NguyenLieuDTO;
import DTO.PhieuNhapDTO;

public class PhieuNhapBUS {
	private final PhieuNhapDAO phieuNhapDao = new PhieuNhapDAO();

	public List<PhieuNhapDTO> getAll() {
		return phieuNhapDao.getAll();
	}

	public int add(PhieuNhapDTO phieuNhap, List<Lo_NguyenLieuDTO> chitiet){
        return phieuNhapDao.add(phieuNhap, chitiet);
    }

    public long getAllPNTongTien(List<PhieuNhapDTO> phieuNhapDTOList) {
        long tongtien = 0;
        for (PhieuNhapDTO pn : phieuNhapDTOList) {
            tongtien += phieuNhapDao.getTongTienByIDPN(pn.getIdPN());
        }

        return tongtien;
    }

    public List<PhieuNhapDTO> searchPhieuNhapByDate(Date start, Date end) {
        return phieuNhapDao.searchByDate(start, end);
    }
}