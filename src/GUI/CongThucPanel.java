package GUI;

import javax.swing.table.DefaultTableModel;
import BUS.CongThucBUS;
import BUS.SanPhamBUS;
import DTO.CongThucDTO;
import DTO.SanPhamDTO;
import DTO.TaiKhoanDTO;
import GUI.Dialog.CongThucDialog;

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
import java.awt.Component;

public class CongThucPanel extends JPanel {
    private CongThucBUS congThucBus = new CongThucBUS();
    private SanPhamBUS sanPhamBus = new SanPhamBUS(); // Vẫn giữ để lấy tên sản phẩm
    private TaiKhoanDTO currentUser;

    private static final long serialVersionUID = 1L;
    private JButton btnEdit, btnDel, btnSearch, btnReset, btnDetail;
    private JTable table;
    private JPanel container = new JPanel();
    private JTextField txtSearch;
    private DefaultTableModel tableModel;
    private JButton btnAdd;

    public CongThucPanel(TaiKhoanDTO currentUser) {
        this.currentUser = currentUser;
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
        
        if(currentUser.getIdNQ() == 1){
            // Nếu là admin thì hiển thị đủ quyền
            btnAdd = new JButton("Thêm");
            btnAdd.setForeground(new Color(0, 0, 0));
            btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnAdd.setBackground(new Color(173, 255, 47));
            btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnAdd.setIcon(new ImageIcon(CongThucPanel.class.getResource("/ASSET/Images/icons8_add_30px.png")));
            btnAdd.addActionListener(e -> showAdd());
            actionPanel.add(btnAdd);

            btnEdit = new JButton("Sửa");
            btnEdit.setBackground(new Color(135, 206, 235));
            btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnEdit.setIcon(new ImageIcon(CongThucPanel.class.getResource("/ASSET/Images/icons8_wrench_30px.png")));
            btnEdit.addActionListener(e -> showEdit());
            actionPanel.add(btnEdit);

            btnDel = new JButton("Xóa");
            btnDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnDel.setBackground(new Color(255, 215, 0));
            btnDel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnDel.setIcon(new ImageIcon(CongThucPanel.class.getResource("/ASSET/Images/icons8_cancel_30px_1.png")));
            btnDel.addActionListener(e -> delete());
            actionPanel.add(btnDel);
        }
            
            btnDetail = new JButton("Xem");
            btnDetail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnDetail.setBackground(new Color(255, 192, 203));
            btnDetail.setMaximumSize(new Dimension(100, 23));
            btnDetail.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnDetail.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnDetail.setPreferredSize(new Dimension(100, 39));
            btnDetail.setIcon(new ImageIcon(CongThucPanel.class.getResource("/ASSET/Images/icons8_more_20px.png")));
            btnDetail.addActionListener(e -> showDetail());
            actionPanel.add(btnDetail);
    }

    private void searchBoxInit() {
        JPanel searchPanel = new JPanel();
        container.add(searchPanel);
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        searchPanel.setBackground(new Color(255, 240, 220));
        
        txtSearch = new JTextField();
        txtSearch.setMinimumSize(new Dimension(7, 30));
        txtSearch.setPreferredSize(new Dimension(7, 30));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchPanel.add(txtSearch);
        txtSearch.setColumns(20);

        btnSearch = new JButton("Tìm");
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSearch.setBackground(new Color(255, 255, 255));
        ImageHelper imgSrch = new ImageHelper(20, 20, CongThucPanel.class.getResource("/ASSET/Images/searchIcon.png"));
        btnSearch.setIcon(imgSrch.getScaledImage());
        btnSearch.addActionListener(e -> search());
        searchPanel.add(btnSearch);

        btnReset = new JButton("Làm mới");
        btnReset.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnReset.setBackground(new Color(255, 255, 255));
        ImageHelper imgReset = new ImageHelper(20, 20, CongThucPanel.class.getResource("/ASSET/Images/icons8_replay_30px.png"));
        btnReset.setIcon(imgReset.getScaledImage());
        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            loadTable(congThucBus.getAll());
        });
        searchPanel.add(btnReset);
    }

    private void tableInit() {
        tableModel = new DefaultTableModel(
            new String[] {"Mã công thức", "Tên sản phẩm"}, 0) { // Chỉ giữ 3 cột
            
                @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép sửa ô nào cả
            }
        };

        JScrollPane tablePane = new JScrollPane();
        container.add(tablePane);
        tablePane.getViewport().setBackground(new Color(255, 240, 220));

        table = new JTable();
        table.setModel(tableModel);
        table.setGridColor(new Color(0, 0, 0));
        tablePane.setViewportView(table);

        // load list
        loadTable(congThucBus.getAll());
    }

    private void loadTable(List<CongThucDTO> arr) {
        tableModel.setRowCount(0); // Xóa tất cả các dòng hiện tại
        if (arr != null)
            for (CongThucDTO ct : arr) {
                // Lấy thông tin sản phẩm từ idSP
                SanPhamDTO sp = sanPhamBus.findByIdSP(ct.getIdSP());

                Object[] row = {
                    ct.getIdCT(),
                    sp.getTenSP(), // Hiển thị tên sản phẩm
                };
                tableModel.addRow(row);
            }
    }

    private void showEdit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn công thức!");
        } else {
            int idCT = (int) table.getValueAt(selectedRow, 0);
            CongThucDialog congThucDialog = new CongThucDialog();
            congThucDialog.showEdit(idCT);
            // Sau khi đóng dialog, reload table
            loadTable(congThucBus.getAll());
        }
    }

    private void showAdd() {
        CongThucDialog congThucDialog = new CongThucDialog();
        congThucDialog.showAdd();
        // Sau khi đóng dialog, reload table
        loadTable(congThucBus.getAll());
    }

    private void showDetail() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn công thức!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            int idCT = (int) table.getValueAt(selectedRow, 0);
            CongThucDialog congThucDialog = new CongThucDialog();
            congThucDialog.showDetail(idCT);
        }
    }

    private void search() {
        String keyWord = txtSearch.getText();
        if (keyWord.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập từ khóa tìm kiếm!");
        } else {
            List<CongThucDTO> result = congThucBus.search(keyWord.trim());
            loadTable(result);
        }
    }

    private void delete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn công thức!");
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa công thức này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int idCT = (int) table.getValueAt(selectedRow, 0);
                if (congThucBus.delete(idCT)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadTable(congThucBus.getAll()); // Reload bảng sau khi xóa thành công
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa công thức này!");
                }
            }
        }
    }
}