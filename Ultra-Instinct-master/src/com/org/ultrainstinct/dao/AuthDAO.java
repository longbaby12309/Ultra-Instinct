package com.org.ultrainstinct.dao;

import java.sql.SQLException;

/**
 * <p>
 * AuthDAO interface implements CRUDDao interface.
 * </p>
 *
 * @author MinhNgoc
 */
public interface AuthDAO {

    boolean logIn(String userName, String password) throws SQLException;

    void logOut();

    boolean isLogin();
    
    boolean isManager();
    
    boolean isSales();
    
    boolean isWarehouse();
    
    String getEmailByUsername(String username) throws SQLException;

    void updatePassword(String username, String newPassword) throws SQLException;
}
