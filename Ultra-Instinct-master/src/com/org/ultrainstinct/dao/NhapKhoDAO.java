package com.org.ultrainstinct.dao;

import java.sql.SQLException;
import java.util.List;

import com.org.ultrainstinct.model.NhapKho;
import com.org.ultrainstinct.model.PhieuNhapChiTiet;

/**
 * <p>
 * NhapKhoDAO interface implements CRUDDao interface.
 * </p>
 *
 * @author MinhNgoc.
 */
public interface NhapKhoDAO extends CRUDDao<NhapKho, Long> {

    /**
     * <p>
     * Method get max NhapKho.
     * </p>
     *
     * @return long.
     * @author MinhNgoc.
     */
    long getMaxMaNhapKho() throws SQLException;

    /**
     * <p>
     * Save HoaDon with List of HoaDonChiTiet.
     * </p>
     *
     * @param nhapKho NhapKho.
     * @param PhieuNhapChiTiet of PhieuNhapChiTiet.
     * @author MinhNgoc
     * @throws SQLException 
     */
    void save(NhapKho nhapKho, List<PhieuNhapChiTiet> phieuNhapChiTietList) throws SQLException;
//
//    /**
//     * <p>
//     * Method searchSanPham.
//     * </p>
//     *
//     * @param request SearchSanPhamRequest
//     * @return List of SanPham.
//     * @author MinhNgoc
//     */
//    List<SanPham> searchSanPham(SearchSanPhamRequest request);
}
