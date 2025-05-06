package BUS;

import java.awt.Desktop;
import java.io.File;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import DAO.CT_HoaDonDAO;
import DAO.HoaDonDAO;
import DAO.SanPhamDAO;
import DAO.TaiKhoanDAO;
import DTO.CT_HoaDonDTO;
import DTO.HoaDonDTO;
import DTO.SanPhamDTO;
import DTO.TaiKhoanDTO;

public class HoaDonBUS {
    private final HoaDonDAO hoaDonDao = new HoaDonDAO();
    private final TaiKhoanDAO taiKhoanDao = new TaiKhoanDAO();
    private final CT_HoaDonDAO ct_HoaDonDao = new CT_HoaDonDAO();
    private final SanPhamDAO sanPhamDao = new SanPhamDAO();
    
    public List<HoaDonDTO> getAll(){
	return hoaDonDao.getAll();
}

    public String add(HoaDonDTO hoaDon, List<CT_HoaDonDTO> dsChiTiet){
        // kiểm tra từng sản phẩm có available ko (đủ nguyên liệu để chế biến theo số lượng đặt không)
        String error = "";
        int quantityAvailable = 0;
        for(CT_HoaDonDTO ct : dsChiTiet){
            quantityAvailable = ct_HoaDonDao.quantityAvailable(ct);
            if(quantityAvailable < ct.getSoluong()){
                error += "Mã sản phẩm " + ct.getIdSP() + " chỉ còn đủ " + quantityAvailable + "sản phẩm" + "\n";
            }
        }

        // nếu tất cả sản phẩm đều available thì tiến hành tạo hóa đơn
        if(error.isEmpty()){
            int newIdHD = hoaDonDao.add(hoaDon, dsChiTiet);
            if(newIdHD == 0)
                error = "Đã xảy ra lỗi trong quá trình thêm hóa đơn";
            else{
                // cập nhật tồn kho
                hoaDonDao.updateInventory(newIdHD);
                error = String.valueOf(newIdHD);
            }
        }
        return error;
    }

    public int getTotalAmount(int idHD){
        return hoaDonDao.getTotalAmount(idHD);
    }

    public List<HoaDonDTO> search(Date dateStart, Date dateEnd, Integer minPrice, Integer maxPrice){
        return hoaDonDao.search(dateStart, dateEnd, minPrice, maxPrice);
    }

    private HoaDonDTO findByIdHD(int idHD){
        return hoaDonDao.findByIdHD(idHD);
    }

     public void printHoaDon(int idHD) throws Exception {
        // Khởi tạo output path
        String relativeFolderPath = "receipt-pdf";
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
        String outputPath = Paths.get(abouslutePath, "hoadon-" + idHD + ".pdf").toString();

        // Khởi tạo PDF
        PdfWriter writer = new PdfWriter(outputPath); //FileNotFoundException
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Load font
        String fontPath = Paths.get(System.getProperty("user.dir"), "src", "ASSET", "Fonts", "Roboto-Regular.ttf")
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
        HoaDonDTO hoaDonInfo = findByIdHD(idHD);
        TaiKhoanDTO taiKhoan = taiKhoanDao.findByIdTK(hoaDonInfo.getIdTK());

        document.add(new Paragraph("Hóa đơn bán hàng #" + idHD)
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
        List<CT_HoaDonDTO> ctHoaDonArr = ct_HoaDonDao.getAllByIdHD(idHD);
        for (CT_HoaDonDTO ct : ctHoaDonArr) {
            SanPhamDTO sp = sanPhamDao.findByIdSP(ct.getIdSP());
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

        // Hiển thị file PDF
        File pdfFile = new File(outputPath);
        if (Desktop.isDesktopSupported() && pdfFile.exists()) {
            Desktop.getDesktop().open(pdfFile); //IOException
        } else {
            throw new Exception("Không thể mở file PDF. Vui lòng kiểm tra file hoặc hỗ trợ Desktop.");
        }
    }

     // Tìm kiếm hóa đơn theo khoảng ngày tạo
     public List<HoaDonDTO> searchByDate(Date start, Date end) {
        return hoaDonDao.searchByDate(start, end);
    }

    public long getAllTongTien(List<HoaDonDTO> hoaDonDTOList) {
        long tongtien = 0;
        for(HoaDonDTO hd: hoaDonDTOList) {
            tongtien += hoaDonDao.getTotalAmount(hd.getIdHD());
        }

        return tongtien;
    }
}
