package com.lenovo.sap.api.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yuhao5
 * @date 2022/3/7
 **/
public class TimeUtil {
    public static final String TZ = "Asia/Shanghai";
    public static final SimpleDateFormat UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
    public static final SimpleDateFormat yyyyMMddHHmmssSSS = new SimpleDateFormat(
            "yyyyMMddHHmmssSSS");
    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
            "yyyyMMdd");
    public static final SimpleDateFormat yyMMdd = new SimpleDateFormat(
            "yyMMdd");
    public static final SimpleDateFormat yyyy_MM_ddHHmmss = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat YYYYMMDDHHMMSS_SLASH = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");
    public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat(
            "yyyy-MM-dd");
    public static final SimpleDateFormat yyyy_MM_dd_dot = new SimpleDateFormat(
            "yyyy.MM.dd");
    public static final SimpleDateFormat HH = new SimpleDateFormat(
            "HH");
    /**
     * 带时区格式：General time zone
     */
    public static final SimpleDateFormat yyyy_MM_ddTHHmmssSSSZ = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    /**
     * 带时区格式：RFC 822 time zone
     */
    public static final SimpleDateFormat yyyy_MM_ddTHHmmssSSSz = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSz");
    /**
     * 带时区格式：ISO 8601 time zone
     */
    public static final SimpleDateFormat yyyy_MM_ddTHHmmssSSSX = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSX");
    private static final int maxToken = 1000;
    private static int increaseToken = 0;

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * UTC时间转换
     *
     * @param UTCString
     * @return
     */
    public static Date UTCStringtODefaultString(String UTCString, SimpleDateFormat formateDate) {
        try {
            UTCString = UTCString.replace("Z", " UTC");
            Date date = UTC_FORMAT.parse(UTCString);
            String utcDate = formateDate.format(date);
            return formateDate.parse(utcDate);
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }

    public static String getCur_yyyy_MM_dd() {
        return yyyy_MM_dd.format(new Date());
    }

    public static String getCur_yyyy_MM_ddHHmmss() {
        return yyyy_MM_ddHHmmss.format(new Date());
    }

    /**
     * Get the date string: "yyyy-MM-dd HH:mm:ss".
     *
     * @param date Date
     * @return date string
     */
    public static String get_yyyy_MM_ddHHmmss(Date date) {
        if (null == date) {
            return "";
        }
        return yyyy_MM_ddHHmmss.format(date);
    }

    /**
     * Parse date from string: "yyyy-MM-dd HH:mm:ss".
     *
     * @param datestr date string
     * @return Date
     */
    public static Date parse_yyyy_MM_ddHHmmss(String datestr) {
        try {
            return yyyy_MM_ddHHmmss.parse(datestr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parse date from string: "yyyy-MM-dd".
     *
     * @param datestr date string
     * @return Date
     */
    public static Date parse_yyyy_MM_dd(String datestr) {
        try {
            return yyyy_MM_dd.parse(datestr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parse date from string: "yyyy-MM-dd'T'HH:mm:ss.SSSZ".
     *
     * @param datestr date string
     * @return Date
     */
    public static Date parse_yyyy_MM_ddTHHmmssSSSZ(String datestr) {
        try {
            return yyyy_MM_ddTHHmmssSSSZ.parse(datestr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUnixTimeString() {
        return "" + System.currentTimeMillis();
    }

    public static long getUnixTime() {
        return System.currentTimeMillis();
    }

    public static String getCur_yyyyMMdd() {
        return yyyyMMdd.format(new Date());
    }

    public static String getCur_yyMMdd() {
        return yyMMdd.format(new Date());
    }

    public static int getCurHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getCur_yyyyMMdd_Int() {
        return Integer.parseInt(yyyyMMdd.format(new Date()));
    }

    /**
     * Truncate date with format pattern.
     *
     * @param date   Date
     * @param format Format pattern
     * @return Truncated Date
     */
    public static Date truncateDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date result = date;
        try {
            result = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get current time's token.
     *
     * @return time mask
     */
    public static synchronized String genTimemark() {
        String timemark = yyyyMMddHHmmssSSS.format(new Date());
        increaseToken++;
        if (increaseToken >= maxToken) {
            increaseToken = 0;
        }
        String token = String.format("%03d", increaseToken);
        return timemark + token;
    }

    /**
     * 计算2个日期之间相差多少年
     */
    public static int yearComparePrecise(Date fromDate, Date toDate) {
        long fromTimestamp = fromDate.getTime();
        long toTimestamp = toDate.getTime();
        // 获取时间戳的间隔秒数
        long interval = (toTimestamp - fromTimestamp) / 1000;
        // 根据间隔秒数，计算得到几年
        long year = interval / (60 * 60 * 24 * 365);
        return (int) year;
    }

    /**
     * 切换日期格式，将日期字符串切换为另一种格式返回
     *
     * @param dateStr
     * @param fromFormat
     * @param toFormat
     * @return
     */
    public static String togglePattern(String dateStr, SimpleDateFormat fromFormat, SimpleDateFormat toFormat) {
        try {
            if (StringUtils.isBlank(dateStr)) {
                return null;
            }

            Date date = fromFormat.parse(dateStr);
            return toFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 切换日期格式，默认从{@link TimeUtil#yyyy_MM_ddTHHmmssSSSX}切换为{@link TimeUtil#yyyy_MM_ddHHmmss}
     *
     * @param dateStr
     * @return
     */
    public static String togglePatternTimeZoneTo_yyyy_MM_ddHHmmss(String dateStr) {
        return togglePattern(dateStr, yyyy_MM_ddTHHmmssSSSX, yyyy_MM_ddHHmmss);
    }

    /**
     * Get the time after n days.
     *
     * @param days How many days after
     * @return Timestamp
     */
    private Timestamp getTimeStampAfterDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return new Timestamp(cal.getTime().getTime());
    }

    public Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
