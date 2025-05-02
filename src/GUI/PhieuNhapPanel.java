package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BUS.PhieuNhapBUS;
import BUS.TrangThai_PnBUS;
import DTO.PhieuNhapDTO;
import DTO.TaiKhoanDTO;
import DTO.TrangThai_PnDTO;
import GUI.Dialog.PhieuNhapDialog;
import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Cursor;

public class PhieuNhapPanel extends JPanel {
	private PhieuNhapBUS phieuNhapBus = new PhieuNhapBUS();
	//HUONGNGUYEN 2/5
	private TrangThai_PnBUS ttpnBus = new TrangThai_PnBUS();

	//HUONGNGUYEN 2/5
	private static final long serialVersionUID = 1L;
	private JButton btnAdd, btnEdit, btnDel, btnDetail, btnSearch, btnReset;
	private JTable table;
	private JPanel container = new JPanel();
	private JDateChooser dateChooserStart, dateChooserEnd;
	private JComboBox<TrangThai_PnDTO> cboTrangThai;
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

	//HUONGNGUYEN 2/5
	private void searchBoxInit() {
		JPanel searchPanel = new JPanel();
		container.add(searchPanel);
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

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

		JLabel lblStatusFilter = new JLabel("Trạng thái ");
		lblStatusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		searchContentPanel2.add(lblStatusFilter);

		cboTrangThai = new JComboBox<>();
		cboTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		//Chèn dữ liệu cho cbobox trạng thái
		TrangThai_PnDTO ttpnDefault = new TrangThai_PnDTO(0, "Tất cả");
		cboTrangThai.addItem(ttpnDefault);
		List<TrangThai_PnDTO> ttpnList = ttpnBus.getAll();
		if (ttpnList != null) {
			for (TrangThai_PnDTO ttpn : ttpnList) {
				cboTrangThai.addItem(ttpn);
			}
		} else {
			System.out.println("Không có trạng thái phiếu nhập nào được truyền vào");
		}
		searchContentPanel2.add(cboTrangThai);

		btnSearch = new JButton("Tìm");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnSearch.setBackground(new Color(255, 255, 255));
		ImageHelper imgSrch = new ImageHelper(20, 20, PhieuNhapPanel.class.getResource("/ASSET/Images/searchIcon.png"));
		btnSearch.setIcon(imgSrch.getScaledImage());
		btnSearch.addActionListener(e->search());
		searchPanel.add(btnSearch);

		btnReset = new JButton("Làm mới");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setFont(new Font("Segoe UI", Font.BOLD, 13));
		ImageHelper imgReset = new ImageHelper(20, 20, PhieuNhapPanel.class.getResource("/ASSET/Images/icons8_replay_30px.png"));
		btnReset.setIcon(imgReset.getScaledImage());
		btnReset.addActionListener(e->{
			// Đặt lại dateChooserStart và dateChooserEnd về null (trống)
			dateChooserStart.setDate(null);
			dateChooserEnd.setDate(null);
			// Đặt lại lựa chọn của cboTrangThai về "Tất cả"
			cboTrangThai.setSelectedIndex(0);
			//Tải lại dữ liệu của bảng
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

	//HUONGNGUYEN 2/5
	private String validateDateFilter(Date start, Date end) {
		if (start != null && end != null) {
			LocalDate today = LocalDate.now();
			LocalDate startDate = start.toLocalDate();
			LocalDate endDate = end.toLocalDate();
			if (startDate.isAfter(today) || endDate.isAfter(today)) {
				return "Ngày bắt đầu hoặc ngày kết thúc không được là ngày trong tương lai!";
			}

			if (startDate.isAfter(endDate)) {
				return "Ngày bắt đầu không thể lớn hơn ngày kết thúc!";
			} else if (startDate.equals(endDate)) {
				return ""; // Cho phép ngày bằng nhau, tùy chỉnh nếu cần
			} else {
				return "";
			}
		} else if (start != null && end == null) {
			return "Bạn chưa chọn ngày kết thúc!";
		} else if (start == null && end != null) {
			return "Bạn chưa chọn ngày bắt đầu";
		} else {
			return "";
		}
	}

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

	//HUONGNGUYEN 2/5
	private void search() {
		// Lấy dữ liệu ngày tháng
		Date dateStart = dateChooserStart.getDate() == null ? null :
				new Date(dateChooserStart.getDate().getTime());
		Date dateEnd = dateChooserEnd.getDate() == null ? null :
				new Date(dateChooserEnd.getDate().getTime());
		String dateFilterError = validateDateFilter(dateStart, dateEnd);
		if (dateFilterError != "") {
			JOptionPane.showMessageDialog(this, dateFilterError, "LỖI",
					JOptionPane.ERROR_MESSAGE);
		} else {
			// Lấy dữ liệu trạng thái
			TrangThai_PnDTO ttpn = (TrangThai_PnDTO) cboTrangThai.getSelectedItem();

			List<PhieuNhapDTO> searchResult = new ArrayList<>();
			searchResult = phieuNhapBus.search(dateStart, dateEnd, ttpn);
			loadTable(searchResult);
		}
	}
}