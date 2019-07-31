package com.makesystem.pidgey.io.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.BitSet;

public class URLEncoder {

    static final BitSet dontNeedEncoding;
    static final int caseDiff = ('a' - 'A');

    static {

        /*
		 * The list of characters that are not encoded has been determined as follows:
		 *
		 * RFC 2396 states: ----- Data characters that are allowed in a URI but do not
		 * have a reserved purpose are called unreserved. These include upper and lower
		 * case letters, decimal digits, and a limited set of punctuation marks and
		 * symbols.
		 *
		 * unreserved = alphanum | mark
		 *
		 * mark = "-" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")"
		 *
		 * Unreserved characters can be escaped without changing the semantics of the
		 * URI, but this should not be done unless the URI is being used in a context
		 * that does not allow the unescaped character to appear. -----
		 *
		 * It appears that both Netscape and Internet Explorer escape all special
		 * characters from this list with the exception of "-", "_", ".", "*". While it
		 * is not clear why they are escaping the other characters, perhaps it is safest
		 * to assume that there might be contexts in which the others are unsafe if not
		 * escaped. Therefore, we will use the same list. It is also noteworthy that
		 * this is consistent with O'Reilly's "HTML: The Definitive Guide" (page 164).
		 *
		 * As a last note, Intenet Explorer does not encode the "@" character which is
		 * clearly not unreserved according to the RFC. We are being consistent with the
		 * RFC in this matter, as is Netscape.
		 *
         */
        dontNeedEncoding = new BitSet(256);
        int i;
        for (i = 'a'; i <= 'z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = 'A'; i <= 'Z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = '0'; i <= '9'; i++) {
            dontNeedEncoding.set(i);
        }
        dontNeedEncoding.set(' ');
        /*
									 * encoding a space to a + is done in the encode() method
         */
        dontNeedEncoding.set('-');
        dontNeedEncoding.set('_');
        dontNeedEncoding.set('.');
        dontNeedEncoding.set('*');

    }

    /**
     * You can't call the constructor.
     */
    private URLEncoder() {
    }

    /**
     * Translates a string into {@code application/x-www-form-urlencoded} format
     * using a specific encoding scheme. This method uses the supplied encoding
     * scheme to obtain the bytes for unsafe characters.
     * <p>
     * <em><strong>Note:</strong> The
     * <a href= "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that UTF-8 should be
     * used. Not doing so may introduce incompatibilities.</em>
     *
     * @param string {@code String} to be translated.
     * @param enc The name of a supported
     * <a href="../lang/package-summary.html#charenc">character encoding</a>.
     * @return the translated {@code String}.
     *
     */
    public static String encode(final String string, final String enc) throws UnsupportedEncodingException {

        if (enc == null || enc.isEmpty()) {
            throw new IllegalArgumentException("Chaset is invalid: " + enc);
        }

        final StringBuffer out = new StringBuffer(string.length());
        final Charset charset = Charset.forName(enc);

        boolean needToChange = false;

        for (int char_index = 0; char_index < string.length();) {
            int _char = (int) string.charAt(char_index);
            if (dontNeedEncoding.get(_char)) {
                if (_char == ' ') {
                    _char = '+';
                    needToChange = true;
                }
                // System.out.println("Storing: " + c);
                out.append((char) _char);
                char_index++;
            } else {
                // convert to external encoding before hex conversion
                final StringBuffer charArrayWriter = new StringBuffer();
                do {
                    charArrayWriter.append((char) _char);
                    /*
					 * If this character represents the start of a Unicode surrogate pair, then pass
					 * in two characters. It's not clear what should be done if a bytes reserved in
					 * the surrogate pairs range occurs outside of a legal surrogate pair. For now,
					 * just treat it as if it were any other character.
                     */
                    if (_char >= 0xD800 && _char <= 0xDBFF) {
                        /*
						 * System.out.println(Integer.toHexString(c) + " is high surrogate");
                         */
                        if ((char_index + 1) < string.length()) {
                            int d = (int) string.charAt(char_index + 1);
                            /*
							 * System.out.println("\tExamining " + Integer.toHexString(d));
                             */
                            if (d >= 0xDC00 && d <= 0xDFFF) {
                                /*
								 * System.out.println("\t" + Integer.toHexString(d) + " is low surrogate");
                                 */
                                charArrayWriter.append((char) _char);
                                char_index++;
                            }
                        }
                    }
                    char_index++;
                } while (char_index < string.length()
                        && !dontNeedEncoding.get((_char = (int) string.charAt(char_index))));

                final String tempString = charArrayWriter.toString();
                final byte[] stringBytes = tempString.getBytes(charset);
                for (int j = 0; j < stringBytes.length; j++) {
                    out.append('%');
                    char charForDigit = Character.forDigit((stringBytes[j] >> 4) & 0xF, 16);
                    // converting to use uppercase letter as part of
                    // the hex value if ch is a letter.
                    if (Character.isLetter(charForDigit)) {
                        charForDigit -= caseDiff;
                    }
                    out.append(charForDigit);
                    charForDigit = Character.forDigit(stringBytes[j] & 0xF, 16);
                    if (Character.isLetter(charForDigit)) {
                        charForDigit -= caseDiff;
                    }
                    out.append(charForDigit);
                }
                needToChange = true;
            }
        }

        return (needToChange ? out.toString() : string);
    }
}
