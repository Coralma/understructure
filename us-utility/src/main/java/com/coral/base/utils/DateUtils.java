package com.coral.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public final class DateUtils {

    /**
     * 日期格式
     */
    public static final String DATE_TIME_FORMAT = ConstantUtils.getValue("yyyy-MM-dd HH:mm:ss");
    public static final String DATE_HOUR_FORMAT = ConstantUtils.getValue("yyyy-MM-dd HH:mm");
    public static final String YYYY_MM_DD = ConstantUtils.getValue("yyyy-MM-dd");
    public static final String HOUR_TIME_FORMAT = ConstantUtils.getValue("HH:mm");

    /**
     * 日期时间相关常量
     */
    public static final long YEAR_DAYS = ConstantUtils.getValue(365L);
    public static final long DAY_HOURS = ConstantUtils.getValue(24L);
    public static final int HOUR_MINUTES = ConstantUtils.getValue(60);
    public static final long HOUR_SECONDS = ConstantUtils.getValue(3600L);
    public static final long SECOND_MILLISECONDS = ConstantUtils.getValue(1000L);
    public static final long YEAR_TIME_MILLIS = ConstantUtils.getValue(SECOND_MILLISECONDS * HOUR_SECONDS * DAY_HOURS * YEAR_DAYS);
    public static final long DAY_TIME_MILLIS = ConstantUtils.getValue(SECOND_MILLISECONDS * HOUR_SECONDS * DAY_HOURS);
    public static final long HOUR_TIME_MILLIS = ConstantUtils.getValue(SECOND_MILLISECONDS * HOUR_SECONDS);

    private static final int BIG_DECIMAL_DIGITAL_2 = ConstantUtils.getValue(2);
    private static final long DATE_SIZE = ConstantUtils.getValue(-1);
    private static final int TIME_LPAD_ZERO_MAX_VALUE = ConstantUtils.getValue(10);
    private static final String[] optionDateFormats = new String[]{"yyyy-MM-dd HH:mm:ss.S a", "yyyy-MM-dd HH:mm:ssz", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ssa"};
    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);
    private static Date defaultDate;

    private DateUtils() {

    }

    /**
     * get the year of a date
     *
     * @param date Date the date which the date get from
     * @return int the year of the date
     */
    public static int getYear(final Date date) {
        final Calendar calendar = createCalendar();
        setCalTime(date, calendar);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * get the month of a date
     *
     * @param date Date the date which the month get from
     * @return int the month of the date
     */
    public static int getMonth(final Date date) {
        final Calendar calendar = createCalendar();
        setCalTime(date, calendar);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * get the day of a date
     *
     * @param date Date the date which the day get from
     * @return int the day of the date
     */
    public static int getDay(final Date date) {
        final Calendar calendar = createCalendar();
        setCalTime(date, calendar);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    private static Calendar createCalendar() {
        final Calendar result;
        result = Calendar.getInstance();
        return result;
    }

    private static void setCalTime(final Date oldDate, final Calendar cal) {
        cal.setTime(oldDate);
    }

    /**
     * Sets the years field to a date returning a new object. The original date object is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to set
     * @return a new Date object set with the specified value
     */
    public static Date setYears(final Date date, final int amount) {
        return set(date, Calendar.YEAR, amount);
    }

    /**
     * Sets the months field to a date returning a new object. The original date object is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to set
     * @return a new Date object set with the specified value
     */
    public static Date setMonths(final Date date, final int amount) {
        return set(date, Calendar.MONTH, amount);
    }

    /**
     * 设置时分秒
     *
     * @param date    设置日期
     * @param hour    目标时
     * @param minutes 目标分
     * @param second  目标秒
     * @return
     */
    public static Date setHourAndMinAndSec(final Date date, final int hour, final int minutes, final int second) {
        final Calendar calendar = createCalendar();
        setCalTime(date, calendar);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * Sets the day of month field to a date returning a new object. The original date object is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to set
     * @return a new Date object set with the specified value
     */
    public static Date setDays(final Date date, final int amount) {
        return set(date, Calendar.DAY_OF_MONTH, amount);
    }

    private static Date set(final Date date, final int calendarField, final int amount) {
        if (date == null) {
            return date;
        }
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(calendarField, amount);
        return c.getTime();
    }

    /**
     * 将日期用指定的日期格式转换成字符串
     *
     * @param date   需要转换的日期
     * @param format 指定的格式
     * @return 转换后的字符串
     */
    public static String date2String(final Date date, String format) {
        if (date == null) {
            return null;
        } else {
            if (StringUtils.isEmpty(format)) {
                format = "dd/MM/yyyy";
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(date);
        }
    }

    /**
     * convert a string to a date according to the indicated format.
     *
     * @param sDate  String the string to be transferred
     * @param format String the indicated format
     * @return Date the transferred date
     */
    public static Date toDate(final String sDate, final String format) {
        if (StringUtils.isEmpty(sDate)) {
            return null;
        }
        return parse(sDate, format);
    }

    /**
     * convert a string to a date according to the indicated format.
     *
     * @param sDate String the string to be transferred
     * @return Date the transferred date
     */
    public static Date toDate(final String sDate) {
        return toDate(sDate, YYYY_MM_DD);
    }

    private static Date parse(final String date, final String defaultFormat) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(defaultFormat);
        Date resultDate = null;
        try {
            resultDate = simpleDateFormat.parse(date);
        } catch (final ParseException e) {
            for (int i = 0; i < optionDateFormats.length; i++) {
                try {
                    final SimpleDateFormat format = new SimpleDateFormat(optionDateFormats[i]);
                    resultDate = format.parse(date);
                } catch (final ParseException e2) {
                    LOG.error(e2.getMessage(), e2);
                }
            }
        }
        return resultDate;
    }

    /**
     * 设置系统日期 格式yyyy-MM-dd
     *
     * @return Date
     */
    public static Date getSysDate() {
        if (defaultDate != null) {
            return defaultDate;
        } else {
            return new Date();
        }
    }

    public static void setDefaultDate(final Date defaultDate) {
        DateUtils.defaultDate = defaultDate;
    }

    /**
     * 获取两个日期之前相差的年数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 两个日期之间相差的年数，四舍五入
     */
    public static double getYearSize(final Date startDate, final Date endDate) {
        if (startDate == null || endDate == null) {
            return Double.valueOf(DATE_SIZE);
        }
        final long timMillis = Math.abs(startDate.getTime() - endDate.getTime());
        return BigDecimal.valueOf(timMillis).divide(BigDecimal.valueOf(YEAR_TIME_MILLIS), BIG_DECIMAL_DIGITAL_2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取两个日期之前相差的天数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 相差的天数，四舍五入
     */
    public static double getDaySize(final Date startDate, final Date endDate) {
        if (startDate == null || endDate == null) {
            return DATE_SIZE;
        }
        final long timMillis = endDate.getTime() - startDate.getTime();
        return BigDecimal.valueOf(timMillis).divide(BigDecimal.valueOf(DAY_TIME_MILLIS), BIG_DECIMAL_DIGITAL_2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 比较两个日期，param1 >= param2，精确到yyyy-MM-dd（不含时分秒）
     *
     * @param param1 日期参数，忽略时分秒
     * @param param2 日期参数，忽略时分秒
     * @return param1 >= param2返回true；反之返回false；
     */
    public static boolean compareDay(final Date param1, final Date param2) {
        final Date m = toDate(date2String(param1, YYYY_MM_DD));
        final Date n = toDate(date2String(param2, YYYY_MM_DD));
        return m.compareTo(n) >= 0;
    }

    /**
     * 参数日期与当前日期比较，date>=sysdate，精确到yyyy-MM-dd（不含时分秒）
     *
     * @param date 日期参数，忽略时分秒
     * @return date>=sysdate返回true；反之返回false；
     */
    public static boolean isNoLessThanCurrentDay(final Date date) {
        return compareDay(date, DateUtils.getSysDate());
    }

    /**
     * 获取两个日期之前相差的小时数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 相差的小时数，四舍五入
     */
    public static double getHourSize(final Date startDate, final Date endDate) {
        if (startDate == null || endDate == null) {
            return DATE_SIZE;
        }
        final long timMillis = Math.abs(startDate.getTime() - endDate.getTime());
        return BigDecimal.valueOf(timMillis).divide(BigDecimal.valueOf(HOUR_TIME_MILLIS), BIG_DECIMAL_DIGITAL_2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取两个日期相差的秒数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 相差的秒数，四舍五入
     */
    public static long getSecondSize(final Date startDate, Date endDate) {
        if (startDate == null) {
            return DATE_SIZE;
        }
        if (endDate == null) {
            endDate = DateUtils.getSysDate();
        }
        final long millis = endDate.getTime() - startDate.getTime();
        return millis / SECOND_MILLISECONDS;
    }

    /**
     * 通过计算出来的剩余时间（含有小数），将其转化为hh:mm格式的剩余时间 例如：1.5小时 --> 01:30
     *
     * @param hour 剩余时间（hour为单位，可能为负值）
     * @return 格式化后的剩余时间
     */
    public static String getTimeInHHMMFormat(final BigDecimal hour) {
        int firstIndex = 1;
        final String hourString = hour.toString();

        if (hour.compareTo(BigDecimal.ZERO) >= 0) {
            firstIndex = 0;
        }
        if (!hourString.contains(".")) {
            final String integerPartString = hourString.substring(firstIndex);
            return (integerPartString.length() == 1 ? "0" + integerPartString : integerPartString) + ":00";
        } else {
            String integerPartString = hourString.substring(firstIndex, hourString.indexOf("."));
            final String decimalPart = "0" + hourString.substring(hourString.indexOf("."));
            int decimalPartInt = ((int) Math.ceil(Double.valueOf(decimalPart) * HOUR_MINUTES));
            if (decimalPartInt == HOUR_MINUTES) {
                integerPartString = (Integer.parseInt(integerPartString) + 1) + "";
                decimalPartInt = 0;
            }
            return ((integerPartString.length() == 1 ? "0" + integerPartString : integerPartString) + ":" + (decimalPartInt < TIME_LPAD_ZERO_MAX_VALUE ? "0" + decimalPartInt : decimalPartInt));
        }
    }

    /**
     * 获取当前时间
     *
     * @return currentTime
     */
    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 为给定的日历字加或减去指定的天数
     *
     * @param date 时间
     * @param day  天数
     * @return 返回时间
     */
    public static Date addDay(final Date date, final int day) {
        final Calendar calendar = createCalendar();
        setCalTime(date, calendar);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 为给定的日历字加或减去指定的小时
     *
     * @param date 时间
     * @param hour 小时
     * @return 返回时间
     */
    public static Date addHour(final Date date, final int hour) {
        final Calendar calendar = createCalendar();
        setCalTime(date, calendar);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    /**
     * 为给定的日历字加或减去指定的分钟
     *
     * @param date   时间
     * @param minute 分钟
     * @return 返回时间
     */
    public static Date addMinute(final Date date, final int minute) {
        final Calendar calendar = createCalendar();
        setCalTime(date, calendar);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }
}
