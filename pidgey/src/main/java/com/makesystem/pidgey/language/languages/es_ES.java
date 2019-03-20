package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class es_ES implements Serializable, HasLanguage {
// Spanish

    public static final String[] monthsFull = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
    public static final String[] monthsShort = {"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};
    public static final String[] weekdaysFull = {"domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"};
    public static final String[] weekdaysShort = {"dom", "lun", "mar", "mié", "jue", "vie", "sáb"};
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
