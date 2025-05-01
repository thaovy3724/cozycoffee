package GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import BUS.NguyenLieuBUS;
import BUS.NhaCungCapBUS;
import BUS.PhieuNhapBUS;
import DAO.NhaCungCapDAO;
import DTO.CT_CongThucDTO;
import DTO.CongThucDTO;
import DTO.Lo_NguyenLieuDTO;
import DTO.NguyenLieuDTO;
import DTO.NhaCungCapDTO;
import DTO.PhieuNhapDTO;
import DTO.SanPhamDTO;
import DTO.TaiKhoanDTO;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class PhieuNhapDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    NhaCungCapBUS nhaCungCapBus = new NhaCungCapBUS();
    NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();

    private JButton btnSubmit, btnAdd, btnCancel;
    private JComboBox<NhaCungCapDTO> cboNCC;
    private JComboBox<String> cboTrangThai = new JComboBox<>();
    private JTable tableLoNguyenLieu;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    private List<NguyenLieuDTO> nguyenLieuList;

    private TaiKhoanDTO currentUser;

    /**
     * Create the dialog.
     */
    public PhieuNhapDialog(TaiKhoanDTO currentUser) {
        this.currentUser = currentUser;
        setTitle("Thêm phiếu nhập");
        setSize(750, 550);
        setMinimumSize(new Dimension(750, 550));
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 0, 0, 0 };
        gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        contentPanel.setLayout(gbl_contentPanel);

        JLabel lblTitle = new JLabel("Thêm phiếu nhập");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 150, 243));
        GridBagConstraints gbc_lblTitle = new GridBagConstraints();
        gbc_lblTitle.gridwidth = 2;
        gbc_lblTitle.anchor = GridBagConstraints.WEST;
        gbc_lblTitle.insets = new Insets(0, 0, 20, 0);
        gbc_lblTitle.gridx = 0;
        gbc_lblTitle.gridy = 0;
        contentPanel.add(lblTitle, gbc_lblTitle);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        textFieldInit();
        initTable();
        actionInit();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
    }

    private void textFieldInit() {
        // Title
        JLabel lblTenNCC = new JLabel("Nhà cung cấp:");
        lblTenNCC.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTenNCC.setForeground(new Color(66, 66, 66));
        GridBagConstraints gbc_lblTenNCC = new GridBagConstraints();
        gbc_lblTenNCC.insets = new Insets(0, 0, 10, 10);
        gbc_lblTenNCC.anchor = GridBagConstraints.EAST;
        gbc_lblTenNCC.gridx = 0;
        gbc_lblTenNCC.gridy = 1;
        contentPanel.add(lblTenNCC, gbc_lblTenNCC);

        cboNCC = new JComboBox<>();
        cboNCC.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboNCC.setBackground(Color.WHITE);
        cboNCC.setBorder(new LineBorder(new Color(189, 189, 189), 1));
        cboNCC.setToolTipText("Chọn nhà cung cấp");
        GridBagConstraints gbc_cboNCC = new GridBagConstraints();
        gbc_cboNCC.insets = new Insets(0, 0, 10, 0);
        gbc_cboNCC.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboNCC.gridx = 1;
        gbc_cboNCC.gridy = 1;
        contentPanel.add(cboNCC, gbc_cboNCC);

        btnAdd = new JButton("Thêm chi tiết phiếu nhập");
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
            tableModel.addRow(new Object[] { defaultNguyenLieu, "", "", "", "", "Xóa" });
            tableLoNguyenLieu.revalidate();
            tableLoNguyenLieu.repaint();
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

        JLabel lblTrangThai = new JLabel("Trạng thái");
        lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GridBagConstraints gbc_lblTrangThai = new GridBagConstraints();
        gbc_lblTrangThai.anchor = GridBagConstraints.EAST;
        gbc_lblTrangThai.insets = new Insets(0, 10, 5, 5);
        gbc_lblTrangThai.gridx = 0;
        gbc_lblTrangThai.gridy = 2;
        contentPanel.add(lblTrangThai, gbc_lblTrangThai);

        cboTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        GridBagConstraints gbc_cboTrangThai = new GridBagConstraints();
        gbc_cboTrangThai.gridwidth = 2;
        gbc_cboTrangThai.insets = new Insets(0, 0, 5, 10);
        gbc_cboTrangThai.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboTrangThai.gridx = 1;
        gbc_cboTrangThai.gridy = 2;
        cboTrangThai.addItem("Chưa hoàn tất");
        cboTrangThai.addItem("Hoàn tất");
        cboTrangThai.addItem("Bị hủy");
        contentPanel.add(cboTrangThai, gbc_cboTrangThai);

        GridBagConstraints gbc_btnAdd = new GridBagConstraints();
        gbc_btnAdd.anchor = GridBagConstraints.WEST;
        gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
        gbc_btnAdd.gridx = 1;
        gbc_btnAdd.gridy = 3;
        contentPanel.add(btnAdd, gbc_btnAdd);

    }

    private void initTable() {
        tableModel = new DefaultTableModel(
                new Object[] { "Nguyên liệu", "Số lượng nhập", "Đơn vị tính", "Đơn giá nhập", "Ngày hết hạn",
                        "Thao tác" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 2;
            }
        };

        tableLoNguyenLieu = new JTable(tableModel);
        tableLoNguyenLieu.setRowHeight(35);
        tableLoNguyenLieu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableLoNguyenLieu.setGridColor(new Color(189, 189, 189));
        tableLoNguyenLieu.setShowGrid(true);
        tableLoNguyenLieu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableLoNguyenLieu.getTableHeader().setBackground(new Color(33, 150, 243));
        tableLoNguyenLieu.getTableHeader().setForeground(Color.WHITE);
        tableLoNguyenLieu.setSelectionBackground(new Color(187, 222, 251));

        nguyenLieuList = nguyenLieuBUS.getAllActive();

        tableLoNguyenLieu.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JComboBox<>()) {
            private JComboBox<NguyenLieuDTO> cboBox;

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                    int column) {
                cboBox = new JComboBox<>();
                cboBox.addItem(new NguyenLieuDTO(0, "---Chọn nguyên liệu---"));
                for (NguyenLieuDTO nguyenLieu : nguyenLieuList) {
                    cboBox.addItem(nguyenLieu);
                }
                if (value instanceof NguyenLieuDTO) {
                    cboBox.setSelectedItem(value);
                } else {
                    cboBox.setSelectedIndex(0);
                }
                cboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));

                cboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            NguyenLieuDTO selectedNL = (NguyenLieuDTO) cboBox.getSelectedItem();
                            if (selectedNL != null && selectedNL.getIdNL() != 0) {
                                tableModel.setValueAt(selectedNL.getDonvi(), row, 2);
                            }
                        }
                    }
                });

                return cboBox;
            }

            @Override
            public Object getCellEditorValue() {
                return cboBox.getSelectedItem();
            }

            @Override
            public boolean stopCellEditing() {
                fireEditingStopped();
                return super.stopCellEditing();
            }
        });

        tableLoNguyenLieu.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JComboBox<NguyenLieuDTO> cboBox = new JComboBox<>();
                cboBox.addItem(new NguyenLieuDTO(0, "---Chọn nguyên liệu---"));
                for (NguyenLieuDTO nguyenLieu : nguyenLieuList) {
                    cboBox.addItem(nguyenLieu);
                }
                if (value instanceof NguyenLieuDTO) {
                    cboBox.setSelectedItem(value);
                    NguyenLieuDTO selectedNL = (NguyenLieuDTO) cboBox.getSelectedItem();
                    if (selectedNL != null && selectedNL.getIdNL() != 0) {
                        tableModel.setValueAt(selectedNL.getDonvi(), row, 2);
                    }
                } else {
                    cboBox.setSelectedIndex(0);
                }
                cboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                if (isSelected) {
                    cboBox.setBackground(table.getSelectionBackground());
                } else {
                    cboBox.setBackground(table.getBackground());
                }
                return cboBox;
            }
        });

        tableLoNguyenLieu.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField() {
            {
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                setBorder(new LineBorder(new Color(189, 189, 189), 1));
            }
        }));

        tableLoNguyenLieu.getColumnModel().getColumn(2).setCellEditor(null);

        tableLoNguyenLieu.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JTextField() {
            {
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                setBorder(new LineBorder(new Color(189, 189, 189), 1));
            }
        }));

        tableLoNguyenLieu.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField() {
            {
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                setBorder(new LineBorder(new Color(189, 189, 189), 1));
            }
        }));

        tableLoNguyenLieu.getColumnModel().getColumn(5).setCellRenderer((new ButtonRenderer()));
        tableLoNguyenLieu.getColumnModel().getColumn(5).setCellEditor((new ButtonEditor()));

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(500, 240));
        scrollPane.setViewportView(tableLoNguyenLieu);
        scrollPane.setBorder(new LineBorder(new Color(189, 189, 189), 1));
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridheight = 4;
        gbc_scrollPane.insets = new Insets(2, 0, 5, 0);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 4;
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
        gbc_actionPanel.gridy = 8;
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

    private void loadComboBoxNCC(List<NhaCungCapDTO> arr) {
        if (cboNCC == null) {
            throw new IllegalStateException("cboNCC is not initialized");
        }
        cboNCC.removeAllItems();
        NhaCungCapDTO nccDefault = new NhaCungCapDTO();
        nccDefault.setTenNCC("---Chọn nhà cung cấp---");
        cboNCC.addItem(nccDefault);
        if (arr != null) {
            for (NhaCungCapDTO item : arr) {
                cboNCC.addItem(item);
            }
        } else {
            System.out.println("Không có nhà cung cấp nào được tải vào cboNCC");
        }
    }

    public void showAdd() {
        List<NhaCungCapDTO> dsNCC = nhaCungCapBus.getAllActive();
        loadComboBoxNCC(dsNCC);
        cboTrangThai.setEnabled(false);

        tableModel.setRowCount(0);
        if (nguyenLieuList != null && !nguyenLieuList.isEmpty()) {
            tableModel.addRow(new Object[] { nguyenLieuList.get(0), "", "", "", "", "Xóa" });
        } else {
            tableModel.addRow(new Object[] { new NguyenLieuDTO(0, "---Chọn nguyên liệu---"), "", "", "", "", "Xóa" });
        }

        btnSubmit.setText("Thêm");
        btnSubmit.setActionCommand("add");
        setVisible(true);
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
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (isSelected) {
                setBackground(new Color(239, 83, 80));
            } else {
                setBackground(new Color(244, 67, 54));
            }
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
        private int row;
        private boolean isPushed;

        public ButtonEditor() {
            button = new JButton("Xóa");
            button.setBackground(new Color(244, 67, 54));
            button.setForeground(Color.WHITE);
            button.setOpaque(true);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button.setBorderPainted(false);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            this.row = row;
            isPushed = false;
            for (ActionListener al : button.getActionListeners()) {
                button.removeActionListener(al);
            }
            button.addActionListener(e -> {
                isPushed = true;
                fireEditingStopped();
            });
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                SwingUtilities.invokeLater(() -> {
                    if (row >= 0 && row < tableModel.getRowCount()) {
                        tableModel.removeRow(row);
                        tableLoNguyenLieu.revalidate();
                        tableLoNguyenLieu.repaint();
                    }
                });
            }
            return "Xóa";
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    private boolean isError() {
        boolean isError = false;

        if (cboNCC.getSelectedItem() == null || cboNCC.getSelectedItem().toString().equals("---Chọn nhà cung cấp---")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            isError = true;
        }

        return isError;
    }

    private String validateForm(String soLuong, String donGia, String ngayHetHan) {
        if (soLuong.isEmpty()) {
            return "Vui lòng nhập số lượng nhập!";
        }
        if (donGia.isEmpty()) {
            return "Vui lòng nhập đơn giá!";
        }
        if (ngayHetHan.isEmpty()) {
            return "Vui lòng nhập ngày hết hạn!";
        }

        try {
            Float.parseFloat(soLuong);
        } catch (NumberFormatException e) {
            return "Số lượng không hợp lệ!";
        }

        try {
            Integer.parseInt(donGia);
        } catch (NumberFormatException e) {
            return "Đơn giá không hợp lệ!";
        }

        try {
            // Tạo SimpleDateFormat với định dạng
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false); // Tắt chế độ linh hoạt để kiểm tra nghiêm ngặt
            java.util.Date parsedDate = sdf.parse(ngayHetHan);

            // Chuyển thành java.sql.Date (nếu cần)
            Date sqlDate = new Date(parsedDate.getTime());
        } catch (ParseException e) {
            return "Ngày hết hạn không hợp lệ (Định dạng: yyyy-mm-dd)";
        }

        return null;
    }

    private void submitForm() {
        if (!isError()) {
            NhaCungCapDTO nhaCungCapSelected = (NhaCungCapDTO) cboNCC.getSelectedItem();
            int idNCC = nhaCungCapSelected.getIdNCC();
            int trangthai = cboTrangThai.getSelectedItem() == "Chưa hoàn tất" ? 1
                    : (cboTrangThai.getSelectedItem() == "Hoàn tất" ? 2 : 3);

            if (tableLoNguyenLieu.isEditing()) {
                tableLoNguyenLieu.getCellEditor().stopCellEditing();
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một nguyên liệu!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            HashSet<Integer> usedIdNL = new HashSet<>();
            List<Lo_NguyenLieuDTO> loNguyenLieuList = new ArrayList<>();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                NguyenLieuDTO nl = (NguyenLieuDTO) tableModel.getValueAt(i, 0);
                Object soLuongObj = tableModel.getValueAt(i, 1);
                String soLuongStr = soLuongObj != null ? soLuongObj.toString().trim() : "";
                Object donGiaObj = tableModel.getValueAt(i, 3);
                String donGiaStr = donGiaObj != null ? donGiaObj.toString().trim() : "";
                Object ngayHetHanObj = tableModel.getValueAt(i, 4);
                String ngayHetHanStr = ngayHetHanObj != null ? ngayHetHanObj.toString().trim() : "";
                // System.out.println("Hàng " + i + ": Nguyên liệu = " + (nl != null ?
                // nl.getTenNL() : "null") + ", Số lượng = " + soLuongStr + ", Đơn giá = " +
                // donGiaStr + ", Ngày hết hạn = " + ngayHetHanStr);

                if (nl == null || nl.getIdNL() == 0) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn nguyên liệu hợp lệ tại hàng " + (i + 1) + "!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String errorMsg = validateForm(soLuongStr, donGiaStr, ngayHetHanStr);
                if (errorMsg != null) {
                    JOptionPane.showMessageDialog(this, errorMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ;

                if (usedIdNL.contains(nl.getIdNL())) {
                    JOptionPane.showMessageDialog(this, "Nguyên liệu '" + nl.getTenNL() + "' đã được chọn!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                usedIdNL.add(nl.getIdNL());
                
                try {
                    float soLuong = Float.parseFloat(soLuongStr);
                    int donGia = Integer.parseInt(donGiaStr);

                    if (soLuong <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0 tại hàng " + (i + 1) + "!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (donGia <= 0) {
                        JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0 tại hàng " + (i + 1) + "!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    loNguyenLieuList
                            .add(new Lo_NguyenLieuDTO(nl.getIdNL(), 0, soLuong, soLuong, donGia, ngayHetHanStr));

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là số hợp lệ tại hàng " + (i + 1) + "!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String actionCommand = btnSubmit.getActionCommand();
            int beginIndex = actionCommand.indexOf('_') + 1;
            try {
                if (beginIndex == 0) { // Thêm một phiếu nhập mới
                    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    // FIXME: sửa lại sau khi ráp code (đã đăng nhập)
                    int idTK = currentUser == null ? 1 : currentUser.getIdTK();
                    PhieuNhapDTO pn = new PhieuNhapDTO(today, today, idTK, idNCC, trangthai);
                    System.out.println(pn);
                    int result = phieuNhapBUS.add(pn, loNguyenLieuList);
                    if (result != -1) {
                        JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    // int idCT = Integer.parseInt(actionCommand.substring(beginIndex));
                    // CongThucDTO ct = new CongThucDTO(idCT, idSP, mota);
                    // error = congThucBus.update(ct, chiTietList);
                }
            } catch (Exception e) {
                // if (e.getCause() instanceof
                // java.sql.SQLIntegrityConstraintViolationException) {
                // JOptionPane.showMessageDialog(this, "Lỗi: Không thể thêm/cập nhật công thức
                // do trùng khóa chính. Vui lòng kiểm tra cơ sở dữ liệu!", "Lỗi cơ sở dữ liệu",
                // JOptionPane.ERROR_MESSAGE);
                // } else {
                // JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi",
                // JOptionPane.ERROR_MESSAGE);
                // }
                e.printStackTrace();
                return;
            }
        }
    }

    private void cancel() {
        dispose();
    }
}
