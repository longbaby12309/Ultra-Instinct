package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.impl.HoaDonDAOImpl;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BaoCaoDonHangJFrame extends javax.swing.JPanel {
  HoaDonDAOImpl hoaDonDao= new HoaDonDAOImpl();
    public BaoCaoDonHangJFrame() {
        initComponents();
//        fillYearComboBox() ;
//        fillTable();
    }
//
//    @SuppressWarnings("unchecked")
//    void fillYearComboBox() {
//        try {
//            List<Integer> years = hoaDonDao.getYearinHoaDon(); // Get all distinct years from invoices
//
//            cboYear.removeAllItems(); // Clear existing items
//
//            for (int year : years) {
//                cboYear.addItem(null);
//                cboYear.addItem(Integer.toString(year)); // Add each year to the combo box
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Log detailed error
//            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu năm: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    public void fillTableByYear(int year) {
//        DefaultTableModel model = (DefaultTableModel) tblThongKeDonHang.getModel();
//        model.setRowCount(0);
//        
//        List<Object[]> data = hoaDonDao.getOrderStatisticsForYear(year);
//        for (Object[] row : data) {
//            model.addRow(row);
//        }
//    }
//      public void fillTable() {
//       DefaultTableModel model = (DefaultTableModel) tblThongKeDonHang.getModel();
//        model.setRowCount(0);
//        List<Object[]> data = hoaDonDao.getThongKeKhachHang();
//        for (Object[] row : data) {
//            model.addRow(row);
//        }
//    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cboYear = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongKeDonHang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 245, 245));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Báo cáo đơn hàng");

        cboYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn năm", "Item 2", "Item 3", "Item 4" }));
        cboYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboYearActionPerformed(evt);
            }
        });

        tblThongKeDonHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Năm", "Số đơn thành công", "Số đơn hủy", "Tổng bán"
            }
        ));
        jScrollPane2.setViewportView(tblThongKeDonHang);

        jLabel2.setText("Chọn năm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1060, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboYearActionPerformed
        // TODO add your handling code here:
             String selectedYearStr = (String) cboYear.getSelectedItem();
    if (selectedYearStr != null) {
        int selectedYear = Integer.parseInt(selectedYearStr);
//        fillTableByYear(selectedYear);
    }
    }//GEN-LAST:event_cboYearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboYear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblThongKeDonHang;
    // End of variables declaration//GEN-END:variables
}
