package BUS;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;
import java.sql.Date;
import java.util.List;

public class HoaDonBUS {
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();

    // Lấy tất cả hóa đơn
    public List<HoaDonDTO> getAllHoaDon() {
        return hoaDonDAO.getAll();
    }

    // Tìm hóa đơn theo ID
    public HoaDonDTO findHoaDonById(int idHD) {
        HoaDonDTO hoaDon = hoaDonDAO.findById("idHD", idHD);
        if (hoaDon == null) {
            throw new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + idHD);
        }
        return hoaDon;
    }

    // Kiểm tra hóa đơn có tồn tại không
    public boolean isHoaDonExist(int idHD) {
        return hoaDonDAO.isExist("idHD", idHD);
    }

    // Thêm hóa đơn mới
    public void addHoaDon(HoaDonDTO hoaDon) throws IllegalArgumentException {
        // Kiểm tra xem hóa đơn đã tồn tại chưa
        if (hoaDonDAO.isExist("idHD", hoaDon.getIdHD())) {
            throw new IllegalArgumentException("Hóa đơn với ID " + hoaDon.getIdHD() + " đã tồn tại");
        }

        // Thêm hóa đơn
        boolean success = hoaDonDAO.add(hoaDon);
        if (!success) {
            throw new IllegalStateException("Không thể thêm hóa đơn");
        }
    }

    // Tìm kiếm hóa đơn theo khoảng tổng tiền
    public List<HoaDonDTO> searchHoaDonByTongtien(int giaBD, int giaKT) {
        return hoaDonDAO.searchByTongTien(giaBD, giaKT);
    }

    // Tìm kiếm hóa đơn theo khoảng ngày tạo
    public List<HoaDonDTO> searchHoaDonByDate(Date start, Date end) {
        return hoaDonDAO.searchByDate(start, end);
    }

    // Tìm kiếm hóa đơn theo ID nhân viên
    public List<HoaDonDTO> searchHoaDonByIdNV(int idTK) {
        return hoaDonDAO.searchByIdNV(idTK);
    }

}