package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class ru_RU implements Serializable, HasLanguage {
// Russian

    public static final String[] monthsFull = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
    public static final String[] monthsShort = {"янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
    public static final String[] weekdaysFull = {"воскресенье", "понедельник", "вторник", "среда", "четверг", "пятница", "суббота"};
    public static final String[] weekdaysShort = {"вс", "пн", "вт", "ср", "чт", "пт", "сб"};
    public static final String[] weekdaysLetter = {"вс", "пн", "вт", "ср", "чт", "пт", "сб"};

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
