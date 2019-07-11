package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class ca_ES implements Serializable, HasLanguage {
// Catalan

    public static final String[] monthsFull = {"Gener", "Febrer", "Març", "Abril", "Maig", "juny", "Juliol", "Agost", "Setembre", "Octubre", "Novembre", "Desembre"};
    public static final String[] monthsShort = {"Gen", "Feb", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Oct", "Nov", "Des"};
    public static final String[] weekdaysFull = {"diumenge", "dilluns", "dimarts", "dimecres", "dijous", "divendres", "dissabte"};
    public static final String[] weekdaysShort = {"diu", "dil", "dim", "dmc", "dij", "div", "dis"};
    public static final String[] weekdaysLetter = {"dg", "dl", "dt", "dc", "dj", "dv", "ds"};

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
