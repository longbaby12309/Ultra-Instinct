package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.UserDao;
import com.org.ultrainstinct.dao.impl.UserDAOImpl;
import com.org.ultrainstinct.dto.UserSession;

import javax.swing.JOptionPane;

public class DoiMatKhauJFrame extends javax.swing.JPanel {

    private UserDao userDAO = new UserDAOImpl();

    public DoiMatKhauJFrame() {
        initComponents();
        hideSecurityGuidelines();
    }

    private void doiMatKhau() {
        String matKhauCu = new String(txtMKCu.getPassword());
        String matKhauMoi = new String(txtMKMoi.getPassword());
        String xacNhanMatKhau = new String(txtXacNhanMK.getPassword());
        String maNhanVien = UserSession.getUser().getUserName();

        // Kiểm tra mật khẩu cũ
        if (!userDAO.checkCurrentPassword(maNhanVien, matKhauCu)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng!");
            return;
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu
        if (!matKhauMoi.equals(xacNhanMatKhau)) {
            JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu không khớp!");
            return;
        }

        if (!kiemTraMatKhauMoi(matKhauMoi)) {
            showSecurityGuidelines();
            JOptionPane.showMessageDialog(this, "Mật khẩu mới không đáp ứng yêu cầu bảo mật!");
            return;
        }

        // Cập nhật mật khẩu
        if (userDAO.updatePassword(maNhanVien, matKhauMoi)) {
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
            hideSecurityGuidelines();
        } else {
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại!");
        }
    }

    private boolean kiemTraMatKhauMoi(String matKhauMoi) {
        if (matKhauMoi.length() < 12) { // Adjusted to 12 characters as per the guideline
            return false;
        }
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        for (char c : matKhauMoi.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    private void showSecurityGuidelines() {
        jLabel4.setVisible(true);
        jLabel5.setVisible(true);
        jLabel6.setVisible(true);
        jLabel7.setVisible(true);
        jLabel8.setVisible(true);
        jLabel10.setVisible(true);
    }

    private void hideSecurityGuidelines() {
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(false);
        jLabel8.setVisible(false);
        jLabel10.setVisible(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblMKCu = new javax.swing.JLabel();
        txtMKCu = new javax.swing.JPasswordField();
        lblMKMoi = new javax.swing.JLabel();
        txtMKMoi = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblXacNhanMK = new javax.swing.JLabel();
        txtXacNhanMK = new javax.swing.JPasswordField();
        btnDoiMK = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Thay đổi mật khẩu");

        lblMKCu.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblMKCu.setText("Mật khẩu cũ");

        lblMKMoi.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblMKMoi.setText("Mật khẩu mới");

        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Tạo mật khẩu an toàn với các kí tự sau:");

        jLabel5.setText("Tối thiểu 12 kí tự");

        jLabel6.setText("Ít nhật 1 kí tự viết hoa");

        jLabel7.setText("Ít nhất 1 kí tự viết thường");

        jLabel8.setText("Ít nhất 1 kí tự đặc biệt");

        jLabel10.setText("Ít nhất 1 chữ số");

        lblXacNhanMK.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblXacNhanMK.setText("Xác nhận mật khẩu mới");

        btnDoiMK.setBackground(new java.awt.Color(0, 204, 51));
        btnDoiMK.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnDoiMK.setForeground(new java.awt.Color(255, 255, 255));
        btnDoiMK.setText("Đổi mật khẩu");
        btnDoiMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMKCu)
                    .addComponent(lblMKMoi)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtMKCu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(txtMKMoi, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(lblXacNhanMK)
                    .addComponent(btnDoiMK, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtXacNhanMK, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jLabel5))
                        .addComponent(jLabel4)))
                .addGap(651, 651, 651))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(lblMKCu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMKCu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMKMoi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMKMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(7, 7, 7)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(lblXacNhanMK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtXacNhanMK, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDoiMK, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(204, 204, 204))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoiMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMKActionPerformed
        // TODO add your handling code here:
        doiMatKhau();
    }//GEN-LAST:event_btnDoiMKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoiMK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblMKCu;
    private javax.swing.JLabel lblMKMoi;
    private javax.swing.JLabel lblXacNhanMK;
    private javax.swing.JPasswordField txtMKCu;
    private javax.swing.JPasswordField txtMKMoi;
    private javax.swing.JPasswordField txtXacNhanMK;
    // End of variables declaration//GEN-END:variables
}
