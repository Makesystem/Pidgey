package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class uk_UA implements Serializable, HasLanguage {
// Ukrainian

    public static final String[] monthsFull = {"січень", "лютий", "березень", "квітень", "травень", "червень", "липень", "серпень", "вересень", "жовтень", "листопад", "грудень"};
    public static final String[] monthsShort = {"січ", "лют", "бер", "кві", "тра", "чер", "лип", "сер", "вер", "жов", "лис", "гру"};
    public static final String[] weekdaysFull = {"неділя", "понеділок", "вівторок", "середа", "четвер", "п‘ятниця", "субота"};
    public static final String[] weekdaysShort = {"нд", "пн", "вт", "ср", "чт", "пт", "сб"};
    public static final String[] weekdaysLetter = {"нд", "пн", "вт", "ср", "чт", "пт", "сб"};

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
