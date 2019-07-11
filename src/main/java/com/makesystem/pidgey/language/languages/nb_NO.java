package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class nb_NO implements Serializable, HasLanguage {
// Norwegian

    public static final String[] monthsFull = {"januar", "februar", "mars", "april", "mai", "juni", "juli", "august", "september", "oktober", "november", "desember"};
    public static final String[] monthsShort = {"jan", "feb", "mar", "apr", "mai", "jun", "jul", "aug", "sep", "okt", "nov", "des"};
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
