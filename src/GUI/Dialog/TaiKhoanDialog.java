package GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import BUS.TaiKhoanBUS;
import BUS.NhomQuyenBUS;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;
import GUI.AdminFrame;
import GUI.TaiKhoanPanel;

import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.awt.Font;
import java.awt.Color;

public class TaiKhoanDialog extends JDialog {
	private TaiKhoanBUS taiKhoanBus = new TaiKhoanBUS();
	private NhomQuyenBUS nhomQuyenBus = new NhomQuyenBUS();

	//TrongHiuuu 23/4
	private AdminFrame adminFrame;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblTitle, lblMatKhau, lblMatKhauConfirm;
	private JTextField txtTenTK, txtMatKhau, txtMatKhauConfirm, txtEmail;
	private JComboBox<String> cboTrangThai = new JComboBox<>();
	private JComboBox<NhomQuyenDTO> cboQuyen = new JComboBox<>();
	private JLabel errTenTK, errMatKhau, errMatKhauConfirm, errEmail;
	private JButton btnSubmit, btnCancel;

	/**
	 * Create the dialog.
	 */
	public TaiKhoanDialog(AdminFrame adminFrame) {
		//TrongHiuuu 23/4
		this.adminFrame = adminFrame;

		setTitle("Thêm tài khoản");
		setSize(418, 457);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			lblTitle = new JLabel("Thêm tài khoản");
			lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.insets = new Insets(5, 10, 5, 10);
			gbc_lblTitle.gridwidth = 4;
			gbc_lblTitle.anchor = GridBagConstraints.WEST;
			gbc_lblTitle.gridx = 0;
			gbc_lblTitle.gridy = 0;
			contentPanel.add(lblTitle, gbc_lblTitle);
		}

