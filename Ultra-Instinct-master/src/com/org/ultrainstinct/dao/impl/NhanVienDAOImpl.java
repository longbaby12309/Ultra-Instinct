package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.NhanVienDAO;
import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.model.NhanVien;
import com.org.ultrainstinct.utils.Constant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAOImpl extends AbstractCrudDao<NhanVien, Long> implements NhanVienDAO {

    @Override
    protected NhanVien mapRow(ResultSet rs) throws SQLException {
        return NhanVien.builder()
            .nhanVienNo(rs.getLong(Constant.NHAN_VIEN_NO))
            .maNhanVien(rs.getString("maNhanVien"))
            .tenNhanVien(rs.getString("tenNhanVien"))
            .hoNhanVien(rs.getString("hoNhanVien"))
            .matKhau(rs.getString("matKhau"))
            .soDienThoai(rs.getString("soDienThoai"))
            .email(rs.getString("email"))
            .chucVu(rs.getString("chucVu"))
            .trangThaiXoa(rs.getBoolean("trangThaiXoa"))
            .nguoiTao(rs.getString("nguoiTao"))
            .thoiGianTao(rs.getDate("thoiGianTao"))
            .build();
    }

    @Override
    protected String getTableName() {
        return Constant.NHAN_VIEN_TABLE_NAME;
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return Constant.NHAN_VIEN_NO;
    }

    @Override
    protected Object[] getEntityValues(NhanVien entity) {
        return new Object[]{
            entity.getMaNhanVien(),
            entity.getTenNhanVien(),
            entity.getHoNhanVien(),
            entity.getMatKhau(),
            entity.getSoDienThoai(),
            entity.getEmail(),
            entity.getChucVu(),
            entity.isTrangThaiXoa(),
            entity.getNguoiTao(),
            entity.getThoiGianTao()
        };
    }

    @Override
    protected String getInsertQuery() {
        return """
            INSERT INTO """ + Constant.NHAN_VIEN_TABLE_NAME + """
            (maNhanVien, tenNhanVien, hoNhanVien, matKhau, soDienThoai, email, chucVu, trangThaiXoa, nguoiTao, thoiGianTao) 
            values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;
    }

    @Override
    protected String getUpdateQuery() {
        return """
            UPDATE """ + Constant.NHAN_VIEN_TABLE_NAME + """
            SET maNhanVien = ?, tenNhanVien = ?, hoNhanVien = ?, matKhau = ?, 
            soDienThoai = ?, email = ?, chucVu = ?, trangThaiXoa = ?, nguoiTao = ?, thoiGianTao = ?  
            WHERE """ + Constant.NHAN_VIEN_NO + " = ? ";
    }

    @Override
    public long getMaxMaNhanVien() throws SQLException {
        String sql = "SELECT COUNT(1) AS MAX_MA_NHANVIEN FROM " + getTableName();
        try (PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("MAX_MA_NHANVIEN");
            }
        }
        return 0L;
}

    @Override
    public List<NhanVien> findByKeyword(String keyword) throws SQLException {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE tenNhanVien LIKE ? OR hoNhanVien LIKE ?";
        try (PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NhanVien nv = mapRow(rs);
                list.add(nv);
            }
        }
        return list;
    }
    
    @Override
    public NhanVien findById(String maNhanVien) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE maNhanVien = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maNhanVien);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void deleteById(String maNhanVien) {
        String sql = "DELETE FROM " + getTableName() + " WHERE maNhanVien = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maNhanVien);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Xóa nhân viên thất bại: " + e.getMessage());
        }
    }

    @Override
   public void insert(NhanVien nv) throws SQLException {
    String sql = "INSERT INTO NhanVien (MaNhanVien, TenNhanVien, HoNhanVien, MatKhau, SoDienThoai, Email, ChucVu, ThoiGianTao, NguoiTao, trangThaiXoa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, nv.getMaNhanVien());
        ps.setString(2, nv.getTenNhanVien());
        ps.setString(3, nv.getHoNhanVien());
        ps.setString(4, nv.getMatKhau());
        ps.setString(5, nv.getSoDienThoai());
        ps.setString(6, nv.getEmail());
        ps.setString(7, nv.getChucVu());

        if (nv.getThoiGianTao() != null) {
            ps.setDate(8, new java.sql.Date(nv.getThoiGianTao().getTime()));
        } else {
            ps.setNull(8, java.sql.Types.DATE);
        }

        ps.setString(9, nv.getNguoiTao());
        
        // Thiết lập giá trị cho cột trangThaiXoa
        ps.setBoolean(10, nv.isTrangThaiXoa());

        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Thêm nhân viên thất bại: " + e.getMessage());
    }
}



    @Override
    public void update(NhanVien nv) throws SQLException {
String sql = "UPDATE NhanVien SET TenNhanVien=?, HoNhanVien=?, MatKhau=?, SoDienThoai=?, Email=?, ChucVu=?, ThoiGianTao=?, NguoiTao=? WHERE MaNhanVien=?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, nv.getTenNhanVien());
        ps.setString(2, nv.getHoNhanVien());
        ps.setString(3, nv.getMatKhau());
        ps.setString(4, nv.getSoDienThoai());
        ps.setString(5, nv.getEmail());
        ps.setString(6, nv.getChucVu());
        ps.setDate(7, new java.sql.Date(nv.getThoiGianTao().getTime()));
        ps.setString(8, nv.getNguoiTao());
        ps.setString(9, nv.getMaNhanVien());

        ps.executeUpdate();
    }
}
    
    public List<NhanVien> getAll(){
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNhanVien"));
                nv.setTenNhanVien(rs.getString("tenNhanVien"));
                nv.setHoNhanVien(rs.getString("hoNhanVien"));
                nv.setMatKhau(rs.getString("matKhau"));
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setEmail(rs.getString("email"));
                nv.setChucVu(rs.getString("chucVu"));
                nv.setTrangThaiXoa(rs.getBoolean("trangThaiXoa"));
                nv.setNguoiTao(rs.getString("nguoiTao"));
                nv.setThoiGianTao(rs.getDate("thoiGianTao"));
                
                list.add(nv);
            }
        } catch (Exception e) {
        }
        return list;
    }

}