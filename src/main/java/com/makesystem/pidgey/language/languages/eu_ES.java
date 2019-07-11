package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class eu_ES implements Serializable, HasLanguage {
// Basque

    public static final String[] monthsFull = {"urtarrila", "otsaila", "martxoa", "apirila", "maiatza", "ekaina", "uztaila", "abuztua", "iraila", "urria", "azaroa", "abendua"};
    public static final String[] monthsShort = {"urt", "ots", "mar", "api", "mai", "eka", "uzt", "abu", "ira", "urr", "aza", "abe"};
    public static final String[] weekdaysFull = {"igandea", "astelehena", "asteartea", "asteazkena", "osteguna", "ostirala", "larunbata"};
    public static final String[] weekdaysShort = {"ig.", "al.", "ar.", "az.", "og.", "or.", "lr."};
    public static final String[] weekdaysLetter = {"ig.", "al.", "ar.", "az.", "og.", "or.", "lr."};

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
