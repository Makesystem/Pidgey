package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class fa_ir implements Serializable, HasLanguage {
// Farsi

    public static final String[] monthsFull = {"دسامبر", "نوامبر", "اکتبر", "سپتامبر", "اوت", "ژوئیه", "ژوئن", "مه", "آوریل", "مارس", "فوریه", "ژانویه"};
    public static final String[] monthsShort = {"دسامبر", "نوامبر", "اکتبر", "سپتامبر", "اوت", "ژوئیه", "ژوئن", "مه", "آوریل", "مارس", "فوریه", "ژانویه"};
    public static final String[] weekdaysFull = {"شنبه", "جمعه", "پنجشنبه", "چهارشنبه", "سه شنبه", "دوشنبه", "یکشنبه"};
    public static final String[] weekdaysShort = {"شنبه", "جمعه", "پنجشنبه", "چهارشنبه", "سه شنبه", "دوشنبه", "یکشنبه"};
    public static final String[] weekdaysLetter = {"شنبه", "جمعه", "پنجشنبه", "چهارشنبه", "سه شنبه", "دوشنبه", "یکشنبه"};

    @Override
	public final String[] getMonthsFull() {
        return monthsFull;
    }

    @Override
	public final String[] getMonthsShort() {
        return monthsShort;
    }

    @Override
	public final String[] getWeekdaysFull() {
        return weekdaysFull;
    }

    @Override
	public final String[] getWeekdaysShort() {
        return weekdaysShort;
    }

    @Override
	public final String[] getWeekdaysLetter() {
        return weekdaysLetter;
    }
}
