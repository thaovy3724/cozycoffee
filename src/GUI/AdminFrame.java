package GUI;

import DTO.TaiKhoanDTO;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import java.awt.Color;
import java.awt.Dimension;

public class AdminFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel menuPanel;
    private JPanel navbarPanel;
    private JPanel dynamicPanel;
    private JToggleButton lastSelectedButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AdminFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    
    // Thuộc tính currentUser - người dùng đang đăng nhập hiện tại trên adminFrame
    private TaiKhoanDTO currentUser;

    // Getter cho currentUser
    public TaiKhoanDTO getCurrentUser() {
        return currentUser;
    }

    // Setter cho currentUser
    public void setCurrentUser(TaiKhoanDTO currentUser) {
        this.currentUser = currentUser;
    }
    
    // Refresh navbar
    public void refreshNavbar() {
        getContentPane().remove(navbarPanel); // Bỏ navbar cũ
        navbarPanel = navbarInit(); // Tạo lại navbar với thông tin mới
        getContentPane().add(navbarPanel);
        revalidate();
        repaint();
    }

    public AdminFrame() {
        init();
    }
    
    public void init() {
        // AdminFrameGUI init
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1100, 600));
        setMinimumSize(new Dimension(800, 400));
        getContentPane().setLayout(new BorderLayout());
        
        // Menu init
        getContentPane().add(menuInit(), BorderLayout.WEST);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        // Navbar init
        centerPanel.add(navbarInit(), BorderLayout.NORTH);
        
        // Dynamic panel
        dynamicPanel = new JPanel(new BorderLayout());
        dynamicPanel.setBackground(new Color(245, 245, 245));
        dynamicPanel.setLayout(new BorderLayout());
        centerPanel.add(dynamicPanel, BorderLayout.CENTER);
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public JPanel menuInit() {
        menuPanel = new JPanel();
        menuPanel.setBackground(new Color(255, 255, 255));
        menuPanel.setPreferredSize(new Dimension(220, 0)); // Tăng chiều rộng sidebar
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Tăng khoảng cách dọc và ngang
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
                
        JLabel logoLB = new JLabel("");
        logoLB.setHorizontalAlignment(SwingConstants.CENTER);
        ImageHelper logoIcon = new ImageHelper(200, 120, AdminFrame.class.getResource("/ASSET/Images/logoRmBg.png"));
        logoLB.setIcon(logoIcon.getScaledImage());
        menuPanel.add(logoLB, gbc);
        
        gbc.gridy++;
        JToggleButton thongkeBtn = new JToggleButton("Thống kê");
        thongkeBtn.setActionCommand("thongke");
        thongkeBtn.setPreferredSize(new Dimension(200, 50)); // Tăng kích thước nút
        toggleBtnInit(thongkeBtn);
        menuPanel.add(thongkeBtn, gbc);
        
        gbc.gridy++;
        JToggleButton hoadonBtn = new JToggleButton("Hóa đơn");
        hoadonBtn.setActionCommand("hoadon");
        hoadonBtn.setPreferredSize(new Dimension(200, 50));
        toggleBtnInit(hoadonBtn);
        menuPanel.add(hoadonBtn, gbc);
        
        gbc.gridy++;
        JToggleButton sanphamBtn = new JToggleButton("Sản phẩm");
        sanphamBtn.setActionCommand("sanpham");
        sanphamBtn.setPreferredSize(new Dimension(200, 50));
        toggleBtnInit(sanphamBtn);
        menuPanel.add(sanphamBtn, gbc);
        
        gbc.gridy++;
        JToggleButton nguyenlieuBtn = new JToggleButton("Nguyên liệu");
        nguyenlieuBtn.setActionCommand("nguyenlieu");
        nguyenlieuBtn.setPreferredSize(new Dimension(200, 50));
        toggleBtnInit(nguyenlieuBtn);
        menuPanel.add(nguyenlieuBtn, gbc);
        
        gbc.gridy++;
        JToggleButton congthucBtn = new JToggleButton("Công thức");
        congthucBtn.setActionCommand("congthuc");
        congthucBtn.setPreferredSize(new Dimension(200, 50));
        toggleBtnInit(congthucBtn);
        menuPanel.add(congthucBtn, gbc);
        
        gbc.gridy++;
        JToggleButton danhmucBtn = new JToggleButton("Danh mục");
        danhmucBtn.setActionCommand("danhmuc");
        danhmucBtn.setPreferredSize(new Dimension(200, 50));
        toggleBtnInit(danhmucBtn);
        menuPanel.add(danhmucBtn, gbc);
        
        gbc.gridy++;
        JToggleButton nhacungcapBtn = new JToggleButton("Nhà cung cấp");
        nhacungcapBtn.setActionCommand("nhacungcap");
        nhacungcapBtn.setPreferredSize(new Dimension(200, 50));
        toggleBtnInit(nhacungcapBtn);
        menuPanel.add(nhacungcapBtn, gbc);
        
        gbc.gridy++;
        JToggleButton phieunhapBtn = new JToggleButton("Phiếu nhập");
        phieunhapBtn.setActionCommand("phieunhap");
        phieunhapBtn.setPreferredSize(new Dimension(200, 50));
        toggleBtnInit(phieunhapBtn);
        menuPanel.add(phieunhapBtn, gbc);
        
        gbc.gridy++;
        JToggleButton taikhoanBtn = new JToggleButton("Tài khoản");
        taikhoanBtn.setActionCommand("taikhoan");
        taikhoanBtn.setPreferredSize(new Dimension(200, 50));
        toggleBtnInit(taikhoanBtn);
        menuPanel.add(taikhoanBtn, gbc);
        
        gbc.gridy++;
        gbc.weighty = 1.0;
        menuPanel.add(new JPanel(), gbc);
        return menuPanel;
    }
    
    public JPanel navbarInit() {
        navbarPanel = new JPanel();
        navbarPanel.setBackground(new Color(245, 245, 245));
        navbarPanel.setPreferredSize(new Dimension(0, 50));
        navbarPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        
        JLabel tenTkLB = new JLabel("Thảo Vy");
        tenTkLB.setBackground(new Color(245, 245, 245));
        tenTkLB.setOpaque(true); 
        tenTkLB.setFont(new Font("Tahoma", Font.BOLD, 12));
        tenTkLB.setHorizontalAlignment(SwingConstants.TRAILING);
        tenTkLB.setPreferredSize(new Dimension(100, 30));
        navbarPanel.add(tenTkLB);
        
        JButton logoutBtn = new JButton("Đăng xuất");
        logoutBtn.setBackground(new Color(245, 245, 245));
        ImageHelper logoutIcon = new ImageHelper(20, 20, AdminFrame.class.getResource("/ASSET/Images/logout.png"));
        logoutBtn.setIcon(logoutIcon.getScaledImage());
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setOpaque(true);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setPreferredSize(new Dimension(100, 30));
        
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = showOptionDialog(
                        "Xác nhận đăng xuất",
                        "Bạn có chắc chắn muốn đăng xuất không?"
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    new DangNhapFrame().setVisible(true);
                    JOptionPane.showMessageDialog(null, "Đăng xuất thành công");
                }
            }
        });
        navbarPanel.add(logoutBtn);
        
        return navbarPanel;
    }
    
    public void toggleBtnInit(JToggleButton btn) {
        btn.setFont(new Font("Tahoma", Font.BOLD, 14)); // Tăng kích thước font
        btn.setBackground(new Color(255, 192, 203));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        
        btn.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (lastSelectedButton != null && lastSelectedButton != btn) {
                        lastSelectedButton.setSelected(false);
                    }
                    lastSelectedButton = btn;
                    replaceDynamicPanel(btn.getActionCommand());
                }
            }
        });
    }
    
    private void replaceDynamicPanel(String panelType) {
        dynamicPanel.removeAll();
        JPanel selectedPanel = new JPanel();

        switch (panelType) {
            case "thongke":
                selectedPanel = new JPanel();
                break;
            case "hoadon":
                selectedPanel = new JPanel();
                break;
            case "sanpham":
                selectedPanel = new JPanel();
                break;
            case "nguyenlieu":
                selectedPanel = new NguyenLieuPanel();
                break;
            case "congthuc":
                selectedPanel = new CongThucPanel();
                break;
            case "danhmuc":
                selectedPanel = new DanhMucPanel();
                break;
            case "nhacungcap":
                selectedPanel = new NhaCungCapPanel();
                break;
            case "phieunhap":
                selectedPanel = new JPanel();
                break;
            case "taikhoan":
                selectedPanel = new TaiKhoanPanel();
                break;
            default:
                selectedPanel = new JPanel();
                System.out.println("Default panel created");
                break;
        }

        dynamicPanel.add(selectedPanel, BorderLayout.CENTER);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private int showOptionDialog(String title, String message) {
        int confirm = JOptionPane.showConfirmDialog(
                AdminFrame.this,
                message,
                title,
                JOptionPane.YES_NO_OPTION
        );
        return confirm;
    }
}