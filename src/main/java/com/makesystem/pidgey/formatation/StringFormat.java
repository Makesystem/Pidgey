/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.formatation;

import com.makesystem.pidgey.formatation.printf.ConversionFormatter;
import com.makesystem.pidgey.formatation.printf.Printf;
import java.util.Arrays;
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
    
    public static String only(final String string, final String ... and) {
        return string.replaceAll("[^" + Arrays.stream(and).collect(Collectors.joining()) +  "]", "");
    }
    
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

    public static String printf(final String format, final Object... values) {
        return printf(format, ConversionFormatter.DEFAULT_PADDING_CHAR, values);
    }
    
    public static String printf(final String format, final char paddingChar, final Object... values) {
        final Printf printf = new Printf();
        printf.setPaddingChar(paddingChar);
        return printf.toString(format, values);
    }
    
    public static String completeLeft(String source, final char character, final int length){
        while (source.length() < length) {
            source = String.valueOf(character) + source;
        }
        return source;
    }
    
    public static String completeRight(String source, final char character, final int length){
        while (source.length() < length) {
            source = source + String.valueOf(character);
        }
        return source;
    }
}
