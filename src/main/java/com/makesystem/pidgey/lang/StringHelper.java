/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author riche
 */
public class StringHelper {

    /**
     * A String for a space character.
     *
     */
    public static final String SPACE = " ";

    /**
     * The empty String {@code ""}.
     *
     */
    public static final String EMPTY = "";

    /**
     * A String for linefeed LF ("\n").
     *
     * @see
     * <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF:
     * Escape Sequences for Character and String Literals</a>
     */
    public static final String LF = "\n";

    /**
     * A String for carriage return CR ("\r").
     *
     * @see
     * <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF:
     * Escape Sequences for Character and String Literals</a>
     */
    public static final String CR = "\r";

    /**
     *
     */
    public static final String TB = "\t";

    /**
     * a-zA-ZÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜàáâãäçèéêëìíîïñòóôõöùúûü
     */
    public static final String LETTERS_FOR_PARTNERS;

    /**
     * 0-9
     */
    public static final String NUMBERS_FOR_PARTNERS;

    public static final Map<Character, Character> NORMALIZE_LETTERS;

    public static final Map<Character, Character> NORMALIZE_CHARS;

    static {

        final Map<Character, Character> normalizeLetters = new HashMap<>();
        normalizeLetters.put('À', 'A');
        normalizeLetters.put('Á', 'A');
        normalizeLetters.put('Â', 'A');
        normalizeLetters.put('Ã', 'A');
        normalizeLetters.put('Ä', 'A');
        normalizeLetters.put('È', 'E');
        normalizeLetters.put('É', 'E');
        normalizeLetters.put('Ê', 'E');
        normalizeLetters.put('Ë', 'E');
        normalizeLetters.put('Í', 'I');
        normalizeLetters.put('Ì', 'I');
        normalizeLetters.put('Î', 'I');
        normalizeLetters.put('Ï', 'I');
        normalizeLetters.put('Ù', 'U');
        normalizeLetters.put('Ú', 'U');
        normalizeLetters.put('Û', 'U');
        normalizeLetters.put('Ü', 'U');
        normalizeLetters.put('Ò', 'O');
        normalizeLetters.put('Ó', 'O');
        normalizeLetters.put('Ô', 'O');
        normalizeLetters.put('Õ', 'O');
        normalizeLetters.put('Ö', 'O');
        normalizeLetters.put('Ñ', 'N');
        normalizeLetters.put('Ç', 'C');
        normalizeLetters.put('à', 'a');
        normalizeLetters.put('á', 'a');
        normalizeLetters.put('â', 'a');
        normalizeLetters.put('ã', 'a');
        normalizeLetters.put('ä', 'a');
        normalizeLetters.put('è', 'e');
        normalizeLetters.put('é', 'e');
        normalizeLetters.put('ê', 'e');
        normalizeLetters.put('ë', 'e');
        normalizeLetters.put('í', 'i');
        normalizeLetters.put('ì', 'i');
        normalizeLetters.put('î', 'i');
        normalizeLetters.put('ï', 'i');
        normalizeLetters.put('ù', 'u');
        normalizeLetters.put('ú', 'u');
        normalizeLetters.put('û', 'u');
        normalizeLetters.put('ü', 'u');
        normalizeLetters.put('ò', 'o');
        normalizeLetters.put('ó', 'o');
        normalizeLetters.put('ô', 'o');
        normalizeLetters.put('õ', 'o');
        normalizeLetters.put('ö', 'o');
        normalizeLetters.put('ñ', 'n');
        normalizeLetters.put('ç', 'c');

        final Map<Character, Character> normalizeChars = new HashMap<>();
        normalizeChars.putAll(normalizeLetters);
        normalizeChars.put('ª', ' ');
        normalizeChars.put('º', ' ');
        normalizeChars.put('§', 'S');
        normalizeChars.put('³', '3');
        normalizeChars.put('²', '2');
        normalizeChars.put('¹', '1');

        NORMALIZE_LETTERS = Collections.unmodifiableMap(normalizeLetters);
        NORMALIZE_CHARS = Collections.unmodifiableMap(normalizeChars);
        LETTERS_FOR_PARTNERS = "a-zA-Z" + NORMALIZE_LETTERS.keySet().stream().map(character -> String.valueOf(character)).collect(Collectors.joining());
        NUMBERS_FOR_PARTNERS = "0-9";
    }

