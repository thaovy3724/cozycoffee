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
	
	 //Thuộc tính currentUser - người dùng đang đăng nhập hiện tại trên adminFrame
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
        /*
        * revalidate() yêu cầu trình quản lý bố cục (layout manager) của container
        * (ở đây là getContentPane()) cập nhật lại kích thước và vị trí của
        * các thành phần giao diện bên trong nó (chứ chưa được hiển thị) => dùng thêm repaint().
        * */
        revalidate();
        /*
        * repaint() yêu cầu hệ thống vẽ lại giao diện của thành phần được gọi
        * (ở đây là cả AdminFrame, hay nói rộng hơn là các đối tượng có kiểu là AdminFrame)
        * */
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
		ImageHelper logoIcon = new ImageHelper(180, 120, AdminFrame.class.getResource("/ASSET/Images/logoRmBg.png"));
		// Set the scaled icon to the JLabel
		logoLB.setIcon(logoIcon.getScaledImage());
		menuPanel.add(logoLB, gbc);
		
		gbc.gridy++;
		JToggleButton thongkeBtn = new JToggleButton("Thống kê");
		thongkeBtn.setActionCommand("thongke");
		toggleBtnInit(thongkeBtn);
		menuPanel.add(thongkeBtn, gbc);
		
		gbc.gridy++;
		JToggleButton hoadonBtn = new JToggleButton("Hóa đơn");
		hoadonBtn.setActionCommand("hoadon");
		toggleBtnInit(hoadonBtn);
		menuPanel.add(hoadonBtn, gbc);
		
		gbc.gridy++;
		JToggleButton sanphamBtn = new JToggleButton("Sản phẩm");
		sanphamBtn.setActionCommand("sanpham");
		sanphamBtn.setBounds(10, 230, 180, 36);
		menuPanel.add(sanphamBtn, gbc);
		
		gbc.gridy++;
		JToggleButton nguyenlieuBtn = new JToggleButton("Nguyên liệu");
		nguyenlieuBtn.setActionCommand("nguyenlieu");
		toggleBtnInit(nguyenlieuBtn);
		nguyenlieuBtn.setBounds(10, 277, 180, 36);
		menuPanel.add(nguyenlieuBtn, gbc);
		
		gbc.gridy++;
		JToggleButton congthucBtn = new JToggleButton("Công thức");
		congthucBtn.setActionCommand("congthuc");
		toggleBtnInit(congthucBtn);
		menuPanel.add(congthucBtn, gbc);
		
		gbc.gridy++;
		JToggleButton danhmucBtn = new JToggleButton("Danh mục");
		danhmucBtn.setActionCommand("danhmuc");
		toggleBtnInit(danhmucBtn);
		danhmucBtn.setBounds(10, 366, 180, 36);
		menuPanel.add(danhmucBtn, gbc);
		
		gbc.gridy++;
		JToggleButton nhacungcapBtn = new JToggleButton("Nhà cung cấp");
		nhacungcapBtn.setActionCommand("nhacungcap");
		toggleBtnInit(nhacungcapBtn);
		nhacungcapBtn.setBounds(10, 413, 180, 36);
		menuPanel.add(nhacungcapBtn, gbc);
		
		gbc.gridy++;
		JToggleButton phieunhapBtn = new JToggleButton("Phiếu nhập");
		phieunhapBtn.setActionCommand("phieunhap");
		toggleBtnInit(phieunhapBtn);
		nhacungcapBtn.setBounds(10, 460, 180, 36);
		menuPanel.add(phieunhapBtn, gbc);
		
		gbc.gridy++;
		JToggleButton taikhoanBtn = new JToggleButton("Tài khoản");
		taikhoanBtn.setActionCommand("taikhoan");
		toggleBtnInit(taikhoanBtn);
		taikhoanBtn.setBounds(10, 507, 180, 36);
		menuPanel.add(taikhoanBtn, gbc);
		
		gbc.gridy++;
        gbc.weighty = 1.0;

        menuPanel.add(new JPanel(), gbc);
        return menuPanel;
	}
	
	public JPanel navbarInit() {
		navbarPanel = new JPanel();
		navbarPanel.setBackground(new Color(245, 245, 245));
        navbarPanel.setPreferredSize(new Dimension(0, 50)); // Giảm chiều cao
        navbarPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 8)); // Căn phải, khoảng cách nhỏ
		
		JLabel tenTkLB = new JLabel("Thảo Vy");
		tenTkLB.setBackground(new Color(245, 245, 245));
		tenTkLB.setOpaque(true); 
		tenTkLB.setFont(new Font("Tahoma", Font.BOLD, 12));
		tenTkLB.setHorizontalAlignment(SwingConstants.TRAILING);
        tenTkLB.setPreferredSize(new Dimension(100, 30)); // Kích thước cố định để căn chỉnh
		navbarPanel.add(tenTkLB);
		
		JButton logoutBtn = new JButton("Đăng xuất");
		logoutBtn.setBackground(new Color(245, 245, 245));
		ImageHelper logoutIcon = new ImageHelper(20, 20, AdminFrame.class.getResource("/ASSET/Images/logout.png"));
		logoutBtn.setIcon(logoutIcon.getScaledImage());
		logoutBtn.setContentAreaFilled(false); // Disable content area filling
		logoutBtn.setOpaque(true);           // Disable background painting
		logoutBtn.setBorderPainted(false);
		logoutBtn.setPreferredSize(new Dimension(100, 30));
		
		//Thêm sự kiện cho nút đăng xuất
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hiển thị hộp thoại xác nhận
                int confirm = showOptionDialog(
                        "Xác nhận đăng xuất",
                        "Bạn có chắc chắn muốn đăng xuất không?"
                );

                // Kiểm tra lựa chọn của người dùng
                if (confirm == JOptionPane.YES_OPTION) {
                    // Nếu chọn "Yes", thực hiện đăng xuất
                    dispose(); // Đóng AdminFrame
                    new DangNhapFrame().setVisible(true); // Mở lại DangNhapFrame
                    JOptionPane.showMessageDialog(null, "Đăng xuất thành công");
                }
                // Nếu chọn "No" hoặc đóng hộp thoại, không làm gì cả
            }
        });
		navbarPanel.add(logoutBtn);
		
		return navbarPanel;
	}
	
	// Start: toggle button
	public void toggleBtnInit(JToggleButton btn) {
		btn.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		// set background and not change to default color onclick
		btn.setBackground(new Color(255, 192, 203));
		btn.setContentAreaFilled(false);
		btn.setOpaque(true);       
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
            case "nguyenlieu":
                selectedPanel = new JPanel(); // Thay bằng panel Nguyên liệu
                break;
            case "congthuc":
                selectedPanel = new JPanel(); // Thay bằng panel Công thức
                break;
            case "danhmuc":
                selectedPanel = new DanhMucPanel();
                break;
            case "nhacungcap":
                selectedPanel = new JPanel(); // Thay bằng panel Nhà cung cấp
                break;
            case "phieunhap":
                selectedPanel = new JPanel(); // Thay bằng panel Phiếu nhập
                break;
           case "taikhoan":
               // Truyền AdminFrame vào TaiKhoanPanel
				selectedPanel = new TaiKhoanPanel(); // Hiển thị TaiKhoanPanel
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

	 private int showOptionDialog(String title, String message) {
	        int confirm = JOptionPane.showConfirmDialog(
	                AdminFrame.this, // Parent component (AdminFrame)
	                message,
	                title,
	                JOptionPane.YES_NO_OPTION // Tùy chọn Yes/No
	        );
	        return confirm;
	    }
	// End: toggle button
}
