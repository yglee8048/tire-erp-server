package com.minsoo.co.tireerpserver.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter ISO_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static String toString(LocalDateTime localDateTime) {
        return localDateTime.format(ISO_LOCAL_DATE_TIME);
    }
}
