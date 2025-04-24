package GUI.Dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import java.util.HashSet;
import java.util.Objects;

import BUS.CongThucBUS;
import BUS.SanPhamBUS;
import BUS.NguyenLieuBUS;
import DTO.CongThucDTO;
import DTO.CT_CongThucDTO;
import DTO.NguyenLieuDTO;
import DTO.SanPhamDTO;

public class CongThucDialog extends JDialog {
    private CongThucBUS congThucBus = new CongThucBUS();
    private SanPhamBUS sanPhamBus = new SanPhamBUS();
    private NguyenLieuBUS nguyenLieuBus = new NguyenLieuBUS();
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JLabel errTenSP, errMoTa;
    private JButton btnSubmit, btnAdd, btnCancel;
    private JComboBox<SanPhamDTO> cboSP;
    private JTextArea txtMoTa;
    private JTable tableNguyenLieu;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private java.util.List<NguyenLieuDTO> nguyenLieuList; // Danh sách nguyên liệu để dùng cho JComboBox

    public CongThucDialog() {
        setTitle("Thêm công thức");
        setSize(750, 550);
        setMinimumSize(new Dimension(750, 550));
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 0, 0, 0 };
        gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        contentPanel.setLayout(gbl_contentPanel);

        JLabel lblTitle = new JLabel("Thêm công thức");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 150, 243));
        GridBagConstraints gbc_lblTitle = new GridBagConstraints();
        gbc_lblTitle.anchor = GridBagConstraints.WEST;
        gbc_lblTitle.gridwidth = 2;
        gbc_lblTitle.insets = new Insets(0, 0, 20, 0);
        gbc_lblTitle.gridx = 0;
        gbc_lblTitle.gridy = 0;
        contentPanel.add(lblTitle, gbc_lblTitle);

        textFieldInit();
        initTable();
        actionInit();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
    }

    private void textFieldInit() {
        JLabel lblTenSP = new JLabel("Tên sản phẩm:");
        lblTenSP.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTenSP.setForeground(new Color(66, 66, 66));
        GridBagConstraints gbc_lblTenSP = new GridBagConstraints();
        gbc_lblTenSP.insets = new Insets(0, 0, 10, 10);
        gbc_lblTenSP.anchor = GridBagConstraints.EAST;
        gbc_lblTenSP.gridx = 0;
        gbc_lblTenSP.gridy = 1;
        contentPanel.add(lblTenSP, gbc_lblTenSP);

        cboSP = new JComboBox<>();
        cboSP.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboSP.setBackground(Color.WHITE);
        cboSP.setBorder(new LineBorder(new Color(189, 189, 189), 1));
        cboSP.setToolTipText("Chọn sản phẩm cho công thức");
        GridBagConstraints gbc_cboSP = new GridBagConstraints();
        gbc_cboSP.insets = new Insets(0, 0, 10, 0);
        gbc_cboSP.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboSP.gridx = 1;
        gbc_cboSP.gridy = 1;
        contentPanel.add(cboSP, gbc_cboSP);

        errTenSP = new JLabel();
        errTenSP.setForeground(new Color(211, 47, 47));
        errTenSP.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        GridBagConstraints gbc_errTenSP = new GridBagConstraints();
        gbc_errTenSP.anchor = GridBagConstraints.WEST;
        gbc_errTenSP.insets = new Insets(0, 0, 10, 0);
        gbc_errTenSP.gridx = 1;
        gbc_errTenSP.gridy = 2;
        contentPanel.add(errTenSP, gbc_errTenSP);

        JLabel lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMoTa.setForeground(new Color(66, 66, 66));
        GridBagConstraints gbc_lblMoTa = new GridBagConstraints();
        gbc_lblMoTa.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblMoTa.insets = new Insets(0, 0, 10, 10);
        gbc_lblMoTa.gridx = 0;
        gbc_lblMoTa.gridy = 3;
        contentPanel.add(lblMoTa, gbc_lblMoTa);

        txtMoTa = new JTextArea();
        txtMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMoTa.setBackground(Color.WHITE);
        txtMoTa.setBorder(new LineBorder(new Color(189, 189, 189), 1));
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setToolTipText("Nhập mô tả chi tiết cho công thức");
        JScrollPane txtMoTaScroll = new JScrollPane(txtMoTa);
        txtMoTaScroll.setPreferredSize(new Dimension(400, 80));
        GridBagConstraints gbc_txtMoTa = new GridBagConstraints();
        gbc_txtMoTa.gridheight = 2;
        gbc_txtMoTa.insets = new Insets(0, 0, 10, 0);
        gbc_txtMoTa.fill = GridBagConstraints.BOTH;
        gbc_txtMoTa.gridx = 1;
        gbc_txtMoTa.gridy = 3;
        contentPanel.add(txtMoTaScroll, gbc_txtMoTa);

        btnAdd = new JButton("Thêm nguyên liệu");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAdd.setBackground(new Color(0, 128, 0));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setOpaque(true);
        btnAdd.setBorderPainted(false);
        btnAdd.setIcon(new ImageIcon(getClass().getResource("/ASSET/Images/icons8_add_30px.png")));
        btnAdd.addActionListener(e -> {
            NguyenLieuDTO defaultNguyenLieu = nguyenLieuList != null && !nguyenLieuList.isEmpty()
                ? nguyenLieuList.get(0)
                : new NguyenLieuDTO(0, "---Chọn nguyên liệu---");
            tableModel.addRow(new Object[] { defaultNguyenLieu, "", "Xóa" });
        });
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAdd.setBackground(new Color(100, 221, 23));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnAdd.setBackground(new Color(76, 175, 80));
            }
        });
        GridBagConstraints gbc_btnAdd = new GridBagConstraints();
        gbc_btnAdd.anchor = GridBagConstraints.WEST;
        gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
        gbc_btnAdd.gridx = 1;
        gbc_btnAdd.gridy = 5;
        contentPanel.add(btnAdd, gbc_btnAdd);

        errMoTa = new JLabel();
        errMoTa.setForeground(new Color(211, 47, 47));
        errMoTa.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        GridBagConstraints gbc_errMoTa = new GridBagConstraints();
        gbc_errMoTa.anchor = GridBagConstraints.WEST;
        gbc_errMoTa.insets = new Insets(0, 0, 10, 0);
        gbc_errMoTa.gridx = 1;
        gbc_errMoTa.gridy = 6;
        contentPanel.add(errMoTa, gbc_errMoTa);
    }

    private void initTable() {
        tableModel = new DefaultTableModel(new Object[] { "Nguyên liệu", "Số lượng", "Thao tác" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 2; // Chỉ cho phép chỉnh sửa cột "Nguyên liệu" và "Số lượng"
            }
        };
    
        tableNguyenLieu = new JTable(tableModel);
        tableNguyenLieu.setRowHeight(35);
        tableNguyenLieu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableNguyenLieu.setGridColor(new Color(189, 189, 189));
        tableNguyenLieu.setShowGrid(true);
        tableNguyenLieu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableNguyenLieu.getTableHeader().setBackground(new Color(33, 150, 243));
        tableNguyenLieu.getTableHeader().setForeground(Color.WHITE);
        tableNguyenLieu.setSelectionBackground(new Color(187, 222, 251));
    
        // Tải danh sách nguyên liệu
        nguyenLieuList = nguyenLieuBus.getAllActive();
    
        // Thiết lập CellEditor cho cột "Nguyên liệu"
        tableNguyenLieu.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JComboBox<NguyenLieuDTO>()) {
            private JComboBox<NguyenLieuDTO> comboBox;
    
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                comboBox = new JComboBox<>();
                comboBox.addItem(new NguyenLieuDTO(0, "---Chọn nguyên liệu---")); // Thêm tùy chọn mặc định
                for (NguyenLieuDTO nl : nguyenLieuList) {
                    comboBox.addItem(nl);
                }
                if (value instanceof NguyenLieuDTO) {
                    comboBox.setSelectedItem(value);
                } else {
                    comboBox.setSelectedIndex(0);
                }
                comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                return comboBox;
            }
    
            @Override
            public Object getCellEditorValue() {
                return comboBox.getSelectedItem(); // Lưu giá trị được chọn vào TableModel
            }
    
            @Override
            public boolean stopCellEditing() {
                // Đảm bảo giá trị được lưu khi chuyển sang ô khác
                fireEditingStopped();
                return super.stopCellEditing();
            }
        });
    
        // Thiết lập CellRenderer cho cột "Nguyên liệu"
        tableNguyenLieu.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JComboBox<NguyenLieuDTO> comboBox = new JComboBox<>();
                comboBox.addItem(new NguyenLieuDTO(0, "---Chọn nguyên liệu---"));
                for (NguyenLieuDTO nl : nguyenLieuList) {
                    comboBox.addItem(nl);
                }
                if (value instanceof NguyenLieuDTO) {
                    comboBox.setSelectedItem(value); // Hiển thị giá trị hiện tại từ TableModel
                } else {
                    comboBox.setSelectedIndex(0);
                }
                comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                if (isSelected) {
                    comboBox.setBackground(table.getSelectionBackground());
                } else {
                    comboBox.setBackground(table.getBackground());
                }
                return comboBox;
            }
        });
    
        // Thiết lập CellEditor cho cột "Số lượng"
        tableNguyenLieu.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField() {{
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setBorder(new LineBorder(new Color(189, 189, 189), 1));
        }}));
    
        // Thiết lập CellRenderer và CellEditor cho cột "Thao tác"
        tableNguyenLieu.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        tableNguyenLieu.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JTextField()));
    
        // Thiết lập độ rộng cột
        tableNguyenLieu.getColumnModel().getColumn(0).setPreferredWidth(250);
        tableNguyenLieu.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableNguyenLieu.getColumnModel().getColumn(2).setPreferredWidth(80);
    
        // Thêm bảng vào JScrollPane
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(500, 120));
        scrollPane.setViewportView(tableNguyenLieu);
        scrollPane.setBorder(new LineBorder(new Color(189, 189, 189), 1));
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridheight = 4;
        gbc_scrollPane.insets = new Insets(0, 0, 10, 0);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 7;
        contentPanel.add(scrollPane, gbc_scrollPane);
    }

    private void actionInit() {
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc_actionPanel = new GridBagConstraints();
        gbc_actionPanel.insets = new Insets(0, 0, 5, 0);
        gbc_actionPanel.gridwidth = 2;
        gbc_actionPanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_actionPanel.gridx = 0;
        gbc_actionPanel.gridy = 12;
        contentPanel.add(actionPanel, gbc_actionPanel);
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnSubmit = new JButton("Thêm");
        btnSubmit.setBackground(new Color(33, 150, 243));
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setOpaque(true);
        btnSubmit.setBorderPainted(false);
        btnSubmit.addActionListener(e -> submitForm());
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSubmit.setBackground(new Color(66, 165, 245));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnSubmit.setBackground(new Color(33, 150, 243));
            }
        });
        actionPanel.add(btnSubmit);

        btnCancel = new JButton("Hủy");
        btnCancel.setBackground(new Color(244, 67, 54));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setOpaque(true);
        btnCancel.setBorderPainted(false);
        btnCancel.addActionListener(e -> cancel());
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCancel.setBackground(new Color(239, 83, 80));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnCancel.setBackground(new Color(244, 67, 54));
            }
        });
        actionPanel.add(btnCancel);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Xóa");
            setBackground(new Color(244, 67, 54));
            setForeground(Color.WHITE);
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setBorderPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
        private String label;
        private int row;

        public ButtonEditor(JTextField txt) {
            button = new JButton();
            button.setText("Xóa");
            button.setBackground(new Color(244, 67, 54));
            button.setForeground(Color.WHITE);
            button.setOpaque(true);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button.setBorderPainted(false);
            button.addActionListener(e -> {
                fireEditingStopped();
                tableModel.removeRow(row);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "Xóa" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }

    private void loadComboBoxSP(java.util.List<SanPhamDTO> arr) {
        if (cboSP == null) {
            throw new IllegalStateException("cboSP is not initialized");
        }
        cboSP.removeAllItems();
        SanPhamDTO sanPhamDefault = new SanPhamDTO();
        sanPhamDefault.setTenSP("---Chọn sản phẩm---");
        cboSP.addItem(sanPhamDefault);
        if (arr != null) {
            for (SanPhamDTO item : arr) {
                cboSP.addItem(item);
            }
        } else {
            System.out.println("Không có sản phẩm nào được tải vào cboSP");
        }
    }

    public void showAdd() {
        java.util.List<SanPhamDTO> listSP = sanPhamBus.getAllActive();
        loadComboBoxSP(listSP);

        tableModel.setRowCount(0); // Xóa các hàng cũ
        if (nguyenLieuList != null && !nguyenLieuList.isEmpty()) {
            tableModel.addRow(new Object[] { nguyenLieuList.get(0), "", "Xóa" }); // Thêm hàng mặc định
        } else {
            tableModel.addRow(new Object[] { new NguyenLieuDTO(0, "---Chọn nguyên liệu---"), "", "Xóa" });
        }

        btnSubmit.setText("Thêm");
        btnSubmit.setActionCommand("add");
        setVisible(true);
    }

    public void showEdit(int idCT) {
        CongThucDTO ct = congThucBus.findByIdCT(idCT);
        if (ct == null) {
            JOptionPane.showMessageDialog(this, "Công thức không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        txtMoTa.setText(ct.getMota());
    
        java.util.List<SanPhamDTO> listSP = sanPhamBus.getAll();
        loadComboBoxSP(listSP);
    
        // Tìm và chọn sản phẩm dựa trên idSP
        if (ct.getIdSP() != 0) {
            for (int i = 0; i < cboSP.getItemCount(); i++) {
                SanPhamDTO sp = cboSP.getItemAt(i);
                if (sp.getIdSP() == ct.getIdSP()) {
                    cboSP.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            cboSP.setSelectedIndex(0); // Chọn "---Chọn sản phẩm---"
        }
    
        tableModel.setColumnIdentifiers(new Object[] { "Nguyên liệu", "Số lượng", "Thao tác" }); // Đảm bảo cột Thao tác
        tableModel.setRowCount(0);
        java.util.List<CT_CongThucDTO> chiTietList = congThucBus.getChiTietCongThuc(idCT);
        if (chiTietList != null && !chiTietList.isEmpty()) {
            for (CT_CongThucDTO ctDetail : chiTietList) {
                NguyenLieuDTO nl = nguyenLieuBus.findByIdNL(ctDetail.getIdNL());
                if (nl != null) {
                    tableModel.addRow(new Object[] { nl, ctDetail.getSoluong(), "Xóa" });
                }
            }
        }
        if (tableModel.getRowCount() == 0 && nguyenLieuList != null && !nguyenLieuList.isEmpty()) {
            tableModel.addRow(new Object[] { nguyenLieuList.get(0), "", "Xóa" });
        } else if (tableModel.getRowCount() == 0) {
            tableModel.addRow(new Object[] { new NguyenLieuDTO(0, "---Chọn nguyên liệu---"), "", "Xóa" });
        }
    
        btnSubmit.setText("Cập nhật");
        btnSubmit.setActionCommand("edit_" + idCT);
        setVisible(true);
    }

    public void showDetail(int idCT) {
        setTitle("Chi tiết công thức");
        CongThucDTO ct = congThucBus.findByIdCT(idCT);
        if (ct == null) {
            JOptionPane.showMessageDialog(this, "Công thức không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.out.println("showDetail: Không tìm thấy công thức với idCT: " + idCT);
            return;
        }
    
        // Thiết lập chế độ chỉ xem
        cboSP.setEnabled(false);
        txtMoTa.setEditable(false);
        btnAdd.setVisible(false);
        btnSubmit.setVisible(false);
        btnCancel.setText("Đóng");
        btnCancel.setBackground(new Color(117, 117, 117));
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCancel.setBackground(new Color(158, 158, 158));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnCancel.setBackground(new Color(117, 117, 117));
            }
        });
    
        // Tải dữ liệu
        txtMoTa.setText(ct.getMota());
        java.util.List<SanPhamDTO> listSP = sanPhamBus.getAll();
        loadComboBoxSP(listSP);
    
        // Tìm và chọn sản phẩm dựa trên idSP
        if (ct.getIdSP() != 0) {
            for (int i = 0; i < cboSP.getItemCount(); i++) {
                SanPhamDTO sp = cboSP.getItemAt(i);
                if (sp.getIdSP() == ct.getIdSP()) {
                    cboSP.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            cboSP.setSelectedIndex(0); // Chọn "---Chọn sản phẩm---"
        }
    
        // Tải danh sách nguyên liệu
        tableModel.setColumnIdentifiers(new Object[] { "Nguyên liệu", "Số lượng" });
        tableModel.setRowCount(0);
        java.util.List<CT_CongThucDTO> chiTietList = congThucBus.getChiTietCongThuc(idCT);
        System.out.println("Số nguyên liệu tìm thấy: " + (chiTietList != null ? chiTietList.size() : 0));
        if (chiTietList != null && !chiTietList.isEmpty()) {
            for (CT_CongThucDTO ctDetail : chiTietList) {
                NguyenLieuDTO nl = nguyenLieuBus.findByIdNL(ctDetail.getIdNL());
                if (nl != null) {
                    tableModel.addRow(new Object[] { nl, ctDetail.getSoluong() });
                    System.out.println("Thêm nguyên liệu: " + nl.getTenNL() + ", Số lượng: " + ctDetail.getSoluong());
                } else {
                    System.out.println("Không tìm thấy nguyên liệu với idNL: " + ctDetail.getIdNL());
                }
            }
        } else {
            System.out.println("Không có chi tiết công thức nào cho idCT: " + idCT);
        }
    
        // Tắt chỉnh sửa bảng
        tableNguyenLieu.setEnabled(false);
        tableNguyenLieu.getColumnModel().getColumn(0).setPreferredWidth(250);
        tableNguyenLieu.getColumnModel().getColumn(1).setPreferredWidth(100);
    
        setVisible(true);
    }

    private boolean isError() {
        errTenSP.setText("");
        errMoTa.setText("");
        boolean isError = false;

        if (cboSP.getSelectedItem() == null || cboSP.getSelectedItem().toString().equals("---Chọn sản phẩm---")) {
            errTenSP.setText("Vui lòng chọn sản phẩm!");
            isError = true;
        }

        if (txtMoTa.getText().trim().isEmpty()) {
            errMoTa.setText("Mô tả không được để trống!");
            isError = true;
        }

        return isError;
    }

    private void submitForm() {
        if (!isError()) {
            String mota = txtMoTa.getText();
            SanPhamDTO sanPhamSelected = (SanPhamDTO) cboSP.getSelectedItem();
            int idSP = sanPhamSelected.getIdSP();

            java.util.List<CT_CongThucDTO> chiTietList = new java.util.ArrayList<>();
            HashSet<Integer> usedIdNL = new HashSet<>(); // Kiểm tra trùng lặp idNL
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                NguyenLieuDTO nl = (NguyenLieuDTO) tableModel.getValueAt(i, 0);
                String soLuongStr = tableModel.getValueAt(i, 1).toString();
                if (nl != null && nl.getIdNL() != 0 && !soLuongStr.trim().isEmpty()) {
                    // Kiểm tra trùng lặp
                    if (usedIdNL.contains(nl.getIdNL())) {
                        JOptionPane.showMessageDialog(this, "Nguyên liệu '" + nl.getTenNL() + "' đã được chọn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    usedIdNL.add(nl.getIdNL());
                    try {
                        float soLuong = Float.parseFloat(soLuongStr);
                        if (soLuong <= 0) {
                            JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        chiTietList.add(new CT_CongThucDTO(0, nl.getIdNL(), soLuong));
                        System.out.println("submitForm: Nguyên liệu gửi: " + nl.getTenNL() + ", Số lượng: " + soLuong);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            if (chiTietList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một nguyên liệu hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String error = "";
            String actionCommand = btnSubmit.getActionCommand();
            int beginIndex = actionCommand.indexOf('_') + 1;
            if (beginIndex == 0) {
                CongThucDTO ct = new CongThucDTO(idSP, mota);
                error = congThucBus.add(ct, chiTietList);
            } else {
                int idCT = Integer.parseInt(actionCommand.substring(beginIndex));
                CongThucDTO ct = new CongThucDTO(idCT, idSP, mota);
                error = congThucBus.update(ct, chiTietList);
            }

            if (!error.isEmpty()) {
                JOptionPane.showMessageDialog(this, error, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, beginIndex == 0 ? "Thêm thành công!" : "Cập nhật thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }
    }

    private void cancel() {
        dispose();
    }
}
