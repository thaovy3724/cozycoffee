package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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

import BUS.NguyenLieuBUS;
import DTO.NguyenLieuDTO;
import GUI.Dialog.NguyenLieuDialog;

public class NguyenLieuPanel extends JPanel {
    private final NguyenLieuBUS nguyenLieuBus = new NguyenLieuBUS();

    private static final long serialVersionUID = 1L;
    private JButton btnAdd, btnEdit, btnDel, btnSearch, btnReset;
    private JTable table;
    private final JPanel container = new JPanel();
    private JTextField txtSearch;
    private DefaultTableModel tableModel;

    /**
     * Create the panel.
     */
    public NguyenLieuPanel() {
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
        btnAdd.setIcon(new ImageIcon(NguyenLieuPanel.class.getResource("/ASSET/Images/icons8_add_30px.png")));
        btnAdd.addActionListener(e->showAdd());
        actionPanel.add(btnAdd);

        btnEdit = new JButton("Sửa");
        btnEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEdit.setBackground(new Color(135, 206, 235));
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEdit.setIcon(new ImageIcon(NguyenLieuPanel.class.getResource("/ASSET/Images/icons8_wrench_30px.png")));
        btnEdit.addActionListener(e->showEdit());
        actionPanel.add(btnEdit);

        btnDel = new JButton("Xóa");
        btnDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDel.setBackground(new Color(255, 215, 0));
        btnDel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnDel.setIcon(new ImageIcon(NguyenLieuPanel.class.getResource("/ASSET/Images/icons8_cancel_30px_1.png")));
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
        ImageHelper imgSrch = new ImageHelper(20, 20, NguyenLieuPanel.class.getResource("/ASSET/Images/searchIcon.png"));
        btnSearch.setIcon(imgSrch.getScaledImage());
        btnSearch.addActionListener(e->search());
        searchPanel.add(btnSearch);

        btnReset = new JButton("Làm mới");
        btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnReset.setBackground(new Color(255, 255, 255));
        btnReset.setFont(new Font("Segoe UI", Font.BOLD, 13));
        ImageHelper imgReset = new ImageHelper(20, 20, NguyenLieuPanel.class.getResource("/ASSET/Images/icons8_replay_30px.png"));
        btnReset.setIcon(imgReset.getScaledImage());
        btnReset.addActionListener(e->{
            txtSearch.setText("");
            loadTable(nguyenLieuBus.getAll());
        });
        searchPanel.add(btnReset);
    }

    private void tableInit() {
        tableModel = new DefaultTableModel(
            new String[] {"ID", "Tên nguyên liệu","Đơn vị"}, 0)
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
        loadTable(nguyenLieuBus.getAll());
    }

    private void loadTable(List<NguyenLieuDTO> arr) {
        tableModel.setRowCount(0); //This removes all the rows but keeps the column structure.
        // kiem tra mang co null ko
        if(arr != null) {
			for (NguyenLieuDTO nl : arr) {
                Object[] row = {
                   nl.getIdNL(),
                    nl.getTenNL(),
                    nl.getDonvi()
                };
                tableModel.addRow(row);
            }
		}
        table.setModel(tableModel);
    }

    private void showEdit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn nguyên liệu");
        }
        else {
            int idNL = (int) table.getValueAt(selectedRow, 0);
            NguyenLieuDialog nguyenLieuDialog = new NguyenLieuDialog();
            nguyenLieuDialog.showEdit(idNL);
            // sau khi đóng dialog, reload table
            loadTable(nguyenLieuBus.getAll());
        }
    }

    private void showAdd() {
        NguyenLieuDialog nguyenLieuDialog = new NguyenLieuDialog();
        nguyenLieuDialog.showAdd();
        // sau khi đóng dialog, reload table
        loadTable(nguyenLieuBus.getAll());
    }

    private void search() {
        // get keyword
        String keyWord = txtSearch.getText();
        // validate
        if(keyWord.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập từ khóa tìm kiếm");
		} else {
            // tìm kiếm: nếu không tìm thấy thì trả về null
            List<NguyenLieuDTO> result = nguyenLieuBus.search(keyWord.trim());
            // hiển thị
            loadTable(result);
        }
    }

    private void delete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn nguyên liệu");
        }
        else {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nguyên liệu này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // tiến hành xóa
                int idNL = (int) table.getValueAt(selectedRow, 0);
                // cập nhật lại CSDL
                // kiểm tra có lỗi ko, nếu có thì xuât thông báo lỗi
                if(nguyenLieuBus.delete(idNL)){
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    // reload table
                    loadTable(nguyenLieuBus.getAll());
                } else {
					JOptionPane.showMessageDialog(this, "Bạn không thể xóa nguyên liệu này!");
				}
            }
        }
    }
}