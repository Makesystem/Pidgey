/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author riche
 */
public class StringHelper implements Serializable {

    private static final long serialVersionUID = -5088130764712821036L;

    private static final int NOT_FOUND = -1;

    /**
     * Match any char zero or one times.
     * String {@code ".?"}
     *
     */
    public static final String REGEX_ANY__ZERO_OR_ONE = ".?";
    
    /**
     * Match any char zero or more times.
     * String {@code ".*"}
     *
     */
    public static final String REGEX_ANY = ".*";
    
    /**
     * Match any char one or more times.
     * String {@code ".+"}
     *
     */
    public static final String REGEX_ANY__ONE_OR_MORE = ".+";
    
    /**
     * A String for a space character.
     *
     */
    public static final String SPACE = " ";

    /**
     * The underline String {@code "_"}.
     *
     */
    public static final String UL = "_";

    /**
     * The dot String {@code "."}.
     *
     */
    public static final String DOT = ".";
    
    /**
     * The real dot into regex String {@code "\\."}.
     *
     */
    public static final String REAL_DOT = "\\.";
    
    /**
     * The double dots String {@code ":"}.
     *
     */
    public static final String DOUBLE_DOTS = ":";
    
    /**
     * The comma String {@code ","}.
     *
     */
    public static final String COMMA = ",";

    /**
     * The single quotes String {@code "'"}.
     *
     */
    public static final String SQ = "'";
    
    /**
     * The double quotes String {@code """}.
     *
     */
    public static final String DQ = "\"";
    
    /**
     * The real double quotes into regex String {@code "\""}.
     *
     */
    public static final String REAL_DQ = "\\\"";

    /**
     * The forward slash String {@code "/"}.
     */
    public static final String FS = "/";
    
    /**
     * The back slash String {@code "\"}.
     *
     */
    public static final String BS = "\\\\";
    
    /**
     * The assign String {@code "="}.
     *
     */
    public static final String ASSIGN = "=";
    
    /**
     * The minus String {@code "-"}.
     *
     */
    public static final String MS = "-";
    
    /**
     * The plus String {@code "+"}.
     *
     */
    public static final String PS = "+";
    
    /**
     * The question mark String {@code "?"}.
     *
     */
    public static final String QM = "?";
    
    /**
     * The hashatag String {@code "#"}.
     */
    public static final String HT = "#";
    
    /**
     * The dollar sign String {@code "$"}.
     */
    public static final String DS = "$";
    
    /**
     * The real dollar sign into regex String {@code "\\$"}.
     */
    public static final String REAL_DS = "\\$";
    
    /**
     * The & String {@code "&"}.
     */
    public static final String AND = "&";
    
    /**
     * The asterisk String {@code "*"}.
     *
     */
    public static final String AR = "*";
    
    /**
     * The open parentheses String {@code "("}.
     *
     */
    public static final String OP = "(";
    
    /**
     * The close parentheses String {@code ")"}.
     *
     */
    public static final String CP = ")";
    
    /**
     * The open square brackets String {@code "["}.
     *
     */
    public static final String OSB = "[";
    
    /**
     * The real open square brackets String {@code "\\["}.
     *
     */
    public static final String REAL_OSB = "\\[";
    
    /**
     * The close square brackets String {@code "]"}.
     *
     */
    public static final String CSB = "]";
    
    /**
     * The real close square brackets String {@code "\\]"}.
     *
     */
    public static final String REAL_CSB = "\\]";
    
    /**
     * The open curly braces String {@code "{"}.
     *
     */
    public static final String OCB = "{";
    
    /**
     * The real open curly braces into regex String {@code "\\{"}.
     *
     */
    public static final String REAL_OCB = "\\{";
    
    /**
     * The close curly braces String {@code "}"}.
     *
     */
    public static final String CCB = "}";
    
    /**
     * The real close curly braces into regex String {@code "\\}"}.
     *
     */
    public static final String REAL_CCB = "\\}";

