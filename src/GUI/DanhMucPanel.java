package GUI;

import javax.swing.table.DefaultTableModel;
import BUS.DanhMucBUS;
import DTO.DanhMucDTO;
import GUI.Dialog.DanhMucDialog;

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
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Cursor;

public class DanhMucPanel extends JPanel {
	private DanhMucBUS danhMucBus = new DanhMucBUS();

	private static final long serialVersionUID = 1L;
	private JButton btnAdd, btnEdit, btnDel, btnSearch, btnReset;
	private JTable table;
	private JPanel container = new JPanel();
	private JTextField txtSearch;
	private DefaultTableModel tableModel;
	
	/**
	 * Create the panel.
	 */
	public DanhMucPanel() {
		setLayout(new BorderLayout(0, 0));
		
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
		actionPanel.setBackground(new Color(255, 240, 220));
		
		btnAdd = new JButton("Thêm");
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnAdd.setBackground(new Color(173, 255, 47));
		btnAdd.setIcon(new ImageIcon(DanhMucPanel.class.getResource("/ASSET/Images/icons8_add_30px.png")));
		btnAdd.addActionListener(e->showAdd());
		actionPanel.add(btnAdd);
		
		btnEdit = new JButton("Sửa");
		btnEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnEdit.setBackground(new Color(135, 206, 235));
		btnEdit.setIcon(new ImageIcon(DanhMucPanel.class.getResource("/ASSET/Images/icons8_wrench_30px.png")));
		btnEdit.addActionListener(e->showEdit());
		actionPanel.add(btnEdit);
		
		btnDel = new JButton("Xóa");
		btnDel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDel.setBackground(new Color(255, 215, 0));
		btnDel.setIcon(new ImageIcon(DanhMucPanel.class.getResource("/ASSET/Images/icons8_cancel_30px_1.png")));
		btnDel.addActionListener(e->delete());
		actionPanel.add(btnDel);
	}
	
	private void searchBoxInit() {
		JPanel searchPanel = new JPanel();
		container.add(searchPanel);
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		searchPanel.setBackground(new Color(255, 240, 220));
		
		txtSearch = new JTextField();
		txtSearch.setPreferredSize(new Dimension(7, 30));
		txtSearch.setMinimumSize(new Dimension(7, 30));
		txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		searchPanel.add(txtSearch);
		txtSearch.setColumns(20);
		
		btnSearch = new JButton("Tìm");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnSearch.setBackground(new Color(255, 255, 255));
		ImageHelper imgSrch = new ImageHelper(20, 20, DanhMucPanel.class.getResource("/ASSET/Images/searchIcon.png"));
		btnSearch.setIcon(imgSrch.getScaledImage());
		btnSearch.addActionListener(e->search());
		searchPanel.add(btnSearch);
		
		btnReset = new JButton("Làm mới");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnReset.setBackground(new Color(255, 255, 255));
		ImageHelper imgReset = new ImageHelper(20, 20, DanhMucPanel.class.getResource("/ASSET/Images/icons8_replay_30px.png"));
		btnReset.setIcon(imgReset.getScaledImage());
		btnReset.addActionListener(e->{
			txtSearch.setText("");
			loadTable(danhMucBus.getAll());
		});
		searchPanel.add(btnReset);
	}
	
	private void tableInit() {
		tableModel = new DefaultTableModel(
			new String[] {"ID", "Tên danh mục", "Trạng thái"}, 0)
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
		tablePane.getViewport().setBackground(new Color(255, 240, 220));
		
		// load list
		loadTable(danhMucBus.getAll());
	}
	
	private void loadTable(List<DanhMucDTO> arr) {
		tableModel.setRowCount(0); //This removes all the rows but keeps the column structure.
		// kiem tra mang co null ko
		if(arr != null) 
			for (DanhMucDTO danhMuc : arr) {
		        Object[] row = {
		            danhMuc.getIdDM(),
		            danhMuc.getTenDM(),
		            danhMuc.getTrangthai() == 1 ? "Hoạt động" : "Bị khóa",
		        };
		        tableModel.addRow(row);
		    }
	    table.setModel(tableModel);
	}
	
	private void showEdit() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn danh mục");
		}
		else {
			int idDM = (int) table.getValueAt(selectedRow, 0);
			DanhMucDialog danhMucDialog = new DanhMucDialog();
			danhMucDialog.showEdit(idDM);
			// sau khi đóng dialog, reload table 
			loadTable(danhMucBus.getAll());
		}
	}
	
	private void showAdd() {
		DanhMucDialog danhMucDialog = new DanhMucDialog();
		danhMucDialog.showAdd();
		// sau khi đóng dialog, reload table 
		loadTable(danhMucBus.getAll());
	}

	private void search() {
		// get keyword
		String keyWord = txtSearch.getText();
		// validate
		if(keyWord.trim().equals(""))
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập từ khóa tìm kiếm");
		else {
			// tìm kiếm: nếu không tìm thấy thì trả về null
			List<DanhMucDTO> result = danhMucBus.search(keyWord.trim());
			// hiển thị
			loadTable(result);
		}
	}
		
	private void delete() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn danh mục");
		}
		else{
			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa danh mục này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
				// tiến hành xóa 
				int idDM = (int) table.getValueAt(selectedRow, 0);
				// cập nhật lại CSDL
				// kiểm tra có lỗi ko, nếu có thì xuât thông báo lỗi
				if(danhMucBus.delete(idDM)) {
					JOptionPane.showMessageDialog(this, "Xóa thành công");
					// reload table
					loadTable(danhMucBus.getAll());
				}
				else {
					JOptionPane.showMessageDialog(this, "Bạn không thể xóa danh mục này!");
				}
			}
		}
	}
}
