package com.makesystem.pidgey.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class StringUtilImpl {

    private static final Map<Character, Character> MAP_NORM = new HashMap<Character, Character>();

    static {
        MAP_NORM.put('À', 'A');
        MAP_NORM.put('Á', 'A');
        MAP_NORM.put('Â', 'A');
        MAP_NORM.put('Ã', 'A');
        MAP_NORM.put('Ä', 'A');
        MAP_NORM.put('È', 'E');
        MAP_NORM.put('É', 'E');
        MAP_NORM.put('Ê', 'E');
        MAP_NORM.put('Ë', 'E');
        MAP_NORM.put('Í', 'I');
        MAP_NORM.put('Ì', 'I');
        MAP_NORM.put('Î', 'I');
        MAP_NORM.put('Ï', 'I');
        MAP_NORM.put('Ù', 'U');
        MAP_NORM.put('Ú', 'U');
        MAP_NORM.put('Û', 'U');
        MAP_NORM.put('Ü', 'U');
        MAP_NORM.put('Ò', 'O');
        MAP_NORM.put('Ó', 'O');
        MAP_NORM.put('Ô', 'O');
        MAP_NORM.put('Õ', 'O');
        MAP_NORM.put('Ö', 'O');
        MAP_NORM.put('Ñ', 'N');
        MAP_NORM.put('Ç', 'C');
        //MAP_NORM.put('ª', 'A');
        MAP_NORM.put('ª', ' ');
        //MAP_NORM.put('º', 'O');
        MAP_NORM.put('º', ' ');
        MAP_NORM.put('§', 'S');
        MAP_NORM.put('³', '3');
        MAP_NORM.put('²', '2');
        MAP_NORM.put('¹', '1');
        MAP_NORM.put('à', 'a');
        MAP_NORM.put('á', 'a');
        MAP_NORM.put('â', 'a');
        MAP_NORM.put('ã', 'a');
        MAP_NORM.put('ä', 'a');
        MAP_NORM.put('è', 'e');
        MAP_NORM.put('é', 'e');
        MAP_NORM.put('ê', 'e');
        MAP_NORM.put('ë', 'e');
        MAP_NORM.put('í', 'i');
        MAP_NORM.put('ì', 'i');
        MAP_NORM.put('î', 'i');
        MAP_NORM.put('ï', 'i');
        MAP_NORM.put('ù', 'u');
        MAP_NORM.put('ú', 'u');
        MAP_NORM.put('û', 'u');
        MAP_NORM.put('ü', 'u');
        MAP_NORM.put('ò', 'o');
        MAP_NORM.put('ó', 'o');
        MAP_NORM.put('ô', 'o');
        MAP_NORM.put('õ', 'o');
        MAP_NORM.put('ö', 'o');
        MAP_NORM.put('ñ', 'n');
        MAP_NORM.put('ç', 'c');
    }

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
     * "\t"
     */
    public static final String TB = "\t";

    /**
     * ""
     */
    public static final String EMPTY = "";

    /**
     * " "
     */
    public static final String SPACE = " ";

    /**
     * "[^0-9]"
     */
    public static final String REGEX_NOT_NUMBER = "[^0-9]";
    /**
     * "[^a-zA-Z0-9 ]+"
     */
    public static final String REGEX_NOT_ALPHANUMERIC = "[^a-zA-Z0-9 ]+";
    /**
     * "\\s+"
     */
    public static final String REGEX_ALL_SPACES = "\\s+";

    /**
     * "[\\s]+"
     */
    public static final String REGEX_ONE_SPACES = "[\\s]+";

    protected static final String MD5 = "MD5";
    protected static final String ZERO = "0";
    protected static final String AT = " at ";
    protected static final String CAUSED_BY = "Caused by: ";
    protected static final String MINUS_D = "-D";
    protected static final String EQUAL = "=";
    
    public static String removeAccents(String value) {

        if (value == null) {
            return EMPTY;
        }

        final StringBuilder sb = new StringBuilder(value);

        for (int i = 0; i < value.length(); i++) {
            final Character c = MAP_NORM.get(sb.charAt(i));
            if (c != null) {
                sb.setCharAt(i, c.charValue());
            }
        }

        return sb.toString();

    }

    public static String delAllDifferentOf(final String string, final String diferentOf) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        if (diferentOf != null && !diferentOf.trim().isEmpty()) {
            return string.replaceAll(diferentOf, EMPTY);
        }
        return EMPTY;
    }

    public static String delAllSpecialCharacter(final String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        return string.replaceAll(REGEX_NOT_ALPHANUMERIC, EMPTY);
    }

    public static String delAllSpaces(String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        return string.replaceAll(REGEX_ALL_SPACES, EMPTY);
    }

    public static String justOnlyOneSpace(String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        return string.replaceAll(REGEX_ONE_SPACES, SPACE);
    }

    public static String toMD5(String string) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance(MD5);
        final BigInteger hash = new BigInteger(1, md.digest(string.getBytes()));
        String s = hash.toString(16);
        if (s.length() % 2 != 0) {
            s = ZERO + s;
        }
        return s;
    }

    public static String getString(final Throwable throwable) {

        if (throwable == null) {
            return EMPTY;
        }

        // O nome da property não pode estar em uma variável estática 
        // porque gera erro na hora de compilar no GWT
        final String NEW_LINE = System.getProperty("line.separator", LF);
        final String TAB_LINE = System.getProperty("line.tab", TB);

        // add the class name and any message passed to constructor
        final StringBuilder result = new StringBuilder();
        result.append(throwable.toString());
        result.append(NEW_LINE);

        // add each element of the stack trace
        for (StackTraceElement element : throwable.getStackTrace()) {
            result.append(TAB_LINE);
            result.append(AT);
            result.append(element);
            result.append(NEW_LINE);
        }

        // add cause
        if (throwable.getCause() != null) {
            result.append(CAUSED_BY);
            result.append(getString(throwable.getCause()));
        }

        return result.toString();
    }

    public static Map<String, String> parserJavaOpts(final String javaOpt) {

        final Map<String, String> properties = new HashMap<>();

        if (javaOpt == null || javaOpt.isEmpty()) {
            return properties;
        }

        final String[] options = javaOpt.replaceAll(MINUS_D, EMPTY).split(SPACE);
        for (String option : options) {
            final String[] map = option.split(EQUAL);
            final String key = map[0];
            final String value = map[1];
            properties.put(key, value);
        }

        return properties;
    }

    public static String completeStartWith(String value, final char c, final int finalSize) {
        while (value.length() < finalSize) {
            value = c + value;
        }
        return value;
    }

    public static String completeEndWith(String value, final char c, final int finalSize) {
        while (value.length() < finalSize) {
            value = value + c;
        }
        return value;
    }

    public static String toNumeric(final String value) {
        return MathUtils.isExponential(value) ? String.valueOf(MathUtils.fromExponential(value)) : value.replaceAll(REGEX_NOT_NUMBER, EMPTY);
    }

    
    public static String replaceAll(String source, final String key, final String replacement) {
        while (source.contains(key)) {
            source = source.replace(key, replacement);
        }
        return source;
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean contains(final CharSequence seq, final int searchChar) {
        if (isEmpty(seq)) {
            return false;
        }
        return CharSequenceUtils.indexOf(seq, searchChar, 0) >= 0;
    }

    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String concat(final String separator, final Object... data) {
        return Arrays.stream(data).map(var -> var == null ? EMPTY : var.toString()).collect(Collectors.joining(separator));
    }

    /**
     * Remove these sequences:
     * <code>
     * <br/> '123'
     * <br/> 'abc' <br/> 'ABC'
     * <br/> 'aaa' <br/> 'AAA'
     * </code>
     *
     * @param value
     * @return
     */
    public static final String stripSeqs(final String value) {

        if (isBlank(value)) {
            return value;
        }

        final char[] chars = value.toCharArray();
        return streamToStrepSeq(value).mapToObj(index -> String.valueOf((char) chars[index])).collect(Collectors.joining());
    }

    /**
     * Remove these sequences:
     * <code>
     * <br/> '123'
     * <br/> 'abc' <br/> 'ABC' <br/> 'AbCd'
     * <br/> 'aaa' <br/> 'AAA' <br/> 'AaAa'
     * </code>
     *
     * @param value
     * @return
     */
    public static final String stripSeqsIgnoreCase(final String value) {

        if (isBlank(value)) {
            return value;
        }

        final char[] chars = value.toCharArray();
        return streamToStrepSeq(value.toLowerCase()).mapToObj(index -> String.valueOf((char) chars[index])).collect(Collectors.joining());
    }

    protected static final IntStream streamToStrepSeq(final String value) {
        final char[] chars = value.toCharArray();
        return IntStream.range(0, chars.length).filter(index -> {

            final int last = index > 0 ? (int) chars[index - 1] : -1;
            final int curr = chars[index];
            final int next = (index + 1) < chars.length ? (int) chars[index + 1] : -1;

            final boolean lastChar = Character.isLetterOrDigit((char) last);
            final boolean currChar = Character.isLetterOrDigit((char) curr);
            final boolean nextChar = Character.isLetterOrDigit((char) next);

            final boolean lastIsSeq = currChar && lastChar && (curr == (last + 1) || curr == last);
            final boolean nextIsSeq = currChar && nextChar && (curr == (next - 1) || curr == next);

            return !lastIsSeq && !nextIsSeq;

        });
    }
}
