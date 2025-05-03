package GUI;

import GUI.Dialog.ThongKeNhapKhoDialog;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import BUS.CT_HoaDonBUS;
import BUS.HoaDonBUS;
import BUS.Lo_NguyenLieuBUS;
import BUS.NguyenLieuBUS;
import BUS.PhieuNhapBUS;
import BUS.SanPhamBUS;
import DTO.CT_HoaDonDTO;
import DTO.ComboItem;
import DTO.HoaDonDTO;
import DTO.Lo_NguyenLieuDTO;
import DTO.NguyenLieuDTO;
import DTO.PhieuNhapDTO;
import DTO.SanPhamDTO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
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
	private HoaDonBUS hoaDonBus = new HoaDonBUS();
	private CT_HoaDonBUS ct_HoaDonBus = new CT_HoaDonBUS();
	private PhieuNhapBUS phieuNhapBus = new PhieuNhapBUS();
	private Lo_NguyenLieuBUS lo_NguyenLieuBus = new Lo_NguyenLieuBUS();
	private NguyenLieuBUS nguyenLieuBus = new NguyenLieuBUS();
	private SanPhamBUS sanPhamBus = new SanPhamBUS();
	private JTable tableThongKe;
	private JLabel lblStatics, lblMonth;
	private JComboBox<ComboItem> comboYear, comboMonth, comboFilterOption;
	private JButton btnXemLoiNhuan, btnXemNhapKho, btnXemSanPham;

	private List<String> thongKeJTableHeaders;
	private DefaultTableModel thongKeJTableModel;
	private JScrollPane tableThongKeScrollPane;
	private Map<String, Integer> tenNLToIdMap = new HashMap<>(); // Map để lưu tenNL -> idNL

	private int clickedRow = -1;
	private int clickedColumn = -1;

	/**
	 * Create the panel.
	 */
	public ThongKePanel() {
		setBackground(new Color(255, 240, 220));
		setLayout(new BorderLayout());

		// Label chức năng (NORTH)
		lblStatics = new JLabel("Thống kê báo cáo");
		lblStatics.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStatics.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(lblStatics, BorderLayout.NORTH);

		// BodyPanel (CENTER) with BoxLayout Y_AXIS
		JPanel bodyPanel = new JPanel();
		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
		bodyPanel.setBackground(new Color(255, 240, 220));
		add(bodyPanel, BorderLayout.CENTER);

		// OptionPanel with BoxLayout Y_AXIS
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		optionPanel.setBackground(new Color(255, 240, 220));
		bodyPanel.add(optionPanel);

		// FilterPanel with FlowLayout
		JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		filterPanel.setBackground(new Color(255, 240, 220));
		optionPanel.add(filterPanel);

		// Filter options in FilterPanel
		JLabel lblFilterOption = new JLabel("Lọc theo:");
		lblFilterOption.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		filterPanel.add(lblFilterOption);
		comboFilterOption = new JComboBox<>();
		comboFilterOption.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboFilterOption.setPreferredSize(new Dimension(130, 25));
		filterPanel.add(comboFilterOption);

		JLabel lblYear = new JLabel("Năm:");
		lblYear.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		filterPanel.add(lblYear);
		comboYear = new JComboBox<>();
		comboYear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboYear.setPreferredSize(new Dimension(130, 25));
		filterPanel.add(comboYear);

		lblMonth = new JLabel("Tháng:");
		lblMonth.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		filterPanel.add(lblMonth);
		comboMonth = new JComboBox<>();
		comboMonth.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboMonth.setPreferredSize(new Dimension(130, 25));
		filterPanel.add(comboMonth);

		// optionButtonsPanel with FlowLayout
		JPanel optionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		optionButtonsPanel.setBackground(new Color(255, 240, 220));
		optionPanel.add(optionButtonsPanel);

		// Buttons in optionButtonsPanel
		btnXemLoiNhuan = new JButton("Xem lợi nhuận");
		btnXemLoiNhuan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnXemLoiNhuan.setBackground(new Color(173, 255, 47));
		btnXemLoiNhuan.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnXemLoiNhuan.setMinimumSize(new Dimension(125, 21));
		btnXemLoiNhuan.setMaximumSize(new Dimension(125, 21));
		btnXemLoiNhuan.setPreferredSize(new Dimension(150, 27));
		optionButtonsPanel.add(btnXemLoiNhuan);

		btnXemNhapKho = new JButton("Xem nhập kho");
		btnXemNhapKho.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnXemNhapKho.setBackground(new Color(255, 192, 203));
		btnXemNhapKho.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnXemNhapKho.setPreferredSize(new Dimension(150, 27));
		optionButtonsPanel.add(btnXemNhapKho);

		btnXemSanPham = new JButton("Xem sản phẩm");
		btnXemSanPham.setBackground(new Color(255, 215, 0));
		btnXemSanPham.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnXemSanPham.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnXemSanPham.setPreferredSize(new Dimension(150, 27));
		optionButtonsPanel.add(btnXemSanPham);

		// TablePanel for the table
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setBackground(new Color(255, 240, 220));
		tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		bodyPanel.add(tablePanel);

		// Initialize table scroll pane (will be populated dynamically)
		tableThongKeScrollPane = new JScrollPane();
		tableThongKeScrollPane.getViewport().setBackground(new Color(255, 240, 220));
		tablePanel.add(tableThongKeScrollPane, BorderLayout.CENTER);

		// Ẩn lựa chọn tháng trước
		lblMonth.setVisible(false);
		comboMonth.setVisible(false);

		// Setup comboBox
		setupComboFilterOption();
		setupComboYear();

		// Gán sự kiện
		setupEventListeners();
		
	}

	// ===Các hàm setUp cho GUI===
	private void setupComboFilterOption() {
		comboFilterOption.removeAllItems();
		comboFilterOption.addItem(new ComboItem(0, "--- Năm ---"));
		comboFilterOption.addItem(new ComboItem(1, "--- Tháng"));
	}

	private void setupComboYear() {
		comboYear.removeAllItems();
		comboYear.addItem(new ComboItem(0, "--- Chọn năm ---"));
		int startYear = 2025;
		for (int i = startYear; i <= LocalDate.now().getYear(); i++) {
			comboYear.addItem(new ComboItem(i, "Năm " + i));
		}
	}

	private void setupComboMonth() {
		comboMonth.removeAllItems();
		comboMonth.addItem(new ComboItem(0, "--- Chọn tháng ---"));
		ComboItem selectedYearItem = (ComboItem) comboYear.getSelectedItem();
		int selectedYear = selectedYearItem.getKey();

		if (selectedYear == 0) {
			return;
		}

		if (selectedYear == LocalDate.now().getYear()) {
			for (int i = 1; i <= LocalDate.now().getMonthValue(); i++) {
				comboMonth.addItem(new ComboItem(i, "Tháng " + i));
			}
		} else {
			for (int i = 1; i <= 12; i++) {
				comboMonth.addItem(new ComboItem(i, "Tháng " + i));
			}
		}
	}

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
			if (selectedFilterOption.getKey() == 1 && selectedYear.getKey() > 0) {
				setupComboMonth();
			}
		});

		btnXemLoiNhuan.addActionListener(
				e -> {
					loadLoiNhuanTable();
					setupTableCellRenderer();
				}
		);

		btnXemNhapKho.addActionListener(
				e -> {
					loadNhapKhoTable();
					setupTableMouseListener();
				}
		);

		btnXemSanPham.addActionListener(
				e -> {
					loadSanPhamTable();
				}
		);
	}
	// ===END: Các hàm setUp cho GUI===

	private List<LocalDate[]> getWeekRanges(LocalDate startOfMonth, LocalDate endOfMonth) {
		List<LocalDate[]> weekRanges = new ArrayList<>();
		LocalDate[][] tempWeekRanges = new LocalDate[5][2];
		tempWeekRanges[0] = new LocalDate[]{startOfMonth, startOfMonth.plusDays(6)};
		tempWeekRanges[1] = new LocalDate[]{startOfMonth.plusDays(7), startOfMonth.plusDays(13)};
		tempWeekRanges[2] = new LocalDate[]{startOfMonth.plusDays(14), startOfMonth.plusDays(20)};
		tempWeekRanges[3] = new LocalDate[]{startOfMonth.plusDays(21), startOfMonth.plusDays(27)};
		tempWeekRanges[4] = new LocalDate[]{startOfMonth.plusDays(28), endOfMonth};

		for (LocalDate[] week : tempWeekRanges) {
			LocalDate from = week[0];
			LocalDate to = week[1].isAfter(endOfMonth) ? endOfMonth : week[1];
			if (!from.isAfter(endOfMonth)) {
				weekRanges.add(new LocalDate[]{from, to});
			}
		}
		return weekRanges;
	}

	private void setupThongKeByMonthTableHeaders(int year, int month) {
		if (tableThongKe != null) tableThongKe.removeAll();
		thongKeJTableHeaders = new ArrayList<>();
		thongKeJTableHeaders.add("");
		LocalDate startOfMonth = LocalDate.of(year, month, 1);
		LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
		List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
		for (int i = 0; i < weekRanges.size(); i++) {
			LocalDate from = weekRanges.get(i)[0];
			LocalDate to = weekRanges.get(i)[1];
			String label = String.format("%s - %s/%d", from.format(formatter), to.format(formatter), from.getYear());
			thongKeJTableHeaders.add(label);
		}
		thongKeJTableHeaders.add("Tổng cộng");
		thongKeJTableHeaders.toArray(new String[0]);
		thongKeJTableModel = new DefaultTableModel(new Object[][]{}, thongKeJTableHeaders.toArray());
		tableThongKe = new JTable(thongKeJTableModel);
		tableThongKe.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableThongKe.setEnabled(true);
		tableThongKe.setDefaultEditor(Object.class, null);
	}

	private void setupThongKeByYearTableHeaders(int year) {
		if (tableThongKe != null) tableThongKe.removeAll();
		thongKeJTableHeaders = new ArrayList<>();
		thongKeJTableHeaders.add("");
		for (int i = 1; i <= 12; i++) {
			thongKeJTableHeaders.add(i + "/" + year);
		}
		thongKeJTableHeaders.add("Tổng cộng");
		thongKeJTableHeaders.toArray(new String[0]);
		thongKeJTableModel = new DefaultTableModel(new Object[][]{}, thongKeJTableHeaders.toArray());
		tableThongKe = new JTable(thongKeJTableModel);
		tableThongKe.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableThongKe.setEnabled(true);
		tableThongKe.setDefaultEditor(Object.class, null);
	}

	private void setupTableScrollPane() {
		tableThongKeScrollPane.setViewportView(tableThongKe);
		tableThongKeScrollPane.getViewport().setBackground(new Color(255, 240, 220));
		thongKeJTableModel.setRowCount(0);
	}

	class CustomTableCellRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component cell = super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);

			// Mặc định reset màu
			cell.setBackground(Color.WHITE);

			if (column == 0 || value == null || row >= table.getRowCount()) {
				return cell;
			}

			try {
				String rawValue = value.toString().replaceAll("[^\\d\\-]", "");
				if (!rawValue.isEmpty()) {
					long numericValue = Long.parseLong(rawValue);
					String rowName = table.getValueAt(row, 0).toString().trim().toLowerCase();

					// Tô màu chỉ dòng "lợi nhuận"
					if (rowName.contains("lợi nhuận")) {
						if (numericValue > 0) {
							cell.setBackground(new Color(0, 128, 0)); // xanh
						} else if (numericValue < 0) {
							cell.setBackground(new Color(255,187,51)); // saffron
						}
					}
				}
			} catch (NumberFormatException e) {
				// Không tô nếu parse lỗi
				e.printStackTrace();
			}

			return cell;
		}
	}

	private void setupTableCellRenderer() {
		tableThongKe.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
	}

	private void setupTableMouseListener() {
		tableThongKe.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				int row = tableThongKe.rowAtPoint(p);
				int column = tableThongKe.columnAtPoint(p);
				Object cellValue = tableThongKe.getValueAt(row, column);
				if (row >= 0 && row < tableThongKe.getRowCount() - 1 &&  column > 0 && Integer.valueOf(cellValue.toString().replaceAll("[^\\d]", "")) > 0 && cellValue != null) {
					tableThongKe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					tableThongKe.setCursor(Cursor.getDefaultCursor());
				}
			}
		});

		tableThongKe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				int row = tableThongKe.rowAtPoint(p);
				int column = tableThongKe.columnAtPoint(p);
				Object cell = null;
				if (row >= 0 && column >= 0) {
					cell = tableThongKe.getValueAt(row, column);
				}
				int cellValue = Integer.valueOf(cell.toString().replaceAll("[^\\d]", ""));
				if (e.getClickCount() == 1) {
					if (row >= 0 && column > 0 && row < tableThongKe.getRowCount() - 1 && cell != null && cellValue > 0) {
						if (clickedRow != row || clickedColumn != column) {
							clickedRow = row;
							clickedColumn = column;
						}
						tableThongKe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					} else {
						if (clickedRow != -1 || clickedColumn != -1) {
							clickedRow = -1;
							clickedColumn = -1;
						}
						tableThongKe.setCursor(Cursor.getDefaultCursor());
					}
				}
				if (e.getClickCount() == 2) {
					if (row >= 0 && column > 0 && row < tableThongKe.getRowCount() - 1 && cell != null && cellValue > 0) {
						ComboItem selectedFilterOptionItem = (ComboItem) comboFilterOption.getSelectedItem();
						int filterOption = selectedFilterOptionItem.getKey();
						ComboItem selectedYearItem = (ComboItem) comboYear.getSelectedItem();
						int year = selectedYearItem.getKey();

						if (filterOption == 0) {
							LocalDate start;
							LocalDate end;
							if (column == tableThongKe.getColumnCount() - 1) {
								start = LocalDate.of(year, 1, 1);
								end = LocalDate.of(year, 12, 31);
							} else {
								int month = column;
								start = LocalDate.of(year, month, 1);
								end = start.withDayOfMonth(start.lengthOfMonth());
							}
							showNhapKhoDetailDialog(row, start, end);
						} else if (filterOption == 1) {
							ComboItem selectedMonthItem = (ComboItem) comboMonth.getSelectedItem();
							int month = selectedMonthItem.getKey();
							LocalDate startOfMonth = LocalDate.of(year, month, 1);
							LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
							List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);
							if (column < weekRanges.size() - 1) {
								LocalDate[] range = weekRanges.get(column - 1);
								showNhapKhoDetailDialog(row, range[0], range[1]);
							} else if (column == tableThongKe.getColumnCount() - 1) {
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
		NguyenLieuDTO nl = nguyenLieuBus.findByIdNL(idNL);
		if (nl == null) return;

		List<PhieuNhapDTO> danhSachPN = phieuNhapBus.searchCompleteByDate(Date.valueOf(start), Date.valueOf(end));
		List<Lo_NguyenLieuDTO> danhSachLo = new ArrayList<>();

		for (PhieuNhapDTO pn : danhSachPN) {
			List<Lo_NguyenLieuDTO> loList = lo_NguyenLieuBus.getAllByIdPN(pn.getIdPN());
			for (Lo_NguyenLieuDTO lo : loList) {
				if (lo.getIdNL() == nl.getIdNL()) {
					System.out.print(1);
					danhSachLo.add(lo);
				}
			}
		}

		new ThongKeNhapKhoDialog(Date.valueOf(start), Date.valueOf(end), idNL, danhSachLo);
	}

	private void loadLoiNhuanTable() {
		lblStatics.setText("Thống kê lợi nhuận");
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
				setupThongKeByYearTableHeaders(year);
				setupTableScrollPane();

				long[] doanhThu = new long[12];
				long[] tongChi = new long[12];
				long[] loiNhuan = new long[12];
				long tongDoanhThu = 0, tongChiPhi = 0, tongLoiNhuan = 0;

				for (int i = 0; i < 12; i++) {
					LocalDate startOfMonth = LocalDate.of(year, i + 1, 1);
					LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

					List<HoaDonDTO> hoaDonList = hoaDonBus.searchByDate(Date.valueOf(startOfMonth), Date.valueOf(endOfMonth));
					long doanhThuThang = hoaDonBus.getAllTongTien(hoaDonList);
					doanhThu[i] += doanhThuThang;

					List<PhieuNhapDTO> phieuNhapList = phieuNhapBus.searchCompleteByDate(Date.valueOf(startOfMonth), Date.valueOf(endOfMonth));
					long tongChiThang = phieuNhapBus.getAllTongTien(phieuNhapList);
					tongChi[i] += tongChiThang;

					loiNhuan[i] += doanhThuThang - tongChiThang;

					tongDoanhThu += doanhThu[i];
					tongChiPhi += tongChi[i];
					tongLoiNhuan += loiNhuan[i];
				}

				DecimalFormat df = new DecimalFormat("#,### VNĐ");

				Object[] rowDoanhThu = new Object[thongKeJTableHeaders.size()];
				rowDoanhThu[0] = "Doanh Thu";
				for (int i = 0; i < 12; i++) {
					rowDoanhThu[i + 1] = df.format(doanhThu[i]);
				}
				rowDoanhThu[rowDoanhThu.length - 1] = df.format(tongDoanhThu);
				thongKeJTableModel.addRow(rowDoanhThu);

				Object[] rowTongChi = new Object[thongKeJTableHeaders.size()];
				rowTongChi[0] = "Tổng chi";
				for (int i = 0; i < 12; i++) {
					rowTongChi[i + 1] = df.format(tongChi[i]);
				}
				rowTongChi[rowTongChi.length - 1] = df.format(tongChiPhi);
				thongKeJTableModel.addRow(rowTongChi);

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
				setupThongKeByMonthTableHeaders(year, month);
				setupTableScrollPane();

				doanhThu = new long[5];
				tongChi = new long[5];
				loiNhuan = new long[5];
				tongDoanhThu = 0;
				tongChiPhi = 0;
				tongLoiNhuan = 0;

				LocalDate startOfMonth = LocalDate.of(year, month, 1);
				LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
				List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);

				for (int i = 0; i < weekRanges.size(); i++) {
					LocalDate weekStart = weekRanges.get(i)[0];
					LocalDate weekEnd = weekRanges.get(i)[1];

					java.sql.Date sqlWeekStart = java.sql.Date.valueOf(weekStart);
					java.sql.Date sqlWeekEnd = java.sql.Date.valueOf(weekEnd);

					List<HoaDonDTO> hoaDonList = hoaDonBus.searchByDate(sqlWeekStart, sqlWeekEnd);
					long doanhThuTuan = hoaDonBus.getAllTongTien(hoaDonList);
					doanhThu[i] += doanhThuTuan;

					List<PhieuNhapDTO> phieuNhapList = phieuNhapBus.searchCompleteByDate(sqlWeekStart, sqlWeekEnd);
					long tongChiTuan = phieuNhapBus.getAllTongTien(phieuNhapList);
					tongChi[i] += tongChiTuan;

					loiNhuan[i] += (doanhThuTuan - tongChiTuan);

					tongDoanhThu += doanhThu[i];
					tongChiPhi += tongChi[i];
					tongLoiNhuan += loiNhuan[i];
				}

				df = new DecimalFormat("#,### VNĐ");

				rowDoanhThu = new Object[thongKeJTableHeaders.size()];
				rowDoanhThu[0] = "Doanh Thu";
				for (int i = 0; i < weekRanges.size(); i++) {
					rowDoanhThu[i + 1] = df.format(doanhThu[i]);
				}
				rowDoanhThu[rowDoanhThu.length - 1] = df.format(tongDoanhThu);
				thongKeJTableModel.addRow(rowDoanhThu);

				rowTongChi = new Object[thongKeJTableHeaders.size()];
				rowTongChi[0] = "Tổng chi";
				for (int i = 0; i < weekRanges.size(); i++) {
					rowTongChi[i + 1] = df.format(tongChi[i]);
				}
				rowTongChi[rowTongChi.length - 1] = df.format(tongChiPhi);
				thongKeJTableModel.addRow(rowTongChi);

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

	private void loadNhapKhoTable() {
		lblStatics.setText("Thống kê nhập kho nguyên liệu");
		ComboItem selectedFilterOptionItem = (ComboItem) comboFilterOption.getSelectedItem();
		int filterOption = selectedFilterOptionItem.getKey();
		ComboItem selectedYearItem = (ComboItem) comboYear.getSelectedItem();
		int year = selectedYearItem.getKey();

		if (year == 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn năm hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<NguyenLieuDTO> nguyenLieuList = nguyenLieuBus.getAll();
		DecimalFormat df = new DecimalFormat("#,### VNĐ");

		switch (filterOption) {
			case 0:
				setupThongKeByYearTableHeaders(year);
				setupTableScrollPane();

				long[] tongNhapKhoAllNLThang = new long[12];
				int tongNhapKhoNam = 0;

				for (NguyenLieuDTO nl : nguyenLieuList) {
					tenNLToIdMap.put(nl.getTenNL(), nl.getIdNL());
					long[] tongNhapKhoNLThang = new long[12];
					long tongNhapKhoNLNam = 0;

					for (int i = 0; i < 12; i++) {
						LocalDate startOfMonth = LocalDate.of(year, i + 1, 1);
						LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

						List<PhieuNhapDTO> phieuNhapList = phieuNhapBus.searchCompleteByDate(Date.valueOf(startOfMonth), Date.valueOf(endOfMonth));
						for (PhieuNhapDTO pn : phieuNhapList) {
							List<Lo_NguyenLieuDTO> lo_NLList = lo_NguyenLieuBus.getAllByIdPN(pn.getIdPN());
							for (Lo_NguyenLieuDTO lo : lo_NLList) {
								if (lo.getIdNL() == nl.getIdNL()) {
									tongNhapKhoNLThang[i] += (long) (lo.getSoluongnhap() * lo.getDongia());
									tongNhapKhoAllNLThang[i] += tongNhapKhoNLThang[i];
								}
							}
						}
						tongNhapKhoNLNam += tongNhapKhoNLThang[i];
					}
					Object[] row = new Object[thongKeJTableHeaders.size()];
					row[0] = nl.getTenNL();
					for (int i = 0; i < 12; i++) {
						row[i + 1] = tongNhapKhoNLThang[i];
					}
					row[row.length - 1] = df.format(tongNhapKhoNLNam);
					thongKeJTableModel.addRow(row);
				}

				Object[] rowTong = new Object[thongKeJTableHeaders.size()];
				rowTong[0] = "Tổng";
				for (int i = 0; i < 12; i++) {
					rowTong[i + 1] = tongNhapKhoAllNLThang[i];
					tongNhapKhoNam += tongNhapKhoAllNLThang[i];
				}
				rowTong[rowTong.length - 1] = df.format(tongNhapKhoNam);
				thongKeJTableModel.addRow(rowTong);
				break;
			case 1:
				ComboItem selectedMonthItem = (ComboItem) comboMonth.getSelectedItem();
				int month = selectedMonthItem.getKey();
				if (month == 0) {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
				setupThongKeByMonthTableHeaders(year, month);
				setupTableScrollPane();

				LocalDate startOfMonth = LocalDate.of(year, month, 1);
				LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
				List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);

				long[] tongNhapKhoAllNLTuan = new long[weekRanges.size()];
				long tongNhapKhoThang = 0;

				for (NguyenLieuDTO nl : nguyenLieuList) {
					tenNLToIdMap.put(nl.getTenNL(), nl.getIdNL());
					long[] tongNhapKhoNLTuan = new long[weekRanges.size()];
					long tongNhapKhoNLThang = 0;

					for (int i = 0; i < weekRanges.size(); i++) {
						LocalDate weekStart = weekRanges.get(i)[0];
						LocalDate weekEnd = weekRanges.get(i)[1];

						Date sqlWeekStart = Date.valueOf(weekStart);
						Date sqlWeekEnd = Date.valueOf(weekEnd);

						List<PhieuNhapDTO> phieuNhapList = phieuNhapBus.searchCompleteByDate(sqlWeekStart, sqlWeekEnd);
						for (PhieuNhapDTO pn : phieuNhapList) {
							List<Lo_NguyenLieuDTO> lo_NLList = lo_NguyenLieuBus.getAllByIdPN(pn.getIdPN());
							for (Lo_NguyenLieuDTO lo : lo_NLList) {
								if (lo.getIdNL() == nl.getIdNL()) {
									tongNhapKhoNLTuan[i] += (long) (lo.getSoluongnhap() * lo.getDongia());
									tongNhapKhoAllNLTuan[i] += tongNhapKhoNLTuan[i];
								}
							}
						}
						tongNhapKhoNLThang += tongNhapKhoNLTuan[i];
					}
					Object[] row = new Object[thongKeJTableHeaders.size()];
					row[0] = nl.getTenNL();
					for (int i = 0; i < weekRanges.size(); i++) {
						row[i + 1] = df.format(tongNhapKhoNLTuan[i]);
					}
					row[row.length - 1] = df.format(tongNhapKhoNLThang);
					thongKeJTableModel.addRow(row);
				}

				rowTong = new Object[thongKeJTableHeaders.size()];
				rowTong[0] = "Tổng";
				for (int i = 0; i < weekRanges.size(); i++) {
					rowTong[i + 1] = df.format(tongNhapKhoAllNLTuan[i]);
					tongNhapKhoThang += tongNhapKhoAllNLTuan[i];
				}
				rowTong[rowTong.length - 1] = df.format(tongNhapKhoThang);
				thongKeJTableModel.addRow(rowTong);
				break;
		}
	}

	private void loadSanPhamTable() {
		lblStatics.setText("Thống kê sản phẩm bán ra");
		ComboItem selectedFilterOptionItem = (ComboItem) comboFilterOption.getSelectedItem();
		int filterOption = selectedFilterOptionItem.getKey();
		ComboItem selectedYearItem = (ComboItem) comboYear.getSelectedItem();
		int year = selectedYearItem.getKey();

		if (year == 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn năm hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<SanPhamDTO> sanPhamList = sanPhamBus.getAll();

		switch (filterOption) {
			case 0:
				setupThongKeByYearTableHeaders(year);
				setupTableScrollPane();

				long[] tongBanAllSPThang = new long[12];
				long tongBanNam = 0;

				for (SanPhamDTO sp : sanPhamList) {
					long[] tongBanSPThang = new long[12];
					long tongBanSPNam = 0;

					for (int i = 0; i < 12; i++) {
						LocalDate startOfMonth = LocalDate.of(year, i + 1, 1);
						LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

						List<HoaDonDTO> hoaDonList = hoaDonBus.searchByDate(Date.valueOf(startOfMonth), Date.valueOf(endOfMonth));
						for (HoaDonDTO hd : hoaDonList) {
							List<CT_HoaDonDTO> ctHoaDonList = ct_HoaDonBus.getAllByIdHD(hd.getIdHD());
							for (CT_HoaDonDTO ct : ctHoaDonList) {
								if (ct.getIdSP() == sp.getIdSP()) {
									tongBanSPThang[i] += ct.getSoluong();
									tongBanAllSPThang[i] += tongBanSPThang[i];
								}
							}
						}
						tongBanSPNam += tongBanSPThang[i];
					}
					Object[] row = new Object[thongKeJTableHeaders.size()];
					row[0] = sp.getTenSP();
					for (int i = 0; i < 12; i++) {
						row[i + 1] = tongBanSPThang[i];
					}
					row[row.length - 1] = tongBanSPNam;
					thongKeJTableModel.addRow(row);
				}

				Object[] rowTong = new Object[thongKeJTableHeaders.size()];
				rowTong[0] = "Tổng";
				for (int i = 0; i < 12; i++) {
					rowTong[i + 1] = tongBanAllSPThang[i];
					tongBanNam += tongBanAllSPThang[i];
				}
				rowTong[rowTong.length - 1] = tongBanNam;
				thongKeJTableModel.addRow(rowTong);
				break;
			case 1:
				ComboItem selectedMonthItem = (ComboItem) comboMonth.getSelectedItem();
				int month = selectedMonthItem.getKey();
				if (month == 0) {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
				setupThongKeByMonthTableHeaders(year, month);
				setupTableScrollPane();

				LocalDate startOfMonth = LocalDate.of(year, month, 1);
				LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
				List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);

				long[] tongBanAllSPTuan = new long[weekRanges.size()];
				long tongBanThang = 0;

				for (SanPhamDTO sp : sanPhamList) {
					long[] tongBanSPTuan = new long[5];
					long tongBanSPThang = 0;

					for (int i = 0; i < weekRanges.size(); i++) {
						LocalDate weekStart = weekRanges.get(i)[0];
						LocalDate weekEnd = weekRanges.get(i)[1];

						java.sql.Date sqlWeekStart = java.sql.Date.valueOf(weekStart);
						java.sql.Date sqlWeekEnd = java.sql.Date.valueOf(weekEnd);

						List<HoaDonDTO> hoaDonList = hoaDonBus.searchByDate(sqlWeekStart, sqlWeekEnd);
						for (HoaDonDTO hd : hoaDonList) {
							List<CT_HoaDonDTO> ctHoaDonList = ct_HoaDonBus.getAllByIdHD(hd.getIdHD());
							for (CT_HoaDonDTO ct : ctHoaDonList) {
								if (ct.getIdSP() == sp.getIdSP()) {
									tongBanSPTuan[i] += ct.getSoluong();
									tongBanAllSPTuan[i] += tongBanSPTuan[i];
								}
							}
						}
						tongBanSPThang += tongBanSPTuan[i];
					}
					Object[] row = new Object[thongKeJTableHeaders.size()];
					row[0] = sp.getTenSP();
					for (int i = 0; i < weekRanges.size(); i++) {
						row[i + 1] = tongBanSPTuan[i];
					}
					row[row.length - 1] = tongBanSPThang;
					thongKeJTableModel.addRow(row);
				}

				rowTong = new Object[thongKeJTableHeaders.size()];
				rowTong[0] = "Tổng";
				for (int i = 0; i < weekRanges.size(); i++) {
					rowTong[i + 1] = tongBanAllSPTuan[i];
					tongBanThang += tongBanAllSPTuan[i];
				}
				rowTong[rowTong.length - 1] = tongBanThang;
				thongKeJTableModel.addRow(rowTong);
				break;
		}
	}
	// ===END: Các hàm tiện ích===
}