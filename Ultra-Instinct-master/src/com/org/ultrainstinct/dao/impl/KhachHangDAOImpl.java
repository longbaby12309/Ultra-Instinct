package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.KhachHangDAO;
import com.org.ultrainstinct.model.KhachHang;
import com.org.ultrainstinct.utils.Constant;
import com.org.ultrainstinct.config.SqlConfig;
import static com.org.ultrainstinct.config.SqlConfig.getConnection;
import com.org.ultrainstinct.dao.AbstractCrudDao;
import static com.org.ultrainstinct.dao.AbstractCrudDao.LOGGER;
import static com.org.ultrainstinct.dao.AbstractCrudDao.connection;
import com.org.ultrainstinct.dto.UserSession;
import com.org.ultrainstinct.helper.DialogHelper;
import com.org.ultrainstinct.model.DiaChi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * <p>
 * KhachHangDAOImpl represents a concrete implementation of KhachHangDAO.
 * </p>
 */
public class KhachHangDAOImpl extends AbstractCrudDao<KhachHang, Long> implements KhachHangDAO {

    @Override
    protected KhachHang mapRow(ResultSet rs) throws SQLException {
        return KhachHang.builder()
                .khachHangNo(rs.getLong(Constant.KHACH_HANG_NO))
                .maKhachHang(rs.getString("maKhachHang"))
                .tenKH(rs.getString("tenKH"))
                .soDienThoai(rs.getString("soDienThoai"))
                .email(rs.getString("email"))
                .ngaySinh(rs.getDate("ngaySinh"))
                .gioiTinh(rs.getBoolean("gioiTinh"))
                .ngayDangKy(rs.getDate("ngayDangKy"))
                .trangThaiKH(rs.getBoolean("trangThaiKH"))
                .trangThaiXoa(rs.getBoolean("trangThaiXoa"))
                .nguoiTao(rs.getString("nguoiTao"))
                .ghiChu(rs.getString("ghiChu"))
                .build();
    }

    @Override
    protected String getTableName() {
        return Constant.KHACH_HANG_TABLE_NAME;
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return Constant.KHACH_HANG_NO;
    }

    @Override
    protected Object[] getEntityValues(KhachHang entity) {
        return new Object[]{
            entity.getMaKhachHang(),
            entity.getTenKH(),
            entity.getSoDienThoai(),
            entity.getEmail(),
            entity.getNgaySinh(),
            entity.isGioiTinh(),
            entity.getNgayDangKy(),
            entity.isTrangThaiKH(),
            entity.isTrangThaiXoa(),
            entity.getNguoiTao(),
            entity.getGhiChu()
        };
    }

