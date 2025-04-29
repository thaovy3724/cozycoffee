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
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;

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
		leftPanel.add(actionPanel, BorderLayout.NORTH);
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		actionPanel.add(txtSearch);
		txtSearch.setColumns(18);
		
		cboDM.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		List<DanhMucDTO> listDM = danhMucBus.getAllActive();
		loadComboBoxDM(listDM);
		actionPanel.add(cboDM);
		
		btnSearch = new JButton("Tìm");
		btnSearch.setBackground(Color.YELLOW);
		btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnSearch.setContentAreaFilled(false);
		btnSearch.setOpaque(true);
		btnSearch.addActionListener(e->search());
		actionPanel.add(btnSearch);
		// === Action Panel ===

		// === Product Panel ===
		productListPanel.setLayout(new GridLayout(0, 3, 10, 10));
		
		JScrollPane productListScrollPane = new JScrollPane(productListPanel);
		loadProduct(null);
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
		btnPay.setForeground(Color.WHITE);
		btnPay.setBackground(Color.RED);
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
		rightPanel.add(deletePanel);
		deletePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnDel = new JButton("Xóa");
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
		orderDetailPanel.setViewportView(table);
		rightPanel.add(orderDetailPanel);
		// ==== Order detail ====
	}

	private void loadProduct(List<SanPhamDTO> arr){
		// remove all items inside productListPanel
		productListPanel.removeAll();
		productListPanel.revalidate();
		productListPanel.repaint();

		// load san pham
		if(arr == null) arr = sanPhamBus.getAllActive();
		// kiem tra mang co null ko
		if(arr.size() != 0){
			ImageHelper addIcon = new ImageHelper(20, 20, BanHangPanel.class.getResource("/ASSET/Images/icons8_add_30px.png"));
			for(SanPhamDTO sp : arr){
				// hiển thị các card chứa thông tin sản phẩm (hình ảnh, tên SP, giá bán, nút mua)
				//==== Card ====
				JPanel productCard = new JPanel();
				productListPanel.add(productCard);
				productCard.setLayout(new BoxLayout(productCard, BoxLayout.Y_AXIS));
				
				JLabel lblProductImage = new JLabel("");
				lblProductImage.setAlignmentX(Component.CENTER_ALIGNMENT);
				ImageHelper productImg = new ImageHelper(100, 100, BanHangPanel.class.getResource("/ASSET/Uploads/"+sp.getHinhanh()));
				lblProductImage.setIcon(productImg.getScaledImage());
				productCard.add(lblProductImage);
				
				Component verticalStrut = Box.createVerticalStrut(5);
				productCard.add(verticalStrut);
				
				JLabel lblTenSP = new JLabel(sp.getTenSP());
				lblTenSP.setAlignmentX(Component.CENTER_ALIGNMENT);
				lblTenSP.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				productCard.add(lblTenSP);
				
				Component verticalStrut_1 = Box.createVerticalStrut(5);
				productCard.add(verticalStrut_1);
				
				JLabel lblGiaban = new JLabel(formatCurrency(sp.getGiaban()));
				lblGiaban.setFont(new Font("Segoe UI", Font.BOLD, 13));
				lblGiaban.setAlignmentX(Component.CENTER_ALIGNMENT);
				productCard.add(lblGiaban);
				
				JButton btnAdd = new JButton("Thêm");
				btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
				btnAdd.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				btnAdd.setIcon(addIcon.getScaledImage());
				btnAdd.addActionListener(e->addDetail(sp));
				productCard.add(btnAdd);
				//==== Card ====
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
		// thêm 1 dòng mới trong table
		Object[] row = {
			sp.getIdSP(),
			sp.getTenSP(),
			1,
			formatCurrency(sp.getGiaban()),
			formatCurrency(sp.getGiaban()),
		};
		tableModel.addRow(row);
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
			int newIdHD = hoaDonBus.add(hoadon, orderDetail); 
			if(newIdHD != -1){
				// Nếu thanh toán thành công, in hóa đơn ra file PDF
				try{
					hoaDonBus.printHoaDon(newIdHD);
				}catch(Exception e){
					JOptionPane.showMessageDialog(this, "Đã có lỗi khi in hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}else JOptionPane.showMessageDialog(this, "Đã có lỗi khi tạo hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
			
			// Remove all order detail
			tableModel.setRowCount(0);
		} else JOptionPane.showMessageDialog(this, "Bạn chưa thêm chi tiết", "Lỗi", JOptionPane.ERROR_MESSAGE);

	}

	private void delete() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) 
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn danh mục");
		else 
			tableModel.removeRow(selectedRow);
	}
}