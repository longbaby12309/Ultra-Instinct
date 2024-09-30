package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.dao.DiaChiDAO;
import com.org.ultrainstinct.model.DiaChi;
import com.org.ultrainstinct.utils.Constant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * DiaChiDAOImpl represents a concrete implementation of DiaChiDAO.
 * </p>
 *
 * @author MinhNgoc.
 */
public class DiaChiDAOImpl extends AbstractCrudDao<DiaChi, Long> implements DiaChiDAO {

    /**
     * <p>
     * Method mapRow DiaChi
     * </p>
     *
     * @param rs ResultSet
     * @return DiaChi
     * @throws SQLException SQLException
     * @author MinhNgoc
     */
    @Override
    protected DiaChi mapRow(ResultSet rs) throws SQLException {
        //option 1 setter
//        DiaChi diaChi = new DiaChi();
//        diaChi.setDiaChiNo(rs.getLong(Constant.DIA_CHI_NO));
//        diaChi.setMaDiaChi(rs.getString("maDiaChi"));
//        return diaChi;
//        option 2 use builder
        return DiaChi.builder()
               .diaChiNo(rs.getLong(Constant.DIA_CHI_NO))
               .maDiaChi(rs.getString("maDiaChi"))
               .diaChi(rs.getString("diaChi"))
               .diaChiMacDinh(rs.getString("diaChiMacDinh"))
               .trangThaiXoa(rs.getBoolean("trangThaiXoa"))
               .maKhachHang(rs.getString("maKhachHang"))
               .build();
    }

    /**
     * <p>
     * Method getTableName table DiaChi.
     * </p>
     *
     * @return String.
     * @author MinhNgoc.
     */
    @Override
    protected String getTableName() {
        return Constant.DIA_CHI_TABLE_NAME;
    }

    /**
     * <p>
     * Method getPrimaryKeyColumnName.
     * </p>
     *
     * @return String.
     * @author MinhNgoc.
     */
    @Override
    protected String getPrimaryKeyColumnName() {
        return Constant.DIA_CHI_NO;
    }

    /**
     * <p>
     * Method getEntityValues.
     * </p>
     * @param entity DiaChi.
     * @return Object[].
     * @author MinhNgoc.
     */
    @Override
    protected Object[] getEntityValues(DiaChi entity) {
        return new Object[]{
            entity.getMaDiaChi(),
            entity.getDiaChi(),
            entity.getDiaChiMacDinh(),
            entity.isTrangThaiXoa(),
            entity.getMaKhachHang(),
        };
    }

    /**
     * <p>
     * Method getInsertQuery.
     * </p>
     * @return String
     * @author MinhNgoc.
     */
    @Override
    protected String getInsertQuery() {
        return Constant.INSERT_INTO + Constant.DIA_CHI_TABLE_NAME +"(maDiaChi, diaChi, diaChiMacDinh, trangThaiXoa, maKhachHang) values (?,?,?,?,?);";
    }

    /**
     * <p>
     * Method getUpdateQuery.
     * </p>
     * @return String.
     * @author MinhNgoc.
     */
    @Override
    protected String getUpdateQuery() {
        return Constant.UPDATE + Constant.DIA_CHI_TABLE_NAME +
             "SET maDiaChi = ?, diaChi = ?, diaChiMacDinh = ?, trangThaiXoa = ?, maKhachHang = ? WHERE "
            + Constant.DIA_CHI_NO + "= ?";
    }

    /**
     * <p>
     * Method get max MaDiaChi
     * </p>
     *
     * @return long maxMaDiaChi
     * @author MinhNgoc
     */
    @Override
    public long getMaxMaDiaChi() throws SQLException {
        String sql = " SELECT COUNT(1) MAX_MA_DIACHI FROM " + getTableName();

        PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getLong("MAX_MA_DIACHI");
        }
        return 0L;
    }
}
