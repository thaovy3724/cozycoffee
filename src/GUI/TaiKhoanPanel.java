package GUI;
import BUS.TaiKhoanBUS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import DTO.ComboItem;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;

public class TaiKhoanPanel extends JPanel {
	TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();

	private static final long serialVersionUID = 1L;
	String iniTextSearchPlaceHolder = "Nhập tên tài khoản, email, điện thoại";
	private String[] taiKhoanJTableColumns = {"ID", "Tên TK", "Họ tên", "Email", "Điện thoại", "Trạng thái", "Nhóm quyền"};
	//Các element sẽ được lấy dữ liệu hoặc gắn sự kiện
	private JTextField textSearch;
	private JTextField textTenTK;
	private JComboBox optionNhomQuyen;
	private JComboBox optionTrangThai;
	private JTable taiKhoanTable;
	private JPasswordField passwordField;
	private JTextField textHoTen;
	private JTextField textEmail;
	private JTextField textDienThoai;
	private JTextField textIdTK;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnSearch;

	/**
	 * Create the panel.
	 */
	// Truyền tham chiếu AdminFrame vào TaiKhoanPanel
	public TaiKhoanPanel() {
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 887, 508);
		setLayout(null);
		ImageIcon searchIcon = getScaledImage(20, 20, "/ASSET/Images/searchIcon.png");

		//Label tên tài khoản
		JLabel lblTenTK = new JLabel("Tên tài khoản");
		lblTenTK.setHorizontalAlignment(SwingConstants.CENTER);
		lblTenTK.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTenTK.setBounds(52, 29, 126, 21);
		add(lblTenTK);

		//Input tên tài khoản
		textTenTK = new JTextField();
		textTenTK.setHorizontalAlignment(SwingConstants.LEFT);
		textTenTK.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textTenTK.setBounds(188, 29, 520, 21);
		add(textTenTK);
		textTenTK.setColumns(10);

		//Label mật khẩu
		JLabel lblMatKhau = new JLabel("Mật khẩu");
		lblMatKhau.setHorizontalAlignment(SwingConstants.CENTER);
		lblMatKhau.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMatKhau.setBounds(52, 58, 126, 21);
		add(lblMatKhau);

		//Input mật khẩu
		passwordField = new JPasswordField();
		passwordField.setBounds(188, 60, 520, 19);
		add(passwordField);

		//Label họ tên
		JLabel lblHoTen = new JLabel("Họ tên");
		lblHoTen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoTen.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblHoTen.setBounds(52, 89, 126, 21);
		add(lblHoTen);

		//Input họ tên
		textHoTen = new JTextField();
		textHoTen.setHorizontalAlignment(SwingConstants.LEFT);
		textHoTen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textHoTen.setColumns(10);
		textHoTen.setBounds(188, 89, 520, 21);
		add(textHoTen);

		//Label Email
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmail.setBounds(52, 120, 126, 21);
		add(lblEmail);

		//Input Email
		textEmail = new JTextField();
		textEmail.setHorizontalAlignment(SwingConstants.LEFT);
		textEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textEmail.setColumns(10);
		textEmail.setBounds(188, 120, 520, 21);
		add(textEmail);

		//Label điện thoại
		JLabel lblDienThoai = new JLabel("Điện thoại");
		lblDienThoai.setHorizontalAlignment(SwingConstants.CENTER);
		lblDienThoai.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDienThoai.setBounds(52, 151, 126, 21);
		add(lblDienThoai);

		//Input điện thoại
		textDienThoai = new JTextField();
		textDienThoai.setHorizontalAlignment(SwingConstants.LEFT);
		textDienThoai.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textDienThoai.setColumns(10);
		textDienThoai.setBounds(188, 151, 520, 21);
		add(textDienThoai);

		//Label nhóm quyền
		JLabel lblNhomQuyen = new JLabel("Nhóm quyền");
		lblNhomQuyen.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhomQuyen.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNhomQuyen.setBounds(52, 182, 126, 21);
		add(lblNhomQuyen);

