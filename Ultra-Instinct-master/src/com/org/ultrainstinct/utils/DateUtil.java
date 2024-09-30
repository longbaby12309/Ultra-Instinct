package com.org.ultrainstinct.utils;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
public class DateUtil {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HH_MM_SS = "hh:mm:ss";
    public static String localDateToString(LocalDate locadate, String formatDate) {
        if (StringUtils.isBlank(formatDate) || ObjectUtils.isEmpty(locadate)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);
        return locadate.format(formatter);
    }
    public static String localDateTimeToString(LocalDateTime locaDateTime, String formatDate) {
        if (StringUtils.isBlank(formatDate) || ObjectUtils.isEmpty(locaDateTime)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);
        return locaDateTime.format(formatter);
    }
    public static Date getDateNow() {
        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
