/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.formatation;

import com.makesystem.pidgey.lang.StringHelper;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Richeli.vargas
 */
public class NumericFormat implements Serializable {

    private static final long serialVersionUID = -4815089416428521144L;

    /**
     * Hepta Bytes
     */
    public static final String HB = "HB";
    
    /**
     * Tera Bytes
     */
    public static final String TB = "TB";
    
    /**
     * Giga Bytes
     */
    public static final String GB = "GB";
    
    /**
     * Mega Bytes
     */
    public static final String MB = "MB";
    
    /**
     * Kilo Bytes
     */
    public static final String KB = "KB";
    
    /**
     * Bytes
     */
    public static final String BYTES = "Bytes";

    public static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final String ZERO = "0";

    private static final String MINUS_DOT = "-.";

    /**
     *
     * @param decimal
     * @return
     */
    public static String toHex(long decimal) {
        int rem;
        String hex = StringHelper.EMPTY;
        while (decimal > 0) {
            rem = (int) (decimal % 16);
            hex = HEX_CHARS[rem] + hex;
            decimal = decimal / 16;
        }
        return hex;
    }

    /**
     * Full with leading zeros up to the specified length
     *
     * @param value
     * @param length
     * @return
     */
    public static String specificLength(final long value, final int length) {
        final String toString = String.valueOf(value);
        final StringBuilder builder = new StringBuilder();
        final int toComplete = length - toString.length();
        for (int i = 0; i < toComplete; i++) {
            builder.append(ZERO);
        }
        builder.append(toString);
        return builder.toString();
    }

    public static String currency(final double value) {

        final BigDecimal bigDecimal = new BigDecimal(String.valueOf(value)).setScale(2, BigDecimal.ROUND_FLOOR);
        final String[] values = bigDecimal.toString().split(StringHelper.REAL_DOT);

        final StringBuilder builder = new StringBuilder(values[0]);

        final int countDot = (builder.toString().length() / 3) - (builder.toString().length() % 3 == 0 ? 1 : 0);

        for (int i = 0, p = 1; i < countDot; i++, p++) {
            builder.insert(builder.toString().length() - ((3 * p) + i), StringHelper.DOT);
        }

        builder.append(StringHelper.COMMA);
        builder.append(values[1]);

        return builder.toString().replace(MINUS_DOT, StringHelper.MS);
    }

    public static String integer(final double value) {

        final BigDecimal bigDecimal = new BigDecimal(String.valueOf(value)).setScale(2, BigDecimal.ROUND_FLOOR);
        final String[] values = bigDecimal.toString().split(StringHelper.REAL_DOT);

        final StringBuilder builder = new StringBuilder(values[0]);

        final int countDot = (builder.toString().length() / 3) - (builder.toString().length() % 3 == 0 ? 1 : 0);

        for (int i = 0, p = 1; i < countDot; i++, p++) {
            builder.insert(builder.toString().length() - ((3 * p) + i), StringHelper.DOT);
        }

        return builder.toString();
    }

    public static String bytes(final long size) {

        final String value = String.valueOf(size);
        final int length = value.length();

        if (length > 15) {
            return currency((double) (size / (Math.pow(1000, 5)))) + HB;
        } else if (length > 12) {
            return currency((double) (size / (Math.pow(1000, 4)))) + TB;
        } else if (length > 9) {
            return currency((double) (size / (Math.pow(1000, 3)))) + GB;
        } else if (length > 6) {
            return currency((double) (size / (Math.pow(1000, 2)))) + MB;
        } else if (length > 3) {
            return currency((double) (size / (Math.pow(1000, 1)))) + KB;
        } else {
            return size + StringHelper.SPACE + BYTES;
        }
    }
}
