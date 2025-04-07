package DAO;

import java.util.List;
import java.util.Map;

/**
 * T: Kiểu của DTO
 * K: kiểu của khóa chính
 */

//Interface sẽ định nghĩa các phương thức mà các DAO cần phải implement
public interface IBaseDAO<T> {
    List<T> getAll();           // Lấy tất cả bản ghi
    T findById(String column, int id);           // Tìm theo ID
    boolean isExist(String column, Object value);
    boolean isExist(String column, Object value, String excludedColumn, Object excludedValue);
//    boolean isExist(Map<String, Object> equals, Map<String, Object> notEquals);// Kiểm tra tồn tại
    boolean add(T entity); //Thêm mới
}
