/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.formatation;

import com.makesystem.pidgey.language.Language;
import com.makesystem.pidgey.language.Locales;
import java.util.Date;
import java.util.function.Function;

/**
 *
 * @author Richeli.vargas
 */
public class TimeFormat {

    public static final long ONE_SECOND_IN_MILLIS = 1000;
    public static final long ONE_MINUTE_IN_MILLIS = ONE_SECOND_IN_MILLIS * 60;
    public static final long ONE_HOUR_IN_MILLIS = ONE_MINUTE_IN_MILLIS * 60;
    public static final long ONE_DAY_IN_MILLIS = ONE_HOUR_IN_MILLIS * 24;

    /**
     * Supported patterns
     */
    public static interface Patterns {

        public static final String YEAR = "{year}";
        public static final String YEAR_SHORT = "{year_short}";
        public static final String MONTH = "{month}";
        public static final String MONTH_FULL = "{month_full}";
        public static final String MONTH_SHORT = "{month_short}";
        public static final String DAY = "{day}";
        public static final String DAY_OF_WEEK = "{day_of_week}";
        public static final String DAY_OF_WEEK_FULL = "{day_of_week_full}";
        public static final String DAY_OF_WEEK_SHORT = "{day_of_week_short}";
        public static final String DAY_OF_WEEK_LETTER = "{day_of_week_letter}";
        public static final String HOURS = "{hous}";
        public static final String MINUTES = "{minutes}";
        public static final String SECONDS = "{minutes}";
        public static final String MILLIS = "{millis}";

    }

    /**
     * @see com.makesystem.pidgey.formatation.TimeFormat.Patterns
     * @param timestamp
     * @param pattern
     * @return
     */
    public static String format(final long timestamp, String pattern) {
        return format(new Date(timestamp), pattern, Locales.pt_BR);
    }

    /**
     * @param locale
     * @see com.makesystem.pidgey.formatation.TimeFormat.Patterns
     * @param timestamp
     * @param pattern
     * @return
     */
    public static String format(final long timestamp, String pattern, final String locale) {
        return format(new Date(timestamp), pattern, locale);
    }

    /**
     * @see com.makesystem.pidgey.formatation.TimeFormat.Patterns
     * @param date
     * @param pattern
     * @return
     */
    public static String format(final Date date, final String pattern) {
        return format(date, pattern, Locales.pt_BR);
    }

    /**
     * @param locale
     * @see com.makesystem.pidgey.formatation.TimeFormat.Patterns
     * @param date
     * @param pattern
     * @return
     */
    public static String format(final Date date, String pattern, final String locale) {

        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }

        final int millis = (int) (date.getTime() % 1000L);
        final int seconds = date.getSeconds();
        final int minutes = date.getMinutes();
        final int hours = date.getHours();
        final int day = date.getDate();
        final int month = date.getMonth() + 1;
        final int year = date.getYear() + 1900;
        final int dayOfWeek = date.getDay();

        pattern = replacePattern(pattern, Patterns.YEAR_SHORT, year, value -> String.valueOf(value).substring(2));
        pattern = replacePattern(pattern, Patterns.YEAR, year, value -> String.valueOf(value));
        pattern = replacePattern(pattern, Patterns.MONTH, month, value -> NumericFormat.specificLength(value, 2));
        pattern = replacePattern(pattern, Patterns.MONTH_FULL, month, value -> Language.monthFull(value, locale));
        pattern = replacePattern(pattern, Patterns.MONTH_SHORT, month, value -> Language.monthShort(value, locale));
        pattern = replacePattern(pattern, Patterns.DAY, day, value -> NumericFormat.specificLength(value, 2));
        pattern = replacePattern(pattern, Patterns.DAY_OF_WEEK, dayOfWeek, value -> NumericFormat.specificLength(value, 1));
        pattern = replacePattern(pattern, Patterns.DAY_OF_WEEK_FULL, dayOfWeek, value -> Language.weekdayFull(value, locale));
        pattern = replacePattern(pattern, Patterns.DAY_OF_WEEK_SHORT, dayOfWeek, value -> Language.weekdayShort(value, locale));
        pattern = replacePattern(pattern, Patterns.DAY_OF_WEEK_LETTER, dayOfWeek, value -> Language.weekdaysLetter(value, locale));
        pattern = replacePattern(pattern, Patterns.HOURS, hours, value -> NumericFormat.specificLength(value, 2));
        pattern = replacePattern(pattern, Patterns.MINUTES, minutes, value -> NumericFormat.specificLength(value, 2));
        pattern = replacePattern(pattern, Patterns.SECONDS, seconds, value -> NumericFormat.specificLength(value, 2));
        pattern = replacePattern(pattern, Patterns.MILLIS, millis, value -> NumericFormat.specificLength(value, 2));

