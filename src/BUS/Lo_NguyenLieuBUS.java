package BUS;
import java.util.List;

import DAO.Lo_NguyenLieuDAO;
import DTO.Lo_NguyenLieuDTO;

public class Lo_NguyenLieuBUS {
	private final Lo_NguyenLieuDAO lo_NguyenLieuDao = new Lo_NguyenLieuDAO();

	public List<Lo_NguyenLieuDTO> getAllByIdPN(int idPN) {
		return lo_NguyenLieuDao.getAllByIdPN(idPN);
	}
}
