package org.istad.mbanking.util;

public class FileSizeUtil {
    public static long parseSizeToBytes(String size) {
        size = size.toUpperCase().trim();
        if (size.endsWith("KB")) {
            return Long.parseLong(size.replace("KB", "").trim()) * 1024;
        } else if (size.endsWith("MB")) {
            return Long.parseLong(size.replace("MB", "").trim()) * 1024 * 1024;
        } else if (size.endsWith("GB")) {
            return Long.parseLong(size.replace("GB", "").trim()) * 1024 * 1024 * 1024;
        } else {
            return Long.parseLong(size); // assume it's already in bytes
        }
    }
}