        return pattern;
    }

    private static <V> String replacePattern(final String pattern, final String valuePattern,
            final V value, final Function<V, String> mapper) {
        if (pattern.contains(valuePattern)) {
            return pattern.replace(valuePattern, mapper.apply(value));
        } else {
            return pattern;
        }
    }

    /**
     *
     * @param seconds
     * @return HH:mm:ss
     */
    public static String seconds(final int seconds) {

        if (seconds < 0) {
            return "00:00:00";
        }

        final int secondsRest = (seconds % 60);
        final int minutes = (seconds / 60) % 60;
        final int hours = (seconds / 60) / 60;

        final StringBuffer builder = new StringBuffer();
        builder.append(NumericFormat.specificLength(hours, 2));
        builder.append(":");
        builder.append(NumericFormat.specificLength(minutes, 2));
        builder.append(":");
        builder.append(NumericFormat.specificLength(secondsRest, 2));
        return builder.toString();
    }

    /**
     *
     * @param seconds
     * @return HH:mm
     */
    public static String secondsII(final int seconds) {

        if (seconds < 0) {
            return "00:00";
        }

        final int minutes = (seconds % 60);
        final int hours = (seconds / 60) % 60;

        final StringBuffer builder = new StringBuffer();
        builder.append(NumericFormat.specificLength(hours, 2));
        builder.append(":");
        builder.append(NumericFormat.specificLength(minutes, 2));

        return builder.toString();
    }

    /**
     *
     * @param seconds
     * @return HH:mm
     */
    public static String secondsIII(final int seconds) {

        if (seconds < 0) {
            return "0h 0min";
        }

        final int minutes = (seconds / 60) % 60;
        final int hours = (seconds / 60) / 60;

        final StringBuffer builder = new StringBuffer();

        builder.append(hours);
        builder.append("h ");
        builder.append(minutes);
        builder.append("min");

        return builder.toString();
    }

    /**
     *
     * @param millis
     * @return HH:mm:ss:mmm
     */
    public static String millis(final long millis) {

        if (millis < 0) {
            return "00:00:00:000";
        }

        final int millisRest = (int) (millis % 1000);
        final int seconds = (int) (millis / 1000);
        final int secondsRest = (seconds % 60);
        final int minutes = (seconds / 60) % 60;
        final int hours = (seconds / 60) / 60;

        final StringBuffer builder = new StringBuffer();
        builder.append(NumericFormat.specificLength(hours, 2));
        builder.append(":");
        builder.append(NumericFormat.specificLength(minutes, 2));
        builder.append(":");
        builder.append(NumericFormat.specificLength(secondsRest, 2));
        builder.append(":");
        builder.append(NumericFormat.specificLength(millisRest, 3));

        return builder.toString();
    }

    public static String toSimpleLivestamp(final long millis) {

        if (millis <= 0) {
            return "0 ms";
        }

        if (millis < ONE_SECOND_IN_MILLIS) {
            return millis + "ms";
        }

        final long ms = millis % 1000;
        final long seconds = millis / 1000;

        if (millis < ONE_MINUTE_IN_MILLIS) {
            return seconds + "s " + (ms > 0 ? ms + "ms" : "");
        }

        final long minutes = seconds / 60;

        if (millis < ONE_HOUR_IN_MILLIS) {
            final long s = (seconds % 60);
            return minutes + "m " + (s > 0 ? s + "ms" : "");
        }

        final long hours = (seconds / 60) / 60;

        if (millis < ONE_DAY_IN_MILLIS) {
            final long m = (minutes % 60);
            return hours + "h " + (m > 0 ? m + "m" : "");
        }

        final long days = hours / 24;
        final long h = (hours % 24);

        return days + "d " + (h > 0 ? h + "h" : "");
    }
}
