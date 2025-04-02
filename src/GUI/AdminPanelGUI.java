package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 600);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 200, 600);
		getContentPane().add(panel);
		panel.setLayout(null);
				
		JLabel lblNewLabel = new JLabel("New label");
		ImageIcon logo = new ImageIcon(AdminPanelGUI.class.getResource("/ASSET/Image/logoRmBg.png"));
		lblNewLabel.setIcon(new ImageIcon(logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));		lblNewLabel.setBounds(0, 0, 200, 100);
		panel.add(lblNewLabel);
		
	}
}
