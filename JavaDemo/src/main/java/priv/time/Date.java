package priv.time;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日期判断的示例
 * @author zhangkai
 * @date 2017-9-21 15:20
 */
public class Date {
    public static final String DATE_PATTERN_YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String DATE_PATTERN_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String STR_MINUTE_MIN = " 00:00:00";
    public static final String STR_MINUTE_MAX = " 23:59:59";

    private static Map<String, ThreadLocal<SimpleDateFormat>> threadLocalMap = new ConcurrentHashMap();
    private static SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat( DATE_PATTERN_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND );
    private static TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    private static ThreadLocal<Calendar> THREAD_LOCAL_CALENDAR = new ThreadLocal<>();

    public Calendar getCalendarByThread() {
        Calendar calendar = THREAD_LOCAL_CALENDAR.get();
        if (calendar == null) {
            calendar = Calendar.getInstance();
            THREAD_LOCAL_CALENDAR.set(calendar);
        }
        calendar.clear();
        calendar.setTimeZone(DEFAULT_TIME_ZONE);
        return calendar;
    }

    public static void setDefaultTimeZone(TimeZone timeZone) {
        DEFAULT_TIME_ZONE = timeZone;
    }

    private Date() {
    }

    private static class DateHandler {
        private static final Date INSTANCE = new Date();
    }

    public static Date getInstance() {
        return DateHandler.INSTANCE;
    }

    public boolean isSameDay(long timeStamp1, long timeStamp2)
    {
        return isSameDay(timeStamp1, timeStamp2, TimeZone.getDefault());
    }

    public boolean isSameDay(long timeStamp1, long timeStamp2, TimeZone timeZone) {
        Calendar calendar1 = getCalendarByThread();
        calendar1.setTimeZone(timeZone);
        calendar1.setTimeInMillis(timeStamp1);
        int year1 = calendar1.get(Calendar.YEAR);
        int dayOfYear1 = calendar1.get(Calendar.DAY_OF_YEAR);

        Calendar calendar2 = getCalendarByThread();
        calendar2.setTimeZone(timeZone);
        calendar2.setTimeInMillis(timeStamp2);
        int yea2 = calendar2.get(Calendar.YEAR);
        int dayOfYear2 = calendar2.get(Calendar.DAY_OF_YEAR);

        return dayOfYear1 == dayOfYear2 && year1 == yea2;
    }

    public boolean isSampDay(Calendar date1, Calendar date2)
    {
        //保证两个时间的时区相同
        if (!date1.getTimeZone().equals(date2.getTimeZone())) {
            TimeZone timeZone = date1.getTimeZone();
            long date2TimeStamp = date2.getTimeInMillis();
            date2 = Calendar.getInstance(timeZone);
            date2.setTimeInMillis(date2TimeStamp);

        }
        return date1.get(Calendar.DAY_OF_YEAR) == date2.get(Calendar.DAY_OF_YEAR)
                && date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
    }

    public boolean isSameWeek(long timeStamp1, long timeStamp2)
    {
        return isSameWeek(timeStamp1, timeStamp2, TimeZone.getDefault());
    }

    public boolean isSameWeek(long timeStamp1, long timeStamp2, TimeZone timeZone)
    {
        Calendar calendar1 = getCalendarByThread();
        calendar1.setTimeZone(timeZone);
        calendar1.setTimeInMillis(timeStamp1);
        Calendar calendar2 = getCalendarByThread();
        calendar2.setTimeZone(timeZone);
        calendar2.setTimeInMillis(timeStamp2);

        return isSameWeek(calendar1, calendar2);
    }

    public boolean isSameWeek(java.util.Date date1, java.util.Date date2)
    {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date2);
        return isSameWeek(calendar1, calendar2);
    }
    public boolean isSameWeek(Calendar date1, Calendar date2)
    {
        int date1Year = date1.get(Calendar.YEAR);
        int date1WeekOfYear = date1.get(Calendar.WEEK_OF_YEAR);
        int date2Year = date2.get(Calendar.YEAR);
        int date2WeekOfYear = date2.get(Calendar.WEEK_OF_YEAR);
        return date1Year == date2Year && date1WeekOfYear == date2WeekOfYear;
    }
