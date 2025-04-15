package GUI;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import BUS.DanhMucBUS;
import DTO.DanhMucDTO;

import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.List;
import DTO.ComboItem;

public class DanhMucPanel2 extends JPanel {
	private DanhMucBUS danhMucBus = new DanhMucBUS();
	
	private static final long serialVersionUID = 1L;
	private JTextField timKiem_Txt;
	private JTextField tenDM_Txt;
	private JTable table;
	private JComboBox<ComboItem> dmucCha_Op;
	private JButton them_Btn;
	private JButton sua_Btn;
	private JButton xoa_Btn;
	private JButton boChon_Btn;


	/**
	 * Create the panel.
	 */
	public DanhMucPanel2() {
		init();
	}
	
	public void init() {
		// panel init
		setLayout(new BorderLayout());
		
		// danh muc list
		List<DanhMucDTO>danhMucList = danhMucBus.getAll();
		
		// infoBox init
		infoBoxInit(danhMucList);
		
		// actionBox init
		actionBoxInit();
		
		// table init
		tableInit(danhMucList);
	}
	
	public void infoBoxInit(List<DanhMucDTO> arr) {
		// ten DM
		JLabel tenDM_Lb = new JLabel("Tên danh mục");
		tenDM_Lb.setHorizontalAlignment(SwingConstants.CENTER);
		tenDM_Lb.setFont(new Font("Tahoma", Font.BOLD, 12));
		tenDM_Lb.setBounds(112, 41, 126, 42);
		add(tenDM_Lb);
		
		tenDM_Txt = new JTextField();
		tenDM_Txt.setHorizontalAlignment(SwingConstants.LEFT);
		tenDM_Txt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tenDM_Txt.setBounds(248, 47, 335, 32);
		add(tenDM_Txt);
		tenDM_Txt.setColumns(10);
		
		// DM cha
		JLabel dmucCha_Lb = new JLabel("Danh mục cha");
		dmucCha_Lb.setHorizontalAlignment(SwingConstants.CENTER);
		dmucCha_Lb.setFont(new Font("Tahoma", Font.BOLD, 12));
		dmucCha_Lb.setBounds(112, 84, 126, 42);
		add(dmucCha_Lb);
		
		dmucCha_Op = new JComboBox<ComboItem>();
//		dmucCha_Op.setToolTipText("");
		dmucCha_Op.setBounds(248, 90, 335, 32);
		add(dmucCha_Op);
		loadComboBoxDMCha(arr);
	}
	
	public void actionBoxInit() {
		// them
		them_Btn = new JButton("Thêm");
		them_Btn.setBackground(Color.GREEN);
		them_Btn.setBounds(113, 194, 114, 32);
		them_Btn.addActionListener(e -> them());
		add(them_Btn);
		
		// sua
		sua_Btn = new JButton("Sửa");
		sua_Btn.setBackground(Color.ORANGE);
		sua_Btn.setBounds(248, 194, 114, 32);
		sua_Btn.addActionListener(e -> sua());
		add(sua_Btn);
		
		// xoa
		xoa_Btn = new JButton("Xóa");
		xoa_Btn.setBackground(Color.RED);
		xoa_Btn.setBounds(395, 194, 114, 32);
		xoa_Btn.addActionListener(e -> xoa());
		add(xoa_Btn);
		
		// bo chon
		boChon_Btn = new JButton("Bỏ chọn");
		boChon_Btn.setBackground(Color.BLUE);
		boChon_Btn.setBounds(538, 194, 114, 32);
		boChon_Btn.addActionListener(e -> boChon());
		add(boChon_Btn);
		// disable nut sua, xoa, bo chon
		disableButton(true, false, false, false);
		
		// tim kiem
		timKiem_Txt = new JTextField();
		timKiem_Txt.setBounds(112, 237, 525, 42);
		add(timKiem_Txt);
		timKiem_Txt.setHorizontalAlignment(SwingConstants.CENTER);
		timKiem_Txt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		timKiem_Txt.setText("");
		timKiem_Txt.setColumns(10);
		
		JButton timKiem_Btn = new JButton("");
		timKiem_Btn.setBounds(637, 237, 79, 42);
		ImageHelper icon = new ImageHelper(
				20, 
				20,
				DanhMucPanel2.class.getResource("/ASSET/Images/searchIcon.png")
				);
		timKiem_Btn.setIcon(icon.getScaledImage());
		timKiem_Btn.addActionListener(e -> timKiem());
		add(timKiem_Btn);
	}
	
