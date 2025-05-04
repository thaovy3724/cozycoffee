package BUS;

import java.util.List;

import DAO.ThongKe.ThongKeTheoNamDAO;
import DAO.ThongKe.ThongKeTheoThangDAO;
import DTO.ThongKe.ThongKeTheoNamDTO;
import DTO.ThongKe.ThongKeTheoThangDTO;

/**
 * @author: huonglamcoder
 */

// HUONGNGUYEN 3/5
public class ThongKeBUS {
    ThongKeTheoNamDAO thongKeTheoNamDAO = new ThongKeTheoNamDAO();
    ThongKeTheoThangDAO thongKeTheoThangDAO = new ThongKeTheoThangDAO();

    public List<ThongKeTheoNamDTO> getThongKeTheoNam(int nam) {
        return thongKeTheoNamDAO.getThongKeTheoNam(nam);
    }

    public List<ThongKeTheoThangDTO> getThongKeTheoThang(int nam, int thang) {
        return thongKeTheoThangDAO.getThongKeTheoThang(nam, thang);
    }
}
