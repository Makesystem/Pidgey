/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.formatation;

import com.makesystem.pidgey.console.Console;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 *
 * @author Richeli.vargas
 */
public class StringFormat {

    public static final String ACCENTED_A = "áàâãäÁÀÂÃÄ";
    public static final String ACCENTED_E = "éèêëÉÈÊË";
    public static final String ACCENTED_I = "íìîïÍÌÎÏ";
    public static final String ACCENTED_O = "óòôõöÓÒÔÕÖ";
    public static final String ACCENTED_U = "úùûüÚÙÛÜ";
    public static final String ACCENTED_C = "çÇ";
    public static final String ACCENTED_LETTERS
            = ACCENTED_A
            + ACCENTED_E
            + ACCENTED_I
            + ACCENTED_O
            + ACCENTED_U
            + ACCENTED_C;

    public static enum Only {

        NUMBERS("0-9"), 
        LETTERS("a-zA-Z" + ACCENTED_LETTERS), 
        ALPHANUMERICS("0-9A-Za-z" + ACCENTED_LETTERS);

        private final String partner;

        private Only(final String partner) {
            this.partner = partner;
        }

    };
    
    public static String only(final String string, final Only type, final String ... and) {
        return string.replaceAll("[^" + type.partner + Arrays.stream(and).collect(Collectors.joining()) +  "]", "");
    }

    public static String onlyNumbers(final String string) {
        return only(string, Only.NUMBERS);
    }

    public static String onlyAlphanumerics(final String string) {
        return only(string, Only.ALPHANUMERICS);
    }

    public static String onlyLetters(final String string) {
        return only(string, Only.LETTERS);
    }

    public static String format(final String format, final Object... values) {

        final Collection<String> params = new LinkedList<>();

        String temp = format;
        int indexOf = temp.indexOf("%-");
        while (indexOf > -1) {
            temp = temp.substring(indexOf, temp.length());
            final int indexOfS = temp.indexOf("s");
            params.add(temp.substring(0, indexOfS + 1));
            temp = temp.substring(indexOfS + 1);
            indexOf = temp.indexOf("%-");
        }

        if (params.size() != values.length) {
            throw new IllegalArgumentException(
                    "Number of %-s is "
                    + (params.size() < values.length ? "less" : "bigger")
                    + " than number of values");
        }

        final String[] parts = params.stream().toArray(String[]::new);
        final int size = Math.min(parts.length, values.length);
        String result = format;

        for (int index = 0; index < size; index++) {
            final Object value = values[index];
            final String part = parts[index].substring(0, parts[index].indexOf("s") + 1);
            final String numbers = onlyNumbers(part);
            final int length = numbers.length() == 0 ? 0 : Integer.valueOf(numbers);
            final String formattedValue = toString(value, length * Console.tabLength());
            result = result.replaceFirst(part, formattedValue);
        }

        return result;
    }

    private static String toString(final Object value, final int length) {

        final String valueAsString = value == null ? "" : value.toString();

        if (length == 0) {
            return valueAsString;
        } else {

            final StringBuilder builder = new StringBuilder(valueAsString);
            while (builder.length() < length) {
                builder.append(" ");
            }

            if (builder.length() > length) {
                if (length > 4) {
                    return builder.substring(0, length - 3) + "...";
                } else {
                    return builder.substring(0, length);
                }
            } else {
                return builder.toString();
            }
        }
    }
}
