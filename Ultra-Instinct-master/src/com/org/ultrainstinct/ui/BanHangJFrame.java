package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.config.SqlConfig;
import com.org.ultrainstinct.dao.HoaDonDAO;
import org.apache.commons.lang3.StringUtils;

import com.org.ultrainstinct.dao.KhachHangDAO;
import com.org.ultrainstinct.dao.SanPhamDAO;
import com.org.ultrainstinct.dao.impl.HoaDonDAOImpl;
import com.org.ultrainstinct.dao.impl.KhachHangDAOImpl;
import com.org.ultrainstinct.dao.impl.SanPhamDAOImpl;
import com.org.ultrainstinct.dto.UserSession;
import com.org.ultrainstinct.main.Main;
import com.org.ultrainstinct.model.HoaDon;
import com.org.ultrainstinct.model.HoaDonChiTiet;
import com.org.ultrainstinct.model.KhachHang;
import com.org.ultrainstinct.model.SanPham;
import com.org.ultrainstinct.utils.Constant;
import com.org.ultrainstinct.utils.DateUtil;
import com.org.ultrainstinct.utils.MessageDialog;
import com.org.ultrainstinct.utils.StringUtil;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.lang3.ObjectUtils;

public class BanHangJFrame extends javax.swing.JPanel {
    private Main main;
    private final SanPhamDAO sanPhamDao;
    private final KhachHangDAO khachHangDAO;
    private final HoaDonDAO hoaDonDAO;

    public BanHangJFrame(Main main) {
        this.main = main;
        sanPhamDao = new SanPhamDAOImpl();
        khachHangDAO = new KhachHangDAOImpl() {};
        hoaDonDAO = new HoaDonDAOImpl();
        initComponents();
        initSanPhamList();
        initTimeCurrent();
    }

    public final void initSanPhamList() {
        DefaultTableModel model = (DefaultTableModel) sanPhamTable.getModel();
        model.setRowCount(0); // Clear the table before adding new data

        try {
            // Get the keyword from the search text field (if applicable)
            String keyword = txtTimKiemSanPham.getText();

            // Initialize the list for SanPham records
            List<SanPham> sanPhamList = new ArrayList<>();

            // Fetch records based on the keyword
            if (keyword != null && !keyword.isEmpty()) {

                sanPhamList = sanPhamDao.selectByKeyword(keyword);
            } else {
                // If no keyword is specified, fetch all records
                sanPhamList = sanPhamDao.findAll();
            }

            // Add rows to the table model
            for (int i = 0; i < sanPhamList.size(); i++) {
                SanPham sp = sanPhamList.get(i);
                Object[] row = {
                    i + 1,
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getGiaNiemYet(),
                    sp.getSoLuongTon()
                };
                model.addRow(row);
            }

            // Set the model to the table
            sanPhamTable.setModel(model);

            // Add the table to the scroll pane
            jScrollPane1.setViewportView(sanPhamTable);

        } catch (Exception e) {
            MessageDialog.alertError(this, "Không tìm thấy sản phẩm!");
        }
    }
   //Tạo hàm xuất hóa đơn
  public void XuatHoaDon(String idhd) {
    try {
        Hashtable<String, Object> map = new Hashtable<>();
        String reportPath = "E:\\DuAn1\\DuAn1\\src\\com\\org\\ultrainstinct\\ui\\HoaDonrpt.jrxml";
        System.out.println("Compiling report from: " + reportPath);
        JasperReport report = JasperCompileManager.compileReport(reportPath);
        
        map.put("MaHD", idhd);
        System.out.println("Report parameters: " + map);
                  
        System.out.println("Getting database connection...");
        JasperPrint p = JasperFillManager.fillReport(report, map, SqlConfig.getConnection());
        System.out.println("Report filled successfully.");
        
        JasperViewer.viewReport(p, false);
        String pdfPath = "test.pdf";
        System.out.println("Exporting report to: " + pdfPath);
        JasperExportManager.exportReportToPdfFile(p, pdfPath);
        System.out.println("Report exported successfully.");
    } catch (Exception ex) {
        System.out.println("An error occurred: " + ex.getMessage());
        ex.printStackTrace(); // Print stack trace for more details
    }
}