		//ComboBox nhóm quyền
		optionNhomQuyen = new JComboBox();
		optionNhomQuyen.setToolTipText("");
		optionNhomQuyen.setBounds(188, 183, 262, 21);
		add(optionNhomQuyen);

		//Label trạng thái
		JLabel lblTrangThai = new JLabel("Trạng thái");
		lblTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrangThai.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTrangThai.setBounds(52, 213, 126, 21);
		add(lblTrangThai);

		//ComboBox trạng thái
		optionTrangThai = new JComboBox();
		optionTrangThai.setToolTipText("");
		optionTrangThai.setBounds(188, 214, 262, 21);
		add(optionTrangThai);

		// Thêm trường ẩn cho idTK
		textIdTK = new JTextField();
		textIdTK.setBounds(0, 0, 0, 0); // Đặt kích thước 0 để ẩn
		textIdTK.setVisible(false); // Ẩn khỏi GUI
		textIdTK.setText("0"); // Giá trị mặc định khi thêm
		add(textIdTK);

		//Nút thêm
		btnAdd = new JButton("Thêm");
		btnAdd.setBackground(Color.GREEN);
		btnAdd.setBounds(718, 29, 114, 32);
		add(btnAdd);

		//Nút update
		btnUpdate = new JButton("Cập nhật");
		btnUpdate.setBackground(Color.ORANGE);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setBounds(718, 72, 114, 32);
		add(btnUpdate);

