package GUI;
import BUS.DanhMucBUS;
import DTO.DanhMucDTO;

import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import DTO.ComboItem;

public class DanhMucPanel extends JPanel {
    DanhMucBUS danhMucBUS = new DanhMucBUS();

    private static final long serialVersionUID = 1L;
    private JTextField txtNhpIddmTn;
    private JTextField tenDMTxt;
    private JTable table;
    private JComboBox dmucChaOption;

    /**
     * Create the panel.
     */
    public DanhMucPanel() {

        setBackground(new Color(255, 255, 255));
        setBounds(0, 0, 887, 508);
        setLayout(null);
        ImageIcon searchIcon = getScaledImage(20, 20, "/ASSET/Images/searchIcon.png");

        JLabel lblNewLabel = new JLabel("Tên danh mục");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel.setBounds(112, 41, 126, 42);
        add(lblNewLabel);

        tenDMTxt = new JTextField();
        tenDMTxt.setHorizontalAlignment(SwingConstants.LEFT);
        tenDMTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tenDMTxt.setBounds(248, 47, 335, 32);
        add(tenDMTxt);
        tenDMTxt.setColumns(10);

        JLabel lblDanhMcCha = new JLabel("Danh mục cha");
        lblDanhMcCha.setHorizontalAlignment(SwingConstants.CENTER);
        lblDanhMcCha.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDanhMcCha.setBounds(112, 84, 126, 42);
        add(lblDanhMcCha);

        dmucChaOption = new JComboBox();
        dmucChaOption.setToolTipText("");
        dmucChaOption.setBounds(248, 90, 335, 32);
        add(dmucChaOption);

        JButton btnSearch_1 = new JButton("Thêm");
        btnSearch_1.setBackground(Color.GREEN);
        btnSearch_1.setBounds(602, 29, 114, 32);
        add(btnSearch_1);

        JButton btnNewButton_1_1 = new JButton("Sửa");
        btnNewButton_1_1.setBackground(Color.ORANGE);
        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton_1_1.setBounds(602, 72, 114, 32);
        add(btnNewButton_1_1);

        JButton btnNewButton_1_1_1 = new JButton("Xóa");
        btnNewButton_1_1_1.setBackground(Color.RED);
        btnNewButton_1_1_1.setBounds(602, 115, 114, 32);
        add(btnNewButton_1_1_1);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(112, 211, 604, 286);
        add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID", "Tên danh mục", "Trạng thái", "ID cha"
                }
        ));
        txtNhpIddmTn = new JTextField();
        txtNhpIddmTn.setBounds(112, 158, 525, 42);
        add(txtNhpIddmTn);
        txtNhpIddmTn.setHorizontalAlignment(SwingConstants.CENTER);
        txtNhpIddmTn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtNhpIddmTn.setText("Nhập idDM, tên danh mục");
        txtNhpIddmTn.setColumns(10);

        JButton btnNewButton = new JButton("");
        btnNewButton.setBounds(637, 158, 79, 42);
        add(btnNewButton);
        btnNewButton.setIcon(searchIcon);

        // load list
        loadDanhMucList();

        // setup field
        setUpFields();
    }

    private ImageIcon getScaledImage(int width, int height, String path) {
        // Load the original image
        ImageIcon originalIcon = new ImageIcon(DanhMucPanel.class.getResource(path));

        // Resize the image (e.g., to 50x50 pixels)
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the scaled image
        return new ImageIcon(scaledImage);
    }

    // load danh muc list
    private void loadDanhMucList() {
        DefaultTableModel dtm = new DefaultTableModel(
                new String[] {"ID", "Tên danh mục", "Trạng thái", "ID cha"}, 0
        );

        List<DanhMucDTO> danhMucArr = danhMucBUS.getAllDanhMuc();
        for (DanhMucDTO danhMuc : danhMucArr) {
            Object[] row = {
                    danhMuc.getIdDM(),
                    danhMuc.getTenDM(),
                    danhMuc.getTrangthai(),
                    danhMuc.getIdDMCha()
            };
            dtm.addRow(row);
        }

        table.setModel(dtm);
    }

    public void setUpFields() {
        // load danh sach danh muc cha
        List<DanhMucDTO> arr = danhMucBUS.getAllDanhMuc();
        dmucChaOption.addItem(new ComboItem(-1, "Không có"));
        for(DanhMucDTO item : arr)
            dmucChaOption.addItem(new ComboItem(item.getIdDM(), item.getTenDM()));
    }
}