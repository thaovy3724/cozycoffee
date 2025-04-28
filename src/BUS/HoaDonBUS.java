package BUS;

import DAO.CTHoaDonDAO;
import DAO.HoaDonDAO;
import DAO.SanPhamDAO;
import DAO.TaiKhoanDAO;
import DTO.CTHoaDonDTO;
import DTO.HoaDonDTO;
import DTO.SanPhamDTO;
import DTO.TaiKhoanDTO;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.awt.Desktop;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class HoaDonBUS {
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private final CTHoaDonDAO ctHoaDonDAO = new CTHoaDonDAO();
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private final SanPhamDAO sanPhamDAO = new SanPhamDAO();

    // Lấy tất cả hóa đơn
    public List<HoaDonDTO> getAllHoaDon() {
        return hoaDonDAO.getAll();
    }

    public List<CTHoaDonDTO> getAllCTHoaDonByID(int idHD) {
        return ctHoaDonDAO.getAllByIDHD(idHD);
    }

    // Tìm hóa đơn theo ID
    public HoaDonDTO findHoaDonById(int idHD) {
        HoaDonDTO hoaDon = hoaDonDAO.findByID(idHD);
        if (hoaDon == null) {
            throw new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + idHD);
        }
        return hoaDon;
    }

    // Kiểm tra hóa đơn có tồn tại không
    public boolean isExist(int idHD) {
        HoaDonDTO hoadon = hoaDonDAO.findByID(idHD);

        return hoadon == null ? true : false;
    }

    // Thêm hóa đơn mới
    public void addHoaDon(HoaDonDTO hoaDon) throws IllegalArgumentException {
        // Kiểm tra xem hóa đơn đã tồn tại chưa
        if (isExist(hoaDon.getIdHD())) {
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

    public void printHoaDon(List<CTHoaDonDTO> ctHoaDons) throws Exception {
        // Khởi tạo output path
        String relativeFolderPath = "invoice-pdf";
        String abouslutePath = Paths.get(System.getProperty("user.dir"), "src", relativeFolderPath)
                // System.getProperty("user.dir"): Lấy thư mục làm việc hiện tại
                // Paths.get() xây dựng đối tượng kiểu đường dẫn, dùng để nối các thành phần đường dẫn lại thành một Path hoàn chỉnh
                // => Đường dẫn sau 2 method này sẽ có dạng: C:/...something.../src/invoice-pdf
                .normalize() // Xử lý các phần tử như ., .., /
                .toAbsolutePath() // Đảm bảo là đường dẫn tuyệt đối (thêm cho chắc ăn thui)
                .toString();

        // Đảm bảo thư mục tồn tại
        File dir = new File(abouslutePath);
        if (!dir.exists()) { //Nếu folder chưa tồn taị, tạo folder tương ứng
            dir.mkdirs();
        }

        // File.separator là ký tự \ (window) hoặc / (linux) tùy vào hệ điều hành hiện tại
        // => không dẫn đến lỗi dùng sai dấu slash (bởi linux nhận diện \ là escape character)
        // Nói chung dùng này cho an toàn cho tất cả trường hợp có thể xảy ra
        String outputPath = Paths.get(abouslutePath, "hoadon-" + ctHoaDons.get(0).getIdHD() + ".pdf").toString();

        // Khởi tạo PDF
        PdfWriter writer = new PdfWriter(outputPath); //FileNotFoundException
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Load font
        String fontPath = Paths.get(System.getProperty("user.dir"), "src", "ASSET", "fonts", "Roboto-Regular.ttf")
                .normalize()
                .toAbsolutePath()
                .toString();
        PdfFont robotoFont = PdfFontFactory.createFont(fontPath, "Identity-H", true);

        // Định dạng tiền tệ và ngày
        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Tiêu đề quán cà phê
        document.add(new Paragraph("Quán Cà Phê Cozy Coffee")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(robotoFont)
                .setFontSize(20)
                .setBold());
        document.add(new Paragraph("Địa chỉ: 273 An Dương Vương, Phường 3, Quận 5, TP.HCM")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(robotoFont)
        );
        document.add(new Paragraph("Hotline: 0123 456 789")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(robotoFont)
        );
        document.add(new Paragraph(" "));

        // Thông tin hóa đơn
        HoaDonDTO hoaDonInfo = findHoaDonById(ctHoaDons.get(0).getIdHD());
        TaiKhoanDTO taiKhoan = taiKhoanDAO.findByIdTK(hoaDonInfo.getIdTK());

        document.add(new Paragraph("Hóa đơn bán hàng #" + ctHoaDons.get(0).getIdHD())
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(robotoFont)
                .setFontSize(16)
                .setBold());
        document.add(new Paragraph("Nhân viên: " + taiKhoan.getTenTK() + " (ID: " + hoaDonInfo.getIdTK() + ")"))
                .setFont(robotoFont);
        document.add(new Paragraph("Ngày tạo: " + dateFormat.format(Date.valueOf(hoaDonInfo.getNgaytao()))))
                .setFont(robotoFont);

        // Tạo bảng danh sách sản phẩm
        float[] columnWidths = {50, 200, 80, 100, 100};
        Table table = new Table(UnitValue.createPointArray(columnWidths));
        table.addHeaderCell("Mã SP")
                .setBold()
                .setFont(robotoFont);
        table.addHeaderCell("Tên sản phẩm")
                .setBold()
                .setFont(robotoFont);
        table.addHeaderCell("Số lượng")
                .setBold()
                .setFont(robotoFont);
        table.addHeaderCell("Giá lúc đặt")
                .setBold()
                .setFont(robotoFont);
        table.addHeaderCell("Thành tiền")
                .setBold()
                .setFont(robotoFont);

        long total = 0;
        for (CTHoaDonDTO ct : ctHoaDons) {
            SanPhamDTO sp = sanPhamDAO.findByIdSP(ct.getIdSP());
            if (sp == null) {
                continue;
            }
            long thanhTien = (long) ct.getSoluong() * ct.getGialucdat();
            total += thanhTien;

            table.addCell(String.valueOf(ct.getIdSP())).setFont(robotoFont);
            table.addCell(sp.getTenSP()).setFont(robotoFont);
            table.addCell(String.valueOf(ct.getSoluong())).setFont(robotoFont);
            table.addCell(df.format(ct.getGialucdat())).setFont(robotoFont);
            table.addCell(df.format(thanhTien)).setFont(robotoFont);
        }

        // Dòng tổng tiền
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("Tổng cộng:")
                .setBold()
                .setFont(robotoFont);
        table.addCell(df.format(total))
                .setBold()
                .setFont(robotoFont);

        document.add(table);

        // Lời cảm ơn
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Wifi: Cozy Coffee")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(robotoFont)
        );
        document.add(new Paragraph("Mật khẩu: cozyxincamon")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(robotoFont)
        );
        document.add(new Paragraph("Cảm ơn quý khách và hẹn gặp lại!")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(robotoFont)
                .setBold()
        );

        // Đóng tài liệu
        document.close();
        System.out.println("Hóa đơn đã được tạo: " + outputPath);

        // Hiển thị file PDF
        File pdfFile = new File(outputPath);
        if (Desktop.isDesktopSupported() && pdfFile.exists()) {
            Desktop.getDesktop().open(pdfFile); //IOException
        } else {
            throw new Exception("Không thể mở file PDF. Vui lòng kiểm tra file hoặc hỗ trợ Desktop.");
        }
    }
}