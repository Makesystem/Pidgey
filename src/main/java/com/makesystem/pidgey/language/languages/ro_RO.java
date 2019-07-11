package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class ro_RO implements Serializable, HasLanguage {
// Romanian

    public static final String[] monthsFull = {"ianuarie", "februarie", "martie", "aprilie", "mai", "iunie", "iulie", "august", "septembrie", "octombrie", "noiembrie", "decembrie"};
    public static final String[] monthsShort = {"ian", "feb", "mar", "apr", "mai", "iun", "iul", "aug", "sep", "oct", "noi", "dec"};
    public static final String[] weekdaysFull = {"duminică", "luni", "marţi", "miercuri", "joi", "vineri", "sâmbătă"};
    public static final String[] weekdaysShort = {"D", "L", "Ma", "Mi", "J", "V", "S"};
    public static final String[] weekdaysLetter = {"D", "L", "Ma", "Mi", "J", "V", "S"};

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
