package GUI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import BUS.DanhMucBUS;
import BUS.HoaDonBUS;
import BUS.SanPhamBUS;
import DTO.CT_HoaDonDTO;
import DTO.DanhMucDTO;
import DTO.HoaDonDTO;
import DTO.SanPhamDTO;
import DTO.TaiKhoanDTO;

import javax.swing.JComboBox;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Cursor;

public class BanHangPanel extends JPanel {
	private SanPhamBUS sanPhamBus = new SanPhamBUS();
	private HoaDonBUS hoaDonBus = new HoaDonBUS();
	private DanhMucBUS danhMucBus = new DanhMucBUS();

	private static final long serialVersionUID = 1L;
	private JPanel panel = new JPanel();
	private JPanel rightPanel = new JPanel();
	private JPanel productListPanel = new JPanel();
	private JTextField txtSearch;
	private JLabel lblTotalQuantity, lblTotalAmount;
	private	JComboBox<DanhMucDTO> cboDM = new JComboBox<>();
	private JButton btnSearch, btnDel;
	private JTable table = new JTable();
	private DefaultTableModel tableModel;
	private TaiKhoanDTO currentUser;

	/**
	 * Create the panel.
	 */
	public BanHangPanel(TaiKhoanDTO currentUser) {
		this.currentUser = currentUser;
		setLayout(new BorderLayout(0, 0));
		
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
	
		leftPanelInit();
		rightPanelInit();
	}

	private void leftPanelInit(){
		JPanel leftPanel = new JPanel();
		panel.add(leftPanel);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		// === Action Panel ===
		JPanel actionPanel = new JPanel();
		actionPanel.setBackground(new Color(255, 240, 220));
		leftPanel.add(actionPanel, BorderLayout.NORTH);
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		actionPanel.add(txtSearch);
		txtSearch.setColumns(18);
		
		cboDM.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		List<DanhMucDTO> listDM = danhMucBus.getAllActive();
		loadComboBoxDM(listDM);
		actionPanel.add(cboDM);
		
		btnSearch = new JButton("Tìm");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ImageHelper searchIcon = new ImageHelper(20, 20, BanHangPanel.class.getResource("/ASSET/Images/searchIcon.png"));
		btnSearch.setIcon(searchIcon.getScaledImage());
		btnSearch.setBackground(new Color(245, 255, 250));
		btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnSearch.setContentAreaFilled(false);
		btnSearch.setOpaque(true);
		btnSearch.addActionListener(e->search());
		actionPanel.add(btnSearch);
		productListPanel.setBackground(new Color(255, 240, 220));
		
		JScrollPane productListScrollPane = new JScrollPane(productListPanel);
		productListPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		loadProduct(sanPhamBus.getAllActive());
		leftPanel.add(productListScrollPane, BorderLayout.CENTER);
		// === Product Panel ===
	}

	private void search() {
		// get keyword & danhmuc
		String keyWord = txtSearch.getText();		
		// tìm kiếm: nếu không tìm thấy thì trả về null
		List<SanPhamDTO> result = sanPhamBus.search(keyWord.trim(), (DanhMucDTO) cboDM.getSelectedItem());
		// hiển thị
		loadProduct(result);
		// empty ô search
		txtSearch.setText("");
	}

	private void loadComboBoxDM(List<DanhMucDTO> arr) {
		cboDM.removeAllItems();
		DanhMucDTO danhMuc = new DanhMucDTO();
		danhMuc.setTenDM("--Tất cả--");
		arr.add(danhMuc);
		for(DanhMucDTO item : arr) 
			cboDM.addItem(item);
	}
	
	private void rightPanelInit(){
		panel.add(rightPanel);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		orderDetailInit();
		paymentTotalInit();
	}

