package priv.time;

import java.util.Calendar;

class DayDifferCalculatorImp implements DateDifferCalculator {
    @Override
    public long between(long start, long end) {
        Calendar calendar = Date.getInstance().getCalendarByThread();
        calendar.setTimeInMillis(start);
        int startYear = calendar.get(Calendar.YEAR);
        int startDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTimeInMillis(end);
        int endYear = calendar.get(Calendar.YEAR);
        int endDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        if (startYear == endYear) {
            return endDayOfYear - startDayOfYear;
        }else {

        }

        return 0;
    }
}
