package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.NhanVienDAO;
import com.org.ultrainstinct.dao.impl.NhanVienDAOImpl;
import com.org.ultrainstinct.main.Main;
import com.org.ultrainstinct.model.NhanVien;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class NhanVienJFrame extends javax.swing.JPanel {
    private NhanVienDAO dao = new NhanVienDAOImpl();
    int index = 0;
    private Main main;
    List<NhanVien> list = new ArrayList<>();

    public NhanVienJFrame(Main main) {
        initComponents();
        this.main = main;
        init();
    }
    public void init() {
        filltable();
        if (tblDanhSachNV.getRowCount() > 0) {
            tblDanhSachNV.setRowSelectionInterval(0, 0);
        }
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachNV.getModel();
        model.setRowCount(0);
        String keyword = txtTimKiem2.getText().trim();
        try {
            list = dao.findByKeyword(keyword); // Fetch the list from the DAO
            for (NhanVien nv : list) {
                Object[] row = {
                    nv.getNhanVienNo(),
                    nv.getMaNhanVien(),
                    nv.getTenNhanVien(),
                    nv.getHoNhanVien(),
                    nv.getMatKhau(),
                    nv.getSoDienThoai(),
                    nv.getEmail(),
                    nv.getChucVu(),
                    nv.getNguoiTao(),
                    nv.getThoiGianTao()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi tải dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    void first() {
        index = 0;
        updateTableSelection();
    }

    void prev() {
        if (index > 0) {
            index--;
            updateTableSelection();
        }
    }

    void next() {
        if (index < tblDanhSachNV.getRowCount() - 1) {
            index++;
            updateTableSelection();
        }
    }

    void last() {
        index = tblDanhSachNV.getRowCount() - 1;
        updateTableSelection();
    }

    void updateTableSelection() {
        if (tblDanhSachNV.getRowCount() > 0) {
            tblDanhSachNV.setRowSelectionInterval(index, index);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTimKiem2 = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnLast = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachNV = new javax.swing.JTable();
        btnDanhSach = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrec = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));

        txtTimKiem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiem2ActionPerformed(evt);
            }
        });
        txtTimKiem2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiem2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiem2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimKiem2KeyTyped(evt);
            }
        });

        btnFirst.setBackground(new java.awt.Color(0, 204, 0));
        btnFirst.setText("<<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Nhân viên");

        btnLast.setBackground(new java.awt.Color(0, 204, 0));
        btnLast.setText(">>");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/org/ultrainstinct/icon/search.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        tblDanhSachNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã nhân viên", "Tên nhân viên", "Họ nhân viên", "Mật khẩu", "Số điện thoại", "Email", "Vai trò", "Người tạo", "Ngày tạo"
            }
        ));
        tblDanhSachNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachNVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachNV);

        btnDanhSach.setBackground(new java.awt.Color(51, 204, 0));
        btnDanhSach.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDanhSach.setForeground(new java.awt.Color(255, 255, 255));
        btnDanhSach.setText("Danh sách");
        btnDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDanhSachActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(0, 204, 0));
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrec.setBackground(new java.awt.Color(0, 204, 0));
        btnPrec.setText("<");
        btnPrec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrecActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(443, 443, 443)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrec, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 443, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTimKiem2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(btnDanhSach)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDanhSach)
                    .addComponent(btnTimKiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLast)
                    .addComponent(btnNext)
                    .addComponent(btnPrec)
                    .addComponent(btnFirst))
                .addContainerGap(35, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem2ActionPerformed

    private void txtTimKiem2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem2KeyReleased
        // TODO add your handling code here:
        filltable();
    }//GEN-LAST:event_txtTimKiem2KeyReleased

    private void txtTimKiem2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem2KeyTyped
        // TODO add your handling code here:
        filltable();
    }//GEN-LAST:event_txtTimKiem2KeyTyped

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblDanhSachNV.getSelectedRow();
        if (selectedRow >= 0) {
            String maSanPham = tblDanhSachNV.getValueAt(selectedRow, 1).toString();
            ChiTietNhanVienJFrame ctnv = new ChiTietNhanVienJFrame(main);
            ctnv.setProductDetails(maSanPham);
            main.showForm(ctnv);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhSachActionPerformed
        // TODO add your handling code here:
        filltable();
    }//GEN-LAST:event_btnDanhSachActionPerformed

    private void txtTimKiem2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem2KeyPressed

    private void tblDanhSachNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachNVMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblDanhSachNV.getSelectedRow();
        if (selectedRow >= 0) {
            String maSanPham = tblDanhSachNV.getValueAt(selectedRow, 1).toString();
            ChiTietNhanVienJFrame ctnv = new  ChiTietNhanVienJFrame(main);
            ctnv.setProductDetails(maSanPham);
            main.showForm(ctnv);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_tblDanhSachNVMouseClicked

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrecActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrecActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDanhSach;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrec;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDanhSachNV;
    private javax.swing.JTextField txtTimKiem2;
    // End of variables declaration//GEN-END:variables
}
