package BUS;

import DTO.DanhMucDTO;
import DTO.SanPhamDTO;
import DAO.SanPhamDAO;

import java.util.ArrayList;
import java.util.List;

public class SanPhamBUS {
	private final SanPhamDAO sanPhamDao = new SanPhamDAO();
	
	public List<SanPhamDTO> getAll(){
		return sanPhamDao.getAll();
	}

	public List<SanPhamDTO> getAllActive() {
        return sanPhamDao.getAllActive();
    }
	
	public String add(SanPhamDTO sanPham) {
		// kiểm tra tên sản phẩm đã tồn tại chưa
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
	
	public String update(SanPhamDTO sp) {
		// kiểm tra tên sản phẩm đã tồn tại chưa
		String error = "";
		if(sanPhamDao.isExist(sp)){
			error = "Sản phẩm đã tồn tại";
		}else if(!sanPhamDao.isProductInRecipe(sp.getIdSP())){
			// nếu sản phẩm chưa có công thức, set trạng thái sản phẩm mặc định là 0 ("không hoạt động")
			sp.setTrangthai(0); // 
		}
		
		if(error.isEmpty()){
			// tiến hành cập nhậtnhật
			if(sp.getHinhanh() == null) { 	
				if(!sanPhamDao.updateNotImage(sp))
					error = "Xảy ra lỗi trong quá trình cập nhật 1";
			}else{
				if(!sanPhamDao.update(sp)) {
					error = "Xảy ra lỗi trong quá trình cập nhật";
				}
			}
		}
		return error;
	}

	public boolean delete(int idSP) {
		boolean success = false;
		// kiểm tra nếu sản phẩm đã tồn tại 
		// trong hóa đơn hoặc trong công thức nào đó
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

	public List<SanPhamDTO> search(String keyWord, DanhMucDTO danhMuc){
		List<SanPhamDTO> result = new ArrayList<>();
		if(keyWord.isEmpty()){
			// kiếm theo danh mục
			if(danhMuc.getIdDM() == 0) result = sanPhamDao.getAllActive();
			else result = sanPhamDao.searchByCategory(danhMuc.getIdDM());
		} else{
			// kiếm theo keyword và danh mục
			if(danhMuc.getIdDM() == 0) result = sanPhamDao.searchByKeyWord(keyWord);
			else result = sanPhamDao.search(keyWord, danhMuc.getIdDM());
		}
		return result;
	}

}