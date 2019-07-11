package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class hu_HU implements Serializable, HasLanguage {
// Hungarian

    public static final String[] monthsFull = {"január", "február", "március", "április", "május", "június", "július", "augusztus", "szeptember", "október", "november", "december"};
    public static final String[] monthsShort = {"jan", "febr", "márc", "ápr", "máj", "jún", "júl", "aug", "szept", "okt", "nov", "dec"};
    public static final String[] weekdaysFull = {"vasárnap", "hétfő", "keXxXx", "szerda", "csütörtök", "péntek", "szombat"};
    public static final String[] weekdaysShort = {"V", "H", "K", "SZe", "CS", "P", "SZo"};
    public static final String[] weekdaysLetter = {"V", "H", "K", "Sze", "Cs", "P", "Szo"};

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
