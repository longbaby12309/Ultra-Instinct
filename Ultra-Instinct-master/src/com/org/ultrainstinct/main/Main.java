package com.org.ultrainstinct.main;

import com.org.ultrainstinct.ui.BaoCaoDoanhThuJFrame;
import com.org.ultrainstinct.ui.HoaDonJFrame;
import com.org.ultrainstinct.ui.KhachHangJFrame;
import com.org.ultrainstinct.ui.NhanVienJFrame;
import com.org.ultrainstinct.ui.SanPhamJFrame;
import com.org.ultrainstinct.ui.ThongTinNguoiDungJFrame;
import com.org.ultrainstinct.ui.BanHangJFrame;
import java.awt.Component;
import com.org.ultraInstinct.menu.MenuEvent;
import com.org.ultrainstinct.dao.UserDao;
import com.org.ultrainstinct.dao.impl.UserDAOImpl;
import com.org.ultrainstinct.dto.UserSession;
import com.org.ultrainstinct.ui.ChartBarAnimationDH_KHJFrame;
import com.org.ultrainstinct.ui.ChartBarAnimationJFrame;
import com.org.ultrainstinct.ui.ChiTietSanPhamJFrame;
import com.org.ultrainstinct.ui.DieuKhoanVaBaoMatJFrame;
import com.org.ultrainstinct.ui.DoiMatKhauJFrame;
import com.org.ultrainstinct.ui.HoiDapJFrame;
import com.org.ultrainstinct.ui.HomeForm;
import com.org.ultrainstinct.ui.KhoHangJFrame;
import com.org.ultrainstinct.ui.ThongBaoJFrame;
import com.org.ultrainstinct.utils.PasswordUtils;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author RAVEN
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
    initComponents();
     // Get the screen dimensions
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        
        // Calculate the new window dimensions
        int width = screenSize.width - 100;
        int height = screenSize.height - 100;

        // Set the size of the window
        setSize(width, height);
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        showForm(new HomeForm());
        menu1.setEvent(new MenuEvent() {
            @Override
            public void selected(int index, int subIndex) {
                switch (index) {
                    case 0:
                        showForm(new BanHangJFrame(Main.this));
                        break;
                    case 1:
                        showForm(new HoaDonJFrame(Main.this));
                        break;
                    case 2:
                        if (subIndex == 1) {
                            showForm(new SanPhamJFrame(Main.this));
                        } else if (subIndex == 2) {
                            showForm(new KhoHangJFrame(Main.this));
                        }
                        break;
                    case 3:
                        if (subIndex == 1) {
                            showForm(new BaoCaoDoanhThuJFrame());
                        } else if (subIndex == 2) {
                            showForm(new ChartBarAnimationJFrame());
                        } else if (subIndex == 3) {
                            showForm(new ChartBarAnimationDH_KHJFrame());
                        }
                        break;
                    case 4:
                        try {
                            showForm(new KhachHangJFrame(Main.this));
                        } catch (SQLException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case 5:
                        showForm(new NhanVienJFrame(Main.this));
                        break;
                    case 6:
                        showForm(new HoiDapJFrame());
                        break;
                    case 7:
                        if (subIndex == 1) {
                            if (confirmPass()) {  // Xác nhận mật khẩu trước khi mở form ThongTinNguoiDungJFrame
                                showForm(new ThongTinNguoiDungJFrame());
                            }
                        } else if (subIndex == 2) {
                            showForm(new DoiMatKhauJFrame());
                        } else if (subIndex == 3) {
                            showForm(new ThongBaoJFrame());
                        } else if (subIndex == 4) {
                            showForm(new DieuKhoanVaBaoMatJFrame());
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    private boolean confirmPass() {
        String maNhanVien = UserSession.getUser().getUserName();
        String matKhauCu = JOptionPane.showInputDialog(this, "Vui lòng nhập mật khẩu của bạn:", "Xác nhận mật khẩu", JOptionPane.PLAIN_MESSAGE);
        UserDao userDAO = new UserDAOImpl();
        if (matKhauCu == null || matKhauCu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống.");
            return false;
        }

        String hashedPassword = PasswordUtils.md5(matKhauCu);  // Mã hóa mật khẩu nhập vào bằng MD5

        if (userDAO.checkCurrentPassword(maNhanVien, hashedPassword)) {
            return true;  // Nếu mật khẩu đúng, cho phép mở form
        } else {
            JOptionPane.showMessageDialog(this, "Mật khẩu không đúng. Vui lòng thử lại.");
            return false;  // Nếu mật khẩu sai, ngăn không cho mở form
        }
    }


    public void showForm(Component com) {
        body.removeAll();
        body.add(com);
        body.repaint();
        body.revalidate();
    }

    public void openChiTietSanPham() {
        ChiTietSanPhamJFrame ctsp = new ChiTietSanPhamJFrame(Main.this);
        ctsp.setVisible(true);
    }
    
    public void openChiTietHoaDon() {
        ChiTietSanPhamJFrame cthd = new ChiTietSanPhamJFrame(Main.this);
        cthd.setVisible(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scrollPaneWin111 = new com.org.ultrainstinct.menu.scroll.win11.ScrollPaneWin11();
        menu1 = new com.org.ultraInstinct.menu.Menu();
        body = new javax.swing.JPanel();
        header1 = new com.org.ultrainstinct.ui.HeaderJFrame();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(163, 163, 163)));

        scrollPaneWin111.setBorder(null);

        menu1.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneWin111.setViewportView(menu1);

        body.setBackground(new java.awt.Color(245, 245, 245));
        body.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(scrollPaneWin111, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(header1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneWin111, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private com.org.ultrainstinct.ui.HeaderJFrame header1;
    private javax.swing.JPanel jPanel1;
    private com.org.ultraInstinct.menu.Menu menu1;
    private com.org.ultrainstinct.menu.scroll.win11.ScrollPaneWin11 scrollPaneWin111;
    // End of variables declaration//GEN-END:variables
}
