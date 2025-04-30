package GUI;

import BUS.*;
import DTO.*;
import GUI.Dialog.ThongKeNhapKhoDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private HoaDonBUS hoaDonBUS = new HoaDonBUS();
	private PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
	private NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
	private TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
	private SanPhamBUS sanPhamBUS = new SanPhamBUS();
	private JTable tableThongKe;
	private JLabel lblMonth;
	private JComboBox comboYear, comboMonth, comboFilterOption;
	private JButton btnXemLoiNhuan, btnXemNhapKho, btnXemSanPham;

	private List<String> thongKeJTableHeaders;
	DefaultTableModel thongKeJTableModel;
	JScrollPane tableThongKeScrollPane;
	private Map<String, Integer> tenNLToIdMap = new HashMap<>(); // Map để lưu tenNL -> idNL

	private int clickedRow = -1;
	private int clickedColumn = -1;
	/**
	 * Create the panel.
	 */
	public ThongKePanel() {
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 887, 508);
		setLayout(null);

		// Label chức năng
		JLabel lblSearch = new JLabel("Thống kê báo cáo");
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSearch.setBounds(20, 10, 150, 25);
		add(lblSearch);

		// Label filterOption
		JLabel lblFilterOption = new JLabel("Lọc theo:");
		lblFilterOption.setBounds(20, 40, 70, 25);
		add(lblFilterOption);
		comboFilterOption = new JComboBox<>();
		comboFilterOption.setBounds(90, 40, 85, 25);
		add(comboFilterOption);

		// Label year
		JLabel lblYear = new JLabel("Năm:");
		lblYear.setBounds(185, 40, 45, 25);
		add(lblYear);
		comboYear = new JComboBox<Object>();
		comboYear.setBounds(230, 40, 85, 25);
		add(comboYear);

		// Label month
		lblMonth = new JLabel("Từ tháng:");
		lblMonth.setBounds(325, 40, 70, 25);
		add(lblMonth);
		comboMonth = new JComboBox<>();
		comboMonth.setBounds(395, 40, 85, 25);
		add(comboMonth);

		// Nút xem thống kê doanh thu
		btnXemLoiNhuan = new JButton("Xem lợi nhuận");
		btnXemLoiNhuan.setBounds(515, 39, 110, 27);
		add(btnXemLoiNhuan);

		// Nút xem thống kê nhập kho
		btnXemNhapKho = new JButton("Xem nhập kho");
		btnXemNhapKho.setBounds(635, 39, 110, 27);
		add(btnXemNhapKho);

		// Nút xem sản phẩm bán ra
		btnXemSanPham = new JButton("Xem sản phẩm");
		btnXemSanPham.setBounds(755, 39, 110, 27);
		add(btnXemSanPham);

		/**
		 * Table sẽ được hiển thị động
		 */
		// Ẩn lựa chọn tháng trước
		lblMonth.setVisible(false);
		comboMonth.setVisible(false);

		// setup comboBox
		setupComboFilterOption();
		setupComboYear();
		//setupComboMonth();


		// Gán sự kiện
		setupEventListeners();
	}

	//													===Các hàm setUp cho GUI===
	// Set up các option trong comboBox filterOption
	private void setupComboFilterOption() {
		comboFilterOption.removeAllItems();
		comboFilterOption.addItem(new ComboItem(0, "--- Năm ---"));
		comboFilterOption.addItem(new ComboItem(1, "--- Tháng"));
	}

	// Set up các option trong comboBox year
	private void setupComboYear() {
		comboYear.removeAllItems();
		comboYear.addItem(new ComboItem(0, "--- Chọn năm ---"));
		int startYear = 2025; //Năm kể từ lúc ứng dụng bắt đầu đi vào hoạt động (có thể dùng truy vấn db để chặt chẽ hơn)
		for (int i = startYear; i <= LocalDate.now().getYear(); i++) {
			comboYear.addItem(new ComboItem(i, "Năm " + i));
		}
	}

	// Set up các option trong comboBox startMonth
	private void setupComboMonth() {
		comboMonth.removeAllItems();
		comboMonth.addItem(new ComboItem(0, "--- Chọn tháng ---"));
		ComboItem selectedYearItem = (ComboItem) comboYear.getSelectedItem();
		int selectedYear = selectedYearItem.getKey();
		System.out.println(selectedYear);

		if (selectedYear == 0) {
			return; // Nếu năm chưa được chọn, chỉ lây option mặc định
		}

		//Nếu năm được chọn trước đó là năm hiện tại, chỉ được chọn đến tháng hiện tại
		if (selectedYear == LocalDate.now().getYear()) {
			for (int i = 1; i <= LocalDate.now().getMonthValue(); i++) {
				comboMonth.addItem(new ComboItem(i, "Tháng " + i));
			}
		} else { //Còn không thì cho chọn đến tháng 12
			for (int i = 1; i <= 12; i++) {
				comboMonth.addItem(new ComboItem(i, "Tháng " + i));
			}
		}
	}



	// Set up các eventListener
	private void setupEventListeners() {
		comboFilterOption.addActionListener(
				e -> {
					setupComboYear();

					ComboItem option = (ComboItem) comboFilterOption.getSelectedItem();
					if (option == null) return;

					lblMonth.setVisible(option.getKey() == 1);
					comboMonth.setVisible(option.getKey() == 1);
				}
		);

		comboYear.addActionListener(e -> {
			ComboItem selectedFilterOption = (ComboItem) comboFilterOption.getSelectedItem();
			ComboItem selectedYear = (ComboItem) comboYear.getSelectedItem();

			if (selectedFilterOption == null || selectedYear == null) return;

			if (selectedFilterOption.getKey() == 1 && selectedYear.getKey() > 0) { // nếu đang lọc theo tháng và có chọn năm
				setupComboMonth();
			}
		});

		//Nút xem doanh thu
		btnXemLoiNhuan.addActionListener(
				e -> {
					loadLoiNhuanTable();
					}
		);

		// Nút xem nhập kho
		btnXemNhapKho.addActionListener(
				e -> {
					// Đối với thống kê nhập kho có thêm sự kiện mở dialog xem chi tiết thống kê nhập kho
					loadNhapKhoTable();
					setupTableMouseListener();
				}
		);

		// Nút xem sản phẩm
		btnXemSanPham.addActionListener(
				e -> {
					//...
				}
		);
	}
	//												===END: Các hàm setUp cho GUI===

	//															===Các hàm tiện ích===

	private ImageIcon getScaledImage(int width, int height, String path) {
		// Load the original image
		ImageIcon originalIcon = new ImageIcon(DanhMucPanel.class.getResource(path));

		// Resize the image (e.g., to 50x50 pixels)
		Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		// Create a new ImageIcon with the scaled image
		return new ImageIcon(scaledImage);
	}

	private List<LocalDate[]> getWeekRanges(LocalDate startOfMonth, LocalDate endOfMonth) {
		List<LocalDate[]> weekRanges = new ArrayList<>();
		LocalDate[][] tempWeekRanges = new LocalDate[5][2]; //tempWeeks[0]: startDate, tempWeeks[1]: endDate
		tempWeekRanges[0] = new LocalDate[]{startOfMonth, startOfMonth.plusDays(6)};
		tempWeekRanges[1] = new LocalDate[]{startOfMonth.plusDays(7), startOfMonth.plusDays(13)};
		tempWeekRanges[2] = new LocalDate[]{startOfMonth.plusDays(14), startOfMonth.plusDays(20)};
		tempWeekRanges[3] = new LocalDate[]{startOfMonth.plusDays(21), startOfMonth.plusDays(27)};
		tempWeekRanges[4] = new LocalDate[]{startOfMonth.plusDays(28), endOfMonth};

		for (LocalDate[] week : tempWeekRanges) {
			LocalDate from = week[0];
			LocalDate to = week[1].isAfter(endOfMonth) ? endOfMonth : week[1];
			if (!from.isAfter(endOfMonth)) {
				//Nếu ngày bắt đầu không vượt quá ngày cuối của tháng
				// (tránh trường hợp tháng có 28 ngày mà ngày bắt đầu của tuần đang duyệt lại bằng 29)
				weekRanges.add(new LocalDate[]{from, to});
			}
		}
		return weekRanges;
	}

	private void setupThongKeByMonthTableHeaders(int year, int month) {
		// Xóa header trước đó nếu đã có header từ trước
		if (tableThongKe != null) tableThongKe.removeAll();

		thongKeJTableHeaders = new ArrayList<>();
		thongKeJTableHeaders.add("");
		// Tạo mốc tuần
		LocalDate startOfMonth = LocalDate.of(year, month, 1);
		LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
		List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

		// Thêm các cột từ tuần 1 - tuần 5
		for (int i = 0; i < weekRanges.size(); i++) {
			LocalDate from = weekRanges.get(i)[0];
			LocalDate to = weekRanges.get(i)[1];

			String label = String.format("%s - %s/%d", from.format(formatter), to.format(formatter), from.getYear());
			thongKeJTableHeaders.add(label);
		}

		// Thêm cột tổng cộng
		thongKeJTableHeaders.add("Tổng cộng");
		thongKeJTableHeaders.toArray(new String[0]);

		thongKeJTableModel = new DefaultTableModel(
				new Object[][]{
				},
				thongKeJTableHeaders.toArray()
		);
		tableThongKe = new JTable(thongKeJTableModel);
		tableThongKe.setEnabled(true); // Cho phép tương tác (chọn dòng), nhưng không cho chỉnh sửa
		tableThongKe.setDefaultEditor(Object.class, null); // Xóa editor mặc định để ngăn chỉnh sửa
	}

	private void setupThongKeByYearTableHeaders(int year) {
		// Xóa header cũ nếu có
		if (tableThongKe != null) tableThongKe.removeAll();

		thongKeJTableHeaders = new ArrayList<>();
		thongKeJTableHeaders.add("");

		//Tháng 1 - Tháng 12
		for (int i = 1; i <= 12; i++) {
			thongKeJTableHeaders.add(i+"/"+year);
		}
		// Cột tổng cộng
		thongKeJTableHeaders.add("Tổng cộng");

		thongKeJTableHeaders.toArray(new String[0]);
		thongKeJTableModel = new DefaultTableModel(
				new Object[][]{
				},
				thongKeJTableHeaders.toArray()
		);
		tableThongKe = new JTable(thongKeJTableModel);
		tableThongKe.setEnabled(true); // Cho phép tương tác (chọn dòng), nhưng không cho chỉnh sửa
		tableThongKe.setDefaultEditor(Object.class, null); // Xóa editor mặc định để ngăn chỉnh sửa
	}

	private void setupTableScrollPane() {
		if (tableThongKeScrollPane != null) {
			remove(tableThongKeScrollPane); // Xóa scrollpane cũ khỏi panel nếu có
		}
		tableThongKeScrollPane = new JScrollPane();
		tableThongKeScrollPane.setBounds(20, 92, 847, 380);
		add(tableThongKeScrollPane);
		tableThongKeScrollPane.setViewportView(tableThongKe);

		// Xóa dữ liệu cũ
		thongKeJTableModel.setRowCount(0);
	}

	//							===Các hàm setup sự kiện hiện dialog khi nhấn vào một ô===
	// Thêm sự kiện đổi màu khi chọn vào ô có thể xem chi tiết nhập kho
	class CustomTableCellRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component c = super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
			if (column > 0 && row < table.getRowCount() - 1 && value != null && Integer.valueOf(value.toString().replaceAll("[^\\d]", "")) > 0) {
				c.setBackground(new Color(255, 246, 0)); // Cadmium yellow
			} else {
				c.setBackground(table.getBackground());
			}
