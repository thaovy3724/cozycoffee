package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel menuPanel;
    private JPanel navbarPanel;
    private JPanel dynamicPanel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminFrame frame = new AdminFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public AdminFrame() {
        init();
    }

    public void init() {
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1100, 600));
        setMinimumSize(new Dimension(800, 400));
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());

        menuPanel = menuInit();
        getContentPane().add(menuPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        navbarPanel = navbarInit();
        centerPanel.add(navbarPanel, BorderLayout.NORTH);
        dynamicPanel = new JPanel();
        dynamicPanel.setBackground(new Color(245, 245, 245));
        dynamicPanel.setLayout(new BorderLayout());
        centerPanel.add(dynamicPanel, BorderLayout.CENTER);
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public JPanel menuInit() {
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(255, 255, 255));
        menuPanel.setPreferredSize(new Dimension(200, 0));
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        JLabel logoLB = new JLabel("");
        logoLB.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon originalIcon = new ImageIcon(AdminFrame.class.getResource("/ASSET/Images/logoRmBg.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        logoLB.setIcon(scaledIcon);
        menuPanel.add(logoLB, gbc);

        gbc.gridy++;
        JToggleButton thongkeBtn = new JToggleButton("Thống kê");
        toggleBtnInit(thongkeBtn, "ThongKePanel");
        menuPanel.add(thongkeBtn, gbc);

        gbc.gridy++;
        JToggleButton hoadonBtn = new JToggleButton("Hóa đơn");
        toggleBtnInit(hoadonBtn, "HoaDonPanel");
        menuPanel.add(hoadonBtn, gbc);

        gbc.gridy++;
        JToggleButton sanphamBtn = new JToggleButton("Sản phẩm");
        toggleBtnInit(sanphamBtn, "SanPhamPanel");
        menuPanel.add(sanphamBtn, gbc);

        gbc.gridy++;
        JToggleButton nglieuBtn = new JToggleButton("Nguyên liệu");
        toggleBtnInit(nglieuBtn, "NguyenLieuPanel");
        menuPanel.add(nglieuBtn, gbc);

        gbc.gridy++;
        JToggleButton congthucBtn = new JToggleButton("Công thức");
        toggleBtnInit(congthucBtn, "CongThucPanel");
        menuPanel.add(congthucBtn, gbc);

        gbc.gridy++;
        JToggleButton dmucBtn = new JToggleButton("Danh mục");
        toggleBtnInit(dmucBtn, "DanhMucPanel");
        menuPanel.add(dmucBtn, gbc);

        gbc.gridy++;
        JToggleButton nccBtn = new JToggleButton("Nhà cung cấp");
        toggleBtnInit(nccBtn, "NhaCungCapPanel");
        menuPanel.add(nccBtn, gbc);

        gbc.gridy++;
        JToggleButton pnhapBtn = new JToggleButton("Phiếu nhập");
        toggleBtnInit(pnhapBtn, "PhieuNhapPanel");
        menuPanel.add(pnhapBtn, gbc);

        gbc.gridy++;
        JToggleButton taikhoanBtn = new JToggleButton("Tài khoản");
        toggleBtnInit(taikhoanBtn, "TaiKhoanPanel");
        menuPanel.add(taikhoanBtn, gbc);

        gbc.gridy++;
        gbc.weighty = 1.0;

        menuPanel.add(new JPanel(), gbc);
        return menuPanel;
    }

    public JPanel navbarInit() {
        JPanel navbarPanel = new JPanel();
        navbarPanel.setBackground(new Color(245, 245, 245));
        navbarPanel.setPreferredSize(new Dimension(0, 50)); // Giảm chiều cao
        navbarPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 8)); // Căn phải, khoảng cách nhỏ

        JLabel tenTkLB = new JLabel("Thảo Vy");
        tenTkLB.setFont(new Font("Tahoma", Font.BOLD, 11)); // Font nhỏ hơn
        tenTkLB.setHorizontalAlignment(SwingConstants.RIGHT);
        tenTkLB.setPreferredSize(new Dimension(100, 30)); // Kích thước cố định để căn chỉnh
        navbarPanel.add(tenTkLB);

        JButton infoBtn = new JButton("Thông tin");
        infoBtn.setIcon(getScaledImage(18, 18, "/ASSET/Images/person.png")); // Biểu tượng nhỏ hơn
        infoBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        infoBtn.setContentAreaFilled(false);
        infoBtn.setOpaque(true);
        infoBtn.setBorderPainted(false);
        infoBtn.setPreferredSize(new Dimension(100, 30)); // Kích thước đồng bộ
        navbarPanel.add(infoBtn);

        JButton logoutBtn = new JButton("Đăng xuất");
        logoutBtn.setIcon(getScaledImage(18, 18, "/ASSET/Images/logout.png"));
        logoutBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setOpaque(true);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setPreferredSize(new Dimension(100, 30));
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic đăng xuất (giữ nguyên)
            }
        });
        navbarPanel.add(logoutBtn);

        return navbarPanel;
    }

    public ImageIcon getScaledImage(int width, int height, String path) {
        ImageIcon originalIcon = new ImageIcon(AdminFrame.class.getResource(path));
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // Gán sự kiện cho nút toggle với panel tương ứng
    public void toggleBtnInit(JToggleButton btn, String panelType) {
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btn.setBackground(new Color(255, 192, 203));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);

        btn.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("Nút được nhấn: " + btn.getText() + " | Panel: " + panelType);
                    replaceDynamicPanel(panelType);
                }
            }
        });
    }

    // Thay thế panel động dựa trên panelType
    private void replaceDynamicPanel(String panelType) {
        dynamicPanel.removeAll();

        JPanel selectedPanel;
        try {
            switch (panelType) {
                case "NguyenLieuPanel":
                    selectedPanel = new NguyenLieuPanel();
                    System.out.println("Hiển thị NguyenLieuPanel");
                    break;
                case "DanhMucPanel":
                    selectedPanel = new DanhMucPanel();
                    System.out.println("Hiển thị DanhMucPanel");
                    break;
                case "ThongKePanel":
                    selectedPanel = new JPanel();
                    selectedPanel.setBackground(Color.YELLOW);
                    System.out.println("Hiển thị ThongKePanel (placeholder)");
                    break;
                case "HoaDonPanel":
                    selectedPanel = new JPanel();
                    selectedPanel.setBackground(Color.GREEN);
                    System.out.println("Hiển thị HoaDonPanel (placeholder)");
                    break;
                case "SanPhamPanel":
                    selectedPanel = new JPanel();
                    selectedPanel.setBackground(Color.BLUE);
                    System.out.println("Hiển thị SanPhamPanel (placeholder)");
                    break;
                case "CongThucPanel":
                    selectedPanel = new JPanel();
                    selectedPanel.setBackground(Color.PINK);
                    System.out.println("Hiển thị CongThucPanel (placeholder)");
                    break;
                case "NhaCungCapPanel":
                    selectedPanel = new JPanel();
                    selectedPanel.setBackground(Color.CYAN);
                    System.out.println("Hiển thị NhaCungCapPanel (placeholder)");
                    break;
                case "PhieuNhapPanel":
                    selectedPanel = new JPanel();
                    selectedPanel.setBackground(Color.MAGENTA);
                    System.out.println("Hiển thị PhieuNhapPanel (placeholder)");
                    break;
                case "TaiKhoanPanel":
                    selectedPanel = new JPanel();
                    selectedPanel.setBackground(Color.ORANGE);
                    System.out.println("Hiển thị TaiKhoanPanel (placeholder)");
                    break;
                default:
                    selectedPanel = new JPanel();
                    selectedPanel.setBackground(Color.GRAY);
                    System.out.println("Hiển thị panel mặc định (trống)");
                    break;
            }
            dynamicPanel.add(selectedPanel, BorderLayout.CENTER);
        } catch (Exception e) {
            System.out.println("Lỗi khi khởi tạo panel: " + panelType);
            e.printStackTrace();
            selectedPanel = new JPanel();
            selectedPanel.setBackground(Color.RED);
            dynamicPanel.add(selectedPanel, BorderLayout.CENTER);
        }

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }
}