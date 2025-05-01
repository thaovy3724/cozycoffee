package GUI;

import BUS.HoaDonBUS;
import BUS.SanPhamBUS;
import BUS.TaiKhoanBUS;
import DTO.*;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Date;
import java.util.*;
import java.util.List;

public class HoaDonPanel extends JPanel {
	private HoaDonBUS hoaDonBUS = new HoaDonBUS();
	private TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
	private SanPhamBUS sanPhamBUS = new SanPhamBUS();
	private JTable hoaDonTable;
	private JTable ctHoaDonTable;
	private JDateChooser dateChooserStart, dateChooserEnd;
	private JTextField txtMinTongTien, txtMaxTongTien;
	private JComboBox<ComboItem> comboIdNV;
	private JButton btnSearch;
	private JLabel lblHoaDonInfo; // Nhãn hiển thị thông tin hóa đơn được chọn

	private String[] hoaDonJTableColumns = {"ID Hóa đơn", "Ngày tạo", "idNV", "Tổng tiền"};
	private String[] ctHoaDonJTableColumns = {"idSP", "Tên sản phẩm", "Số lượng", "Giá lúc đặt", "Thành tiền"};
	private Map<Integer, String> sanPhamMap; // Map để lưu idSP -> tenSP

	public HoaDonPanel() {
		ImageIcon searchIcon = getScaledImage(20, 20, "/ASSET/Images/searchIcon.png");
		
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 887, 508);
		setLayout(null);

//		// Tạo map nhân viên
//		nhanVienMap = new HashMap<>();
//		loadNhanVienMap();

		// Tạo map sản phẩm
		sanPhamMap = new HashMap<>();
		loadSanPhamMap();

		// Phần tìm kiếm
		JLabel lblSearch = new JLabel("Tìm kiếm hóa đơn");
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSearch.setBounds(20, 10, 150, 25);
		add(lblSearch);

		// Label startDate
		JLabel lblStartDate = new JLabel("Từ ngày:");
		lblStartDate.setBounds(20, 40, 70, 25);
		add(lblStartDate);
		dateChooserStart = new JDateChooser();
		dateChooserStart.setDateFormatString("yyyy-MM-dd");
		dateChooserStart.setBounds(100, 40, 120, 25);
		((JTextField) dateChooserStart.getDateEditor().getUiComponent()).setEditable(false); // Tắt editor
		add(dateChooserStart);

		// Label endDate
		JLabel lblEndDate = new JLabel("Đến ngày:");
		lblEndDate.setBounds(230, 40, 70, 25);
		add(lblEndDate);
		dateChooserEnd = new JDateChooser();
		dateChooserEnd.setDateFormatString("yyyy-MM-dd");
		dateChooserEnd.setBounds(310, 40, 120, 25);
		((JTextField) dateChooserEnd.getDateEditor().getUiComponent()).setEditable(false); // Tắt editor
		add(dateChooserEnd);

