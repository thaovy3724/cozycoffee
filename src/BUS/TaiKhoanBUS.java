package BUS;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;

import java.util.List;

public class TaiKhoanBUS {
    private final TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanBUS() {
        taiKhoanDAO = new TaiKhoanDAO();
    }

    public List<TaiKhoanDTO> getAll() {
        return taiKhoanDAO.getAll();
    }

    public boolean add(TaiKhoanDTO taiKhoan) {
        // Kiểm tra logic nghiệp vụ trước khi thêm
        if (taiKhoanDAO.isExist("idTK", taiKhoan.getIdTK())) {
            return false; // Tài khoản đã tồn tại
        }
        return taiKhoanDAO.add(
                List.of(
                        taiKhoan.getIdTK(),
                        taiKhoan.getTenTK(),
                        taiKhoan.getHoten(),
                        taiKhoan.getEmail(),
                        taiKhoan.getDienthoai(),
                        taiKhoan.getTrangthai(),
                        taiKhoan.getIdNQ()
                )
        );
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
        return taiKhoanDAO.lock(taiKhoan.getIdTK());
    }

    public boolean unlock(TaiKhoanDTO taiKhoan) {
        if (!taiKhoanDAO.isExist("idTK", taiKhoan.getIdTK())) {
            return false; // Tài khoản không tồn tại
        }
        return taiKhoanDAO.unlock(taiKhoan.getIdTK());
    }
}
