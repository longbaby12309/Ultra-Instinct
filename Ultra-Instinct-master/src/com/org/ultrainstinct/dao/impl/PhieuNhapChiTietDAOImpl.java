/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.dao.PhieuNhapChiTietDAO;
import com.org.ultrainstinct.model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.org.ultrainstinct.utils.Constant;
import java.util.List;
/**
 *
 * @author Admin
 */
public class PhieuNhapChiTietDAOImpl extends AbstractCrudDao<PhieuNhapChiTiet, Long> implements PhieuNhapChiTietDAO{

    @Override
    protected PhieuNhapChiTiet mapRow(ResultSet rs) throws SQLException {
        return PhieuNhapChiTiet.builder().PNCTNo(rs.getLong(Constant.PHIEU_NHAP_CHI_TIET_NO)).maPNCT(rs.getString("maPNCT")).maSanPham(rs.getString("maSanPham")).maNhapKho(rs.getString("maNhapKho")).maNhaCungCap(rs.getString("maNhaCungCap")).giaNhap(rs.getFloat("giaNhap")).soLuong(rs.getInt("soLuong")).build();
    }

    @Override
    protected String getTableName() {
        return Constant.PHIEU_NHAP_CHI_TIET_TABLE_NAME;
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return Constant.PHIEU_NHAP_CHI_TIET_NO;
    }

    @Override
    protected Object[] getEntityValues(PhieuNhapChiTiet entity) {
        return new Object[]{
            entity.getPNCTNo(),
            entity.getMaPNCT(),
            entity.getMaNhapKho(),
            entity.getMaNhaCungCap(),
            entity.getGiaNhap(),
            entity.getSoLuong()
        };
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO PhieuNhapChiTiet ( maPNCT, maSanPham, maNhapKho, maNhaCungCap, giaNhap, soLuong) VALUES(?, ?, ?, ?, ?, ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return """
               UPDATE PhieuNhapChiTiet 
               SET maSanPham = ?,
               \tmaNhapKho = ?,
               \tmaNhaCungCap = ?,
               \tgiaNhap = ?,
               \tsoLuong = ?
               WHERE maPNCT = ?;""";
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }


//    @Override
//    public List<PhieuNhapChiTiet> findAll() {
//        String sql = "SELECT * FROM " + getTableName();
//        try (PreparedStatement stmt = connection.prepareStatement(sql)instanceof ;
//             ResultSet rs = stmt.executeQuery()) {
//            List<PhieuNhapChiTiet> list = new ArrayList<>();
//            while (rs.next()) {
//                list.add(mapRow(rs));
//            }
//            return list;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error finding all PhieuNhapChiTiet", e);
//        }
//    }
//    @Override
//    public PhieuNhapChiTiet findById(Long id) {
//        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getPrimaryKeyColumnName() + " = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setLong(1, id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return mapRow(rs);
//                }
//                return null;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error finding PhieuNhapChiTiet by ID", e);
//        }
//    }
//
//    @Override
//    public void update(PhieuNhapChiTiet entity, Long id) {
//        String sql = getUpdateQuery();
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            int index = 1;
//            stmt.setString(index++, entity.getMaSanPham());
//            stmt.setString(index++, entity.getMaNhapKho());
//            stmt.setString(index++, entity.getMaNhaCungCap());
//            stmt.setFloat(index++, entity.getGiaNhap());
//            stmt.setInt(index++, entity.getSoLuong());
//            stmt.setLong(index, id);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error updating PhieuNhapChiTiet", e);
//        }
//    }
//
//    @Override
//    public void save(PhieuNhapChiTiet entity) {
//        String sql = getInsertQuery();
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, entity.getMaPNCT());
//            stmt.setString(2, entity.getMaSanPham());
//            stmt.setString(3, entity.getMaNhapKho());
//            stmt.setString(4, entity.getMaNhaCungCap());
//            stmt.setFloat(5, entity.getGiaNhap());
//            stmt.setInt(6, entity.getSoLuong());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error saving PhieuNhapChiTiet", e);
//        }
//    }

    
    
    
    
}
