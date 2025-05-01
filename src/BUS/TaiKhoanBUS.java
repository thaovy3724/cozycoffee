package BUS;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class TaiKhoanBUS {
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public List<TaiKhoanDTO> getAll() {
        return taiKhoanDAO.getAll();
    }

    public String add(TaiKhoanDTO taiKhoan) {
		// kiểm tra email đã tồn tại chưa nếu có trả về thông báo lỗi
		String error = "";
		if(taiKhoanDAO.isExist(taiKhoan))
			error = "Email đã tồn tại";
		else if(!taiKhoanDAO.add(taiKhoan)) { 		// thêm mới vào CSDL
			error = "Xảy ra lỗi trong quá trình thêm mới";
		}
		return error;
	}

    public TaiKhoanDTO findByIdTK(int idTK) {
		return taiKhoanDAO.findByIdTK(idTK);
	}
	
	public String updateInfo(TaiKhoanDTO taiKhoan) {
		// kiểm tra email đã tồn tại chưa nếu có trả về thông báo lỗi
		String error = "";
		if(taiKhoanDAO.isExist(taiKhoan))
			error = "Email đã tồn tại";
		else if(!taiKhoanDAO.updateInfo(taiKhoan))
			error = "Xảy ra lỗi trong quá trình cập nhật";
		
		return error;
	}

	public String updatePassword(TaiKhoanDTO taiKhoan) {
		String error = "";
		if (!taiKhoanDAO.updatePassword(taiKhoan)) {
			error = "Xảy ra lỗi trong quá trình đổi mật khẩu";
		}

		return error;
	}

	public boolean delete(int idTK) {
		boolean success = false;
		// kiểm tra nếu tài khoản của nhân viên đã tồn tại 
		// trong hóa đơn bất kì (có khóa ngoại)
		// -> ko xóa được
		if(!taiKhoanDAO.isEmployeeInReceipt(idTK)) {
			if(taiKhoanDAO.delete(idTK))
				success = true;
		}
		return success;
	}
	
	public List<TaiKhoanDTO> search(String keyWord){
		return taiKhoanDAO.search(keyWord);
	}

    public List<TaiKhoanDTO> getAllTaiKhoanByIDNQ(int idNQ) {
        return taiKhoanDAO.getAllByIDNQ(idNQ);
    }

	// TrongHiuuu 27/04/2025: Thêm hash password và check hashedPassword
    // Hash mật khẩu bằng BCrypt
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Kiểm tra mật khẩu
    public boolean checkPassword(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
    }

	//TrongHiuuu 27/04/2025: Thêm struct mới để thông báo lỗi đăng nhập chi tiết hơn
	public class LoginResult {
		private TaiKhoanDTO taiKhoan;
		private String message;

		public LoginResult() {
			this.taiKhoan = null;
			this.message = "";
		}

		public LoginResult(TaiKhoanDTO taiKhoan, String message) {
			this.taiKhoan = taiKhoan;
			this.message = message;
		}

		public TaiKhoanDTO getTaiKhoan() {
			return taiKhoan;
		}

		public String getMessage() {
			return message;
		}

		public void setTaiKhoan(TaiKhoanDTO taiKhoan) {
			this.taiKhoan = taiKhoan;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

    public LoginResult checkLogin(String tenTK, String matkhau) {
		LoginResult result = new LoginResult();
		TaiKhoanDTO taiKhoan = taiKhoanDAO.findByTenTK(tenTK);
		if (taiKhoan == null) {
			result.setMessage("Tài khoản không tồn tại");
		} else {
			try {
				if (checkPassword(matkhau, taiKhoan.getMatkhau())) {
					result.setTaiKhoan(taiKhoan);
					result.setMessage("Đăng nhập thành công");
				}
				else result.setMessage("Mật khẩu không chính xác");
			} catch (IllegalArgumentException e) {
				//Nếu như tài khoản có password chưa được hash thì sẽ có lỗi "Invalid salt version
				result.setMessage("Mật khẩu không chính xác");
				e.printStackTrace();
			}
		}
		return result;
    }
}