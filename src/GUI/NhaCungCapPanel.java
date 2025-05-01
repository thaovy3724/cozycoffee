package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import GUI.Dialog.NhaCungCapDialog;

public class NhaCungCapPanel extends JPanel {
	private NhaCungCapBUS nhaCungCapBus = new NhaCungCapBUS();

	private static final long serialVersionUID = 1L;
	private JButton btnAdd, btnEdit, btnDel, btnSearch, btnReset;
	private JTable table;
	private JTextField txtSearch;
	private JPanel container, actionPanel, searchPanel;
	private JScrollPane tablePane;
	private DefaultTableModel tableModel;
	/**
	 * Create the panel.
	 */
	public NhaCungCapPanel() {
		setLayout(new BorderLayout(0, 0));

		container = new JPanel();
		add(container, BorderLayout.CENTER);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		tableModel = new DefaultTableModel(
		   new String[] {"ID", "Tên nhà cung cấp", "Email", "Điện thoại", "Trạng thái"}, 0)
		{                                                // (2) Mở đầu khai báo lớp vô danh (anonymous class)
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; // Không cho phép sửa ô nào cả
	        }                                                   // (3) Đóng method isCellEditable
	    };

		// actionBox init
		actionBoxInit();

		// searchBox init
		searchBoxInit();

		// table init
		tableInit();
	}

	private void actionBoxInit() {
		actionPanel = new JPanel();
		container.add(actionPanel);
		actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		btnAdd = new JButton("Thêm");
		btnAdd.setIcon(new ImageIcon(NhaCungCapPanel.class.getResource("/ASSET/Images/icons8_add_30px.png")));
		btnAdd.addActionListener(e->showAdd());
		actionPanel.add(btnAdd);

		btnEdit = new JButton("Sửa");
		btnEdit.setIcon(new ImageIcon(NhaCungCapPanel.class.getResource("/ASSET/Images/icons8_wrench_30px.png")));
		btnEdit.addActionListener(e->showEdit());
		actionPanel.add(btnEdit);

		btnDel = new JButton("Xóa");
		btnDel.setIcon(new ImageIcon(NhaCungCapPanel.class.getResource("/ASSET/Images/icons8_cancel_30px_1.png")));
		btnDel.addActionListener(e->delete());
		actionPanel.add(btnDel);
	}

	private void searchBoxInit() {
		searchPanel = new JPanel();
		container.add(searchPanel);
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		searchPanel.add(txtSearch);
		txtSearch.setColumns(20);

		btnSearch = new JButton("Tìm");
		ImageHelper imgSrch = new ImageHelper(20, 20, NhaCungCapPanel.class.getResource("/ASSET/Images/searchIcon.png"));
		btnSearch.setIcon(imgSrch.getScaledImage());
		btnSearch.addActionListener(e->search());
		searchPanel.add(btnSearch);

		btnReset = new JButton("Làm mới");
		ImageHelper imgReset = new ImageHelper(20, 20, NhaCungCapPanel.class.getResource("/ASSET/Images/icons8_replay_30px.png"));
		btnReset.setIcon(imgReset.getScaledImage());
		btnReset.addActionListener(e->{
			txtSearch.setText("");
			loadTable(null);
		});
		searchPanel.add(btnReset);
	}

	private void tableInit() {
		tablePane = new JScrollPane();
		container.add(tablePane);

		table = new JTable();
		tablePane.setViewportView(table);

		// load list
		loadTable(null);
	}

	private void loadTable(List<NhaCungCapDTO> arr) {
		tableModel.setRowCount(0); //This removes all the rows but keeps the column structure.
		if(arr == null) {
			arr = nhaCungCapBus.getAll();
		}
		// kiem tra mang co null ko
		if(arr.size() != 0) {
			for (NhaCungCapDTO nhaCungCap : arr) {
		        Object[] row = {
		            nhaCungCap.getIdNCC(),
		            nhaCungCap.getTenNCC(),
		            nhaCungCap.getEmail(),
		            nhaCungCap.getSdt(),
		            nhaCungCap.getTrangthai() == 1 ? "Hoạt động" : "Bị khóa",
		        };
		        tableModel.addRow(row);
		    }
		}
	    table.setModel(tableModel);
	}

	private void showEdit() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhà cung cấp");
		}
		else {
			int idNCC = (int) table.getValueAt(selectedRow, 0);
			NhaCungCapDialog nhaCungCapDialog = new NhaCungCapDialog();
			nhaCungCapDialog.showEdit(idNCC);
			// sau khi đóng dialog, reload table
			loadTable(null);
		}
	}

	private void showAdd() {
		NhaCungCapDialog nhaCungCapDialog = new NhaCungCapDialog();
		nhaCungCapDialog.showAdd();
		// sau khi đóng dialog, reload table
		loadTable(null);
	}

	private void search() {
		// get keyword
		String keyWord = txtSearch.getText();
		// validate
		if(keyWord.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập từ khóa tìm kiếm");
		} else {
			// tìm kiếm: nếu không tìm thấy thì trả về null
			List<NhaCungCapDTO> result = nhaCungCapBus.search(keyWord.trim());
			// hiển thị
			loadTable(result);
			// empty ô search
			txtSearch.setText("");
		}
	}

	private void delete() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhà cung cấp");
		}
		else {
			// tiến hành xóa
			int idNCC = (int) table.getValueAt(selectedRow, 0);
			// cập nhật lại CSDL
			// kiểm tra có lỗi ko, nếu có thì xuât thông báo lỗi
			if(nhaCungCapBus.delete(idNCC)) {
				JOptionPane.showMessageDialog(this, "Xóa thành công");
				// reload table
				loadTable(null);
			}
			else {
				JOptionPane.showMessageDialog(this, "Bạn không thể xóa nhà cung cấp này");
			}
		}
	}
}
