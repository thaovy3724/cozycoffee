package BUS;

import DAO.NhomQuyenDAO;
import DAO.TaiKhoanDAO;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;
//import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    // Hash mật khẩu bằng BCrypt
//    private String hashPassword(String plainPassword) {
//        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
//    }
//
//    // Kiểm tra mật khẩu
//    private boolean checkPassword(String plainPassword, String hashedPassword) {
//        return BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword).verified;
//    }

    public List<String> add(TaiKhoanDTO taiKhoan) {
        // Kiểm tra logic nghiệp vụ trước khi thêm
        List<String> message = new ArrayList<>();
//        Map<String, Object> equals = new HashMap<>();
//        equals.put("tenTK", taiKhoan.getIdTK());
//        equals.put("email", taiKhoan.getEmail());
//        equals.put("dienthoai", taiKhoan.getDienthoai());
        if (taiKhoanDAO.isExist("tenTK", taiKhoan.getTenTK())) {
            message.add("Tên tài khoản đã tồn tại");
        }
        if (taiKhoanDAO.isExist("email", taiKhoan.getEmail())) {
            message.add("Email đã tồn tại");
        }
        if (taiKhoanDAO.isExist("dienthoai", taiKhoan.getDienthoai())) {
            message.add("Điện thoại đã tồn tại");
        }

        if (message.isEmpty()) {
//            String hashedPassword = hashPassword(taiKhoan.getMatkhau());
//            taiKhoan.setMatkhau(hashedPassword);
            taiKhoanDAO.add(taiKhoan);
        }

        return message;
    }

    public List<String> update(TaiKhoanDTO taiKhoan) {
        List<String> message = new ArrayList<>();
        if (taiKhoanDAO.isExist("tenTK", taiKhoan.getTenTK(), "idTK", taiKhoan.getIdTK())) {
            message.add("Tên tài khoản đã tồn tại");
        }
        if (taiKhoanDAO.isExist("email", taiKhoan.getEmail(), "idTK", taiKhoan.getIdTK())) {
            message.add("Email đã tồn tại");
        }
        if (taiKhoanDAO.isExist("dienthoai", taiKhoan.getDienthoai(), "idTK", taiKhoan.getIdTK())) {
            message.add("Điện thoại đã tồn tại");
        }
        if(message.isEmpty()) {
//            if (taiKhoan.getMatkhau() != null && !taiKhoan.getMatkhau().isEmpty()) {
//                taiKhoan.setMatkhau(hashPassword(taiKhoan.getMatkhau()));
//            }
            taiKhoanDAO.update(taiKhoan);
        }

        return message;
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

    public List<TaiKhoanDTO> search(String kyw) {
        return taiKhoanDAO.search(kyw);
    }

    public TaiKhoanDTO checkLogin(String tenTK, String matkhau) {
        return taiKhoanDAO.checkLogin(tenTK, matkhau);
    }
}