		// Label tongTien
		JLabel lblTongTien = new JLabel("Tổng tiền:");
		lblTongTien.setBounds(20, 75, 70, 25);
		add(lblTongTien);
		// minTongTien field
		txtMinTongTien = new JTextField("0");
		txtMinTongTien.setBounds(100, 75, 100, 25);
		add(txtMinTongTien);
		//Thêm sự kiện tự xóa nội dung khi focus vào input
		txtMinTongTien.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtMinTongTien.setText(""); // Xóa nội dung khi focus
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtMinTongTien.getText().trim().isEmpty()) { // Nếu field rỗng
					txtMinTongTien.setText("0"); // Đặt lại giá trị 0
				}
			}
		});
		// Label "-"
		JLabel lblTo = new JLabel("-");
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setBounds(199, 75, 25, 25);
		add(lblTo);
		// maxTongTien field
		txtMaxTongTien = new JTextField("0");
		txtMaxTongTien.setBounds(225, 75, 100, 25);
		add(txtMaxTongTien);
		//Thêm sự kiện tự xóa nội dung khi focus vào input
		txtMaxTongTien.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtMaxTongTien.setText(""); // Xóa nội dung khi focus
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtMaxTongTien.getText().trim().isEmpty()) { // Nếu field rỗng
					txtMaxTongTien.setText("0"); // Đặt lại giá trị 0
				}
			}

		});

		// Label idNV
		JLabel lblIdNV = new JLabel("Nhân viên:");
		lblIdNV.setBounds(470, 40, 70, 25);
		add(lblIdNV);
		// ComboBox idNV - tenNV
		comboIdNV = new JComboBox<>();
		comboIdNV.setBounds(550, 40, 175, 25); // Tăng chiều rộng để hiển thị tên
		setupComboIdNV();
		add(comboIdNV);

		// Nút tìm kiếm
		btnSearch = new JButton();
		btnSearch.setBounds(766, 39, 46, 27);
		btnSearch.setIcon(searchIcon);
		add(btnSearch);

		// Bảng danh sách hóa đơn
		JScrollPane hoaDonScrollPane = new JScrollPane();
		hoaDonScrollPane.setBounds(20, 110, 847, 120); // Giảm chiều cao để nhường chỗ cho thông tin hóa đơn
		add(hoaDonScrollPane);

		DefaultTableModel hoaDonModel = new DefaultTableModel(
				new Object[][] {
				},
				hoaDonJTableColumns
		);
		hoaDonTable = new JTable(hoaDonModel);
		hoaDonTable.setEnabled(true); // Cho phép tương tác (chọn dòng), nhưng không cho chỉnh sửa
		hoaDonTable.setDefaultEditor(Object.class, null); // Xóa editor mặc định để ngăn chỉnh sửa
		hoaDonScrollPane.setViewportView(hoaDonTable);

		// Phần thông tin hóa đơn được chọn
		lblHoaDonInfo = new JLabel("Chọn một hóa đơn để hiển thị thông tin hóa đơn");
		lblHoaDonInfo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblHoaDonInfo.setBounds(20, 240, 847, 25);
		add(lblHoaDonInfo);

		// Bảng chi tiết hóa đơn
		JLabel lblCTHoaDon = new JLabel("Chi tiết hóa đơn");
		lblCTHoaDon.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCTHoaDon.setBounds(20, 270, 150, 25);
		add(lblCTHoaDon);

		JScrollPane ctHoaDonScrollPane = new JScrollPane();
		ctHoaDonScrollPane.setBounds(20, 300, 847, 188); // Điều chỉnh vị trí và kích thước
		add(ctHoaDonScrollPane);

		DefaultTableModel ctHoaDonModel = new DefaultTableModel(
				new Object[][]{
				},
				ctHoaDonJTableColumns
		);
		ctHoaDonTable = new JTable(ctHoaDonModel);
		ctHoaDonTable.setEnabled(true); // Cho phép tương tác (chọn dòng), nhưng không cho chỉnh sửa
		ctHoaDonTable.setDefaultEditor(Object.class, null); // Xóa editor mặc định để ngăn chỉnh sửa
		ctHoaDonScrollPane.setViewportView(ctHoaDonTable);

		// Load danh sách hóa đơn ban đầu
		loadHoaDonList(hoaDonBUS.getAllHoaDon());

		// Gán sự kiện
		setupEventListeners();
	}

	/*
	 * Dưới đây bao gồm:
	 * 		+ Các hàm setUp cho GUI (truyền dữ liệu vào các element sau khi GUI được load xong)
	 * 		+ Các hàm xử lý sự kiện (eventHandler)
	 * 		+ Hàm validateInput
	 * 		+ Các hàm tiện ích
	 * */

	//													===Các hàm setUp cho GUI===

	// Set up các option trong comboBox idNV (Phần tìm kiếm)
	private void setupComboIdNV() {
		comboIdNV.addItem(new ComboItem(0, "--- Tất cả ---"));
		List<TaiKhoanDTO> nhanVienList = taiKhoanBUS.getAllTaiKhoanByIDNQ(2);
		for (TaiKhoanDTO tk : nhanVienList) {
			comboIdNV.addItem(new ComboItem(tk.getIdTK(), tk.getTenTK().toString()));
		}
	}

	// Set up các eventListener
	private void setupEventListeners() {

		//Set up sự kiện: khi nhấn một dòng trong bảng
		hoaDonTable.getSelectionModel().addListSelectionListener(
				e -> {
					if (!e.getValueIsAdjusting() && hoaDonTable.getSelectedRow() != -1) {
						int selectedRow = hoaDonTable.getSelectedRow();
						int idHD = (int) hoaDonTable.getValueAt(selectedRow, 0);
						loadCTHoaDon(idHD);
					}
				}
		);

		//Nút tìm kiếm
		btnSearch.addActionListener(
				e -> {
					if (validateInput()) {
						searchHoaDon();
					}
				}
		);
	}
	//												===END: Các hàm setUp cho GUI===

	//													===Các hàm xử lý sự kiện===

	// Load thông tin hóa đơn và danh sách chi tiết hóa đơn khi nhấn vào một hóa đơn
	private void loadCTHoaDon(int idHD) {
		// Load thông tin hóa đơn
		HoaDonDTO selectedHD = hoaDonBUS.findHoaDonById(idHD);
		List<CTHoaDonDTO> ctHoaDonList = hoaDonBUS.getAllCTHoaDonByIDHD(idHD);

		int tongTien = ctHoaDonList.stream() //Biến đổi list thành luồng dữ liệu => luồng dữ liệu sẽ gồm danh sách CTHoaDon chứa danh sách thông tin của nó
				.mapToInt(ctHoaDon -> ctHoaDon.getSoluong() * ctHoaDon.getGialucdat()) //Map luồng dữ liệu thành luồng tổng tiền của từng ctHoaDon
				.sum(); //Cộng tất cả giá trị thành phần trong luồng lại => tổng tiền của hóa đơn

		lblHoaDonInfo.setText(
				"Thông tin hóa đơn: ID hóa đơn: " + idHD + " | " +
				" Ngày tạo hóa đơn: " + selectedHD.getNgaytao() + " | " +
				" ID Nhân viên tạo hóa đơn: " + selectedHD.getIdTK() + " | " +
				" Tổng tiền: " + tongTien
		);

		// Load danh sách chi tiết hóa đơn
		loadCTHoaDonList(ctHoaDonList);
	}

	// Xử lý sự kiện nút tìm kiếm
	private void searchHoaDon() {
		Date startDate = dateChooserStart.getDate() != null ? new Date(dateChooserStart.getDate().getTime()) : null;
		Date endDate = dateChooserEnd.getDate() != null ? new Date(dateChooserEnd.getDate().getTime()) : null;
		int minTongTien = Integer.parseInt(txtMinTongTien.getText().trim());
		int maxTongTien = Integer.parseInt(txtMaxTongTien.getText().trim());
		ComboItem selectedNV = (ComboItem) comboIdNV.getSelectedItem();
		int idNV = selectedNV.getKey();

		List<HoaDonDTO> result = hoaDonBUS.searchHoaDon(startDate, endDate, minTongTien, maxTongTien, idNV);
		loadHoaDonList(result);
		lblHoaDonInfo.setText("Chọn một hóa đơn để hiển thị thông tin hóa đơn");
		ctHoaDonTable.setModel(new DefaultTableModel(ctHoaDonJTableColumns, 0));
	}
	//												===END: Các hàm xử lý sự kiện===

	//												===Hàm validateInput (tìm kiếm)===

	private boolean validateInput() {
		StringBuilder errorMessage = new StringBuilder();

		// Validate ngày
		Date startDate = dateChooserStart.getDate() != null ? new Date(dateChooserStart.getDate().getTime()) : null;
		Date endDate = dateChooserEnd.getDate() != null ? new Date(dateChooserEnd.getDate().getTime()) : null;
		if (startDate != null && endDate != null && startDate.after(endDate)) {
			errorMessage.append("Ngày bắt đầu không được lớn hơn ngày kết thúc.\n");
		}

		// Validate tổng tiền
		String minTongTienInput = txtMinTongTien.getText().trim();
		String maxTongTienInput = txtMaxTongTien.getText().trim();
		int minTongTien = 0;
		int maxTongTien = 0;

		try {
			minTongTien = Integer.parseInt(minTongTienInput);
			if (minTongTien < 0) {
				errorMessage.append("Tổng tiền tối thiểu không được nhỏ hơn 0.\n");
			}
		} catch (NumberFormatException e) {
			errorMessage.append("Tổng tiền tối thiểu phải là số nguyên.\n");
		}

		try {
			maxTongTien = Integer.parseInt(maxTongTienInput);
			if (maxTongTien < 0) {
				errorMessage.append("Tổng tiền tối đa không được nhỏ hơn 0.\n");
			}
		} catch (NumberFormatException e) {
			errorMessage.append("Tổng tiền tối đa phải là số nguyên.\n");
		}

		if (minTongTien > maxTongTien && maxTongTien > 0) {
			errorMessage.append("Tổng tiền tối thiểu không được lớn hơn tổng tiền tối đa.\n");
		}

		if (errorMessage.length() > 0) {
			showErrorMessage("Lỗi nhập liệu", errorMessage.toString());
			return false;
		}
		return true;
	}
	//											===END: Hàm validateInput (tìm kiếm)===

	//														===Các hàm tiện ích===

	//Căn chỉnh hình ảnh (hàm này nên được nằm trong class chung)
	private ImageIcon getScaledImage(int width, int height, String path) {
		// Load the original image
		ImageIcon originalIcon = new ImageIcon(DanhMucPanel.class.getResource(path));

		// Resize the image (e.g., to 50x50 pixels)
		Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		// Create a new ImageIcon with the scaled image
		return new ImageIcon(scaledImage);
	}

	private void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title,JOptionPane.ERROR_MESSAGE);
	}

	// Load map sản phẩm
	private void loadSanPhamMap() {
		List<SanPhamDTO> sanPhamList = sanPhamBUS.getAll();
		for(SanPhamDTO sp: sanPhamList) {
			this.sanPhamMap.put(sp.getIdSP(), sp.getTenSP());
		}
	}
	// ***

	// Hàm load danh sách hóa đơn
	private void loadHoaDonList(List<HoaDonDTO> hoaDonList) {
		DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
		model.setRowCount(0);

		for (HoaDonDTO hd : hoaDonList) {
			List<CTHoaDonDTO> ctList = hoaDonBUS.getAllCTHoaDonByIDHD(hd.getIdHD());
			int tongTien = ctList.stream()
					.mapToInt(ct -> ct.getSoluong() * ct.getGialucdat())
					.sum();

			Object[] row = {
					hd.getIdHD(),
					hd.getNgaytao(),
					hd.getIdTK(),
					tongTien
			};
			model.addRow(row);
		}
	}

	//Hàm load danh sách chi tiết hóa đơn
	private void loadCTHoaDonList(List<CTHoaDonDTO> ctHoaDonList) {
		DefaultTableModel model = (DefaultTableModel) ctHoaDonTable.getModel();
		model.setRowCount(0);

		for (CTHoaDonDTO ct : ctHoaDonList) {
			Object[] row = {
					ct.getIdSP(),
					sanPhamMap.get(ct.getIdSP()),
					ct.getSoluong(),
					ct.getGialucdat(),
					ct.getSoluong() * ct.getGialucdat()
			};
			model.addRow(row);
		}
	}

	//													===END: Các hàm tiện ích===
}