package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class da_DK implements Serializable, HasLanguage {
// Danish

    public static final String[] monthsFull = {"januar", "februar", "marts", "april", "maj", "juni", "juli", "august", "september", "oktober", "november", "december"};
    public static final String[] monthsShort = {"jan", "feb", "mar", "apr", "maj", "jun", "jul", "aug", "sep", "okt", "nov", "dec"};
    public static final String[] weekdaysFull = {"søndag", "mandag", "tirsdag", "onsdag", "torsdag", "fredag", "lørdag"};
    public static final String[] weekdaysShort = {"søn", "man", "tir", "ons", "tor", "fre", "lør"};
    public static final String[] weekdaysLetter = {"Sø", "Ma", "Ti", "On", "To", "Fr", "Lø"};

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
