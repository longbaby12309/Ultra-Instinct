package com.org.ultrainstinct.utils;
import java.time.LocalDate;
import java.util.UUID;
public class StringUtil {
    public static String genCode(String code) {
        StringBuilder codeFormat = new StringBuilder(code)
                .append("-")
                .append(LocalDate.now().toString()).append("-")
                .append(UUID.randomUUID());
        return codeFormat.toString();
    }
    public static String objectToString(Object object) {
        if (object == null) {
            return null;
        }
        return object.toString();
    }
    private StringUtil() {
        throw new IllegalStateException("Utility class");
    }
}
