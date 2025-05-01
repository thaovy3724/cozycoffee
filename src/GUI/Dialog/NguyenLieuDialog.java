package GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BUS.NguyenLieuBUS;
import DTO.NguyenLieuDTO;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JComboBox;

public class NguyenLieuDialog extends JDialog {
    private NguyenLieuBUS nguyenLieuBus = new NguyenLieuBUS();

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txtTenNL;
    private JComboBox<String> cboTrangThai = new JComboBox<>();
    private JLabel errTenNL, errDonVi, errTrangThai;
    private JButton btnSubmit, btnCancel;
    private JTextField txtDonVi;

    /**
     * Create the dialog.
     */
    public NguyenLieuDialog() {
        setModal(true);
        setTitle("Thêm nguyên liệu");
        setSize(445, 234);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
        gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel lblTitle = new JLabel("Thêm nguyên liệu");
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
            GridBagConstraints gbc_lblTitle = new GridBagConstraints();
            gbc_lblTitle.anchor = GridBagConstraints.WEST;
            gbc_lblTitle.gridwidth = 4;
            gbc_lblTitle.insets = new Insets(5, 10, 5, 10);
            gbc_lblTitle.gridx = 0;
            gbc_lblTitle.gridy = 0;
            contentPanel.add(lblTitle, gbc_lblTitle);
        }

