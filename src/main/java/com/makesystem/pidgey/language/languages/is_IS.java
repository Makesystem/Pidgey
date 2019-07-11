package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class is_IS implements Serializable, HasLanguage {
// Icelandic

    public static final String[] monthsFull = {"janúar", "febrúar", "mars", "apríl", "maí", "júní", "júlí", "ágúst", "september", "október", "nóvember", "desember"};
    public static final String[] monthsShort = {"jan", "feb", "mar", "apr", "maí", "jún", "júl", "ágú", "sep", "okt", "nóv", "des"};
    public static final String[] weekdaysFull = {"sunnudagur", "mánudagur", "þriðjudagur", "miðvikudagur", "fimmtudagur", "föstudagur", "laugardagur"};
    public static final String[] weekdaysShort = {"sun", "mán", "þri", "mið", "fim", "fös", "lau"};
    public static final String[] weekdaysLetter = {"Su", "Má", "Þr", "Mi", "Fi", "Fö", "La"};

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
