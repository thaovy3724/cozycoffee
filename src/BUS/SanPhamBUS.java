package BUS;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

import java.util.List;

public class SanPhamBUS {
    private final SanPhamDAO sanPhamDAO = new SanPhamDAO();

    public List<SanPhamDTO> getAllSP() {
        return sanPhamDAO.getAll();
    }

    public SanPhamDTO getAllSPByID(int idSP) {
        return sanPhamDAO.getSPByID("idSP", idSP);
    }
}
