package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Font;

public class DangNhapFrame extends JFrame {
	private JTextField textField;
	private JPasswordField passwordField;

	public DangNhapFrame() {
		// Cấu hình JFrame
		setTitle("Login");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		// Icon (JLabel)
		JLabel lblIcon = new JLabel("");
		lblIcon.setBounds(50, 75, 350, 350);
		// Thêm hình ảnh (thay đường dẫn bằng file ảnh của bạn)
		lblIcon.setIcon(new ImageIcon("C:\\Users\\ACER\\eclipse-workspace\\QuanLyQuanCaPhe\\src\\img\\LogoDangNhap.jpg"));
		getContentPane().add(lblIcon);

		// Form đăng nhập (JPanel)
		JPanel panel = new JPanel();
		panel.setBounds(450, 125, 300, 250);
		panel.setLayout(null);
		getContentPane().add(panel);

		// Username Label và TextField
		JLabel lblUsername = new JLabel("XIN CHÀO");
		lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblUsername.setFont(new Font("Inter", Font.BOLD, 14));
		lblUsername.setBounds(115, 10, 80, 25);
		panel.add(lblUsername);

		textField = new JTextField();
		textField.setBounds(100, 50, 180, 25);
		panel.add(textField);

		// Password Label và PasswordField
		JLabel lblPassword = new JLabel("Mật khẩu");
		lblPassword.setBounds(20, 100, 80, 25);
		panel.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(100, 100, 180, 25);
		panel.add(passwordField);

		// Nút Login
		JButton btnLogin = new JButton("ĐĂNG NHẬP");
		btnLogin.setBounds(30, 175, 240, 60);
		panel.add(btnLogin);

		JLabel lblUsername_1 = new JLabel("Tên tài khoản");
		lblUsername_1.setBounds(20, 50, 80, 25);
		panel.add(lblUsername_1);

		// Sự kiện cho nút Login
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Login clicked!");
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