package com.troyanskiievgen.acctroyanskii.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Relax on 02.07.2017.
 */

public class DateUtils {

    private final static String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss";

    public static String millisecondsConverter(long milliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        formatter.setTimeZone(TimeZone.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }
}