    public final void searchSanPham(String sanPhamTextSearch) {
        List<SanPham> sanPhamList = sanPhamDao.findAll();
        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Giá", "Số lượng"},
                0
        );
        // Populate the model with data from sanPhamList
        for (int i = 0; i < sanPhamList.size(); i++) {
            SanPham sp = sanPhamList.get(i);
            model.addRow(new Object[]{i + 1, sp.getMaSanPham(), sp.getTenSanPham(), sp.getGiaNiemYet(), sp.getSoLuongTon()});
        }

        // Set the model to the table
        sanPhamTable.setModel(model);

        // Add the table to the scroll pane
        jScrollPane1.setViewportView(sanPhamTable);
    }

    public final void findByMaKhachHang(String maKhachHang) throws SQLException {
        KhachHang khachHang = khachHangDAO.findByMaKhachHang(maKhachHang);
        if (ObjectUtils.isNotEmpty(khachHang)) {
            txtMaKhachHang.setText(khachHang.getMaKhachHang());
            txtTenKhachHang.setText(khachHang.getTenKH());
        }

    }

    private void updateSecondTableSanPham(Object[] rowData) {
        DefaultTableModel model = (DefaultTableModel) sanPhamTable2.getModel();
        int count = model.getRowCount();
        rowData[0] = count + 1;
        model.addRow(rowData);

    }

    private void calculateChange() {
        try {
            // Get the total amount from lblTongTien
            String txtTotalHoaDon = lblTongTien.getText();
            Double totalHoaDon = Double.valueOf(txtTotalHoaDon);

            Double khachtra = Double.valueOf(txtKhachTra.getText());

            // Calculate the change to be returned to the customer
            Double tienThoi = khachtra - totalHoaDon;

            // Update lblTienThoi with the calculated change
            txtTraLaiKhach.setText(tienThoi.toString());

        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid number
            txtTraLaiKhach.setText("Invalid input");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sanPhamTable = new javax.swing.JTable();
        lblTongTien = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        sanPhamTable2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jtextKhachHangSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lbDateCurrent = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaKhachHang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        btnAddKH = new javax.swing.JButton();
        jBtnKhachHangSeach = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtTimKiemSanPham = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTongSoLuong = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtKhachTra = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTraLaiKhach = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnThanhToan = new javax.swing.JButton();
        btnClearForm = new javax.swing.JButton();
        txtGhiChu = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("jLabel4");

        setBackground(new java.awt.Color(245, 245, 245));
        setAutoscrolls(true);

        sanPhamTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Giá ", "Số lượng"
            }
        ));
        sanPhamTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sanPhamTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(sanPhamTable);

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/org/ultrainstinct/ui/Bundle"); // NOI18N
        lblTongTien.setText(bundle.getString("BanHangJFrame.jLabel14.text")); // NOI18N

        sanPhamTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên sản phẩm", "Số lượng", "Giá", "T. Tiền", "Mã sản phẩm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(sanPhamTable2);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel8.setText(bundle.getString("BanHangJFrame.jLabel8.text")); // NOI18N

        jLabel1.setText(bundle.getString("BanHangJFrame.jLabel1.text")); // NOI18N

        jtextKhachHangSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtextKhachHangSearchActionPerformed(evt);
            }
        });

        jLabel2.setText(bundle.getString("BanHangJFrame.jLabel2.text")); // NOI18N

        lbDateCurrent.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbDateCurrent.setText(bundle.getString("BanHangJFrame.jLabel9.text")); // NOI18N

        jLabel3.setText(bundle.getString("BanHangJFrame.jLabel3.text")); // NOI18N

        txtMaKhachHang.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText(bundle.getString("BanHangJFrame.jLabel11.text")); // NOI18N

        btnAddKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/org/ultrainstinct/icon/add.png"))); // NOI18N
        btnAddKH.setText(bundle.getString("BanHangJFrame.jButton3.text")); // NOI18N
        btnAddKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKHActionPerformed(evt);
            }
        });

        jBtnKhachHangSeach.setText(bundle.getString("BanHangJFrame.jBtnKhachHangSeach.text")); // NOI18N
        jBtnKhachHangSeach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBtnKhachHangSeachMouseClicked(evt);
            }
        });
        jBtnKhachHangSeach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnKhachHangSeachActionPerformed(evt);
            }
        });

        jLabel12.setText(bundle.getString("BanHangJFrame.jLabel12.text")); // NOI18N

        txtTimKiemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemSanPhamActionPerformed(evt);
            }
        });
        txtTimKiemSanPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemSanPhamKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimKiemSanPhamKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText(bundle.getString("BanHangJFrame.jLabel13.text")); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Tổng số lượng SP ");

        lblTongSoLuong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTongSoLuong.setText("0");

        jLabel6.setText("Khách trả");

        txtKhachTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKhachTraKeyTyped(evt);
            }
        });

        jLabel7.setText("Trả lại khách");

        txtTraLaiKhach.setEditable(false);

        jLabel9.setText("Sản phẩm khách mua");

        btnThanhToan.setBackground(new java.awt.Color(51, 204, 0));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText(bundle.getString("BanHangJFrame.jButton4.text")); // NOI18N
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnClearForm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/org/ultrainstinct/icon/add-document.png"))); // NOI18N
        btnClearForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFormActionPerformed(evt);
            }
        });

        txtGhiChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGhiChuActionPerformed(evt);
            }
        });

        jLabel10.setText("Ghi chú");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addComponent(jLabel1)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel5)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbDateCurrent)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTongTien)
                                    .addComponent(lblTongSoLuong, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTraLaiKhach)
                                    .addComponent(txtMaKhachHang)
                                    .addComponent(txtTenKhachHang)
                                    .addComponent(txtKhachTra)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jtextKhachHangSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(41, 41, 41)
                                        .addComponent(jBtnKhachHangSeach, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(btnAddKH)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtGhiChu)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearForm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThanhToan)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel12)
                            .addComponent(jtextKhachHangSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnKhachHangSeach)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(lbDateCurrent)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddKH)))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(lblTongSoLuong))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(lblTongTien))
                                .addGap(35, 35, 35)
                                .addComponent(txtKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtTraLaiKhach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnThanhToan)
                                    .addComponent(btnClearForm))
                                .addGap(0, 262, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sanPhamTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sanPhamTableMouseClicked
        int selectedRow = sanPhamTable.getSelectedRow();
        if (selectedRow != -1) {
            // Fetch data from the selected row
            Object[] rowData = new Object[6];
            for (int i = 0; i < sanPhamTable.getColumnCount(); i++) {
                rowData[i] = sanPhamTable.getValueAt(selectedRow, i);
            }
            Float price = rowData[3] != null ? (Float) rowData[3] : 0;
            Integer quantity = rowData[4] != null ? (Integer) rowData[4] : 0;

            // Check if the product already exists in the second table
            boolean exists = false;
            for (int i = 0; i < sanPhamTable2.getRowCount(); i++) {
                if (sanPhamTable2.getValueAt(i, 1).equals(rowData[2])) { // Assuming column 1 contains product identifier
                    // Update the quantity
                    Integer currentQuantity = (Integer) sanPhamTable2.getValueAt(i, 2);
                    currentQuantity += 1; // Increment by 1
                    sanPhamTable2.setValueAt(currentQuantity, i, 2);

                    // Recalculate the total price for this row
                    Float newTotal = currentQuantity * price;
                    sanPhamTable2.setValueAt(newTotal, i, 4);
                    exists = true;
                    break;
                }
            }

            // If the product does not exist, add a new row
            if (!exists) {
                Object[] rowDataCal = new Object[7];
                rowDataCal[0] = sanPhamTable2.getRowCount() + 1; // STT
                rowDataCal[1] = rowData[2];
                rowDataCal[2] = 1; // Initial quantity is 1
                rowDataCal[3] = rowData[3];
                rowDataCal[4] = price; // Initial total is price * 1
                rowDataCal[5] = rowData[1];
                ((DefaultTableModel) sanPhamTable2.getModel()).addRow(rowDataCal);
            }

            // Update the total amount in lblTongTien
            String txtTotalHoaDon = lblTongTien.getText();
            Double totalHoaDon = Double.valueOf(txtTotalHoaDon);
            totalHoaDon += price; // Add the price of the newly added/incremented product
            lblTongTien.setText(totalHoaDon.toString());

            // Calculate the total quantity of products
            int totalQuantity = 0;
            for (int i = 0; i < sanPhamTable2.getRowCount(); i++) {
                totalQuantity += (Integer) sanPhamTable2.getValueAt(i, 2);
            }

            // Update lblTongSoLuong with the total quantity
            lblTongSoLuong.setText(String.valueOf(totalQuantity));

        }
    }//GEN-LAST:event_sanPhamTableMouseClicked
    private boolean isKhachHangIsEmty() {
        boolean isCheckKhachHangIsEmty = Boolean.FALSE;
        if (StringUtils.isBlank(txtMaKhachHang.getText())) {
            MessageDialog.alertError(this, "Vui lòng không để trống mã khách hàng !!!");
            isCheckKhachHangIsEmty = Boolean.TRUE;
        }

        if (isCheckKhachHangIsEmty && StringUtils.isBlank(txtTenKhachHang.getText())) {
            MessageDialog.alertError(this, "Vui lòng không để trống tên khách hàng !!!");
            isCheckKhachHangIsEmty = Boolean.TRUE;
        }
        return isCheckKhachHangIsEmty;
    }
    private void ClearForm() {                                             
    // Clear text fields
    jtextKhachHangSearch.setText("");
    txtMaKhachHang.setText("");
    txtTenKhachHang.setText("");
    txtTimKiemSanPham.setText("");
    txtKhachTra.setText("");
    txtTraLaiKhach.setText("");
    txtGhiChu.setText("");

    // Clear tables

    DefaultTableModel model2 = (DefaultTableModel) sanPhamTable2.getModel();
    model2.setRowCount(0); // Clear the selected products table

    // Reset totals
    lblTongTien.setText("0");
    lblTongSoLuong.setText("0");
} 
    private boolean isSanPhamIsEmty() {
        boolean isSanPhamIsEmty = Boolean.FALSE;
        if (sanPhamTable2.getRowCount() == 0) {
            MessageDialog.alertError(this, "Vui lòng chọn sản phẩm !!!");
            isSanPhamIsEmty = Boolean.TRUE;
        }
        return isSanPhamIsEmty;
    }

    private void initTimeCurrent() {
        lbDateCurrent.setText(DateUtil.localDateToString(LocalDate.now(), DateUtil.YYYY_MM_DD));
    }

    private List<HoaDonChiTiet> getAllDataFromTableSanPhamListChoose(String maHoaDon) {
        DefaultTableModel model = (DefaultTableModel) sanPhamTable2.getModel();
        int rowCount = model.getRowCount();
        List<HoaDonChiTiet> donChiTietList = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            Object[] rowData = getRowData(model, i);
            donChiTietList.add(createHoaDonChiTiet(rowData, maHoaDon));
        }

        return donChiTietList;
    }

    private Object[] getRowData(DefaultTableModel model, int rowIndex) {
        int columnCount = model.getColumnCount();
        Object[] rowData = new Object[columnCount];

        for (int j = 0; j < columnCount; j++) {
            rowData[j] = model.getValueAt(rowIndex, j);
        }

        return rowData;
    }

    private HoaDonChiTiet createHoaDonChiTiet(Object[] rowData, String maHoaDon) {
        return HoaDonChiTiet.builder()
                .maHDCT(StringUtil.genCode(Constant.CODE_HDCT))
                .giaBan((Float) rowData[3])
                .soLuong((Integer) rowData[2])
                .maHoaDon(maHoaDon)
                .maSanPham(StringUtil.objectToString(rowData[5]))
                .ghiChu(txtGhiChu.getText())
                .build();
    }


    private void jtextKhachHangSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtextKhachHangSearchActionPerformed
        // TODO add your handling code here:
        String seachTextKhachHang = jtextKhachHangSearch.getText();
        if (StringUtils.isNotBlank(seachTextKhachHang)) {
            try {
                findByMaKhachHang(seachTextKhachHang);
            } catch (SQLException ex) {
                Logger.getLogger(BanHangJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jtextKhachHangSearchActionPerformed

    private void jBtnKhachHangSeachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnKhachHangSeachMouseClicked
        String seachTextKhachHang = jtextKhachHangSearch.getText();
        if (StringUtils.isNotBlank(seachTextKhachHang)) {
            try {
                findByMaKhachHang(seachTextKhachHang);
            } catch (SQLException ex) {
                Logger.getLogger(BanHangJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jBtnKhachHangSeachMouseClicked

    private void jBtnKhachHangSeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnKhachHangSeachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnKhachHangSeachActionPerformed

    private void txtTimKiemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemSanPhamActionPerformed

    private void txtTimKiemSanPhamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSanPhamKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemSanPhamKeyReleased

    private void txtTimKiemSanPhamKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSanPhamKeyTyped
        // TODO add your handling code here:
        initSanPhamList();
    }//GEN-LAST:event_txtTimKiemSanPhamKeyTyped

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        if (!isKhachHangIsEmty() && !isSanPhamIsEmty()) {
            
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHoaDon(StringUtil.genCode(Constant.CODE_HD));
            hoaDon.setMaKhachHang(txtMaKhachHang.getText());
//            hoaDon.setMaNhanVien("NV005");
            hoaDon.setMaNhanVien(UserSession.getUser().getUserName());
            hoaDon.setNgayLap(DateUtil.getDateNow());
            hoaDon.setTrangThai(Boolean.FALSE);
            
            List<HoaDonChiTiet> hoaDonChiTiets = getAllDataFromTableSanPhamListChoose(hoaDon.getMaHoaDon());
            try {
                  if (MessageDialog.confirm(this, "Bạn thực sự muốn thanh toán hóa đơn này?")) {
                hoaDonDAO.save(hoaDon, hoaDonChiTiets);
                MessageDialog.alert(this, "Đặt hàng thành công !!!");
                  ClearForm();
                  XuatHoaDon(hoaDon.getMaHoaDon());}
            } catch (SQLException ex) {
                Logger.getLogger(BanHangJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtKhachTraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachTraKeyTyped
        // TODO add your handling code here:
        calculateChange();
    }//GEN-LAST:event_txtKhachTraKeyTyped

    private void txtGhiChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGhiChuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGhiChuActionPerformed

    private void btnClearFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFormActionPerformed
        // TODO add your handling code here:
        ClearForm();
    }//GEN-LAST:event_btnClearFormActionPerformed

    private void btnAddKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddKHActionPerformed
        try {
            // TODO add your handling code here:
            KhachHangJFrame kh =  new KhachHangJFrame(main);
            main.showForm(kh);
        } catch (SQLException ex) {
            Logger.getLogger(BanHangJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddKHActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddKH;
    private javax.swing.JButton btnClearForm;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton jBtnKhachHangSeach;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jtextKhachHangSearch;
    private javax.swing.JLabel lbDateCurrent;
    private javax.swing.JLabel lblTongSoLuong;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable sanPhamTable;
    private javax.swing.JTable sanPhamTable2;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtKhachTra;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTimKiemSanPham;
    private javax.swing.JTextField txtTraLaiKhach;
    // End of variables declaration//GEN-END:variables
}
