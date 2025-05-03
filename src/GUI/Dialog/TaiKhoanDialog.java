package GUI.Dialog;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import BUS.TaiKhoanBUS;
import BUS.NhomQuyenBUS;
import DTO.DanhMucDTO;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;
import GUI.AdminFrame;
import java.util.List;

public class TaiKhoanDialog extends JDialog {
    private TaiKhoanBUS taiKhoanBus = new TaiKhoanBUS();
    private NhomQuyenBUS nhomQuyenBus = new NhomQuyenBUS();
    private AdminFrame adminFrame;

    private static final long serialVersionUID = 1L;
    private JPanel contentPanel;
    private JLabel lblTitle;
    private JTextField txtTenTK, txtEmail;
    private JPasswordField txtMatKhau, txtMatKhauConfirm;
    private JComboBox<String> cboTrangThai;
    private JComboBox<NhomQuyenDTO> cboQuyen;
    private JButton btnSubmit, btnCancel;
    private boolean isEditMode;
    private int editId;

    public TaiKhoanDialog(AdminFrame adminFrame) {
        super(adminFrame, true);
        this.adminFrame = adminFrame;

        setTitle("Thêm tài khoản");
        setPreferredSize(new Dimension(450, 350));
        getContentPane().setLayout(new BorderLayout());

        // Content panel
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPanel.setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Title
        lblTitle = new JLabel("Thêm tài khoản");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 150, 243));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(lblTitle, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        contentPanel.add(formPanel, BorderLayout.CENTER);

        // Initialize fields
        txtTenTK = new JTextField(20);
        txtMatKhau = new JPasswordField(20);
        txtMatKhauConfirm = new JPasswordField(20);
        txtEmail = new JTextField(20);
        cboQuyen = new JComboBox<>();
        cboTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Bị khóa"});

        // Add components to form panel
        formPanel.add(new JLabel("Tên tài khoản:"));
        formPanel.add(txtTenTK);
        formPanel.add(new JLabel("Mật khẩu:"));
        formPanel.add(txtMatKhau);
        formPanel.add(new JLabel("Mật khẩu xác nhận:"));
        formPanel.add(txtMatKhauConfirm);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("Quyền:"));
        formPanel.add(cboQuyen);
        formPanel.add(new JLabel("Trạng thái:"));
        formPanel.add(cboTrangThai);

        // Load danh sách quyền
        loadCboNhomQuyen();

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        btnSubmit = new JButton("Thêm");
        btnCancel = new JButton("Hủy");
        btnSubmit.setBackground(new Color(30, 144, 255));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBackground(new Color(255, 0, 0));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnCancel);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Button events
        btnSubmit.addActionListener(e -> submitForm());
        btnCancel.addActionListener(e -> cancel());

		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
    }

    private void loadCboNhomQuyen() {
        List<NhomQuyenDTO> arr = nhomQuyenBus.getAll();
        for (NhomQuyenDTO item : arr) {
            cboQuyen.addItem(item);
        }
    }

    public void showAdd() {
        isEditMode = false;
        setTitle("Thêm tài khoản");
        lblTitle.setText("Thêm tài khoản");
        btnSubmit.setText("Thêm");
        btnSubmit.setActionCommand("add");
        cboTrangThai.setEnabled(false);
        cboTrangThai.setSelectedItem("Hoạt động");
        // txtMatKhau.setVisible(true);
        // txtMatKhauConfirm.setVisible(true);
        // clearFields();
        // contentPanel.revalidate();
        // contentPanel.repaint();
        // setLocationRelativeTo(null); // Căn giữa khi hiển thị
        setVisible(true);
    }

    public void showEdit(int idTK) {
        isEditMode = true;
        editId = idTK;
        setTitle("Cập nhật thông tin tài khoản");
        lblTitle.setText("Cập nhật thông tin tài khoản");
        btnSubmit.setText("Cập nhật");
        btnSubmit.setActionCommand("edit_" + idTK);
        TaiKhoanDTO taiKhoan = taiKhoanBus.findByIdTK(idTK);
        txtTenTK.setText(taiKhoan.getTenTK());
        txtEmail.setText(taiKhoan.getEmail());
		
        // set default
		if(taiKhoan.getIdNQ() != 0) {
			for (int i = 0; i < cboQuyen.getItemCount(); i++) {
                NhomQuyenDTO nq = cboQuyen.getItemAt(i);
                if (nq.getIdNQ() == taiKhoan.getIdNQ()) {
                    cboQuyen.setSelectedIndex(i);
                    break;
                }
            }
		}
        cboTrangThai.setSelectedItem(taiKhoan.getTrangthai() == 1 ? "Hoạt động" : "Bị khóa");
        cboTrangThai.setEnabled(true);
		txtMatKhau.setEnabled(false);
		txtMatKhauConfirm.setEnabled(false);
        // txtMatKhau.setVisible(true);
        // txtMatKhauConfirm.setVisible(true);
        // clearFields();
        // contentPanel.revalidate();
        // setLocationRelativeTo(null); // Căn giữa khi hiển thị
        // contentPanel.repaint();
        setVisible(true);
    }

    // private void clearFields() {
    //     if (!isEditMode) {
    //         txtMatKhau.setText("");
    //         txtMatKhauConfirm.setText("");
    //     }
    //     txtTenTK.setText("");
    //     txtEmail.setText("");
    //     cboQuyen.setSelectedIndex(0);
    //     cboTrangThai.setSelectedIndex(0);
    // }

    private boolean isError() {
        if (txtTenTK.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return true;
        }

        if (!isEditMode) {
            String matKhau = new String(txtMatKhau.getPassword());
            String matKhauConfirm = new String(txtMatKhauConfirm.getPassword());
            String matKhauRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
            if (matKhau.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return true;
            } else if (!matKhau.matches(matKhauRegex)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu cần 8+ ký tự, gồm chữ hoa, thường, số", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return true;
            }
            if (matKhauConfirm.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return true;
            } else if (!matKhauConfirm.equals(matKhau)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không chính xác", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return true;
            }
        }

        String email = txtEmail.getText();
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(this, "Email không đúng định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return true;
        }

        return false;
    }

    private void submitForm() {
        if (!isError()) {
            String tenTK = txtTenTK.getText().trim();
            String email = txtEmail.getText().trim();
            String matKhau = "";
            if (!isEditMode) {
                matKhau = taiKhoanBus.hashPassword(new String(txtMatKhau.getPassword()).trim());
            }
            NhomQuyenDTO nhomQuyenSelected = (NhomQuyenDTO) cboQuyen.getSelectedItem();
            int idNQ = nhomQuyenSelected.getIdNQ();
            int trangthai = cboTrangThai.getSelectedItem().equals("Hoạt động") ? 1 : 0;
			System.out.print(idNQ);
            TaiKhoanDTO taiKhoan = new TaiKhoanDTO(tenTK, matKhau, email, trangthai, idNQ);
            String error = "";
            if (isEditMode) {
                taiKhoan.setIdTK(editId);
                error = taiKhoanBus.updateInfo(taiKhoan);
            } else {
                error = taiKhoanBus.add(taiKhoan);
            }

            if (error != null && !error.isEmpty()) {
                JOptionPane.showMessageDialog(this, error, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, isEditMode ? "Cập nhật thành công" : "Thêm thành công");
                if (isEditMode && taiKhoan.getIdTK() == adminFrame.getCurrentUser().getIdTK()) {
                    adminFrame.setCurrentUser(taiKhoan);
                }
                dispose();
            }
        }
    }

    private void cancel() {
        dispose();
    }
}