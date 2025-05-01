package GUI;

import javax.swing.table.DefaultTableModel;
import BUS.SanPhamBUS;
import DTO.SanPhamDTO;
import GUI.Dialog.SanPhamDialog;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.util.List;

public class SanPhamPanel extends JPanel {
	private SanPhamBUS sanPhamBus = new SanPhamBUS();

	private static final long serialVersionUID = 1L;
	private JButton btnAdd, btnEdit, btnDel, btnSearch, btnReset;
	private JTable table;
	private JPanel container;
	private JTextField txtSearch;
	private DefaultTableModel tableModel;
	/**
	 * Create the panel.
	 */
	public SanPhamPanel() {
		setLayout(new BorderLayout(0, 0));
		
		container = new JPanel();
		add(container, BorderLayout.CENTER);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		    
		// actionBox init
		actionBoxInit();
		
		// searchBox init
		searchBoxInit();
		
		// table init
		tableInit();
	}
	
	private void actionBoxInit() {
		JPanel actionPanel = new JPanel();
		container.add(actionPanel);
		actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		btnAdd = new JButton("Thêm");
		btnAdd.setIcon(new ImageIcon(SanPhamPanel.class.getResource("/ASSET/Images/icons8_add_30px.png")));
		btnAdd.addActionListener(e->showAdd());
		actionPanel.add(btnAdd);
		
		btnEdit = new JButton("Sửa");
		btnEdit.setIcon(new ImageIcon(SanPhamPanel.class.getResource("/ASSET/Images/icons8_wrench_30px.png")));
		btnEdit.addActionListener(e->showEdit());
		actionPanel.add(btnEdit);
		
		btnDel = new JButton("Xóa");
		btnDel.setIcon(new ImageIcon(SanPhamPanel.class.getResource("/ASSET/Images/icons8_cancel_30px_1.png")));
		btnDel.addActionListener(e->delete());
		actionPanel.add(btnDel);
	}
	
	private void searchBoxInit() {
		JPanel searchPanel = new JPanel();
		container.add(searchPanel);
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		searchPanel.add(txtSearch);
		txtSearch.setColumns(20);
		
		btnSearch = new JButton("Tìm");
		ImageHelper imgSrch = new ImageHelper(20, 20, SanPhamPanel.class.getResource("/ASSET/Images/searchIcon.png"));
		btnSearch.setIcon(imgSrch.getScaledImage());
		btnSearch.addActionListener(e->search());
		searchPanel.add(btnSearch);
		
		btnReset = new JButton("Làm mới");
		ImageHelper imgReset = new ImageHelper(20, 20, SanPhamPanel.class.getResource("/ASSET/Images/icons8_replay_30px.png"));
		btnReset.setIcon(imgReset.getScaledImage());
		btnReset.addActionListener(e->{
			txtSearch.setText("");
			loadTable(sanPhamBus.getAll());
		});
		searchPanel.add(btnReset);
	}
	
	private void tableInit() {
		tableModel = new DefaultTableModel(
		   new String[] {"ID", "Tên sản phẩm", "Giá bán", "Trạng thái"}, 0)
		{                                                // (2) Mở đầu khai báo lớp vô danh (anonymous class)
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; // Không cho phép sửa ô nào cả
	        }                                                   // (3) Đóng method isCellEditable
	    };   

		JScrollPane tablePane = new JScrollPane();
		container.add(tablePane);
		
		table = new JTable();
		tablePane.setViewportView(table);
		
		// load list
		loadTable(sanPhamBus.getAll());
	}
	
	private void loadTable(List<SanPhamDTO> arr) {
		tableModel.setRowCount(0); //This removes all the rows but keeps the column structure.
		// kiem tra mang co null ko
		if(arr != null) 
			for (SanPhamDTO sanPham : arr) {
		        Object[] row = {
		            sanPham.getIdSP(),
		            sanPham.getTenSP(),
					sanPham.getGiaban(),
		            sanPham.getTrangthai() == 1 ? "Hoạt động" : "Bị khóa",
		        };
		        tableModel.addRow(row);
		    }
	    table.setModel(tableModel);
	}
	
	private void showEdit() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn sản phẩm");
		}
		else {
			int idSP = (int) table.getValueAt(selectedRow, 0);
			SanPhamDialog sanPhamDialog = new SanPhamDialog();
			sanPhamDialog.showEdit(idSP);
			// sau khi đóng dialog, reload table 
			loadTable(sanPhamBus.getAll());
		}
	}
	
	private void showAdd() {
		SanPhamDialog sanPhamDialog = new SanPhamDialog();
		sanPhamDialog.showAdd();
		// sau khi đóng dialog, reload table 
		loadTable(sanPhamBus.getAll());
	}

	private void search() {
		// get keyword
		String keyWord = txtSearch.getText();
		// validate
		if(keyWord.trim().equals(""))
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập từ khóa tìm kiếm");
		else {
			// tìm kiếm: nếu không tìm thấy thì trả về null
			List<SanPhamDTO> result = sanPhamBus.search(keyWord.trim());
			// hiển thị
			loadTable(result);
			// empty ô search
			txtSearch.setText("");
		}
	}
	
	private void delete() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn sản phẩm");
		}
		else {
			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
				// tiến hành xóa
				int idSP = (int) table.getValueAt(selectedRow, 0);
				// cập nhật lại CSDL
				// kiểm tra có lỗi ko, nếu có thì xuât thông báo lỗi
				if(sanPhamBus.delete(idSP)) {
					JOptionPane.showMessageDialog(this, "Xóa thành công");
					// reload table
					loadTable(sanPhamBus.getAll());
				}
				else {
					JOptionPane.showMessageDialog(this, "Bạn không thể xóa sản phẩm này");
				}
			}
		}
	}
}