	private void paymentTotalInit(){
		// ==== Payment total ====
		JPanel paymentPanel = new JPanel();
		paymentPanel.setBackground(new Color(255, 240, 220));
		rightPanel.add(paymentPanel);
		GridBagLayout gbl_paymentPanel = new GridBagLayout();
		gbl_paymentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_paymentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_paymentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_paymentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		paymentPanel.setLayout(gbl_paymentPanel);
		
		JLabel lblTotalProduct = new JLabel("Tổng sản phẩm");
		lblTotalProduct.setFont(new Font("Segoe UI", Font.BOLD, 17));
		GridBagConstraints gbc_lblTotalProduct = new GridBagConstraints();
		gbc_lblTotalProduct.insets = new Insets(5, 5, 5, 5);
		gbc_lblTotalProduct.anchor = GridBagConstraints.WEST;
		gbc_lblTotalProduct.gridx = 0;
		gbc_lblTotalProduct.gridy = 0;
		paymentPanel.add(lblTotalProduct, gbc_lblTotalProduct);
		
		lblTotalQuantity = new JLabel();
		lblTotalQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		GridBagConstraints gbc_lblTotalQuantity = new GridBagConstraints();
		gbc_lblTotalQuantity.anchor = GridBagConstraints.WEST;
		gbc_lblTotalQuantity.gridwidth = 2;
		gbc_lblTotalQuantity.insets = new Insets(5, 5, 5, 5);
		gbc_lblTotalQuantity.gridx = 1;
		gbc_lblTotalQuantity.gridy = 0;
		paymentPanel.add(lblTotalQuantity, gbc_lblTotalQuantity);
		
		JButton btnPay = new JButton("Thanh toán");
		btnPay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPay.setForeground(Color.WHITE);
		btnPay.setBackground(new Color(244, 67, 54));
		btnPay.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnPay.setContentAreaFilled(false);
		btnPay.setOpaque(true);
		btnPay.addActionListener(e->pay());
		GridBagConstraints gbc_btnPay = new GridBagConstraints();
		gbc_btnPay.gridwidth = 2;
		gbc_btnPay.gridheight = 2;
		gbc_btnPay.fill = GridBagConstraints.BOTH;
		gbc_btnPay.insets = new Insets(5, 5, 5, 5);
		gbc_btnPay.gridx = 7;
		gbc_btnPay.gridy = 0;
		paymentPanel.add(btnPay, gbc_btnPay);
		
		JLabel lblTongTien = new JLabel("Tổng tiền");
		lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 17));
		GridBagConstraints gbc_lblTongTien = new GridBagConstraints();
		gbc_lblTongTien.anchor = GridBagConstraints.WEST;
		gbc_lblTongTien.insets = new Insets(5, 5, 5, 5);
		gbc_lblTongTien.gridx = 0;
		gbc_lblTongTien.gridy = 1;
		paymentPanel.add(lblTongTien, gbc_lblTongTien);
		
		lblTotalAmount = new JLabel();
		lblTotalAmount.setForeground(Color.BLACK);
		lblTotalAmount.setFont(new Font("Segoe UI", Font.BOLD, 20));
		GridBagConstraints gbc_lblTotalAmount = new GridBagConstraints();
		gbc_lblTotalAmount.anchor = GridBagConstraints.WEST;
		gbc_lblTotalAmount.insets = new Insets(5, 5, 0, 5);
		gbc_lblTotalAmount.gridwidth = 2;
		gbc_lblTotalAmount.gridx = 1;
		gbc_lblTotalAmount.gridy = 1;
		paymentPanel.add(lblTotalAmount, gbc_lblTotalAmount);
		
		// ==== Payment total ====
	}

	private void orderDetailInit(){
		JPanel deletePanel = new JPanel();
		deletePanel.setBackground(new Color(255, 240, 220));
		rightPanel.add(deletePanel);
		deletePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnDel = new JButton("Xóa");
		btnDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnDel.setBackground(new Color(255, 215, 0));
		btnDel.setIcon(new ImageIcon(BanHangPanel.class.getResource("/ASSET/Images/icons8_cancel_30px_1.png")));
		btnDel.addActionListener(e->delete());
		deletePanel.add(btnDel);
		// ==== Order detail ====
		tableModel = new DefaultTableModel(
		   new String[] {"ID", "Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"}, 0)
		{                                                //(2) Mở đầu khai báo lớp vô danh (anonymous class)
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 2; //Chỉ cho sửa Số lượng
			}
	    };   

		//Format số lượng chỉ cho nhập số
		JTextField numericField = new JTextField();
		((AbstractDocument) numericField.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				if (string.matches("\\d+")) super.insertString(fb, offset, string, attr);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				if (text.matches("\\d*")) super.replace(fb, offset, length, text, attrs);
			}
		});
		
		tableModel.addTableModelListener(e->{
			// === Cập nhật giá tiền khi thay đổi số lượng ===
			int row = e.getFirstRow();
			int col = e.getColumn();

			if(col == 2){
				try{
					String soLuongValue = tableModel.getValueAt(row, 2).toString();
					int soLuong = 0;
					if(soLuongValue != "") soLuong = Integer.parseInt(soLuongValue);
					int donGia = parseCurrency(tableModel.getValueAt(row, 3).toString());
					int thanhTien = soLuong * donGia;
					tableModel.setValueAt(formatCurrency(thanhTien), row, 4);
				} catch(Exception ex){
					tableModel.setValueAt(formatCurrency(0), row, 4);
				}
			}
			// === Cập nhật giá tiền khi thay đổi số lượng ===

			// Khi có bất kì sự thay đổi nào trong bảng chi tiết, 
			// cập nhật lại tổng sản phẩm và tổng tiền
			int rowCount = tableModel.getRowCount();
			int totalQuantity = 0;
			int totalAmount = 0;
			for(int i = 0; i<rowCount; i++){
				totalQuantity += Integer.parseInt(tableModel.getValueAt(i, 2).toString());
				totalAmount += parseCurrency(tableModel.getValueAt(i, 4).toString());
			}

			lblTotalQuantity.setText(String.valueOf(totalQuantity));
			lblTotalAmount.setText(formatCurrency(totalAmount));
		});

		table.setModel(tableModel);
		table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(numericField));
		
		JScrollPane orderDetailPanel = new JScrollPane();
		orderDetailPanel.setBackground(new Color (255, 245, 228));
		orderDetailPanel.setViewportView(table);
		rightPanel.add(orderDetailPanel);
		// ==== Order detail ====
	}

	private void loadProduct(List<SanPhamDTO> arr) {
	    // Remove all items inside productListPanel
	    productListPanel.removeAll();
	    productListPanel.revalidate();
	    productListPanel.repaint();

	    // Load san pham
	    if (arr != null){
			ImageHelper addIcon = new ImageHelper(20, 20, BanHangPanel.class.getResource("/ASSET/Images/icons8_add_30px.png"));

			for (SanPhamDTO sp : arr) {
				// Hiển thị các card chứa thông tin sản phẩm
				// ==== Card ====
				JPanel productCard = new JPanel();
				productCard.setBackground(new Color(245, 222, 179));
				productListPanel.add(productCard);
				productCard.setLayout(new BoxLayout(productCard, BoxLayout.Y_AXIS));
				productCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

				// Giới hạn kích thước của productCard
				productCard.setPreferredSize(new Dimension(120, 165)); // Điều chỉnh chiều cao và chiều rộng
				productCard.setMaximumSize(new Dimension(120, 165));   // Đảm bảo không vượt quá kích thước này

				// Hình ảnh sản phẩm
				JLabel lblProductImage = new JLabel("");
				lblProductImage.setAlignmentX(Component.CENTER_ALIGNMENT);
	//	        ImageHelper productImg = new ImageHelper(80, 80, BanHangPanel.class.getResource("/ASSET/Uploads/360_F_243123463_zTooub557xEWABDLk0jJklDyLSGl2jrr.jpg"));
				ImageHelper productImg = new ImageHelper(80, 80, BanHangPanel.class.getResource("/ASSET/Uploads/" + sp.getHinhanh()));
				lblProductImage.setIcon(productImg.getScaledImage());
				productCard.add(lblProductImage);

				// Khoảng cách nhỏ hơn
				Component verticalStrut = Box.createVerticalStrut(5); // Giảm từ 5 xuống 3
				productCard.add(verticalStrut);

				// Tên sản phẩm
	//	        JLabel lblTenSP = new JLabel("Cafe");
				JLabel lblTenSP = new JLabel(sp.getTenSP());
				lblTenSP.setAlignmentX(Component.CENTER_ALIGNMENT);
				lblTenSP.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Giảm font size từ 15 xuống 14
				productCard.add(lblTenSP);

				Component verticalStrut_1 = Box.createVerticalStrut(3); // Giảm từ 5 xuống 3
				productCard.add(verticalStrut_1);

				// Giá bán
	//	        JLabel lblGiaban = new JLabel(formatCurrency(100000));
				JLabel lblGiaban = new JLabel(formatCurrency(sp.getGiaban()));
				lblGiaban.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Giảm font size từ 13 xuống 12
				lblGiaban.setAlignmentX(Component.CENTER_ALIGNMENT);
				productCard.add(lblGiaban);

				// Nút "Thêm"
				JButton btnAdd = new JButton("Thêm");
				btnAdd.setForeground(new Color(255, 255, 255));
				btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnAdd.setBackground(new Color(0, 128, 0));
				btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
				btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Giảm font size từ 13 xuống 12
				btnAdd.setIcon(addIcon.getScaledImage());
				btnAdd.addActionListener(e -> addDetail(sp));
				
				Component verticalStrut_2 = Box.createVerticalStrut(4);
				productCard.add(verticalStrut_2);
				productCard.add(btnAdd);
				// ==== Card ====
			}
		}
	}

	private String formatCurrency(int giaban){
		DecimalFormat formatter = new DecimalFormat("#, ###");
		String formatted  = formatter.format(giaban).replace(",", ".");
		return formatted + " ₫";
	}

	private int parseCurrency(String formatted){
		return Integer.parseInt(formatted.replaceAll("[^0-9]",""));
	}

	private void addDetail(SanPhamDTO sp){
		// kiểm tra nếu idSP đã tồn tại trong tableModel thì cộng thêm số lượng vào
		int rowCount = tableModel.getRowCount();

		boolean isExist = false;
		int quantityTmp = 0;
		for(int i = 0; i < rowCount; i++){
			if(sp.getIdSP() == (int) tableModel.getValueAt(i, 0)){
				isExist = true;
				// kiểm tra nếu ô só lượng bị rỗng, gán số lượng bằng 0 và cộng thêm 1
				try{
					quantityTmp = (int) tableModel.getValueAt(i, 2);
					tableModel.setValueAt(quantityTmp+1, i, 2);
				}catch(NumberFormatException e){
					tableModel.setValueAt(1, i, 2);
				}
			}
		}
		// thêm 1 dòng mới trong table nếu sản phẩm chưa tồn tại
		if(!isExist){
			Object[] row = {
				sp.getIdSP(),
				sp.getTenSP(),
				1,
				formatCurrency(sp.getGiaban()),
				formatCurrency(sp.getGiaban()),
			};
			tableModel.addRow(row);
		}
	}

	private void pay(){
		int rowCount = tableModel.getRowCount();
		if(rowCount > 0){
			// Tạo hóa đơn
			// Collect data
			List<CT_HoaDonDTO> orderDetail = new ArrayList<>();
			int idSP, soLuong, gialucdat; 
			for(int i = 0; i < rowCount; i++){
				idSP = (int) tableModel.getValueAt(i, 0);
				soLuong = (int) tableModel.getValueAt(i, 2);
				gialucdat = parseCurrency(tableModel.getValueAt(i, 3).toString());
				orderDetail.add(new CT_HoaDonDTO(idSP, soLuong, gialucdat));
			}
			LocalDate currentDate = LocalDate.now();
			HoaDonDTO hoadon = new HoaDonDTO(currentDate, currentUser.getIdTK());
			// trả về idHD mới nhất (nếu thành công), thất bại trả về -1 
			String result = hoaDonBus.add(hoadon, orderDetail); 
			
			try{
				// nếu lấy newIdHD thành công, in ra file pdf, ngược lại xuất thông báo lỗi
				int newIdHD = Integer.parseInt(result);
				try{
					// Remove all order detail
					tableModel.setRowCount(0);
					hoaDonBus.printHoaDon(newIdHD);
				}catch(Exception ex){
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		} else JOptionPane.showMessageDialog(this, "Bạn chưa thêm sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);

	}

	private void delete() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) 
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn sản phẩm");
		else 
			tableModel.removeRow(selectedRow);
	}
}