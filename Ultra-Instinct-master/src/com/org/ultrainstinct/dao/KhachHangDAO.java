package com.org.ultrainstinct.dao;

import com.org.ultrainstinct.model.DiaChi;
import com.org.ultrainstinct.model.KhachHang;

import java.sql.SQLException;
import java.util.List;

public interface KhachHangDAO extends CRUDDao<KhachHang, Long> {

    /**
     * <p>
     * Method get max MaKhachHang.
     * </p>
     *
     * @return long.
     * @throws SQLException
     */
    long getMaxMaKhachHang() throws SQLException;

    KhachHang findByMaKhachHang(String maKhachHang) throws SQLException;

    void insert(KhachHang kh) throws SQLException;

    void update(KhachHang kh) throws SQLException;

    void deleteById(String maKhachHang) throws SQLException;

    void deleteDiaChiByKhachHangId(String maKhachHang) throws SQLException;

    /**
     * <p>
     * Method calculate total sales for a customer.
     * </p>
     *
     * @param maKhachHang the customer ID.
     * @return double total sales.
     * @throws SQLException
     */

    double tongBanHang(String maKhachHang) throws SQLException;

    List<KhachHang> selectByKeyword(String keyword) throws SQLException;

    String getDiaChi(String maKhachHang) throws SQLException;

    void saveKhachHangAndDiaChi(KhachHang khachHang, DiaChi diaChi) throws SQLException;

    void updateDiaChi(String maKhachHang, String diaChi, boolean diaChiMacDinh) throws SQLException;

    void deleteDiaChi(String maKhachHang) throws SQLException;

    public List<DiaChi> findDiaChiByMaKhachHang(String maKhachHang) throws SQLException;
}
