package BUS;

import DTO.NguyenLieuDTO;
import DAO.NguyenLieuDAO;

import java.util.List;

public class NguyenLieuBUS {
    private final NguyenLieuDAO nguyenLieuDao = new NguyenLieuDAO();

    public List<NguyenLieuDTO> getAll(){
        return nguyenLieuDao.getAll();
    }

    public String add(NguyenLieuDTO nl) {
        // kiểm tra tên danh mục đã tồn tại chưa
        // nếu có trả về thông báo lỗi
        String error = "";
        if(nguyenLieuDao.isExist(nl))
            error = "Tên nguyên liệu đã tồn tại";
        else if(!nguyenLieuDao.add(nl)) {       // thêm mới vào CSDL
            error = "Xảy ra lỗi trong quá trình thêm mới";
        }
        return error;
    }
    public List<NguyenLieuDTO> getAllActive(){
        return nguyenLieuDao.getAllActive();
    }

    public NguyenLieuDTO findByIdNL(int idNL) {
        return nguyenLieuDao.findByIdNL(idNL);
    }

    public String update(NguyenLieuDTO nl) {
        // kiểm tra tên danh mục đã tồn tại chưa
        // nếu có trả về thông báo lỗi
        String error = "";
        if(nguyenLieuDao.isExist(nl))
            error = "Nguyên liệu đã tồn tại";
        else if(!nguyenLieuDao.update(nl))
            error = "Xảy ra lỗi trong quá trình cập nhật";

        return error;
    }

    public boolean delete(int idNL) {
        boolean success = false;

        if(!nguyenLieuDao.isInRecipe(idNL) && !nguyenLieuDao.isInInvoice(idNL)) {
            if(nguyenLieuDao.delete(idNL))
                success = true;
        }
        return success;
    }

    public List<NguyenLieuDTO> search(String keyWord){
        return nguyenLieuDao.search(keyWord);
    }
}