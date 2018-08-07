package com.matas.caroperatingsystem.utils;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    public static int getCurrentHour() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }
}
