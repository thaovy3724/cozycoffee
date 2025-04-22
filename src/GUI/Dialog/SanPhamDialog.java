package GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GUI.ImageHelper;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.Box;

public class SanPhamDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtTenSP;
	private JTextField txtGia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SanPhamDialog dialog = new SanPhamDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SanPhamDialog() {
		setTitle("Thêm sản phẩm");
		setBounds(100, 100, 576, 431);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			getContentPane().add(panel, BorderLayout.NORTH);
			{
				JLabel lblTitle = new JLabel("Thêm sản phẩm");
				lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
				panel.add(lblTitle);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.WEST);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
				{
					JLabel lblImage = new JLabel("");
					ImageHelper image = new ImageHelper(200, 200, SanPhamDialog.class.getResource("/ASSET/Uploads/360_F_243123463_zTooub557xEWABDLk0jJklDyLSGl2jrr.jpg"));
					{
						Component verticalStrut = Box.createVerticalStrut(50);
						panel_1.add(verticalStrut);
					}
					lblImage.setIcon(image.getScaledImage());
					lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
					panel_1.add(lblImage);
				}
				{
					JButton btnUpload = new JButton("Tải ảnh");
					btnUpload.setAlignmentX(Component.CENTER_ALIGNMENT);
					btnUpload.setBackground(Color.BLACK);
					btnUpload.setForeground(new Color(255, 255, 255));
					btnUpload.setFont(new Font("Segoe UI", Font.PLAIN, 14));
					btnUpload.setContentAreaFilled(false);
					btnUpload.setOpaque(true);
					panel_1.add(btnUpload);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblTenSP = new JLabel("Tên sản phẩm");
				lblTenSP.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				GridBagConstraints gbc_lblTenSP = new GridBagConstraints();
				gbc_lblTenSP.gridwidth = 4;
				gbc_lblTenSP.insets = new Insets(0, 10, 5, 10);
				gbc_lblTenSP.anchor = GridBagConstraints.WEST;
				gbc_lblTenSP.gridx = 0;
				gbc_lblTenSP.gridy = 0;
				panel.add(lblTenSP, gbc_lblTenSP);
			}
			{
				txtTenSP = new JTextField();
				txtTenSP.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				GridBagConstraints gbc_txtTenSP = new GridBagConstraints();
				gbc_txtTenSP.insets = new Insets(0, 10, 5, 10);
				gbc_txtTenSP.gridwidth = 4;
				gbc_txtTenSP.anchor = GridBagConstraints.WEST;
				gbc_txtTenSP.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtTenSP.gridx = 0;
				gbc_txtTenSP.gridy = 1;
				panel.add(txtTenSP, gbc_txtTenSP);
				txtTenSP.setColumns(10);
			}
			{
				JLabel errTenSP = new JLabel("New label");
				errTenSP.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				errTenSP.setForeground(Color.RED);
				GridBagConstraints gbc_errTenSP = new GridBagConstraints();
				gbc_errTenSP.anchor = GridBagConstraints.WEST;
				gbc_errTenSP.gridwidth = 4;
				gbc_errTenSP.insets = new Insets(0, 10, 10, 10);
				gbc_errTenSP.gridx = 0;
				gbc_errTenSP.gridy = 2;
				panel.add(errTenSP, gbc_errTenSP);
			}
			{
				JLabel lblDM = new JLabel("Danh mục");
				lblDM.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				GridBagConstraints gbc_lblDM = new GridBagConstraints();
				gbc_lblDM.gridwidth = 4;
				gbc_lblDM.anchor = GridBagConstraints.WEST;
				gbc_lblDM.insets = new Insets(0, 10, 5, 10);
				gbc_lblDM.gridx = 0;
				gbc_lblDM.gridy = 3;
				panel.add(lblDM, gbc_lblDM);
			}
			{
				JComboBox cboDM = new JComboBox();
				cboDM.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				GridBagConstraints gbc_cboDM = new GridBagConstraints();
				gbc_cboDM.fill = GridBagConstraints.HORIZONTAL;
				gbc_cboDM.gridwidth = 4;
				gbc_cboDM.anchor = GridBagConstraints.WEST;
				gbc_cboDM.insets = new Insets(0, 10, 5, 10);
				gbc_cboDM.gridx = 0;
				gbc_cboDM.gridy = 4;
				panel.add(cboDM, gbc_cboDM);
			}
			{
				JLabel errDM = new JLabel("New label");
				errDM.setForeground(Color.RED);
				errDM.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				GridBagConstraints gbc_errDM = new GridBagConstraints();
				gbc_errDM.gridwidth = 4;
				gbc_errDM.anchor = GridBagConstraints.WEST;
				gbc_errDM.insets = new Insets(0, 10, 10, 10);
				gbc_errDM.gridx = 0;
				gbc_errDM.gridy = 5;
				panel.add(errDM, gbc_errDM);
			}
			{
				JLabel lblGia = new JLabel("Giá bán");
				lblGia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				GridBagConstraints gbc_lblGia = new GridBagConstraints();
				gbc_lblGia.gridwidth = 4;
				gbc_lblGia.anchor = GridBagConstraints.WEST;
				gbc_lblGia.insets = new Insets(0, 10, 5, 10);
				gbc_lblGia.gridx = 0;
				gbc_lblGia.gridy = 6;
				panel.add(lblGia, gbc_lblGia);
			}
			{
				txtGia = new JTextField();
				txtGia.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				txtGia.setColumns(10);
				GridBagConstraints gbc_txtGia = new GridBagConstraints();
				gbc_txtGia.gridwidth = 4;
				gbc_txtGia.anchor = GridBagConstraints.WEST;
				gbc_txtGia.insets = new Insets(0, 10, 5, 10);
				gbc_txtGia.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtGia.gridx = 0;
				gbc_txtGia.gridy = 7;
				panel.add(txtGia, gbc_txtGia);
			}
			{
				JLabel errGia = new JLabel("New label");
				errGia.setForeground(Color.RED);
				errGia.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				GridBagConstraints gbc_errGia = new GridBagConstraints();
				gbc_errGia.gridwidth = 4;
				gbc_errGia.anchor = GridBagConstraints.WEST;
				gbc_errGia.insets = new Insets(0, 10, 10, 10);
				gbc_errGia.gridx = 0;
				gbc_errGia.gridy = 8;
				panel.add(errGia, gbc_errGia);
			}
			{
				JLabel lblTrangThai = new JLabel("Trạng thái");
				lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				GridBagConstraints gbc_lblTrangThai = new GridBagConstraints();
				gbc_lblTrangThai.gridwidth = 4;
				gbc_lblTrangThai.anchor = GridBagConstraints.WEST;
				gbc_lblTrangThai.insets = new Insets(0, 10, 5, 10);
				gbc_lblTrangThai.gridx = 0;
				gbc_lblTrangThai.gridy = 9;
				panel.add(lblTrangThai, gbc_lblTrangThai);
			}
			{
				JComboBox cboTrangThai = new JComboBox();
				cboTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				GridBagConstraints gbc_cboTrangThai = new GridBagConstraints();
				gbc_cboTrangThai.gridwidth = 4;
				gbc_cboTrangThai.anchor = GridBagConstraints.WEST;
				gbc_cboTrangThai.insets = new Insets(0, 10, 5, 10);
				gbc_cboTrangThai.fill = GridBagConstraints.HORIZONTAL;
				gbc_cboTrangThai.gridx = 0;
				gbc_cboTrangThai.gridy = 10;
				panel.add(cboTrangThai, gbc_cboTrangThai);
			}
			{
				JPanel panel_1 = new JPanel();
				GridBagConstraints gbc_panel_1 = new GridBagConstraints();
				gbc_panel_1.gridwidth = 4;
				gbc_panel_1.anchor = GridBagConstraints.EAST;
				gbc_panel_1.insets = new Insets(0, 10, 10, 10);
				gbc_panel_1.fill = GridBagConstraints.BOTH;
				gbc_panel_1.gridx = 0;
				gbc_panel_1.gridy = 12;
				panel.add(panel_1, gbc_panel_1);
				{
					JButton btnSubmit = new JButton("Thêm");
					btnSubmit.setBackground(new Color(0, 128, 0));
					btnSubmit.setForeground(Color.WHITE);
					btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
					btnSubmit.setContentAreaFilled(false);
					btnSubmit.setOpaque(true);
					panel_1.add(btnSubmit);
				}
				{
					JButton btnCancel = new JButton("Hủy");
					btnCancel.setForeground(Color.WHITE);
					btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
					btnCancel.setBackground(new Color(255, 51, 102));
					btnCancel.setContentAreaFilled(false);
					btnCancel.setOpaque(true);
					panel_1.add(btnCancel);
				}
			}
		}
	}

}
