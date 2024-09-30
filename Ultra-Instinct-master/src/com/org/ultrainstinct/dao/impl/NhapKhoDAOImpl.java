package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.dao.NhapKhoDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.org.ultrainstinct.model.NhapKho;
import com.org.ultrainstinct.model.PhieuNhapChiTiet;
import com.org.ultrainstinct.model.SanPham;
import com.org.ultrainstinct.model.XuatSanPham;
import com.org.ultrainstinct.utils.Constant;
import com.org.ultrainstinct.utils.MessageDialog;
import java.util.ArrayList;
import java.util.Date;
import java.sql.CallableStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * NhapKhoDAOImpl represents a concrete implementation of NhapKhoDAO.
 * </p>
 *
 * @author MinhNgoc.
 */
public class NhapKhoDAOImpl extends AbstractCrudDao<NhapKho, Long> implements NhapKhoDAO {

    /**
     * <p>
     * Method mapRow NhapKho.
     * </p>
     *
     * @param rs ResultSet.
     * @return NhapKho.
     * @throws SQLException SQLException.
     * @author MinhNgoc.
     */
    @Override
    protected NhapKho mapRow(ResultSet rs) throws SQLException {
        return NhapKho
               .builder()
               .nhapKhoNo(rs.getLong(Constant.NHAP_KHO_NO))
               .maNhapKho(rs.getString("maNhapKho"))
               .maNhanVien(rs.getString("maNhanVien"))
               .ngayNhap(rs.getDate("ngayNhap"))
               .trangThai(rs.getBoolean("trangThai"))
               .build();
    }

    /**
     * <p>
     * Method getTableName table NhapKho.
     * </p>
     *
     * @return String.
     * @author MinhNgoc.
     */
    @Override
    protected String getTableName() {
        return Constant.NHAP_KHO_TABLE_NAME;
    }

    /**
     * <p>
     * Method getPrimaryKeyColumnName
     * </p>
     *
     * @return String.
     * @author MinhNgoc
     */
    @Override
    protected String getPrimaryKeyColumnName() {
        return Constant.NHAP_KHO_NO;
    }

    /**
     * <p>
     * Method getEntityValues.
     * </p>
     * 
     * @param entity NhapKho.
     * @return Object[].
     * @author MinhNgoc.
     */
    @Override
    protected Object[] getEntityValues(NhapKho entity) {
        return new Object[] {
            entity.getMaNhapKho(),
            entity.getMaNhanVien(),
            entity.getNgayNhap(),
            entity.isTrangThai()
        };
    }

    /**
     * <p>
     * Method getInsertQuery.
     * </p>
     * 
     * @return String
     * @author MinhNgoc.
     */
    @Override
    protected String getInsertQuery() {
        return """
                INSERT INTO
                """ + Constant.NHAP_KHO_TABLE_NAME + """
                (maNhapKho, maNhanVien, ngayNhap, trangThai)
                values (?,?,?,?);
                """;
    }

    /**
     * <p>
     * Method getUpdateQuery.
     * </p>
     * 
     * @return String
     * @author MinhNgoc.
     */
    @Override
protected String getUpdateQuery() {
        return """
                UPDATE
                """ + Constant.NHAP_KHO_TABLE_NAME +
                " SET maNhapKho = ?, maNhanVien = ?, ngayNhap = ?, trangThai = ? WHERE "
                + Constant.NHAP_KHO_NO + " = ?";
    }

