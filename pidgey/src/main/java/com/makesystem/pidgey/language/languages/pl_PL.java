package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class pl_PL implements Serializable, HasLanguage {
// Polish

    public static final String[] monthsFull = {"styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"};
    public static final String[] monthsShort = {"sty", "lut", "mar", "kwi", "maj", "cze", "lip", "sie", "wrz", "paź", "lis", "gru"};
    public static final String[] weekdaysFull = {"niedziela", "poniedziałek", "wtorek", "środa", "czwartek", "piątek", "sobota"};
    public static final String[] weekdaysShort = {"niedz.", "pn.", "wt.", "śr.", "cz.", "pt.", "sob."};
    public static final String[] weekdaysLetter = {"N", "Pn", "Wt", "Śr", "Cz", "Pt", "So"};

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
