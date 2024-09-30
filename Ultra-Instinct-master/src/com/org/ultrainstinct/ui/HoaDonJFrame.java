package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.HoaDonDAO;
import com.org.ultrainstinct.dao.impl.HoaDonDAOImpl;
import com.org.ultrainstinct.helper.DialogHelper;
import com.org.ultrainstinct.main.Main;
import com.org.ultrainstinct.model.HoaDon;
import com.toedter.calendar.JCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class HoaDonJFrame extends javax.swing.JPanel {
    private Main main;
    HoaDonDAOImpl hoadonDao = new HoaDonDAOImpl();
     private Map <String, String> salesPeopleMap;
    public HoaDonJFrame(Main main) {
        this.main = main;
        initComponents();
        fillComboBox();
        filltable();
    }
  void fillComboBox() {
        try {
            salesPeopleMap = hoadonDao.getSalesAndManagerPeople();
            cboNguoiBan.removeAllItems(); // Clear existing items
            cboNguoiBan.addItem("Chọn người bán"); // Add default item

            for (String person : salesPeopleMap.keySet()) {
                cboNguoiBan.addItem(person); // Add each person to the combo box
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu người bán và quản lý!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
   void filltable() {
    DefaultTableModel model = (DefaultTableModel) tblhoaDon.getModel();
    model.setRowCount(0);
    try {
        // Get the keyword from the search text field
        String keyword = txttimKiem.getText();
        
        // Initialize the list for HoaDon records
        List<HoaDon> list = new ArrayList<>();
        
        // Fetch records based on the keyword
        if (keyword != null && !keyword.isEmpty()) {
            list = hoadonDao.selectByKeyword(keyword);
        } else {
            // If no keyword is specified, you can decide to fetch all records or none
            list = hoadonDao.findAll(); // This line can be modified based on your requirement
        }

        // Add rows to the table model
        for (HoaDon hoaDon : list) {
            String trangThai = hoaDon.isTrangThai() ? "Đã hủy" : "Hoàn thành";
            Object[] row = {
                hoaDon.getMaHoaDon(),
                hoaDon.getMaKhachHang(),
                hoaDon.getMaNhanVien(),
                hoaDon.getNgayLap(),
                trangThai
            };
            model.addRow(row);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}   
   public void fillTableByDate() {
    DefaultTableModel model = (DefaultTableModel) tblhoaDon.getModel();
    model.setRowCount(0);
    try {
        // Get the selected dates from the JDateChooser
        Date startDate = jDateChooser1.getDate();
        Date endDate = jDateChooser2.getDate();
        
        // Initialize the list for HoaDon records
        List<HoaDon> list = new ArrayList<>();
        
        // Fetch records based on the selected date range
        if (startDate != null && endDate != null) {
            list = hoadonDao.selectByDateRange(startDate, endDate);
        } else {
            // Handle the case when no date is selected, if needed
            JOptionPane.showMessageDialog(this, "Please select both start and end dates!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Add rows to the table model
        for (HoaDon hoaDon : list) {
            String trangThai = hoaDon.isTrangThai() ? "Đã hủy" : "Hoàn thành";
            Object[] row = {
                hoaDon.getMaHoaDon(),
                hoaDon.getMaKhachHang(),
                hoaDon.getMaNhanVien(),
                hoaDon.getNgayLap(),
                trangThai
            };
            model.addRow(row);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
     void fillTableByNguoiBan() {
        DefaultTableModel model = (DefaultTableModel) tblhoaDon.getModel();
        model.setRowCount(0);
        try {
            // Get the selected salesperson from the combo box
            String selectedPerson = (String) cboNguoiBan.getSelectedItem();

            // Check if a salesperson is selected
            if (selectedPerson != null && !"Chọn người bán".equals(selectedPerson)) {
                // Get the employee ID of the selected salesperson
                String maNhanVien = salesPeopleMap.get(selectedPerson);

                // Fetch records based on the employee ID
                List<HoaDon> list = hoadonDao.selectByNguoiBan(maNhanVien);

                // Add rows to the table model
                for (HoaDon hoaDon : list) {
                    String trangThai = hoaDon.isTrangThai() ? "Đã hủy" : "Hoàn thành";
                    Object[] row = {
                        hoaDon.getMaHoaDon(),
                        hoaDon.getMaKhachHang(),
                        hoaDon.getMaNhanVien(),
                        hoaDon.getNgayLap(),
                        trangThai
                    };
                    model.addRow(row);
                }
            } else {
                // Handle the case when no salesperson is selected
                JOptionPane.showMessageDialog(this, "Please select a salesperson!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    void fillTableByTrangThai() {
    DefaultTableModel model = (DefaultTableModel) tblhoaDon.getModel();
    model.setRowCount(0);
    try {
        // Get the selected status from the checkboxes
        Boolean hoanThanh = chkHoanThanh.isSelected() ? Boolean.TRUE : null;
        Boolean daHuy = chkDaHuy.isSelected() ? Boolean.TRUE : null;

        // Initialize the list for HoaDon records
        List<HoaDon> list = new ArrayList<>();

        // Fetch records based on the selected status
        if (hoanThanh != null || daHuy != null) {
            list = hoadonDao.selectByTrangThai(hoanThanh, daHuy);
        } else {
            // Handle the case when no status is selected
            JOptionPane.showMessageDialog(this, "Please select at least one status!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Add rows to the table model
        for (HoaDon hoaDon : list) {
            String trangThai = hoaDon.isTrangThai() ? "Đã hủy" : "Hoàn thành";
            Object[] row = {
                hoaDon.getMaHoaDon(),
                hoaDon.getMaKhachHang(),
                hoaDon.getMaNhanVien(),
                hoaDon.getNgayLap(),
                trangThai
            };
            model.addRow(row);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    

    






    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblhoaDon = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txttimKiem = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cboNguoiBan = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        chkHoanThanh = new javax.swing.JCheckBox();
        chkDaHuy = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 245, 245));

        tblhoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã khách hàng", "Mã nhân viên", "Ngày lập", "Trạng thái"
            }
        ));
        tblhoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblhoaDonMouseClicked(evt);
            }
        });
        tblhoaDon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblhoaDonKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblhoaDon);

        jButton4.setBackground(new java.awt.Color(51, 204, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Danh sách");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Hóa đơn");

        txttimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttimKiemActionPerformed(evt);
            }
        });
        txttimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttimKiemKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttimKiemKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttimKiemKeyTyped(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Ngày bắt đầu");

        jLabel6.setText("Ngày kết thúc");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Người bán");

        cboNguoiBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn người bán", "Item 2", "Item 3", "Item 4" }));
        cboNguoiBan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNguoiBanItemStateChanged(evt);
            }
        });
        cboNguoiBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNguoiBanActionPerformed(evt);
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
                    .addComponent(cboNguoiBan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboNguoiBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Trạng thái");

        chkHoanThanh.setText("Hoàn thành");
        chkHoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHoanThanhActionPerformed(evt);
            }
        });

        chkDaHuy.setText("Đã hủy");
        chkDaHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDaHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(chkHoanThanh)
                    .addComponent(chkDaHuy))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkHoanThanh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkDaHuy)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel2.setText("Tìm hóa đơn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txttimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(19, 287, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txttimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txttimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttimKiemActionPerformed

    private void txttimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttimKiemKeyReleased
        // TODO add your handling code here:
        filltable();
    }//GEN-LAST:event_txttimKiemKeyReleased

    private void txttimKiemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttimKiemKeyTyped
        // TODO add your handling code here:
        filltable();
    }//GEN-LAST:event_txttimKiemKeyTyped

    private void cboNguoiBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNguoiBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboNguoiBanActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
       // Check if a date is selected and fill table by date
    if (jDateChooser.getDate() != null) {
        fillTableByDate();
       
    }

    // Check if a salesperson is selected and fill table by salesperson
    if (cboNguoiBan.getSelectedItem() != null && !"Chọn người bán".equals(cboNguoiBan.getSelectedItem())) {
        fillTableByNguoiBan();
      
    }

    // Check if any status checkbox is selected and fill table by status
    if (chkHoanThanh.isSelected() || chkDaHuy.isSelected()) {
        fillTableByTrangThai();
       
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txttimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttimKiemKeyPressed
        // TODO add your handling code here:
        filltable();
    }//GEN-LAST:event_txttimKiemKeyPressed

    private void chkDaHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDaHuyActionPerformed
        // TODO add your handling code here:
         if (chkHoanThanh.isSelected() || chkDaHuy.isSelected()) {
        fillTableByTrangThai();
       
    }
    }//GEN-LAST:event_chkDaHuyActionPerformed

    private void chkHoanThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHoanThanhActionPerformed
        // TODO add your handling code here:
         if (chkHoanThanh.isSelected() || chkDaHuy.isSelected()) {
        fillTableByTrangThai();
       
    }
    }//GEN-LAST:event_chkHoanThanhActionPerformed

    private void cboNguoiBanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNguoiBanItemStateChanged
        // TODO add your handling code here:
        
    // Check if a salesperson is selected and fill table by salesperson
    if (cboNguoiBan.getSelectedItem() != null && !"Chọn người bán".equals(cboNguoiBan.getSelectedItem())) {
        fillTableByNguoiBan();
      
    }
    }//GEN-LAST:event_cboNguoiBanItemStateChanged

    private void tblhoaDonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblhoaDonKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tblhoaDonKeyPressed

    private void tblhoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblhoaDonMouseClicked
        int selectedRow = tblhoaDon.getSelectedRow();
    if (selectedRow !=-1) {
        String maHoaDon = tblhoaDon.getValueAt(selectedRow, 0).toString();
        try {
            ChiTietHoaDonJFrame cthd = new ChiTietHoaDonJFrame(main);
            cthd.setProductDetails(maHoaDon);
            main.showForm(cthd);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin hóa đơn: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_tblhoaDonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboNguoiBan;
    private javax.swing.JCheckBox chkDaHuy;
    private javax.swing.JCheckBox chkHoanThanh;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblhoaDon;
    private javax.swing.JTextField txttimKiem;
    // End of variables declaration//GEN-END:variables
}
