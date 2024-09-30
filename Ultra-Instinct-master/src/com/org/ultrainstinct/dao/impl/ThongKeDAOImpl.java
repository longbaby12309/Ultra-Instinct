/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.chart.ModelChart;
import com.org.ultrainstinct.config.SqlConfig;
import static com.org.ultrainstinct.dao.AbstractCrudDao.connection;
import com.org.ultrainstinct.dao.ThongKeDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Minh Ngoc
 */
public class ThongKeDAOImpl implements ThongKeDAO {
      @Override
    public int getFirstInvoiceYear() throws SQLException {
        String sql = "SELECT MIN(YEAR(ngayLap)) AS firstYear FROM HoaDon";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("firstYear");
            } else {
                throw new SQLException("Không tìm thấy năm hóa đơn đầu tiên");
            }
        }
    }
    

    public List<Object[]> getDoanhThu() {
        List<Object[]> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String sql = "{call sp_ThongKeDoanhThuTheoSanPham}";
            rs = SqlConfig.executeQuery(sql);
            while (rs.next()) {
                Object[] model = {
                    rs.getString("MaSanPham"),
                    rs.getString("TenSanPham"),
                    rs.getString("Maloai"),
                    rs.getInt("SoLuongBan"),
                    rs.getDouble("DoanhThu")
                };
                list.add(model);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

  public List<Object[]> getDoanhThuforYear(int year) {
    List<Object[]> list = new ArrayList<>();
    ResultSet rs = null;

    String sql = "{call sp_ThongKeDoanhThutheoNam(?)}";
    try (
         PreparedStatement stmt = connection.prepareStatement(sql)) {
        
        stmt.setInt(1, year);
        
        // Execute the query
        rs = stmt.executeQuery();

        // Process the result set
        while (rs.next()) {
            Object[] model = {
                rs.getString("MaSanPham"),
                rs.getString("TenSanPham"),
                rs.getString("Maloai"),
                rs.getInt("SoLuongBan"),
                rs.getDouble("DoanhThu")
            };
            list.add(model);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } finally {
        // Close ResultSet
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return list;
}
@Override
public List<Integer> getYearinHoaDon() throws SQLException {
    String sql = "SELECT DISTINCT YEAR(ngayLap) AS year FROM HoaDon";
    List<Integer> years = new ArrayList<>();
    
    try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            years.add(rs.getInt("year"));
        }
    } catch (SQLException e) {
        // Log the exception (consider using a logging framework)
        System.err.println("SQL Exception: " + e.getMessage());
        throw e; // Rethrow the exception after logging
    }
    
    return years;
}
    public List<Object[]> getOrderStatisticsForYear(int year) {
        List<Object[]> list = new ArrayList<>();
        String sql = "{call sp_GetOrderStatisticsByYear(?)}";

        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("Nam"),
                        rs.getInt("SoDonThanhCong"),
                        rs.getInt("SoDonHuy"),
                        rs.getDouble("TongBan")
                    };
                    list.add(row);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
   
     public List<Object[]> searchDoanhThuByKeyword(String keyword) {
    List<Object[]> list = new ArrayList<>();
    String sql = "SELECT sp.maSanPham, sp.tenSanPham, lsp.maLoaiSanPham, SUM(hdct.soLuong), SUM(hdct.giaBan * hdct.soLuong) " +
                 "FROM SanPham sp " +
                 "JOIN LoaiSanPham lsp ON sp.maLoaiSanPham = lsp.maLoaiSanPham " +
                 "JOIN HoaDonChiTiet hdct ON sp.maSanPham = hdct.maSanPham " +
                 "JOIN HoaDon hd ON hdct.maHoaDon = hd.maHoaDon " +
                 "WHERE sp.maSanPham LIKE ? OR sp.tenSanPham LIKE ? " +
                 "GROUP BY sp.maSanPham, sp.tenSanPham, lsp.maLoaiSanPham";
    
    try (
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, "%" + keyword + "%");
        pstmt.setString(2, "%" + keyword + "%");
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString(1), 
                    rs.getString(2), 
                    rs.getString(3), 
                    rs.getInt(4), 
                    rs.getDouble(5)
                });
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
public List<Object[]> getDoanhThuByProductType(String productType) {
    List<Object[]> list = new ArrayList<>();
    String sql = "SELECT sp.maSanPham, sp.tenSanPham, lsp.maLoaiSanPham, SUM(hdct.soLuong), SUM(hdct.giaBan * hdct.soLuong) " +
                 "FROM SanPham sp " +
                 "JOIN LoaiSanPham lsp ON sp.maLoaiSanPham = lsp.maLoaiSanPham " +
                 "JOIN HoaDonChiTiet hdct ON sp.maSanPham = hdct.maSanPham " +
                 "JOIN HoaDon hd ON hdct.maHoaDon = hd.maHoaDon " +
                 "WHERE lsp.maLoaiSanPham = ? " +
                 "GROUP BY sp.maSanPham, sp.tenSanPham, lsp.maLoaiSanPham";
    
    try (
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, productType);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString(1), 
                    rs.getString(2), 
                    rs.getString(3), 
                    rs.getInt(4), 
                    rs.getDouble(5)
                });
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
 public static List<ModelChart> fetchData() {
        List<ModelChart> data = new ArrayList<>();
        int currentYear = Year.now().getValue(); // Get the current year

        String sql = "SELECT month(hd.ngayLap) AS month, " +
                     "SUM(hdct.giaBan * hdct.soLuong) AS doanhThu, " +
                     "SUM(pnct.giaNhap * pnct.soLuong) AS chiPhi " +
                     "FROM HoaDonChiTiet hdct " +
                     "JOIN HoaDon hd ON hdct.maHoaDon = hd.maHoaDon " +
                     "JOIN SanPham sp ON hdct.maSanPham = sp.maSanPham " +
                     "LEFT JOIN PhieuNhapChiTiet pnct ON sp.maSanPham = pnct.maSanPham " +
                     "WHERE YEAR(hd.ngayLap) = ? " + // Filter by current year
                     "GROUP BY month(hd.ngayLap)";

        try (
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, currentYear); // Set the current year parameter
            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int month = rs.getInt("month");
                    double doanhThu = rs.getDouble("doanhThu");
                    double chiPhi = rs.getDouble("chiPhi");
                    double loiNhuan = doanhThu - chiPhi;
                    data.add(new ModelChart("Tháng " + month, new double[]{doanhThu, chiPhi, loiNhuan}));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
      public static List<ModelChart> fetchDataKH_DH() {
        List<ModelChart> data = new ArrayList<>();
        int currentYear = Year.now().getValue(); // Get the current year
        String sql = "SELECT MONTH(hd.ngayLap) AS month, " +
                     "COUNT(DISTINCT hd.maKhachHang) AS soLuongKhachHang, " +
                     "SUM(CASE WHEN hd.trangThai = 0 THEN 1 ELSE 0 END) AS soDonThanhCong, " +
                     "SUM(CASE WHEN hd.trangThai = 1 THEN 1 ELSE 0 END) AS soDonHuy, " +
                     "SUM(hdct.soLuong) AS soLuongSanPhamBanDuoc " +
                     "FROM HoaDon hd " +
                     "LEFT JOIN HoaDonChiTiet hdct ON hd.maHoaDon = hdct.maHoaDon " +
                     "WHERE  YEAR(hd.ngayLap) = ? " + // Filter by current year
                     "GROUP BY month(hd.ngayLap)";

        try (
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, currentYear); // Set the current year parameter
            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int month = rs.getInt("month");
                    int soLuongKhachHang = rs.getInt("soLuongKhachHang");
                    int soDonThanhCong = rs.getInt("soDonThanhCong");
                    int soDonHuy = rs.getInt("soDonHuy");
                    int soLuongSanPhamBanDuoc = rs.getInt("soLuongSanPhamBanDuoc");
                    data.add(new ModelChart("Tháng " + month, new double[]{soLuongKhachHang, soDonThanhCong, soDonHuy, soLuongSanPhamBanDuoc}));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
