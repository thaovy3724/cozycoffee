package GUI;

import DTO.TaiKhoanDTO;
import GUI.Dialog.DoiMatKhauDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Cursor;

public class StaffFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel menuPanel;
    private JPanel navbarPanel;
    private JPanel dynamicPanel;
    private JToggleButton lastSelectedButton;
    private TaiKhoanDTO currentUser;
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 12);

    public TaiKhoanDTO getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(TaiKhoanDTO currentUser) {
        this.currentUser = currentUser;
    }

    public void refreshNavbar() {
        getContentPane().remove(navbarPanel);
        navbarPanel = navbarInit();
        getContentPane().add(navbarPanel);
        revalidate();
        repaint();
    }

    public StaffFrame(TaiKhoanDTO currentUser) {
        this.currentUser = currentUser;
        init();
    }

    public void init() {
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setPreferredSize(new Dimension(1200, 700));
        // setMinimumSize(new Dimension(800, 400));
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
        menuPanel.setBackground(new Color(240, 187, 120)); // Darker, modern background color
        menuPanel.setPreferredSize(new Dimension(240, 0)); // Slightly wider for better spacing
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10); // Increased top/bottom padding for logo
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        // Logo
        ImageHelper logoIcon = new ImageHelper(220, 140, StaffFrame.class.getResource("/ASSET/Images/logoRmBg.png"));
        JLabel logoLB = new JLabel();
        logoLB.setHorizontalAlignment(SwingConstants.CENTER);
        logoLB.setIcon(logoIcon.getScaledImage());
        menuPanel.add(logoLB, gbc);

        // Separator for visual distinction
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(200, 200, 200));
        menuPanel.add(separator, gbc);

        // Button: Bán hàng
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15); // Consistent button spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        JToggleButton btnBanHang = new JToggleButton("Bán hàng");
        btnBanHang.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBanHang.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnBanHang.setBackground(new Color (250, 250, 210));
        btnBanHang.setFocusPainted(false);
        btnBanHang.setForeground(new Color(34, 40, 49));
        btnBanHang.setActionCommand("banhang");
        btnBanHang.setPreferredSize(new Dimension(210, 50));
        ImageHelper iconThongKe = new ImageHelper(28, 28, StaffFrame.class.getResource("/ASSET/Images/1.png"));
        btnBanHang.setIcon(iconThongKe.getScaledImage());
        btnBanHang.setIconTextGap(12);
        toggleBtnInit(btnBanHang);
        menuPanel.add(btnBanHang, gbc);

        // Button: Bán hàng
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        
        JToggleButton btnHoaDon = new JToggleButton("Hóa đơn");
        btnHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnHoaDon.setBackground(new Color(250, 250, 210)); // Light neutral background
        btnHoaDon.setFocusPainted(false);
        btnHoaDon.setForeground(new Color(34, 40, 49)); // Dark text for contrast
        btnHoaDon.setActionCommand("hoadon");
        btnHoaDon.setPreferredSize(new Dimension(210, 50));
        ImageHelper iconHoaDon = new ImageHelper(28, 28, StaffFrame.class.getResource("/ASSET/Images/2.png"));
        btnHoaDon.setIcon(iconHoaDon.getScaledImage());
        btnHoaDon.setIconTextGap(12);
        toggleBtnInit(btnHoaDon);
        menuPanel.add(btnHoaDon, gbc);
        dynamicPanel.add(new BanHangPanel(currentUser), BorderLayout.CENTER);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();

        // Button: Công thức
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        JToggleButton btnCongThuc = new JToggleButton("Công thức");
        btnCongThuc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCongThuc.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnCongThuc.setBackground(new Color(250, 250, 210));
        btnCongThuc.setFocusPainted(false);
        btnCongThuc.setForeground(new Color(34, 40, 49));
        btnCongThuc.setActionCommand("congthuc");
        btnCongThuc.setPreferredSize(new Dimension(210, 50));
        ImageHelper iconCongThuc = new ImageHelper(28, 28, StaffFrame.class.getResource("/ASSET/Images/6.png"));
        btnCongThuc.setIcon(iconCongThuc.getScaledImage());
        btnCongThuc.setIconTextGap(12);
  
        toggleBtnInit(btnCongThuc);
        menuPanel.add(btnCongThuc, gbc);

        // Add vertical glue to push content upward
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weighty = 1.0; // Push all content to the top
        menuPanel.add(Box.createVerticalGlue(), gbc);

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

        JLabel tenTkLB = new JLabel(currentUser.getTenTK());
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

        ImageHelper logoutIcon = new ImageHelper(20, 20, StaffFrame.class.getResource("/ASSET/Images/logout.png"));
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
            case "hoadon":
                selectedPanel = new HoaDonPanel();
                break;
            
            case "congthuc":
                selectedPanel = new CongThucPanel(currentUser);
                break;

            case "banhang":
                selectedPanel = new BanHangPanel(currentUser);
                break;
            
        }

        dynamicPanel.add(selectedPanel, BorderLayout.CENTER);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }
}