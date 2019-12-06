/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.security;

/**
 *
 * @author Richeli Vargas
 */
public class PasswordMeterJRE extends AbstractPasswordMeter {

    private static final long serialVersionUID = -4463118814109329719L;

    @Override
    public boolean match(final String value, final String partner) {
        return java.util.regex.Pattern.compile(partner).matcher(value).find();
    }
}