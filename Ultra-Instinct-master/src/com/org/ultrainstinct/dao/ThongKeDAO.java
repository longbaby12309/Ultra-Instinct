/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.org.ultrainstinct.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Minh Ngoc
 */
public interface ThongKeDAO {
    public int getFirstInvoiceYear() throws SQLException ;
    List<Integer> getYearinHoaDon() throws SQLException;
}
