package BUS;

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
}
