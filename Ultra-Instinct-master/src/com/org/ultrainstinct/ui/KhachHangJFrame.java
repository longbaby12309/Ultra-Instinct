package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.KhachHangDAO;
import com.org.ultrainstinct.dao.impl.KhachHangDAOImpl;
import com.org.ultrainstinct.main.Main;
import com.org.ultrainstinct.model.KhachHang;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class KhachHangJFrame extends javax.swing.JPanel {

    // private KhachHangDAO dao = new KhachHangDAOImpl() {
    private  KhachHangDAOImpl dao = new KhachHangDAOImpl() {};

    int index = 0;
    private Main main;

    public KhachHangJFrame(Main main) throws SQLException {
        this.main = main;
        initComponents();
        init();
        filltable();
    }

    public void init() {
        if (tblDanhSachKH.getRowCount() > 0) {
            tblDanhSachKH.setRowSelectionInterval(0, 0);

            tblDanhSachKH.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        tblDanhSachKHMouseClicked(evt);
                    }
                }
            });
        }

        // Thêm KeyListener để tìm kiếm tự động
        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filltable();
            }
        });
    }

    void resetFillToTable() throws SQLException {
        filltable();
        List<KhachHang> list = dao.listAll();
        DefaultTableModel model = (DefaultTableModel) tblDanhSachKH.getModel();
        model.setRowCount(0);

        String keyword = txtTimKiem.getText().trim();

        try {
            if (keyword.isEmpty()) {
                list = dao.listAll();
                System.out.println(list);
            } else {
                System.out.println("Hello");
//                list = dao.selectByKeyword(keyword);
            }

            for (KhachHang kh : list) {
                double tongBan = dao.tongBanHang(kh.getMaKhachHang());
                if (!kh.isTrangThaiXoa()) {
                    Object[] row = {
                        kh.getMaKhachHang(),
                        kh.getTenKH(),
                        kh.getSoDienThoai(),
                        kh.getEmail(),
                        kh.getNgayDangKy(),
                        kh.getNguoiTao(),
                        tongBan
                    };
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi không xác định: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }

  void filltable() {
    DefaultTableModel model = (DefaultTableModel) tblDanhSachKH.getModel();
    model.setRowCount(0);

    String keyword = txtTimKiem.getText().trim();

    try {
        List<KhachHang> list;
        if (keyword.isEmpty()) {
            System.out.println("Hello Error1: ");
            list = dao.listAll();
            System.out.println(list);
        } else {
            list = dao.listAll();
            System.out.println("Hello");
            //list = dao.selectByKeyword(keyword);
        }

        for (KhachHang kh : list) {
            double tongBan = dao.tongBanHang(kh.getMaKhachHang());
            if (!kh.isTrangThaiXoa()) {
                Object[] row = {
                    kh.getMaKhachHang(),
                    kh.getTenKH(),
                    kh.getSoDienThoai(),
                    kh.getEmail(),
                    kh.getNgayDangKy(),
                    kh.getNguoiTao(),
                    tongBan
                };
                model.addRow(row);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi không xác định: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        if (index < tblDanhSachKH.getRowCount() - 1) {
            index++;
            updateTableSelection();
        }
    }

    void last() {
        index = tblDanhSachKH.getRowCount() - 1;
        updateTableSelection();
    }

    void updateTableSelection() {
        if (tblDanhSachKH.getRowCount() > 0) {
            tblDanhSachKH.setRowSelectionInterval(index, index);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDanhSach = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachKH = new javax.swing.JTable();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));

        btnDanhSach.setBackground(new java.awt.Color(51, 204, 0));
        btnDanhSach.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDanhSach.setForeground(new java.awt.Color(255, 255, 255));
        btnDanhSach.setText("Danh sách");
        btnDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDanhSachActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Khách hàng");

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

        tblDanhSachKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Email", "Ngày đăng ký", "Mã nhân viên", "Tổng bán"
            }
        ));
        tblDanhSachKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachKHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachKH);

        btnPrev.setBackground(new java.awt.Color(0, 204, 51));
        btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(0, 204, 51));
        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/org/ultrainstinct/icon/add.png"))); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(0, 204, 51));
        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnFirst.setBackground(new java.awt.Color(0, 204, 51));
        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(84, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(btnFirst)
                .addGap(52, 52, 52)
                .addComponent(btnPrev)
                .addGap(58, 58, 58)
                .addComponent(btnNext)
                .addGap(59, 59, 59)
                .addComponent(btnLast)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTimKiemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemKeyTyped

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed

        ChiTietKhachHangJFrame ctkh = new ChiTietKhachHangJFrame(main);
        ctkh.clearForm(); // Gọi phương thức clearForm để xóa dữ liệu cũ
        main.showForm(ctkh); // Hiển thị form chi tiết khách hàng
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhSachActionPerformed
        try {
            resetFillToTable();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDanhSachActionPerformed

    private void tblDanhSachKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachKHMouseClicked
        int selectedRow = tblDanhSachKH.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String maKhachHang = tblDanhSachKH.getValueAt(selectedRow, 0).toString(); // Lấy mã khách hàng từ cột đầu tiên
                System.out.println("MKH:" + maKhachHang);
                ChiTietKhachHangJFrame ctkh = new ChiTietKhachHangJFrame(main);
                ctkh.setForm(maKhachHang); // Truyền mã khách hàng sang form chi tiết
                main.showForm(ctkh); // Hiển thị form chi tiết khách hàng
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi mở chi tiết khách hàng.");
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!");
        }
    }//GEN-LAST:event_tblDanhSachKHMouseClicked

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDanhSach;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDanhSachKH;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
