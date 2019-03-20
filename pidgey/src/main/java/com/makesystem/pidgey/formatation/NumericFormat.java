/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.formatation;

import java.math.BigDecimal;

/**
 *
 * @author Richeli.vargas
 */
public class NumericFormat {
 
    public static String hex(final long value){
        return Long.toHexString(value).toUpperCase();
    }
    
    public static String currency(final double value) {

        final BigDecimal bigDecimal = new BigDecimal(String.valueOf(value)).setScale(2, BigDecimal.ROUND_FLOOR);
        final String[] values = bigDecimal.toString().split("\\.");

        final StringBuilder builder = new StringBuilder(values[0]);

        final int countDot = (builder.toString().length() / 3) - (builder.toString().length() % 3 == 0 ? 1 : 0);

        for (int i = 0, p = 1; i < countDot; i++, p++) {
            builder.insert(builder.toString().length() - ((3 * p) + i), ".");
        }

        builder.append(",");
        builder.append(values[1]);

        return builder.toString().replace("-.", "-");
    }

    public static String integer(final double value) {

        final BigDecimal bigDecimal = new BigDecimal(String.valueOf(value)).setScale(2, BigDecimal.ROUND_FLOOR);
        final String[] values = bigDecimal.toString().split("\\.");

        final StringBuilder builder = new StringBuilder(values[0]);

        final int countDot = (builder.toString().length() / 3) - (builder.toString().length() % 3 == 0 ? 1 : 0);

        for (int i = 0, p = 1; i < countDot; i++, p++) {
            builder.insert(builder.toString().length() - ((3 * p) + i), ".");
        }

        return builder.toString();
    }

    public static String bytes(final long size) {

        final String value = String.valueOf(size);
        final int length = value.length();

        if (length > 15) {
            return currency((double) (size / (Math.pow(1000, 5)))) + " HB";
        } else if (length > 12) {
            return currency((double) (size / (Math.pow(1000, 4)))) + " TB";
        } else if (length > 9) {
            return currency((double) (size / (Math.pow(1000, 3)))) + " GB";
        } else if (length > 6) {
            return currency((double) (size / (Math.pow(1000, 2)))) + " MB";
        } else if (length > 3) {
            return currency((double) (size / (Math.pow(1000, 1)))) + " KB";
        } else {
            return size + " Bytes";
        }
    }
    
}
