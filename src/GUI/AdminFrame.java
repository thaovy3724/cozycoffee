package GUI;

import DTO.TaiKhoanDTO;
import GUI.Dialog.DoiMatKhauDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Cursor;

public class AdminFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel menuPanel;
    private JPanel navbarPanel;
    private JPanel dynamicPanel;
    private JToggleButton lastSelectedButton;
    private TaiKhoanDTO currentUser;
    private JLabel tenTkLB;
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 12);

    public TaiKhoanDTO getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(TaiKhoanDTO currentUser) {
        this.currentUser = currentUser;
        tenTkLB.setText(currentUser.getTenTK());
    }

    public void refreshNavbar() {
        getContentPane().remove(navbarPanel);
        navbarPanel = navbarInit();
        getContentPane().add(navbarPanel);
        revalidate();
        repaint();
    }

    public AdminFrame(TaiKhoanDTO currentUser) {
		this.currentUser = currentUser;
		init();
	}

    public void init() {
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 700));
        setMinimumSize(new Dimension(800, 400));
        getContentPane().setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(navbarInit(), BorderLayout.NORTH);

        dynamicPanel = new JPanel(new BorderLayout());
        dynamicPanel.setBackground(new Color(255, 240, 220));
        dynamicPanel.setLayout(new BorderLayout());
        centerPanel.add(dynamicPanel, BorderLayout.CENTER);
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        getContentPane().add(menuInit(), BorderLayout.WEST);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JPanel menuInit() {
        menuPanel = new JPanel();
        menuPanel.setBackground(new Color(240, 187, 120));
        menuPanel.setPreferredSize(new Dimension(220, 0));
        GridBagLayout gbl_menuPanel = new GridBagLayout();
        gbl_menuPanel.rowHeights = new int[]{0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        menuPanel.setLayout(gbl_menuPanel);
        ImageHelper logoIcon = new ImageHelper(200, 120, AdminFrame.class.getResource("/ASSET/Images/logoRmBg.png"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        JLabel logoLB = new JLabel("logo");
        logoLB.setHorizontalAlignment(SwingConstants.CENTER);
        logoLB.setIcon(logoIcon.getScaledImage());
        menuPanel.add(logoLB, gbc);    
                               
        // Button 1: Thống kê
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        JToggleButton btnThongKe = new JToggleButton("Thống kê");
        btnThongKe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnThongKe.setBackground(new Color(250, 250, 210));
        ImageHelper iconThongKe = new ImageHelper(30, 30, AdminFrame.class.getResource("/ASSET/Images/1.png"));
        btnThongKe.setIcon(iconThongKe.getScaledImage());
        btnThongKe.setActionCommand("thongke");
        btnThongKe.setPreferredSize(new Dimension(200, 50));
        
        btnThongKe.setIconTextGap(10);
        // set default
        dynamicPanel.add(new ThongKePanel(), BorderLayout.CENTER);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        toggleBtnInit(btnThongKe);
        menuPanel.add(btnThongKe, gbc);
        
        // Button 2: Hóa đơn
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        JToggleButton btnHoaDon = new JToggleButton("Hóa đơn");
        btnHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnHoaDon.setBackground(new Color(250, 250, 210));
        btnHoaDon.setActionCommand("hoadon");
        btnHoaDon.setPreferredSize(new Dimension(200, 50));
        ImageHelper iconHoaDon = new ImageHelper(30, 30, AdminFrame.class.getResource("/ASSET/Images/2.png"));
        btnHoaDon.setIcon(iconHoaDon.getScaledImage());
        btnHoaDon.setIconTextGap(10);
        toggleBtnInit(btnHoaDon);
        menuPanel.add(btnHoaDon, gbc);

        // Button 4: Sản phẩm
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        JToggleButton btnSanPham = new JToggleButton("Sản phẩm");
        btnSanPham.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSanPham.setBackground(new Color(250, 250, 210));
        btnSanPham.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSanPham.setActionCommand("sanpham");
        btnSanPham.setPreferredSize(new Dimension(200, 50));
        ImageHelper iconSanPham = new ImageHelper(30, 30, AdminFrame.class.getResource("/ASSET/Images/4.png"));
        btnSanPham.setIcon(iconSanPham.getScaledImage());
        toggleBtnInit(btnSanPham);
        menuPanel.add(btnSanPham, gbc);

        // Button 5: Nguyên liệu
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        JToggleButton btnNguyenLieu = new JToggleButton("Nguyên liệu");
        btnNguyenLieu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNguyenLieu.setBackground(new Color(250, 250, 210));
        btnNguyenLieu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnNguyenLieu.setActionCommand("nguyenlieu");
        btnNguyenLieu.setPreferredSize(new Dimension(200, 50));
        ImageHelper iconNguyenLieu = new ImageHelper(30, 30, AdminFrame.class.getResource("/ASSET/Images/5.png"));
        btnNguyenLieu.setIcon(iconNguyenLieu.getScaledImage());
        btnNguyenLieu.setIconTextGap(10);
        toggleBtnInit(btnNguyenLieu);
        menuPanel.add(btnNguyenLieu, gbc);

        // Button 6: Công thức
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        JToggleButton btnCongThuc = new JToggleButton("Công thức");
        btnCongThuc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCongThuc.setBackground(new Color(250, 250, 210));
        btnCongThuc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCongThuc.setActionCommand("congthuc");
        btnCongThuc.setPreferredSize(new Dimension(200, 50));
        ImageHelper iconCongThuc = new ImageHelper(30, 30, AdminFrame.class.getResource("/ASSET/Images/6.png"));
        btnCongThuc.setIcon(iconCongThuc.getScaledImage());
        btnCongThuc.setIconTextGap(10);
        toggleBtnInit(btnCongThuc);
        menuPanel.add(btnCongThuc, gbc);

        // Button 7: Danh mục
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        JToggleButton btnDanhMuc = new JToggleButton("Danh mục");
        btnDanhMuc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDanhMuc.setBackground(new Color(250, 250, 210));
        btnDanhMuc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDanhMuc.setActionCommand("danhmuc");
        btnDanhMuc.setPreferredSize(new Dimension(200, 50));
        ImageHelper iconDanhMuc = new ImageHelper(30, 30, AdminFrame.class.getResource("/ASSET/Images/7.png"));
        btnDanhMuc.setIcon(iconDanhMuc.getScaledImage());
        btnDanhMuc.setIconTextGap(10); 
        toggleBtnInit(btnDanhMuc);
        menuPanel.add(btnDanhMuc, gbc);

        // Button 8: Nhà cung cấp
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 1.0;
        JToggleButton btnNhaCungCap = new JToggleButton("Nhà cung cấp");
        btnNhaCungCap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNhaCungCap.setBackground(new Color(250, 250, 210));
        btnNhaCungCap.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnNhaCungCap.setActionCommand("nhacungcap");
        btnNhaCungCap.setPreferredSize(new Dimension(200, 50));
        ImageHelper iconNhaCungCap = new ImageHelper(30, 30, AdminFrame.class.getResource("/ASSET/Images/8.png"));
        btnNhaCungCap.setIcon(iconNhaCungCap.getScaledImage());
        btnNhaCungCap.setIconTextGap(10);
        toggleBtnInit(btnNhaCungCap);
        menuPanel.add(btnNhaCungCap, gbc);

        // Button 9: Phiếu nhập
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.weightx = 1.0;
        JToggleButton btnPhieuNhap = new JToggleButton("Phiếu nhập");
        btnPhieuNhap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPhieuNhap.setBackground(new Color(250, 250, 210));
        btnPhieuNhap.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnPhieuNhap.setActionCommand("phieunhap");
        btnPhieuNhap.setPreferredSize(new Dimension(200, 50));
        ImageHelper iconPhieuNhap = new ImageHelper(30, 30, AdminFrame.class.getResource("/ASSET/Images/9.png"));
        btnPhieuNhap.setIcon(iconPhieuNhap.getScaledImage()); 
        btnPhieuNhap.setIconTextGap(10);
        toggleBtnInit(btnPhieuNhap);
        menuPanel.add(btnPhieuNhap, gbc);
        
        // Button 10: Tài khoản
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.weightx = 1.0;
        JToggleButton btnTaiKhoan = new JToggleButton("Tài khoản");
        btnTaiKhoan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnTaiKhoan.setBackground(new Color(250, 250, 210));
        btnTaiKhoan.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnTaiKhoan.setActionCommand("taikhoan");
        btnTaiKhoan.setPreferredSize(new Dimension(200, 50));
        ImageHelper iconTaiKhoan = new ImageHelper(30, 30, AdminFrame.class.getResource("/ASSET/Images/10.png"));
        btnTaiKhoan.setIcon(iconTaiKhoan.getScaledImage());
        btnTaiKhoan.setIconTextGap(10);
        toggleBtnInit(btnTaiKhoan);
        menuPanel.add(btnTaiKhoan, gbc);

        return menuPanel;
    }

    public JPanel navbarInit() {
        navbarPanel = new JPanel();
        navbarPanel.setBackground(new Color(240, 187, 120));
        navbarPanel.setPreferredSize(new Dimension(0, 60));
        navbarPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        navbarPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton changePasswordBtn = new JButton("Đổi mật khẩu");
        changePasswordBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        changePasswordBtn.setPreferredSize(new Dimension(130, 35));
        changePasswordBtn.setFont(LABEL_FONT);
        changePasswordBtn.setBackground(Color.WHITE);
        changePasswordBtn.setOpaque(true);
        changePasswordBtn.setBorder(new EmptyBorder(5, 10, 5, 10));
        changePasswordBtn.setFocusPainted(false);
        changePasswordBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                changePasswordBtn.setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                changePasswordBtn.setBackground(Color.WHITE);
            }
        });
        changePasswordBtn.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				// Mở dialog đổi mật khẩu
				new DoiMatKhauDialog(currentUser);
                // nếu chỉ dùng "this" sẽ trỏ đến lớp ActionListener
			}
		});
        navbarPanel.add(changePasswordBtn);

        tenTkLB = new JLabel(currentUser.getTenTK());
        tenTkLB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tenTkLB.setFont(LABEL_FONT);
        tenTkLB.setBackground(Color.WHITE);
        tenTkLB.setOpaque(true);
        tenTkLB.setHorizontalAlignment(SwingConstants.CENTER);
        tenTkLB.setPreferredSize(new Dimension(130, 35));
        tenTkLB.setBorder(new EmptyBorder(5, 10, 5, 10));
        navbarPanel.add(tenTkLB);

        JButton logoutBtn = new JButton("Đăng xuất");
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.setFont(LABEL_FONT);
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setOpaque(true);
        logoutBtn.setBorder(new EmptyBorder(5, 10, 5, 10));
        logoutBtn.setPreferredSize(new Dimension(130, 35));
        logoutBtn.setFocusPainted(false);

        ImageHelper logoutIcon = new ImageHelper(20, 20, AdminFrame.class.getResource("/ASSET/Images/logout.png"));
        logoutBtn.setIcon(logoutIcon.getScaledImage());
        logoutBtn.setIconTextGap(8);

        logoutBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutBtn.setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutBtn.setBackground(Color.WHITE);
            }
        });

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn đăng xuất không?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION
        );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new DangNhapFrame().setVisible(true);
            }
        });
        navbarPanel.add(logoutBtn);

        return navbarPanel;
    }

    public void toggleBtnInit(JToggleButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setOpaque(true);

        btn.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	 btn.setForeground(Color.BLACK);
                    if (lastSelectedButton != null && lastSelectedButton != btn) {
                    	  lastSelectedButton.setSelected(false);	  
                    }
                    
                    lastSelectedButton = btn;
                    replaceDynamicPanel(btn.getActionCommand());
                } else {
         
                    btn.setForeground(Color.BLACK);
                }
            }
        });
    }

    private void replaceDynamicPanel(String panelType) {
        dynamicPanel.removeAll();
        JPanel selectedPanel = new JPanel();

        switch (panelType) {
            case "thongke":
                selectedPanel = new ThongKePanel(); // Thay bằng panel Thống kê
                break;
            case "hoadon":
                selectedPanel = new HoaDonPanel(); // Thay bằng panel Hóa đơn
                break;
            case "sanpham":
                selectedPanel = new SanPhamPanel(); // Thay bằng panel Sản phẩm
                break;
            case "nguyenlieu":
                selectedPanel = new NguyenLieuPanel(); // Thay bằng panel Nguyên liệu
                break;
            case "congthuc":
                selectedPanel = new CongThucPanel(currentUser); // Thay bằng panel Công thức
                break;
            case "danhmuc":
                selectedPanel = new DanhMucPanel();
                break;
            case "nhacungcap":
                selectedPanel = new NhaCungCapPanel(); // Thay bằng panel Nhà cung cấp
                break;
            case "phieunhap":
                selectedPanel = new PhieuNhapPanel(currentUser); // Thay bằng panel Phiếu nhập
                break;
           case "taikhoan":
               // Truyền AdminFrame vào TaiKhoanPanel
				selectedPanel = new TaiKhoanPanel(this); // Hiển thị TaiKhoanPanel
               break;
        }

        dynamicPanel.add(selectedPanel, BorderLayout.CENTER);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }
}