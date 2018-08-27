package com.srn.crm.core.utils;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {

    public static final long ONE_SECOND = 1000L;
    public static final long ONE_MINUTE = 60L * ONE_SECOND;
    public static final long ONE_HOUR = 60L * ONE_MINUTE;
    public static final long ONE_DAY = 24L * ONE_HOUR;

    public static String getTimeZoneString() {
        return TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT, Locale.ENGLISH);
    }

    public static long getCurrentTimestamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

}