		//Input tìm kiếm
		textSearch = new JTextField();
		textSearch.setBounds(52, 245, 704, 30);
		add(textSearch);
		textSearch.setHorizontalAlignment(SwingConstants.CENTER);
		textSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textSearch.setText(iniTextSearchPlaceHolder);
		textSearch.setColumns(10);
		//Thêm sự kiện tự xóa nội dung khi focus vào input
		textSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textSearch.setText(""); // Xóa nội dung khi focus
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textSearch.getText().trim().isEmpty()) { //Nếu field rỗng
					textSearch.setText(iniTextSearchPlaceHolder); // Đặt placeholder về lại như ban đầu
				}
			}
		});

		//Nút tìm kiếm
		btnSearch = new JButton("");
		btnSearch.setBounds(753, 245, 79, 30);
		add(btnSearch);
		btnSearch.setIcon(searchIcon);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(52, 285, 780, 189);
		add(scrollPane);
		

		DefaultTableModel tableModel = new DefaultTableModel(
				new Object[][] {
				},
				taiKhoanJTableColumns
		);
		taiKhoanTable = new JTable(tableModel);
		taiKhoanTable.setEnabled(true); // Cho phép tương tác (chọn dòng), nhưng không cho chỉnh sửa
		taiKhoanTable.setDefaultEditor(Object.class, null); // Xóa editor mặc định để ngăn chỉnh sửa
		scrollPane.setViewportView(taiKhoanTable);
		
		// load list
		loadTaiKhoanList(taiKhoanBUS.getAllTaiKhoan());

		// setup field
		setUpNQFields();
		setUpTTFields();

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

	//Set up các option trong ComboBox nhóm quyền
	public void setUpNQFields() {
		// load danh sach danh muc cha
		List<NhomQuyenDTO> nhomQuyenList = taiKhoanBUS.getAllNhomQuyen();
		optionNhomQuyen.addItem(new ComboItem(-1, "--- Chọn nhóm quyền ---"));
		for(NhomQuyenDTO item : nhomQuyenList)
			optionNhomQuyen.addItem(new ComboItem(item.getIdNQ(), item.getTenNQ()));
	}

	//Set up các option trong Combobox Trạng thái
	public void setUpTTFields(){
		optionTrangThai.addItem(new ComboItem(0, "Bị khóa"));
		optionTrangThai.addItem(new ComboItem(1, "Hoạt động"));
	}

	//Set up các eventListener
	private void setupEventListeners() {
		//Nút thêm
		btnAdd.addActionListener(
				e -> {
					if (validateInput("add")) {
						addTaiKhoan();
					}
				}
		);

		//Nút cập nhật
		btnUpdate.addActionListener(
				e -> {
					if (validateInput("update")) {
						updateTaiKhoan();
					}
				}
		);

		//Nút tìm kiếm
		btnSearch.addActionListener(e -> searchTaiKhoan());

		//Set up sự kiện: khi nhấn dòng trong bảng
		taiKhoanTable.getSelectionModel().addListSelectionListener(e -> {
			/*
			* getValueIsAdjusting(): Là một phương thức của ListSelectionEvent, trả về một giá trị boolean:
			*		+ true: Nếu sự kiện là một phần của chuỗi thay đổi liên tục,
			* 			tức là quá trình chọn vẫn đang "điều chỉnh" (adjusting).
			*		+ false: Nếu quá trình chọn đã hoàn tất, trạng thái đã ổn định.
			* Trong Swing, khi người dùng tương tác với bảng (như nhấp chuột, kéo chuột, hoặc dùng phím),
			* hệ thống có thể phát sinh nhiều sự kiện liên tiếp.
			* getValueIsAdjusting() giúp phân biệt giữa các sự kiện trung gian và sự kiện cuối cùng
			* Kiểm tra !e.getValueIsAdjusting() đảm bảo chỉ xử lý sự kiện khi người dùng đã hoàn tất thao tác chọn,
			* tức là khi trạng thái chọn đã ổn định.
			* */
			if (!e.getValueIsAdjusting() && taiKhoanTable.getSelectedRow() != -1) {
				int selectedRow = taiKhoanTable.getSelectedRow();
				fillFieldsFromSelectedRow(selectedRow);
			}
		});
	}
	//											===END: các hàm setUp cho GUI===

	//												===Các hàm xử lý sự kiện===
	//Xử lý sự kiện nút thêm
	private void addTaiKhoan() {
		TaiKhoanDTO taiKhoan = createTaiKhoanDTO();
		List<String> addTaiKhoanMessage = taiKhoanBUS.add(taiKhoan);
		if (addTaiKhoanMessage.isEmpty()) {
			showSuccessMessageAndRefreshUI("Thành công", "Thêm tài khoản thành công!");
		} else {
			StringBuilder isExistedMessage = new StringBuilder();
			for(String message: addTaiKhoanMessage) {
				isExistedMessage.append(message + "\n");
			}
			showErrorMessage("Lỗi!!!", isExistedMessage.toString());
		}
	}

	//Xử lý sự kiện nút cập nhật
	private void updateTaiKhoan() {
		TaiKhoanDTO taiKhoan = createTaiKhoanDTO();
		List<String> updateTaiKhoanMessage = taiKhoanBUS.update(taiKhoan);
		if (updateTaiKhoanMessage.isEmpty()) {
			// Kiểm tra xem tài khoản vừa cập nhật có phải là tài khoản đang đăng nhập không
			TaiKhoanDTO currentUser = adminFrame.getCurrentUser();
			if (currentUser != null && currentUser.getIdTK() == taiKhoan.getIdTK()) {
				// Cập nhật currentUser với thông tin mới
				adminFrame.setCurrentUser(taiKhoan);
				// Làm mới giao diện navbar để hiển thị thông tin mới
				adminFrame.refreshNavbar();
			}
			showSuccessMessageAndRefreshUI("Thành công!", "Cập nhật tài khoản thành công!");
		} else {
			StringBuilder isExistedMessage = new StringBuilder();
			for(String message: updateTaiKhoanMessage) {
				isExistedMessage.append(message + "\n");
			}
			showErrorMessage("Lỗi!!!", isExistedMessage.toString());
		}
	}

	//Điền dữ liệu vào các trường khi chọn một dòng trong bảng
	private void fillFieldsFromSelectedRow(int selectedRow) {
		textIdTK.setText(taiKhoanTable.getValueAt(selectedRow, 0).toString()); // gán ID của hàng được chọn vào trường ẩn
		textTenTK.setText(taiKhoanTable.getValueAt(selectedRow, 1).toString());
		textHoTen.setText(taiKhoanTable.getValueAt(selectedRow, 2).toString());
		textEmail.setText(taiKhoanTable.getValueAt(selectedRow, 3).toString());
		textDienThoai.setText(taiKhoanTable.getValueAt(selectedRow, 4).toString());

		String trangThai = taiKhoanTable.getValueAt(selectedRow, 5).toString();
		optionTrangThai.setSelectedIndex(trangThai.equals("Hoạt động") ? 1 : 0);

		int idNQ = -1;
		List<NhomQuyenDTO> nhomQuyenList = taiKhoanBUS.getAllNhomQuyen();
		for (NhomQuyenDTO nhomquyen: nhomQuyenList) {
			if (nhomquyen.getTenNQ().equals(taiKhoanTable.getValueAt(selectedRow, 6).toString())) {
				idNQ = nhomquyen.getIdNQ();
				break;
			}
		}
		selectComboBoxItem(optionNhomQuyen, idNQ);

		passwordField.setText(""); //Không hiển thị mật khẩu vì lý do bảo mật
	}

	//Xử lý sự kiện nút tìm kiếm
	private void searchTaiKhoan() {
		String searchText = textSearch.getText().trim();
		if (iniTextSearchPlaceHolder.equals(searchText)) {
			searchText = "";
		}
		List<TaiKhoanDTO> result = taiKhoanBUS.search(searchText);
		loadTaiKhoanList(result);
	}
	//											===END: Các hàm xử lý sự kiện===

	//										===Hàm validate input (add, update)===
	private boolean validateInput(String action) {
		//boolean isValidAddInput = true;
		String tenTK = textTenTK.getText().trim();
		String matKhau = new String(passwordField.getPassword()).trim();
		String hoTen = textHoTen.getText().trim();
		String email = textEmail.getText().trim();
		String dienThoai =textDienThoai.getText().trim();
		ComboItem selectedNhomQuyen = (ComboItem) optionNhomQuyen.getSelectedItem();
		ComboItem selectedTrangThai = (ComboItem) optionTrangThai.getSelectedItem();
		//Việc xuất nhiều JOptionPane khiến chúng bị ghi đè với nhau
		// => append hết lỗi, rồi chỉ xuất 1 lần duy nhất
		//Biến errorMessage giờ cũng đóng vai trò tựa như một cờ hiệu
		//Nếu errorMessage.length() > 0 => có lỗi => xuất lỗi và return false; ngược lại return true
		StringBuilder errorMessage = new StringBuilder();

		//Các trường không được để trống (mật khẩu có thể trống khi update)
		if (
				tenTK.isEmpty() ||
				(matKhau.isEmpty() && "add".equals(action)) ||
				hoTen.isEmpty() ||
				email.isEmpty() ||
				dienThoai.isEmpty()
		) {
			errorMessage .append("Vui lòng nhập thông tin cho các trường được để trống: \n");

			if (tenTK.isEmpty()) {
				errorMessage.append("+ Tên tài khoản \n");
			}
			if (matKhau.isEmpty() && "add".equals(action))
				errorMessage.append("+ Mật khẩu \n");
			if (hoTen.isEmpty()) {
				errorMessage.append("+ Họ tên \n");
			}
			if (email.isEmpty()) {
				errorMessage.append("+ Email \n");
			}
			if (dienThoai.isEmpty()) {
				errorMessage.append("+ Điện thoại \n");
			}
		}

		//Kiểm tra định dạng các trường thông tin (regex trong java phải dùng \\ thay vì \ nếu có)
		String tenTKRegex = "^\\S+$"; //Không được gồm dấu cách
		String matKhauRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
		/*
		* Mật khẩu phải có tối thiểu 8 ký tự: .{8,}, bao gồm ít nhất:
		* 		+ Một ký tự thường: (?=.*[a-z])
		* 		+ Một ký tự in hoa: (?=.*[A-Z])
		* 		+ Một ký tự số (?=.*\d)
		*/

		String hoTenRegex = "^[A-Za-zÀ-ỹ]+( [A-Za-zÀ-ỹ]+)*$";
		/*
		* Họ tên chỉ chứa chữ cái (cho phép cả Tiếng Việt)
		* 		+ Từ đầu tiên [A-Za-zÀ-ỹ]
		* 		+ Một hoặc nhiều từ tiếp theo phải có dấu cách ở phía trước ( [A-Za-zÀ-ỹ]+)*
		*/

		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		/* Email có định dạng:
		* 		+ Tên người dùng (có thể bao gồm ._%+-): [a-zA-Z0-9._%+-]
		*		+ Tên miền bao gồm ký tự @ và kèm phần tên miền [a-zA-Z0-9.-]
		* 		+ Kết thúc bằng đuôi .com hay đuôi khác tương tự, tối thiểu 2 ký tự: .[a-zA-Z]{2,}
		* */

		String dienThoaiRegex = "^0[0-9]{9}$"; //regex sdt thông thường (số ảo vẫn được chấp nhận)
		// String dienThoaiRegex = "^(0|\\+84)(3[2-9]|5[689]|7[06-9]|8[1-5]|9[0-9])[0-9]{7}$";
		/*
		* Regex này bao gồm các số điện thoại hợp lệ của VN, bao gồm:
		* 		+ Đầu 0 hoặc + 84
		* 		+ 2 chữ số tiếp theo có thể gồm:
		* 			- 32 - 39: Viettel
		* 			- 56 - 58 - 59: Vietnamobile, Gmobile
		* 			- 70 - 76 -> 79: Mobifone
		* 			- 81 -> 85: Vinaphone
		* 			- 90 - 99: các mạng cũ và chưa được chuyển đổi, ...
		*/
		if (
				(!tenTK.isEmpty() && !tenTK.matches(tenTKRegex) ||
				!matKhau.isEmpty() && !matKhau.matches(matKhauRegex) ||
				(!hoTen.isEmpty() && !hoTen.matches(hoTenRegex)) ||
				(!email.isEmpty() && !email.matches(emailRegex)) ||
				!dienThoai.isEmpty() && !dienThoai.matches(dienThoaiRegex)
			)
		) {
			errorMessage.append("Vui lòng nhập đúng định dạng cho các trường thông tin \n");

			if (!tenTK.isEmpty() && !tenTK.matches(tenTKRegex)) {
				errorMessage.append("+ Tên tài khoản: không được chứa khoảng trắng \n");
			}
			if (!matKhau.isEmpty() && !matKhau.matches(matKhauRegex)) {
				errorMessage.append("+ Mật khẩu: Chứa ít nhất 8 ký tự, bao gồm ít nhất: \n");
				errorMessage.append("		- 1 ký tự in thường \n");
				errorMessage.append("		- 1 ký tự in hoa \n");
				errorMessage.append("		- 1 ký tự số \n");
				errorMessage.append("		* Ví dụ: examPle2 \n");
			}
			if (!hoTen.isEmpty() && !hoTen.matches(hoTenRegex)) {
				errorMessage.append("+ Họ tên: Chỉ bao gồm chữ cái và 1 khoảng trắng giữa các từ, không bắt đầu bằng khoảng trắng");
				errorMessage.append("		- Ví dụ: \"Nguyễn Trọng Hiếu\" ");
			}
			if (!email.isEmpty() && !email.matches(emailRegex)) {
				errorMessage.append("+ Email (Ví dụ: tralalerotralala@example.com) \n");
			}
			if (!dienThoai.isEmpty() && !dienThoai.matches(dienThoaiRegex)) {
				errorMessage.append("+ Email (Bao gồm 10 chữ số, bắt đầu từ số 0) \n");
			}
		}

		// Kiểm tra combo box
		if (selectedNhomQuyen == null || selectedNhomQuyen.getKey() == -1) {
			errorMessage.append("Vui lòng chọn một nhóm quyền hợp lệ \n");
		}
		if (selectedTrangThai == null) {
			errorMessage.append("Vui lòng chọn trạng thái cho tài khoản \n");
		}

		if (errorMessage.length() > 0) {
			showErrorMessage("Lỗi nhập liệu", errorMessage.toString());
			return false;
		}
		return true;
	}
	//									===END: Hàm validate input (add, update)===

	//													===Các hàm tiện ích===

	//Căn chỉnh hình ảnh (hàm này nên được nằm trong class chung)
	private ImageIcon getScaledImage(int width, int height, String path) {
		// Load the original image
		ImageIcon originalIcon = new ImageIcon(DanhMucPanel.class.getResource(path));

		// Resize the image (e.g., to 50x50 pixels)
		Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		// Create a new ImageIcon with the scaled image
		return new ImageIcon(scaledImage);
	}

	//Hàm tạo một đối tượng TaiKhoanDTO (để add, update)
	private TaiKhoanDTO createTaiKhoanDTO() {
		TaiKhoanDTO taiKhoan = new TaiKhoanDTO();
		taiKhoan.setIdTK(Integer.parseInt(textIdTK.getText().trim()));
		taiKhoan.setTenTK(textTenTK.getText().trim());
		taiKhoan.setMatkhau(new String(passwordField.getPassword()).trim());
		taiKhoan.setHoten(textHoTen.getText().trim());
		taiKhoan.setEmail(textEmail.getText().trim());
		taiKhoan.setDienthoai(textDienThoai.getText().trim());
		ComboItem selectedNhomQuyen = (ComboItem) optionNhomQuyen.getSelectedItem();
		ComboItem selectedTrangThai = (ComboItem) optionTrangThai.getSelectedItem();
		taiKhoan.setIdNQ(selectedNhomQuyen.getKey());
		taiKhoan.setTrangthai(selectedTrangThai.getKey() == 1 ? true : false);
		return taiKhoan;
	}

	// Hàm load danh sách tài khoản
	private void loadTaiKhoanList(List<TaiKhoanDTO> taiKhoanList) {
		DefaultTableModel dtm = new DefaultTableModel(
				taiKhoanJTableColumns, 0
		);

		for (TaiKhoanDTO item : taiKhoanList) {
			String tenNQ;
			switch (item.getIdNQ()){
				case 1:
					tenNQ = "Admin";
					break;
				case 2:
					tenNQ = "Nhân viên";
					break;
				default:
					tenNQ = "Không rõ";
					break;
			}

			Object[] row = {
					item.getIdTK(),
					item.getTenTK(),
					item.getHoten(),
					item.getEmail(),
					item.getDienthoai(),
					item.getTrangthai() ? "Hoạt động" : "Bị ẩn",
					tenNQ
			};
			dtm.addRow(row);
		}

		taiKhoanTable.setModel(dtm);
	}

	//Hàm xóa các trường thông tin sau khi add/ update
	private void clearFields() {
		textIdTK.setText("0"); // Đặt lại idTK trong trường ẩn về 0 để giúp cho hàm thêm mới
		textTenTK.setText("");
		passwordField.setText("");
		textHoTen.setText("");
		textEmail.setText("");
		textDienThoai.setText("");
		optionNhomQuyen.setSelectedIndex(0);
		optionTrangThai.setSelectedIndex(0);
	}

	//Hàm lấy item tương ứng của đối tượng được chọn cho ComboBox
	private void selectComboBoxItem(JComboBox<ComboItem> comboBox, int key) {
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			ComboItem item = comboBox.getItemAt(i);
			if (item.getKey() == key) {
				comboBox.setSelectedIndex(i);
				break;
			}
		}
	}

	//Hiển thị thông báo lỗi (hàm này cũng nên nằm trong một class chung)
	private void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title,JOptionPane.ERROR_MESSAGE);
	}

	//Hiển thị thông báo thành công và làm mới UI
	private void showSuccessMessageAndRefreshUI(String title, String successMessage) {
		JOptionPane.showMessageDialog(null, successMessage, title, JOptionPane.INFORMATION_MESSAGE);
		//Cập nhật lại UI
		loadTaiKhoanList(taiKhoanBUS.getAllTaiKhoan());
		clearFields();
	}
	//											===END: các hàm tiện ích===
}