//			c.setForeground(table.getForeground()); // Giữ màu chữ mặc định
			return c;
		}
	}

	private void setupTableCellRenderer() {
		tableThongKe.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
	}
	//

	private void setupTableMouseListener() {
		setupTableCellRenderer();
		// Đổi con trỏ khi hover
		tableThongKe.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				int row = tableThongKe.rowAtPoint(p);
				int column = tableThongKe.columnAtPoint(p);
				Object cellValue = tableThongKe.getValueAt(row, column);
				if (row >= 0 && column > 0 && Integer.valueOf(cellValue.toString().replaceAll("[^\\d]", "")) > 0 &&  cellValue != null) {
					tableThongKe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					tableThongKe.setCursor(Cursor.getDefaultCursor());
				}
			}
		});


		// Mở dialog khi double-click
		tableThongKe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Lấy giá trị ô được chỉ đến
				Point p = e.getPoint();
				int row = tableThongKe.rowAtPoint(p);
				int column = tableThongKe.columnAtPoint(p);
				Object cellValue = null;
				if (row >= 0 && column >= 0) {
					cellValue = tableThongKe.getValueAt(row, column);
				}
				if (e.getClickCount() == 1) {
					// Xử lý click một lần
					if (e.getClickCount() == 1) {
						if (row >= 0 && column > 0 && cellValue != null && Integer.valueOf(cellValue.toString().replaceAll("[^\\d]", "")) > 0) {
							if (clickedRow != row || clickedColumn != column) {
								clickedRow = row;
								clickedColumn = column;
								tableThongKe.repaint(); // Repaint để áp dụng màu mới
							}
							tableThongKe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						} else {
							if (clickedRow != -1 || clickedColumn != -1) {
								clickedRow = -1;
								clickedColumn = -1;
								tableThongKe.repaint(); // Repaint để xóa màu
							}
							tableThongKe.setCursor(Cursor.getDefaultCursor());
						}
					}
				}
				if (e.getClickCount() == 2) {
//					row = tableThongKe.rowAtPoint(e.getPoint());
//					column = tableThongKe.columnAtPoint(e.getPoint());
//					cellValue = tableThongKe.getValueAt(row, column);
					if (row >= 0 && column > 0 && Integer.valueOf(cellValue.toString().replaceAll("[^\\d]", "")) > 0 &&  cellValue != null) {
						// Lấy filter option hiện tại
						ComboItem selectedFilterOptionItem = (ComboItem) comboFilterOption.getSelectedItem();
						int filterOption = selectedFilterOptionItem.getKey();
						ComboItem selectedYearItem = (ComboItem) comboYear.getSelectedItem();
						int year = selectedYearItem.getKey();

						if (filterOption == 0) {
							// Lọc theo năm (12 tháng)
							// Vì bảng có 14 cột, trừ cột đầu và cột cuối => cột 1 -> cột 12 tương ứng tháng 1 -> tháng 12
							LocalDate start;
							LocalDate end;
							if (column == tableThongKe.getColumnCount() - 1) { //Cột tổng => Xem cả năm
								start = LocalDate.of(year,1,1);
								end = LocalDate.of(year, 12, 31);
							} else {
								int month = column;
								start = LocalDate.of(year, month, 1);
								end = start.withDayOfMonth(start.lengthOfMonth());
							}
							showNhapKhoDetailDialog(row, start, end);
						} else if (filterOption == 1) {
							// Lọc theo tháng (5 tuần)
							ComboItem selectedMonthItem = (ComboItem) comboMonth.getSelectedItem();
							int month = selectedMonthItem.getKey();
							LocalDate startOfMonth = LocalDate.of(year, month, 1);
							LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
							List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);
							// Nếu ô được chọn có cột nằm trong phạm vi 5 cột tuần
							if (column< weekRanges.size() - 1) {
								//Lấy ra khoảng thời gian của cột tương ứng
								LocalDate[] range = weekRanges.get(column - 1);
								// Pattern: dd/MM - dd/MM year
								showNhapKhoDetailDialog(row, range[0], range[1]);
							} else if (column == tableThongKe.getColumnCount() - 1) { // Nếu là cột tổng => Xem cả tháng
								showNhapKhoDetailDialog(row, startOfMonth, endOfMonth);
							}
						}
					}
				}
			}
		});
	}

	private void showNhapKhoDetailDialog(int row, LocalDate start, LocalDate end) {
		String tenNguyenLieu = (String) tableThongKe.getValueAt(row, 0);
		Integer idNL = tenNLToIdMap.get(tenNguyenLieu);
		if (idNL == null) return;
		NguyenLieuDTO nl = nguyenLieuBUS.findByIdNL(idNL);
		if (nl == null) return;

		// Lấy danh sách phiếu nhập
		List<PhieuNhapDTO> danhSachPN = phieuNhapBUS.searchPhieuNhapByDate(Date.valueOf(start), Date.valueOf(end));
		List<Lo_NguyenLieuDTO> danhSachLo = new ArrayList<>();

		for (PhieuNhapDTO pn : danhSachPN) {
			List<Lo_NguyenLieuDTO> loList = phieuNhapBUS.getAllLo_NLByIDPN(pn.getIdPN());
			for (Lo_NguyenLieuDTO lo : loList) {
				if (lo.getIdNL() == nl.getIdNL()) {
					danhSachLo.add(lo);
				}
			}
		}

		new ThongKeNhapKhoDialog(Date.valueOf(start), Date.valueOf(end), idNL, danhSachLo);
	}
	//						===END: Các hàm setup sự kiện hiện dialog khi nhấn vào một ô===


	// Hàm load data doanh thu vào bảng
	private void loadLoiNhuanTable() {
		// Get selected year and months
		ComboItem selectedFilterOptionItem = (ComboItem) comboFilterOption.getSelectedItem();
		int filterOption = selectedFilterOptionItem.getKey();
		ComboItem selectedYearItem = (ComboItem) comboYear.getSelectedItem();
		int year = selectedYearItem.getKey();

		if (year == 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn năm hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}

		switch (filterOption) {
			case 0:
				//setup table
				setupThongKeByYearTableHeaders(year);
				setupTableScrollPane();

				// Khởi tạo mảng lưu trữ thông tin cho các ô trong table
				long[] doanhThu = new long[12]; // Tháng 1 to Tháng 5
				long[] tongChi = new long[12];
				long[] loiNhuan = new long[12];
				long tongDoanhThu = 0, tongChiPhi = 0, tongLoiNhuan = 0;

				for (int i = 0; i < 12; i++) {
					LocalDate startOfMonth = LocalDate.of(year, i+1, 1);
					LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

					// Doanh thu từng tháng từng tháng
					List<HoaDonDTO> hoaDonList = hoaDonBUS.searchHoaDonByDate(Date.valueOf(startOfMonth), Date.valueOf(endOfMonth));
					long doanhThuThang = hoaDonBUS.getAllHDTongTien(hoaDonList);
					doanhThu[i] += doanhThuThang;

					// Tổng chi từng tháng
					List<PhieuNhapDTO> phieuNhapList = phieuNhapBUS.searchPhieuNhapByDate(Date.valueOf(startOfMonth), Date.valueOf(endOfMonth));
					long tongChiThang= phieuNhapBUS.getAllPNTongTien(phieuNhapList);
					tongChi[i] += tongChiThang;

					// Lợi nhuận từng tháng
					loiNhuan[i] += doanhThuThang - tongChiThang;

					// Tổng năm
					tongDoanhThu += doanhThu[i];
					tongChiPhi += tongChi[i];
					tongLoiNhuan += loiNhuan[i];
				}

				// Format numbers with commas
				DecimalFormat df = new DecimalFormat("#,### VNĐ");

				//Dòng doanh thu
				Object[] rowDoanhThu = new Object[thongKeJTableHeaders.size()];
				rowDoanhThu[0] = "Doanh Thu";
				for (int i = 0; i < 12; i++) {
					rowDoanhThu[i + 1] = df.format(doanhThu[i]);
				}
				rowDoanhThu[rowDoanhThu.length - 1] = df.format(tongDoanhThu);
				thongKeJTableModel.addRow(rowDoanhThu);

				// Dòng tổng chi
				Object[] rowTongChi = new Object[thongKeJTableHeaders.size()];
				rowTongChi[0] = "Tổng chi";
				for (int i = 0; i < 12; i++) {
					rowTongChi[i + 1] = df.format(tongChi[i]);
				}
				rowTongChi[rowTongChi.length - 1] = df.format(tongChiPhi);
				thongKeJTableModel.addRow(rowTongChi);

				// Dòng lợi nhuận
				Object[] rowLoiNhuan = new Object[thongKeJTableHeaders.size()];
				rowLoiNhuan[0] = "Lợi nhuận";
				for (int i = 0; i < 12; i++) {
					rowLoiNhuan[i + 1] = df.format(loiNhuan[i]);
				}
				rowLoiNhuan[rowLoiNhuan.length - 1] = df.format(tongLoiNhuan);
				thongKeJTableModel.addRow(rowLoiNhuan);

				break;
			case 1:
				ComboItem selectedMonthItem = (ComboItem) comboMonth.getSelectedItem();
				int month = selectedMonthItem.getKey();
				if (month == 0) {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//setup table
				setupThongKeByMonthTableHeaders(year, month);
				setupTableScrollPane();

				// Khởi tạo mảng lưu trữ thông tin cho các ô trong table
				doanhThu = new long[5]; // Tuần 1 to Tuần 5
				tongChi = new long[5];
				loiNhuan = new long[5];
				tongDoanhThu = 0;
				tongChiPhi = 0;
				tongLoiNhuan = 0;

				// Ngày đầu và ngày cuối cùng của tháng
				LocalDate startOfMonth = LocalDate.of(year, month, 1);
				LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
				// Lấy khoảng tuần dựa trên startDate và endDate
				List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);

				// Tính doanh thu và chi phí của từng tuần
				for (int i = 0; i < weekRanges.size(); i++) {
					LocalDate weekStart = weekRanges.get(i)[0];
					LocalDate weekEnd = weekRanges.get(i)[1];

					// Chuyển đổi LocalDate về java.sql.Date
					java.sql.Date sqlWeekStart = java.sql.Date.valueOf(weekStart);
					java.sql.Date sqlWeekEnd = java.sql.Date.valueOf(weekEnd);

					// Lấy lợi nhuận
					List<HoaDonDTO> hoaDonList = hoaDonBUS.searchHoaDonByDate(sqlWeekStart, sqlWeekEnd);
					long doanhThuTuan = hoaDonBUS.getAllHDTongTien(hoaDonList);
					doanhThu[i] += doanhThuTuan;

					// Lấy tổng chi
					List<PhieuNhapDTO> phieuNhapList = phieuNhapBUS.searchPhieuNhapByDate(sqlWeekStart, sqlWeekEnd);
					long tongChiTuan = phieuNhapBUS.getAllPNTongTien(phieuNhapList);
					tongChi[i] += tongChiTuan;

					// Lợi nhuận = doanh thu - tổng chi
					loiNhuan[i] += (doanhThuTuan - tongChiTuan);

					// Tổng tháng
					tongDoanhThu += doanhThu[i];
					tongChiPhi += tongChi[i];
					tongLoiNhuan += loiNhuan[i];
				}

				// Format numbers with commas
				df = new DecimalFormat("#,### VNĐ");

				//Dòng doanh thu
				rowDoanhThu = new Object[thongKeJTableHeaders.size()];
				rowDoanhThu[0] = "Doanh Thu";
				for (int i = 0; i < weekRanges.size(); i++) {
					rowDoanhThu[i + 1] = df.format(doanhThu[i]);
				}
				rowDoanhThu[rowDoanhThu.length - 1] = df.format(tongDoanhThu);
				thongKeJTableModel.addRow(rowDoanhThu);

				// Dòng tổng chi
				rowTongChi = new Object[thongKeJTableHeaders.size()];
				rowTongChi[0] = "Tổng chi";
				for (int i = 0; i < weekRanges.size(); i++) {
					rowTongChi[i + 1] = df.format(tongChi[i]);
				}
				rowTongChi[rowTongChi.length - 1] = df.format(tongChiPhi);
				thongKeJTableModel.addRow(rowTongChi);

				// Dòng lợi nhuận
				rowLoiNhuan = new Object[thongKeJTableHeaders.size()];
				rowLoiNhuan[0] = "Lợi nhuận";
				for (int i = 0; i < weekRanges.size(); i++) {
					rowLoiNhuan[i + 1] = df.format(loiNhuan[i]);
				}
				rowLoiNhuan[rowLoiNhuan.length - 1] = df.format(tongLoiNhuan);
				thongKeJTableModel.addRow(rowLoiNhuan);


				break;
		}
	}

	// Hàm load data nhập kho vào bảng
	private void loadNhapKhoTable() {
		// Get selected year and months
		ComboItem selectedFilterOptionItem = (ComboItem) comboFilterOption.getSelectedItem();
		int filterOption = selectedFilterOptionItem.getKey();
		ComboItem selectedYearItem = (ComboItem) comboYear.getSelectedItem();
		int year = selectedYearItem.getKey();

		if (year == 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn năm hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Load tất cả nguyên liệu
		List<NguyenLieuDTO> nguyenLieuList = nguyenLieuBUS.getAll();
		// Format tiền
		DecimalFormat df = new DecimalFormat("#,### VNĐ");

		switch (filterOption) {
			case 0:
				setupThongKeByYearTableHeaders(year);
				setupTableScrollPane();

				// Hàng tổng dưới cùng
				long[] tong = new long [12];
				// Duyệt qua danh sách nguyên liệu, mỗi nguyên liệu là một dòng trong bảng
				for (NguyenLieuDTO nl : nguyenLieuList) {
					tenNLToIdMap.put(nl.getTenNL(), nl.getIdNL());
					long[] tongTien = new long[12];
					long tongTienNam = 0;

					for (int i = 0; i < 12; i++) {
						LocalDate startOfMonth = LocalDate.of(year, i+1, 1);
						LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

						// Tổng tiền từng tháng
						// Lấy danh sách phiếu nhập nằm trong tháng đang duyệt
						List<PhieuNhapDTO> phieuNhapList = phieuNhapBUS.searchPhieuNhapByDate(Date.valueOf(startOfMonth), Date.valueOf(endOfMonth));
						for (PhieuNhapDTO pn : phieuNhapList) {
							// Với mỗi phiếu nhập, lấy ra danh sách lô nguyên liệu tương ứng
							List<Lo_NguyenLieuDTO> lo_NLList = phieuNhapBUS.getAllLo_NLByIDPN(pn.getIdPN());
							for (Lo_NguyenLieuDTO lo : lo_NLList) { // Duyệt qua danh sách lô nguyên liệu
								if (lo.getIdNL() == nl.getIdNL()) {  // Nếu lô nguyện liệu có idNL là idNL của loại nguyên liệu đang được duyệt, tính tổng tiền
									tongTien[i] += (long) (lo.getSoluongnhap() * lo.getDongia());
									tong[i] += tongTien[i];
								}
							}
						}
						tongTienNam += tongTien[i];
					}
					Object[] row = new Object[thongKeJTableHeaders.size()];
					row[0] = nl.getTenNL();
					for (int i = 0; i < 12; i++) {
						row[i + 1] = tongTien[i];
					}
					row[row.length - 1] = df.format(tongTienNam);
					thongKeJTableModel.addRow(row);
				}
				int tongNam = 0;
				Object[] rowTong = new Object[thongKeJTableHeaders.size()];
				rowTong[0] = "Tổng";
				for (int i = 0; i < 12; i++) {
					rowTong[i+1] = tong[i];
					tongNam += tong[i];
				}
				rowTong[rowTong.length - 1] = df.format(tongNam);
				thongKeJTableModel.addRow(rowTong);
				break;
			case 1:
				ComboItem selectedMonthItem = (ComboItem) comboMonth.getSelectedItem();
				int month = selectedMonthItem.getKey();
				if (month == 0) {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//setup table
				setupThongKeByMonthTableHeaders(year, month);
				setupTableScrollPane();

				for (NguyenLieuDTO nl : nguyenLieuList) {
					tenNLToIdMap.put(nl.getTenNL(), nl.getIdNL());
					long[] tongTien = new long[5];
					long tongTienThang = 0;

					// Ngày đầu và ngày cuối cùng của tháng
					LocalDate startOfMonth = LocalDate.of(year, month, 1);
					LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
					// Lấy khoảng tuần dựa trên startDate và endDate
					List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);

					// Tính tổng tiền từng tuần
					for (int i = 0; i < weekRanges.size(); i++) {
						LocalDate weekStart = weekRanges.get(i)[0];
						LocalDate weekEnd = weekRanges.get(i)[1];

						// Chuyển đổi LocalDate về java.sql.Date
						java.sql.Date sqlWeekStart = java.sql.Date.valueOf(weekStart);
						java.sql.Date sqlWeekEnd = java.sql.Date.valueOf(weekEnd);

						// Tổng tiền từng tháng
						// Lấy danh sách phiếu nhập nằm trong tháng đang duyệt
						List<PhieuNhapDTO> phieuNhapList = phieuNhapBUS.searchPhieuNhapByDate(sqlWeekStart, sqlWeekEnd);
						for (PhieuNhapDTO pn : phieuNhapList) {
							// Với mỗi phiếu nhập, lấy ra danh sách lô nguyên liệu tương ứng
							List<Lo_NguyenLieuDTO> lo_NLList = phieuNhapBUS.getAllLo_NLByIDPN(pn.getIdPN());
							for (Lo_NguyenLieuDTO lo : lo_NLList) { // Duyệt qua danh sách lô nguyên liệu
								if (lo.getIdNL() == nl.getIdNL()) {  // Nếu lô nguyện liệu có idNL là idNL của loại nguyên liệu đang được duyệt, tính tổng tiền
									tongTien[i] += (long) (lo.getSoluongnhap() * lo.getDongia());
								}
							}
						}
						tongTienThang += tongTien[i];
					}
					Object[] row = new Object[thongKeJTableHeaders.size()];
					row[0] = nl.getTenNL();
					for (int i = 0; i < weekRanges.size(); i++) {
						row[i + 1] = df.format(tongTien[i]);
					}
					row[row.length - 1] = df.format(tongTienThang);
					thongKeJTableModel.addRow(row);
				}
				break;
		}
	}

	// Hàm xử lý khi nhấn vào một ô trong table thống kê nhập kho

	// Hàm load data sản phẩm vào bảng

	//														===END: Các hàm tiện ích===
}