    /**
     * The empty String {@code ""}.
     *
     */
    public static final String EMPTY = "";
    
    /**
     * The null String {@code "null"}.
     *
     */
    public static final String NULL = "";

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

        return stripDiffOf(value, paterner.toString());
    }

    public static final String stripDiffOfLetter(final String value, final String... andPreserve) {
        return stripDiffOf(value, LETTERS_FOR_PARTNERS, join(andPreserve));
    }

    public static final String stripDiffOfNumber(final String value, final String... andPreserve) {
        return stripDiffOf(value, NUMBERS_FOR_PARTNERS, join(andPreserve));
    }

    public static final String stripDifOfAlphanumeric(final String value, final String... andPreserve) {
        return stripDiffOf(value, LETTERS_FOR_PARTNERS, NUMBERS_FOR_PARTNERS, join((Object[]) andPreserve));
    }

    public static final String stripDiffOf(final String value, final String... preserve) {

        if (preserve == null || preserve.length == 0) {
            throw new IllegalArgumentException("Param 'preserve' can not be null or empty");
        }

        if (value == null || value.trim().isEmpty()) {
            return value;
        }

        final StringBuilder paterner = new StringBuilder();
        paterner.append("[^");
        paterner.append(join((Object[]) preserve));
        paterner.append("]+");

        return value.replaceAll(paterner.toString(), "");
    }

    /**
     * Concat values with null prevent
     *
     * @param <O>
     * @param data
     * @return
     */
    public static <O> String join(final O... data) {
        return Arrays.stream(data).map(var -> var == null ? EMPTY : var.toString()).collect(Collectors.joining());
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

    // Empty checks
    //-----------------------------------------------------------------------
    /**
     * <p>
     * Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the
     * CharSequence. That functionality is available in isBlank().</p>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * isEmpty(CharSequence)
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>
     * Checks if a CharSequence is empty (""), null or whitespace only.</p>
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * only
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to
     * isBlank(CharSequence)
     */
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

    /**
     *
     * Gets a CharSequence length or {@code 0} if the CharSequence is
     * {@code null}.
     *
     * @param cs a CharSequence or {@code null}
     * @return CharSequence length or {@code 0} if the CharSequence is
     * {@code null}.
     * @since 2.4
     * @since 3.0 Changed signature from length(String) to length(CharSequence)
     */
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * <p>
     * Checks if the CharSequence contains only Unicode digits. A decimal point
     * is not a Unicode digit and returns false.</p>
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence
     * (length()=0) will return {@code false}.</p>
     *
     * <p>
     * Note that the method does not allow for a leading sign, either positive
     * or negative. Also, if a String passes the numeric test, it may still
     * generate a NumberFormatException when parsed by Integer.parseInt or
     * Long.parseLong, e.g. if the value is outside the range for int or long
     * respectively.</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * StringUtils.isNumeric("-123") = false
     * StringUtils.isNumeric("+123") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains digits, and is non-null
     * @since 3.0 Changed signature from isNumeric(String) to
     * isNumeric(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
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

    /**
     * <p>
     * Checks if CharSequence contains a search CharSequence, handling
     * {@code null}. This method uses {@link String#indexOf(String)} if
     * possible.</p>
     *
     * <p>
     * A {@code null} CharSequence will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.contains(null, *)     = false
     * StringUtils.contains(*, null)     = false
     * StringUtils.contains("", "")      = true
     * StringUtils.contains("abc", "")   = true
     * StringUtils.contains("abc", "a")  = true
     * StringUtils.contains("abc", "z")  = false
     * </pre>
     *
     * @param seq the CharSequence to check, may be null
     * @param searchSeq the CharSequence to find, may be null
     * @return true if the CharSequence contains the search CharSequence, false
     * if not or {@code null} string input
     * @since 2.0
     * @since 3.0 Changed signature from contains(String, String) to
     * contains(CharSequence, CharSequence)
     */
    public static boolean contains(final CharSequence seq, final CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return false;
        }
        return indexOf(seq, searchSeq, 0) >= 0;
    }

    /**
     * <p>
     * Checks if CharSequence contains a search character, handling
     * {@code null}. This method uses {@link String#indexOf(int)} if
     * possible.</p>
     *
     * <p>
     * A {@code null} or empty ("") CharSequence will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.contains(null, *)    = false
     * StringUtils.contains("", *)      = false
     * StringUtils.contains("abc", 'a') = true
     * StringUtils.contains("abc", 'z') = false
     * </pre>
     *
     * @param seq the CharSequence to check, may be null
     * @param searchChar the character to find
     * @return true if the CharSequence contains the search character, false if
     * not or {@code null} string input
     * @since 2.0
     * @since 3.0 Changed signature from contains(String, int) to
     * contains(CharSequence, int)
     */
    public static boolean contains(final CharSequence seq, final int searchChar) {
        if (isEmpty(seq)) {
            return false;
        }
        return indexOf(seq, searchChar, 0) >= 0;
    }

    /**
     * Used by the indexOf(CharSequence methods) as a green implementation of
     * indexOf.
     *
     * @param cs the {@code CharSequence} to be processed
     * @param searchChar the {@code CharSequence} to be searched for
     * @param start the start index
     * @return the index where the search sequence was found
     */
    public static int indexOf(final CharSequence cs, final CharSequence searchChar, final int start) {
        return cs.toString().indexOf(searchChar.toString(), start);
    }

    /**
     * Returns the index within <code>cs</code> of the first occurrence of the
     * specified character, starting the search at the specified index.
     * <p>
     * If a character with value <code>searchChar</code> occurs in the character
     * sequence represented by the <code>cs</code> object at an index no smaller
     * than <code>start</code>, then the index of the first such occurrence is
     * returned. For values of <code>searchChar</code> in the range from 0 to
     * 0xFFFF (inclusive), this is the smallest value <i>k</i> such that:
     * <blockquote><pre>
     * (this.charAt(<i>k</i>) == searchChar) &amp;&amp; (<i>k</i> &gt;= start)
     * </pre></blockquote>
     * is true. For other values of <code>searchChar</code>, it is the smallest
     * value <i>k</i> such that:
     * <blockquote><pre>
     * (this.codePointAt(<i>k</i>) == searchChar) &amp;&amp; (<i>k</i> &gt;= start)
     * </pre></blockquote>
     * is true. In either case, if no such character occurs inm <code>cs</code>
     * at or after position <code>start</code>, then <code>-1</code> is
     * returned.
     *
     * <p>
     * There is no restriction on the value of <code>start</code>. If it is
     * negative, it has the same effect as if it were zero: the entire
     * <code>CharSequence</code> may be searched. If it is greater than the
     * length of <code>cs</code>, it has the same effect as if it were equal to
     * the length of <code>cs</code>: <code>-1</code> is returned.
     *
     * <p>
     * All indices are specified in <code>char</code> values (Unicode code
     * units).
     *
     * @param cs the {@code CharSequence} to be processed, not null
     * @param searchChar the char to be searched for
     * @param start the start index, negative starts at the string start
     * @return the index where the search char was found, -1 if not found
     * @since 3.6 updated to behave more like <code>String</code>
     */
    final static int indexOf(final CharSequence cs, final int searchChar, int start) {
        if (cs instanceof String) {
            return ((String) cs).indexOf(searchChar, start);
        }

        @SuppressWarnings("null")
        final int sz = cs.length();
        if (start < 0) {
            start = 0;
        }
        if (searchChar < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
            for (int i = start; i < sz; i++) {
                if (cs.charAt(i) == searchChar) {
                    return i;
                }
            }
        }
        //supplementary characters (LANG1300)
        if (searchChar <= Character.MAX_CODE_POINT) {
            final char[] chars = Character.toChars(searchChar);
            for (int i = start; i < sz - 1; i++) {
                final char high = cs.charAt(i);
                final char low = cs.charAt(i + 1);
                if (high == chars[0] && low == chars[1]) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }
}
