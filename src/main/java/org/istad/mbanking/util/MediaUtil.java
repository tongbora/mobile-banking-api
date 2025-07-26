package org.istad.mbanking.util;

public class MediaUtil {

    public static String extractExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
