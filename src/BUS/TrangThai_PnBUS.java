package BUS;

import DAO.TrangThai_PnDAO;
import DTO.TrangThai_PnDTO;

import java.util.List;

/**
 * @author: huonglamcoder
 */

public class TrangThai_PnBUS {
    private final TrangThai_PnDAO ttpnDao = new TrangThai_PnDAO();

    public List<TrangThai_PnDTO> getAll() {
        return ttpnDao.getAll();
    }
}
