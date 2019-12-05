/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.security;

/**
 *
 * @author riche
 */
public class PasswordMeterGWT extends AbstractPasswordMeter {

    private static final long serialVersionUID = -1653080120240066380L;

    @Override
    protected native boolean match(final String value, final String partner) /*-{
        return (new RegExp(partner, "g")).test(value);
    }-*/;

}
