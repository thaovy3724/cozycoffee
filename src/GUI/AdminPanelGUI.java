package GUI;

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
public class AdminPanelGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPanelGUI frame = new AdminPanelGUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminPanelGUI() {
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 600);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 200, 563);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// Load the original image
				ImageIcon originalIcon = new ImageIcon(AdminPanelGUI.class.getResource("/ASSET/Images/logoRmBg.png"));

				// Resize the image (e.g., to 50x50 pixels)
				Image scaledImage = originalIcon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);

				// Create a new ImageIcon with the scaled image
				ImageIcon scaledIcon = new ImageIcon(scaledImage);

				// Set the scaled icon to the JLabel
				lblNewLabel.setIcon(scaledIcon);
				
		// warning
//		try {
//            InputStream imgStream = AdminPanelGUI.class.getResourceAsStream("/ASSET/Images/logoRmBg.png");
//            if (imgStream != null) {
//                BufferedImage bufferedImage = ImageIO.read(imgStream);
//                Image scaledImage = bufferedImage.getScaledInstance(180, 111, Image.SCALE_SMOOTH);
//                lblNewLabel.setIcon(new ImageIcon(scaledImage));
//            } else {
//                System.out.println("Image not found!");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
		// warning
		lblNewLabel.setBounds(0, 1, 200, 130);
		panel.add(lblNewLabel);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Thống kê");
		tglbtnNewToggleButton.setBackground(new Color(255, 192, 203));
		// set to my new background color and not change to default color onclick
		tglbtnNewToggleButton.setContentAreaFilled(false);
		tglbtnNewToggleButton.setOpaque(true);       
		//---------------------------------------
		tglbtnNewToggleButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		tglbtnNewToggleButton.setBounds(10, 161, 180, 36);
		panel.add(tglbtnNewToggleButton);
		
		JToggleButton tglbtnHan = new JToggleButton("Hóa đơn");
		tglbtnHan.setBackground(new Color(255, 192, 203));
		// set to my new background color and not change to default color onclick
		tglbtnHan.setContentAreaFilled(false);
		tglbtnHan.setOpaque(true);       
		//---------------------------------------
		tglbtnHan.setFont(new Font("Tahoma", Font.BOLD, 12));
		tglbtnHan.setBounds(10, 208, 180, 36);
		panel.add(tglbtnHan);
		
		JToggleButton tglbtnSnPhm = new JToggleButton("Sản phẩm");
		tglbtnSnPhm.setFont(new Font("Tahoma", Font.BOLD, 12));
		tglbtnSnPhm.setBackground(new Color(255, 192, 203));
		// set to my new background color and not change to default color onclick
		tglbtnSnPhm.setContentAreaFilled(false);
		tglbtnSnPhm.setOpaque(true);       
		//---------------------------------------
		tglbtnSnPhm.setBounds(10, 255, 180, 36);
		panel.add(tglbtnSnPhm);
		
		JToggleButton tglbtnNguynLiu = new JToggleButton("Nguyên liệu");
		tglbtnNguynLiu.setFont(new Font("Tahoma", Font.BOLD, 12));
		tglbtnNguynLiu.setBackground(new Color(255, 192, 203));
		// set to my new background color and not change to default color onclick
		tglbtnNguynLiu.setContentAreaFilled(false);
		tglbtnNguynLiu.setOpaque(true);       
		//---------------------------------------
		tglbtnNguynLiu.setBounds(10, 302, 180, 36);
		panel.add(tglbtnNguynLiu);
		
		JToggleButton tglbtnCngThc = new JToggleButton("Công thức");
		tglbtnCngThc.setFont(new Font("Tahoma", Font.BOLD, 12));
		tglbtnCngThc.setBackground(new Color(255, 192, 203));
		// set to my new background color and not change to default color onclick
		tglbtnCngThc.setContentAreaFilled(false);
		tglbtnCngThc.setOpaque(true);       
		//---------------------------------------
		tglbtnCngThc.setBounds(10, 353, 180, 36);
		panel.add(tglbtnCngThc);
		
		JToggleButton tglbtnNhCungCp = new JToggleButton("Danh mục");
		tglbtnNhCungCp.setFont(new Font("Tahoma", Font.BOLD, 12));
		tglbtnNhCungCp.setBackground(new Color(255, 192, 203));
		// set to my new background color and not change to default color onclick
		tglbtnNhCungCp.setContentAreaFilled(false);
		tglbtnNhCungCp.setOpaque(true);       
		//---------------------------------------
		tglbtnNhCungCp.setBounds(10, 400, 180, 36);
		panel.add(tglbtnNhCungCp);
		
		JToggleButton tglbtnNhCungCp_1 = new JToggleButton("Nhà cung cấp");
		tglbtnNhCungCp_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		tglbtnNhCungCp_1.setBackground(new Color(255, 192, 203));
		// set to my new background color and not change to default color onclick
		tglbtnNhCungCp_1.setContentAreaFilled(false);
		tglbtnNhCungCp_1.setOpaque(true);       
		//---------------------------------------
		tglbtnNhCungCp_1.setBounds(10, 447, 180, 36);
		panel.add(tglbtnNhCungCp_1);
		
		JToggleButton tglbtnNhCungCp_1_1 = new JToggleButton("Phiếu nhập");
		tglbtnNhCungCp_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		tglbtnNhCungCp_1_1.setBackground(new Color(255, 192, 203));
		// set to my new background color and not change to default color onclick
		tglbtnNhCungCp_1_1.setContentAreaFilled(false);
		tglbtnNhCungCp_1_1.setOpaque(true);       
		//---------------------------------------
		tglbtnNhCungCp_1_1.setBounds(10, 491, 180, 36);
		panel.add(tglbtnNhCungCp_1_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(199, 0, 887, 56);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Thảo Vy");
		lblNewLabel_2.setBackground(new Color(245, 245, 245));
		lblNewLabel_2.setOpaque(true); 
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setBounds(0, 0, 758, 54);
		panel_1.add(lblNewLabel_2);
		
		
		// Load the original image
		originalIcon = new ImageIcon(AdminPanelGUI.class.getResource("/ASSET/Images/logout.png"));

		// Resize the image (e.g., to 50x50 pixels)
		scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		// Create a new ImageIcon with the scaled image
		scaledIcon = new ImageIcon(scaledImage);
		JButton btnNewButton = new JButton("Đăng xuất");
		btnNewButton.setBackground(new Color(245, 245, 245));
		btnNewButton.setIcon(scaledIcon);
		btnNewButton.setContentAreaFilled(false); // Disable content area filling
		btnNewButton.setOpaque(true);           // Disable background painting
		btnNewButton.setBorderPainted(false);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(757, 1, 130, 54);
		panel_1.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(245, 245, 245));
		panel_2.setBounds(199, 55, 887, 508);
		getContentPane().add(panel_2);
		
//		JLabel lblNewLabel_1 = new JLabel("Đăng xuất");
//		lblNewLabel_1.setBounds(771, 137, 148, 54);
//		getContentPane().add(lblNewLabel_1);
//		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
//		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
//		
//				// Set the scaled icon to the JLabel
//				lblNewLabel_1.setIcon(scaledIcon);
	}
}
