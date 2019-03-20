package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class gl_ES implements Serializable, HasLanguage {
// Galician

    public static final String[] monthsFull = {"Xaneiro", "Febreiro", "Marzo", "Abril", "Maio", "Xuño", "Xullo", "Agosto", "Setembro", "Outubro", "Novembro", "Decembro"};
    public static final String[] monthsShort = {"xan", "feb", "mar", "abr", "mai", "xun", "xul", "ago", "sep", "out", "nov", "dec"};
    public static final String[] weekdaysFull = {"domingo", "luns", "martes", "mércores", "xoves", "venres", "sábado"};
    public static final String[] weekdaysShort = {"dom", "lun", "mar", "mér", "xov", "ven", "sab"};
    public static final String[] weekdaysLetter = {"Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"};

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
