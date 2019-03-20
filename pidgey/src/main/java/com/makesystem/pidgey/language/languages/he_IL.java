package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class he_IL implements Serializable, HasLanguage {
// Hebrew

    public static final String[] monthsFull = {"ינואר", "פברואר", "מרץ", "אפריל", "מאי", "יוני", "יולי", "אוגוסט", "ספטמבר", "אוקטובר", "נובמבר", "דצמבר"};
    public static final String[] monthsShort = {"ינו", "פבר", "מרץ", "אפר", "מאי", "יונ", "יול", "אוג", "ספט", "אוק", "נוב", "דצמ"};
    public static final String[] weekdaysFull = {"יום ראשון", "יום שני", "יום שלישי", "יום רביעי", "יום חמישי", "יום ששי", "יום שבת"};
    public static final String[] weekdaysShort = {"א", "ב", "ג", "ד", "ה", "ו", "ש"};
    public static final String[] weekdaysLetter = {"א", "ב", "ג", "ד", "ה", "ו", "ש"};

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
