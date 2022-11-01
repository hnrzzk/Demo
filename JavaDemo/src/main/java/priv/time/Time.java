package priv.time;

public class Time {
    public static final int SECOND_OF_MINUTE = 60;
    public static final int MINUTE_OF_HOUR = 60;
    public static final int HOUR_OF_DAY = 24;
    public static final int DAY_OF_WEEK = 7;

    public static final int MILLISECOND_OF_SECOND = 1000;
    public static final long MILLISECOND_OF_MINUTE = MILLISECOND_OF_SECOND * SECOND_OF_MINUTE;
    public static final long MILLISECOND_OF_HOUR = MILLISECOND_OF_MINUTE * MINUTE_OF_HOUR;
    public static final long MILLISECOND_OF_DAY = MILLISECOND_OF_HOUR * HOUR_OF_DAY;
    public static final long MILLISECOND_OF_WEEK = MILLISECOND_OF_DAY * DAY_OF_WEEK;

}
