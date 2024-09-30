package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.dao.AuthDAO;
import com.org.ultrainstinct.dto.UserSession;
import com.org.ultrainstinct.utils.PasswordUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.ObjectUtils;

public class AuthDAOImpl implements AuthDAO {

   @Override
public boolean logIn(String userName, String password) throws SQLException {
    String sql = """
            SELECT maNhanVien, matKhau, chucVu, hoNhanVien, tenNhanVien FROM dbo.NhanVien
            WHERE maNhanVien = ? AND matKhau = ?
            """;

    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
        stmt = AbstractCrudDao.connection.prepareStatement(sql);
        stmt.setString(1, userName);
        stmt.setString(2, PasswordUtils.md5(password)); // Mã hóa mật khẩu trước khi so sánh
        rs = stmt.executeQuery();
        if (rs.next()) {
            String fullName = rs.getString("hoNhanVien") + " " + rs.getString("tenNhanVien");
            UserSession.getUser(userName, rs.getString("matKhau"), rs.getString("chucVu"), fullName); // Khởi tạo UserSession với mật khẩu đã mã hóa
            System.out.println("Logged in user: " + userName + ", Role: " + rs.getString("chucVu")); // Debugging line
            return true;
        }
        return false;
    } finally {
        if (rs != null)
            rs.close();
        if (stmt != null)
            stmt.close();
    }
}

    @Override
    public void logOut() {
        UserSession.clear();
    }

    @Override
    public boolean isLogin() {
        UserSession userSession = UserSession.getUser();
        return ObjectUtils.isNotEmpty(userSession) && userSession.isLogin();
    }

    @Override
    public boolean isManager() {
        UserSession userSession = UserSession.getUser();
        return userSession != null && userSession.isManager();
    }

    @Override
    public boolean isSales() {
        UserSession userSession = UserSession.getUser();
        return userSession != null && userSession.isSales();
    }

    @Override
    public boolean isWarehouse() {
        UserSession userSession = UserSession.getUser();
        return userSession != null && userSession.isWarehouse();
    }

    @Override
    public String getEmailByUsername(String username) throws SQLException {
        String sql = "SELECT email FROM dbo.NhanVien WHERE maNhanVien = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = AbstractCrudDao.connection.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
            return null;
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    @Override
    public void updatePassword(String username, String newPassword) throws SQLException {
        String sql = "UPDATE dbo.NhanVien SET matKhau = ? WHERE maNhanVien = ?";
        PreparedStatement stmt = null;
        try {
            stmt = AbstractCrudDao.connection.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }
}
