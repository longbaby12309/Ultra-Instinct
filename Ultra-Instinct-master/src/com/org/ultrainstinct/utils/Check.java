package com.org.ultrainstinct.utils;

import com.org.ultrainstinct.model.NhanVien;




public class Check {
   
//    Đối tượng này chứa thông tin người sử dụng sau khi đăng nhập
    
    public static NhanVien user = null;
   
//    Xóa thông tin của người sử dụng khi có yêu cầu đăng xuất
 
    public static void clear() {
        Check.user = null;
    }

//     Kiểm tra xem đăng nhập hay chưa
   
    public static boolean isLogin() {
        return Check.user != null;
    }
     
//      Kiểm tra xem có phải là trưởng phòng hay không
     
//    public static boolean isManager() {
//        return Check.isLogin() && user.getChucVu();
//    }
}
