package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class it_IT implements Serializable, HasLanguage {
// Italian

    public static final String[] monthsFull = {"gennaio", "febbraio", "marzo", "aprile", "maggio", "giugno", "luglio", "agosto", "settembre", "ottobre", "novembre", "dicembre"};
    public static final String[] monthsShort = {"gen", "feb", "mar", "apr", "mag", "giu", "lug", "ago", "set", "ott", "nov", "dic"};
    public static final String[] weekdaysFull = {"domenica", "lunedì", "martedì", "mercoledì", "giovedì", "venerdì", "sabato"};
    public static final String[] weekdaysShort = {"dom", "lun", "mar", "mer", "gio", "ven", "sab"};
    public static final String[] weekdaysLetter = {"Do", "Lu", "Ma", "Me", "Gi", "Ve", "Sa"};

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
