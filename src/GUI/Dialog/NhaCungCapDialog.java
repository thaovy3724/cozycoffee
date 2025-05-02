package GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.Cursor;

public class NhaCungCapDialog extends JDialog {
	private NhaCungCapBUS nhaCungCapBus = new NhaCungCapBUS();

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtTenNCC, txtDiaChi, txtDienThoai, txtEmail;
	private JComboBox<String> cboTrangThai = new JComboBox<>();
	private JLabel errTenNCC, errDiaChi, errDienThoai, errEmail;
	private JButton btnSubmit, btnCancel;
	/**
	 * Create the dialog.
	 */
	public NhaCungCapDialog() {
		setModal(true);
		setTitle("Thêm nhà cung cấp");
		setBounds(100, 100, 569, 322);
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
			JLabel lblTitle = new JLabel("Thêm nhà cung cấp");
			lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.insets = new Insets(5, 10, 5, 0);
			gbc_lblTitle.gridwidth = 4;
			gbc_lblTitle.anchor = GridBagConstraints.WEST;
			gbc_lblTitle.gridx = 0;
			gbc_lblTitle.gridy = 0;
			contentPanel.add(lblTitle, gbc_lblTitle);
		}

		textFieldInit();
		actionInit();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);

		
	}

	private void textFieldInit(){
		{
			JLabel lblTenNCC = new JLabel("Tên nhà cung cấp");
			lblTenNCC.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblTenNCC = new GridBagConstraints();
			gbc_lblTenNCC.anchor = GridBagConstraints.WEST;
			gbc_lblTenNCC.insets = new Insets(5, 10, 5, 5);
			gbc_lblTenNCC.gridx = 0;
			gbc_lblTenNCC.gridy = 1;
			contentPanel.add(lblTenNCC, gbc_lblTenNCC);
		}
		{
			txtTenNCC = new JTextField();
			txtTenNCC.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_txtTenNCC = new GridBagConstraints();
			gbc_txtTenNCC.insets = new Insets(0, 0, 5, 10);
			gbc_txtTenNCC.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtTenNCC.gridx = 2;
			gbc_txtTenNCC.gridy = 1;
			contentPanel.add(txtTenNCC, gbc_txtTenNCC);
			txtTenNCC.setColumns(10);
		}
		{
			errTenNCC = new JLabel();
			errTenNCC.setForeground(Color.RED);
			errTenNCC.setFont(new Font("Segoe UI", Font.ITALIC, 12));
			GridBagConstraints gbc_errTenNCC = new GridBagConstraints();
			gbc_errTenNCC.anchor = GridBagConstraints.WEST;
			gbc_errTenNCC.insets = new Insets(0, 10, 5, 10);
			gbc_errTenNCC.gridx = 2;
			gbc_errTenNCC.gridy = 2;
			contentPanel.add(errTenNCC, gbc_errTenNCC);
		}
		{
			JLabel lblDiachi = new JLabel("Địa chỉ");
			lblDiachi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblDiachi = new GridBagConstraints();
			gbc_lblDiachi.anchor = GridBagConstraints.WEST;
			gbc_lblDiachi.insets = new Insets(0, 10, 5, 5);
			gbc_lblDiachi.gridx = 0;
			gbc_lblDiachi.gridy = 3;
			contentPanel.add(lblDiachi, gbc_lblDiachi);
		}
		{
			txtDiaChi = new JTextField();
			txtDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			txtDiaChi.setColumns(10);
			GridBagConstraints gbc_txtDiaChi = new GridBagConstraints();
			gbc_txtDiaChi.insets = new Insets(0, 0, 5, 10);
			gbc_txtDiaChi.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtDiaChi.gridx = 2;
			gbc_txtDiaChi.gridy = 3;
			contentPanel.add(txtDiaChi, gbc_txtDiaChi);
		}
		{
			errDiaChi = new JLabel();
			errDiaChi.setForeground(Color.RED);
			errDiaChi.setFont(new Font("Segoe UI", Font.ITALIC, 12));
			GridBagConstraints gbc_errDiaChi = new GridBagConstraints();
			gbc_errDiaChi.anchor = GridBagConstraints.WEST;
			gbc_errDiaChi.insets = new Insets(0, 10, 5, 10);
			gbc_errDiaChi.gridx = 2;
			gbc_errDiaChi.gridy = 4;
			contentPanel.add(errDiaChi, gbc_errDiaChi);
		}
		{
			JLabel lblDienThoai = new JLabel("Điện thoại");
			lblDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblDienThoai = new GridBagConstraints();
			gbc_lblDienThoai.anchor = GridBagConstraints.WEST;
			gbc_lblDienThoai.insets = new Insets(0, 10, 5, 5);
			gbc_lblDienThoai.gridx = 0;
			gbc_lblDienThoai.gridy = 5;
			contentPanel.add(lblDienThoai, gbc_lblDienThoai);
		}
		{
			txtDienThoai = new JTextField();
			txtDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			txtDienThoai.setColumns(10);
			GridBagConstraints gbc_txtDienThoai = new GridBagConstraints();
			gbc_txtDienThoai.insets = new Insets(0, 0, 5, 10);
			gbc_txtDienThoai.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtDienThoai.gridx = 2;
			gbc_txtDienThoai.gridy = 5;
			contentPanel.add(txtDienThoai, gbc_txtDienThoai);
		}
		{
			errDienThoai = new JLabel();
			errDienThoai.setForeground(Color.RED);
			errDienThoai.setFont(new Font("Segoe UI", Font.ITALIC, 12));
			GridBagConstraints gbc_errDienThoai = new GridBagConstraints();
			gbc_errDienThoai.anchor = GridBagConstraints.WEST;
			gbc_errDienThoai.insets = new Insets(0, 10, 5, 10);
			gbc_errDienThoai.gridx = 2;
			gbc_errDienThoai.gridy = 6;
			contentPanel.add(errDienThoai, gbc_errDienThoai);
		}
		{
			JLabel lblEmail = new JLabel("Email");
			lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblEmail = new GridBagConstraints();
			gbc_lblEmail.anchor = GridBagConstraints.WEST;
			gbc_lblEmail.insets = new Insets(0, 10, 5, 5);
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
			errEmail.setFont(new Font("Segoe UI", Font.ITALIC, 12));
			GridBagConstraints gbc_errEmail = new GridBagConstraints();
			gbc_errEmail.anchor = GridBagConstraints.WEST;
			gbc_errEmail.insets = new Insets(0, 10, 5, 10);
			gbc_errEmail.gridx = 2;
			gbc_errEmail.gridy = 8;
			contentPanel.add(errEmail, gbc_errEmail);
		}
		{
			JLabel lblTrangThai = new JLabel("Trạng thái");
			lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblTrangThai = new GridBagConstraints();
			gbc_lblTrangThai.anchor = GridBagConstraints.WEST;
			gbc_lblTrangThai.insets = new Insets(0, 10, 5, 5);
			gbc_lblTrangThai.gridx = 0;
			gbc_lblTrangThai.gridy = 9;
			contentPanel.add(lblTrangThai, gbc_lblTrangThai);
		}
		{
			cboTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_cboTrangThai = new GridBagConstraints();
			gbc_cboTrangThai.insets = new Insets(0, 0, 5, 5);
			gbc_cboTrangThai.fill = GridBagConstraints.HORIZONTAL;
			gbc_cboTrangThai.gridx = 2;
			gbc_cboTrangThai.gridy = 9;
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
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 11;
			contentPanel.add(panel, gbc_panel);
			FlowLayout fl_panel = new FlowLayout(FlowLayout.RIGHT, 5, 5);
			fl_panel.setAlignOnBaseline(true);
			panel.setLayout(fl_panel);
			{
				btnSubmit = new JButton("Thêm");
				btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnSubmit.setBackground(new Color(30, 144, 255));
				btnSubmit.setForeground(new Color(255, 255, 255));
				btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
				btnSubmit.setContentAreaFilled(false);
				btnSubmit.setOpaque(true);
				btnSubmit.addActionListener(e->submitForm());
				panel.add(btnSubmit);
			}
			{
				btnCancel = new JButton("Hủy");
				btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnCancel.setBackground(new Color(255, 0, 0));
				btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
				btnCancel.setForeground(new Color(255, 255, 255));
				btnCancel.setContentAreaFilled(false);
				btnCancel.setOpaque(true);
				btnCancel.addActionListener(e->cancel());
				panel.add(btnCancel);
			}
		}
	}

	public void showAdd() {		
		// reset action button
		cboTrangThai.setEnabled(false);

		btnSubmit.setText("Thêm");
		btnSubmit.setActionCommand("add");
		setVisible(true);
	}

	public void showEdit(int idNCC) {
		NhaCungCapDTO nhaCungCap = nhaCungCapBus.findByIdNCC(idNCC);
		txtTenNCC.setText(nhaCungCap.getTenNCC());
		txtDiaChi.setText(nhaCungCap.getDiachi());
		txtDienThoai.setText(nhaCungCap.getSdt());
		txtEmail.setText(nhaCungCap.getEmail());
		cboTrangThai.setSelectedItem(nhaCungCap.getTrangthai() == 1 ? "Hoạt động" : "Bị khóa");
		
		// reset action button
		btnSubmit.setText("Cập nhật");
		btnSubmit.setActionCommand("edit_"+idNCC);
		setVisible(true);
	}

	private boolean isError() {
		// remove existed errors
		JLabel[] errors = {errTenNCC, errDiaChi, errDienThoai, errEmail};
		for (JLabel error : errors) {
			error.setText("");
		}

		boolean isError = false;
		// tenTK
		if(txtTenNCC.getText().trim().isEmpty()){
			errTenNCC.setText("Tên không được để trống");
			isError = true;
		}

		// diachi
		if(txtDiaChi.getText().trim().isEmpty()){
			errDiaChi.setText("Địa chỉ không được để trống");
			isError = true;
		}
		
		// dienthoai
		String dienThoaiRegex = "^0\\d{9}$";
		String dienThoai = txtDienThoai.getText();
		if(dienThoai.trim().isEmpty()){
			errDienThoai.setText("Điện thoại không được để trống");
			isError = true;
		}else if(!dienThoai.matches(dienThoaiRegex)) {
			errDienThoai.setText("Điện thoại không đúng định dạng");
			isError = true;
		}
		
		// email
		/* Email có định dạng:
		* 		+ Tên người dùng (có thể bao gồm ._%+-): [a-zA-Z0-9._%+-]
		*		+ Tên miền bao gồm ký tự @ và kèm phần tên miền [a-zA-Z0-9.-]
		* 		+ Kết thúc bằng đuôi .com hay đuôi khác tương tự, tối thiểu 2 ký tự: .[a-zA-Z]{2,}
		* */
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		String email = txtEmail.getText();
		if(email.trim().isEmpty()){
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
			String tenNCC = txtTenNCC.getText();
			String diaChi = txtDiaChi.getText();
			String dienThoai = txtDienThoai.getText();
			String email = txtEmail.getText();
			int trangthai = cboTrangThai.getSelectedItem() == "Hoạt động" ? 1 : 0;

			// action
			String error = "";
			String actionCommand = btnSubmit.getActionCommand();
			int beginIndex = actionCommand.indexOf('_')+1;
			if(beginIndex == 0) {
				// add
				error = nhaCungCapBus.add(new NhaCungCapDTO(tenNCC, diaChi, dienThoai, email, trangthai));
			}else {
				// update
				int idNCC = Integer.parseInt(actionCommand.substring(beginIndex));
				error = nhaCungCapBus.update(new NhaCungCapDTO(idNCC, tenNCC, diaChi, dienThoai, email, trangthai));
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
				else 
					JOptionPane.showMessageDialog(this, "Cập nhật thành công ");
			}
		}
	}

	private void cancel() {
		dispose();
	}

}
