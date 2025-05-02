package BUS;

import java.util.List;

import DAO.NhomQuyenDAO;
import DTO.NhomQuyenDTO;

public class NhomQuyenBUS {
    private final NhomQuyenDAO nhomQuyenDao = new NhomQuyenDAO();

    public List<NhomQuyenDTO> getAll(){
		return nhomQuyenDao.getAll();
	}

    public NhomQuyenDTO findByIdNQ(int idNQ) {
		return nhomQuyenDao.findByIdNQ(idNQ);
	}

}