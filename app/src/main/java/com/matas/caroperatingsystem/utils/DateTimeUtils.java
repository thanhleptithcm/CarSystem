package com.matas.caroperatingsystem.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {
    public static final SimpleDateFormat SERVER_DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    private static final SimpleDateFormat MONTH_DAY_FORMAT = new SimpleDateFormat("MMMM dd", Locale.US);
    private static final SimpleDateFormat MONTH_DAY_YYYY_FORMAT = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

    public static String toMonthDay(Date date) {
        return MONTH_DAY_FORMAT.format(date);
    }

    public static String toMonthDayYearDay(Date date) {
        if (date == null) {
            return null;
        }
        return MONTH_DAY_YYYY_FORMAT.format(date);
    }

    // convert date object from a timezone to another timezone
    public static Date convertTimeZone(Date date, TimeZone fromTZ, TimeZone toTZ) {
        long fromTZDst = 0;
        if (fromTZ.inDaylightTime(date)) {
            fromTZDst = fromTZ.getDSTSavings();
        }

        long fromTZOffset = fromTZ.getRawOffset() + fromTZDst;

        long toTZDst = 0;
        if (toTZ.inDaylightTime(date)) {
            toTZDst = toTZ.getDSTSavings();
        }
        long toTZOffset = toTZ.getRawOffset() + toTZDst;

        return new Date(date.getTime() + (toTZOffset - fromTZOffset));
    }
}
