package BUS;
import DTO.DanhMucDTO;
import DAO.DanhMucDAO;
import java.util.List;

public class DanhMucBUS {
	private final DanhMucDAO danhMucDao = new DanhMucDAO();
	
	public List<DanhMucDTO> getAll(){
		return danhMucDao.getAll();
	}
	
	public List<DanhMucDTO> getAllActiveF0Except(int idDMCha){
		return danhMucDao.getAllActiveF0Except(idDMCha);
	}

	public List<DanhMucDTO> getAllActiveF0(){
		// dùng để hiển thị cbo DMCha trong khi thêm danh mục
		return danhMucDao.getAllActiveF0();
	}

	public List<DanhMucDTO> getAllActiveF1(){
		// dùng để hiển thị cbo DM trong BanHangPanel, trong cbo DM của SanPhamDialog
		return danhMucDao.getAllActiveF1();
	}

	public List<DanhMucDTO> getAllActiveF1Except(int idDM){
		return danhMucDao.getAllActiveF1Except(idDM);
	}

	public String add(DanhMucDTO danhMuc) {
		// kiểm tra tên danh mục đã tồn tại chưa
		// nếu có trả về thông báo lỗi
		String error = "";
		if(danhMucDao.isExist(danhMuc)) 
			error = "Tên danh mục đã tồn tại";
		else if(!danhMucDao.add(danhMuc)) { 		// thêm mới vào CSDL
			error = "Xảy ra lỗi trong quá trình thêm mới";
		}
		return error;
	}
	
	public DanhMucDTO findByIdDM(int idDM) {
		return danhMucDao.findByIdDM(idDM);
	}
	
	public String update(DanhMucDTO danhMuc) {
		// kiểm tra tên danh mục đã tồn tại chưa
		// nếu có trả về thông báo lỗi
		String error = "";
		if(danhMucDao.isExist(danhMuc))
			error = "Tên danh mục đã tồn tại";
		else if(!danhMucDao.update(danhMuc)) 
			error = "Xảy ra lỗi trong quá trình cập nhật";
		
		return error;
	}

	public boolean delete(int idDM) {
		boolean success = false;
		// kiểm tra nếu danh mục là danh mục cha của danh mục khác 
		// hoặc đã có sản phẩm thuộc danh mục này
		if(!danhMucDao.isParentCategory(idDM) && !danhMucDao.hasProductInCategory(idDM)) {
			if(danhMucDao.delete(idDM)) 
				success = true;
		}
		return success;
	}
	
	public List<DanhMucDTO> search(String keyWord){
		return danhMucDao.search(keyWord);
	}
}
