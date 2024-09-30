package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.chart.ModelChart;
import com.org.ultrainstinct.dao.SanPhamDAO;
import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.model.SanPham;
import com.org.ultrainstinct.dto.SearchSanPhamDto;
import com.org.ultrainstinct.utils.Constant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * SanPhamServiceImpl represents a concrete implementation of SanPhamDAO.
 * </p>
 *
 * @author MinhNgoc
 */
public class SanPhamDAOImpl extends AbstractCrudDao<SanPham, Long> implements SanPhamDAO {
    
private static final Logger LOGGER = Logger.getLogger(SanPhamDAOImpl.class.getName());
    /**
     * <p>
     * Method mapRow SanPham
     * </p>
     *
     * @param rs ResultSet
     * @return SanPham
     * @throws SQLException SQLException
     * @author MinhNgoc
     */
    @Override
    protected SanPham mapRow(ResultSet rs) throws SQLException {
        return SanPham.builder()
            .sanPhamNo(rs.getLong(Constant.SAN_PHAM_NO))
            .maSanPham(rs.getString("maSanPham"))
            .tenSanPham(rs.getString("tenSanPham"))
            .giaNiemYet(rs.getFloat("giaNiemYet"))
            .soLuongTon(rs.getInt("soLuongTon"))
            .hinh(rs.getString("hinh"))
            .loaiSanPham(rs.getString("maLoaiSanPham"))
            .build();
    }

    /**
     * <p>
     * Method getTableName table SanPham.
     * </p>
     *
     * @return String.
     * @author MinhNgoc.
     */
    @Override
    protected String getTableName() {
        return Constant.SAN_PHAM_TABLE_NAME;
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
        return Constant.SAN_PHAM_NO;
    }

    /**
     * <p>
     * Method getEntityValues.
     * </p>
     *
     * @param entity SanPham.
     * @return Object[].
     * @author MinhNgoc.
     */
    @Override
    protected Object[] getEntityValues(SanPham entity) {
        return new Object[]{
            entity.getMaSanPham(),
            entity.getLoaiSanPham(),
            entity.getTenSanPham(),
            entity.getGiaNiemYet(),
            entity.getSoLuongTon(),
            entity.getHinh(),
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
            """ + Constant.SAN_PHAM_TABLE_NAME + """
            (maSanPham, maLoaiSanPham, tenSanPham, giaNiemYet, soLuongTon, hinh) 
            values (?, ?, ?, ?, ?, ?);
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
        return "UPDATE " + Constant.SAN_PHAM_TABLE_NAME +
            " SET maSanPham = ?, maLoaiSanPham = ?, tenSanPham = ?, giaNiemYet = ?, soLuongTon = ?, hinh = ?  WHERE "
            + Constant.SAN_PHAM_NO + " = ?";
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
    public long getMaxMaSanPham() throws SQLException {
        String sql = " SELECT COUNT(1) MAX_MA_SANPHAM FROM " + getTableName();
        PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getLong("MAX_MA_SANPHAM");
        }
        return 0L;
    }

    /**
     * <p>
     * Method searchSanPham.
     * </p>
     *
     * @param dto SearchSanPhamDto.
     * @return List of SanPham.
     * @author MinhNgoc.
     */
    @Override
    public List<SanPham> searchSanPham(SearchSanPhamDto dto) {
        StringBuilder sql = new StringBuilder("""
            SELECT SanPhamNo
                 , maSanPham
                 , maLoaiSanPham
                 , tenSanPham
                 , giaNiemYet
                 , soLuongTon
                 , hinh 
            FROM dbo.SanPham
            WHERE 1=1
            """);

        List<Object> params = new ArrayList<>();

        if (StringUtils.isNotBlank(dto.getMaSanPham())) {
            sql.append(" AND maSanPham = ? ");
            params.add(dto.getMaSanPham());
        }

        if (StringUtils.isNotBlank(dto.getTenSanPham())) {
            sql.append(" AND tenSanPham LIKE ? ");
            params.add("%" + dto.getTenSanPham() + "%");
        }

        if (StringUtils.isNotBlank(dto.getLoaiSanPham())) {
            sql.append(" AND maLoaiSanPham = ? ");
            params.add(dto.getLoaiSanPham());
        }

        if (ObjectUtils.isNotEmpty(dto.getGiaNiemYet())) {
            sql.append(" AND giaNiemYet = ? ");
            params.add(dto.getLoaiSanPham());
        }

        if (ObjectUtils.isNotEmpty(dto.getSoLuongTon())) {
            sql.append(" AND soLuongTon = ? ");
            params.add(dto.getLoaiSanPham());
        }

        if (StringUtils.isNotBlank(dto.getFreeText())) {
            sql.append("""
                AND (maSanPham = ? 
                     OR tenSanPham LIKE ? 
                     OR maLoaiSanPham = ? 
                     OR giaNiemYet = ? 
                     OR soLuongTon = ?) 
                """);
            params.add(dto.getFreeText());
            params.add("%" + dto.getFreeText() + "%");
            params.add(dto.getFreeText());
            params.add(dto.getFreeText());
            params.add(dto.getFreeText());
        }

        List<SanPham> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public List<SanPham> selectByKeyword(String keyword) {
        String sql = """
            SELECT * FROM SanPham 
            WHERE maSanPham LIKE ? 
            OR maLoaiSanPham LIKE ? 
            OR tenSanPham LIKE ? 
            OR giaNiemYet LIKE ?
        """;
        String wildcardKeyword = "%" + keyword + "%";

        return select(sql, wildcardKeyword, wildcardKeyword, wildcardKeyword, wildcardKeyword);
    }

    private List<SanPham> select(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to execute query.", e);
        }
        return list;
    }
    @Override
    public List<SanPham> getBestSellingProducts(int limit) {
        String sql = """
        SELECT TOP (?) * FROM SanPham
        ORDER BY soLuongBan DESC
        """;
    List<SanPham> bestSellingProducts = new ArrayList<>();
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, limit);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            bestSellingProducts.add(mapRow(rs));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to fetch best-selling products: " + e.getMessage());
    }
    return bestSellingProducts;
    }
    
    @Override
    public SanPham findById(String maSanPham) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE maSanPham = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maSanPham);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Hoặc ném ngoại lệ nếu cần
    }
  
///////của taoooo đừng động vô

    @Override
    public void deleteById(String maSanPham) {
        String sql = "DELETE FROM " + getTableName() + " WHERE maSanPham = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maSanPham);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Xóa sản phẩm thất bại: " + e.getMessage());
        }
    }

    @Override
    public void insert(SanPham sanPham) throws SQLException {
        String sql = getInsertQuery();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            Object[] entityValues = getEntityValues(sanPham);
            for (int i = 0; i < entityValues.length; i++) {
                stmt.setObject(i + 1, entityValues[i]);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Thêm sản phẩm thất bại: " + e.getMessage());
        }
    }

    @Override
    public void update(SanPham sanPham) throws SQLException {
        String sql = "UPDATE sanpham SET TenSanPham = ?, GiaNiemYet = ?, SoLuongTon = ?, Hinh = ? WHERE MaSanPham = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, sanPham.getTenSanPham());
            pst.setFloat(2, sanPham.getGiaNiemYet());
            pst.setInt(3, sanPham.getSoLuongTon());
            pst.setString(4, sanPham.getHinh());
            pst.setString(5, sanPham.getMaSanPham());
            pst.executeUpdate();
        }
    }
    @Override
    public List<SanPham> selectByLoai(String loai) {
        String sql = "SELECT * FROM SanPham WHERE maLoaiSanPham = ?";
        return select(sql, loai);
    }
}
