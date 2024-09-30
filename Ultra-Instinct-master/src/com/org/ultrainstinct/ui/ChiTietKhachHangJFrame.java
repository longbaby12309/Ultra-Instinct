package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.KhachHangDAO;
import com.org.ultrainstinct.dao.impl.KhachHangDAOImpl;
import com.org.ultrainstinct.dto.UserSession;
import com.org.ultrainstinct.helper.DateHelper;
import com.org.ultrainstinct.main.Main;
import com.org.ultrainstinct.model.DiaChi;
import com.org.ultrainstinct.model.KhachHang;
import com.org.ultrainstinct.utils.Constant;
import com.org.ultrainstinct.utils.StringUtil;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.org.ultrainstinct.dao.AbstractCrudDao.connection;

public class ChiTietKhachHangJFrame extends javax.swing.JPanel {

    int index = 0;
    private Main main;
    public  KhachHangDAOImpl dao = new KhachHangDAOImpl() {} ;
    private List<KhachHang> khachHangList;

    public ChiTietKhachHangJFrame(Main main) {
        initComponents();
        this.main = main;
//        loadData();
    }

    private void loadData() {
        try {
            khachHangList = dao.listAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

     public void setForm(String maKhachHang) {
        try {
            KhachHang kh = dao.findByMaKhachHang(maKhachHang);
            if (kh != null) {
                System.out.println("MKH: " + kh.getMaKhachHang());
                txtMaKH.setText(kh.getMaKhachHang());
                txtTenKH.setText(kh.getTenKH());
                txtSoDT.setText(kh.getSoDienThoai());
                txtEmail.setText(kh.getEmail());
                txtGhiChu.setText(kh.getGhiChu());
                txtNgaySinh.setText(DateHelper.toString(kh.getNgaySinh(), "MM/dd/yyyy"));
                if (kh.isGioiTinh()) {
                    rdoNam.setSelected(true);
                    rdoNu.setSelected(false);
                } else {
                    rdoNu.setSelected(true);
                    rdoNam.setSelected(false);
                }
                txtNguoiTao.setText(kh.getNguoiTao());

                Date ngayDangKy = kh.getNgayDangKy();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String ngayDangKyText = sdf.format(ngayDangKy);
                txtNgayTao.setText(ngayDangKyText);

                // Sử dụng phương thức getDiaChi để lấy địa chỉ
                String diaChiResult = dao.getDiaChi(maKhachHang);
                if (diaChiResult != null && !diaChiResult.isEmpty()) {
                    String[] parts = diaChiResult.split("\\|", 3);
                    String diaChiMacDinh = parts[0];
                    boolean isMacDinh = Boolean.parseBoolean(parts[1]);
                    String diaChiString = parts[2];

                    txtDiaChi.setText(diaChiString);
                    rdoMacDinh.setSelected(isMacDinh);
                } else {
                    txtDiaChi.setText("Không có địa chỉ");
                    rdoMacDinh.setSelected(false);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thiết lập thông tin khách hàng.");
            e.printStackTrace();
        }
    }

    private DiaChi createDiaChi(String maKhachHang) {

        return DiaChi.builder()
                .maDiaChi(StringUtil.genCode(Constant.CODE_DIA_CHI))
                .maKhachHang(maKhachHang)
                .diaChi(txtDiaChi.getText()) // Assuming address is at index 0
                .diaChiMacDinh(rdoMacDinh.isSelected()? "Có" : "Không") // Assuming default address indicator is at index 1
                .trangThaiXoa(false) // Assuming delete status is at index 2
                .build();
    }

    public void clearForm() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtSoDT.setText("");
        txtEmail.setText("");
        txtNgaySinh.setText("");
        txtNguoiTao.setText("");
        txtNgayTao.setText("");
        txtDiaChi.setText("");
        txtGhiChu.setText("");
        rdoNam.setSelected(false);
        rdoNu.setSelected(false);
        rdoMacDinh.setSelected(false);
    }

    public void save() {
        try {
            UserSession user = UserSession.getUser();
            String nguoiTao = user.getUserName();
            KhachHang kh = new KhachHang();
            kh.setMaKhachHang(txtMaKH.getText().trim());
            kh.setTenKH(txtTenKH.getText().trim());
            kh.setSoDienThoai(txtSoDT.getText().trim());
            kh.setEmail(txtEmail.getText().trim());
            kh.setNgaySinh(DateHelper.toDate(txtNgaySinh.getText().trim(), "MM/dd/yyyy"));
            kh.setGioiTinh(rdoNam.isSelected());
            kh.setGhiChu(txtGhiChu.getText().trim());
            kh.setNguoiTao(nguoiTao);

            // Lấy thời gian hiện tại
            LocalDateTime now = LocalDateTime.now();
            Timestamp ngayDangKy = Timestamp.valueOf(now);
            kh.setNgayDangKy(ngayDangKy);

            kh.setTrangThaiKH(true); // hoặc lấy giá trị từ giao diện người dùng
            kh.setTrangThaiXoa(false); // hoặc lấy giá trị từ giao diện người dùng

            String diaChi = txtDiaChi.getText().trim();
            boolean diaChiMacDinh = rdoMacDinh.isSelected();
            DiaChi dc = createDiaChi(txtMaKH.getText());
            dao.saveKhachHangAndDiaChi(kh, dc);
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");

            loadData();
            setForm(kh.getMaKhachHang());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu thông tin khách hàng: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            String maKhachHang = txtMaKH.getText().trim();
            if (maKhachHang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng không được để trống.");
                return;
            }

            int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn thực sự muốn xóa khách hàng này?");
            if (confirmResult == JOptionPane.YES_OPTION) {
                dao.deleteDiaChiByKhachHangId(maKhachHang); // Xóa địa chỉ liên quan
                dao.deleteById(maKhachHang); // Xóa khách hàng

                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                clearForm();
                loadData();
                if (!khachHangList.isEmpty()) {
                    index = 0;
                    setForm(khachHangList.get(index).getMaKhachHang());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            KhachHang kh = new KhachHang();
            kh.setMaKhachHang(txtMaKH.getText().trim());
            kh.setTenKH(txtTenKH.getText().trim());
            kh.setSoDienThoai(txtSoDT.getText().trim());
            kh.setEmail(txtEmail.getText().trim());
            kh.setNgaySinh(DateHelper.toDate(txtNgaySinh.getText().trim(), "MM/dd/yyyy"));
            kh.setGioiTinh(rdoNam.isSelected());
            kh.setGhiChu(txtGhiChu.getText().trim());
            kh.setNguoiTao(UserSession.getUser().getUserName());
            kh.setNgayDangKy(Timestamp.valueOf(LocalDateTime.now()));
            kh.setTrangThaiKH(true); // hoặc lấy giá trị từ giao diện người dùng
            kh.setTrangThaiXoa(false); // hoặc lấy giá trị từ giao diện người dùng

            dao.update(kh);
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");

            loadData();
            setForm(kh.getMaKhachHang());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thông tin khách hàng. ");
            e.printStackTrace();
        }
    }

    public void first() {
        if (!khachHangList.isEmpty()) {
            index = 0;
            setForm(khachHangList.get(index).getMaKhachHang());
        }
    }

    public void previous() {
        if (index > 0) {
            index--;
            setForm(khachHangList.get(index).getMaKhachHang());
        }
    }

    public void next() {
        if (index < khachHangList.size() - 1) {
            index++;
            setForm(khachHangList.get(index).getMaKhachHang());
        }
    }

    public void last() {
        if (!khachHangList.isEmpty()) {
            index = khachHangList.size() - 1;
            setForm(khachHangList.get(index).getMaKhachHang());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtTimKiem2 = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        rdoMacDinh = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSoDT = new javax.swing.JTextField();
        lblGioiTinh = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txtTenKH = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        btnMoi = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnDanhsach = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));

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

        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/org/ultrainstinct/icon/search.png"))); // NOI18N
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        jLabel7.setText("Ghi chú");

        jLabel6.setText("Địa chỉ");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane2.setViewportView(txtDiaChi);

        rdoMacDinh.setText("Mặc định");
        rdoMacDinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMacDinhActionPerformed(evt);
            }
        });

        jLabel5.setText("Ngày sinh");

        jLabel9.setText("Email");

        jLabel3.setText("Số điện thoại");

        lblGioiTinh.setText("Giới tính");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel2.setText("Tên khách hàng");

        jLabel1.setText("Mã khách hàng");

        txtMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKHActionPerformed(evt);
            }
        });

        btnMoi.setBackground(new java.awt.Color(0, 204, 0));
        btnMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 204, 51));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 204, 0));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLuu.setBackground(new java.awt.Color(0, 204, 0));
        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(255, 255, 255));
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(0, 204, 51));
        btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(51, 204, 0));
        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
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

        btnFirst.setBackground(new java.awt.Color(51, 204, 0));
        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        jLabel8.setText("Người tạo");

        txtNguoiTao.setEnabled(false);

        jLabel10.setText("Ngày tạo");

        txtNgayTao.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(122, 122, 122)
                                        .addComponent(lblGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)
                                        .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(rdoMacDinh, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(73, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(644, 644, 644)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFirst)
                    .addComponent(btnLuu))
                .addGap(32, 32, 32)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnPrev)
                        .addGap(34, 34, 34)
                        .addComponent(btnNext))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSua)))
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLast)
                    .addComponent(btnMoi))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(lblGioiTinh)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(132, 132, 132)
                                .addComponent(jLabel4))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8)
                                        .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10)
                                        .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(rdoMacDinh)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnPrev)
                                .addComponent(btnFirst))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnLast)
                                .addComponent(btnNext)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLuu)
                            .addComponent(btnXoa)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSua)
                                .addComponent(btnMoi))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(jLabel7)))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Chi tiết khách hàng");

        btnDanhsach.setBackground(new java.awt.Color(51, 204, 0));
        btnDanhsach.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDanhsach.setForeground(new java.awt.Color(255, 255, 255));
        btnDanhsach.setText("Danh sách");
        btnDanhsach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDanhsachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(txtTimKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDanhsach))
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDanhsach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem2ActionPerformed

    private void txtTimKiem2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem2KeyReleased

    private void txtTimKiem2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem2KeyTyped
        // TODO add your handling code here:
        loadData();
    }//GEN-LAST:event_txtTimKiem2KeyTyped

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
       String maKhachHang = txtTimKiem2.getText().trim();
        if (!maKhachHang.isEmpty()) {
            try {
                setForm(maKhachHang);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi tìm kiếm khách hàng: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khách hàng để tìm kiếm.");
        }
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnDanhsachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhsachActionPerformed

        try {
                loadData();
                 main.showForm(new KhachHangJFrame(main));
            
//        try
//        {
//            loadData();
//            main.showForm(new KhachHangJFrame(main));
//        } catch (SQLException ex) {
//            Logger.getLogger(ChiTietKhachHangJFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietKhachHangJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnDanhsachActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        save();
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        previous();
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

    private void txtMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKHActionPerformed

    private void rdoMacDinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMacDinhActionPerformed
        rdoMacDinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (rdoMacDinh.isSelected()) {
                    try {
                        String maKhachHang = txtMaKH.getText().trim();
                        if (!maKhachHang.isEmpty()) {
                            String diaChiResult = dao.getDiaChi(maKhachHang);
                            String[] diaChiParts = diaChiResult.split("\\|");
                            if (diaChiParts.length == 2) {
                                boolean isMacDinh = Boolean.parseBoolean(diaChiParts[0]);
                                txtDiaChi.setText(diaChiParts[1]);
                                rdoMacDinh.setSelected(isMacDinh);
                            }
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi lấy thông tin địa chỉ.");
                        e.printStackTrace();
                    }
                }
            }
        });
    }//GEN-LAST:event_rdoMacDinhActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDanhsach;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTim;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JRadioButton rdoMacDinh;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtSoDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem2;
    // End of variables declaration//GEN-END:variables
}
