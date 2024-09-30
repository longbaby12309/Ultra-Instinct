package com.org.ultrainstinct.dao;

import java.sql.SQLException;
import java.util.List;

import com.org.ultrainstinct.model.HoaDon;
import com.org.ultrainstinct.model.HoaDonChiTiet;
import com.org.ultrainstinct.model.SanPham;

/**
 * <p>
 * HoaDonDAO interface implements CRUDDao interface.
 * </p>
 *
 * @author MinhNgoc.
 */
public interface HoaDonDAO extends CRUDDao<HoaDon, Long> {

    /**
     * <p>
     * Method get max MaHoaDon.
     * </p>
     *
     * @return long.
     * @author MinhNgoc.
     */
    long getMaxMaSanPham() throws SQLException;

    /**
     * <p>
     * Save HoaDon with List of HoaDonChiTiet.
     * </p>
     *
     * @param hoaDon HoaDon.
     * @param hoaDonChiTietList of HoaDonChiTiet.
     * @author MinhNgoc
     * @throws SQLException
     */
    void save(HoaDon hoaDon, List<HoaDonChiTiet> hoaDonChiTietList) throws SQLException;

    List<HoaDon> selectByKeyword(String keyword);
    
    public HoaDon findById(String maHoaDon);
    List<HoaDonChiTiet> findHoaDonChiTietByMaHoaDon(String maHoaDon);
    List<SanPham> findTopSellingProducts(int limit) throws SQLException;
    void updateSoLuongHDCT(List<HoaDonChiTiet> hoaDonChiTietList) throws SQLException;
}
