/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.org.ultrainstinct.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author phung
 */
public class NumberUtil {

    public static double withBigDecimalDouble(double value, int places) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();

    }

    public static float withBigDecimalFloat(float value, int places) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }

    public static Float convertObjectToFloat(Object rowData) {
        if (rowData == null) {
            return null;
        }
        return Float.valueOf((String) rowData);
    }
    
    public static Integer convertObjectToInteger(Object rowData) {
        if (rowData == null) {
            return null;
        }
        return Integer.valueOf((String) rowData);
    }
}
