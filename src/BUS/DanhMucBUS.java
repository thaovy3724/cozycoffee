package BUS;

import DAO.DanhMucDAO;
import DTO.DanhMucDTO;
import java.util.List;

public class DanhMucBUS {
    DanhMucDAO danhMucDAO = new DanhMucDAO();

    public List<DanhMucDTO> getAllDanhMuc(){
        return danhMucDAO.getAll();
    }
}