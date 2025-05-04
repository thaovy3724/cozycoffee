package BUS;

import java.util.List;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;

public class NhaCungCapBUS {
	private final NhaCungCapDAO nhaCungCapDao = new NhaCungCapDAO();

	public List<NhaCungCapDTO> getAll(){
		return nhaCungCapDao.getAll();
	}

	// HUONGNGUYEN: 29/04
	public List<NhaCungCapDTO> getAllActive(){
		return nhaCungCapDao.getAllActive();
	}

	public List<NhaCungCapDTO> getAllActiveExcept(int idNCC) {
		return nhaCungCapDao.getAllActiveExcept(idNCC);
	}

	public String add(NhaCungCapDTO nhaCungCap) {
		// kiểm tra tên nhà cung cấp hoặc email hoặc số điện thoại đã tồn tại chưa
		String error = "";
		if(nhaCungCapDao.isExist(nhaCungCap)) {
			error = "Nhà cung cấp đã tồn tại (do trùng tên, email hoặc số điện thoại)";
		} else if(!nhaCungCapDao.add(nhaCungCap)) { 		// thêm mới vào CSDL
			error = "Xảy ra lỗi trong quá trình thêm mới";
		}
		return error;
	}

	public NhaCungCapDTO findByIdNCC(int idNCC) {
		return nhaCungCapDao.findByIdNCC(idNCC);
	}

	public String update(NhaCungCapDTO nhaCungCap) {
		// kiểm tra tên nhà cung cấp hoặc email hoặc số điện thoại đã tồn tại chưa
		String error = "";
		if(nhaCungCapDao.isExist(nhaCungCap)) {
			error = "Nhà cung cấp đã tồn tại (do trùng tên, email hoặc số điện thoại)";
		} else if(!nhaCungCapDao.update(nhaCungCap)) { 		// thêm mới vào CSDL
			error = "Xảy ra lỗi trong quá trình cập nhật";
		}
		return error;
	}

	public boolean delete(int idNCC) {
		boolean success = false;
		// kiểm tra nếu tài khoản của nhà cung cấp đã tồn tại
		// trong phiếu nhập bất kì (có khóa ngoại)
		// -> ko xóa được
		if(!nhaCungCapDao.isSupplierInInvoice(idNCC)) {
			if(nhaCungCapDao.delete(idNCC)) {
				success = true;
			}
		}
		return success;
	}

	public List<NhaCungCapDTO> search(String keyWord){
		return nhaCungCapDao.search(keyWord);
	}
}
