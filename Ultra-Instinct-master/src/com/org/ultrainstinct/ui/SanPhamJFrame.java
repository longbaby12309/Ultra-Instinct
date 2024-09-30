package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.SanPhamDAO;
import com.org.ultrainstinct.dao.impl.SanPhamDAOImpl;
import com.org.ultrainstinct.main.Main;
import com.org.ultrainstinct.model.SanPham;
import org.apache.poi.ss.usermodel.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

public class SanPhamJFrame extends javax.swing.JPanel {
    
    private SanPhamDAO dao = new SanPhamDAOImpl();
    int index = 0;
    private Main main;
    List<SanPham> list = new ArrayList<>();

    public SanPhamJFrame(Main main) {
        this.main = main;
        initComponents();
        init();
        fillComboBox();
    }

    public void init() {
        filltable();
        tblSanPham.setRowSelectionInterval(0, 0);
        fillComboBox();

        cboLoai.addActionListener(evt -> filltable());
//        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                if (evt.getClickCount() == 2) {
//                    tblSanPhamMouseClicked(evt);
//                }
//            }
//        });
//        cboLoai.addActionListener(evt -> filterByLoai());
    }

    void fillComboBox() {
        // Các giá trị cần thêm vào JComboBox
        String[] categories = {"Chọn loại hàng", "Garmin", "Loa", "headphone", "earbuds", "soundbar"};

        // Cập nhật các giá trị vào JComboBox
        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(categories));
    }
    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

        String keyword = txtTimKiem2.getText().trim();
        String loai = cboLoai.getSelectedItem().toString();

        try {
            List<SanPham> list = new ArrayList<>();

            // Handle the case where no category is selected
            if ("Chọn loại hàng".equals(loai)) {
                loai = null;
            }

            // Apply filters based on the presence of keyword and loai
            if (keyword.isEmpty() && loai == null) {
                list = dao.findAll();
            } else if (keyword.isEmpty()) {
                // Filter by Loai only
                list = dao.selectByLoai(loai);
            } else if (loai == null) {
                // Filter by keyword only
                list = dao.selectByKeyword(keyword);
            } 
//            else {
//                // Filter by both keyword and Loai
//                list = dao.selectByKeywordAndLoai(keyword, loai);
//            }

            // Populate the table with filtered data
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getSanPhamNo(),
                    sp.getMaSanPham(),
                    sp.getLoaiSanPham(),
                    sp.getTenSanPham(),
                    sp.getGiaNiemYet(),
                    sp.getHinh() != null ? sp.getHinh() : "",
                    sp.getSoLuongTon()
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi tải dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }



