package com.makesystem.pidgey.language.languages;

import java.io.Serializable;
import com.makesystem.pidgey.language.HasLanguage;

public class zh_TW implements Serializable, HasLanguage {
// Traditional Chinese

    public static final String[] monthsFull = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
    public static final String[] monthsShort = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    public static final String[] weekdaysFull = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public static final String[] weekdaysShort = {"日", "一", "二", "三", "四", "五", "六"};
    public static final String[] weekdaysLetter = {"日", "一", "二", "三", "四", "五", "六"};

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
