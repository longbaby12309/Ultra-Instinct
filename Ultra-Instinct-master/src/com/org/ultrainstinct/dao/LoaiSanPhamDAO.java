package com.org.ultrainstinct.dao;

import java.sql.SQLException;
import com.org.ultrainstinct.model.LoaiSanPham;

/**
 * <p>
 * LoaiSanPhamDAO interface implements CRUDDao interface.
 * </p>
 *
 * @author MinhNgoc.
 */
public interface LoaiSanPhamDAO extends CRUDDao<LoaiSanPham, Long> {

    /**
     * <p>
     * Method get max MaLoaiSanPham.
     * </p>
     *
     * @return long.
     * @author MinhNgoc.
     */
    long getMaxMaLoaiSanPham() throws SQLException;
}
