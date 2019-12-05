/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.language;

import com.makesystem.pidgey.language.languages.*;
import java.io.Serializable;

/**
 *
 * @author Richeli Vargas
 */
public class Language implements Serializable {

    private static final long serialVersionUID = -229572251085663896L;

    /**
     *
     * @param month 1 ... 12
     * @param locale
     * @return
     */
    public static String monthFull(final int month, final String locale) {
        return getLanguage(locale).getMonthsFull()[month - 1];
    }

    /**
     *
     * @param month 1 ... 12
     * @param locale
     * @return
     */
    public static String monthShort(final int month, final String locale) {
        return getLanguage(locale).getMonthsShort()[month - 1];
    }

    /**
     *
     * @param weekDay 0 ... 6
     * @param locale
     * @return
     */
    public static String weekdayFull(final int weekDay, final String locale) {
        return getLanguage(locale).getWeekdaysFull()[weekDay];
    }

    /**
     *
     * @param weekDay 0 ... 6
     * @param locale
     * @return
     */
    public static String weekdayShort(final int weekDay, final String locale) {
        return getLanguage(locale).getWeekdaysFull()[weekDay];
    }

    /**
     *
     * @param weekDay 0 ... 6
     * @param locale
     * @return
     */
    public static String weekdaysLetter(final int weekDay, final String locale) {
        return getLanguage(locale).getWeekdaysFull()[weekDay];
    }

    private static HasLanguage getLanguage(final String locale) {

        switch (locale) {
            case Locales.ar:
                return new ar();
            case Locales.bg_BG:
                return new bg_BG();
            case Locales.bs_BA:
                return new bs_BA();
            case Locales.ca_ES:
                return new ca_ES();
            case Locales.cs_CZ:
                return new cs_CZ();
            case Locales.da_DK:
                return new da_DK();
            case Locales.de_DE:
                return new de_DE();
            case Locales.el_GR:
                return new el_GR();
            case Locales.en:
                return new en();
            case Locales.es_ES:
                return new es_ES();
            case Locales.et_EE:
                return new et_EE();
            case Locales.eu_ES:
                return new eu_ES();
            case Locales.fa_ir:
                return new fa_ir();
            case Locales.fi_FI:
                return new fi_FI();
            case Locales.fr_FR:
                return new fr_FR();
            case Locales.gl_ES:
                return new gl_ES();
            case Locales.he_IL:
                return new he_IL();
            case Locales.hi_IN:
                return new hi_IN();
            case Locales.hr_HR:
                return new hr_HR();
            case Locales.hu_HU:
                return new hu_HU();
            case Locales.id_ID:
                return new id_ID();
            case Locales.is_IS:
                return new is_IS();
            case Locales.it_IT:
                return new it_IT();
            case Locales.ja_JP:
                return new ja_JP();
            case Locales.ko_KR:
                return new ko_KR();
            case Locales.lt_LT:
                return new lt_LT();
            case Locales.lv_LV:
                return new lv_LV();
            case Locales.nb_NO:
                return new nb_NO();
            case Locales.ne_NP:
                return new ne_NP();
            case Locales.nl_NL:
                return new nl_NL();
            case Locales.pl_PL:
                return new pl_PL();
            case Locales.pt_PT:
                return new pt_PT();
            case Locales.ro_RO:
                return new ro_RO();
            case Locales.ru_RU:
                return new ru_RU();
            case Locales.sk_SK:
                return new sk_SK();
            case Locales.sl_SI:
                return new sl_SI();
            case Locales.sv_SE:
                return new sv_SE();
            case Locales.th_TH:
                return new th_TH();
            case Locales.tr_TR:
                return new tr_TR();
            case Locales.uk_UA:
                return new uk_UA();
            case Locales.vi_VN:
                return new vi_VN();
            case Locales.zh_CN:
                return new zh_CN();
            case Locales.zh_TW:
                return new zh_TW();
            case Locales.pt_BR:
            default:
                return new pt_BR();
        }

    }
}
