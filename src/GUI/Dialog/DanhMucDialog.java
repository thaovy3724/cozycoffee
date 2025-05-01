package GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import BUS.DanhMucBUS;
import DTO.DanhMucDTO;

public class DanhMucDialog extends JDialog {
	private DanhMucBUS danhMucBus = new DanhMucBUS();

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtTenDM;
	private JComboBox<DanhMucDTO> cboDMCha = new JComboBox<>();
	private JComboBox<String> cboTrangThai = new JComboBox<>();
	private JLabel errTenDM, errDMCha, errTrangThai;
	private JButton btnSubmit, btnCancel;

	/**
	 * Create the dialog.
	 */
	public DanhMucDialog() {
		setTitle("Thêm danh mục");
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
			JLabel lblTitle = new JLabel("Thêm danh mục");
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
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModal(true);
	}

	private void textFieldInit() {
		{
			JLabel lblTenDM = new JLabel("Tên danh mục");
			lblTenDM.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblTenDM = new GridBagConstraints();
			gbc_lblTenDM.insets = new Insets(0, 10, 5, 5);
			gbc_lblTenDM.anchor = GridBagConstraints.WEST;
			gbc_lblTenDM.gridx = 0;
			gbc_lblTenDM.gridy = 1;
			contentPanel.add(lblTenDM, gbc_lblTenDM);
		}
		{
			txtTenDM = new JTextField();
			txtTenDM.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_txtTenDM = new GridBagConstraints();
			gbc_txtTenDM.gridwidth = 2;
			gbc_txtTenDM.insets = new Insets(0, 0, 5, 10);
			gbc_txtTenDM.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtTenDM.gridx = 1;
			gbc_txtTenDM.gridy = 1;
			contentPanel.add(txtTenDM, gbc_txtTenDM);
			txtTenDM.setColumns(10);
		}
		{
			errTenDM = new JLabel();
			errTenDM.setForeground(Color.RED);
			errTenDM.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_errTenDM = new GridBagConstraints();
			gbc_errTenDM.gridwidth = 2;
			gbc_errTenDM.anchor = GridBagConstraints.WEST;
			gbc_errTenDM.insets = new Insets(0, 0, 5, 10);
			gbc_errTenDM.gridx = 1;
			gbc_errTenDM.gridy = 2;
			contentPanel.add(errTenDM, gbc_errTenDM);
		}
		{
			JLabel lblDMCha = new JLabel("Danh mục cha");
			lblDMCha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			GridBagConstraints gbc_lblDMCha = new GridBagConstraints();
			gbc_lblDMCha.anchor = GridBagConstraints.EAST;
			gbc_lblDMCha.insets = new Insets(0, 10, 5, 5);
			gbc_lblDMCha.gridx = 0;
			gbc_lblDMCha.gridy = 3;
			contentPanel.add(lblDMCha, gbc_lblDMCha);
		}
		{
			cboDMCha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_cboDMCha = new GridBagConstraints();
			gbc_cboDMCha.gridwidth = 2;
			gbc_cboDMCha.insets = new Insets(0, 0, 5, 10);
			gbc_cboDMCha.fill = GridBagConstraints.HORIZONTAL;
			gbc_cboDMCha.gridx = 1;
			gbc_cboDMCha.gridy = 3;
			contentPanel.add(cboDMCha, gbc_cboDMCha);
		}
		{
			errDMCha = new JLabel();
			errDMCha.setForeground(Color.RED);
			errDMCha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			GridBagConstraints gbc_errDMCha = new GridBagConstraints();
			gbc_errDMCha.gridwidth = 2;
			gbc_errDMCha.anchor = GridBagConstraints.WEST;
			gbc_errDMCha.insets = new Insets(0, 0, 5, 10);
			gbc_errDMCha.gridx = 1;
			gbc_errDMCha.gridy = 4;
			contentPanel.add(errDMCha, gbc_errDMCha);
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

	private void loadComboBoxDMCha(List<DanhMucDTO> arr) {
		cboDMCha.removeAllItems();
		DanhMucDTO danhMucDefault = new DanhMucDTO();
		danhMucDefault.setTenDM("---Chọn danh mục---");
		cboDMCha.addItem(danhMucDefault);
		for(DanhMucDTO item : arr) {
			cboDMCha.addItem(item);
		}
	}

	public void showAdd() {
		// load tất cả danh mục đang hoạt động
		List<DanhMucDTO> listDMCha = danhMucBus.getAllActive();
		loadComboBoxDMCha(listDMCha);
		cboTrangThai.setEnabled(false);

		// reset action button
		btnSubmit.setText("Thêm");
		btnSubmit.setActionCommand("add");
		setVisible(true);
	}

	public void showEdit(int idDM) {
		DanhMucDTO danhMuc = danhMucBus.findByIdDM(idDM);
		txtTenDM.setText(danhMuc.getTenDM());
		cboTrangThai.setSelectedItem(danhMuc.getTrangthai() == 1 ? "Hoạt động" : "Bị khóa");

		// load tất cả danh mục đang hoạt động và danh mục cha của danh mục được chọn nếu có và trừ danh mục được chọn
		List<DanhMucDTO> listDMCha = danhMucBus.getAllActiveEdit(idDM, danhMuc.getIdDMCha());
		loadComboBoxDMCha(listDMCha);

		// set default
		if(danhMuc.getIdDMCha() != 0) {
			DanhMucDTO dmucCha = danhMucBus.findByIdDM(danhMuc.getIdDMCha());
			cboDMCha.setSelectedItem(dmucCha);
		}

		// reset action button
		btnSubmit.setText("Cập nhật");
		btnSubmit.setActionCommand("edit_"+idDM);
		setVisible(true);
	}

	private boolean isError() {
		// remove existed errors
		errTenDM.setText("");
		errDMCha.setText("");

		boolean isError = false;

		// tenDM
		if(txtTenDM.getText().trim().equals("")) {
			errTenDM.setText("Tên danh mục không được để trống");
			isError = true;
		}

		return isError;
	}

	private void submitForm() {
		// validate du lieu
		if(!isError()) {
			// collect data
			String tenDM = txtTenDM.getText();
			DanhMucDTO dmucChaSelected = (DanhMucDTO) cboDMCha.getSelectedItem();
			int idDMCha = dmucChaSelected.getIdDM();
			int trangthai = cboTrangThai.getSelectedItem() == "Hoạt động" ? 1 : 0;

			// action
			String error = "";
			String actionCommand = btnSubmit.getActionCommand();
			int beginIndex = actionCommand.indexOf('_')+1;
			if(beginIndex == 0) {
				// add
				error = danhMucBus.add(new DanhMucDTO(tenDM, trangthai, idDMCha));
			}else {
				// update
				int idDM = Integer.parseInt(actionCommand.substring(beginIndex));
				error = danhMucBus.update(new DanhMucDTO(idDM, tenDM, trangthai, idDMCha));
			}

			// show message
			if(error != "") {
				// fail
				JOptionPane.showMessageDialog(this, error);
			}
			else {
				// success
				if(beginIndex == 0) {
					JOptionPane.showMessageDialog(this, "Thêm thành công ");
				} else {
					JOptionPane.showMessageDialog(this, "Cập nhật thành công ");
				}
			}
		}
	}

	private void cancel() {
		dispose();
	}
}
