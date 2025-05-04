package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;

public class DangNhapFrame extends JFrame {
    private final TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
    private final JTextField textTenTK;
    private final JPasswordField passwordField;
    private final JButton btnLogin;

    public DangNhapFrame() {
        getContentPane().setBackground(new Color(255, 228, 181));
        // Cấu hình JFrame
        setTitle("Login");
        setSize(800, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        // Icon (JLabel)
        JLabel lblIcon = new JLabel("");
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblIcon.setBounds(0, 0, 389, 463);
        // Thêm hình ảnh (thay đường dẫn bằng file ảnh của bạn)
        ImageHelper img = new ImageHelper(400, 465, DangNhapFrame.class.getResource("/ASSET/Images/logoBg.png"));
        lblIcon.setIcon(img.getScaledImage());
        getContentPane().add(lblIcon);

        // Form đăng nhập (JPanel)
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 228, 181));
        panel.setBounds(409, 28, 353, 392);
        panel.setLayout(null);
        getContentPane().add(panel);

        // label tenTK
        JLabel lblUsername_1 = new JLabel("Tên tài khoản");
        lblUsername_1.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
        lblUsername_1.setBounds(20, 119, 94, 25);
        panel.add(lblUsername_1);

        // tenTK field
        textTenTK = new JTextField();
        textTenTK.setBounds(20, 154, 323, 41);
        panel.add(textTenTK);

        // label matkhau
        JLabel lblMatKhau = new JLabel("Mật khẩu");
        lblMatKhau.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
        lblMatKhau.setBounds(20, 205, 80, 25);
        panel.add(lblMatKhau);

        // password field
        passwordField = new JPasswordField();
        passwordField.setBounds(20, 240, 323, 41);
        panel.add(passwordField);

        // Nút Login
        btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLogin.setForeground(new Color(255, 255, 255, 240));
        btnLogin.setBackground(new Color(139, 69, 19));
        btnLogin.setBounds(221, 326, 122, 41);
        panel.add(btnLogin);

        // Tiêu đề "HELLO CAFÉ"
        JLabel lblFormHeader = new JLabel("HELLO CAFÉ");
        lblFormHeader.setIcon(new ImageIcon(DangNhapFrame.class.getResource("/ASSET/Images/logo.png")));
        lblFormHeader.setBounds(46, 0, 307, 109);
        panel.add(lblFormHeader);
        lblFormHeader.setVerticalAlignment(SwingConstants.TOP);
        lblFormHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFormHeader.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 30));

        // Sự kiện cho nút Login
        btnLogin.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                String tenTK = textTenTK.getText().trim();
                String matkhau = new String(passwordField.getPassword()).trim();
                StringBuilder message = new StringBuilder();
                if (!tenTK.isEmpty() && !matkhau.isEmpty()) {
                    TaiKhoanBUS.LoginResult loginResult = taiKhoanBUS.checkLogin(tenTK, matkhau);
                    TaiKhoanDTO tk = loginResult.getTaiKhoan();
                    String msg = loginResult.getMessage();
                    if (tk != null) {
                        // Đóng frame đăng nhập và mở AdminFrame hoặc StaffFrame
                        dispose();
                        if (tk.getIdNQ() == 1) {
                            // Lưu tài khoản vào phiên làm việc hiện tại của AdminFrame
                            new AdminFrame(tk).setVisible(true);
                        } else if (tk.getIdNQ() == 2) {
                            // Lưu tài khoản vào phiên làm việc hiện tại của StaffFrame
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

        // Thêm sự kiện nhấn Enter cho textTenTK và passwordField
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnLogin.doClick(); // Kích hoạt nút Login khi nhấn Enter
                }
            }
        };
        textTenTK.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
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