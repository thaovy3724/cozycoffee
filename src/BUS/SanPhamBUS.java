package BUS;

import DTO.SanPhamDTO;
import DAO.SanPhamDAO;
import java.util.List;

public class SanPhamBUS {
	private final SanPhamDAO sanPhamDao = new SanPhamDAO();
	
	public List<SanPhamDTO> getAll(){
		return sanPhamDao.getAll();
	}
	
	public String add(SanPhamDTO sanPham) {
		// kiểm tra tên nhà cung cấp hoặc email hoặc số điện thoại đã tồn tại chưa
		String error = "";
		if(sanPhamDao.isExist(sanPham)) 
			error = "Sản phẩm đã tồn tại";
		else if(!sanPhamDao.add(sanPham)) { 		// thêm mới vào CSDL
			error = "Xảy ra lỗi trong quá trình thêm mới";
		}
		return error;
	}
	
	public SanPhamDTO findByIdSP(int idSP) {
		return sanPhamDao.findByIdSP(idSP);
	}
	
	public String update(SanPhamDTO sanPham) {
		// kiểm tra tên nhà cung cấp hoặc email hoặc số điện thoại đã tồn tại chưa
		String error = "";
		if(sanPhamDao.isExist(sanPham)) 
			error = "Nhà cung cấp đã tồn tại (do trùng tên, email hoặc số điện thoại)";
		else if(!sanPhamDao.add(sanPham)) { 		// thêm mới vào CSDL
			error = "Xảy ra lỗi trong quá trình cập nhật";
		}
		return error;
	}

	public boolean delete(int idSP) {
		boolean success = false;
		// kiểm tra nếu tài khoản của nhà cung cấp đã tồn tại 
		// trong phiếu nhập bất kì (có khóa ngoại)
		// -> ko xóa được
		if(!sanPhamDao.isProductInRecipe(idSP) && !sanPhamDao.isProductInReceipt(idSP)) {
			if(sanPhamDao.delete(idSP)) 
				success = true;
		}
		return success;
	}
	
	public List<SanPhamDTO> search(String keyWord){
		return sanPhamDao.search(keyWord);
	}
}
