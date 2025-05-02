package GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;

import java.awt.Color;
import java.awt.Cursor;

public class DoiMatKhauDialog extends JDialog {

    private TaiKhoanDTO currentUser;
    
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPasswordField txtNewPwd, txtConfirmPwd;
	private JLabel errNewPwd, errConfirmPwd;
	private JButton btnSubmit, btnCancel;

	/**
	 * Create the dialog.
	 */
	public DoiMatKhauDialog(TaiKhoanDTO currentUser) {
		this.currentUser = currentUser;
        setTitle("Đổi mật khẩu");
		setSize(442, 168);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblTitle = new JLabel("Đổi mật khẩu");
			lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
	        lblTitle.setForeground(new Color(33, 150, 243));
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.anchor = GridBagConstraints.WEST;
			gbc_lblTitle.gridwidth = 4;
			gbc_lblTitle.insets = new Insets(5, 10, 5, 10);
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
	
	private void textFieldInit() {
		{
			JLabel lblNewPwd = new JLabel("Mật khẩu mới");
			lblNewPwd.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblNewPwd = new GridBagConstraints();
			gbc_lblNewPwd.insets = new Insets(0, 10, 5, 5);
			gbc_lblNewPwd.anchor = GridBagConstraints.WEST;
			gbc_lblNewPwd.gridx = 0;
			gbc_lblNewPwd.gridy = 1;
			contentPanel.add(lblNewPwd, gbc_lblNewPwd);
		}
		{
			txtNewPwd = new JPasswordField(20);
			txtNewPwd.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_txtNewPwd = new GridBagConstraints();
			gbc_txtNewPwd.gridwidth = 2;
			gbc_txtNewPwd.insets = new Insets(0, 0, 5, 10);
			gbc_txtNewPwd.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtNewPwd.gridx = 1;
			gbc_txtNewPwd.gridy = 1;
			contentPanel.add(txtNewPwd, gbc_txtNewPwd);
			txtNewPwd.setColumns(10);
		}
		{
			errNewPwd = new JLabel();
			errNewPwd.setForeground(Color.RED);
			errNewPwd.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_errNewPwd = new GridBagConstraints();
			gbc_errNewPwd.gridwidth = 2;
			gbc_errNewPwd.anchor = GridBagConstraints.WEST;
			gbc_errNewPwd.insets = new Insets(0, 0, 5, 10);
			gbc_errNewPwd.gridx = 1;
			gbc_errNewPwd.gridy = 2;
			contentPanel.add(errNewPwd, gbc_errNewPwd);
		}
		{
			JLabel lblConfirmPwd = new JLabel("Mật khẩu xác nhận");
			lblConfirmPwd.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblConfirmPwd = new GridBagConstraints();
			gbc_lblConfirmPwd.anchor = GridBagConstraints.EAST;
			gbc_lblConfirmPwd.insets = new Insets(0, 10, 5, 5);
			gbc_lblConfirmPwd.gridx = 0;
			gbc_lblConfirmPwd.gridy = 3;
			contentPanel.add(lblConfirmPwd, gbc_lblConfirmPwd);
		}
		{
			txtConfirmPwd = new JPasswordField(20);
			txtConfirmPwd.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			txtConfirmPwd.setColumns(10);
			GridBagConstraints gbc_txtConfirmPwd = new GridBagConstraints();
			gbc_txtConfirmPwd.gridwidth = 2;
			gbc_txtConfirmPwd.insets = new Insets(0, 0, 5, 10);
			gbc_txtConfirmPwd.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtConfirmPwd.gridx = 1;
			gbc_txtConfirmPwd.gridy = 3;
			contentPanel.add(txtConfirmPwd, gbc_txtConfirmPwd);
		}
		{
			errConfirmPwd = new JLabel();
			errConfirmPwd.setForeground(Color.RED);
			errConfirmPwd.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_errConfirmPwd = new GridBagConstraints();
			gbc_errConfirmPwd.gridwidth = 2;
			gbc_errConfirmPwd.anchor = GridBagConstraints.WEST;
			gbc_errConfirmPwd.insets = new Insets(0, 0, 5, 10);
			gbc_errConfirmPwd.gridx = 1;
			gbc_errConfirmPwd.gridy = 4;
			contentPanel.add(errConfirmPwd, gbc_errConfirmPwd);
		}
	}

	private void actionInit() {
        JPanel actionPanel = new JPanel();
		actionPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_actionPanel = new GridBagConstraints();
		gbc_actionPanel.insets = new Insets(0, 0, 5, 5);
		gbc_actionPanel.gridwidth = 2;
		gbc_actionPanel.fill = GridBagConstraints.BOTH;
		gbc_actionPanel.gridx = 1;
		gbc_actionPanel.gridy = 5;
		contentPanel.add(actionPanel, gbc_actionPanel);
		actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			btnSubmit = new JButton("Thêm");
			btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnSubmit.setBackground(new Color(0, 128, 0));
			btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
			btnSubmit.setForeground(Color.WHITE);
			btnSubmit.setContentAreaFilled(false);
			btnSubmit.setOpaque(true);
			btnSubmit.addActionListener(e->handleChangePassword());
			actionPanel.add(btnSubmit);
		}
		{
			btnCancel = new JButton("Hủy");
			btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnCancel.setBackground(new Color(255, 51, 102));
			btnCancel.setForeground(new Color(255, 255, 255));
			btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
			btnCancel.setContentAreaFilled(false);
			btnCancel.setOpaque(true);
			btnCancel.addActionListener(e->dispose());
			actionPanel.add(btnCancel);
		}
	}

    private boolean isError(){
        // remove existed errors
        errNewPwd.setText("");
        errConfirmPwd.setText("");

        boolean isError = false;

		String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
		char[] newPwd = txtNewPwd.getPassword();
		String newPwdStr = newPwd.toString();
		if(newPwdStr.isEmpty()){
			errNewPwd.setText("Mật khẩu không được để trống");
			isError = true;
		}else if(!newPwdStr.matches(pwdRegex)){
			errNewPwd.setText("Mật khẩu phải có tối thiểu 8 ký tự, gồm 1 ký tự thường, 1 ký tự in hoa, 1 ký tự số");
			isError = true;
		}

		// PwdConfirm
		char[] pwdConfirm = txtConfirmPwd.getPassword();
		String pwdConfirmStr = pwdConfirm.toString();
		if(pwdConfirmStr.isEmpty()){
			errConfirmPwd.setText("Mật khẩu xác nhận không được để trống");
			isError = true;
		}else if(!pwdConfirmStr.equals(newPwdStr)){
			errConfirmPwd.setText("Mật khẩu xác nhận không chính xác");
			isError = true;
		}
		return isError;
    }

	private void handleChangePassword() {
        String newPassword = new String(txtNewPwd.getPassword());

		if(!isError()){
			// Cập nhật mật khẩu
			TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
			String hashedPassword = taiKhoanBUS.hashPassword(newPassword);
			currentUser.setMatkhau(hashedPassword);
			String error = taiKhoanBUS.updatePassword(currentUser);
			if (error.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, error, "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		}
    }
}