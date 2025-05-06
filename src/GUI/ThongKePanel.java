package GUI;

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

import BUS.*;
import DTO.CT_HoaDonDTO;
import DTO.ComboItem;
import DTO.HoaDonDTO;
import DTO.Lo_NguyenLieuDTO;
import DTO.NguyenLieuDTO;
import DTO.PhieuNhapDTO;
import DTO.SanPhamDTO;
import GUI.Dialog.ThongKeNhapKhoDialog;
import GUI.ThongKe.ThongKeLoiNhuanTheoNam;
import GUI.ThongKe.ThongKeLoiNhuanTheoThang;

public class ThongKePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final HoaDonBUS hoaDonBus = new HoaDonBUS();
	private final CT_HoaDonBUS ct_HoaDonBus = new CT_HoaDonBUS();
	private final PhieuNhapBUS phieuNhapBus = new PhieuNhapBUS();
	private final ThongKeBUS thongKeBUS = new ThongKeBUS();
	private final Lo_NguyenLieuBUS lo_NguyenLieuBus = new Lo_NguyenLieuBUS();
	private final NguyenLieuBUS nguyenLieuBus = new NguyenLieuBUS();
	private final SanPhamBUS sanPhamBus = new SanPhamBUS();
	private JTable tableThongKe;
	private JLabel lblStatics;
    private JLabel lblMonth;
	private JComboBox<ComboItem> comboYear;
    private JComboBox<ComboItem> comboMonth;
    private JComboBox<ComboItem> comboFilterOption;
	private JButton btnXemLoiNhuan;
    private JButton btnXemNhapKho;
    private JButton btnXemSanPham;
	private JPanel bodyPanel;

	private List<String> thongKeJTableHeaders;
	private DefaultTableModel thongKeJTableModel;
	private JScrollPane tableThongKeScrollPane;
	private Map<String, Integer> tenNLToIdMap = new HashMap<>(); // Map để lưu tenNL -> idNL
	
	private int clickedRow = -1;
	private int clickedColumn = -1;

	// HUONGNGUYEN 4/5
	private JScrollPane chartPanel;

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
		bodyPanel = new JPanel();
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

		tableThongKe = new JTable();

		// Initialize table scroll pane (will be populated dynamically)
		tableThongKeScrollPane = new JScrollPane();
		tableThongKeScrollPane.setViewportView(tableThongKe);
		tableThongKeScrollPane.getViewport().setBackground(new Color(255, 240, 220));
		tablePanel.add(tableThongKeScrollPane, BorderLayout.CENTER);
		int rowHeight = tableThongKe.getRowHeight();
		int rowCount = tableThongKe.getRowCount();
		int headerHeight = tableThongKe.getTableHeader().getPreferredSize().height;

		// Max number of visible rows before scrolling is needed
		int maxVisibleRows = 10;

		// Use min(rowCount, maxVisibleRows) to determine visible height
		int visibleRows = Math.min(rowCount, maxVisibleRows);
		int tableHeight = visibleRows * rowHeight + headerHeight + 5; // add padding if needed

		// Set preferred size of scroll pane to fit table height
		tableThongKeScrollPane.setPreferredSize(new Dimension(
			tableThongKe.getPreferredSize().width, tableHeight
		));

		// Ẩn lựa chọn tháng trước
		lblMonth.setVisible(false);
		comboMonth.setVisible(false);

		// Setup comboBox
		setupComboFilterOption();
		setupComboYear();

		// Setup tenNL - idNL map (để hiển thị dialog thống kê nhập kho)
			for (NguyenLieuDTO nl : nguyenLieuBus.getAll()) {
				tenNLToIdMap.put(nl.getTenNL(), nl.getIdNL());
			}

		// Gán sự kiện
		setupEventListeners();
		setupTableCellRenderer();
		setupTableMouseListener();
	}

	private void chartInit() {
		// HUONGNGUYEN 4/5
		chartPanel = new JScrollPane();
		chartPanel.getViewport().setBackground(new Color(255, 240, 220));
		bodyPanel.add(chartPanel);
	}

	// ===Các hàm setUp cho GUI===
	private void setupComboFilterOption() {
		//comboFilterOption.removeAllItems();
		comboFilterOption.addItem(new ComboItem(0, "--- Năm ---"));
		comboFilterOption.addItem(new ComboItem(1, "--- Tháng ---"));
	}

	private void setupComboYear() {
		//comboYear.removeAllItems();
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
					ComboItem option = (ComboItem) comboFilterOption.getSelectedItem();
					if (option.getValue().equals("--- Tháng ---")) {
						lblMonth.setVisible(true);
						setupComboMonth();
						comboMonth.setVisible(true);
					} else {
						lblMonth.setVisible(false);
						comboMonth.setVisible(false);
					}
				}
		);

		comboYear.addActionListener(e -> {
			ComboItem selectedFilterOption = (ComboItem) comboFilterOption.getSelectedItem();
			ComboItem selectedYear = (ComboItem) comboYear.getSelectedItem();
			if (selectedFilterOption.getValue().equals("--- Tháng ---") && selectedYear.getKey() > 0) {
				setupComboMonth();
			}
		});

		btnXemLoiNhuan.addActionListener(
				e -> {
					if (!isError()) {
						if(chartPanel != null){
							bodyPanel.remove(chartPanel);
							bodyPanel.revalidate();
							bodyPanel.repaint();
						}
						chartInit();
						loadThongKe();
						loadProfitTable();
					}
				}
		);

		btnXemNhapKho.addActionListener(
				e -> {
					if (!isError()) {
						if(chartPanel != null){
							bodyPanel.remove(chartPanel);
							bodyPanel.revalidate();
							bodyPanel.repaint();
						}
						loadNhapKhoTable();
					}
				}
		);

		btnXemSanPham.addActionListener(
				e -> {
					if (!isError()) {
						if(chartPanel != null){
							bodyPanel.remove(chartPanel);
							bodyPanel.revalidate();
							bodyPanel.repaint();
						}
						loadSanPhamTable();
					}
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

	private void setupStatisticByMonth(int month, int year) {
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
		thongKeJTableModel = new DefaultTableModel(thongKeJTableHeaders.toArray(),0);
		tableThongKe.setModel(thongKeJTableModel);
		tableThongKe.setDefaultEditor(Object.class, null);
	}

	private void setupStatisticByYear(int year) {
		thongKeJTableHeaders = new ArrayList<>();
		thongKeJTableHeaders.add("");
		for (int i = 1; i <= 12; i++) {
			thongKeJTableHeaders.add(i + "/" + year);
		}
		thongKeJTableHeaders.add("Tổng cộng");
		thongKeJTableHeaders.toArray(new String[0]);
		thongKeJTableModel = new DefaultTableModel(thongKeJTableHeaders.toArray(), 0);
		tableThongKe.setModel(thongKeJTableModel);
		tableThongKe.setDefaultEditor(Object.class, null);
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
		if (tableThongKe != null) {
			tableThongKe.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		}
	}

	private void setupTableMouseListener() {
		 if (tableThongKe != null) {
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
					if (row >= 0 && column > 0) {
						cell = tableThongKe.getValueAt(row, column);
					}
					if (cell != null) {
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
							if ( //ThongKeNhapKhoDialog được mở nếu click 2 lần lên ô
									row >= 0 && // Không phải hàng header
									row < tableThongKe.getRowCount() - 1 && // Không phải dòng tổng dưới cùng
									column > 0 && // Không phải cột chứa tên NL
									cell != null && cellValue > 0 // Ô phải có giá trị và lớn hơn 0
							) {
								ComboItem selectedFilterOptionItem = (ComboItem) comboFilterOption.getSelectedItem();
								int filterOption = selectedFilterOptionItem.getKey();
								ComboItem selectedYearItem = (ComboItem) comboYear.getSelectedItem();
								int year = selectedYearItem.getKey();

								// Lấy tên NL từ ô được chọn và lấy idNL dựa trên map được khởi tạo trong constructor
								String tenNguyenLieu = (String) tableThongKe.getValueAt(row, 0);
								Integer idNL = tenNLToIdMap.get(tenNguyenLieu);
								if (idNL == null) {
									return;
								}

								LocalDate start = null, end = null;

								if (filterOption == 0) { // Nếu duyệt theo năm

									if (column == tableThongKe.getColumnCount() - 1) { // Và là cột tổng
										// Thời gian duyệt chi tiết nhập kho là cả năm
										start = LocalDate.of(year, 1, 1);
										end = LocalDate.of(year, 12, 31);

									} else { // Nếu không
										// Thời gian duyệt là tháng được chọn
										int month = column;
										start = LocalDate.of(year, month, 1);
										end = start.withDayOfMonth(start.lengthOfMonth());
									}

								} else if (filterOption == 1) { // Duyệt theo tháng
									ComboItem selectedMonthItem = (ComboItem) comboMonth.getSelectedItem();
									int month = selectedMonthItem.getKey();
									// Gán trước thời gian là cả tháng
									start = LocalDate.of(year, month, 1);
									end = start.withDayOfMonth(start.lengthOfMonth());
									List<LocalDate[]> weekRanges = getWeekRanges(start, end);

									if (column != tableThongKe.getColumnCount() - 1) {
										// Nếu không phải cột tổng, lấy khoảng thời gian dựa trên cột của ô được chọn
										LocalDate[] range = weekRanges.get(column - 1);
										start = range[0];
										end = range[1];
									}
								}

								if (start != null && end != null) { // Dựa trên thời gian mà hiển thị dialog tương ứng
									// Load danh sách CT lô nguyên liệu dựa trên thời gian
									List<Lo_NguyenLieuDTO> danhSachLo = thongKeBUS.getChoseIngredientBatchList(idNL, start, end);
									new ThongKeNhapKhoDialog(Date.valueOf(start), Date.valueOf(end), idNL, danhSachLo);
								}
							}
						}
					}
				}
			});
		}
	}

