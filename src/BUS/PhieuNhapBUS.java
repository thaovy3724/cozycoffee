package BUS;

import java.sql.Date;
import java.util.List;

import DAO.Lo_NguyenLieuDAO;
import DAO.NhaCungCapDAO;
import DAO.PhieuNhapDAO;
import DTO.Lo_NguyenLieuDTO;
import DTO.PhieuNhapDTO;
import DTO.TrangThai_PnDTO;

public class PhieuNhapBUS {
	private final PhieuNhapDAO phieuNhapDao = new PhieuNhapDAO();
    private final Lo_NguyenLieuDAO loNguyenLieuDao = new Lo_NguyenLieuDAO();

	public List<PhieuNhapDTO> getAll() {
		return phieuNhapDao.getAll();
	}

	public int add(PhieuNhapDTO phieuNhap, List<Lo_NguyenLieuDTO> chitiet){
        return phieuNhapDao.add(phieuNhap, chitiet);
    }

    public PhieuNhapDTO findByIdPN(int idPN) {
        return phieuNhapDao.findByIdPN(idPN);
    }

    public String update(PhieuNhapDTO pn, List<Lo_NguyenLieuDTO> loNguyenLieuList) {
        if (pn.getIdPN() <= 0 || phieuNhapDao.findByIdPN(pn.getIdPN()) == null) {
            return "Phiếu nhập không tồn tại";
        }

        if (!new NhaCungCapDAO().exists(pn.getIdNCC())) {
            return "Nhà cung cấp không tồn tại";
        }

        boolean success = phieuNhapDao.update(pn, loNguyenLieuList);

        return success ? "" : "Không thể cập nhật công thức";
    }

    public boolean delete(int idPN) {
        return loNguyenLieuDao.delete(idPN) && phieuNhapDao.delete(idPN);
    }

    // Hiếu
    public List<PhieuNhapDTO> searchCompleteByDate(Date start, Date end) {
        return phieuNhapDao.searchCompleteByDate(start, end);
    }

    public long getAllTongTien(List<PhieuNhapDTO> phieuNhapDTOList) {
        long tongtien = 0;
        for (PhieuNhapDTO pn : phieuNhapDTOList) {
            tongtien += phieuNhapDao.getTotalAmount(pn.getIdPN());
        }

        return tongtien;
    }

//    HUONGNGUYEN 2/5
    public List<PhieuNhapDTO> search(Date dateStart, Date dateEnd,
                                     TrangThai_PnDTO ttpn) {
        return phieuNhapDao.search(dateStart, dateEnd, ttpn);
    }
}