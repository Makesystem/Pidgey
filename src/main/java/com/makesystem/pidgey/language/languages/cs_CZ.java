package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class cs_CZ implements Serializable, HasLanguage {
// Czech

    public static final String[] monthsFull = {"leden", "únor", "březen", "duben", "květen", "červen", "červenec", "srpen", "září", "říjen", "listopad", "prosinec"};
    public static final String[] monthsShort = {"led", "úno", "bře", "dub", "kvě", "čer", "čvc", "srp", "zář", "říj", "lis", "pro"};
    public static final String[] weekdaysFull = {"neděle", "pondělí", "úterý", "středa", "čtvrtek", "pátek", "sobota"};
    public static final String[] weekdaysShort = {"ne", "po", "út", "st", "čt", "pá", "so"};
    public static final String[] weekdaysLetter = {"Ne", "Po", "Út", "St", "Čt", "Pá", "So"};

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
