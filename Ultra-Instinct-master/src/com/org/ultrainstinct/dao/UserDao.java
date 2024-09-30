/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.org.ultrainstinct.dao;

import com.org.ultrainstinct.dto.UserSession;
import com.org.ultrainstinct.model.NhanVien;

import java.sql.SQLException;

/**
 *
 * @author daota
 */
public interface UserDao {
    boolean checkCurrentPassword(String maNhanVien, String matKhauCu);
    boolean updatePassword(String maNhanVien, String matKhauMoi);
    UserSession getUserDetails(String maNhanVien);
    NhanVien findByID(String maNhanVien);
    void update(NhanVien nv);
}
