package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class bg_BG implements Serializable, HasLanguage {
// Bulgarian

    public static final String[] monthsFull = {"януари", "февруари", "март", "април", "май", "юни", "юли", "август", "септември", "октомври", "ноември", "декември"};
    public static final String[] monthsShort = {"янр", "фев", "мар", "апр", "май", "юни", "юли", "авг", "сеп", "окт", "ное", "дек"};
    public static final String[] weekdaysFull = {"неделя", "понеделник", "вторник", "сряда", "четвъртък", "петък", "събота"};
    public static final String[] weekdaysShort = {"нд", "пн", "вт", "ср", "чт", "пт", "сб"};
    public static final String[] weekdaysLetter = {"Н", "П", "В", "С", "Ч", "П", "С"};

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
