package com.org.ultrainstinct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSession {

    private static UserSession user;

    private String userName;
    private String password;
    private String role;
    private String fullName;

    public static final String ROLE_MANAGER = "Quản Trị Viên";
    public static final String ROLE_SALES = "Nhân Viên Bán Hàng";
    public static final String ROLE_WAREHOUSE = "Nhân Viên Kho";

    // Phương thức tĩnh để trả về thể hiện duy nhất của lớp
    public static UserSession getUser() {
        if (user == null) {
            user = new UserSession();
        }
        return user;
    }

    // Phương thức tĩnh để tạo thể hiện với các tham số userName, password, role và fullName
    public static UserSession getUser(String userName, String password, String role, String fullName) {
        if (user == null) {
            user = new UserSession(userName, password, role, fullName);
        } else {
            user.setUserName(userName);
            user.setPassword(password);
            user.setRole(role);
            user.setFullName(fullName);
        }
        return user;
    }

    public boolean isLogin() {
        return this.userName != null && !this.userName.isEmpty();
    }

    public boolean isManager() {
        return isLogin() && this.role.equals(ROLE_MANAGER);
    }

    public boolean isSales() {
        return isLogin() && this.role.equals(ROLE_SALES);
    }

    public boolean isWarehouse() {
        return isLogin() && this.role.equals(ROLE_WAREHOUSE);
    }

    public static void clear() {
        user = null;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}