    public static String stripAccents(final String value) {

        if (value == null) {
            return "";
        }

        final StringBuilder sb = new StringBuilder(value);

        for (int i = 0; i < value.length(); i++) {
            final Character c = NORMALIZE_CHARS.get(sb.charAt(i));
            if (c != null) {
                sb.setCharAt(i, c);
            }
        }

        return sb.toString();

    }

    public static final String stripSpecialChars(final String value) {
        return stripSpecialChars(value, "");
    }

    public static final String stripSpecialChars(final String value, final String preserve) {

        if (value == null || value.trim().isEmpty()) {
            return value;
        }

        final StringBuilder paterner = new StringBuilder();
        paterner.append(LETTERS_FOR_PARTNERS);
        paterner.append(NUMBERS_FOR_PARTNERS);
        if (preserve != null) {
            paterner.append(preserve);
        }

        return stripDifferentOf(value, paterner.toString());
    }

    public static final String stripDifferentOfLetter(final String value) {
        return stripDifferentOf(value, LETTERS_FOR_PARTNERS);
    }

    public static final String stripDifferentOfNumber(final String value) {
        return stripDifferentOf(value, NUMBERS_FOR_PARTNERS);
    }

    public static final String stripDifferentOf(final String value, final String preserve) {

        if (preserve == null || preserve.isEmpty()) {
            throw new IllegalArgumentException("Param 'preserve' can not be null or empty");
        }

        if (value == null || value.trim().isEmpty()) {
            return value;
        }

        final StringBuilder paterner = new StringBuilder();
        paterner.append("[^");
        paterner.append(preserve);
        paterner.append("]+");

        return value.replaceAll(paterner.toString(), "");
    }

    /**
     * Concat values with null prevent
     *
     * @param separator
     * @param data
     * @return
     */
    public static String concat(final String separator, final Object... data) {
        return Arrays.stream(data).map(var -> var == null ? "" : var.toString()).collect(Collectors.joining(separator));
    }

    /**
     *
     * @param value
     * @param dataToAppend
     * @param finalSize
     * @return
     */
    public static String appendAtStart(String value, final String dataToAppend, final int finalSize) {
        while (value.length() < finalSize) {
            value = dataToAppend + value;
        }
        return value;
    }

    /**
     *
     * @param value
     * @param dataToAppend
     * @param finalSize
     * @return
     */
    public static String appendAtEnd(String value, final String dataToAppend, final int finalSize) {
        while (value.length() < finalSize) {
            value = value + dataToAppend;
        }
        return value;
    }

    /**
     * 
     * @param value
     * @param maxLength
     * @return 
     */
    public static final String maxLength(final String value, final int maxLength) {
        return value == null ? null
                : value.substring(0,
                        Math.min(maxLength, value.length()));
    }

    /**
     *
     * @param javaOpt -Xms1024m -Dmy_prop=my_value ...
     * @return
     */
    public static Map<String, String> readJavaOpts(final String javaOpt) {

        final Map<String, String> properties = new HashMap<>();

        if (javaOpt == null || javaOpt.isEmpty()) {
            return properties;
        }

        final String[] options = javaOpt.replaceAll("-D", "").split(" ");
        for (String option : options) {
            final String[] map = option.split("=");
            final String key = map[0];
            final String value = map[1];
            properties.put(key, value);
        }

        return properties;
    }

    public static String toString(final Throwable throwable) {

        if (throwable == null) {
            return "";
        }

        final String NEW_LINE = System.getProperty("line.separator", LF);
        final String TAB_LINE = System.getProperty("line.tab", TB);

        // add the class name and any message passed to constructor
        final StringBuilder result = new StringBuilder();
        result.append(throwable.toString());
        result.append(NEW_LINE);

        // add each element of the stack trace
        for (StackTraceElement element : throwable.getStackTrace()) {
            result.append(TAB_LINE);
            result.append(" at ");
            result.append(element);
            result.append(NEW_LINE);
        }

        // add cause
        if (throwable.getCause() != null) {
            result.append("Caused by: ");
            result.append(toString(throwable.getCause()));
        }

        return result.toString();
    }
}
