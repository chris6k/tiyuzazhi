package com.tiyuzazhi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chris.xue
 */
public class DatetimeUtils {
    private static long MILLS_OF_DAY = 3600 * 24;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd");

    public static synchronized String format(Date date) {
        if (date == null) return "";
        return formatter.format(date);
    }

    public static synchronized String format2(Date date) {
        if (date == null) return "";
        return formatter2.format(date);
    }

    public static synchronized int getDuringDay(Date start, Date end) {
        if (start == null || end == null) return 0;
        long diff = end.getTime() - start.getTime();
        return Math.round((float) diff / MILLS_OF_DAY);
    }

    /**
     * @param time
     * @return null if parse failed.
     */
    public static Date parse(String time) {
        try {
            return formatter.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param time
     * @return null if parse failed.
     */
    public static Date parse2(String time) {
        try {
            return formatter2.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }
}
