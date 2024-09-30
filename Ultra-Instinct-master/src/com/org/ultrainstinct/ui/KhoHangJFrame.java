package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.impl.NhapKhoDAOImpl;
import com.org.ultrainstinct.dao.impl.PhieuNhapChiTietDAOImpl;
import com.org.ultrainstinct.main.Main;
import com.org.ultrainstinct.model.PhieuNhapChiTiet;
import java.sql.SQLException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JOptionPane;


import javax.swing.table.DefaultTableModel;

public class KhoHangJFrame extends javax.swing.JPanel {

    NhapKhoDAOImpl nkDAO = new NhapKhoDAOImpl();
    PhieuNhapChiTietDAOImpl pnctDAO = new PhieuNhapChiTietDAOImpl();
    private Main main;
    void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
        model.setRowCount(0);
        try {
            List<PhieuNhapChiTiet> list = pnctDAO.findAll();
            for (PhieuNhapChiTiet pnct : list) {
                model.addRow(new Object[]{
                    pnct.getMaPNCT(),
                    pnct.getMaSanPham(),
                    pnct.getMaNhapKho(),
                    pnct.getMaNhaCungCap(),
                    pnct.getGiaNhap(),
                    pnct.getSoLuong()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    void comboboxNCC() {
        try {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            List<PhieuNhapChiTiet> list = pnctDAO.findAll();
            for (PhieuNhapChiTiet pnct : list) {
                model.addElement(pnct.getMaNhaCungCap());
            }
            cboNhaCungCap.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    void findComboboxNCC(String maNhaCungCap) {
        DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
        model.setRowCount(0);
        try {
            List<PhieuNhapChiTiet> list = pnctDAO.findAll();
            for (PhieuNhapChiTiet pnct : list) {
                if (pnct.getMaNhaCungCap().equals(maNhaCungCap)) {
                    model.addRow(new Object[]{
                        pnct.getMaPNCT(),
                        pnct.getMaSanPham(),
                        pnct.getMaNhapKho(),
                        pnct.getMaNhaCungCap(),
                        pnct.getGiaNhap(),
                        pnct.getSoLuong()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    public KhoHangJFrame(Main main) {
        initComponents();
        init();
        this.main = main;
    }
    
    void init(){
        fillToTable();
        comboboxNCC();

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDonHang = new javax.swing.JTable();
        btnTimKiem = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cboNhaCungCap = new javax.swing.JComboBox<>();
        btnFillTable = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Quản lý kho");

        tblDonHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã phiếu nhập", "Mã sản phẩm", "Mã nhập kho", "Mã nhà cung cấp", "Giá nhập", "Số lượng"
            }
        ));
        tblDonHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDonHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDonHang);

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/org/ultrainstinct/icon/search.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyTyped(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Nhà cung cấp");

        cboNhaCungCap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn nhà cung cấp", "Item 2", "Item 3", "Item 4" }));
        cboNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNhaCungCapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cboNhaCungCap, 0, 161, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnFillTable.setBackground(new java.awt.Color(0, 204, 0));
        btnFillTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnFillTable.setForeground(new java.awt.Color(255, 255, 255));
        btnFillTable.setText("Danh Sách");
        btnFillTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFillTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFillTable, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 220, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnFillTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
            try {
            // TODO add your handling code here:
            List<PhieuNhapChiTiet> list = nkDAO.getKeyWord(txtTimKiem.getText());
            DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
                model.setRowCount(0);
            for(PhieuNhapChiTiet pnct : list){
                model.addRow(new Object[]{
                    pnct.getMaPNCT(), pnct.getMaSanPham(), pnct.getMaNhapKho(), pnct.getMaNhaCungCap(), pnct.getGiaNhap(), pnct.getSoLuong()
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhoHangJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTimKiemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemKeyTyped

    private void cboNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNhaCungCapActionPerformed
        // TODO add your handling code here:
        String maNCC = (String) cboNhaCungCap.getSelectedItem();
        findComboboxNCC(maNCC);
    }//GEN-LAST:event_cboNhaCungCapActionPerformed

    private void tblDonHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDonHangMouseClicked
        // TODO add your handling code here:
        System.out.println("Click");
        DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
         // Xác định hàng và cột của ô được click
        int row = tblDonHang.rowAtPoint(evt.getPoint());
        if (row >= 0) {
         // Lấy số lượng cột trong bảng
        int columnCount = model.getColumnCount();
        
        // Tạo một danh sách để lưu các giá trị của hàng được chọn
        Object[] rowData = new Object[columnCount];
        PhieuNhapChiTiet pnct = new PhieuNhapChiTiet();
        // Lặp qua tất cả các cột và lấy giá trị
        for (int column = 0; column < columnCount; column++) {
            rowData[column] = model.getValueAt(row, column);
        }
        // Gán giá trị vào thuộc tính của PhieuNhapChiTiet
        try {
            pnct.setMaPNCT((String) rowData[0]);
            pnct.setMaSanPham((String) rowData[1]);
            pnct.setMaNhapKho((String) rowData[2]);
            pnct.setMaNhaCungCap((String) rowData[3]);
            pnct.setGiaNhap(Float.parseFloat(rowData[4].toString()));
            pnct.setSoLuong(Integer.parseInt(rowData[5].toString())); // Chỉnh sửa chỉ số cột phù hợp
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có lỗi trong chuyển đổi số
        }
            System.out.println(">> phiếu nhập chi tiết: " + pnct);


        ChiTietPhieuNhapJFrame detailFrame = new ChiTietPhieuNhapJFrame(main);
        detailFrame.fillToTableByID(pnct);
        main.showForm(detailFrame);
          }
        

    }//GEN-LAST:event_tblDonHangMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnFillTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFillTableActionPerformed
        // TODO add your handling code here:
        fillToTable();
    }//GEN-LAST:event_btnFillTableActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFillTable;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboNhaCungCap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDonHang;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