    @Override
    protected String getInsertQuery() {
        return """
                INSERT INTO """ + Constant.KHACH_HANG_TABLE_NAME + """
                (maKhachHang, tenKH, soDienThoai, email, ngaySinh, gioiTinh, ngayDangKy, trangThaiKH, trangThaiXoa, nguoiTao, ghiChu)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
    }

    @Override
    protected String getUpdateQuery() {
        return """
                UPDATE """ + getTableName() + """
                SET maKhachHang = ?, tenKH = ?, soDienThoai = ?, email = ?, ngaySinh = ?, gioiTinh = ?, ngayDangKy = ?, trangThaiKH = ?, trangThaiXoa = ?, nguoiTao = ?, ghiChu = ?
                WHERE """ + getPrimaryKeyColumnName() + " = ? ";
    }

    @Override
    public long getMaxMaKhachHang() throws SQLException {
        String sql = "SELECT MAX(maKhachHang) AS maxMaKhachHang FROM " + getTableName();
        try (PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("maxMaKhachHang");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get max MaKhachHang.", e);
        }
        return 0L;
    }

    @Override
    public KhachHang findByMaKhachHang(String maKhachHang) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE maKhachHang = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, maKhachHang);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return mapRow(rs);
            
        }
        return null;
    }

    @Override
    public void insert(KhachHang kh) throws SQLException {
        String sql = "INSERT INTO KhachHang (maKhachHang, tenKH, soDienThoai, email, ngaySinh, gioiTinh, ngayDangKy, trangThaiKH, trangThaiXoa, nguoiTao, ghiChu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = SqlConfig.prepareStatement(sql)) {
            // Get the current user from UserSession
            UserSession currentUser = UserSession.getUser();
            if (currentUser == null) {
                throw new SQLException("Người dùng hiện tại ko có sẵn.");
            }
            String nguoiTao = currentUser.getUserName();
            if (nguoiTao == null || nguoiTao.trim().isEmpty()) {
                throw new SQLException("Tên người dùng ko có sẵn.");
            }

            // Set the values for the prepared statement
            stmt.setString(1, kh.getMaKhachHang());
            stmt.setString(2, kh.getTenKH());
            stmt.setString(3, kh.getSoDienThoai());
            stmt.setString(4, kh.getEmail());
            stmt.setDate(5, new java.sql.Date(kh.getNgaySinh().getTime()));
            stmt.setBoolean(6, kh.isGioiTinh());
            stmt.setDate(7, new java.sql.Date(kh.getNgayDangKy().getTime()));
            stmt.setBoolean(8, kh.isTrangThaiKH());
            stmt.setBoolean(9, kh.isTrangThaiXoa());
            stmt.setString(10, nguoiTao); // Ensure this is not NULL
            stmt.setString(11, kh.getGhiChu()); // Provide a default value if ghiChu is null

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Thêm khách hàng thất bại: " + e.getMessage());
        }
    }

    public void deleteById(String maKhachHang) throws SQLException {
        String sql = "DELETE FROM KhachHang WHERE maKhachHang = ?";
        try (PreparedStatement stmt = SqlConfig.prepareStatement(sql)) {
            stmt.setString(1, maKhachHang);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Xóa khách hàng thất bại: " + e.getMessage());
        }
    }

    public void deleteDiaChiByKhachHangId(String maKhachHang) throws SQLException {
        String sql = "DELETE FROM DiaChi WHERE maKhachHang = ?";
        try (PreparedStatement stmt = SqlConfig.prepareStatement(sql)) {
            stmt.setString(1, maKhachHang);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Xóa địa chỉ khách hàng thất bại: " + e.getMessage());
        }
    }

    @Override
    public void update(KhachHang kh) throws SQLException {
        String sql = "UPDATE KhachHang SET tenKH = ?, soDienThoai = ?, email = ?, ngaySinh = ?, gioiTinh = ?, ngayDangKy = ?, trangThaiKH = ?, trangThaiXoa = ?, nguoiTao = ?, ghiChu = ? WHERE maKhachHang = ?";
        try (PreparedStatement stmt = SqlConfig.prepareStatement(sql)) {
            // Get the current user from UserSession
            UserSession currentUser = UserSession.getUser();
            String nguoiTao = currentUser.getUserName(); // Assuming the userName is used as the creator ID

            // Set the values for the prepared statement
            stmt.setString(1, kh.getTenKH());
            stmt.setString(2, kh.getSoDienThoai());
            stmt.setString(3, kh.getEmail());
            stmt.setDate(4, new java.sql.Date(kh.getNgaySinh().getTime()));
            stmt.setBoolean(5, kh.isGioiTinh());
            stmt.setDate(6, new java.sql.Date(kh.getNgayDangKy().getTime()));
            stmt.setBoolean(7, kh.isTrangThaiKH());
            stmt.setBoolean(8, kh.isTrangThaiXoa());
            stmt.setString(9, nguoiTao); // Set nguoiTao here
            stmt.setString(10, kh.getGhiChu());
            stmt.setString(11, kh.getMaKhachHang()); // Primary key condition

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cập nhật khách hàng thất bại: " + e.getMessage());
        }
    }

    public double tongBanHang(String maKhachHang) throws SQLException {
        String sql = """
            SELECT SUM(hdct.giaBan * hdct.soLuong) AS tongBan
            FROM HoaDon hd
            JOIN HoaDonChiTiet hdct ON hd.maHoaDon = hdct.maHoaDon
            WHERE hd.maKhachHang = ?
            """;
        double tongBan = 0;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            System.out.println("Database connection established.");
            stmt.setString(1, maKhachHang);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tongBan = rs.getDouble("tongBan");
                    if (rs.wasNull()) {
                        tongBan = 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Không tính được tổng bán cho khách hàng: " + maKhachHang);
            e.printStackTrace();
            throw new SQLException("Không tính được tổng bán.", e);
        }
        return tongBan;
    }

    /**
     *
     * @param maKhachHang
     * @return
     * @throws SQLException
     */
    @Override
    public String getDiaChi(String maKhachHang) throws SQLException {
        StringBuilder diaChiStringBuilder = new StringBuilder();
        String sql = "SELECT diaChi, diaChiMacDinh FROM DiaChi WHERE maKhachHang = ? AND trangThaiXoa = 0";
        String diaChiMacDinh = null;
        boolean isDiaChiMacDinh = false;

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKhachHang);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String diaChi = rs.getString("diaChi");
                    String diaChiTempMacDinh = rs.getString("diaChiMacDinh");

                    if (diaChiTempMacDinh != null && !diaChiTempMacDinh.isEmpty()) {
                        diaChiMacDinh = diaChiTempMacDinh;
                        isDiaChiMacDinh = true;
                    } else if (diaChiMacDinh == null) {
                        diaChiMacDinh = diaChi;
                        isDiaChiMacDinh = false;
                    }

                    diaChiStringBuilder.append(diaChi)
                            .append(" (")
                            .append(diaChiTempMacDinh != null && !diaChiTempMacDinh.isEmpty() ? "Default" : "Non-default")
                            .append(")\n");
                }
            }
        }

        return (diaChiMacDinh != null ? diaChiMacDinh : "") + "|" + isDiaChiMacDinh + "|" + diaChiStringBuilder.toString();
    }

    public void saveKhachHangAndDiaChi(KhachHang khachHang, DiaChi diaChi) throws SQLException {
        String insertKhachHangSql = """
        INSERT INTO KhachHang(maKhachHang, tenKH, soDienThoai, email, ngaySinh, gioiTinh, ngayDangKy, trangThaiKH, trangThaiXoa, nguoiTao, ghiChu)
        VALUES (?, ?, ?, ?, ?, ?, DEFAULT, ?, ?, ?, ?);
    """;
        String insertDiaChiSql = """
        INSERT INTO DiaChi(maDiaChi, maKhachHang, diaChi, diaChiMacDinh, trangThaiXoa)
        VALUES (?, ?, ?, ?, 0);
    """;
        String checkKhachHangExistsSql = """
        SELECT COUNT(*) FROM KhachHang WHERE maKhachHang = ?;
    """;

        // Bắt đầu giao dịch
        connection.setAutoCommit(false);

        try (PreparedStatement checkKhachHangStmt = connection.prepareStatement(checkKhachHangExistsSql); PreparedStatement khachHangStmt = connection.prepareStatement(insertKhachHangSql); PreparedStatement diaChiStmt = connection.prepareStatement(insertDiaChiSql)) {

            // Kiểm tra sự tồn tại của KhachHang
            checkKhachHangStmt.setString(1, khachHang.getMaKhachHang());
            try (ResultSet rs = checkKhachHangStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Customer with maKhachHang " + khachHang.getMaKhachHang() + " already exists.");
                }
            }

            // Chèn vào bảng KhachHang
khachHangStmt.setString(1, khachHang.getMaKhachHang());
            khachHangStmt.setString(2, khachHang.getTenKH());
            khachHangStmt.setString(3, khachHang.getSoDienThoai());
            khachHangStmt.setString(4, khachHang.getEmail());
            khachHangStmt.setDate(5, new java.sql.Date(khachHang.getNgaySinh().getTime()));
            khachHangStmt.setBoolean(6, khachHang.isGioiTinh());
            khachHangStmt.setBoolean(7, khachHang.isTrangThaiKH());
            khachHangStmt.setBoolean(8, khachHang.isTrangThaiXoa());
            khachHangStmt.setString(9, khachHang.getNguoiTao());
            khachHangStmt.setString(10, khachHang.getGhiChu());
            khachHangStmt.executeUpdate();

            // Chèn vào bảng DiaChi
            diaChiStmt.setString(1, diaChi.getMaDiaChi());
            diaChiStmt.setString(2, diaChi.getMaKhachHang());
            diaChiStmt.setString(3, diaChi.getDiaChi());
            diaChiStmt.setString(4, diaChi.getDiaChiMacDinh());
            diaChiStmt.executeUpdate();

            // Xác nhận giao dịch
            connection.commit();
        } catch (SQLException e) {
            // Hoàn tác giao dịch trong trường hợp lỗi
            connection.rollback();
            System.err.println("Failed to save customer and address: " + e.getMessage());
            throw new SQLException("Failed to save customer and address", e);
        } finally {
            // Đặt lại auto-commit thành true
            connection.setAutoCommit(true);
        }
    }

    public void updateDiaChi(String maKhachHang, String diaChi, boolean diaChiMacDinh) throws SQLException {
        String sql = "UPDATE DiaChi SET diaChi = ?, diaChiMacDinh = ? WHERE maKhachHang = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, diaChi);
            stmt.setBoolean(2, diaChiMacDinh);
            stmt.setString(3, maKhachHang);
            stmt.executeUpdate();
        }
    }

    public void deleteDiaChi(String maKhachHang) throws SQLException {
        String sql = "DELETE FROM DiaChi WHERE maKhachHang = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maKhachHang);
            stmt.executeUpdate();
        }
    }

    public List<KhachHang> selectByKeyword(String keyword) {
        String sql = """
         SELECT * FROM KhachHang 
         WHERE maKhachHang LIKE ? 
         OR tenKH LIKE ? 
         OR soDienThoai LIKE ? 
         """;
        String wildcardKeyword = "%" + keyword + "%";

        return select(sql, wildcardKeyword, wildcardKeyword, wildcardKeyword);
    }

    private List<KhachHang> select(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            DialogHelper.alert(null, "Không thực hiện được truy vấn. Hello");
        }
        return list;
    }

    @Override
    public List<DiaChi> findDiaChiByMaKhachHang(String maKhachHang) throws SQLException {
        String query = "SELECT * FROM DiaChi WHERE maKhachHang = ?";
        List<DiaChi> diaChiList = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, maKhachHang);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DiaChi diaChi = new DiaChi();
                    diaChi.setMaDiaChi(rs.getString("maDiaChi"));
                    diaChi.setMaKhachHang(rs.getString("maKhachHang"));
                    diaChi.setDiaChi(rs.getString("diaChi"));
                    diaChi.setDiaChiMacDinh(rs.getString("diaChiMacDinh"));
                    diaChi.setTrangThaiXoa(rs.getBoolean("trangThaiXoa"));
                    diaChiList.add(diaChi);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log lỗi
            throw e; // Ném lại ngoại lệ để lớp gọi có thể xử lý
        }
        return diaChiList;
    }

    
   public List<KhachHang> listAll() throws SQLException {
    List<KhachHang> list = new ArrayList<>();
    String sql = "SELECT * FROM KhachHang";

    // Kiểm tra và ghi log tình trạng kết nối
    if (connection == null) {
        LOGGER.log(Level.SEVERE, "Kết nối bị null.");
        throw new SQLException("Kết nối bị null.");
    } else if (connection.isClosed()) {
        LOGGER.log(Level.SEVERE, "Kết nối đã bị đóng.");
        try {
            connection = SqlConfig.getConnection(); // Tạo lại kết nối nếu cần
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Không thể kết nối lại với cơ sở dữ liệu.", e);
            throw e; // Ném ngoại lệ để dừng quá trình nếu không thể kết nối
        }
    } else {
        LOGGER.log(Level.INFO, "Kết nối đang mở và hợp lệ.");
    }

    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            KhachHang kh = new KhachHang();
            kh.setMaKhachHang(rs.getString("maKhachHang"));
            kh.setTenKH(rs.getString("tenKH"));
            kh.setSoDienThoai(rs.getString("soDienThoai"));
            kh.setEmail(rs.getString("email"));
            kh.setNgaySinh(rs.getDate("ngaySinh"));
            kh.setGioiTinh(rs.getBoolean("gioiTinh"));
            kh.setNgayDangKy(rs.getDate("ngayDangKy"));
            kh.setTrangThaiKH(rs.getBoolean("trangThaiKH"));
            kh.setTrangThaiXoa(rs.getBoolean("trangThaiXoa"));
            kh.setNguoiTao(rs.getString("nguoiTao"));
            kh.setGhiChu(rs.getString("ghiChu"));
            list.add(kh);
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Câu lệnh truy vấn bị lỗi.");
        //throw e; // Đảm bảo ném ngoại lệ ra ngoài để thông báo lỗi
    }
    return list;
    }


    
    
}