    /**
     * <p>
     * Method get max maxMaNhapKho.
     * </p>
     *
     * @return long maxMaNhapKho.
     * @author MinhNgoc.
     * @throws java.sql.SQLException
     */
    @Override
    public long getMaxMaNhapKho() throws SQLException {
        String sql = " SELECT COUNT(1) MAX_MA_NHAPKHO FROM " + getTableName();

        PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getLong("MAX_MA_NHAPKHO");
        }
        return 0L;
    }

    @Override
    public void save(NhapKho nhapKho, List<PhieuNhapChiTiet> phieuNhapChiTietList) throws SQLException {
        String insertPhieuNhapChiTietSql = """
        INSERT INTO PhieuNhapChiTiet(maPNCT, maSanPham, maNhapKho, maNhaCungCap, giaNhap, soLuong)
        VALUES (?, ?, ?, ?, ?, ?);
        """;

        try (PreparedStatement chiTietStmt = connection.prepareStatement(insertPhieuNhapChiTietSql)) {

            // Insert into HoaDon table
            this.save(nhapKho);

            // Insert into HoaDonChiTiet table
            for (PhieuNhapChiTiet chiTiet : phieuNhapChiTietList) {
                chiTietStmt.setString(1, chiTiet.getMaPNCT());
                chiTietStmt.setString(2, chiTiet.getMaSanPham());
                chiTietStmt.setString(3, chiTiet.getMaNhapKho());
                chiTietStmt.setString(4, chiTiet.getMaNhaCungCap());
                chiTietStmt.setFloat(5, chiTiet.getGiaNhap());
                chiTietStmt.setInt(6, chiTiet.getSoLuong());
                chiTietStmt.addBatch();
            }
            chiTietStmt.executeBatch();
        } catch (SQLException e) {
            throw new SQLException("Failed to create invoice and details", e);
        }
    }
    
    public List<NhapKhoChiTiet> ShowByID(String id, String ncc, Integer sl, String maPNCT) {
    String sql = """
                 SELECT NK.maNhapKho, NK.ngayNhap, NK.maNhanVien, PNCT.maPNCT,
                        PNCT.maSanPham, PNCT.maNhaCungCap, PNCT.giaNhap, PNCT.soLuong, PNCT.trangThai 
                 FROM PhieuNhapChiTiet PNCT
                 LEFT JOIN NhapKho NK ON NK.maNhapKho = PNCT.maNhapKho
                 WHERE NK.maNhapKho = ? AND PNCT.maNhaCungCap = ? AND PNCT.soLuong = ? AND PNCT.maPNCT = ?
                 """;

    List<NhapKhoChiTiet> result = new ArrayList<>();
    System.out.println("Running query: " + sql);
    System.out.println("With parameters: id=" + id + ", ncc=" + ncc + ", sl=" + sl);

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, id);
        stmt.setString(2, ncc);
        stmt.setInt(3, sl);