	public void disableButton(boolean them, boolean sua, boolean xoa, boolean boChon) {
		them_Btn.setEnabled(them);
		sua_Btn.setEnabled(sua);
		xoa_Btn.setEnabled(xoa);
		boChon_Btn.setEnabled(boChon);
	}
	
	public void tableInit(List<DanhMucDTO> arr) {
		// scroll pane
		JScrollPane dmuc_Tb = new JScrollPane();
		dmuc_Tb.setBounds(112, 290, 604, 207);
		add(dmuc_Tb);
		
		// table
		table = new JTable();
		dmuc_Tb.setViewportView(table);
		
		// load list
		loadTable(arr);
		
		// xử lý sự kiện khi 1 dòng được chọn
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		    	// disable nút thêm
		    	disableButton(false, true, true, true);
		        // Chỉ xử lý khi sự kiện đã kết thúc (tránh gọi 2 lần)
		        if (!e.getValueIsAdjusting()) {
        			// getValueAt: return Object
		    		int selectedRow = table.getSelectedRow();
		    		if(selectedRow != -1) {
		    			// edit form
	        			String tenDM = (String) table.getValueAt(selectedRow, 1);
	        			int idDMCha = (int) table.getValueAt(selectedRow, 3);
	        			// kiểm tra trạng thái danh mục, nếu đang khóa thì đặt lại nút 'xóa' là 'mở khóa'
	        			int trangthai = (int) table.getValueAt(selectedRow, 2);
	        			if(trangthai == 0) xoa_Btn.setText("Mở khóa");
	        			tenDM_Txt.setText(tenDM);
	        			DanhMucDTO dmucCha = danhMucBus.findByIdDM(idDMCha);
	        			if(dmucCha == null) dmucCha_Op.setSelectedIndex(0);
	        			else
	        				dmucCha_Op.setSelectedItem(new ComboItem(dmucCha.getIdDM(), dmucCha.getTenDM()));
		    		}
        			
		         }
		    }
		});

	}
	
	// setUpComboBox
	public void loadComboBoxDMCha(List<DanhMucDTO> arr) {
		dmucCha_Op.removeAllItems();
		dmucCha_Op.addItem(new ComboItem(-1, "---Chọn danh mục---"));
		for(DanhMucDTO item : arr) 
			dmucCha_Op.addItem(new ComboItem(item.getIdDM(), item.getTenDM()));
	}
	
	// load danh muc list
	public void loadTable(List<DanhMucDTO> arr) {
		// khoi tao cot 
		DefaultTableModel tableModel = new DefaultTableModel(
	        new String[] {"ID", "Tên danh mục", "Trạng thái", "ID cha"}, 0
	    ) {
			 @Override
			    public boolean isCellEditable(int row, int column) {
			        return false; // No cells can be edited
			    }
		};
	
		// kiem tra mang co null ko
		if(arr.size() != 0) 
			for (DanhMucDTO danhMuc : arr) {
		        Object[] row = {
		            danhMuc.getIdDM(),
		            danhMuc.getTenDM(),
		            danhMuc.getTrangthai(),
		            danhMuc.getIdDMCha()
		        };
		        tableModel.addRow(row);
		    }
	    table.setModel(tableModel);
	}
	
	public void them() {
		try {
			// validate
			if(tenDM_Txt.getText().trim().equals(""))
				JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin");
			else {
				DanhMucDTO danhMuc = new DanhMucDTO();
				danhMuc.setTenDM(tenDM_Txt.getText());
				// getSelectedItem() của JComboBox trả về kiểu Object nên phải cast lại thành kiểu ComboItem 
				ComboItem dmucCha = (ComboItem) dmucCha_Op.getSelectedItem();
				danhMuc.setIdDMCha(dmucCha.getKey());
				
				// them vao CSDL
				String error = danhMucBus.add(danhMuc);
				// kiểm tra có lỗi ko, nếu có thì xuât thông báo lỗi
				if(error!="")
					JOptionPane.showMessageDialog(this, error);
				else {
					// thông báo thêm mới thành công và load lại table
					JOptionPane.showMessageDialog(this, "Thêm mới thành công ");
					refresh();
				}
			} 
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Thông tin không hợp lệ");
		}
	}
	
	public void sua() {
		// kiểm tra người dùng đã chọn dòng cần sửa chưa
		int selectedRow = table.getSelectedRow();
		if(selectedRow == -1)
			JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục cần sửa");
		else {	
		// nếu đã sửa xong, kiểm tra thông tin sau khi sửa (trong ô text box) và tiến hành update
			try {
				// validate
				if(tenDM_Txt.getText().trim().equals(""))
					JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin");
				else {
					int idDM = (int) table.getValueAt(selectedRow, 0);
					String tenDM = tenDM_Txt.getText();
					int trangthai = (int) table.getValueAt(selectedRow, 2);
					// getSelectedItem() của JComboBox trả về kiểu Object nên phải cast lại thành kiểu ComboItem 
					ComboItem dmucChaSelected = (ComboItem) dmucCha_Op.getSelectedItem();
					int idDMCha = dmucChaSelected.getKey();
					DanhMucDTO danhMuc = new DanhMucDTO(idDM, tenDM, trangthai, idDMCha);
					
					// cập nhật lại CSDL
					String error = danhMucBus.update(danhMuc);
					// kiểm tra có lỗi ko, nếu có thì xuât thông báo lỗi
					if(error!="")
						JOptionPane.showMessageDialog(this, error);
					else {
						// thông báo cập nhật thành công 
						JOptionPane.showMessageDialog(this, "Cập nhật thành công ");
						refresh();
					}
				} 
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Thông tin không hợp lệ");
			}
		}
	}
	
	public void xoa() {
		int selectedRow = table.getSelectedRow();
		int idDM = (int) table.getValueAt(selectedRow, 0);
		int trangthai = (int) table.getValueAt(selectedRow, 2);
		
		// cập nhật lại CSDL
		String success = "";
		if(trangthai == 0) {
			if(danhMucBus.unlock(idDM)) success = "Mở khóa thành công";
		}
		else success = danhMucBus.delete(idDM);
		// kiểm tra có lỗi ko, nếu có thì xuât thông báo lỗi
		if(success == "")
			JOptionPane.showMessageDialog(this, "Xảy ra lỗi");
		else {
			JOptionPane.showMessageDialog(this, success);
		}
		refresh();
	}
	
	public void boChon() {
		// empty các ô input text
		tenDM_Txt.setText("");
		timKiem_Txt.setText("");
		xoa_Btn.setText("Xóa");
		
		// deselect dong duoc chon
		table.clearSelection();
		// deselect combo item duoc chon ve item mac dinh
		dmucCha_Op.setSelectedIndex(0);
	}
	
	public void timKiem() {
		// get keyword
		String keyWord = timKiem_Txt.getText();
		// validate
		if(keyWord.trim().equals(""))
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập từ khóa tìm kiếm");
		else {
			// tìm kiếm: nếu không tìm thấy thì trả về null
			List<DanhMucDTO> result = danhMucBus.search(keyWord.trim());
			// hiển thị
			loadTable(result);
			// empty ô search
			timKiem_Txt.setText("");
		}
	}
	
	public void refresh() {
		// empty các ô input text
		tenDM_Txt.setText("");
		timKiem_Txt.setText("");
		xoa_Btn.setText("Xóa");
		// disable sua, xoa, bo chon
		disableButton(true, false, false, false);
		
		// reload data
		List<DanhMucDTO>danhMucList = danhMucBus.getAll();
		
		// combobox
		loadComboBoxDMCha(danhMucList);

		// load lại table
		loadTable(danhMucList);

	}
}
