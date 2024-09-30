/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.org.ultrainstinct.helper;

import com.org.ultrainstinct.model.NhanVien;

/**
 *
 * @author Minh Ngoc
 */
public class AuthHelper {
    public static NhanVien user = null;
    public static void clear(){
    AuthHelper.user=null;
    }
    public static boolean isLogin(){
    return AuthHelper.user!=null;
    }
//    public static boolean isManager(){
////    return AuthHelper.isLogin()&& user.getChucVu();
//    }
}
