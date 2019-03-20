package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class hi_IN implements Serializable, HasLanguage {

    public static final String[] monthsFull = {"जनवरी", "फरवरी", "मार्च", "अप्रैल", "मई", "जून", "जुलाई", "अगस्त", "सितम्बर", "अक्टूबर", "नवम्बर", "दिसम्बर"};
    public static final String[] monthsShort = {"जन", "फर", "मार्च", "अप्रैल", "मई", "जून", "जु", "अग", "सित", "अक्टू", "नव", "दिस"};
    public static final String[] weekdaysFull = {"रविवार", "सोमवार", "मंगलवार", "बुधवार", "गुरुवार", "शुक्रवार", "शनिवार"};
    public static final String[] weekdaysShort = {"रवि", "सोम", "मंगल", "बुध", "गुरु", "शुक्र", "शनि"};
    public static final String[] weekdaysLetter = {"रवि", "सोम", "मंगल", "बुध", "गुरु", "शुक्र", "शनि"};

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
