package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class ko_KR implements Serializable, HasLanguage {
// Korean

    public static final String[] monthsFull = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};
    public static final String[] monthsShort = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};
    public static final String[] weekdaysFull = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
    public static final String[] weekdaysShort = {"일", "월", "화", "수", "목", "금", "토"};
    public static final String[] weekdaysLetter = {"일", "월", "화", "수", "목", "금", "토"};

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
