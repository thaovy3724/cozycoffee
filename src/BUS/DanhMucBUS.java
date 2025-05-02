package BUS;
import DTO.DanhMucDTO;
import DAO.DanhMucDAO;
import java.util.List;

public class DanhMucBUS {
	private final DanhMucDAO danhMucDao = new DanhMucDAO();
	
	public List<DanhMucDTO> getAll(){
		return danhMucDao.getAll();
	}
	
	public List<DanhMucDTO> getAllActiveEdit(int idDMCon, int idDMCha){
		return danhMucDao.getAllActiveEdit(idDMCon, idDMCha);
	}

	public List<DanhMucDTO> getAllActiveExcept(int idDM){
		return danhMucDao.getAllActiveExcept(idDM);
	}
	
	public List<DanhMucDTO> getAllActive(){
		return danhMucDao.getAllActive();
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