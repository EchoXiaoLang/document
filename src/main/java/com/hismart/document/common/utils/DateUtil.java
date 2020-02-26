package com.hismart.document.common.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 */
public class DateUtil {

    private static final String FULL_TIME_PATTERN = "yyyyMMddHHmmssSSS";


    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String FULL_TIME_SPLIT_PATTERN_S = "yyyy-MM-dd HH:mm:ss.SSSSSS";
    private static final String FULL_TIME_SPLIT_PATTERN_SS = "yyyyMMddHHmmssSSS";


    public static final String FULL_DATE_PATTERN = "yyyyMMddHH";


    public static final String FULL_DATE_PATTERN_ = "yyyy-MM-dd";

    public static Integer length = 17;

    public static String formatFullTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
    }


    public static String formatFullDate(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_DATE_PATTERN);


    }

    public static String formatFullTime2(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_DATE_PATTERN_);
    }

    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    private static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simformat = new SimpleDateFormat(dateFormatType);
        return simformat.format(date);
    }

    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(FULL_TIME_SPLIT_PATTERN);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static String formatUsTTime(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date d = sdf.parse(date);
        return DateUtil.getDateFormat(d, FULL_DATE_PATTERN_);
    }

    public static String formatTTime(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_TIME_SPLIT_PATTERN_S, Locale.CHINESE);
        Date d = sdf.parse(date);
        return DateUtil.getDateFormat(d, FULL_TIME_PATTERN);
    }

    public static String getDateBefore(Date d, int day) {
        Calendar no = Calendar.getInstance();
        no.setTime(d);
        no.set(Calendar.DATE, no.get(Calendar.DATE) - day);
        return getDateFormat(no.getTime(), FULL_DATE_PATTERN_);
    }


    public static Date timeStampToDate(String timeStamp) throws ParseException {
        timeStamp = timeStamp +  appendZero(length - timeStamp.length());
        SimpleDateFormat formatter = new SimpleDateFormat(FULL_TIME_PATTERN);
        formatter.setLenient(false);
        Date newDate = formatter.parse(timeStamp);
        return newDate;
    }

    private static String appendZero(int i) {
        StringBuilder a = new StringBuilder();
        for (int j = 0; j < i ; j++) {
            a.append("0");
        }
        return a.toString();

    }

    public static String timeStampToString(Long ts) {
        Date date = new Date(ts);
        String tsStr;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            tsStr = sdformat.format(date);
            return tsStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
