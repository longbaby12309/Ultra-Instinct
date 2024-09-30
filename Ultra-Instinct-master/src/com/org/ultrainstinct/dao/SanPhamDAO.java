package com.org.ultrainstinct.dao;

import java.sql.SQLException;
import java.util.List;

import com.org.ultrainstinct.model.SanPham;
import com.org.ultrainstinct.dto.SearchSanPhamDto;
import com.org.ultrainstinct.model.HoaDon;

/**
 * <p>
 * SanPhamDAO interface implements CRUDDao interface.
 * </p>
 *
 * @author MinhNgoc.
 */
public interface SanPhamDAO extends CRUDDao<SanPham, Long> {

    /**
     * <p>
     * Method get max MaSanPham.
     * </p>
     *
     * @return long.
     * @author MinhNgoc.
     */
    long getMaxMaSanPham() throws SQLException;

    /**
     * <p>
     * Method searchSanPham.
     * </p>
     *
     * @param searchSanPhamDto SearchSanPhamDto
     * @return List of SanPham.
     * @author MinhNgoc
     */
    List<SanPham> searchSanPham(SearchSanPhamDto searchSanPhamDto);
    List<SanPham> selectByKeyword(String keyword);

    public SanPham findById(String maSanPham);

    public void deleteById(String maSanPham);
    
    public void insert(SanPham sanPham) throws SQLException;
    
    public void update(SanPham sanPham) throws SQLException;

    public List<SanPham> selectByLoai(String loai);
    
    List<SanPham> getBestSellingProducts(int limit);
}
