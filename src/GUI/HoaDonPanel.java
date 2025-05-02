package GUI;

import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import BUS.CT_HoaDonBUS;
import BUS.HoaDonBUS;
import BUS.SanPhamBUS;
import DTO.CT_HoaDonDTO;
import DTO.HoaDonDTO;
import DTO.SanPhamDTO;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;

public class HoaDonPanel extends JPanel {
	private HoaDonBUS hoaDonBus = new HoaDonBUS();
	private CT_HoaDonBUS ct_HoaDonBus = new CT_HoaDonBUS();
	private SanPhamBUS sanPhamBus = new SanPhamBUS();

	private static final long serialVersionUID = 1L;
	private JButton btnSearch, btnReset;
	private JTable hoaDonTable;
	private JPanel container = new JPanel();
	private DefaultTableModel hoaDonTableModel, ctHoaDonTableModel;
	private JTable ctHoaDonTable;
	private JDateChooser dateChooserStart, dateChooserEnd;
	private JTextField txtMinTongTien, txtMaxTongTien;
	
	/**
	 * Create the panel.
	 */
	public HoaDonPanel() {
		setLayout(new BorderLayout(0, 0));
		
		add(container, BorderLayout.CENTER);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		// searchBox init
		searchBoxInit();
		
		// table init
		hoaDonTableInit();
		ctHoaDonTableInit();
	}
	
	private void searchBoxInit() {
		JPanel searchPanel = new JPanel();
		container.add(searchPanel);
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		searchPanel.setBackground(new Color(255, 240, 220));
		
		btnSearch = new JButton("Tìm");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnSearch.setBackground(new Color(255, 255, 255));
		ImageHelper imgSrch = new ImageHelper(20, 20, HoaDonPanel.class.getResource("/ASSET/Images/searchIcon.png"));
		btnSearch.setIcon(imgSrch.getScaledImage());
		btnSearch.addActionListener(e->search());
		
		JPanel searchContentPanel = new JPanel();
		searchPanel.add(searchContentPanel);
		searchContentPanel.setLayout(new BoxLayout(searchContentPanel, BoxLayout.Y_AXIS));
		
		JPanel searchContentPanel1 = new JPanel();
		searchContentPanel.add(searchContentPanel1);
		searchContentPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		searchContentPanel1.setBackground(new Color(255, 240, 220));

		JLabel lblDateStart = new JLabel("Từ ngày");
		lblDateStart.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		searchContentPanel1.add(lblDateStart);
		
		dateChooserStart = new JDateChooser();
		dateChooserStart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchContentPanel1.add(dateChooserStart);
		dateChooserStart.setDateFormatString("yyyy-MM-dd");
		((JTextField) dateChooserStart.getDateEditor().getUiComponent()).setEditable(false); // Tắt editor
		
		JLabel lblDateEnd = new JLabel("đến ngày");
		lblDateEnd.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		searchContentPanel1.add(lblDateEnd);
		
		dateChooserEnd = new JDateChooser();
		dateChooserEnd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchContentPanel1.add(dateChooserEnd);
		dateChooserEnd.setDateFormatString("yyyy-MM-dd");
		((JTextField) dateChooserEnd.getDateEditor().getUiComponent()).setEditable(false); // Tắt editor

		JPanel searchContentPanel2 = new JPanel();
		searchContentPanel.add(searchContentPanel2);
		searchContentPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		searchContentPanel2.setBackground(new Color(255, 240, 220));

		JLabel lblAmountStart = new JLabel("Khoảng tiền");
		lblAmountStart.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		searchContentPanel2.add(lblAmountStart);
		
		txtMinTongTien = new JTextField();
		txtMinTongTien.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		searchContentPanel2.add(txtMinTongTien);
		txtMinTongTien.setColumns(10);
		
		JLabel lblAmountEnd = new JLabel("đến");
		lblAmountEnd.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		searchContentPanel2.add(lblAmountEnd);
		
		txtMaxTongTien = new JTextField();
		txtMaxTongTien.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		searchContentPanel2.add(txtMaxTongTien);
		txtMaxTongTien.setColumns(10);
		searchPanel.add(btnSearch);
		
		btnReset = new JButton("Làm mới");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setBackground(new Color(255, 255, 255));
		btnReset.setFont(new Font("Segoe UI", Font.BOLD, 13));
		ImageHelper imgReset = new ImageHelper(20, 20, HoaDonPanel.class.getResource("/ASSET/Images/icons8_replay_30px.png"));
		btnReset.setIcon(imgReset.getScaledImage());
		btnReset.addActionListener(e->{
			loadHoaDon(hoaDonBus.getAll());
			ctHoaDonTableModel.setRowCount(0);
		});
		searchPanel.add(btnReset);
	}
	
	private void hoaDonTableInit() {
		hoaDonTableModel = new DefaultTableModel(
			new String[] {"Mã hóa đơn", "Ngày tạo", "idNV", "Tổng tiền"}, 0)
		{                                                // (2) Mở đầu khai báo lớp vô danh (anonymous class)
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Không cho phép sửa ô nào cả
			}                                                   // (3) Đóng method isCellEditable
		};   

