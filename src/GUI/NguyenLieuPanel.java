package GUI;

import BUS.NguyenLieuBUS;
import BUS.Lo_NguyenLieuBUS;
import DTO.NguyenLieuDTO;
import GUI.NguyenLieu.InputDialog;
import DTO.Lo_NguyenLieuDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NguyenLieuPanel extends JPanel {
    private NguyenLieuBUS bus;
    private Lo_NguyenLieuBUS loBus;
    private JTable table1;
    private DefaultTableModel tableModel;
    private JButton button1, button2, button3, button7;
    private JComboBox<String> comboBox1, comboBox2;
    private JTextField textField1;
    private List<NguyenLieuDTO> fullList;

    public NguyenLieuPanel() {
        bus = new NguyenLieuBUS();
        loBus = new Lo_NguyenLieuBUS();
        initComponents();
        loadTableData();
    }

    private void initComponents() {
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel paneltr = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button7 = new JButton();
        comboBox1 = new JComboBox<>();
        comboBox2 = new JComboBox<>();
        textField1 = new JTextField();
        JPanel panel5 = new JPanel();
        JScrollPane scrollPane1 = new JScrollPane();
        table1 = new JTable();

        setLayout(new BorderLayout());

        panel1.setLayout(new GridLayout(2, 1, 0, 5));

        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        button1.setText("Thêm");
        button1.setPreferredSize(new Dimension(120, 40));
        panel2.add(button1);

        button2.setText("Xóa");
        button2.setPreferredSize(new Dimension(120, 40));
        panel2.add(button2);

        button3.setText("Sửa");
        button3.setPreferredSize(new Dimension(120, 40));
        panel2.add(button3);

        panel1.add(panel2);

        panel3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        paneltr.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        paneltr.setPreferredSize(new Dimension(180, 70));
        comboBox2.setPreferredSize(new Dimension(120, 30));
        paneltr.add(comboBox2);
        panel3.add(paneltr);

        panel4.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        comboBox1.setPreferredSize(new Dimension(120, 30));
        panel4.add(comboBox1);
        textField1.setPreferredSize(new Dimension(120, 30));
        panel4.add(textField1);
        panel3.add(panel4);

        button7.setText("Làm mới");
        button7.setPreferredSize(new Dimension(120, 40));
        panel3.add(button7);

        panel1.add(panel3);

        add(panel1, BorderLayout.NORTH);

        panel5.setLayout(new BorderLayout());
        scrollPane1.setViewportView(table1);
        panel5.add(scrollPane1, BorderLayout.CENTER);
        add(panel5, BorderLayout.CENTER);

        URL imageUrlThem = getClass().getResource("/ASSET/Images/icons8_add_30px.png");
        ImageIcon iconThem = (imageUrlThem != null) ? new ImageIcon(imageUrlThem) : new ImageIcon();
        if (imageUrlThem == null) {
            System.out.println("Không tìm thấy hình ảnh: /ASSET/Images/icons8_add_30px.png");
        }

        URL imageUrlXoa = getClass().getResource("/ASSET/Images/icons8_delete_forever_30px_1.png");
        ImageIcon iconXoa = (imageUrlXoa != null) ? new ImageIcon(imageUrlXoa) : new ImageIcon();
        if (imageUrlXoa == null) {
            System.out.println("Không tìm thấy hình ảnh: /ASSET/Images/icons8_delete_forever_30px_1.png");
        }

        URL imageUrlSua = getClass().getResource("/ASSET/Images/icons8_wrench_30px.png");
        ImageIcon iconSua = (imageUrlSua != null) ? new ImageIcon(imageUrlSua) : new ImageIcon();
        if (imageUrlSua == null) {
            System.out.println("Không tìm thấy hình ảnh: /ASSET/Images/icons8_wrench_30px.png");
        }

        URL imageUrlRefresh = getClass().getResource("/ASSET/Images/icons8_data_backup_30px.png");
        ImageIcon refreshIcon = (imageUrlRefresh != null) ? new ImageIcon(imageUrlRefresh) : new ImageIcon();
        if (imageUrlRefresh == null) {
            System.out.println("Không tìm thấy hình ảnh: /ASSET/Images/icons8_data_backup_30px.png");
        }

        button1.setIcon(iconThem);
        button2.setIcon(iconXoa);
        button3.setIcon(iconSua);
        button7.setIcon(refreshIcon);

        Font font = new Font("Segoe UI", 0, 16);
        Font fontHeader = new Font("Segoe UI", Font.BOLD, 16);
        table1.setFont(font);
        table1.getTableHeader().setFont(fontHeader);

        table1.setRowHeight(30);

        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Tên nguyên liệu", "Đơn vị", "Tồn kho", "HSD" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);

        TableColumnModel columnModel = table1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(120);

        String[] searchItems = { "Tất cả", "ID", "Tên nguyên liệu", "Đơn vị", "HSD" };
        for (String item : searchItems) {
            comboBox1.addItem(item);
        }

        String[] statusItems = { "Tất cả", "Còn tồn kho", "Hết tồn kho" };
        for (String item : statusItems) {
            comboBox2.addItem(item);
        }

        button1.addActionListener(e -> showInputDialog(true, null));

        button3.addActionListener(e -> {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nguyên liệu để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idNL = (int) tableModel.getValueAt(selectedRow, 0);
            String tenNL = (String) tableModel.getValueAt(selectedRow, 1);
            String donvi = (String) tableModel.getValueAt(selectedRow, 2);
            NguyenLieuDTO nl = new NguyenLieuDTO(idNL, donvi, tenNL);
            showInputDialog(false, nl);
        });

        button2.addActionListener(e -> {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nguyên liệu để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idNL = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nguyên liệu ID " + idNL + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = bus.deleteNguyenLieu(idNL);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Xóa nguyên liệu thành công!");
                    loadTableData();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nguyên liệu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        button7.addActionListener(e -> {
            textField1.setText("");
            comboBox1.setSelectedIndex(0);
            comboBox2.setSelectedIndex(0);
            loadTableData();
            JOptionPane.showMessageDialog(this, "Bảng đã được làm mới!");
        });

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTable();
            }
        });

        comboBox1.addActionListener(e -> filterTable());

        comboBox2.addActionListener(e -> filterTable());
    }

    private void loadTableData() {
        fullList = bus.getAllNguyenLieu();
        filterTable();
    }

    private void filterTable() {
        tableModel.setRowCount(0);
        String searchText = textField1.getText().trim().toLowerCase();
        String searchType = (String) comboBox1.getSelectedItem();
        String statusFilter = (String) comboBox2.getSelectedItem();

        List<Lo_NguyenLieuDTO> loList = loBus.getAllLoNguyenLieu();

        for (NguyenLieuDTO nl : fullList) {
            float tonkho = 0;
            String hsd = "Chưa nhập";
            int maxIdPN = -1;

            if (loList != null) {
                for (Lo_NguyenLieuDTO lo : loList) {
                    if (lo.getIdNL() == nl.getIdNL()) {
                        tonkho += lo.getTonkho();
                        if (lo.getIdPN() > maxIdPN) {
                            maxIdPN = lo.getIdPN();
                            hsd = lo.getHsd() != null ? lo.getHsd() : "Chưa nhập";
                        }
                    }
                }
            }

            boolean statusMatch = true;
            if (statusFilter.equals("Còn tồn kho") && tonkho <= 0) {
                statusMatch = false;
            } else if (statusFilter.equals("Hết tồn kho") && tonkho > 0) {
                statusMatch = false;
            }

            if (!statusMatch) {
                continue;
            }

            boolean searchMatch = false;
            if (searchText.isEmpty()) {
                searchMatch = true;
            } else {
                switch (searchType) {
                    case "ID":
                        searchMatch = String.valueOf(nl.getIdNL()).contains(searchText);
                        break;
                    case "Tên nguyên liệu":
                        searchMatch = nl.getTenNL().toLowerCase().contains(searchText);
                        break;
                    case "Đơn vị":
                        searchMatch = nl.getDonvi().toLowerCase().contains(searchText);
                        break;
                    case "HSD":
                        searchMatch = hsd.toLowerCase().contains(searchText);
                        break;
                    case "Tất cả":
                        searchMatch = String.valueOf(nl.getIdNL()).contains(searchText) ||
                                      nl.getTenNL().toLowerCase().contains(searchText) ||
                                      nl.getDonvi().toLowerCase().contains(searchText) ||
                                      hsd.toLowerCase().contains(searchText);
                        break;
                }
            }

            if (searchMatch) {
                tableModel.addRow(new Object[]{nl.getIdNL(), nl.getTenNL(), nl.getDonvi(), tonkho, hsd});
            }
        }
    }

    private int getNextIdNL() {
        int maxId = 0;
        for (NguyenLieuDTO nl : bus.getAllNguyenLieu()) {
            if (nl.getIdNL() > maxId) {
                maxId = nl.getIdNL();
            }
        }
        return maxId + 1;
    }

    private void showInputDialog(boolean isAdd, NguyenLieuDTO nl) {
        InputDialog dialog = new InputDialog((Frame) SwingUtilities.getWindowAncestor(this), isAdd, nl, isAdd ? getNextIdNL() : 0);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            loadTableData();
        }
    }
}