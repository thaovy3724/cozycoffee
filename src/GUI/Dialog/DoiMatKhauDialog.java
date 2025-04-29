package GUI.Dialog;

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import GUI.AdminFrame;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoiMatKhauDialog extends JDialog {

    private AdminFrame adminFrame;
    private TaiKhoanDTO currentUser;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public DoiMatKhauDialog(AdminFrame adminFrame) {
        this.adminFrame = adminFrame;
        this.currentUser = this.adminFrame.getCurrentUser();
        initComponents();
    }

    private void initComponents() {
        setTitle("Đổi mật khẩu");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(adminFrame);

        // Tạo panel chứa các trường nhập liệu
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Mật khẩu mới:"), gbc);
        gbc.gridx = 1;
        panel.add(newPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Xác nhận mật khẩu:"), gbc);
        gbc.gridx = 1;
        panel.add(confirmPasswordField, gbc);

        // Thêm panel vào dialog
        add(panel);

        // Thêm nút OK và Cancel
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChangePassword();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, "South");

        setVisible(true);
    }

    private void handleChangePassword() {
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Kiểm tra mật khẩu
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mật khẩu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

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