//	private void showNhapKhoDetailDialog(int idNL, LocalDate start, LocalDate end) {
//		List<Lo_NguyenLieuDTO> danhSachLo = thongKeBUS.getChoseIngredientBatchList(idNL, start, end);
//
//		new ThongKeNhapKhoDialog(Date.valueOf(start), Date.valueOf(end), idNL, danhSachLo);
//	}

	private void loadProfitTable() {
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
			case 0: // Năm
				setupStatisticByYear(year);
				List<List<Long>> statisticByYear = thongKeBUS.getProfitStatisticByYear(year);

				DecimalFormat df = new DecimalFormat("#,### VNĐ");

				// Hàng tổng chi
				Object[] rowTongChi = new Object[thongKeJTableHeaders.size()];
				long tongChi = 0;
				rowTongChi[0] = "Tổng chi";
				for (int i = 0; i < 12; i++) {
					rowTongChi[i + 1] = df.format(statisticByYear.get(0).get(i));
					tongChi += statisticByYear.get(0).get(i);
				}
				rowTongChi[rowTongChi.length - 1] = df.format(tongChi);
				thongKeJTableModel.addRow(rowTongChi);

				// Hàng tổng doanh thu
				Object[] rowDoanhThu = new Object[thongKeJTableHeaders.size()];
				long tongDoanhThu = 0;
				rowDoanhThu[0] = "Doanh Thu";
				for (int i = 0; i < 12; i++) {
					rowDoanhThu[i + 1] = df.format(statisticByYear.get(1).get(i));
					tongDoanhThu += statisticByYear.get(1).get(i);
				}
				rowDoanhThu[rowDoanhThu.length - 1] = df.format(tongDoanhThu);
				thongKeJTableModel.addRow(rowDoanhThu);

				// Hàng lợi nhuận
				Object[] rowLoiNhuan = new Object[thongKeJTableHeaders.size()];
				long tongLoiNhuan = 0;
				rowLoiNhuan[0] = "Lợi nhuận";
				for (int i = 0; i < 12; i++) {
					rowLoiNhuan[i + 1] = df.format(statisticByYear.get(2).get(i));
					tongLoiNhuan += statisticByYear.get(2).get(i);
				}
				rowLoiNhuan[rowLoiNhuan.length - 1] = df.format(tongLoiNhuan);
				thongKeJTableModel.addRow(rowLoiNhuan);

				break;
			case 1:
				ComboItem selectedMonthItem = (ComboItem) comboMonth.getSelectedItem();
				int month = selectedMonthItem.getKey();
				setupStatisticByMonth(month, year);

				List<List<Long>> statisticByMonth = thongKeBUS.getProfitStatisticByMonth(month, year);

				df = new DecimalFormat("#,### VNĐ");

				// Hàng chi
				rowTongChi = new Object[thongKeJTableHeaders.size()];
				tongChi = 0;
				rowTongChi[0] = "Tổng chi";
				for (int i = 0; i < statisticByMonth.get(0).size(); i++) {
					rowTongChi[i + 1] = df.format(statisticByMonth.get(0).get(i));
					tongChi += statisticByMonth.get(0).get(i);
				}
				rowTongChi[rowTongChi.length - 1] = df.format(tongChi);
				thongKeJTableModel.addRow(rowTongChi);

				// Hàng doanh thu
				rowDoanhThu = new Object[thongKeJTableHeaders.size()];
				tongDoanhThu = 0;
				rowDoanhThu[0] = "Doanh Thu";
				for (int i = 0; i < statisticByMonth.get(1).size(); i++) {
					rowDoanhThu[i + 1] = df.format(statisticByMonth.get(1).get(i));
					tongDoanhThu += statisticByMonth.get(1).get(i);
				}
				rowDoanhThu[rowDoanhThu.length - 1] = df.format(tongDoanhThu);
				thongKeJTableModel.addRow(rowDoanhThu);

				// Hàng lợi nhuận
				rowLoiNhuan = new Object[thongKeJTableHeaders.size()];
				tongLoiNhuan = 0;
				rowLoiNhuan[0] = "Lợi nhuận";
				for (int i = 0; i < statisticByMonth.get(2).size(); i++) {
					rowLoiNhuan[i + 1] = df.format(statisticByMonth.get(2).get(i));
					tongLoiNhuan += statisticByMonth.get(2).get(i);
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
				setupStatisticByYear(year);

				long[] tongNhapKhoAllNLThang = new long[12];
				int tongNhapKhoNam = 0;
				List<Map<String, List<Long>>> IngredientsAndTongTienMapList = thongKeBUS.getIngredientsCostStatisticByYear(year);

				for (Map<String, List<Long>> ingredientMap : IngredientsAndTongTienMapList) { // Với mỗi Map có trong List Map
					for (Map.Entry<String, List<Long>> entry : ingredientMap.entrySet()) { // Với mỗi map, duyệt từng entry (cặp key - value)
						long tongNhapKhoNLNam = 0;
						Object[] row = new Object[thongKeJTableHeaders.size()];
						row[0] = entry.getKey();
						for (int i = 0; i < 12; i++) {
							row[i + 1] = entry.getValue().get(i);
							tongNhapKhoAllNLThang[i] += entry.getValue().get(i);
							tongNhapKhoNLNam += entry.getValue().get(i);
						}
						row[row.length - 1] = df.format(tongNhapKhoNLNam);
						thongKeJTableModel.addRow(row);

					}
				}

				//Hàng tổng
				Object[] rowTong = new Object[thongKeJTableHeaders.size()];
				rowTong[0] = "Tổng";
				for (int i = 0; i < 12; i++) {
					rowTong[i + 1] = df.format(tongNhapKhoAllNLThang[i]);
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
				setupStatisticByMonth(month, year);

				LocalDate startOfMonth = LocalDate.of(year, month, 1);
				LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
				List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);

				long[] tongNhapKhoAllNLTuan = new long[weekRanges.size()];
				long tongNhapKhoThang = 0;

				IngredientsAndTongTienMapList = thongKeBUS.getIngredientsCostStatisticByMonth(month, year);

				for (Map<String, List<Long>> ingredientMap : IngredientsAndTongTienMapList) { // Với mỗi Map có trong List Map
					for (Map.Entry<String, List<Long>> entry : ingredientMap.entrySet()) { // Với mỗi map, duyệt từng entry (cặp key - value)
						long tongNhapKhoNLThang = 0;
						Object[] row = new Object[thongKeJTableHeaders.size()];
						row[0] = entry.getKey();
						for (int i = 0; i < weekRanges.size(); i++) {
							row[i + 1] = entry.getValue().get(i);
							tongNhapKhoAllNLTuan[i] += entry.getValue().get(i);
							tongNhapKhoNLThang += entry.getValue().get(i);
						}
						row[row.length - 1] = df.format(tongNhapKhoNLThang);
						thongKeJTableModel.addRow(row);
					}
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
				setupStatisticByYear(year);

				long[] tongBanAllSPThang = new long[12];
				long tongBanNam = 0;

				List<Map<String, List<Long>>> productsAndQuantityMapList = thongKeBUS.getProductsQuantityStatisticByYear(year);

				for (Map<String, List<Long>> productMap : productsAndQuantityMapList) { // Với mỗi Map có trong List Map
					for (Map.Entry<String, List<Long>> entry : productMap.entrySet()) { // Với mỗi map, duyệt từng entry (cặp key - value)
						long tongNhapKhoNLNam = 0;
						Object[] row = new Object[thongKeJTableHeaders.size()];
						row[0] = entry.getKey();
						for (int i = 0; i < 12; i++) {
							row[i + 1] = entry.getValue().get(i);
							tongBanAllSPThang[i] += entry.getValue().get(i);
							tongNhapKhoNLNam += entry.getValue().get(i);
						}
						row[row.length - 1] = tongNhapKhoNLNam;
						thongKeJTableModel.addRow(row);

					}
				}

				//Hàng tổng
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
				setupStatisticByMonth(month, year);

				LocalDate startOfMonth = LocalDate.of(year, month, 1);
				LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
				List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);

				long[] tongBanAllSPTuan = new long[weekRanges.size()];
				long tongBanThang = 0;

				productsAndQuantityMapList = thongKeBUS.getProductsQuantityStatisticByMonth(month, year);

				for (Map<String, List<Long>> productMap : productsAndQuantityMapList) { // Với mỗi Map có trong List Map
					for (Map.Entry<String, List<Long>> entry : productMap.entrySet()) { // Với mỗi map, duyệt từng entry (cặp key - value)
						long tongBanSPThang = 0;
						Object[] row = new Object[thongKeJTableHeaders.size()];
						row[0] = entry.getKey();
						for (int i = 0; i < weekRanges.size(); i++) {
							row[i + 1] = entry.getValue().get(i);
							tongBanAllSPTuan[i] += entry.getValue().get(i);
							tongBanSPThang += entry.getValue().get(i);
						}
						row[row.length - 1] = tongBanSPThang;
						thongKeJTableModel.addRow(row);
					}
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

	// HUONGNGUYEN 4/5
	public void loadThongKe() {
		ComboItem filterOption =
				(ComboItem) comboFilterOption.getSelectedItem();
		ComboItem yearItem = (ComboItem) comboYear.getSelectedItem();
		ComboItem monthItem = (ComboItem) comboMonth.getSelectedItem();

		int year = yearItem.getKey();
		int month =
				filterOption.getKey() == 1 && monthItem != null && monthItem.getKey() > 0 ? monthItem.getKey() : -1;
		if (filterOption.getKey() == 0) {
			// Chọn xem theo năm
			ThongKeLoiNhuanTheoNam chartByYear = new ThongKeLoiNhuanTheoNam(year);
			chartPanel.setViewportView(chartByYear);
		} else {
			ThongKeLoiNhuanTheoThang chartByMonth = new ThongKeLoiNhuanTheoThang(month, year);
			chartPanel.setViewportView(chartByMonth);
		}
	}

	private boolean isError() {
		boolean isError = false;

		ComboItem filterOption =
				(ComboItem) comboFilterOption.getSelectedItem();
		ComboItem yearItem = (ComboItem) comboYear.getSelectedItem();
		ComboItem monthItem = (ComboItem) comboMonth.getSelectedItem();

		if (yearItem.getKey() == 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn năm!", "LỖI", JOptionPane.ERROR_MESSAGE);
			isError = true;
		}

		if (filterOption.getKey() == 1 &&  monthItem.getKey() == 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng!",
					"LỖI", JOptionPane.ERROR_MESSAGE);
			isError = true;
		}

		return isError;
	}
}