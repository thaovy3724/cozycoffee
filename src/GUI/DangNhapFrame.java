package GUI;

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

/*
 * Do ở frame đăng nhập tạm thời có một tính năng nên tui để actionListener trong cái khởi tạo luôn nha
 * */

public class DangNhapFrame extends JFrame {
	private TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
	private JTextField textTenTK;
	private JPasswordField passwordField;

	public DangNhapFrame() {
		// Cấu hình JFrame
		setTitle("Login");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		// Icon (JLabel)
		JLabel lblIcon = new JLabel("");
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblIcon.setBounds(50, 75, 350, 350);
		// Thêm hình ảnh (thay đường dẫn bằng file ảnh của bạn)
		ImageHelper img = new ImageHelper(400, 400, DangNhapFrame.class.getResource("/ASSET/Images/logoBg.png"));
		lblIcon.setIcon(img.getScaledImage());
		getContentPane().add(lblIcon);

		// Form đăng nhập (JPanel)
		JPanel panel = new JPanel();
		panel.setBounds(450, 125, 300, 250);
		panel.setLayout(null);
		getContentPane().add(panel);

		// Tiêu đề "Xin chào"
		JLabel lblFormHeader = new JLabel("XIN CHÀO");
		lblFormHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblFormHeader.setFont(new Font("Inter", Font.BOLD, 14));
		lblFormHeader.setBounds(115, 10, 80, 25);
		panel.add(lblFormHeader);

		// label tenTK
		JLabel lblUsername_1 = new JLabel("Tên tài khoản");
		lblUsername_1.setBounds(20, 50, 80, 25);
		panel.add(lblUsername_1);

		// tenTK field
		textTenTK = new JTextField();
		textTenTK.setBounds(100, 50, 180, 25);
		panel.add(textTenTK);

		// label matkhau
		JLabel lblMatKhau = new JLabel("Mật khẩu");
		lblMatKhau.setBounds(20, 100, 80, 25);
		panel.add(lblMatKhau);

		//password field
		passwordField = new JPasswordField();
		passwordField.setBounds(100, 100, 180, 25);
		panel.add(passwordField);

		// Nút Login
		JButton btnLogin = new JButton("ĐĂNG NHẬP");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(128, 128, 255));
		btnLogin.setBounds(30, 175, 240, 60);
		panel.add(btnLogin);

		// Sự kiện cho nút Login
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tenTK = textTenTK.getText().trim();
				String matkhau = new String(passwordField.getPassword()).trim();
				StringBuilder message = new StringBuilder();
				if (!tenTK.isEmpty() && !matkhau.isEmpty()) {
					TaiKhoanBUS.LoginResult loginResult = taiKhoanBUS.checkLogin(tenTK, matkhau);
					TaiKhoanDTO tk = loginResult.getTaiKhoan();
					String msg = loginResult.getMessage();
					if(tk != null) {
						// Đóng frame đăng nhập và mở AdminFrame
						dispose();
						
						if(tk.getIdNQ() == 1){
							//Lưu tài khoản vào phiên làm việc hiện tại của AdminFrame
							new AdminFrame(tk).setVisible(true);
						}else if(tk.getIdNQ() == 2){
							//Lưu tài khoản vào phiên làm việc hiện tại của AdminFrame
							new StaffFrame(tk).setVisible(true);
						}
					} else {
						JOptionPane.showMessageDialog(null, msg);
					}
				} else {
					if (tenTK.isEmpty()) {
						message.append("Vui lòng nhập tên tài khoản \n");
					}
					if (matkhau.isEmpty()) {
						message.append("Vui lòng nhập mật khẩu \n");
					}

					JOptionPane.showMessageDialog(null, message.toString());
				}
			}
		});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DangNhapFrame frame = new DangNhapFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}