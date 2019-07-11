package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class hr_HR implements Serializable, HasLanguage {
// Croatian

    public static final String[] monthsFull = {"sijećanj", "veljača", "ožujak", "travanj", "svibanj", "lipanj", "srpanj", "kolovoz", "rujan", "listopad", "studeni", "prosinac"};
    public static final String[] monthsShort = {"sij", "velj", "ožu", "tra", "svi", "lip", "srp", "kol", "ruj", "lis", "stu", "pro"};
    public static final String[] weekdaysFull = {"nedjelja", "ponedjeljak", "utorak", "srijeda", "četvrtak", "petak", "subota"};
    public static final String[] weekdaysShort = {"ned", "pon", "uto", "sri", "čet", "pet", "sub"};
    public static final String[] weekdaysLetter = {"Ne", "Po", "Ut", "Sr", "Če", "Pe", "Su"};

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
