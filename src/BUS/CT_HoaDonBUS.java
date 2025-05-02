package BUS;
import DTO.CT_HoaDonDTO;
import DAO.CT_HoaDonDAO;
import java.util.List;

public class CT_HoaDonBUS {
	private final CT_HoaDonDAO CT_HoaDonDao = new CT_HoaDonDAO();
	
	public List<CT_HoaDonDTO> getAllByIdHD(int idHD) {
		return CT_HoaDonDao.getAllByIdHD(idHD);
	}
}