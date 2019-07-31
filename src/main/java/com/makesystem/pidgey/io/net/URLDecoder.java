package com.makesystem.pidgey.io.net;

import java.io.UnsupportedEncodingException;

/**
 * Utility class for HTML form decoding. This class contains static methods for
 * decoding a String from the <CODE>application/x-www-form-urlencoded</CODE>
 * MIME format.
 * <p>
 * The conversion process is the reverse of that used by the URLEncoder class.
 * It is assumed that all characters in the encoded string are one of the
 * following: &quot;{@code a}&quot; through &quot;{@code z}&quot;,
 * &quot;{@code A}&quot; through &quot;{@code Z}&quot;, &quot;{@code 0}&quot;
 * through &quot;{@code 9}&quot;, and &quot;{@code -}&quot;,
 * &quot;{@code _}&quot;, &quot;{@code .}&quot;, and &quot;{@code *}&quot;. The
 * character &quot;{@code %}&quot; is allowed but is interpreted as the start of
 * a special escaped sequence.
 * <p>
 * The following rules are applied in the conversion:
 *
 * <ul>
 * <li>The alphanumeric characters &quot;{@code a}&quot; through
 * &quot;{@code z}&quot;, &quot;{@code A}&quot; through &quot;{@code Z}&quot;
 * and &quot;{@code 0}&quot; through &quot;{@code 9}&quot; remain the same.
 * <li>The special characters &quot;{@code .}&quot;, &quot;{@code -}&quot;,
 * &quot;{@code *}&quot;, and &quot;{@code _}&quot; remain the same.
 * <li>The plus sign &quot;{@code +}&quot; is converted into a space character
 * &quot; &nbsp; &quot; .
 * <li>A sequence of the form "<i>{@code %xy}</i>" will be treated as
 * representing a byte where <i>xy</i> is the two-digit hexadecimal
 * representation of the 8 bits. Then, all substrings that contain one or more
 * of these byte sequences consecutively will be replaced by the character(s)
 * whose encoding would result in those consecutive bytes. The encoding scheme
 * used to decode these characters may be specified, or if unspecified, the
 * default encoding of the platform will be used.
 * </ul>
 * <p>
 * There are two possible ways in which this decoder could deal with illegal
 * strings. It could either leave illegal characters alone or it could throw an
 * {@link java.lang.IllegalArgumentException}. Which approach the decoder takes
 * is left to the implementation.
 *
 * @author Mark Chamness
 * @author Michael McCloskey
 * @since 1.2
 */
public class URLDecoder {

    /**
     * Decodes a {@code application/x-www-form-urlencoded} string using a
     * specific encoding scheme. The supplied encoding is used to determine what
     * characters are represented by any consecutive sequences of the form
     * "<i>{@code %xy}</i>".
     * <p>
     * <em><strong>Note:</strong> The
     * <a href= "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that UTF-8 should be
     * used. Not doing so may introduce incompatibilities.</em>
     *
     * @param string the {@code String} to decode
     * @param enc The name of a supported
     * <a href="../lang/package-summary.html#charenc">character encoding</a>.
     * @return the newly decoded {@code String}
     * @throws UnsupportedEncodingException
     */
    public static String decode(final String string, final String enc) throws UnsupportedEncodingException {

        if (enc == null || enc.isEmpty()) {
            throw new IllegalArgumentException("Chaset is invalid: " + enc);
        }

        final int numChars = string.length();
        final StringBuffer out = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);

        boolean needToChange = false;
        int index = 0;
        char _char;
        byte[] bytes = null;

        while (index < numChars) {
            _char = string.charAt(index);
            switch (_char) {
                case '+':
                    out.append(' ');
                    index++;
                    needToChange = true;
                    break;
                case '%':
                    /*
				 * Starting with this instance of %, process all consecutive substrings of the
				 * form %xy. Each substring %xy will yield a byte. Convert all consecutive bytes
				 * obtained this way to whatever character(s) they represent in the provided
				 * encoding.
                     */

                    try {

                        // (numChars-i)/3 is an upper bound for the number
                        // of remaining bytes
                        if (bytes == null) {
                            bytes = new byte[(numChars - index) / 3];
                        }
                        int pos = 0;

                        while (((index + 2) < numChars) && (_char == '%')) {
                            int v = Integer.parseInt(string.substring(index + 1, index + 3), 16);
                            if (v < 0) {
                                throw new IllegalArgumentException(
                                        "URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                            }
                            bytes[pos++] = (byte) v;
                            index += 3;
                            if (index < numChars) {
                                _char = string.charAt(index);
                            }
                        }

                        // A trailing, incomplete byte encoding such as
                        // "%x" will cause an exception to be thrown
                        if ((index < numChars) && (_char == '%')) {
                            throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");
                        }

                        out.append(new String(bytes, 0, pos, enc));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(
                                "URLDecoder: Illegal hex characters in escape (%) pattern - " + e.getMessage());
                    }
                    needToChange = true;
                    break;
                default:
                    out.append(_char);
                    index++;
                    break;
            }
        }

        return (needToChange ? out.toString() : string);
    }
}
