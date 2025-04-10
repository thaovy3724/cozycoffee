package BUS;

import DAO.CTHoaDonDAO;
import DAO.HoaDonDAO;
import DTO.CTHoaDonDTO;
import DTO.HoaDonDTO;
import java.sql.Date;
import java.util.*;

public class HoaDonBUS {
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private final CTHoaDonDAO ctHoaDonDAO = new CTHoaDonDAO();

    // Lấy tất cả hóa đơn
    public List<HoaDonDTO> getAllHoaDon() {
        return hoaDonDAO.getAll();
    }

    public List<CTHoaDonDTO> getAllCTHoaDonByID(int idHD) {
        return ctHoaDonDAO.getAllByID("idHD", idHD);
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
    public boolean isExist(int idHD) {
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
    public List<HoaDonDTO> searchHoaDonByTongTien(int giaBD, int giaKT) {
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

    //Tìm kiếm nâng cao
    public List<HoaDonDTO> searchHoaDon(Date startDate, Date endDate, int minTongTien, int maxTongTien, int idNV) {
        List<HoaDonDTO> allHoaDon = getAllHoaDon();
        // Tạm thời chưa khởi tạo joinLists
        List<List<HoaDonDTO>> joinLists = null;

        if (startDate != null || endDate != null || minTongTien > 0 || maxTongTien > 0 || idNV > 0) {
            joinLists = new ArrayList<>();
            //nếu người dùng có nhập tiêu chí tìm kiếm, khởi tạo joinLists
        }

        if (startDate != null || endDate != null) {
            joinLists.add(searchHoaDonByDate(startDate, endDate));
        }
        if (minTongTien > 0 || maxTongTien > 0) {
            joinLists.add(searchHoaDonByTongTien(minTongTien, maxTongTien));
        }
        if (idNV > 0) {
            joinLists.add(searchHoaDonByIdNV(idNV));
        }

        return joinHoaDonLists(allHoaDon, joinLists);
    }

    // Hàm tiện ích: join các arrayList
    // Ý tưởng chính:
    //	+ mainList sẽ là danh sách chính để thực hiện join
    //	+ Xử lý các trường hợp đặc biệt trước:
    //		- Nếu danh sách các list rỗng, trả về một list rỗng
    //		- Nếu chỉ có list được truyền vào, trả về chính nó
    //	+ Chuyển list về thành set, sau đó dùng phương thức contains để lấy các phần tử chung
    List<HoaDonDTO> joinHoaDonLists(List<HoaDonDTO> mainList, List<List<HoaDonDTO>> joinLists) {
        // Trường hợp đặc biệt: mainList null hoặc rỗng => trả về danh sách rỗng
        if (mainList == null || mainList.isEmpty()) {
            return new ArrayList<>();
        }

        // Trường hợp joinLists null => joinLists không tồn tại => trả về mainList
        if (joinLists == null) {
            return new ArrayList<>(mainList);
        }

        // Chuyển mainList thành Set làm mốc
        //Ở đây dùng LinkedHashSet chứ không dùng HashSet là vì
        //HashSet không đảm bảo thứ tự của List sau khi được gộp trở về List
        //Tuy nhiên, LinkedHashSet tốn bộ nhớ hơn
        Set<HoaDonDTO> resultSet = new LinkedHashSet<>(mainList);

        // Duyệt qua từng danh sách trong joinLists
        for (List<HoaDonDTO> list : joinLists) {
            // Nếu danh sách con là null => bỏ qua, tránh gây lỗi nullPointException
            if (list == null) {
                continue;
            }

            // Nếu danh sách con rỗng => trả về danh sách rỗng ngay lập tức bởi sẽ không có phần tử chung
            if (list.isEmpty()) {
                return new ArrayList<>();
            }

            Set<HoaDonDTO> currentSet = new LinkedHashSet<>(list);
            //Hàm retainAll chỉ có thể hoạt động khi class của đối tượng được dùng có hàm equals
            // => nhớ khai báo hàm equals() khi dùng hàm này nha
            resultSet.retainAll(currentSet); // Giữ lại phần tử chung
            // Nếu resultSet rỗng sau retainAll, thoát ra khỏi vòng lặp luôn
            if (resultSet.isEmpty()) {
                break;
            }
        }

        return new ArrayList<>(resultSet);
    }
}