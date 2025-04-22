package BUS;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
//import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.List;

public class TaiKhoanBUS {
    private final TaiKhoanDAO taiKhoanDao = new TaiKhoanDAO();

    public List<TaiKhoanDTO> getAll() {
        return taiKhoanDao.getAll();
    }

    public String add(TaiKhoanDTO taiKhoan) {
		// kiểm tra email đã tồn tại chưa nếu có trả về thông báo lỗi
		String error = "";
		if(taiKhoanDao.isExist(taiKhoan)) 
			error = "Email đã tồn tại";
		else if(!taiKhoanDao.add(taiKhoan)) { 		// thêm mới vào CSDL
			error = "Xảy ra lỗi trong quá trình thêm mới";
		}
		return error;
	}

    public TaiKhoanDTO findByIdTK(int idTK) {
		return taiKhoanDao.findByIdTK(idTK);
	}
	
	public String update(TaiKhoanDTO taiKhoan) {
		// kiểm tra email đã tồn tại chưa nếu có trả về thông báo lỗi
		String error = "";
		if(taiKhoanDao.isExist(taiKhoan)) 
			error = "Email đã tồn tại";
		else if(!taiKhoanDao.update(taiKhoan)) 
			error = "Xảy ra lỗi trong quá trình cập nhật";
		
		return error;
	}

	public boolean delete(int idTK) {
		boolean success = false;
		// kiểm tra nếu tài khoản của nhân viên đã tồn tại 
		// trong hóa đơn bất kì (có khóa ngoại)
		// -> ko xóa được
		if(!taiKhoanDao.isEmployeeInReceipt(idTK)) {
			if(taiKhoanDao.delete(idTK)) 
				success = true;
		}
		return success;
	}
	
	public List<TaiKhoanDTO> search(String keyWord){
		return taiKhoanDao.search(keyWord);
	}

//    public List<TaiKhoanDTO> getAllTaiKhoanByIDNQ(int idNQ) {
//        return taiKhoanDAO.getAllByIDNQ(idNQ);
//    }

//    // Hash mật khẩu bằng BCrypt
//    private String hashPassword(String plainPassword) {
//        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
//    }
//
//    // Kiểm tra mật khẩu
//    private boolean checkPassword(String plainPassword, String hashedPassword) {
//        return BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword).verified;
//    }

//    public List<String> add(TaiKhoanDTO taiKhoan) {
//        // Kiểm tra logic nghiệp vụ trước khi thêm
//        List<String> message = new ArrayList<>();
////        Map<String, Object> equals = new HashMap<>();
////        equals.put("tenTK", taiKhoan.getIdTK());
////        equals.put("email", taiKhoan.getEmail());
////        equals.put("dienthoai", taiKhoan.getDienthoai());
//        if (taiKhoanDAO.isExist("tenTK", taiKhoan.getTenTK())) {
//            message.add("Tên tài khoản đã tồn tại");
//        }
//        if (taiKhoanDAO.isExist("email", taiKhoan.getEmail())) {
//            message.add("Email đã tồn tại");
//        }
//        if (taiKhoanDAO.isExist("dienthoai", taiKhoan.getDienthoai())) {
//            message.add("Điện thoại đã tồn tại");
//        }
//
//        if (message.isEmpty()) {
////            String hashedPassword = hashPassword(taiKhoan.getMatkhau());
////            taiKhoan.setMatkhau(hashedPassword);
//            taiKhoanDAO.add(taiKhoan);
//        }
//
//        return message;
//    }
//
//    public List<String> update(TaiKhoanDTO taiKhoan) {
//        List<String> message = new ArrayList<>();
//        if (taiKhoanDAO.isExist("tenTK", taiKhoan.getTenTK(), "idTK", taiKhoan.getIdTK())) {
//            message.add("Tên tài khoản đã tồn tại");
//        }
//        if (taiKhoanDAO.isExist("email", taiKhoan.getEmail(), "idTK", taiKhoan.getIdTK())) {
//            message.add("Email đã tồn tại");
//        }
//        if (taiKhoanDAO.isExist("dienthoai", taiKhoan.getDienthoai(), "idTK", taiKhoan.getIdTK())) {
//            message.add("Điện thoại đã tồn tại");
//        }
//        if(message.isEmpty()) {
////            if (taiKhoan.getMatkhau() != null && !taiKhoan.getMatkhau().isEmpty()) {
////                taiKhoan.setMatkhau(hashPassword(taiKhoan.getMatkhau()));
////            }
//            taiKhoanDAO.update(taiKhoan);
//        }
//
//        return message;
//    }
//
//    public boolean lock(TaiKhoanDTO taiKhoan) {
//        if (!taiKhoanDAO.isExist("idTK", taiKhoan.getIdTK())) {
//            return false; // Tài khoản không tồn tại
//        }
//        return taiKhoanDAO.lock(taiKhoan);
//    }
//
//    public boolean unlock(TaiKhoanDTO taiKhoan) {
//        if (!taiKhoanDAO.isExist("idTK", taiKhoan.getIdTK())) {
//            return false; // Tài khoản không tồn tại
//        }
//        return taiKhoanDAO.unlock(taiKhoan);
//    }
//
//    public List<TaiKhoanDTO> search(String kyw) {
//        return taiKhoanDAO.search(kyw);
//    }
//
//    public TaiKhoanDTO checkLogin(String tenTK, String matkhau) {
//        return taiKhoanDAO.checkLogin(tenTK, matkhau);
//    }
}