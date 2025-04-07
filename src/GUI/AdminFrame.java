package GUI;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import java.awt.Color;
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
        // AdminFrameGUI init
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        // Menu init
        menuPanel = menuInit();
        getContentPane().add(menuPanel);

        // Navbar init
        navbarPanel = navbarInit();
        getContentPane().add(navbarPanel);

        //content
        dynamicPanel = new JPanel();
        dynamicPanel.setBackground(new Color(245, 245, 245));

        // calculate to set the height for panel_2
        int fullHeight = getContentPane().getHeight();
        dynamicPanel.setBounds(199, 55, 887, 508);
        //---------------------------------------

        getContentPane().add(dynamicPanel);
        dynamicPanel.setLayout(new BorderLayout(0, 0));

        setVisible(true); // set visible for adminframe
    }

    public JPanel menuInit() {
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(255, 255, 255));
        menuPanel.setBounds(0, 0, 200, 563);

        menuPanel.setLayout(null);

        JLabel logoLB = new JLabel("");
        logoLB.setHorizontalAlignment(SwingConstants.CENTER);
        // Load the original image
        ImageIcon originalIcon = new ImageIcon(AdminFrame.class.getResource("/ASSET/Images/logoRmBg.png"));

        // Resize the image (e.g., to 50x50 pixels)
        Image scaledImage = originalIcon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the scaled image
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the scaled icon to the JLabel
        logoLB.setIcon(scaledIcon);


        logoLB.setBounds(0, 1, 200, 130);
        menuPanel.add(logoLB);

        JToggleButton thongkeBtn = new JToggleButton("Thống kê");
        toggleBtnInit(thongkeBtn, "thongke");
        thongkeBtn.setBounds(10, 136, 180, 36);
        menuPanel.add(thongkeBtn);

        JToggleButton hoadonBtn = new JToggleButton("Hóa đơn");
        toggleBtnInit(hoadonBtn, "hoadon");
        hoadonBtn.setBounds(10, 183, 180, 36);
        menuPanel.add(hoadonBtn);

        JToggleButton sanphamBtn = new JToggleButton("Sản phẩm");
        toggleBtnInit(sanphamBtn, "sanpham");
        sanphamBtn.setBounds(10, 230, 180, 36);
        menuPanel.add(sanphamBtn);

        JToggleButton nglieuBtn = new JToggleButton("Nguyên liệu");
        toggleBtnInit(nglieuBtn, "nglieu");
        nglieuBtn.setBounds(10, 277, 180, 36);
        menuPanel.add(nglieuBtn);

        JToggleButton congthucBtn = new JToggleButton("Công thức");
        toggleBtnInit(congthucBtn, "congthuc");
        congthucBtn.setBounds(10, 319, 180, 36);
        menuPanel.add(congthucBtn);

        JToggleButton dmucBtn = new JToggleButton("Danh mục");
        toggleBtnInit(dmucBtn, "dmuc");
        dmucBtn.setBounds(10, 366, 180, 36);
        menuPanel.add(dmucBtn);

        JToggleButton nccBtn = new JToggleButton("Nhà cung cấp");
        toggleBtnInit(nccBtn, "ncc");
        nccBtn.setBounds(10, 413, 180, 36);
        menuPanel.add(nccBtn);

        JToggleButton pnhapBtn = new JToggleButton("Phiếu nhập");
        toggleBtnInit(pnhapBtn, "pnhap");
        pnhapBtn.setBounds(10, 460, 180, 36);
        menuPanel.add(pnhapBtn);

        JToggleButton taikhoanBtn = new JToggleButton("Tài khoản");
        toggleBtnInit(taikhoanBtn, "taikhoan");
        taikhoanBtn.setBounds(10, 507, 180, 36);
        menuPanel.add(taikhoanBtn);

        return menuPanel;
    }

    public JPanel navbarInit() {
        JPanel navbarPanel = new JPanel();
        navbarPanel.setBounds(199, 0, 887, 56);
        navbarPanel.setLayout(null);

        JLabel tenTkLB = new JLabel("Thảo Vy");
        tenTkLB.setBackground(new Color(245, 245, 245));
        tenTkLB.setOpaque(true);
        tenTkLB.setFont(new Font("Tahoma", Font.BOLD, 12));
        tenTkLB.setHorizontalAlignment(SwingConstants.TRAILING);
        tenTkLB.setBounds(0, 0, 563, 54);
        navbarPanel.add(tenTkLB);

        JButton logoutBtn = new JButton("Đăng xuất");
        logoutBtn.setBackground(new Color(245, 245, 245));
        logoutBtn.setIcon(getScaledImage(20, 20, "/ASSET/Images/logout.png"));
        logoutBtn.setContentAreaFilled(false); // Disable content area filling
        logoutBtn.setOpaque(true);           // Disable background painting
        logoutBtn.setBorderPainted(false);

        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        logoutBtn.setBounds(757, 1, 130, 54);
        navbarPanel.add(logoutBtn);


        JButton infoBtn = new JButton("Thông tin cá nhân");
        infoBtn.setIcon(getScaledImage(20, 20, "/ASSET/Images/person.png"));
        infoBtn.setContentAreaFilled(false); // Disable content area filling
        infoBtn.setOpaque(true);           // Disable background painting
        infoBtn.setBorderPainted(false);
        infoBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
        infoBtn.setBounds(573, 0, 183, 54);
        navbarPanel.add(infoBtn);

        return navbarPanel;
    }

    public ImageIcon getScaledImage(int width, int height, String path) {
        // Load the original image
        ImageIcon originalIcon = new ImageIcon(AdminFrame.class.getResource(path));

        // Resize the image (e.g., to 50x50 pixels)
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the scaled image
        return new ImageIcon(scaledImage);
    }

    // Start: toggle button
    public void toggleBtnInit(JToggleButton btn, String actionCommand) {
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));

        // set background and not change to default color onclick
        btn.setBackground(new Color(255, 192, 203));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setActionCommand(actionCommand);
        //---------------------------------------

        // replace panel onclick
        btn.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    //Cập nhật lại nút ngay lập tức, tránh tình trạng nhấn 2 lần mới được
                    if (lastSelectedButton != null && lastSelectedButton != btn) {
                        lastSelectedButton.setSelected(false); // Bỏ chọn nút trước đó
                    }
                    lastSelectedButton = btn; // Cập nhật nút được chọn
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
                selectedPanel = new JPanel(); // Thay bằng panel Thống kê
                break;
            case "hoadon":
                selectedPanel = new JPanel(); // Thay bằng panel Hóa đơn
                break;
            case "sanpham":
                selectedPanel = new JPanel(); // Thay bằng panel Sản phẩm
                break;
            case "nglieu":
                selectedPanel = new JPanel(); // Thay bằng panel Nguyên liệu
                break;
            case "congthuc":
                selectedPanel = new JPanel(); // Thay bằng panel Công thức
                break;
            case "dmuc":
                selectedPanel = new DanhMucPanel();
                break;
            case "ncc":
                selectedPanel = new JPanel(); // Thay bằng panel Nhà cung cấp
                break;
            case "pnhap":
                selectedPanel = new JPanel(); // Thay bằng panel Phiếu nhập
                break;
            case "taikhoan":
                selectedPanel = new TaiKhoanPanel(); // Hiển thị TaiKhoanPanel
                System.out.println("TaiKhoanPanel created");
                break;
            default:
                selectedPanel = new JPanel(); // Mặc định
                System.out.println("Default panel created");
                break;
        }

        dynamicPanel.add(selectedPanel, BorderLayout.CENTER);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    // End: toggle button
}