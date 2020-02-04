/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.security;

import com.google.gwt.core.shared.GwtIncompatible;

/**
 *
 * @author Richeli Vargas
 */
@GwtIncompatible
public class PasswordMeterJRE extends AbstractPasswordMeter {

    private static final long serialVersionUID = -654056078881971687L;

    @Override
    protected boolean match(final String value, final String partner) {
        return java.util.regex.Pattern.compile(partner).matcher(value).find();
    }
}
