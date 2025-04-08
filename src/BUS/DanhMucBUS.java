package BUS;
import DTO.DanhMucDTO;
import DAO.DanhMucDAO;
import java.util.List;
import java.util.ArrayList;

public class DanhMucBUS {
	private DanhMucDAO danhMucDao;
	
	public DanhMucBUS() {
		danhMucDao = new DanhMucDAO();
	}
	
	public List<DanhMucDTO> getAll(){
		return danhMucDao.getAll();
	}
	
	public String add(DanhMucDTO danhMuc) {
		// kiểm tra tên danh mục đã tồn tại chưa
		// nếu có trả về thông báo lỗi
		String error = "";
		if(isExist(danhMuc)) 
			error = "Tên danh mục đã tồn tại";
		
		// kiem tra trang thai cua danh muc cha, neu danh muc cha bi khoa thi thong bao loi
		if(danhMuc.getIdDMCha() != -1) {
			DanhMucDTO dmucCha = findByID(danhMuc.getIdDMCha());
			if(dmucCha == null)
				error = "Xảy ra lỗi trong quá trình truy xuất dữ liệu";
			else if(dmucCha.getTrangthai() == 0) 
				error = "Danh mục cha đã bị khóa, vui lòng chọn danh mục khác";
		}
		
		// thêm mới vào CSDL
		if(error == "" && danhMucDao.add(danhMuc) == -1) 
			error = "Xảy ra lỗi trong quá trình thêm mới";
		
		return error;
	}
	
	public DanhMucDTO findByID(int id) {
		List<String> conditions = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		
		conditions.add("idDM");
		params.add(id);
		return danhMucDao.findOne(conditions, params);
	}
	
	public String update(int index, DanhMucDTO danhMuc) {
		// kiểm tra tên danh mục đã tồn tại chưa
		// nếu có trả về thông báo lỗi
		String error = "";
		if(isExist(danhMuc)) error = "Tên danh mục đã tồn tại";
		
		// kiem tra trang thai cua danh muc cha, neu danh muc cha bi khoa thi thong bao loi
		int idDMCha = danhMuc.getIdDMCha();
		if(idDMCha != -1) {
			if(idDMCha == danhMuc.getIdDM())
				error = "Danh mục cha không hợp lệ";
			else {
				DanhMucDTO dmucCha = findByID(idDMCha);
				if(dmucCha == null)
					error = "Xảy ra lỗi trong quá trình truy xuất dữ liệu";
				else if(dmucCha.getTrangthai() == 0 && dmucCha.getIdDM()!=danhMuc.getTrangthai()) 
					error = "Danh mục cha đã bị khóa, vui lòng chọn danh mục khác";
				}
		}
		
		// cập nhật  vào CSDL
		if(error == "" && !danhMucDao.update(danhMuc)) 
			error = "Xảy ra lỗi trong quá trình cập nhật";
		
		return error;
	}
	
	public boolean isExist(DanhMucDTO danhMuc) {
		// conditions: tên cột thể hiện tồn tại
		List<String> conditions = new ArrayList<>();
		// paramsCond: giá trị thể hiện tồn tại
		List<Object> paramsCond = new ArrayList<>();
		// refs: tên cột tham chiếu
		String refs = "";
		// paramRefs: giá trị tham chiếu
		int paramRefs = 0;

		conditions.add("tenDM");
		paramsCond.add(danhMuc.getTenDM());
		
		// neu idDM = 0: action = add, nguoc lai action = sua
		if(danhMuc.getIdDM() != 0) {
			refs = "idDM";
			paramRefs = danhMuc.getIdDM();
		}
		
		return danhMucDao.isExist(conditions, paramsCond, refs, paramRefs);
	}
	
	public String delete(int idDM) {
		String success = "";
		// kiểm tra nếu danh mục là danh mục cha của danh mục khác 
		// hoặc đã có sản phẩm thuộc danh mục này
		if(!danhMucDao.isParentCategory(idDM) && !danhMucDao.hasProductInCategory(idDM)) {
			if(danhMucDao.delete(idDM)) 
				success = "Xóa danh mục thành công";
		} else if(danhMucDao.lock(idDM))
			success = "Khóa danh mục thành công";
		return success;
	}
	
	public boolean unlock(int idDM) {
		return danhMucDao.unlock(idDM);
	}
	
	public List<DanhMucDTO> search(String keyWord){
		return danhMucDao.search(keyWord);
	}
}
