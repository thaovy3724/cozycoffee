package BUS;

import java.util.List;

import DAO.TrangThai_PnDAO;
import DTO.TrangThai_PnDTO;

/**
 * @author: huonglamcoder
 */

public class TrangThai_PnBUS {
    private final TrangThai_PnDAO ttpnDao = new TrangThai_PnDAO();

    public List<TrangThai_PnDTO> getAll() {
        return ttpnDao.getAll();
    }
}