stmt.setString(4, maPNCT);
        ResultSet rs = stmt.executeQuery();

        // Thêm lệnh in để kiểm tra tham số
        System.out.println("Set parameters: id=" + id + ", ncc=" + ncc + ", sl=" + sl);

        while (rs.next()) {
                // Ánh xạ kết quả vào đối tượng NhapKho
                NhapKho nhapKho = new NhapKho();
                nhapKho.setMaNhapKho(rs.getString("maNhapKho"));
                nhapKho.setNgayNhap(rs.getDate("ngayNhap"));
                nhapKho.setTrangThai(rs.getBoolean("trangThai"));
                nhapKho.setMaNhanVien(rs.getString("maNhanVien"));

                // Ánh xạ kết quả vào đối tượng PhieuNhapChiTiet
                PhieuNhapChiTiet phieuNhapChiTiet = new PhieuNhapChiTiet();
                phieuNhapChiTiet.setMaPNCT(rs.getString("maPNCT"));
                phieuNhapChiTiet.setMaNhapKho(rs.getString("maNhapKho"));
                phieuNhapChiTiet.setMaSanPham(rs.getString("maSanPham"));
                phieuNhapChiTiet.setMaNhaCungCap(rs.getString("maNhaCungCap"));
                phieuNhapChiTiet.setGiaNhap(rs.getFloat("giaNhap"));
                phieuNhapChiTiet.setSoLuong(rs.getInt("soLuong"));
                phieuNhapChiTiet.setTrangThai(rs.getBoolean("trangThai"));

                // Tạo đối tượng NhapKhoChiTiet và thêm vào danh sách kết quả
                NhapKhoChiTiet nhapKhoChiTiet = new NhapKhoChiTiet(nhapKho, phieuNhapChiTiet);
                result.add(nhapKhoChiTiet);
            }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }

    System.out.println("Results size: " + result.size());
    return result;
    }
    public List<XuatSanPham> FillToSanPhamById(String maSanPham, String maNCC){
       //String maSanPham, String maNCC
        List<XuatSanPham> list = new ArrayList<>();
        System.out.println("ma san Pham: " + maSanPham);
        //String sql = "SELECT PNCT.maSanPham, SP.tenSanPham, PNCT.soLuong, PNCT.giaNhap FROM PhieuNhapChiTiet PNCT LEFT JOIN SanPham SP ON PNCT.maSanPham = SP.maSanPham";
        String sql = "SELECT PNCT.maSanPham, SP.tenSanPham, PNCT.soLuong, PNCT.giaNhap FROM PhieuNhapChiTiet PNCT LEFT JOIN SanPham SP ON PNCT.maSanPham = SP.maSanPham where PNCT.maSanPham = ? AND PNCT.maNhaCungCap= ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, maSanPham);            
            stmt.setString(2, maNCC);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                SanPham sp = new SanPham();
                sp.setTenSanPham(rs.getString("tenSanPham"));
                PhieuNhapChiTiet pnct = new PhieuNhapChiTiet();
                pnct.setMaSanPham(rs.getString("maSanPham"));
                pnct.setSoLuong(Integer.parseInt(rs.getString("soLuong")));
                pnct.setGiaNhap(Float.parseFloat(rs.getString("giaNhap")));
                XuatSanPham xsp = new XuatSanPham(pnct, sp);
list.add(xsp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapKhoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(">> list1213" + list);
        return list;
    }
    
    public List<PhieuNhapChiTiet> getKeyWord(String keyWord) throws SQLException {
    List<PhieuNhapChiTiet> list = new ArrayList<>();
    String sql = "";

    if (keyWord.startsWith("NK")) {
        System.out.println("NK");
        sql = "SELECT * FROM PhieuNhapChiTiet WHERE maNhapKho LIKE ?";
    } else if (keyWord.startsWith("PNCT")) {
        System.out.println("PNCT");
        sql = "SELECT * FROM PhieuNhapChiTiet WHERE maPNCT LIKE ?";
    } else if (keyWord.startsWith("NCC")) {
        System.out.println("NCC");
        sql = "SELECT * FROM PhieuNhapChiTiet WHERE maNhaCungCap LIKE ?";
    } else {
        return list; // Không có điều kiện khớp, trả về danh sách trống
    }
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyWord + "%"); // Đặt dấu % bên trong chuỗi tham số
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PhieuNhapChiTiet phieuNhapChiTiet = new PhieuNhapChiTiet();
                phieuNhapChiTiet.setMaPNCT(rs.getString("maPNCT"));
                phieuNhapChiTiet.setMaNhapKho(rs.getString("maNhapKho"));
                phieuNhapChiTiet.setMaSanPham(rs.getString("maSanPham"));
                phieuNhapChiTiet.setMaNhaCungCap(rs.getString("maNhaCungCap"));
                phieuNhapChiTiet.setGiaNhap(rs.getFloat("giaNhap"));
                phieuNhapChiTiet.setSoLuong(rs.getInt("soLuong"));
//                phieuNhapChiTiet.setTrangThai(rs.getBoolean("trangThai"));
                list.add(phieuNhapChiTiet); // Thêm đối tượng vào danh sách
            }
        }

    System.out.println(">> list: " + list);
    return list; // Trả về danh sách đối tượng
}



    public List<NhapKhoChiTiet> ListAll(){
        String sql = """
                     SELECT NK.maNhapKho, NK.ngayNhap, NK.maNhanVien, PNCT.maPNCT,
                                             PNCT.maSanPham, PNCT.maNhaCungCap, PNCT.giaNhap, PNCT.soLuong, PNCT.trangThai 
                                      FROM PhieuNhapChiTiet PNCT
                                      LEFT JOIN NhapKho NK ON NK.maNhapKho = PNCT.maNhapKho""";
        List<NhapKhoChiTiet> result = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Ánh xạ kết quả vào đối tượng NhapKho
                NhapKho nhapKho = new NhapKho();
                nhapKho.setMaNhapKho(rs.getString("maNhapKho"));
                nhapKho.setTrangThai(rs.getBoolean("trangThai"));
                nhapKho.setMaNhanVien(rs.getString("maNhanVien"));
// Ánh xạ kết quả vào đối tượng PhieuNhapChiTiet
                PhieuNhapChiTiet phieuNhapChiTiet = new PhieuNhapChiTiet();
                phieuNhapChiTiet.setMaPNCT(rs.getString("maPNCT"));
                phieuNhapChiTiet.setMaNhapKho(rs.getString("maNhapKho"));
                phieuNhapChiTiet.setMaSanPham(rs.getString("maSanPham"));
                phieuNhapChiTiet.setMaNhaCungCap(rs.getString("maNhaCungCap"));
                phieuNhapChiTiet.setGiaNhap(rs.getFloat("giaNhap"));
                phieuNhapChiTiet.setSoLuong(rs.getInt("soLuong"));
                phieuNhapChiTiet.setTrangThai(rs.getBoolean("trangThai"));
                phieuNhapChiTiet.setNgayNhap(rs.getDate("ngayNhap"));

                // Tạo đối tượng NhapKhoChiTiet và thêm vào danh sách kết quả
                NhapKhoChiTiet nhapKhoChiTiet = new NhapKhoChiTiet(nhapKho, phieuNhapChiTiet);
                result.add(nhapKhoChiTiet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
   
    public void InsertOrUpdateProcedure(
        String maPNCT, String maNhapKho, Date ngayNhap, boolean trangThai,
        String maNhanVien, String maSanPham, String maNhaCungCap, float giaNhap, int soLuong) {

        String sql = "{call sp_InsertOrUpdateNhapKho (?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement stmt = connection.prepareCall(sql)) {
            // Thiết lập các tham số cho stored procedure
            stmt.setString(1, maPNCT);
            stmt.setString(2, maNhapKho);
            stmt.setDate(3, (java.sql.Date) ngayNhap);
            stmt.setBoolean(4, trangThai);
            stmt.setString(5, maNhanVien);
            stmt.setString(6, maSanPham);
            stmt.setString(7, maNhaCungCap);
            stmt.setFloat(8, giaNhap);
            stmt.setInt(9, soLuong);

            // Ghi log câu lệnh SQL để kiểm tra
            System.out.println("Executing stored procedure with parameters:");
            System.out.println("maPNCT: " + maPNCT);
            System.out.println("maNhapKho: " + maNhapKho);
            System.out.println("ngayNhap: " + ngayNhap);
            System.out.println("trangThai: " + trangThai);
            System.out.println("maNhanVien: " + maNhanVien);
            System.out.println("maSanPham: " + maSanPham);
            System.out.println("maNhaCungCap: " + maNhaCungCap);
            System.out.println("giaNhap: " + giaNhap);
            System.out.println("soLuong: " + soLuong);

            // Thực thi stored procedure
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            System.out.println("Lỗi khi thực thi stored procedure:");
            e.printStackTrace();
        }
    }
    public String getTenSanPham(String maSanPham) throws SQLException{
        String tenSanPham = "";
String sql = "SELECT tenSanPham FROM SanPham WHERE maSanPham = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, maSanPham);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                tenSanPham = rs.getString("tenSanPham");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return tenSanPham;
    }

    
    public void DeleteProce(String maCTPN, String maSanPham){
        String sql = "{call sp_DeleteNhapKho (?, ?)}";

        try (CallableStatement stmt = connection.prepareCall(sql)) {
            // Thiết lập tham số cho stored procedure
            stmt.setString(1, maCTPN);
            stmt.setString(2, maSanPham);

            // Thực thi stored procedure
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            

        } catch (SQLException e) {
            System.out.println("Lỗi khi thực thi stored procedure:");
            e.printStackTrace();
        }
    }




}
