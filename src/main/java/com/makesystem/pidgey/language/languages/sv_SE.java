package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class sv_SE implements Serializable, HasLanguage {
// Swedish

    public static final String[] monthsFull = {"januari", "februari", "mars", "april", "maj", "juni", "juli", "augusti", "september", "oktober", "november", "december"};
    public static final String[] monthsShort = {"jan", "feb", "mar", "apr", "maj", "jun", "jul", "aug", "sep", "okt", "nov", "dec"};
    public static final String[] weekdaysFull = {"söndag", "måndag", "tisdag", "onsdag", "torsdag", "fredag", "lördag"};
    public static final String[] weekdaysShort = {"sön", "mån", "tis", "ons", "tor", "fre", "lör"};
    public static final String[] weekdaysLetter = {"Sö", "Må", "Ti", "On", "To", "Fr", "Lö"};

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
