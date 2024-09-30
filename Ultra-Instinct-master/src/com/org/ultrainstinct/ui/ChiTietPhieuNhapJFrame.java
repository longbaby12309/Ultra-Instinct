package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.AuthDAO;
import com.org.ultrainstinct.dao.impl.AuthDAOImpl;
import com.org.ultrainstinct.dao.impl.NhapKhoChiTiet;
import com.org.ultrainstinct.dao.impl.NhapKhoDAOImpl;
import com.org.ultrainstinct.dto.UserSession;
import com.org.ultrainstinct.main.Main;
import com.org.ultrainstinct.model.NhapKho;
import com.org.ultrainstinct.model.PhieuNhapChiTiet;
import com.org.ultrainstinct.model.SanPham;
import com.org.ultrainstinct.model.XuatSanPham;
import com.org.ultrainstinct.utils.MessageDialog;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ChiTietPhieuNhapJFrame extends javax.swing.JPanel { 
      private int currentIndex = 0;
    List<XuatSanPham> listTable = new ArrayList<>();
    List<NhapKhoChiTiet> listMouses = new ArrayList<>();
    private AuthDAO authDao = new AuthDAOImpl();
    private Main main;
    private PhieuNhapChiTiet pnct;
    NhapKhoDAOImpl nkDAO = new NhapKhoDAOImpl();
    NhapKho nhapKho = new NhapKho();
    NhapKhoChiTiet nkct = new NhapKhoChiTiet(nhapKho, pnct);
    public ChiTietPhieuNhapJFrame(Main main) {
        this.main = main;
        initComponents();        
    }
    
    void cboNCC(){
        List<NhapKhoChiTiet> list = nkDAO.ListAll();
        for(NhapKhoChiTiet nkct : list){
        cboNhaCungCap.addItem(nkct.getPhieuNhapChiTiet().getMaNhaCungCap());
        }
    }
    
    
    void resetFillToTable(){
        List<NhapKhoChiTiet> list = nkDAO.ListAll();
        DefaultTableModel model = (DefaultTableModel) tblPhieuNhapKho.getModel();
        fillToTable();
    }
    
    void fillToTableByID(PhieuNhapChiTiet pnct) {
    // Gọi phương thức để lấy dữ liệu từ cơ sở dữ liệu
    List<NhapKhoChiTiet> list = nkDAO.ListAll();
    System.out.println("Results size: " + list.size());
    for (NhapKhoChiTiet nkct : list) {
        System.out.println(">> " + nkct.toString());
        if(nkct.getPhieuNhapChiTiet().getMaPNCT().equals(pnct.getMaPNCT())){
        txtMaPNCT.setText(nkct.getPhieuNhapChiTiet().getMaPNCT());
        txtMaNhapKho.setText(nkct.getNhapKho().getMaNhapKho());
        txtNgayLap.setText(nkct.getPhieuNhapChiTiet().getNgayNhap().toString());
        txtTrangThai.setText(nkct.getPhieuNhapChiTiet().isTrangThai() ? "Thành công" : "Thất bại");
        txtMaNhanVien.setText(nkct.getNhapKho().getMaNhanVien());
        txtMaSanPham.setText(nkct.getPhieuNhapChiTiet().getMaSanPham());
        cboNhaCungCap.addItem(nkct.getPhieuNhapChiTiet().getMaNhaCungCap());
        txtGiaNhap.setText(String.valueOf(nkct.getPhieuNhapChiTiet().getGiaNhap()));
        txtSoLuong.setText(String.valueOf(nkct.getPhieuNhapChiTiet().getSoLuong()));}
    }
    fillToTable();
    }
    
    void setForm(){
        txtMaNhapKho.setText("");
        txtNgayLap.setText("");
        txtTrangThai.setText("");
        txtMaNhanVien.setText("");
        txtMaSanPham.setText("");
        cboNhaCungCap.removeAllItems();
        txtGiaNhap.setText("");
        txtSoLuong.setText("");
        txtMaPNCT.setText("");
    }
    
    public void Insert() {
    // Đọc giá trị từ các trường văn bản
    String maPNCT = txtMaPNCT.getText();
    String maNhapKho = txtMaNhapKho.getText();
    
    // Chuyển đổi ngày từ chuỗi
    java.sql.Date ngayNhap = java.sql.Date.valueOf(txtNgayLap.getText());

    boolean trangThai = false;
    // Chuyển đổi trạng thái
    if(txtTrangThai.getText().equals("Thành công")){
        trangThai = true;
    }else{
        trangThai = false;
    }

    // Chuyển đổi giá từ chuỗi
    float giaNhap = Float.parseFloat(txtGiaNhap.getText());
// Chuyển đổi số lượng từ chuỗi
    int soLuong = Integer.parseInt(txtSoLuong.getText());

    // Đọc các giá trị khác
    String maNhanVien = txtMaNhanVien.getText();
    String maSanPham = txtMaSanPham.getText();
    String nhaCungCap = cboNhaCungCap.getSelectedItem().toString();

    // Gọi phương thức để chèn hoặc cập nhật dữ liệu nếu maPNCT không tồn tại
    nkDAO.InsertOrUpdateProcedure(maPNCT, maNhapKho, ngayNhap, trangThai, maNhanVien, maSanPham, nhaCungCap, giaNhap, soLuong);
    }
    
    void Delete(){
        nkDAO.DeleteProce(txtMaPNCT.getText(),txtMaSanPham.getText());
        MessageDialog.alert(this, "Xóa Thành Công");
    }
    
    void getForm(int index) {
    // Lấy danh sách NhapKhoChiTiet từ DAO
    List<NhapKhoChiTiet> list = nkDAO.ListAll();

    // Kiểm tra chỉ số có hợp lệ không
    if (index >= 0 && index < list.size()) {
        NhapKhoChiTiet nkctNew = list.get(index);
        for(NhapKhoChiTiet nkct : list){
            if(nkct.getPhieuNhapChiTiet().getMaPNCT().contains(nkctNew.getPhieuNhapChiTiet().getMaPNCT())){
                cboNhaCungCap.removeAllItems();
                // Cập nhật các trường văn bản hoặc thành phần giao diện với dữ liệu từ nkct
                txtMaPNCT.setText(nkct.getPhieuNhapChiTiet().getMaPNCT());
                txtMaSanPham.setText(nkct.getPhieuNhapChiTiet().getMaSanPham());
                txtMaNhapKho.setText(nkct.getPhieuNhapChiTiet().getMaNhapKho());
                cboNhaCungCap.addItem((String)nkct.getPhieuNhapChiTiet().getMaNhaCungCap());
                txtGiaNhap.setText(String.valueOf(nkct.getPhieuNhapChiTiet().getGiaNhap()));
                txtSoLuong.setText(String.valueOf(nkct.getPhieuNhapChiTiet().getSoLuong()));
                txtTrangThai.setText(nkct.getPhieuNhapChiTiet().isTrangThai() ? "Thành công" : "Chưa thành công");
                txtNgayLap.setText(nkct.getPhieuNhapChiTiet().getNgayNhap().toString());
                // Cập nhật chỉ số hiện tại
                currentIndex = index;
            }
        }
    } else {
        // Xử lý trường hợp chỉ số không hợp lệ
        System.out.println("Index is out of range.");
    }
    }
    
    boolean checkTrungMaSanPhamAndSoLuong(String maSanPham, String maNhapKho, float giaNhap, int soLuong){
        List<NhapKhoChiTiet> list = nkDAO.ListAll();
        for(NhapKhoChiTiet nkct : list){
            if(!nkct.getPhieuNhapChiTiet().getMaSanPham().equals(maSanPham) && nkct.getPhieuNhapChiTiet().getMaNhapKho().equals(maNhapKho) && nkct.getPhieuNhapChiTiet().getGiaNhap()==giaNhap && nkct.getPhieuNhapChiTiet().getSoLuong()==soLuong){
                return true;
            }}
        return false;
    }

    
    void getListallFillToMsg(){
        List<XuatSanPham> list = nkDAO.FillToSanPhamById(txtMaSanPham.getText(), (String) cboNhaCungCap.getSelectedItem());
        DefaultTableModel model = (DefaultTableModel) tblPhieuNhapKho.getModel();
model.setRowCount(0);
        for(XuatSanPham xsp : list){
            model.addRow(new Object[]{
           xsp.getPnct().getMaSanPham(), xsp.getSp().getTenSanPham(), xsp.getPnct().getSoLuong(), xsp.getPnct().getGiaNhap(), xsp.getPnct().getSoLuong()*xsp.getPnct().getGiaNhap()
        });
        }
    }
    
    void addListToTable(){ 
        PhieuNhapChiTiet pnctNew = new PhieuNhapChiTiet();
        SanPham sp = new SanPham();
        NhapKho nk = new NhapKho();
        String maSP = txtMaSanPham.getText();
        String tenSP = "";
        String maPNCT = txtMaPNCT.getText();
        String maNhapKho = txtMaNhapKho.getText();

        // Chuyển đổi ngày từ chuỗi
        java.sql.Date ngayNhap = java.sql.Date.valueOf(txtNgayLap.getText());

        
        
        String maNhanVien = txtMaNhanVien.getText();
        
        boolean trangThai = false;
        
        
        
        // Chuyển đổi trạng thái
        if(txtTrangThai.getText().equals("Thành công")){
            trangThai = true;
        }else{
            trangThai = false;
        }
        try {
            tenSP = nkDAO.getTenSanPham(maSP);
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietPhieuNhapJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        float giaNgap = Float.parseFloat(txtGiaNhap.getText());
        pnctNew.setMaPNCT(maPNCT);
        pnctNew.setMaNhapKho(maNhapKho);
        pnctNew.setNgayNhap(ngayNhap);
        pnctNew.setTrangThai(trangThai);
        pnctNew.setMaNhaCungCap(String.valueOf(cboNhaCungCap.getSelectedItem()));
        nk.setMaNhanVien(maNhanVien);
        pnctNew.setMaSanPham(maSP);
        sp.setTenSanPham(tenSP);
        pnctNew.setSoLuong(soLuong);
        pnctNew.setGiaNhap(giaNgap);
        XuatSanPham xspNew = new XuatSanPham(pnctNew, sp, nk);
        listTable.add(xspNew);
        DefaultTableModel model = (DefaultTableModel) tblPhieuNhapKho.getModel();
        model.setRowCount(0);
        for(XuatSanPham xsp : listTable){
            model.addRow(new Object[]{
           xsp.getPnct().getMaSanPham(), xsp.getSp().getTenSanPham(), xsp.getPnct().getSoLuong(), xsp.getPnct().getGiaNhap(), xsp.getPnct().getSoLuong()*xsp.getPnct().getGiaNhap()
        });
        }
            
    }
    void fillToTable(){
        String maSP = txtMaSanPham.getText();
        String maNCC = (String) cboNhaCungCap.getSelectedItem();
        List<XuatSanPham> list = nkDAO.FillToSanPhamById(maSP, maNCC);
        DefaultTableModel model = (DefaultTableModel) tblPhieuNhapKho.getModel();
        model.setRowCount(0);
        for(XuatSanPham xsp : list){
            model.addRow(new Object[]{
           xsp.getPnct().getMaSanPham(), xsp.getSp().getTenSanPham(), xsp.getPnct().getSoLuong(), xsp.getPnct().getGiaNhap(), xsp.getPnct().getSoLuong()*xsp.getPnct().getGiaNhap()
        });
        }
    }
    
    void getAddTableToInsert(){
for(XuatSanPham xsp : listTable){
            nkDAO.InsertOrUpdateProcedure(xsp.getPnct().getMaPNCT(), xsp.getPnct().getMaNhapKho(), xsp.getPnct().getNgayNhap(), xsp.getPnct().isTrangThai(), xsp.getNk().getMaNhanVien(), xsp.getPnct().getMaSanPham(), xsp.getPnct().getMaNhaCungCap(), xsp.getPnct().getGiaNhap(), xsp.getPnct().getSoLuong());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNgayLap = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTrangThai = new javax.swing.JTextField();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtMaNhapKho = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cboNhaCungCap = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuNhapKho = new javax.swing.JTable();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtMaPNCT = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();

        setBackground(new java.awt.Color(245, 245, 245));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Quản lý kho");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Phiếu nhập kho");

        jLabel3.setText("Mã nhập kho");

        jLabel4.setText("Ngày lập");

        jLabel5.setText("Trạng thái");

        jLabel6.setText("Mã nhân viên");

        jLabel7.setText("Mã sản phẩm");

        jLabel8.setText("Mã nhà cung cấp");

        jLabel10.setText("Giá nhập");

        jLabel13.setText("Số lượng");

        tblPhieuNhapKho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Giá nhập", "Tổng nhập"
            }
        ));
        tblPhieuNhapKho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuNhapKhoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhieuNhapKho);

        btnPre.setBackground(new java.awt.Color(0, 204, 0));
        btnPre.setForeground(new java.awt.Color(255, 255, 255));
        btnPre.setText("<<");
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(0, 204, 0));
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLuu.setBackground(new java.awt.Color(0, 204, 0));
        btnLuu.setForeground(new java.awt.Color(255, 255, 255));
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 204, 0));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 204, 0));
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnMoi.setBackground(new java.awt.Color(0, 204, 0));
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        jLabel1.setText("Mã chi tiết phiếu nhập");

        btnAdd.setBackground(new java.awt.Color(0, 204, 0));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Thêm vào danh sách");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(0, 204, 0));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Lưu danh sách");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(txtMaNhapKho, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(txtMaSanPham)
                            .addComponent(txtGiaNhap))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(66, 66, 66))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(cboNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(71, 71, 71)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtMaPNCT)
                                        .addGap(66, 66, 66))))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLuu)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoa)
                                .addGap(18, 18, 18)
                                .addComponent(btnSua)
                                .addGap(18, 18, 18)
                                .addComponent(btnMoi)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSave))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnPre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNext))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNhapKho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaPNCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPre)
                    .addComponent(btnNext))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLuu)
                    .addComponent(btnXoa)
                    .addComponent(btnSua)
                    .addComponent(btnMoi)
                    .addComponent(btnAdd)
                    .addComponent(btnSave))
                .addContainerGap())
        );

        jButton4.setBackground(new java.awt.Color(51, 204, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Danh sách");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)))
                        .addGap(0, 203, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel11)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        main.showForm(new KhoHangJFrame(main));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTimKiemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemKeyTyped

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        setForm();
        cboNCC();
        
        System.out.println(">> Check: " + authDao.isLogin());
        
        if(authDao.isLogin()){
            UserSession userSession = UserSession.getUser();
            txtMaNhanVien.setText(userSession.getUserName());
        }

        DefaultTableModel model = (DefaultTableModel) tblPhieuNhapKho.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
         resetListAll();
        List<NhapKhoChiTiet> list = nkDAO.ListAll();
        for (NhapKhoChiTiet nkct : list) {
            if(nkct.getPhieuNhapChiTiet().getMaPNCT().equals(txtMaPNCT.getText())){
                MessageDialog.alert(this, "Vui lòng không trùng mã Phiếu nhập chi tiết!");
                break;
            }else{
                String maSP = txtMaSanPham.getText();
                String maNK = txtMaNhapKho.getText();
                float giaNhap = Float.parseFloat(txtGiaNhap.getText());
                int soLuong = Integer.parseInt(txtSoLuong.getText());
                if(checkTrungMaSanPhamAndSoLuong(maSP, maNK, giaNhap, soLuong)){
                    MessageDialog.alert(this, "bạn đã nhập trùng");
                }else{
                    Insert();
                    MessageDialog.alert(this, "Lưu Thành Công");
                    resetFillToTable();
                }
                break;
            }
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
         Insert();
        resetFillToTable();
        MessageDialog.alert(this, "Sửa Thành Công");
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        Delete();
        resetFillToTable();
        setForm();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        getAddTableToInsert();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        String maSP = txtMaSanPham.getText();
        String maNK = txtMaNhapKho.getText();
        float giaNhap = Float.parseFloat(txtGiaNhap.getText());
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        if(checkTrungMaSanPhamAndSoLuong(maSP, maNK, giaNhap, soLuong)){
            MessageDialog.alert(this, "bạn đã nhập trùng");
            
        }else{
            addListToTable();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void tblPhieuNhapKhoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuNhapKhoMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tblPhieuNhapKho.getModel();
        int index = tblPhieuNhapKho.getSelectedRow();
        getForm(index);
        System.out.println("Click");
    }//GEN-LAST:event_tblPhieuNhapKhoMouseClicked

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        // TODO add your handling code here:
        if (currentIndex > 0) {
            currentIndex--;
            getForm(currentIndex);
        } else {
            // Nếu đã ở bản ghi đầu tiên, có thể thông báo cho người dùng
            JOptionPane.showMessageDialog(this, "Đã đến bản ghi đầu tiên.");
        }
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        List<NhapKhoChiTiet> list = nkDAO.ListAll();
        if (currentIndex < list.size() - 1) {
            currentIndex++;
            getForm(currentIndex);
        } else {
            // Nếu đã ở bản ghi cuối cùng, có thể thông báo cho người dùng
            JOptionPane.showMessageDialog(this, "Đã đến bản ghi cuối cùng.");
        }
    }//GEN-LAST:event_btnNextActionPerformed
    
    void resetListAll(){
        List<NhapKhoChiTiet> list = nkDAO.ListAll();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboNhaCungCap;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPhieuNhapKho;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtMaNhapKho;
    private javax.swing.JTextField txtMaPNCT;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtNgayLap;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTrangThai;
    // End of variables declaration//GEN-END:variables
}
