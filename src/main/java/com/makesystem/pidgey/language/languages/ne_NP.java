package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class ne_NP implements Serializable, HasLanguage {
// Nepali

    public static final String[] monthsFull = {"जनवरी", "फेब्रुअरी", "मार्च", "अप्रिल", "मे", "जुन", "जुलाई", "अगस्त", "सेप्टेम्बर", "अक्टोबर", "नोवेम्बर", "डिसेम्बर"};
    public static final String[] monthsShort = {"जन", "फेब्रु", "मार्च", "अप्रिल", "मे", "जुन", "जुल", "अग", "सेप्टे", "अक्टो", "नोभे", "डिसे"};
    public static final String[] weekdaysFull = {"सोमबार", "मङ्लबार", "बुधबार", "बिहीबार", "शुक्रबार", "शनिबार", "आईतबार"};
    public static final String[] weekdaysShort = {"सोम", "मंगल्", "बुध", "बिही", "शुक्र", "शनि", "आईत"};
    public static final String[] weekdaysLetter = {"सोम", "मंगल्", "बुध", "बिही", "शुक्र", "शनि", "आईत"};

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
