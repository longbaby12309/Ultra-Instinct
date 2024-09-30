package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.impl.HoaDonDAOImpl;
import com.org.ultrainstinct.dao.impl.LoaiSanPhamDAOImpl;
import com.org.ultrainstinct.dao.impl.ThongKeDAOImpl;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BaoCaoDoanhThuJFrame extends javax.swing.JPanel {
     LoaiSanPhamDAOImpl loaiSanPhamDao = new LoaiSanPhamDAOImpl();
     HoaDonDAOImpl hoaDonDao= new HoaDonDAOImpl();
     ThongKeDAOImpl thongkeDao= new ThongKeDAOImpl();
     private Map <String, String> getProductTypes;
    public BaoCaoDoanhThuJFrame() {
        initComponents();
        fillComboBoxLSP();
        fillYearComboBox();
        fillTable();
    }
    
    @SuppressWarnings("unchecked")
            
    void fillComboBoxLSP() {
    try {
        Map<String, String> productTypes = loaiSanPhamDao.getProductTypes();
        cboMaLoai.removeAllItems(); // Clear existing items
        cboMaLoai.addItem("Chọn loại sản phẩm"); // Add default item

        for (Map.Entry<String, String> entry : productTypes.entrySet()) {
            cboMaLoai.addItem(entry.getKey() + " - " + entry.getValue()); // Add each product type to the combo box
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu loại sản phẩm!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    void fillYearComboBox() {
    try {
        int startYear = thongkeDao.getFirstInvoiceYear(); // Get the year of the first invoice
        int currentYear = java.time.Year.now().getValue(); // Get the current year

        cboYear.removeAllItems(); // Clear existing items

        for (int year = startYear; year <= currentYear; year++) {
            cboYear.addItem(Integer.toString(year)); // Add each year to the combo box
        }
    } catch (Exception e) {
        e.printStackTrace(); // Log detailed error
        JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu năm: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    public void fillTableByKeyword(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
        model.setRowCount(0);
        
        List<Object[]> data = thongkeDao.searchDoanhThuByKeyword(keyword);
        for (Object[] row : data) {
            model.addRow(row);
        }
    }
    public void fillTable() {
       DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
        model.setRowCount(0);
        List<Object[]> data = thongkeDao.getDoanhThu();
        for (Object[] row : data) {
            model.addRow(row);
        }
    }
   public void fillTableByYear(int year) {
    DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
    model.setRowCount(0);
    
    List<Object[]> data = thongkeDao.getDoanhThuforYear(year);
    for (Object[] row : data) {
        model.addRow(row);
    }
    
}
    public void fillTableByProductType(String productType) {
        DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
        model.setRowCount(0);
        
        List<Object[]> data = thongkeDao.getDoanhThuByProductType(productType);
        for (Object[] row : data) {
            model.addRow(row);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtTimKiem2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDoanhThu = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cboYear = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cboMaLoai = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 245, 245));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Báo cáo doanh thu");

        txtTimKiem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiem2ActionPerformed(evt);
            }
        });
        txtTimKiem2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiem2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimKiem2KeyTyped(evt);
            }
        });

        tblDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Mã loại", "Số lượng bán", "Doanh thu"
            }
        ));
        jScrollPane1.setViewportView(tblDoanhThu);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Năm");

        cboYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboYearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel4)
                .addGap(63, 71, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Mã loại");

        cboMaLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn mã loại", "Item 2", "Item 3", "Item 4" }));
        cboMaLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaLoaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cboMaLoai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMaLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel2.setText("Tìm mã sản phẩm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(55, 55, 55)
                                .addComponent(txtTimKiem2))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem2ActionPerformed

    private void txtTimKiem2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem2KeyReleased
        // TODO add your handling code here:
          String keyword = txtTimKiem2.getText().trim();
           fillTableByKeyword(keyword);
    }//GEN-LAST:event_txtTimKiem2KeyReleased

    private void txtTimKiem2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem2KeyTyped

    private void cboMaLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaLoaiActionPerformed
        // TODO add your handling code here:
        String selectedItem = (String) cboMaLoai.getSelectedItem();
    if (selectedItem != null && !selectedItem.equals("Chọn loại sản phẩm")) {
        // Extract the product type code from the selected item
        String[] parts = selectedItem.split(" - ");
        String productTypeCode = parts[0];
        fillTableByProductType(productTypeCode);
    }
    }//GEN-LAST:event_cboMaLoaiActionPerformed

    private void cboYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboYearActionPerformed
        // TODO add your handling code here:
       String selectedYearStr = (String) cboYear.getSelectedItem();
    if (selectedYearStr != null) {
        int selectedYear = Integer.parseInt(selectedYearStr);
        fillTableByYear(selectedYear);
    }
    }//GEN-LAST:event_cboYearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboMaLoai;
    private javax.swing.JComboBox<String> cboYear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDoanhThu;
    private javax.swing.JTextField txtTimKiem2;
    // End of variables declaration//GEN-END:variables
}
