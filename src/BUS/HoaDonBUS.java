package BUS;

import java.util.List;

import DAO.HoaDonDAO;
import DTO.CT_HoaDonDTO;
import DTO.HoaDonDTO;

public class HoaDonBUS {
    private final HoaDonDAO hoaDonDao = new HoaDonDAO();
    
    public int add(HoaDonDTO hoaDon, List<CT_HoaDonDTO> chitiet){
        return hoaDonDao.add(hoaDon, chitiet);
    }
}
