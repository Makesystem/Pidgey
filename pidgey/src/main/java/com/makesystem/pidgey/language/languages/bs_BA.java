package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class bs_BA implements Serializable, HasLanguage {
// Bosnian

    public static final String[] monthsFull = {"januar", "februar", "mart", "april", "maj", "juni", "juli", "august", "septembar", "oktobar", "novembar", "decembar"};
    public static final String[] monthsShort = {"jan", "feb", "mar", "apr", "maj", "jun", "jul", "aug", "sep", "okt", "nov", "dec"};
    public static final String[] weekdaysFull = {"nedjelja", "ponedjeljak", "utorak", "srijeda", "cetvrtak", "petak", "subota"};
    public static final String[] weekdaysShort = {"ne", "po", "ut", "sr", "če", "pe", "su"};
    public static final String[] weekdaysLetter = {"n", "p", "u", "s", "č", "p", "s"};

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
