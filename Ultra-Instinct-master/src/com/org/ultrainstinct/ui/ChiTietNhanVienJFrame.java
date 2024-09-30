package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.AuthDAO;
import com.org.ultrainstinct.dao.NhanVienDAO;
import com.org.ultrainstinct.dao.impl.AuthDAOImpl;
import com.org.ultrainstinct.dao.impl.NhanVienDAOImpl;
import com.org.ultrainstinct.dto.UserSession;
import com.org.ultrainstinct.main.Main;
import com.org.ultrainstinct.model.NhanVien;
import com.org.ultrainstinct.utils.PasswordUtils;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class ChiTietNhanVienJFrame extends javax.swing.JPanel {
    private Main main;
    private NhanVienDAO dao = new NhanVienDAOImpl();

    public ChiTietNhanVienJFrame(Main main) {
        initComponents();
        this.main = main;
    }
    public void setProductDetails(String maNhanVien) {
    try {
            NhanVien nv = dao.findById(maNhanVien);
            if (nv != null) {
                txtMaNV.setText(nv.getMaNhanVien());
                txtTenNV.setText(nv.getTenNhanVien());
                txtHoNV.setText(nv.getHoNhanVien());
                txtMatKhau.setText(nv.getMatKhau());
                txtSoDT.setText(nv.getSoDienThoai());
                txtEmail.setText(nv.getEmail());

                String chucVu = nv.getChucVu();
                System.out.println("ChucVu: " + chucVu); // Debugging line

                if (chucVu != null) {
                    switch (chucVu) {
                        case "Nhân Viên Bán Hàng":
                            rdoNVBanHang.setSelected(true);
                            break;
                        case "Nhân Viên Kho":
                            rdoNVKho.setSelected(true);
                            break;
                        case "Quản Trị Viên":
                            rdoQTV.setSelected(true);
                            break;
                        default:
                            buttonGroup1.clearSelection();
                            break;
                    }
                } else {
                    buttonGroup1.clearSelection();
                }

                // Check if ThoiGianTao is null before calling toString()
                Date thoiGianTao = nv.getThoiGianTao();
                if (thoiGianTao != null) {
                    txtNgayTao.setText(thoiGianTao.toString());
                } else {
                    txtNgayTao.setText("N/A"); // Or some other default value
                }

                txtNguoiTao.setText(nv.getNguoiTao());
            } else {
                JOptionPane.showMessageDialog(this, "Nhân viên không tồn tại.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị chi tiết nhân viên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearForm() {
    txtMaNV.setText("");
    txtTenNV.setText("");
    txtHoNV.setText("");
    txtMatKhau.setText("");
    txtEmail.setText("");
    txtSoDT.setText(""); // Clear the SoDT field
    buttonGroup1.clearSelection(); // Clear radio button selection
    rdoNVBanHang.setSelected(false);
    rdoNVKho.setSelected(false);
    rdoQTV.setSelected(false);

    // Lấy thông tin người dùng hiện tại từ UserSession
        UserSession userSession = UserSession.getUser();
    if (userSession != null) {
        // Cập nhật trường người tạo
        txtNguoiTao.setText(userSession.getUserName());

        // Cập nhật trường thời gian tạo với giờ hiện tại
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtNgayTao.setText(sdf.format(new Date())); // Lấy ngày hiện tại
    }
}
    void delete() {
    AuthDAO authDAO = new AuthDAOImpl();
    try {
        if (!authDAO.isLogin()) {
            JOptionPane.showMessageDialog(this, "Bạn phải đăng nhập để xóa nhân viên.");
            
        }else{
            UserSession userSession = UserSession.getUser();
                System.out.println(">> check" + userSession.getRole());
                   System.out.println(">> check" + userSession.isManager());
            if (!authDAO.isManager()) {

        }else{
                 int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn thực sự muốn xóa nhân viên này?");
       if (confirmResult == JOptionPane.YES_OPTION) {
           String maNhanVien = txtMaNV.getText();
           try {
              dao.deleteById(maNhanVien);
               this.clearForm();
               JOptionPane.showMessageDialog(this, "Xóa thành công!");
           } catch (Exception e) {
               JOptionPane.showMessageDialog(this, "Xóa thất bại!");
           }
       }
//                UserSession userSession = UserSession.getUser();
//                if(userSession.isManager()){
//                 
//                }
            }
        }

        

    
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi kiểm tra quyền người dùng.");
    }
}



public void insert() {
    AuthDAO authDAO = new AuthDAOImpl();
    try {
        if (!authDAO.isLogin()) {
            JOptionPane.showMessageDialog(this, "Bạn phải đăng nhập để thêm nhân viên.");
            return;
        }

        if (!authDAO.isManager() && !authDAO.isWarehouse()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền thêm nhân viên.");
            return;
        }
       

        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(txtMaNV.getText());
        nv.setTenNhanVien(txtTenNV.getText());
        nv.setHoNhanVien(txtHoNV.getText());
        // Mã hóa mật khẩu trước khi lưu
        String encryptedPassword = PasswordUtils.md5(txtMatKhau.getText());
        nv.setMatKhau(encryptedPassword);
        nv.setSoDienThoai(txtSoDT.getText());
        nv.setEmail(txtEmail.getText());

        String chucVu = "";
        if (rdoNVBanHang.isSelected()) {
            chucVu = "Nhân Viên Bán Hàng";
        } else if (rdoNVKho.isSelected()) {
            chucVu = "Nhân Viên Kho";
        } else if (rdoQTV.isSelected()) {
            chucVu = "Quản Trị Viên";
        }
        nv.setChucVu(chucVu);

        // Convert and set ThoiGianTao
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(txtNgayTao.getText());
            nv.setThoiGianTao(date); // Passing Date object
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ. Vui lòng nhập ngày theo định dạng yyyy-MM-dd.");
            return;
        }

        nv.setNguoiTao(txtNguoiTao.getText());
        
        
        // Insert into the database
        try {
            dao.insert(nv);
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi thêm nhân viên vào cơ sở dữ liệu: " + e.getMessage());
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi kiểm tra quyền người dùng: " + e.getMessage());
    }
}



public void update() {
    AuthDAO authDAO = new AuthDAOImpl();
    try {
        if (!authDAO.isLogin()) {
            JOptionPane.showMessageDialog(this, "Bạn phải đăng nhập để sửa nhân viên.");
            return;
        }

        if (!authDAO.isManager() && !authDAO.isWarehouse()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền sửa nhân viên.");
            return;
        }

        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(txtMaNV.getText());
        nv.setTenNhanVien(txtTenNV.getText());
        nv.setHoNhanVien(txtHoNV.getText());
        // Mã hóa mật khẩu trước khi lưu
        String encryptedPassword = PasswordUtils.md5(txtMatKhau.getText());
        nv.setMatKhau(encryptedPassword);
        nv.setSoDienThoai(txtSoDT.getText());
        nv.setEmail(txtEmail.getText());

        String chucVu = "";
        if (rdoNVBanHang.isSelected()) {
            chucVu = "Nhân Viên Bán Hàng";
        } else if (rdoNVKho.isSelected()) {
            chucVu = "Nhân Viên Kho";
        } else if (rdoQTV.isSelected()) {
            chucVu = "Quản Trị Viên";
        }
        nv.setChucVu(chucVu);

        // Convert and set ThoiGianTao
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(txtNgayTao.getText());
            nv.setThoiGianTao(date); // Passing Date object
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ. Vui lòng nhập ngày theo định dạng yyyy-MM-dd.");
            return;
        }

        nv.setNguoiTao(txtNguoiTao.getText());
        
        // Ensure trangThaiXoa is set
        nv.setTrangThaiXoa(false);

        // Update in the database
        try {
            dao.update(nv);
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi sửa nhân viên trong cơ sở dữ liệu: " + e.getMessage());
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi kiểm tra quyền người dùng: " + e.getMessage());
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        txtHoNV = new javax.swing.JTextField();
        txtSoDT = new javax.swing.JTextField();
        rdoNVKho = new javax.swing.JRadioButton();
        rdoNVBanHang = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        rdoQTV = new javax.swing.JRadioButton();
        btnLuu = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        txtMatKhau = new javax.swing.JPasswordField();
        txtEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnDanhSach = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));

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

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/org/ultrainstinct/icon/search.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel1.setText("Mã nhân viên");

        jLabel2.setText("Tên nhân viên");

        jLabel3.setText("Họ nhân viên");

        jLabel10.setText("Số điện thoại");

        jLabel5.setText("Email");

        jLabel6.setText("Vai trò");

        buttonGroup1.add(rdoNVKho);
        rdoNVKho.setText("Nhân viên kho");

        buttonGroup1.add(rdoNVBanHang);
        rdoNVBanHang.setText("Nhân viên bán hàng");

        jLabel8.setText("Mật khẩu");

        buttonGroup1.add(rdoQTV);
        rdoQTV.setText("Quản trị viên");

        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/org/ultrainstinct/icon/add.png"))); // NOI18N
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/org/ultrainstinct/icon/pencil.png"))); // NOI18N
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/org/ultrainstinct/icon/trash.png"))); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/org/ultrainstinct/icon/add-document.png"))); // NOI18N
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        txtMatKhau.setText("jPasswordField1");

        jLabel7.setText("Ngày tạo");

        jLabel9.setText("Người tạo");

        txtNguoiTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNguoiTaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoNVKho, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(299, 299, 299)
                                .addComponent(rdoNVBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdoQTV, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(436, 436, 436))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(txtSoDT, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                            .addGap(91, 91, 91))
                                        .addComponent(txtHoNV)))
                                .addGap(101, 101, 101)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(12, 12, 12)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtEmail)
                                    .addComponent(txtMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                                    .addComponent(txtNgayTao)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHoNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel5)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rdoNVKho)
                    .addComponent(rdoNVBanHang)
                    .addComponent(rdoQTV))
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(163, 163, 163))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Chi tiết nhân viên");

        btnDanhSach.setBackground(new java.awt.Color(51, 204, 0));
        btnDanhSach.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDanhSach.setForeground(new java.awt.Color(255, 255, 255));
        btnDanhSach.setText("Danh sách");
        btnDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDanhSachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDanhSach))
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem)
                    .addComponent(btnTimKiem)
                    .addComponent(btnDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhSachActionPerformed
        // TODO add your handling code here:
        main.showForm(new NhanVienJFrame(main));
    }//GEN-LAST:event_btnDanhSachActionPerformed

    private void txtNguoiTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNguoiTaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNguoiTaoActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        AuthDAO authDAO = new AuthDAOImpl();
         if (rdoQTV.isSelected()==authDAO.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa quản trị viên khác.");
        }else{
             delete();
         }
        
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        AuthDAO authDAO = new AuthDAOImpl();
         if (rdoQTV.isSelected()==authDAO.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền sửa quản trị viên khác.");
        }else{
             update();
         }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        
        insert();
    }//GEN-LAST:event_btnLuuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDanhSach;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton rdoNVBanHang;
    private javax.swing.JRadioButton rdoNVKho;
    private javax.swing.JRadioButton rdoQTV;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoNV;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtSoDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
