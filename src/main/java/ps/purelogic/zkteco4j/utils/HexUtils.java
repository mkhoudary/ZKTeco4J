/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Mohammed
 */
public class HexUtils {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(int[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String byteArrayToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static Date extractDate(long encDate) throws ParseException {
        long second = encDate % 60;
        encDate = encDate / 60;
        long minute = encDate % 60;
        encDate = encDate / 60;
        long hour = encDate % 24;
        encDate = encDate / 24;
        long day = encDate % 31 + 1;
        encDate = encDate / 31;
        long month = encDate % 12 + 1;
        encDate = encDate / 12;
        long year = (long) Math.floor(encDate + 2000);

        String dateStr = year + "-"
                + StringUtils.leftPad(String.valueOf(month), 2, "0") + "-"
                + StringUtils.leftPad(String.valueOf(day), 2, "0") + " "
                + StringUtils.leftPad(String.valueOf(hour), 2, "0") + ":"
                + StringUtils.leftPad(String.valueOf(minute), 2, "0") + ":"
                + StringUtils.leftPad(String.valueOf(second), 2, "0");

        Date date = DATE_FORMAT.parse(dateStr);

        return date;
    }
}
