package GUI;

import java.awt.BorderLayout;
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

import BUS.CongThucBUS;
import BUS.SanPhamBUS;
import DTO.CongThucDTO;
import DTO.SanPhamDTO;
import GUI.Dialog.CongThucDialog;

public class CongThucPanel extends JPanel {
    private CongThucBUS congThucBus = new CongThucBUS();
    private SanPhamBUS sanPhamBus = new SanPhamBUS(); // Vẫn giữ để lấy tên sản phẩm

    private static final long serialVersionUID = 1L;
    private JButton btnAdd, btnEdit, btnDel, btnSearch, btnReset;
    private JTable table;
    private JPanel container = new JPanel();
    private JTextField txtSearch;
    private DefaultTableModel tableModel;
    private JButton btnDetail;

    public CongThucPanel() {
        setLayout(new BorderLayout(0, 0));

        add(container, BorderLayout.CENTER);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // Xóa cột "Trạng thái" khỏi tableModel
        tableModel = new DefaultTableModel(
            new String[] {"ID", "Tên sản phẩm", "Mô tả"}, 0) { // Chỉ giữ 3 cột
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
        btnAdd.setIcon(new ImageIcon(CongThucPanel.class.getResource("/ASSET/Images/icons8_add_30px.png")));
        btnAdd.addActionListener(e -> showAdd());
        actionPanel.add(btnAdd);

        btnEdit = new JButton("Sửa");
        btnEdit.setIcon(new ImageIcon(CongThucPanel.class.getResource("/ASSET/Images/icons8_wrench_30px.png")));
        btnEdit.addActionListener(e -> showEdit());
        actionPanel.add(btnEdit);

        btnDel = new JButton("Xóa");
        btnDel.setIcon(new ImageIcon(CongThucPanel.class.getResource("/ASSET/Images/icons8_cancel_30px_1.png")));
        btnDel.addActionListener(e -> delete());
        actionPanel.add(btnDel);

        btnDetail = new JButton("Chi tiết");
        btnDetail.setPreferredSize(new Dimension(100, 39));
        btnDetail.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\cozycoffee\\src\\ASSET\\Images\\icons8_more_20px.png"));
        btnDetail.addActionListener(e -> showDetail());
        actionPanel.add(btnDetail);

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
        ImageHelper imgSrch = new ImageHelper(20, 20, CongThucPanel.class.getResource("/ASSET/Images/searchIcon.png"));
        btnSearch.setIcon(imgSrch.getScaledImage());
        btnSearch.addActionListener(e -> search());
        searchPanel.add(btnSearch);

        btnReset = new JButton("Làm mới");
        ImageHelper imgReset = new ImageHelper(20, 20, CongThucPanel.class.getResource("/ASSET/Images/icons8_replay_30px.png"));
        btnReset.setIcon(imgReset.getScaledImage());
        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            loadTable(null);
        });
        searchPanel.add(btnReset);
    }

    private void tableInit() {
        JScrollPane tablePane = new JScrollPane();
        container.add(tablePane);

        table = new JTable();
        tablePane.setViewportView(table);

        // load list
        loadTable(null);
    }

    private void loadTable(List<CongThucDTO> arr) {
        tableModel.setRowCount(0); // Xóa tất cả các dòng hiện tại
        if (arr == null) {
			arr = congThucBus.getAll();
		}

        if (arr != null && !arr.isEmpty()) {
            for (CongThucDTO ct : arr) {
                // Lấy thông tin sản phẩm từ idSP
                SanPhamDTO sp = sanPhamBus.findByIdSP(ct.getIdSP());
                String tenSP = sp != null ? sp.getTenSP() : "Không xác định";

                Object[] row = {
                    ct.getIdCT(),
                    tenSP, // Hiển thị tên sản phẩm
                    ct.getMota()
                };
                tableModel.addRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có công thức nào để hiển thị.");
        }
        table.setModel(tableModel);
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
            loadTable(null);
        }
    }

    private void showAdd() {
        CongThucDialog congThucDialog = new CongThucDialog();
        congThucDialog.showAdd();
        // Sau khi đóng dialog, reload table
        loadTable(null);
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
            if (result == null || result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy công thức nào với từ khóa: " + keyWord);
            }
            loadTable(result);
            txtSearch.setText("");
        }
    }

    private void delete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn công thức!");
        } else {
            int idCT = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa công thức này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (congThucBus.delete(idCT)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadTable(null); // Reload bảng sau khi xóa thành công
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa công thức này!");
                    loadTable(null); // Reload bảng nếu có lỗi
                }
            }
        }
    }
}
