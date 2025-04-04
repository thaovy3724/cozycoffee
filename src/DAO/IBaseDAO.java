package DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * T: Kiểu của DTO
 * K: kiểu của khóa chính
 */

//Interface sẽ định nghĩa các phương thức mà các DAO cần phải implement
public interface IBaseDAO<T> {
    List<T> getAll();           // Lấy tất cả bản ghi
    Optional<T> findById(String column, int id);           // Tìm theo ID
    boolean isExist(String column, int id);      // Kiểm tra tồn tại
    boolean add(List<Object> tuple); //Thêm mới
    void closeConnection();     // Đóng kết nối
}