		actionInit();
		textFieldInit();


		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		
	}

	private void textFieldInit(){
		// TrongHiuuu 27/04/2025: giờ chỉ khai bảo các trường mật khẩu trước, việc thêm và xóa sẽ thực hiện sau
		txtMatKhau = new JPasswordField();
		txtMatKhauConfirm = new JPasswordField();

		{
			JLabel lblTenTK = new JLabel("Tên tài khoản");
			lblTenTK.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblTenTK = new GridBagConstraints();
			gbc_lblTenTK.anchor = GridBagConstraints.WEST;
			gbc_lblTenTK.insets = new Insets(0, 10, 0, 0);
			gbc_lblTenTK.gridx = 0;
			gbc_lblTenTK.gridy = 1;
			contentPanel.add(lblTenTK, gbc_lblTenTK);
		}
		{
			txtTenTK = new JTextField();
			txtTenTK.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_txtTenTK = new GridBagConstraints();
			gbc_txtTenTK.insets = new Insets(0, 0, 5, 10);
			gbc_txtTenTK.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtTenTK.gridx = 2;
			gbc_txtTenTK.gridy = 1;
			contentPanel.add(txtTenTK, gbc_txtTenTK);
			txtTenTK.setColumns(10);
		}
		{
			errTenTK = new JLabel();
			errTenTK.setForeground(Color.RED);
			errTenTK.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			GridBagConstraints gbc_errTenTK = new GridBagConstraints();
			gbc_errTenTK.anchor = GridBagConstraints.WEST;
			gbc_errTenTK.insets = new Insets(0, 10, 5, 10);
			gbc_errTenTK.gridx = 2;
			gbc_errTenTK.gridy = 2;
			contentPanel.add(errTenTK, gbc_errTenTK);
		}
		{
			JLabel lblEmail = new JLabel("Email");
			lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblEmail = new GridBagConstraints();
			gbc_lblEmail.anchor = GridBagConstraints.WEST;
			gbc_lblEmail.insets = new Insets(0, 10, 0, 5);
			gbc_lblEmail.gridx = 0;
			gbc_lblEmail.gridy = 7;
			contentPanel.add(lblEmail, gbc_lblEmail);
		}

		{
			txtEmail = new JTextField();
			txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			txtEmail.setColumns(10);
			GridBagConstraints gbc_txtEmail = new GridBagConstraints();
			gbc_txtEmail.insets = new Insets(0, 0, 5, 10);
			gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtEmail.gridx = 2;
			gbc_txtEmail.gridy = 7;
			contentPanel.add(txtEmail, gbc_txtEmail);
		}
		{
			errEmail = new JLabel();
			errEmail.setForeground(Color.RED);
			errEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			GridBagConstraints gbc_errEmail = new GridBagConstraints();
			gbc_errEmail.anchor = GridBagConstraints.WEST;
			gbc_errEmail.insets = new Insets(0, 10, 5, 10);
			gbc_errEmail.gridx = 2;
			gbc_errEmail.gridy = 8;
			contentPanel.add(errEmail, gbc_errEmail);
		}
		{
			JLabel lblQuyen = new JLabel("Quyền");
			lblQuyen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblQuyen = new GridBagConstraints();
			gbc_lblQuyen.anchor = GridBagConstraints.WEST;
			gbc_lblQuyen.insets = new Insets(0, 10, 0, 5);
			gbc_lblQuyen.gridx = 0;
			gbc_lblQuyen.gridy = 9;
			contentPanel.add(lblQuyen, gbc_lblQuyen);
		}
		{
			cboQuyen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_cboQuyen = new GridBagConstraints();
			gbc_cboQuyen.insets = new Insets(0, 0, 5, 10);
			gbc_cboQuyen.fill = GridBagConstraints.HORIZONTAL;
			gbc_cboQuyen.gridx = 2;
			gbc_cboQuyen.gridy = 9;
			loadCboNhomQuyen();
			contentPanel.add(cboQuyen, gbc_cboQuyen);
		}
		{
			JLabel lblTrangThai = new JLabel("Trạng thái");
			lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblTrangThai = new GridBagConstraints();
			gbc_lblTrangThai.anchor = GridBagConstraints.WEST;
			gbc_lblTrangThai.insets = new Insets(0, 10, 0, 5);
			gbc_lblTrangThai.gridx = 0;
			gbc_lblTrangThai.gridy = 11;
			contentPanel.add(lblTrangThai, gbc_lblTrangThai);
		}
		{
			cboTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_cboTrangThai = new GridBagConstraints();
			gbc_cboTrangThai.insets = new Insets(0, 0, 5, 10);
			gbc_cboTrangThai.fill = GridBagConstraints.HORIZONTAL;
			gbc_cboTrangThai.gridx = 2;
			gbc_cboTrangThai.gridy = 11;
			cboTrangThai.addItem("Hoạt động");
			cboTrangThai.addItem("Bị khóa");
			contentPanel.add(cboTrangThai, gbc_cboTrangThai);
		}
	}

	private void actionInit(){
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 14;
			contentPanel.add(panel, gbc_panel);
			FlowLayout fl_panel = new FlowLayout(FlowLayout.RIGHT, 5, 5);
			fl_panel.setAlignOnBaseline(true);
			panel.setLayout(fl_panel);
			{
				btnSubmit = new JButton("Thêm");
				btnSubmit.setBackground(new Color(0, 128, 0));
				btnSubmit.setForeground(new Color(255, 255, 255));
				btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
				btnSubmit.setContentAreaFilled(false);
				btnSubmit.setOpaque(true);
				btnSubmit.addActionListener(e->submitForm());
				panel.add(btnSubmit);
			}
			{
				btnCancel = new JButton("Hủy");
				btnCancel.setBackground(new Color(255, 51, 102));
				btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
				btnCancel.setForeground(new Color(255, 255, 255));
				btnCancel.setContentAreaFilled(false);
				btnCancel.setOpaque(true);
				btnCancel.addActionListener(e->cancel());
				panel.add(btnCancel);
			}
		}
	}

	// TrongHiuuu 27/04/2025: thêm hàm thêm và loại bỏ các trường mật khẩu
	private void addPasswordFields() {
		{
			lblMatKhau = new JLabel("Mật khẩu");
			lblMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblMatKhau = new GridBagConstraints();
			gbc_lblMatKhau.anchor = GridBagConstraints.WEST;
			gbc_lblMatKhau.insets = new Insets(0, 10, 0, 5);
			gbc_lblMatKhau.gridx = 0;
			gbc_lblMatKhau.gridy = 3;
			contentPanel.add(lblMatKhau, gbc_lblMatKhau);
		}
		{
			txtMatKhau = new JPasswordField();
			txtMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			txtMatKhau.setColumns(10);
			GridBagConstraints gbc_txtMatKhau = new GridBagConstraints();
			gbc_txtMatKhau.insets = new Insets(0, 0, 5, 10);
			gbc_txtMatKhau.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtMatKhau.gridx = 2;
			gbc_txtMatKhau.gridy = 3;
			contentPanel.add(txtMatKhau, gbc_txtMatKhau);
		}
		{
			errMatKhau = new JLabel();
			errMatKhau.setForeground(Color.RED);
			errMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			GridBagConstraints gbc_errMatKhau = new GridBagConstraints();
			gbc_errMatKhau.anchor = GridBagConstraints.WEST;
			gbc_errMatKhau.insets = new Insets(0, 10, 5, 10);
			gbc_errMatKhau.gridx = 2;
			gbc_errMatKhau.gridy = 4;
			contentPanel.add(errMatKhau, gbc_errMatKhau);
		}
		{
			lblMatKhauConfirm = new JLabel("Mật khẩu xác nhận");
			lblMatKhauConfirm.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblMatKhauConfirm = new GridBagConstraints();
			gbc_lblMatKhauConfirm.anchor = GridBagConstraints.WEST;
			gbc_lblMatKhauConfirm.insets = new Insets(0, 10, 0, 5);
			gbc_lblMatKhauConfirm.gridx = 0;
			gbc_lblMatKhauConfirm.gridy = 5;
			contentPanel.add(lblMatKhauConfirm, gbc_lblMatKhauConfirm);
		}
		{
			txtMatKhauConfirm = new JPasswordField();
			txtMatKhauConfirm.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			txtMatKhauConfirm.setColumns(10);
			GridBagConstraints gbc_txtMatKhauConfirm = new GridBagConstraints();
			gbc_txtMatKhauConfirm.insets = new Insets(0, 0, 5, 10);
			gbc_txtMatKhauConfirm.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtMatKhauConfirm.gridx = 2;
			gbc_txtMatKhauConfirm.gridy = 5;
			contentPanel.add(txtMatKhauConfirm, gbc_txtMatKhauConfirm);
		}
		{
			errMatKhauConfirm = new JLabel();
			errMatKhauConfirm.setForeground(Color.RED);
			errMatKhauConfirm.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			GridBagConstraints gbc_errMatKhauConfirm = new GridBagConstraints();
			gbc_errMatKhauConfirm.anchor = GridBagConstraints.WEST;
			gbc_errMatKhauConfirm.insets = new Insets(0, 10, 5, 10);
			gbc_errMatKhauConfirm.gridx = 2;
			gbc_errMatKhauConfirm.gridy = 6;
			contentPanel.add(errMatKhauConfirm, gbc_errMatKhauConfirm);
		}
	}

	private void removePasswordFields() {
		if (txtMatKhau != null && lblMatKhau != null) {
			contentPanel.remove(txtMatKhau);
			contentPanel.remove(lblMatKhau);
		}
		if (txtMatKhauConfirm != null && lblMatKhauConfirm != null) {
			contentPanel.remove(txtMatKhauConfirm);
			contentPanel.remove(lblMatKhauConfirm);
		}
	}

	private void loadCboNhomQuyen() {
		List<NhomQuyenDTO> arr = nhomQuyenBus.getAll();
		for(NhomQuyenDTO item : arr) 
			cboQuyen.addItem(item);
	}

	public void showAdd() {
		// reset action button
		cboTrangThai.setEnabled(false);

		// TrongHiuuu 27/04/2025: Thêm các trường mật khẩu nếu là form thêm tài khoản
		addPasswordFields();
		btnSubmit.setText("Thêm");
		btnSubmit.setActionCommand("add");

		setModal(true);
		setVisible(true);
	}

	public void showEdit(int idTK) {
//		textFieldInit(false);
		setTitle("Cập nhật thông tin tài khoản");
		lblTitle.setText("Cập nhật thông tin tài khoản");

		// TrongHiuuu 27/04/2025: Xóa các trường mật khẩu nếu là form cập nhật tài khoản
		removePasswordFields();
		TaiKhoanDTO taiKhoan = taiKhoanBus.findByIdTK(idTK);
		txtTenTK.setText(taiKhoan.getTenTK());
		txtEmail.setText(taiKhoan.getEmail());
		txtMatKhau.setVisible(false);
		txtMatKhauConfirm.setVisible(false);
		NhomQuyenDTO quyen = nhomQuyenBus.findByIdNQ(taiKhoan.getIdNQ());
		cboQuyen.setSelectedItem(quyen);
		cboTrangThai.setSelectedItem(taiKhoan.getTrangthai() == 1 ? "Hoạt động" : "Bị khóa");
		
		// reset action button
		btnSubmit.setText("Cập nhật");
		btnSubmit.setActionCommand("edit_"+idTK);

		setVisible(true);
	}

	private boolean isError() {
		// TrongHiuuu 27/04/2025: Thay đổi danh sách label errors từ mảng thành arrayList
		// để thuận tiện việc thêm động 2 label error password và confirm password
		// remove existed errors
		List <JLabel> errors = new ArrayList<>();
		errors.add(errTenTK);
		errors.add(errEmail);
		if (btnSubmit.getActionCommand().equals("add")) {
			errors.add(errMatKhau);
			errors.add(errMatKhauConfirm);
		}

		for (JLabel error : errors) {
			error.setText("");
		}

		boolean isError = false;
		// tenTK
		if(txtTenTK.getText().trim().isEmpty()){
			errTenTK.setText("Tên không được để trống");
			isError = true;
		}

		// matkhau
		/*
		* Mật khẩu phải có tối thiểu 8 ký tự: .{8,}, bao gồm ít nhất:
		* 		+ Một ký tự thường: (?=.*[a-z])
		* 		+ Một ký tự in hoa: (?=.*[A-Z])
		* 		+ Một ký tự số (?=.*\d)
		*/
		if (btnSubmit.getActionCommand().equals("add")) {
			String matKhauRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
			String matKhau = txtMatKhau.getText();
			if(matKhau.trim().isEmpty()){
				errMatKhau.setText("Mật khẩu không được để trống");
				isError = true;
			}else if(!matKhau.matches(matKhauRegex)){
				errMatKhau.setText("Mật khẩu phải có tối thiểu 8 ký tự, gồm 1 ký tự thường, 1 ký tự in hoa, 1 ký tự số");
				isError = true;
			}

			// matkhauConfirm
			String matKhauConfirm = txtMatKhauConfirm.getText();
			if(matKhauConfirm.trim().isEmpty()){
				errMatKhauConfirm.setText("Mật khẩu xác nhận không được để trống");
				isError = true;
			}else if(!matKhauConfirm.equals(txtMatKhau.getText())){
				errMatKhauConfirm.setText("Mật khẩu xác nhận không chính xác");
				isError = true;
			}
		}

		// email
		/* Email có định dạng:
		* 		+ Tên người dùng (có thể bao gồm ._%+-): [a-zA-Z0-9._%+-]
		*		+ Tên miền bao gồm ký tự @ và kèm phần tên miền [a-zA-Z0-9.-]
		* 		+ Kết thúc bằng đuôi .com hay đuôi khác tương tự, tối thiểu 2 ký tự: .[a-zA-Z]{2,}
		* */
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		String email = txtEmail.getText();
		if(txtEmail.getText().trim().isEmpty()){
			errEmail.setText("Email không được để trống");
			isError = true;
		}else if(!email.matches(emailRegex)){
			errEmail.setText("Email không đúng định dạng");
			isError = true;
		}

		return isError;
	}

	private void submitForm() {
		// validate du lieu
		if(!isError()) {
			// collect data
			String tenTK = txtTenTK.getText();
			String email = txtEmail.getText();
			// TrongHiuuu 27/04/2025: hash pw trước khi add/ update
			String matKhau = "";
			if (btnSubmit.getActionCommand().equals("add")) {
				matKhau = taiKhoanBus.hashPassword(txtMatKhau.getText());
			}
			NhomQuyenDTO nhomQuyenSelected = (NhomQuyenDTO) cboQuyen.getSelectedItem();
			int idNQ = nhomQuyenSelected.getIdNQ();
			int trangthai = cboTrangThai.getSelectedItem() == "Hoạt động" ? 1 : 0;

			// action
			String error = "";
			String actionCommand = btnSubmit.getActionCommand();
			int beginIndex = actionCommand.indexOf('_')+1;
			//TrongHiuuu 23/4
			TaiKhoanDTO taiKhoan = new TaiKhoanDTO(tenTK, matKhau, email, trangthai, idNQ);
			if(beginIndex == 0) {
				// add
				error = taiKhoanBus.add(taiKhoan);
			}else {
				// update
				int idTK = Integer.parseInt(actionCommand.substring(beginIndex));
				taiKhoan.setIdTK(idTK);
				error = taiKhoanBus.updateInfo(taiKhoan);
			}
			
			// show message
			if(error != "") {
				// fail
				JOptionPane.showMessageDialog(this, error);
			}
			else {
				// success
				if(beginIndex == 0) 
					JOptionPane.showMessageDialog(this, "Thêm thành công ");
				else {
					//TrongHiuuu 23/4
					JOptionPane.showMessageDialog(this, "Cập nhật thành công ");
					if (taiKhoan.getIdTK() == adminFrame.getCurrentUser().getIdTK()) {
						// Cập nhật currentUser với thông tin mới
						adminFrame.setCurrentUser(taiKhoan);
					}
				}
			}
		}
	}

	private void cancel() {
		dispose();
	}
}
