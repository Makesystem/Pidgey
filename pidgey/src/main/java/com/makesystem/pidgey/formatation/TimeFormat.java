/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.formatation;

import com.makesystem.pidgey.language.Language;
import com.makesystem.pidgey.language.Locales;
import java.util.Date;

/**
 *
 * @author Richeli.vargas
 */
public class TimeFormat {

    /**
     * Supported patterns
     */
    public static interface Patterns {

        public static final String YEAR = "{year}";
        public static final String COMPACT_YEAR = "{compact_year}";
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

    public static void main(String[] args) {

        final StringBuilder pattern = new StringBuilder();
        pattern.append(Patterns.DAY).append("/");
        pattern.append(Patterns.MONTH_SHORT).append("/");
        pattern.append(Patterns.YEAR).append(" ");
        pattern.append(Patterns.HOURS).append(":");
        pattern.append(Patterns.MINUTES).append(":");
        pattern.append(Patterns.SECONDS).append(":");
        pattern.append(Patterns.MILLIS);

        System.out.println(format(new Date(), pattern.toString(), Locales.pt_BR));
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

        pattern = pattern.replace(Patterns.COMPACT_YEAR, String.valueOf(year).substring(2));
        pattern = pattern.replace(Patterns.YEAR, String.valueOf(year));
        pattern = pattern.replace(Patterns.MONTH, NumericFormat.specificLength(month, 2));
        pattern = pattern.replace(Patterns.MONTH_FULL, Language.monthFull(month, locale));
        pattern = pattern.replace(Patterns.MONTH_SHORT, Language.monthShort(month, locale));
        pattern = pattern.replace(Patterns.DAY, NumericFormat.specificLength(day, 2));
        pattern = pattern.replace(Patterns.DAY_OF_WEEK, NumericFormat.specificLength(dayOfWeek, 1));
        pattern = pattern.replace(Patterns.DAY_OF_WEEK_FULL, Language.weekdayFull(dayOfWeek, locale));
        pattern = pattern.replace(Patterns.DAY_OF_WEEK_SHORT, Language.weekdayShort(dayOfWeek, locale));
        pattern = pattern.replace(Patterns.DAY_OF_WEEK_LETTER, Language.weekdaysLetter(dayOfWeek, locale));
        pattern = pattern.replace(Patterns.HOURS, NumericFormat.specificLength(hours, 2));
        pattern = pattern.replace(Patterns.MINUTES, NumericFormat.specificLength(minutes, 2));
        pattern = pattern.replace(Patterns.SECONDS, NumericFormat.specificLength(seconds, 2));
        pattern = pattern.replace(Patterns.MILLIS, NumericFormat.specificLength(millis, 2));

        return pattern;
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

}
