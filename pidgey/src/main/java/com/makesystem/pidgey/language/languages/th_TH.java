package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class th_TH implements Serializable, HasLanguage {
// Thai

    public static final String[] monthsFull = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};
    public static final String[] monthsShort = {"ม.ค.", "ก.พ.", "มี.ค.", "เม.ย.", "พ.ค.", "มิ.ย.", "ก.ค.", "ส.ค.", "ก.ย.", "ต.ค.", "พ.ย.", "ธ.ค."};
    public static final String[] weekdaysFull = {"อาทติย", "จันทร", "องัคาร", "พุธ", "พฤหสั บดี", "ศกุร", "เสาร"};
    public static final String[] weekdaysShort = {"อ.", "จ.", "อ.", "พ.", "พฤ.", "ศ.", "ส."};
    public static final String[] weekdaysLetter = {"อ.", "จ.", "อ.", "พ.", "พฤ.", "ศ.", "ส."};

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
