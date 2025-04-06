package BUS;

import DAO.NhomQuyenDAO;
import DAO.TaiKhoanDAO;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;

import java.util.List;

public class TaiKhoanBUS {
    private final TaiKhoanDAO taiKhoanDAO;
    private final NhomQuyenDAO nhomQuyenDAO;

    public TaiKhoanBUS() {
        taiKhoanDAO = new TaiKhoanDAO();
        nhomQuyenDAO = new NhomQuyenDAO();
    }

    public List<TaiKhoanDTO> getAllTaiKhoan() {
        return taiKhoanDAO.getAll();
    }

    public List<NhomQuyenDTO> getAllNhomQuyen() {
        return nhomQuyenDAO.getAll();
    }

    public boolean add(TaiKhoanDTO taiKhoan) {
        // Kiểm tra logic nghiệp vụ trước khi thêm
        if (taiKhoanDAO.isExist("idTK", taiKhoan.getIdTK())) {
            return false; // Tài khoản đã tồn tại
        }
        return taiKhoanDAO.add(taiKhoan);
    }

    public boolean update(TaiKhoanDTO taiKhoan) {
        // Kiểm tra logic nghiệp vụ trước khi cập nhật
        if (!taiKhoanDAO.isExist("idTK", taiKhoan.getIdTK())) {
            return false; // Tài khoản không tồn tại
        }
        return taiKhoanDAO.update(taiKhoan);
    }

    public boolean lock(TaiKhoanDTO taiKhoan) {
        if (!taiKhoanDAO.isExist("idTK", taiKhoan.getIdTK())) {
            return false; // Tài khoản không tồn tại
        }
        return taiKhoanDAO.lock(taiKhoan);
    }

    public boolean unlock(TaiKhoanDTO taiKhoan) {
        if (!taiKhoanDAO.isExist("idTK", taiKhoan.getIdTK())) {
            return false; // Tài khoản không tồn tại
        }
        return taiKhoanDAO.unlock(taiKhoan);
    }
}
