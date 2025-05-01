package GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import BUS.SanPhamBUS;
import BUS.DanhMucBUS;
import DTO.DanhMucDTO;
import DTO.SanPhamDTO;
import GUI.ImageHelper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.Box;

public class SanPhamDialog extends JDialog {
	private SanPhamBUS sanPhamBus = new SanPhamBUS();
	private DanhMucBUS danhMucBus = new DanhMucBUS();

	private static final long serialVersionUID = 1L;
	private JPanel txtPanel = new JPanel();
	private JTextField txtTenSP, txtGia;
	private JComboBox<DanhMucDTO> cboDM = new JComboBox<>();
	private JComboBox<String> cboTrangThai = new JComboBox<>();
	private JLabel errTenSP, errGia, errHinh;
	private JLabel lblImage;
	private JLabel lblTitle;
	private JButton btnSubmit, btnCancel;
	private File uploadFile;

	/**
	 * Create the dialog.
	 */
	public SanPhamDialog() {
		setBounds(100, 100, 576, 403);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel titlePanel = new JPanel();
			FlowLayout fl_titlePanel = (FlowLayout) titlePanel.getLayout();
			fl_titlePanel.setAlignment(FlowLayout.LEFT);
			getContentPane().add(titlePanel, BorderLayout.NORTH);
			{
				lblTitle = new JLabel();
				lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
				titlePanel.add(lblTitle);
			}
		}
		imageInit();
		textFieldInit();
		actionInit();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
	}

	private void imageInit(){
		JPanel imagePanel = new JPanel();
		getContentPane().add(imagePanel, BorderLayout.WEST);
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
		{
			lblImage = new JLabel("");
			ImageHelper image = new ImageHelper(200, 200, SanPhamDialog.class.getResource("/ASSET/Uploads/default.jpg"));
			lblImage.setIcon(image.getScaledImage());
			lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
			imagePanel.add(lblImage);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			imagePanel.add(verticalStrut);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(5);
			imagePanel.add(verticalStrut);
		}
		{
			errHinh = new JLabel("");
			errHinh.setAlignmentX(Component.CENTER_ALIGNMENT);
			errHinh.setForeground(Color.RED);
			errHinh.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			imagePanel.add(errHinh);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			imagePanel.add(verticalStrut);
		}
		{
			JButton btnUpload = new JButton("Tải ảnh");
			btnUpload.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnUpload.setBackground(Color.BLACK);
			btnUpload.setForeground(new Color(255, 255, 255));
			btnUpload.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			btnUpload.setContentAreaFilled(false);
			btnUpload.setOpaque(true);
			btnUpload.addActionListener(e->uploadImage());
			imagePanel.add(btnUpload);
		}
	}

	private void textFieldInit(){
		getContentPane().add(txtPanel, BorderLayout.CENTER);
		GridBagLayout gbl_txtPanel = new GridBagLayout();
		gbl_txtPanel.columnWidths = new int[]{0, 0};
		gbl_txtPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_txtPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_txtPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		txtPanel.setLayout(gbl_txtPanel);
		{
			JLabel lblTenSP = new JLabel("Tên sản phẩm");
			lblTenSP.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblTenSP = new GridBagConstraints();
			gbc_lblTenSP.gridwidth = 4;
			gbc_lblTenSP.insets = new Insets(0, 10, 5, 10);
			gbc_lblTenSP.anchor = GridBagConstraints.WEST;
			gbc_lblTenSP.gridx = 0;
			gbc_lblTenSP.gridy = 0;
			txtPanel.add(lblTenSP, gbc_lblTenSP);
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
			txtPanel.add(txtTenSP, gbc_txtTenSP);
			txtTenSP.setColumns(10);
		}
		{
			errTenSP = new JLabel();
			errTenSP.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			errTenSP.setForeground(Color.RED);
			GridBagConstraints gbc_errTenSP = new GridBagConstraints();
			gbc_errTenSP.anchor = GridBagConstraints.WEST;
			gbc_errTenSP.gridwidth = 4;
			gbc_errTenSP.insets = new Insets(0, 10, 10, 10);
			gbc_errTenSP.gridx = 0;
			gbc_errTenSP.gridy = 2;
			txtPanel.add(errTenSP, gbc_errTenSP);
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
			txtPanel.add(lblDM, gbc_lblDM);
		}
		{
			cboDM.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_cboDM = new GridBagConstraints();
			gbc_cboDM.fill = GridBagConstraints.HORIZONTAL;
			gbc_cboDM.gridwidth = 4;
			gbc_cboDM.anchor = GridBagConstraints.WEST;
			gbc_cboDM.insets = new Insets(0, 10, 5, 10);
			gbc_cboDM.gridx = 0;
			gbc_cboDM.gridy = 4;
			txtPanel.add(cboDM, gbc_cboDM);
		}
		{
			JLabel lblGia = new JLabel("Giá bán");
			lblGia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblGia = new GridBagConstraints();
			gbc_lblGia.gridwidth = 4;
			gbc_lblGia.anchor = GridBagConstraints.WEST;
			gbc_lblGia.insets = new Insets(0, 10, 5, 10);
			gbc_lblGia.gridx = 0;
			gbc_lblGia.gridy = 5;
			txtPanel.add(lblGia, gbc_lblGia);
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
			gbc_txtGia.gridy = 6;
			txtPanel.add(txtGia, gbc_txtGia);
		}
		{
			errGia = new JLabel();
			errGia.setIcon(null);
			errGia.setForeground(Color.RED);
			errGia.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_errGia = new GridBagConstraints();
			gbc_errGia.gridwidth = 4;
			gbc_errGia.anchor = GridBagConstraints.WEST;
			gbc_errGia.insets = new Insets(0, 10, 10, 10);
			gbc_errGia.gridx = 0;
			gbc_errGia.gridy = 7;
			txtPanel.add(errGia, gbc_errGia);
		}
		{
			cboTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			cboTrangThai.addItem("Bị khóa");
			cboTrangThai.addItem("Hoạt động");
			{
				JLabel lblTrangThai = new JLabel("Trạng thái");
				lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				GridBagConstraints gbc_lblTrangThai = new GridBagConstraints();
				gbc_lblTrangThai.gridwidth = 4;
				gbc_lblTrangThai.anchor = GridBagConstraints.WEST;
				gbc_lblTrangThai.insets = new Insets(0, 10, 5, 10);
				gbc_lblTrangThai.gridx = 0;
				gbc_lblTrangThai.gridy = 8;
				txtPanel.add(lblTrangThai, gbc_lblTrangThai);
			}
		}
	}

	private void actionInit(){
		GridBagConstraints gbc_cboTrangThai = new GridBagConstraints();
		gbc_cboTrangThai.gridwidth = 4;
		gbc_cboTrangThai.anchor = GridBagConstraints.WEST;
		gbc_cboTrangThai.insets = new Insets(0, 10, 5, 10);
		gbc_cboTrangThai.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboTrangThai.gridx = 0;
		gbc_cboTrangThai.gridy = 9;
		txtPanel.add(cboTrangThai, gbc_cboTrangThai);
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 4;
		gbc_panel_1.anchor = GridBagConstraints.EAST;
		gbc_panel_1.insets = new Insets(0, 10, 10, 10);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 11;
		txtPanel.add(panel_1, gbc_panel_1);
		{
			btnSubmit = new JButton("Thêm");
			btnSubmit.setBackground(new Color(0, 128, 0));
			btnSubmit.setForeground(Color.WHITE);
			btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
			btnSubmit.setContentAreaFilled(false);
			btnSubmit.setOpaque(true);
			btnSubmit.addActionListener(e->submitForm());
			panel_1.add(btnSubmit);
		}
		{
			btnCancel = new JButton("Hủy");
			btnCancel.setForeground(Color.WHITE);
			btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
			btnCancel.setBackground(new Color(255, 51, 102));
			btnCancel.setContentAreaFilled(false);
			btnCancel.setOpaque(true);
			btnCancel.addActionListener(e->cancel());
			panel_1.add(btnCancel);
		}
	}

	private void loadComboBoxDM(List<DanhMucDTO> arr) {
		cboDM.removeAllItems();
		for(DanhMucDTO item : arr) 
			cboDM.addItem(item);
	}

	public void showAdd() {
		// load tất cả danh mục đang hoạt động 
		List<DanhMucDTO> listDM = danhMucBus.getAllActive();
		loadComboBoxDM(listDM);
		cboTrangThai.setEnabled(false);
		
		setTitle("Thêm sản phẩm");
		lblTitle.setText("Thêm sản phẩm");
		// reset action button
		btnSubmit.setText("Thêm");
		btnSubmit.setActionCommand("add");
		setVisible(true);
	}

	public void showEdit(int idSP) {
		SanPhamDTO sanPham = sanPhamBus.findByIdSP(idSP);
		txtTenSP.setText(sanPham.getTenSP());
		txtGia.setText(String.valueOf(sanPham.getGiaban()));
		ImageHelper img = new ImageHelper(200, 200, SanPhamDialog.class.getResource("/ASSET/Uploads/"+sanPham.getHinhanh()));
		lblImage.setIcon(img.getScaledImage());
		cboTrangThai.setSelectedItem(sanPham.getTrangthai() == 1 ? "Hoạt động" : "Bị khóa");
		
		// load tất cả danh mục đang hoạt động 
		List<DanhMucDTO> listDM = danhMucBus.getAllActiveExcept(sanPham.getIdDM());
		loadComboBoxDM(listDM);
		
		// set default
		DanhMucDTO dmuc = danhMucBus.findByIdDM(sanPham.getIdDM());
		cboDM.setSelectedItem(dmuc);
		
		setTitle("Sửa sản phẩm");
		lblTitle.setText("Sửa sản phẩm");
		// reset action button
		btnSubmit.setText("Cập nhật");
		btnSubmit.setActionCommand("edit_"+idSP);
		setVisible(true);
	}

	private boolean isError(boolean isEdit) {
		// remove existed errors
		JLabel[] errors = {errTenSP, errGia, errHinh};
		for (JLabel error : errors)
			error.setText("");

		boolean isError = false;
		
		// hinhanh
		if(!isEdit && uploadFile == null) {
			errHinh.setText("Hình sản phẩm không được để trống");
			isError = true;
		}
		
		// tenSP
		if(txtTenSP.getText().trim().equals("")) {
			errTenSP.setText("Tên sản phẩm không được để trống");
			isError = true;
		}
		
		// gia ban
		String giaban = txtGia.getText();
		if(giaban.trim().equals("")){
			errGia.setText("Giá bán không được để trống");
			isError = true;
		}else{
			try {
				int gia = Integer.parseInt(giaban); 
				if (gia <= 0) {
					errGia.setText("Giá bán phải lớn hơn 0");
					isError = true;
				}
			} catch (NumberFormatException e) {
				errGia.setText("Giá bán phải là số hợp lệ");
				isError = true;
			}
		}
		return isError;
	}

	private void submitForm() {
		// validate du lieu
		String actionCommand = btnSubmit.getActionCommand();
		int beginIndex = actionCommand.indexOf('_')+1;
		if(!isError(beginIndex != 0)) {
			// collect data
			String tenSP = txtTenSP.getText();
			int giaban = Integer.parseInt(txtGia.getText()); 
			DanhMucDTO dmucSelected = (DanhMucDTO) cboDM.getSelectedItem();
			int idDM = dmucSelected.getIdDM();
			int trangthai = cboTrangThai.getSelectedItem() == "Hoạt động" ? 1 : 0;
			
			// image
			String imageName = null;
			if(uploadFile != null) {
				String sourcePath = uploadFile.getAbsolutePath();
	            String destinationFolder = "src/ASSET/Uploads/";
	            String originalFileName = uploadFile.getName();
	            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
	            imageName = UUID.randomUUID().toString() + fileExtension;
	            String destinationPath = destinationFolder + imageName;

	            try {
	                // Copy the image to the destination folder
	                Files.copy(Paths.get(sourcePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
	            }catch (IOException ex) {
	                JOptionPane.showMessageDialog(this, "Lỗi khi tải ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
	            }
			}
			
			// action
			String error = "";
			if(beginIndex == 0) {
				// add
				error = sanPhamBus.add(new SanPhamDTO(tenSP, giaban, imageName, trangthai, idDM));
			}else {
				// update
				int idSP = Integer.parseInt(actionCommand.substring(beginIndex));
				error = sanPhamBus.update(new SanPhamDTO(idSP, tenSP, giaban, imageName, trangthai, idDM));
			}
			
			// show message
			if(error != "") {
				// fail
				JOptionPane.showMessageDialog(this, error, "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
			else {
				// success
				if(beginIndex == 0) 
					JOptionPane.showMessageDialog(this, "Thêm thành công ");
				else 
					JOptionPane.showMessageDialog(this, "Cập nhật thành công ");
			}
		}
	}

	private void cancel() {
		dispose();
	}

	private void uploadImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "jpeg"));
        int result = chooser.showOpenDialog(null);
        
        // Chọn ảnh từ máy
        try {
	        if (result == JFileChooser.APPROVE_OPTION) {
	            uploadFile = chooser.getSelectedFile();
	            // image preview
	            BufferedImage buff = ImageIO.read(uploadFile);
	            Image img = buff.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	            ImageIcon icon = new ImageIcon(img);
	            lblImage.setIcon(icon);
	        }
	        else uploadFile = null;
        }catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tải ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
	}
}
