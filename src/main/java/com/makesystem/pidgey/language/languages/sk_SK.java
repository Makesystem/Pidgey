package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class sk_SK implements Serializable, HasLanguage {
// Slovak

    public static final String[] monthsFull = {"január", "február", "marec", "apríl", "máj", "jún", "júl", "august", "september", "október", "november", "december"};
    public static final String[] monthsShort = {"jan", "feb", "mar", "apr", "máj", "jún", "júl", "aug", "sep", "okt", "nov", "dec"};
    public static final String[] weekdaysFull = {"nedeľa", "pondelok", "utorok", "streda", "štvrtok", "piatok", "sobota"};
    public static final String[] weekdaysShort = {"Ne", "Po", "Ut", "St", "Št", "Pi", "So"};
    public static final String[] weekdaysLetter = {"Ne", "Po", "Ut", "St", "Št", "Pi", "So"};

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
