package org.ckr.msdemo.adminservice.util;

/**
 * An util class for String objects processing.
 */
public class StringUtil {

    /**
     * Check if an string is "null".
     * @param str The string that is checked.
     * @return return true if str is null or it equals to "". Otherwise, return false.
     */
    public static boolean isNull(String str) {

        return (str == null || str.length() == 0);

    }
}