//    void filterByLoai() {
//        SearchSanPhamDto dto = new SearchSanPhamDto();
//        dto.setLoaiSanPham((String) cboLoai.getSelectedItem());
//
//        try {
//            List<SanPham> list = dao.searchSanPham(dto);
//
//            DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
//            model.setRowCount(0);
//            for (SanPham sp : list) {
//                Object[] row = {
//                    sp.getSanPhamNo(),
//                    sp.getMaSanPham(),
//                    sp.getLoaiSanPham(),
//                    sp.getTenSanPham(),
//                    sp.getGiaNiemYet(),
//                    sp.getHinh() != null ? sp.getHinh() : "",
//                    sp.getSoLuongTon()
//                };
//                model.addRow(row);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi lọc dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }

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
        if (index < tblSanPham.getRowCount() - 1) {
            index++;
            updateTableSelection();
        }
    }

    void last() {
        index = tblSanPham.getRowCount() - 1;
        updateTableSelection();
    }

    void updateTableSelection() {
        if (tblSanPham.getRowCount() > 0) {
            tblSanPham.setRowSelectionInterval(index, index);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtTimKiem2 = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        cboLoai = new javax.swing.JComboBox<>();
        btnDanhSach = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Sản phẩm");

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

        btnFirst.setBackground(new java.awt.Color(51, 204, 0));
        btnFirst.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnFirst.setForeground(new java.awt.Color(255, 255, 255));
        btnFirst.setText("<<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(51, 204, 0));
        btnLast.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnLast.setForeground(new java.awt.Color(255, 255, 255));
        btnLast.setText(">>");
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn loại hàng", "Garmin", "Loa" }));

        btnDanhSach.setBackground(new java.awt.Color(51, 204, 0));
        btnDanhSach.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDanhSach.setForeground(new java.awt.Color(255, 255, 255));
        btnDanhSach.setText("Danh sách");
        btnDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDanhSachActionPerformed(evt);
            }
        });

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Mã loại sản phẩm", "Tên sản phẩm", "Giá niêm yết", "Hình", "Số lượng tồn"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        btnNext.setBackground(new java.awt.Color(51, 204, 0));
        btnNext.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(51, 204, 0));
        btnPrev.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnPrev.setForeground(new java.awt.Color(255, 255, 255));
        btnPrev.setText("<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnXuatFile.setBackground(new java.awt.Color(0, 102, 255));
        btnXuatFile.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnXuatFile.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatFile.setText("Xuất file");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtTimKiem2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem2)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(btnDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXuatFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(98, 98, 98))
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
    }//GEN-LAST:event_txtTimKiem2KeyTyped

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        filltable();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhSachActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblSanPham.getSelectedRow();
        if (selectedRow >= 0) {
            String maSanPham = tblSanPham.getValueAt(selectedRow, 1).toString();
            ChiTietSanPhamJFrame ctsp = new ChiTietSanPhamJFrame(main);
            ctsp.setProductDetails(maSanPham);
            main.showForm(ctsp);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDanhSachActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblSanPham.getSelectedRow();
        if (selectedRow >= 0) {
            String maSanPham = tblSanPham.getValueAt(selectedRow, 1).toString();
            ChiTietSanPhamJFrame ctsp = new ChiTietSanPhamJFrame(main);
            ctsp.setProductDetails(maSanPham);
            main.showForm(ctsp);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        // TODO add your handling code here:
        try {
            // Cập nhật danh sách từ bảng
            DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
            list.clear(); 
            for (int i = 0; i < model.getRowCount(); i++) {
                SanPham sp = new SanPham();
                sp.setSanPhamNo((Long) model.getValueAt(i, 0));
                sp.setMaSanPham((String) model.getValueAt(i, 1));
                sp.setLoaiSanPham((String) model.getValueAt(i, 2));
                sp.setTenSanPham((String) model.getValueAt(i, 3));
                sp.setGiaNiemYet((float) model.getValueAt(i, 4));
                sp.setHinh((String) model.getValueAt(i, 5));
                sp.setSoLuongTon((Integer) model.getValueAt(i, 6));
                list.add(sp);
            }

            // Hiển thị hộp thoại lưu file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return; // Người dùng hủy chọn
            }

            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Thêm phần mở rộng nếu người dùng không nhập
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            FileOutputStream fileOut = new FileOutputStream(filePath);
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("SanPham");

            // Tạo style cho tiêu đề cột
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Thêm tiêu đề cột vào sheet
            Row headerRow = sheet.createRow(0);
            String[] headers = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Giá niêm yết", "Số lượng tồn", "Hình ảnh", "Loại sản phẩm"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Thêm dữ liệu vào sheet
            for (int i = 0; i < list.size(); i++) {
                SanPham sp = list.get(i);
                Row row = sheet.createRow(1 + i);

                Cell cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(i + 1);

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(sp.getMaSanPham());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(sp.getTenSanPham());

                cell = row.createCell(3, CellType.NUMERIC);
                cell.setCellValue(sp.getGiaNiemYet());

                cell = row.createCell(4, CellType.NUMERIC);
                cell.setCellValue(sp.getSoLuongTon());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(sp.getHinh());

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(sp.getLoaiSanPham());
            }

            // Đặt độ rộng cho các cột
            sheet.setColumnWidth(0, 2000); // Độ rộng của cột STT
            sheet.setColumnWidth(1, 6000); // Độ rộng của cột Mã sản phẩm
            sheet.setColumnWidth(2, 12000); // Độ rộng của cột Tên sản phẩm
            sheet.setColumnWidth(3, 3000); // Độ rộng của cột Giá niêm yết
            sheet.setColumnWidth(4, 3000); // Độ rộng của cột Số lượng tồn
            sheet.setColumnWidth(5, 6000); // Độ rộng của cột Hình ảnh
            sheet.setColumnWidth(6, 4000); // Độ rộng của cột Loại sản phẩm

            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            JOptionPane.showMessageDialog(this, "File đã xuất thành công vào: " + filePath);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể ghi file: Quyền truy cập bị từ chối hoặc đường dẫn không tồn tại.");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void createHeader(Sheet sheet) {
        CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = sheet.getWorkbook().createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Giá niêm yết", "Số lượng tồn", "Hình ảnh", "Loại sản phẩm"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void populateRow(Row row, SanPham sp, int index) {
        row.createCell(0, CellType.NUMERIC).setCellValue(index);
        row.createCell(1, CellType.STRING).setCellValue(sp.getMaSanPham());
        row.createCell(2, CellType.STRING).setCellValue(sp.getTenSanPham());
        row.createCell(3, CellType.NUMERIC).setCellValue(sp.getGiaNiemYet());
        row.createCell(4, CellType.NUMERIC).setCellValue(sp.getSoLuongTon());
        row.createCell(5, CellType.STRING).setCellValue(sp.getHinh());
        row.createCell(6, CellType.STRING).setCellValue(sp.getLoaiSanPham());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDanhSach;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtTimKiem2;
    // End of variables declaration//GEN-END:variables
}
