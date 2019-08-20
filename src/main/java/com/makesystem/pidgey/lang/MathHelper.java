/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Richeli.vargas
 */
public class MathHelper {

    protected static String preventComma(final String value) {
        if (value.contains(",")) {
            return value.replace(".", "").replace(",", ".");
        } else {
            return value;
        }
    }

    public static boolean isExponential(final String exponential) {
        if (exponential == null || exponential.isEmpty()) {
            return false;
        } else {
            return preventComma(exponential).replaceAll("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)", "").isEmpty();
        }
    }

    public static long fromExponential(final String exponential) {
        final BigDecimal bd = new BigDecimal(preventComma(exponential));
        return bd.longValue();
    }

    public final static long divide(final long valueOne, final long valueTwo) {
        if (valueOne == 0 || valueTwo == 0) {
            return 0;
        }
        return valueOne / valueTwo;
    }

    public final static long calcDifference(final long valueOne, final long valueTwo) {
        return valueOne > valueTwo ? valueOne - valueTwo : valueTwo - valueOne;
    }

    public final static long toMillis(final long value, final TimeUnit unit) {
        switch (unit) {
            case DAYS:
                return value * 24L * 60L * 60L * 1000L;
            case HOURS:
                return value * 60L * 60L * 1000L;
            case MINUTES:
                return value * 60L * 1000L;
            case SECONDS:
                return value * 1000L;
            case MILLISECONDS:
                return value;
            case MICROSECONDS:
                return value / 1000L;
            case NANOSECONDS:
                return value / 1000L / 1000L;
            default:
                return value;
        }
    }
}
