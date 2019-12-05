/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.security;

import com.makesystem.pidgey.lang.StringHelper;
import java.io.Serializable;

/**
 *
 * @author riche
 */
abstract class AbstractPasswordMeter implements Serializable {

    private static final long serialVersionUID = 8427582862071678707L;

    public final static String PARTNER__STRONG_PASSWORD = "^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$";
    public final static String PARTNER__MEDIUM_PASSWORD = "^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$";
    public final static String PARTNER__ENOUGH_PASSWORD = "(?=.{6,}).*";

    public PasswordScore getScore(final String value) {

        final String toMatch = StringHelper.stripSeqs(value);

        if (StringHelper.isBlank(value) || value.length() == 0) {
            return PasswordScore.EMPYT;
        } else if (false == match(toMatch, PARTNER__ENOUGH_PASSWORD)) {
            return PasswordScore.VERY_WEAK;
        } else if (match(toMatch, PARTNER__STRONG_PASSWORD)) {
            return PasswordScore.STRONG;
        } else if (match(toMatch, PARTNER__MEDIUM_PASSWORD)) {
            return PasswordScore.MEDIUM;
        } else {
            return PasswordScore.WEAK;
        }
    }

    protected abstract boolean match(final String value, final String partner);
}