        textFieldInit();
        actionInit();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
    }
    
    private void textFieldInit() {
        {
            JLabel lblTenNL = new JLabel("Tên nguyên liệu");
            lblTenNL.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            GridBagConstraints gbc_lblTenNL = new GridBagConstraints();
            gbc_lblTenNL.insets = new Insets(0, 10, 5, 5);
            gbc_lblTenNL.anchor = GridBagConstraints.WEST;
            gbc_lblTenNL.gridx = 0;
            gbc_lblTenNL.gridy = 1;
            contentPanel.add(lblTenNL, gbc_lblTenNL);
        }
        {
            txtTenNL = new JTextField();
            txtTenNL.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            GridBagConstraints gbc_txtTenNL = new GridBagConstraints();
            gbc_txtTenNL.gridwidth = 2;
            gbc_txtTenNL.insets = new Insets(0, 0, 5, 10);
            gbc_txtTenNL.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtTenNL.gridx = 1;
            gbc_txtTenNL.gridy = 1;
            contentPanel.add(txtTenNL, gbc_txtTenNL);
            txtTenNL.setColumns(10);
        }
        {
            errTenNL = new JLabel();
            errTenNL.setForeground(Color.RED);
            errTenNL.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            GridBagConstraints gbc_errTenNL = new GridBagConstraints();
            gbc_errTenNL.gridwidth = 2;
            gbc_errTenNL.anchor = GridBagConstraints.WEST;
            gbc_errTenNL.insets = new Insets(0, 0, 5, 10);
            gbc_errTenNL.gridx = 1;
            gbc_errTenNL.gridy = 2;
            contentPanel.add(errTenNL, gbc_errTenNL);
        }
        {
            JLabel lblDonVi = new JLabel("Đơn vị");
            lblDonVi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            GridBagConstraints gbc_lblDonVi = new GridBagConstraints();
            gbc_lblDonVi.anchor = GridBagConstraints.EAST;
            gbc_lblDonVi.insets = new Insets(0, 10, 5, 5);
            gbc_lblDonVi.gridx = 0;
            gbc_lblDonVi.gridy = 3;
            contentPanel.add(lblDonVi, gbc_lblDonVi);
        }
        {
            txtDonVi = new JTextField();
            txtDonVi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            txtDonVi.setColumns(10);
            GridBagConstraints gbc_txtDonVi = new GridBagConstraints();
            gbc_txtDonVi.gridwidth = 2;
            gbc_txtDonVi.insets = new Insets(0, 0, 5, 10);
            gbc_txtDonVi.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtDonVi.gridx = 1;
            gbc_txtDonVi.gridy = 3;
            contentPanel.add(txtDonVi, gbc_txtDonVi);
        }
        {
            errDonVi = new JLabel();
            errDonVi.setForeground(Color.RED);
            errDonVi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            GridBagConstraints gbc_errDonVi = new GridBagConstraints();
            gbc_errDonVi.gridwidth = 2;
            gbc_errDonVi.anchor = GridBagConstraints.WEST;
            gbc_errDonVi.insets = new Insets(0, 0, 5, 10);
            gbc_errDonVi.gridx = 1;
            gbc_errDonVi.gridy = 4;
            contentPanel.add(errDonVi, gbc_errDonVi);
        }
        {
            JLabel lblTrangThai = new JLabel("Trạng thái");
            lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            GridBagConstraints gbc_lblTrangThai = new GridBagConstraints();
            gbc_lblTrangThai.anchor = GridBagConstraints.EAST;
            gbc_lblTrangThai.insets = new Insets(0, 10, 5, 5);
            gbc_lblTrangThai.gridx = 0;
            gbc_lblTrangThai.gridy = 5;
            contentPanel.add(lblTrangThai, gbc_lblTrangThai);
        }
        {
            cboTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            GridBagConstraints gbc_cboTrangThai = new GridBagConstraints();
            gbc_cboTrangThai.gridwidth = 2;
            gbc_cboTrangThai.insets = new Insets(0, 0, 5, 10);
            gbc_cboTrangThai.fill = GridBagConstraints.HORIZONTAL;
            gbc_cboTrangThai.gridx = 1;
            gbc_cboTrangThai.gridy = 5;
            cboTrangThai.addItem("Hoạt động");
            cboTrangThai.addItem("Bị khóa");
            contentPanel.add(cboTrangThai, gbc_cboTrangThai);
        }
        {
            errTrangThai = new JLabel("");
            errTrangThai.setForeground(Color.RED);
            errTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            GridBagConstraints gbc_errTrangThai = new GridBagConstraints();
            gbc_errTrangThai.anchor = GridBagConstraints.WEST;
            gbc_errTrangThai.gridwidth = 2;
            gbc_errTrangThai.insets = new Insets(0, 0, 5, 10);
            gbc_errTrangThai.gridx = 1;
            gbc_errTrangThai.gridy = 6;
            contentPanel.add(errTrangThai, gbc_errTrangThai);
        }
    }

    private void actionInit() {
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc_actionPanel = new GridBagConstraints();
        gbc_actionPanel.insets = new Insets(0, 0, 0, 5);
        gbc_actionPanel.gridwidth = 2;
        gbc_actionPanel.fill = GridBagConstraints.BOTH;
        gbc_actionPanel.gridx = 1;
        gbc_actionPanel.gridy = 7;
        contentPanel.add(actionPanel, gbc_actionPanel);
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        {
            btnSubmit = new JButton("Thêm");
            btnSubmit.setBackground(new Color(0, 128, 0));
            btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnSubmit.setForeground(Color.WHITE);
            btnSubmit.setContentAreaFilled(false);
            btnSubmit.setOpaque(true);
            btnSubmit.addActionListener(e->submitForm());
            actionPanel.add(btnSubmit);
        }
        {
            btnCancel = new JButton("Hủy");
            btnCancel.setBackground(new Color(255, 51, 102));
            btnCancel.setForeground(new Color(255, 255, 255));
            btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnCancel.setContentAreaFilled(false);
            btnCancel.setOpaque(true);
            btnCancel.addActionListener(e->cancel());
            actionPanel.add(btnCancel);
        }
    }

    public void showAdd() {
        
        cboTrangThai.setEnabled(false);
        
        // reset action button
        btnSubmit.setText("Thêm");
        btnSubmit.setActionCommand("add");
        setVisible(true);
    }

    public void showEdit(int idNL) {
        NguyenLieuDTO nl = nguyenLieuBus.findByIdNL(idNL);
        txtTenNL.setText(nl.getTenNL());
        txtDonVi.setText(nl.getDonvi());
        cboTrangThai.setSelectedItem(nl.getTrangthai() == 1 ? "Hoạt động" : "Bị khóa");
        
        // reset action button
        btnSubmit.setText("Cập nhật");
        btnSubmit.setActionCommand("edit_"+idNL);
        setVisible(true);
    }

    private boolean isError() {
        // remove existed errors
        errTenNL.setText("");
        errDonVi.setText("");

        boolean isError = false;
        
        // tenNL
        if(txtTenNL.getText().trim().equals("")) {
            errTenNL.setText("Tên không được để trống");
            isError = true;
        }
        if (txtDonVi.getText().trim().isEmpty()) {
            errDonVi.setText("Đơn vị không được để trống");
            isError = true;
        }
        
        return isError;
    }

    private void submitForm() {
        // validate du lieu
        if(!isError()) {
            // collect data
            String tenNL = txtTenNL.getText().trim();
            String donvi = txtDonVi.getText().trim();
            int trangthai = cboTrangThai.getSelectedItem() == "Hoạt động" ? 1 : 0;

            // action
            String error = "";
            String actionCommand = btnSubmit.getActionCommand();
            int beginIndex = actionCommand.indexOf('_')+1;
            if(beginIndex == 0) {
                // add
                error = nguyenLieuBus.add(new NguyenLieuDTO(tenNL, donvi, trangthai));
            }else {
                // update
                int idNL = Integer.parseInt(actionCommand.substring(beginIndex));
                error = nguyenLieuBus.update(new NguyenLieuDTO(idNL, tenNL, donvi, trangthai));
            }
            
            // show message
            if(error != "") {
                // fail
                JOptionPane.showMessageDialog(this, error);
            }
            else {
                // success
                if(beginIndex == 0) 
                    JOptionPane.showMessageDialog(this, "Thêm thành công ");
                else 
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công ");
                dispose();
            }
        }
    }

    private void cancel() {
        dispose();
    }
}