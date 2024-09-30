package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.dao.LoaiSanPhamDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.org.ultrainstinct.model.LoaiSanPham;
import com.org.ultrainstinct.utils.Constant;
import com.org.ultrainstinct.utils.MessageDialog;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * LoaiSanPhamDAOImpl represents a concrete implementation of LoaiSanPhamDAO.
 * </p>
 *
 * @author MinhNgoc.
 */
public class LoaiSanPhamDAOImpl extends AbstractCrudDao<LoaiSanPham, Long> implements LoaiSanPhamDAO {

    /**
     * <p>
     * Method mapRow LoaiSanPham.
     * </p>
     *
     * @param rs ResultSet.
     * @return LoaiSanPham.
     * @throws SQLException SQLException.
     * @author MinhNgoc.
     */
    @Override
    protected LoaiSanPham mapRow(ResultSet rs) throws SQLException {
        return LoaiSanPham
               .builder()
               .loaiSanPhamNo(rs.getLong(Constant.LOAI_SAN_PHAM_NO))
               .maLoaiSanPham(rs.getString("maLoaiSanPham"))
               .tenLoai(rs.getString("tenLoai"))
               .tenLoai(rs.getString("tenLoai"))
               .moTa(rs.getString("moTa"))
               .build();
    }

    /**
     * <p>
     * Method getTableName table LoaiSanPham.
     * </p>
     *
     * @return String.
     * @author MinhNgoc.
     */
    @Override
    protected String getTableName() {
        return Constant.LOAI_SAN_PHAM_TABLE_NAME;
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
        return Constant.LOAI_SAN_PHAM_NO;
    }

    /**
     * <p>
     * Method getEntityValues.
     * </p>
     * 
     * @param entity LoaiSanPham.
     * @return Object[].
     * @author MinhNgoc.
     */
    @Override
    protected Object[] getEntityValues(LoaiSanPham entity) {
        return new Object[] {
            entity.getMaLoaiSanPham(),
            entity.getTenLoai(),
            entity.getMoTa()
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
                """ + Constant.LOAI_SAN_PHAM_TABLE_NAME + """
                (maLoaiSanPham, tenLoai, moTa)
                values (?,?,?);
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
                """ + Constant.LOAI_SAN_PHAM_TABLE_NAME +
                " SET maLoaiSanPham = ?, tenLoai = ?, moTa = ? WHERE "
                + Constant.LOAI_SAN_PHAM_NO + " = ?";
    }

    /**
     * <p>
     * Method get max maxMaSanPham.
     * </p>
     *
     * @return long maxMaSanPham.
     * @author MinhNgoc.
     */
    @Override
    public long getMaxMaLoaiSanPham() throws SQLException {
        String sql = " SELECT COUNT(1) MAX_MA_LOAI_SAN_PHAM FROM " + getTableName();

        PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getLong("MAX_MA_LOAI_SAN_PHAM");
        }
        return 0L;
    }
    
     public Map<String, String> getProductTypes() {
    Map<String, String> productTypes = new HashMap<>();
    String sql = "SELECT maLoaiSanPham,  tenLoai FROM LoaiSanPham";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            productTypes.put(rs.getString("maLoaiSanPham"), rs.getString("tenLoai"));
        }
    } catch (SQLException e) {
        MessageDialog.alert(null, "Failed to get product types.");
    }
    return productTypes;
}

}
