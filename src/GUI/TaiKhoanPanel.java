package GUI;
import BUS.TaiKhoanBUS;
import DTO.DanhMucDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import DTO.ComboItem;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;

public class TaiKhoanPanel extends JPanel {
	TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();

	private static final long serialVersionUID = 1L;
	private JTextField textSearch;
	private JTextField textTenTK;
	private JComboBox optionNhomQuyen;
	private JComboBox optionTrangThai;
	private JTable table;
	private String[] tableColumns = {"ID", "Tên TK", "Họ tên", "Email", "Điện thoại", "Trạng thái", "Nhóm quyền"};
	private JPasswordField passwordField;
	private JTextField textHoTen;
	private JTextField textEmail;
	private JTextField textDienThoai;

	/**
	 * Create the panel.
	 */
	public TaiKhoanPanel() {

		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 887, 508);
		setLayout(null);
		ImageIcon searchIcon = getScaledImage(20, 20, "/ASSET/Images/searchIcon.png");

		JLabel lblTenTK = new JLabel("Tên tài khoản");
		lblTenTK.setHorizontalAlignment(SwingConstants.CENTER);
		lblTenTK.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTenTK.setBounds(52, 29, 126, 21);
		add(lblTenTK);

		textTenTK = new JTextField();
		textTenTK.setHorizontalAlignment(SwingConstants.LEFT);
		textTenTK.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textTenTK.setBounds(188, 29, 520, 21);
		add(textTenTK);
		textTenTK.setColumns(10);
		
		JLabel lblMatKhau = new JLabel("Mật khẩu");
		lblMatKhau.setHorizontalAlignment(SwingConstants.CENTER);
		lblMatKhau.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMatKhau.setBounds(52, 58, 126, 21);
		add(lblMatKhau);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(188, 60, 520, 19);
		add(passwordField);
		
		JLabel lblHoTen = new JLabel("Họ tên");
		lblHoTen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoTen.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblHoTen.setBounds(52, 89, 126, 21);
		add(lblHoTen);
		
		textHoTen = new JTextField();
		textHoTen.setHorizontalAlignment(SwingConstants.LEFT);
		textHoTen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textHoTen.setColumns(10);
		textHoTen.setBounds(188, 89, 520, 21);
		add(textHoTen);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmail.setBounds(52, 120, 126, 21);
		add(lblEmail);
		
		textEmail = new JTextField();
		textEmail.setHorizontalAlignment(SwingConstants.LEFT);
		textEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textEmail.setColumns(10);
		textEmail.setBounds(188, 120, 520, 21);
		add(textEmail);
		
		JLabel lblDienThoai = new JLabel("Điện thoại");
		lblDienThoai.setHorizontalAlignment(SwingConstants.CENTER);
		lblDienThoai.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDienThoai.setBounds(52, 151, 126, 21);
		add(lblDienThoai);
		
		textDienThoai = new JTextField();
		textDienThoai.setHorizontalAlignment(SwingConstants.LEFT);
		textDienThoai.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textDienThoai.setColumns(10);
		textDienThoai.setBounds(188, 151, 520, 21);
		add(textDienThoai);
		
		JLabel lblNhomQuyen = new JLabel("Nhóm quyền");
		lblNhomQuyen.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhomQuyen.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNhomQuyen.setBounds(52, 182, 126, 21);
		add(lblNhomQuyen);

		optionNhomQuyen = new JComboBox();
		optionNhomQuyen.setToolTipText("");
		optionNhomQuyen.setBounds(188, 183, 262, 21);
		add(optionNhomQuyen);
		
		JLabel lblTrangThai = new JLabel("Trạng thái");
		lblTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrangThai.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTrangThai.setBounds(52, 213, 126, 21);
		add(lblTrangThai);
		
		optionTrangThai = new JComboBox();
		optionTrangThai.setToolTipText("");
		optionTrangThai.setBounds(188, 214, 262, 21);
		add(optionTrangThai);


		JButton btnAdd = new JButton("Thêm");
		btnAdd.setBackground(Color.GREEN);
		btnAdd.setBounds(718, 29, 114, 32);
		add(btnAdd);

		JButton btnUpdate = new JButton("Cập nhật");
		btnUpdate.setBackground(Color.ORANGE);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setBounds(718, 72, 114, 32);
		add(btnUpdate);
		textSearch = new JTextField();
		textSearch.setBounds(52, 245, 704, 30);
		add(textSearch);
		textSearch.setHorizontalAlignment(SwingConstants.CENTER);
		textSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textSearch.setText("Nhập idDM, tên danh mục");
		textSearch.setColumns(10);

		JButton btnSearch = new JButton("");
		btnSearch.setBounds(753, 244, 79, 32);
		add(btnSearch);
		btnSearch.setIcon(searchIcon);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(52, 285, 780, 189);
		add(scrollPane);
		

		DefaultTableModel tableModel = new DefaultTableModel(
				new Object[][] {
				},
				tableColumns
		);
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		
		// load list
		loadTaiKhoanList();

		// setup field
		setUpNQFields();
		setUpTTFields();
	}

	private ImageIcon getScaledImage(int width, int height, String path) {
		// Load the original image
		ImageIcon originalIcon = new ImageIcon(DanhMucPanel.class.getResource(path));

		// Resize the image (e.g., to 50x50 pixels)
		Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		// Create a new ImageIcon with the scaled image
		return new ImageIcon(scaledImage);
	}

	// load danh muc list
	private void loadTaiKhoanList() {
		DefaultTableModel dtm = new DefaultTableModel(
				tableColumns, 0
		);

		List<TaiKhoanDTO> taiKhoanList = taiKhoanBUS.getAllTaiKhoan();
		for (TaiKhoanDTO item : taiKhoanList) {

			Object[] row = {
					item.getIdTK(),
					item.getIdTK(),
					item.getHoten(),
					item.getEmail(),
					item.getDienthoai(),
					item.getTrangthai(),
					item.getIdNQ()
			};
			dtm.addRow(row);
		}

		table.setModel(dtm);
	}

	public void setUpNQFields() {
		// load danh sach danh muc cha
		List<NhomQuyenDTO> nhomQuyenList = taiKhoanBUS.getAllNhomQuyen();
		optionNhomQuyen.addItem(new ComboItem(-1, "Không có"));
		for(NhomQuyenDTO item : nhomQuyenList)
			optionNhomQuyen.addItem(new ComboItem(item.getIdNQ(), item.getTenNQ()));
	}
	
	public void setUpTTFields(){
		optionTrangThai.addItem(new ComboItem(-1, "Hoạt động"));
		optionTrangThai.addItem(new ComboItem(0, "Bị khóa"));
	}

}