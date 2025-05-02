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

import BUS.PhieuNhapBUS;
import DTO.PhieuNhapDTO;
import DTO.TaiKhoanDTO;
import GUI.Dialog.PhieuNhapDialog;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Cursor;

public class PhieuNhapPanel extends JPanel {
	private PhieuNhapBUS phieuNhapBus = new PhieuNhapBUS();

	private static final long serialVersionUID = 1L;
	private JButton btnAdd, btnEdit, btnDel, btnDetail, btnSearch, btnReset;
	private JTable table;
	private JPanel container = new JPanel();
	private JTextField txtSearch;
	private DefaultTableModel tableModel;
	private TaiKhoanDTO currentUser;

	/**
	 * Create the panel.
	 */
	public PhieuNhapPanel(TaiKhoanDTO currentUser) {
		this.currentUser = currentUser;
		setLayout(new BorderLayout(0, 0));

		add(container, BorderLayout.CENTER);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		tableModel = new DefaultTableModel(
		   new String[] {"ID", "Ngày tạo", "Ngày cập nhật", "Trạng thái"}, 0)
		{
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; // Không cho phép sửa ô nào cả
	        }
	    };

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
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setBackground(new Color(173, 255, 47));
		btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnAdd.setIcon(new ImageIcon(PhieuNhapPanel.class.getResource("/ASSET/Images/icons8_add_30px.png")));
		btnAdd.addActionListener(e->showAdd());
		actionPanel.add(btnAdd);

		btnEdit = new JButton("Sửa");
		btnEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnEdit.setBackground(new Color(135, 206, 235));
		btnEdit.setIcon(new ImageIcon(PhieuNhapPanel.class.getResource("/ASSET/Images/icons8_wrench_30px.png")));
		btnEdit.addActionListener(e->showEdit());
		actionPanel.add(btnEdit);

		btnDel = new JButton("Xóa");
		btnDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDel.setBackground(new Color(255, 215, 0));
		btnDel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnDel.setIcon(new ImageIcon(PhieuNhapPanel.class.getResource("/ASSET/Images/icons8_cancel_30px_1.png")));
		btnDel.addActionListener(e->delete());
		actionPanel.add(btnDel);

		btnDetail = new JButton("Chi tiết");
		btnDetail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDetail.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnDetail.setBackground(new Color(255, 192, 203));
		ImageHelper moreIcon = new ImageHelper(30, 30, PhieuNhapPanel.class.getResource("/ASSET/Images/icons8_more_20px.png"));
        btnDetail.setIcon(moreIcon.getScaledImage());
         btnDetail.addActionListener(e -> showDetail());
        actionPanel.add(btnDetail);
	}

	private void searchBoxInit() {
		JPanel searchPanel = new JPanel();
		container.add(searchPanel);
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		txtSearch = new JTextField();
		txtSearch.setMinimumSize(new Dimension(7, 30));
		txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		searchPanel.add(txtSearch);
		txtSearch.setColumns(20);

		btnSearch = new JButton("Tìm");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnSearch.setBackground(new Color(255, 255, 255));
		ImageHelper imgSrch = new ImageHelper(20, 20, PhieuNhapPanel.class.getResource("/ASSET/Images/searchIcon.png"));
		btnSearch.setIcon(imgSrch.getScaledImage());
//		btnSearch.addActionListener(e->search());
		searchPanel.add(btnSearch);

		btnReset = new JButton("Làm mới");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setFont(new Font("Segoe UI", Font.BOLD, 13));
		ImageHelper imgReset = new ImageHelper(20, 20, PhieuNhapPanel.class.getResource("/ASSET/Images/icons8_replay_30px.png"));
		btnReset.setIcon(imgReset.getScaledImage());
		btnReset.addActionListener(e->{
			txtSearch.setText("");
			loadTable(phieuNhapBus.getAll());
		});
		searchPanel.add(btnReset);
	}

	private void tableInit() {
		JScrollPane tablePane = new JScrollPane();
		container.add(tablePane);

		table = new JTable();
		tablePane.setViewportView(table);
		tablePane.getViewport().setBackground(new Color(255, 240, 220));

		// load list
		loadTable(phieuNhapBus.getAll());
	}

	private void loadTable(List<PhieuNhapDTO> arr) {
		tableModel.setRowCount(0); //This removes all the rows but keeps the column structure.
		if(arr == null) {
			arr = phieuNhapBus.getAll();
		}
		// kiem tra mang co null ko
		if(arr != null) {
			for (PhieuNhapDTO phieuNhap : arr) {
		        Object[] row = {
		            phieuNhap.getIdPN(),
		            phieuNhap.getNgaytao(),
		            phieuNhap.getNgaycapnhat(),
		            phieuNhap.getIdTT() == 1 ? "Chưa hoàn tất" : "Hoàn tất"
		        };
		        tableModel.addRow(row);
		    }
		}
	    table.setModel(tableModel);
	}

	private void showEdit() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu nhập!");
		}
		else {
			String trangThai = table.getValueAt(selectedRow, 3).toString();
			if (!trangThai.equals("Chưa hoàn tất")) {
				JOptionPane.showMessageDialog(this, "Chỉ có thể chỉnh sửa phiếu nhập có trạng thái 'Chưa hoàn tất'!",
						"Cảnh báo", JOptionPane.WARNING_MESSAGE);
			} else {
				int idPN = (int) table.getValueAt(selectedRow, 0);
				PhieuNhapDialog phieuNhapDialog = new PhieuNhapDialog(currentUser);
				phieuNhapDialog.showEdit(idPN);
				// sau khi đóng dialog, reload table
				loadTable(phieuNhapBus.getAll());
			}
		}
	}

	private void showAdd() {
		PhieuNhapDialog phieuNhapDialog = new PhieuNhapDialog(currentUser);
		phieuNhapDialog.showAdd();
		// sau khi đóng dialog, reload table
		loadTable(phieuNhapBus.getAll());
	}

	private void showDetail() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu nhập!");
		} else {
			int idPN = (int) table.getValueAt(selectedRow, 0);
			PhieuNhapDialog phieuNhapDialog = new PhieuNhapDialog(currentUser);
			phieuNhapDialog.showDetail(idPN);
		}
	}

//
//	private void search() {
//		// get keyword
//		String keyWord = txtSearch.getText();
//		// validate
//		if(keyWord.trim().equals(""))
//			JOptionPane.showMessageDialog(this, "Bạn chưa nhập từ khóa tìm kiếm");
//		else {
//			// tìm kiếm: nếu không tìm thấy thì trả về null
//			List<DanhMucDTO> result = danhMucBus.search(keyWord.trim());
//			// hiển thị
//			loadTable(result);
//			// empty ô search
//			txtSearch.setText("");
//		}
//	}
//
	private void delete() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu nhập nào!");
		} else {
			String trangThai = table.getValueAt(selectedRow, 3).toString();
			if (!trangThai.equals("Chưa hoàn tất")) {
				JOptionPane.showMessageDialog(this, "Chỉ có thể xóa phiếu nhập có trạng thái 'Chưa hoàn tất'!",
						"Cảnh báo", JOptionPane.WARNING_MESSAGE);
			} else {
				int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phiếu nhập này?", "Xác nhận xóa",
						JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					int idPN = (int) table.getValueAt(selectedRow, 0);
					if (phieuNhapBus.delete(idPN)) {
						JOptionPane.showMessageDialog(this, "Xóa thành công!");
						// sau khi đóng dialog, reload table
						loadTable(phieuNhapBus.getAll());
					} else {
						JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thất bại"
								, "LỖI", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
}