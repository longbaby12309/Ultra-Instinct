package com.org.ultrainstinct.dao;

import java.sql.SQLException;

import com.org.ultrainstinct.model.NhanVien;
import java.util.List;

/**
 * <p>
 * NhanVienDAO interface implements CRUDDao interface.
 * </p>
 *
 * @author MinhNgoc.
 */
public interface NhanVienDAO extends CRUDDao<NhanVien, Long> {

    /**
     * <p>
     * Method get max MaNhanVien.
     * </p>
     *
     * @return long.
     * @author MinhNgoc.
     */
   long getMaxMaNhanVien() throws SQLException;
    NhanVien findById(String maNhanVien) throws SQLException;
    List<NhanVien> findByKeyword(String keyword) throws SQLException;
    void insert(NhanVien nhanVien) throws SQLException;
    void update(NhanVien nhanVien) throws SQLException;
    void deleteById(String maNhanVien) throws SQLException;
}
