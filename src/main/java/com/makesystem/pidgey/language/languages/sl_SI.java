package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class sl_SI implements Serializable, HasLanguage {
// Slovenian

    public static final String[] monthsFull = {"januar", "februar", "marec", "april", "maj", "junij", "julij", "avgust", "september", "oktober", "november", "december"};
    public static final String[] monthsShort = {"jan", "feb", "mar", "apr", "maj", "jun", "jul", "avg", "sep", "okt", "nov", "dec"};
    public static final String[] weekdaysFull = {"nedelja", "ponedeljek", "torek", "sreda", "četrtek", "petek", "sobota"};
    public static final String[] weekdaysShort = {"ned", "pon", "tor", "sre", "čet", "pet", "sob"};
    public static final String[] weekdaysLetter = {"Ne", "Po", "To", "Sr", "Če", "Pe", "So"};

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
