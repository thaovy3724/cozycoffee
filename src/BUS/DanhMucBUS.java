package BUS;
import DTO.DanhMucDTO;
import DAO.DanhMucDAO;
import java.util.List;

public class DanhMucBUS {
	private final DanhMucDAO danhMucDao = new DanhMucDAO();
	
	public List<DanhMucDTO> getAll(){
		return danhMucDao.getAll();
	}
	
	public String add(DanhMucDTO danhMuc) {
		// kiểm tra tên danh mục đã tồn tại chưa
		// nếu có trả về thông báo lỗi
		String error = "";
		if(danhMucDao.isExist(danhMuc)) 
			error = "Tên danh mục đã tồn tại";
		
		// kiem tra trang thai cua danh muc cha, neu danh muc cha bi khoa thi thong bao loi
		if(danhMuc.getIdDMCha() != -1) {
			DanhMucDTO dmucCha = findByIdDM(danhMuc.getIdDMCha());
			if(dmucCha == null)
				error = "Xảy ra lỗi trong quá trình truy xuất dữ liệu";
			else if(dmucCha.getTrangthai() == 0) 
				error = "Danh mục cha đã bị khóa, vui lòng chọn danh mục khác";
		}
		
		// thêm mới vào CSDL
		if(error == "")
			if(!danhMucDao.add(danhMuc)) 
				error = "Xảy ra lỗi trong quá trình thêm mới";
		
		return error;
	}
	
	public DanhMucDTO findByIdDM(int idDM) {
		return danhMucDao.findByIdDM(idDM);
	}
	
	public String update(DanhMucDTO danhMucClone) {
		// kiểm tra tên danh mục đã tồn tại chưa
		// nếu có trả về thông báo lỗi
		String error = "";
		if(danhMucDao.isExist(danhMucClone)) error = "Tên danh mục đã tồn tại";
		
		int idDMCha = danhMucClone.getIdDMCha();
		if(idDMCha != -1) {
			// kiem tra neu idDM trung voi idDMCha
			if(idDMCha == danhMucClone.getIdDM())
				error = "Danh mục cha không hợp lệ";
			else {
				// kiem tra trang thai cua danh muc cha, neu danh muc cha bi khoa thi thong bao loi
				DanhMucDTO danhMuc = findByIdDM(danhMucClone.getIdDM());
				DanhMucDTO dmucCha = findByIdDM(idDMCha);
				if(dmucCha == null)
					error = "Xảy ra lỗi trong quá trình truy xuất dữ liệu";
				else if(dmucCha.getTrangthai() == 0 && dmucCha.getIdDM()!=danhMuc.getIdDMCha()) 
					error = "Danh mục cha đã bị khóa, vui lòng chọn danh mục khác";
				}
		}
		
		// cập nhật  vào CSDL
		if(error == "")
			if(!danhMucDao.update(danhMucClone)) 
				error = "Xảy ra lỗi trong quá trình cập nhật";
		
		return error;
	}

	public String delete(int idDM) {
		String success = "";
		// kiểm tra nếu danh mục là danh mục cha của danh mục khác 
		// hoặc đã có sản phẩm thuộc danh mục này
		if(!danhMucDao.isParentCategory(idDM) && !danhMucDao.hasProductInCategory(idDM)) {
			if(danhMucDao.delete(idDM)) 
				success = "Xóa danh mục thành công";
		} else if(danhMucDao.toggleLock(true, idDM))
			success = "Khóa danh mục thành công";
		return success;
	}
	
	public boolean unlock(int idDM) {
		return danhMucDao.toggleLock(false, idDM);
	}
	
	public List<DanhMucDTO> search(String keyWord){
		return danhMucDao.search(keyWord);
	}
}
