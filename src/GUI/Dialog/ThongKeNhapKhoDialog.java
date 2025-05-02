package GUI.Dialog;

import BUS.NguyenLieuBUS;
import BUS.PhieuNhapBUS;
import DTO.Lo_NguyenLieuDTO;
import DTO.NguyenLieuDTO;

import java.awt.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ThongKeNhapKhoDialog extends JDialog {
	private PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
	private NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */

	public ThongKeNhapKhoDialog(Date start, Date end, int idNL, List<Lo_NguyenLieuDTO> lo_NLList) {
		getContentPane().setBackground(new Color(255, 255, 255));
		DecimalFormat df = new DecimalFormat("#,### VNĐ");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		NguyenLieuDTO nl = nguyenLieuBUS.findByIdNL(idNL);
		String tenNL = nl.getTenNL();
		String donvi = nl.getDonvi();

		String formattedStartDate = start.toLocalDate().format(dateFormatter);
		String formattedEndDate = end.toLocalDate().format(dateFormatter);

		setTitle("Thống kê nhập kho " + tenNL.toLowerCase() + " từ " + formattedStartDate + " đến " + formattedEndDate);
		setSize(700, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);

		// Tạo bảng chi tiết
		String[] columns = {"", "Số lượng nhập ("+donvi+")", "Đơn giá /"+donvi, "Tổng tiền", "Ngày nhập"};
		DefaultTableModel detailModel = new DefaultTableModel(columns, 0);
		JTable detailTable = new JTable(detailModel);
		detailTable.setDefaultEditor(Object.class, null);
		//detailTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Tăng chiều rộng cột Ngày nhập
		JScrollPane scrollPane = new JScrollPane(detailTable);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		// Thêm nút Đóng
		JButton closeButton = new JButton("Đóng");
		closeButton.setBackground(new Color(30, 144, 255));
		closeButton.addActionListener(e -> dispose());
		getContentPane().add(closeButton, BorderLayout.SOUTH);

		// Thêm dữ liệu vào bảng
		float tongSoLuong = 0;
		long tongTien = 0;

		for (Lo_NguyenLieuDTO lo : lo_NLList) {
			int idLo = lo.getIdPN();
			float soLuong = lo.getSoluongnhap();
			int donGia = lo.getDongia();
			long tongtien = (long) (soLuong * donGia);
			
			String ngayNhap = phieuNhapBUS.findByIdPN(lo.getIdPN()).getNgaycapnhat().format(dateFormatter);
			detailModel.addRow(new Object[]{
					"Lô "+ idLo,
					soLuong,
					donGia,
					df.format(tongtien),
					ngayNhap
			});
			tongSoLuong += soLuong;
			tongTien += tongtien;
		}

		// Hàng tổng cộng
		detailModel.addRow(new Object[]{
				"Tổng cộng: ",
				tongSoLuong,
				"",
				df.format(tongTien),
				""
		});

		setVisible(true);
	}

}