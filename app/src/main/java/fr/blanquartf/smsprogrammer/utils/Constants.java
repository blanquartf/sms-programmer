package fr.blanquartf.smsprogrammer.utils;

import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by blanquartf on 28/12/2017.
 */

public final class Constants {
    public static final String PATTERN_FOR_REPLACEMENT = "${%s}";
    public static final DateFormat DATE_FORMAT =  DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM, Locale.getDefault());
}
