package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.config.SqlConfig;
import com.org.ultrainstinct.dao.UserDao;
import com.org.ultrainstinct.dto.UserSession;
import com.org.ultrainstinct.model.NhanVien;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDao {

    @Override
    public boolean checkCurrentPassword(String maNhanVien, String matKhauCu) {
        String sql = "SELECT matKhau FROM NhanVien WHERE maNhanVien = ?";
        try (PreparedStatement stmt = SqlConfig.prepareStatement(sql, maNhanVien)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String matKhauHienTai = rs.getString("matKhau");
                return matKhauCu.equals(matKhauHienTai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePassword(String maNhanVien, String matKhauMoi) {
        String sql = "UPDATE NhanVien SET matKhau = ? WHERE maNhanVien = ?";
        try (PreparedStatement stmt = SqlConfig.prepareStatement(sql, matKhauMoi, maNhanVien)) {
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserSession getUserDetails(String maNhanVien) {
        String sql = "SELECT maNhanVien, matKhau, vaiTro, hoTen FROM NhanVien WHERE maNhanVien = ?";
        try (PreparedStatement stmt = SqlConfig.prepareStatement(sql, maNhanVien)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return UserSession.builder()
                        .userName(rs.getString("maNhanVien"))
                        .password(rs.getString("matKhau"))
                        .role(rs.getString("vaiTro"))
                        .fullName(rs.getString("hoTen"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public NhanVien findByID(String maNhanVien) {
        NhanVien nv = null;
        String sql = "SELECT * FROM NhanVien WHERE MaNhanVien = ?";
        try (PreparedStatement stmt = SqlConfig.prepareStatement(sql, maNhanVien);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNhanVien"));
                nv.setHoNhanVien(rs.getString("hoNhanVien"));
                nv.setTenNhanVien(rs.getString("tenNhanVien"));
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setEmail(rs.getString("email"));
                // Các trường khác nếu có...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nv;
    }

    @Override
    public void update(NhanVien nv) {
        String sql = "UPDATE NhanVien SET HoNhanVien=?, TenNhanVien=?, SoDienThoai=?, Email=? WHERE MaNhanVien=?";
        try (PreparedStatement stmt = SqlConfig.prepareStatement(sql,
                nv.getHoNhanVien(),
                nv.getTenNhanVien(),
                nv.getSoDienThoai(),
                nv.getEmail(),
                nv.getMaNhanVien())) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating NhanVien: " + e.getMessage());
        }
    }
    
}