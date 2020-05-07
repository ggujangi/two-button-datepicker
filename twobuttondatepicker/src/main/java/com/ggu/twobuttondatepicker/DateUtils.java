package com.ggu.twobuttondatepicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String DATE_FORMAT_FULL = "YYYYMMdd";
    public static final String DATE_FORMAT_FULL_SLASH = "YYYY-MM-dd";
    public static final String DATE_FORMAT_FULL_NAME = "YYYY년 M월 d일 EEEE";
    public static final String DATE_FORMAT_DAY_OF_WEEK = "E";
    public static final String DATE_FORMAT_HHMM = "HHmm";

    /** @return current date by format **/
    public static String getCurrentTime(String format) {
        String result = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        result = dateFormat.format(cal.getTime());

        return result;
    }


    /** @return particular date by format **/
    public static String getTargetDate(Date date, String format) {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        result = simpleDateFormat.format(date);

        return result;
    }
}