//
//    public long between(long timeStampStart, long timeStampENd, TimeZone timeZone, TimeUnit unit) {
//
//    }
//
//    public long between(java.util.Date start, java.util.Date end, TimeUnit unit) {
//        if(start != null && end != null && unit != null) {
//            long duration = end.getTime() - start.getTime();
//            return unit.convert(duration, TimeUnit.MILLISECONDS);
//        } else {
//            return 0L;
//        }
//    }
//
//    public boolean isBetween(java.util.Date date, java.util.Date start, java.util.Date end) {
//        return date.getTime() > start.getTime() && date.getTime() < end.getTime();
//    }
//
//    public java.util.Date getFirstOfADay(String date) throws Exception {
//        return getFirstOfADay(parseDate(date, PATTERN_DAY));
//    }
//
//    public java.util.Date getEndOfADay(String date) throws Exception {
//        return getEndOfADay(parseDate(date, PATTERN_DAY));
//    }
//
//    public java.util.Date getFirstOfADay(java.util.Date date) throws Exception {
//        SimpleDateFormat sdf = getSimpleDateFormat(PATTERN_DAY);
//        return getSimpleDateFormat(PATTERN_FULL).parse(sdf.format(date) + STR_MINUTE_MIN);
//    }
//
//    public java.util.Date getEndOfADay(java.util.Date date) throws Exception {
//        SimpleDateFormat sdf = getSimpleDateFormat(PATTERN_DAY);
//        return getSimpleDateFormat(PATTERN_FULL).parse(sdf.format(date) + STR_MINUTE_MAX);
//    }
//
//    public java.util.Date getFirstOfAWeek(java.util.Date date) throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(7, 2);
//        return getFirstOfADay(calendar.getTime());
//    }
//
//    public java.util.Date getEndOfAWeek(java.util.Date date) throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(7, 1);
//        calendar.add(5, 7);
//        return getEndOfADay(calendar.getTime());
//    }
//
//    public java.util.Date getFirstOfAMonth(java.util.Date date) throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(5, 1);
//        return getFirstOfADay(calendar.getTime());
//    }
//
//    public java.util.Date getEndOfAMonth(java.util.Date date) throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(5, 1);
//        calendar.roll(5, -1);
//        return getEndOfADay(calendar.getTime());
//    }
//
//    public java.util.Date getFirstOfLastMonth(java.util.Date date) throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(2, -1);
//        return getFirstOfAMonth(calendar.getTime());
//    }
//
//    public java.util.Date getEndOfLastMonth(java.util.Date date) throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(2, -1);
//        return getEndOfAMonth(calendar.getTime());
//    }
//
//    public String getQuarterName(java.util.Date date, int changeNum) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(2, changeNum * 3);
//        return getQuarterName(calendar.getTime());
//    }
//
//    public String getQuarterName(java.util.Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int year = calendar.get(1);
//        int month = calendar.get(2);
//        switch(month) {
//            case 0:
//            case 1:
//            case 2:
//                return year + " 第一季度";
//            case 3:
//            case 4:
//            case 5:
//                return year + " 第二季度";
//            case 6:
//            case 7:
//            case 8:
//                return year + " 第三季度";
//            case 9:
//            case 10:
//            case 11:
//                return year + " 第四季度";
//            default:
//                return year + "";
//        }
//    }
//
//    public int dateSub(java.util.Date date1, java.util.Date date2) {
//        return (int)Math.ceil((double)(date1.getTime() - date2.getTime()) / 8.64E7D);
//    }
//
//    public static java.util.Date getDateByAddDays(java.util.Date date, int days) {
//        Calendar calender = Calendar.getInstance();
//        calender.setTime(date);
//        calender.add(5, days);
//        return calender.getTime();
//    }
//
//    public java.util.Date getDateByAddDays(int days) {
//        java.util.Date date = new java.util.Date();
//        return getDateByAddDays(date, days);
//    }
//
//    public String getDateStrByAddDays(java.util.Date date, int days) {
//        java.util.Date newDate = getDateByAddDays(date, days);
//        return formatDate(newDate);
//    }
//
//    public String getDateStrByAddDays(int days) {
//        java.util.Date newDate = getDateByAddDays(days);
//        return formatDate(newDate);
//    }
//
//    public boolean isLeapYear(int year) {
//        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
//    }
//
//    public List<String> getDateStrs(java.util.Date startDate, java.util.Date endDate, String pattern) {
//        List<String> dates = new ArrayList();
//        int i = 0;
//
//        while(true) {
//            java.util.Date date = getDateByAddDays(startDate, i);
//            if(date.compareTo(endDate) > 0) {
//                return dates;
//            }
//
//            dates.add(formatDate(date, pattern));
//            ++i;
//        }
//    }
//
//    public Long getMonthNo(java.util.Date date) {
//        String monthNoStr = formatDate(date, "MM");
//        Long monthNo = Long.valueOf(monthNoStr);
//        return monthNo;
//    }
//
//    public Long getYearNo(java.util.Date date) {
//        String yearNoStr = formatDate(date, "YYYY");
//        Long yearNo = Long.valueOf(yearNoStr);
//        return yearNo;
//    }
//
//    public Long getDayNo(java.util.Date date) {
//        String dayNoStr = formatDate(date, "dd");
//        Long dayNo = Long.valueOf(dayNoStr);
//        return dayNo;
//    }
//
//    public java.util.Date parseUTCDate(String utcTime) {
//        utcTime = utcTime.replace("Z", " UTC");
//        return parseDate(utcTime, "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//    }

}