		JScrollPane hoaDonScrollPane = new JScrollPane();
		hoaDonScrollPane.getViewport().setBackground(new Color(255, 240, 220));
		container.add(hoaDonScrollPane);
		
		hoaDonTable = new JTable();
		hoaDonTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Kiểm tra nếu dòng được chọn không phải dòng đang chọn cũ
                if (!e.getValueIsAdjusting()) { //Người dùng đã hoàn tất chọn (ổn định)
                    int selectedRow = hoaDonTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int idHD = (int) hoaDonTable.getValueAt(selectedRow, 0);
						loadCtHoaDon(idHD);
                    }
                }
            }
        });

		hoaDonScrollPane.setViewportView(hoaDonTable);

		// load list
		loadHoaDon(hoaDonBus.getAll());
	}

	private void ctHoaDonTableInit(){
		ctHoaDonTableModel = new DefaultTableModel(
		   new String[] {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"}, 0)
		{                                                // (2) Mở đầu khai báo lớp vô danh (anonymous class)
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; // Không cho phép sửa ô nào cả
	        }                                                   // (3) Đóng method isCellEditable
	    }; 

		JPanel ctHoaDonPanel = new JPanel();
		container.add(ctHoaDonPanel);
		ctHoaDonPanel.setLayout(new BoxLayout(ctHoaDonPanel, BoxLayout.Y_AXIS));
		ctHoaDonPanel.setBackground(new Color(255, 240, 220));
		
		JLabel lblChitiet = new JLabel("Chi tiết hóa đơn");
		lblChitiet.setFont(new Font("Segoe UI", Font.BOLD, 16));
		ctHoaDonPanel.add(lblChitiet);
		
		JScrollPane ctHoaDonScrollPane = new JScrollPane();
		ctHoaDonScrollPane.getViewport().setBackground(new Color(255, 240, 220));
		ctHoaDonPanel.add(ctHoaDonScrollPane);
		
		ctHoaDonTable = new JTable();
		ctHoaDonScrollPane.setViewportView(ctHoaDonTable);
	}
	
	private void loadHoaDon(List<HoaDonDTO> arr) {
		hoaDonTableModel.setRowCount(0); //This removes all the rows but keeps the column structure.
		if(arr == null) arr = hoaDonBus.getAll();
		int tongtien = 0; 
		for (HoaDonDTO hoaDon : arr) {
			tongtien = hoaDonBus.getTotalAmount(hoaDon.getIdHD());
			Object[] row = {
				hoaDon.getIdHD(),
				hoaDon.getNgaytao(),
				hoaDon.getIdTK(),
				tongtien
			};
			hoaDonTableModel.addRow(row);
		}
	    hoaDonTable.setModel(hoaDonTableModel);
	}

	private void loadCtHoaDon(int idHD) {
		ctHoaDonTableModel.setRowCount(0); //This removes all the rows but keeps the column structure.
		
		List<CT_HoaDonDTO> arr = ct_HoaDonBus.getAllByIdHD(idHD);
		SanPhamDTO sp;

		for (CT_HoaDonDTO ctHoaDon : arr) {
			sp = sanPhamBus.findByIdSP(ctHoaDon.getIdSP());
			Object[] row = {
				ctHoaDon.getIdSP(),
				sp.getTenSP(),
				ctHoaDon.getSoluong(),
				formatCurrency(ctHoaDon.getGialucdat()),
				formatCurrency(ctHoaDon.getSoluong() * ctHoaDon.getGialucdat())
			};
			ctHoaDonTableModel.addRow(row);
		}
	    ctHoaDonTable.setModel(ctHoaDonTableModel);
	}

	private void search() {
		// validate
		// nếu chưa nhập thông tin tìm kiếm (khoảng ngày && khoảng giá)
		Date dateStart = (Date) dateChooserStart.getDate();
		Date dateEnd = (Date) dateChooserEnd.getDate();

		String txtMinPrice = txtMinTongTien.getText().trim();
		String txtMaxPrice = txtMaxTongTien.getText().trim();

		List<HoaDonDTO> result = new ArrayList<>();
		if(dateStart == null && dateEnd == null &&
		 	txtMinPrice.isEmpty()&& txtMaxPrice.isEmpty()){
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập từ khóa tìm kiếm", "Lỗi", JOptionPane.ERROR_MESSAGE);
		 } else{
			try{
				Integer minPrice = null, maxPrice = null;
				if(!txtMinPrice.isEmpty()) minPrice = Integer.parseInt(txtMinPrice);
				if(!txtMaxPrice.isEmpty()) maxPrice = Integer.parseInt(txtMaxPrice);
				result = hoaDonBus.search(dateStart, dateEnd, minPrice, maxPrice);
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this, "Khoảng giá không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		 }
		// hiển thị
		loadHoaDon(result);
		// empty ô search
		dateChooserStart.setDate(null);
		dateChooserEnd.setDate(null);
		txtMinTongTien.setText("");
		txtMaxTongTien.setText("");
	}

	private String formatCurrency(int giaban){
		DecimalFormat formatter = new DecimalFormat("#, ###");
		String formatted  = formatter.format(giaban).replace(",", ".");
		return formatted + " ₫";
	}
}
