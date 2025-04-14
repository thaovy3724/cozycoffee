package GUI.NguyenLieu;

import BUS.NguyenLieuBUS;
import BUS.Lo_NguyenLieuBUS;
import BUS.PhieuNhapBUS;
import DTO.NguyenLieuDTO;
import DTO.Lo_NguyenLieuDTO;
import DTO.PhieuNhapDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InputDialog extends JDialog {
    private NguyenLieuBUS nlBus;
    private Lo_NguyenLieuBUS loBus;
    private PhieuNhapBUS pnBus;
    private JTextField idPNField, idNLField, tenNLField, donviField, soLuongNhapField, tonKhoField, donGiaField, hsdField;
    private boolean confirmed = false;
    private boolean isAdd;

    public InputDialog(Frame parent, boolean isAdd, NguyenLieuDTO nl, int nextIdNL) {
        super(parent, isAdd ? "Thêm nguyên liệu" : "Sửa nguyên liệu", true);
        this.nlBus = new NguyenLieuBUS();
        this.loBus = new Lo_NguyenLieuBUS();
        this.pnBus = new PhieuNhapBUS();
        this.isAdd = isAdd;
        initComponents(nl, nextIdNL);
    }

    private void initComponents(NguyenLieuDTO nl, int nextIdNL) {
        setSize(400, isAdd ? 400 : 300);
        setLocationRelativeTo(getParent());
        setLayout(new GridLayout(isAdd ? 9 : 6, 2, 10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);

        if (isAdd) {
            JLabel idPNLabel = new JLabel("ID phiếu nhập:");
            idPNLabel.setFont(labelFont);
            add(idPNLabel);
            idPNField = new JTextField();
            idPNField.setFont(textFont);
            idPNField.setEnabled(false);
            idPNField.setBackground(new Color(230, 230, 230));
            idPNField.setText(String.valueOf(pnBus.getNextIdPN()));
            add(idPNField);
        }

        JLabel idNLLabel = new JLabel("ID nguyên liệu:");
        idNLLabel.setFont(labelFont);
        add(idNLLabel);
        idNLField = new JTextField();
        idNLField.setFont(textFont);
        idNLField.setEnabled(false);
        idNLField.setBackground(new Color(230, 230, 230));
        add(idNLField);

        JLabel tenNLLabel = new JLabel("Tên nguyên liệu:");
        tenNLLabel.setFont(labelFont);
        add(tenNLLabel);
        tenNLField = new JTextField();
        tenNLField.setFont(textFont);
        add(tenNLField);

        JLabel donviLabel = new JLabel("Đơn vị:");
        donviLabel.setFont(labelFont);
        add(donviLabel);
        donviField = new JTextField();
        donviField.setFont(textFont);
        add(donviField);

        JLabel tonKhoLabel = new JLabel("Tồn kho:");
        tonKhoLabel.setFont(labelFont);
        add(tonKhoLabel);
        tonKhoField = new JTextField();
        tonKhoField.setFont(textFont);
        add(tonKhoField);

        JLabel hsdLabel = new JLabel("HSD (YYYY-MM-DD):");
        hsdLabel.setFont(labelFont);
        add(hsdLabel);
        hsdField = new JTextField();
        hsdField.setFont(textFont);
        add(hsdField);

        if (isAdd) {
            JLabel soLuongNhapLabel = new JLabel("Số lượng nhập:");
            soLuongNhapLabel.setFont(labelFont);
            add(soLuongNhapLabel);
            soLuongNhapField = new JTextField();
            soLuongNhapField.setFont(textFont);
            add(soLuongNhapField);

            JLabel donGiaLabel = new JLabel("Đơn giá:");
            donGiaLabel.setFont(labelFont);
            add(donGiaLabel);
            donGiaField = new JTextField();
            donGiaField.setFont(textFont);
            add(donGiaField);
        }

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        okButton.setBackground(new Color(50, 168, 82));
        okButton.setForeground(Color.WHITE);
        add(okButton);

        JButton cancelButton = new JButton("Hủy");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        add(cancelButton);

        // Điền dữ liệu
        if (isAdd) {
            idNLField.setText(String.valueOf(nextIdNL));
        } else if (nl != null) {
            idNLField.setText(String.valueOf(nl.getIdNL()));
            tenNLField.setText(nl.getTenNL());
            donviField.setText(nl.getDonvi());
            float tonkho = 0;
            String hsd = "";
            try {
                List<Lo_NguyenLieuDTO> loList = loBus.getAllLoNguyenLieu();
                if (loList != null && !loList.isEmpty()) {
                    Lo_NguyenLieuDTO latest = null;
                    for (Lo_NguyenLieuDTO lo : loList) {
                        if (lo.getIdNL() == nl.getIdNL()) {
                            if (latest == null || lo.getIdPN() > latest.getIdPN()) {
                                latest = lo;
                            }
                        }
                    }
                    if (latest != null) {
                        tonkho = latest.getTonkho();
                        hsd = latest.getHsd() != null ? latest.getHsd() : "";
                    }
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi lấy danh sách lô nguyên liệu: " + e.getMessage());
                e.printStackTrace();
            }
            tonKhoField.setText(String.valueOf(tonkho));
            hsdField.setText(hsd);
        }

        // Sự kiện nút
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void saveData() {
        try {
            int idNL = Integer.parseInt(idNLField.getText().trim());
            String tenNL = tenNLField.getText().trim();
            String donvi = donviField.getText().trim();

            if (tenNL.isEmpty() || donvi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên và đơn vị không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success;
            if (isAdd) {
                int idPN = Integer.parseInt(idPNField.getText().trim());
                float soLuongNhap = Float.parseFloat(soLuongNhapField.getText().trim());
                float tonKho = Float.parseFloat(tonKhoField.getText().trim());
                int donGia = (int) Float.parseFloat(donGiaField.getText().trim());
                String hsd = hsdField.getText().trim();

                if (soLuongNhap <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng nhập phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tonKho < 0) {
                    JOptionPane.showMessageDialog(this, "Tồn kho không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (donGia <= 0) {
                    JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (hsd.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "HSD không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(hsd);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "HSD phải có định dạng YYYY-MM-DD!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PhieuNhapDTO pn = new PhieuNhapDTO();
                pn.setIdPN(idPN);
                String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                pn.setNgaytao(currentDate);
                pn.setNgaycapnhat(currentDate);
                pn.setIdTK(1);
                pn.setIdNCC(1);
                pn.setIdTT(1);
                success = pnBus.addPhieuNhap(pn);
                if (!success) {
                    JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                NguyenLieuDTO nl = new NguyenLieuDTO(idNL, donvi, tenNL);
                success = nlBus.addNguyenLieu(nl);
                if (!success) {
                    pnBus.deletePhieuNhap(idPN);
                    JOptionPane.showMessageDialog(this, "Thêm nguyên liệu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Lo_NguyenLieuDTO lo = new Lo_NguyenLieuDTO(idNL, idPN, soLuongNhap, tonKho, donGia, hsd);
                success = loBus.addLoNguyenLieu(lo);
                if (!success) {
                    nlBus.deleteNguyenLieu(idNL);
                    pnBus.deletePhieuNhap(idPN);
                    JOptionPane.showMessageDialog(this, "Thêm lô nguyên liệu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(this, "Thêm nguyên liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                float tonKho = Float.parseFloat(tonKhoField.getText().trim());
                String hsd = hsdField.getText().trim();

                if (tonKho < 0) {
                    JOptionPane.showMessageDialog(this, "Tồn kho không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (hsd.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "HSD không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(hsd);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "HSD phải có định dạng YYYY-MM-DD!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                NguyenLieuDTO nl = new NguyenLieuDTO(idNL, donvi, tenNL);
                success = nlBus.updateNguyenLieu(nl);
                if (!success) {
                    JOptionPane.showMessageDialog(this, "Sửa nguyên liệu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                success = loBus.updateTonKho(idNL, tonKho);
                if (!success) {
                    JOptionPane.showMessageDialog(this, "Sửa tồn kho thất bại! Không tìm thấy lô nguyên liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                success = loBus.updateHSD(idNL, hsd);
                if (!success) {
                    JOptionPane.showMessageDialog(this, "Sửa HSD thất bại! Không tìm thấy lô nguyên liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(this, "Sửa nguyên liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }

            confirmed = true;
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho ID, số lượng, tồn kho, đơn giá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi không xác định: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}