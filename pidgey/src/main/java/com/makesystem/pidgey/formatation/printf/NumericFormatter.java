package com.makesystem.pidgey.formatation.printf;

import com.makesystem.pidgey.formatation.printf.bit.Bit;
import com.makesystem.pidgey.formatation.printf.bit.BitVector;
import com.makesystem.pidgey.formatation.printf.bit.BitVectorFormat;

/**
 * Class used to format objects to numeric values.
 *
 * @author Richeli Vargas
 */
final class NumericFormatter
        implements ConversionFormatter {

    private final BitVectorFormat bvFormatter = new BitVectorFormat();
    private static final String NULL_STRING = "<null>";
    private static final int NULL_STRING_LENGTH = NULL_STRING.length();
    private char paddingChar = DEFAULT_PADDING_CHAR;
    
    /**
     * Sole constructor.
     */
    public NumericFormatter() {
        // don't print length/radix information on BitVectors
        bvFormatter.setPrintRadix(false);
        bvFormatter.setPrintLength(false);
    }

    @Override
    public char getPaddingChar() {
        return paddingChar;
    }

    @Override
    public void setPaddingChar(final char paddingChar) {
        this.paddingChar = paddingChar;
    }   
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumLength(final PrintfSpec spec) {
        if (spec.obj == null) {
            return NULL_STRING_LENGTH;
        }

        int numChars = getNaturalWidth(spec);

        numChars += 1; // might add "-"

        // look at flags/width/precision to guess how long to make the string
        // err on making it too large
        if (spec.flags.contains(PrintfFlag.ALTERNATE_FORM)) {
            numChars += 2; // might add "0x"
        }
        if (spec.flags.contains(PrintfFlag.PRINT_SIGN)) {
            ++numChars; // might add "+"
        }
        if (spec.flags.contains(PrintfFlag.SPACE_BEFORE_POSITIVE_VALUE)) {
            ++numChars; // might add " "
        }
        if (spec.precisionIsValid) {
            numChars += (spec.precision + 1); // might add ".XXXX"
        }
        if (spec.widthIsValid) {
            return Math.max(spec.width, numChars);
        } else {
            return numChars;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean consumesArg(final PrintfSpec spec) {
        // all numeric conversions consume an argument
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void format(final PrintfSpec spec, final StringBuilder buf) {
        if (spec.precisionIsValid) {
            // TODO: precision specifiers for numeric conversions
            throw new UnsupportedOperationException(
                    "Precision specifiers are not yet supported for numeric conversions");
        }

        if (Character.isUpperCase(spec.conversionSpec)) {
            // only %X is supported as far as capital specifiers go
            if (spec.conversionSpec != 'X') {
                throw new InvalidFormatSpecException("%" + spec.conversionSpec
                        + " is not a valid conversion specifier.");
            }
            spec.flags.add(PrintfFlag.UPPERCASE);
        }

        if (spec.obj == null) {
            buf.append(NULL_STRING);
            return;
        }

        // there are a few "non-common" cases.
        // 1. the flags contain '+' or ' ' in which case we have to insert
        //    a character for non-negative values
        // 2. the flags contain '#' in which case we have to insert "0x" for
        //    hex conversions and '0' for octal conversions (that don't have
        //    a leading character of 0)
        // 3. the flags contain '0' in which case we must zero pad (and this
        //    padding occurs in between any alternate format and the actual
        //    value)
        // 4. a width is specified that is larger than the string including
        //    extra flag stuff. in this case we pad with spaces either on the
        //    left or right depending on the presence of the '-' flag.
        //
        // to do this efficiently we'll make the common case fast:
        // * append the value string directly to the string buffer. this way
        //   if we don't hit any of the above cases, we're done.
        // * get the "alternate string" consisting of "0x", "0", or ""
        // * get the "sign string" consisting of "+" or " "
        // * get the number of zeros to put between either of the above two
        //   strings and the value string
        // * create exactly 1 string builder of alternate string, sign string,
        //   zero-pad string, and extra width and insert this into the string
        //   buffer
        //
        // since alternate/sign strings should not be newed (they're effectively
        // static), this gets us down to 1 StringBuilder creation and 1
        // StringBuilder insert (which is effectively a System.arrayCopy()).
        final int startIdx = buf.length();
        appendValueString(spec, buf);

        if (spec.flags.isEmpty() && !spec.widthIsValid) {
            // a very coarse check to see if we're done. if so, return quickly
            return;
        }

        // determine the strings for alternate flags, sign flags, etc
        final int valueStringLength = buf.length() - startIdx;

        final String altString = getAlternateString(spec, buf, startIdx);
        final int altStringLength = altString.length();

        final String signString = getSignString(spec, buf, startIdx);
        final int signStringLength = signString.length();

        final int zeroPadLength = getZeroPadSize(spec, buf, valueStringLength,
                altStringLength + signStringLength);

        final int extraLength = altStringLength + signStringLength
                + zeroPadLength;
        final int length = valueStringLength + extraLength;

        // determine the width that should be printed.
        int width = spec.widthIsValid ? spec.width : 0;
        final int numPaddingChars = Math.max(width - length, 0);

        if (!spec.flags.contains(PrintfFlag.LEFT_JUSTIFY)) {
            if ((numPaddingChars + extraLength) > 0) {
                final StringBuilder tmpBuf = new StringBuilder(numPaddingChars
                        + extraLength);
                for (int i = 0; i < numPaddingChars; ++i) {
                    tmpBuf.append(getPaddingChar());
                }
                tmpBuf.append(altString);
                tmpBuf.append(signString);
                final char paddingChar = getPaddingChar(spec, buf, startIdx);
                for (int i = 0; i < zeroPadLength; ++i) {
                    tmpBuf.append(paddingChar);
                }
                // only do 1 insert. making a new StringBuilder, appending to
                // it and then inserting it seems much cheaper than inserting
                // character by character into buf (which would cause a
                // System.arrayCopy() each time)
                buf.insert(startIdx, tmpBuf);
            }
        } else {
            if ((numPaddingChars + extraLength) > 0) {
                StringBuilder tmpBuf = new StringBuilder(numPaddingChars
                        + extraLength);
                tmpBuf.append(altString);
                tmpBuf.append(signString);
                final char paddingChar = getPaddingChar(spec, buf, startIdx);
                if (!Character.isWhitespace(paddingChar)) {
                    for (int i = 0; i < zeroPadLength; ++i) {
                        tmpBuf.append(paddingChar);
                    }
                }

                // only do 1 insert. making a new StringBuilder, appending to
                // it and then inserting it seems much cheaper than inserting
                // character by character into buf (which would cause a
                // System.arrayCopy() each time)
                buf.insert(startIdx, tmpBuf);

                for (int i = 0; i < numPaddingChars; ++i) {
                    buf.append(getPaddingChar());
                }
            }
        }
    }

    /**
     * Returns the length of zero-padding that should prefix the value string.
     * <P>
     * A zero is always returned in the case where
     * {@link PrintfFlag#LEFT_JUSTIFY} is present. Further if neither
     * {@link PrintfFlag#ZERO_PAD} or {@link PrintfFlag#USE_NATURAL_WIDTH} is
     * present, a zero is always returned.
     * <P>
     * If USE_NATURAL_WIDTH is not present, the width of the specification is
     * used to determine the length of the padded zeros. Otherwise, the value's
     * natural width is used to determine the length.
     *
     * @param spec the PrintfSpec to analyze
     * @param buf the StringBuilder which contains the value string
     * @param valueStringLength the length of the value string without any
     * padding
     * @param extraLength the length of extra chracters that will be printed,
     * due to various {@link PrintfFlag} flags contained in the printf
     * specification
     * @return the length of zero-padding that should prefix the value string
     */
    private int getZeroPadSize(
            final PrintfSpec spec,
            final StringBuilder buf,
            final int valueStringLength,
            final int extraLength) {
        if (spec.flags.contains(PrintfFlag.LEFT_JUSTIFY)
                || (!spec.flags.contains(PrintfFlag.ZERO_PAD) && !spec.flags
                .contains(PrintfFlag.USE_NATURAL_WIDTH))) {
            return 0;
        }

        int width = spec.widthIsValid ? spec.width : 0;
        if (spec.flags.contains(PrintfFlag.USE_NATURAL_WIDTH)) {
            return Math.max(width, getNaturalWidth(spec) - valueStringLength);

        }
        return Math.max(width - (valueStringLength + extraLength), 0);
    }

    /**
     * Returns the string that should be prefixed to the value string due to the
     * {@link PrintfFlag#ALTERNATE_FORM} flag being present.
     *
     * The ALTERNATE_FORM flag will prefix "0x" if the conversion radix is 16.
     * <P>
     * The ALTERNATE_FORM flag will prefix "0" if the conversion radix is 8 and
     * the leading character is not already "0". For example, printf("%#3o", 5)
     * should print "005", not "0005".
     * <P>
     * If the radix is not 8 or 16, the empty string is returned.
     * <P>
     * Note that static strings are used in this method to avoid creating new
     * String objects.
     *
     * @param spec the PrintfSpec to analyze
     * @param buf the StringBuilder containing the value string
     * @param startIdx the offset of the value string in <code>buf</code>
     * @return the string that should be prefixed to the value string, which
     * might be the empty string
     */
    private String getAlternateString(
            final PrintfSpec spec,
            final StringBuilder buf,
            final int startIdx) {
        final int bufLength = buf.length();
        if (bufLength == startIdx) {
            return "";
        }

        final int radix = getRadix(spec.conversionSpec);
        char leadingChar;
        int idx = startIdx;
        do {
            leadingChar = buf.charAt(idx);
            ++idx;
        } while (idx < bufLength && !Character.isDigit(leadingChar)
                && !Character.isLetter(leadingChar));

        if (spec.flags.contains(PrintfFlag.ALTERNATE_FORM)) {
            switch (radix) {
                case 16:
                    if (spec.flags.contains(PrintfFlag.UPPERCASE)) {
                        return "0X";
                    } else {
                        return "0x";
                    }
                case 8:
                    if (leadingChar != '0') {
                        return "0";
                    } else {
                        return "";
                    }
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    /**
     * Returns the string that should be prefixed to the value string due to the
     * {@link PrintfFlag#PRINT_SIGN} or
     * {@link PrintfFlag#SPACE_BEFORE_POSITIVE_VALUE} flags being present.
     * <P>
     * This method will always return the empty string of the conversion
     * specifier is for a radix which is not signed. Futher, the empty string
     * will be returned if the value being printed is negative.
     * <P>
     * PRINT_SIGN takes precedence over SPACE_BEFORE_POSITIVE_VALUE if both are
     * specified.
     *
     * @param spec the PrintfSpec to analyze
     * @param buf the StringBuilder containing the value string
     * @param startIdx the offset of the value string in <code>buf</code>
     * @return the string that should be prefixed to the value string, which
     * might be the empty string
     */
    private String getSignString(
            final PrintfSpec spec,
            final StringBuilder buf,
            final int startIdx) {
        if (buf.length() == startIdx
                || !isSignedConversion(spec.conversionSpec)) {
            return "";
        }

        char leadingChar = buf.charAt(startIdx);

        if (spec.flags.contains(PrintfFlag.PRINT_SIGN)) {
            if (leadingChar != '-') {
                return "+";
            }
        } else if (spec.flags.contains(PrintfFlag.SPACE_BEFORE_POSITIVE_VALUE)) {
            if (leadingChar != '-') {
                return " ";
            }
        }

        return "";
    }

    /**
     * Appends the value string of <code>spec</code> to <code>buf</code>.
     * <P>
     * Supported types are {@link Boolean}, {@link Number}, {@link String}, and
     * {@link Enum}.
     * <P>
     * @param spec the PrintfSpec containing the value to append
     * @param buf the buffer to which the value string should be appended
     * @throws InvalidFormatSpecException if the type to be printed is not a
     * supported type
     */
    @SuppressWarnings("null")
    private void appendValueString(final PrintfSpec spec, final StringBuilder buf) {
        int radix = getRadix(spec.conversionSpec);

        // The following type conversions to numeric types are supported:
        // Boolean, Bit, Byte, Short, Integer, Long, BitVectorBuffer
        // BitVector, String, Enum
        Object obj = spec.obj;
        String valueString = null;
        if (obj instanceof Boolean) {
            valueString = ((Boolean) obj) ? "1" : "0";
        } else if (obj instanceof Bit) {
            switch (((Bit) obj).getID()) {
                case 0:
                    valueString = "0";
                    break;
                case 1:
                    valueString = "1";
                    break;
                case 2: {
                    switch (radix) {
                        case 10:
                        case 8:
                            valueString = "?";
                            break;
                        case 2:
                            valueString = "z";
                            break;
                        default:
                            assert (radix == 16);
                            valueString = "Z";
                            break;
                    }
                }
                break;

                case 3: {
                    switch (radix) {
                        case 10:
                        case 8:
                            valueString = "?";
                            break;
                        case 2:
                            valueString = "x";
                            break;
                        default:
                            assert (radix == 16);
                            valueString = "X";
                            break;
                    }
                }
                break;

                default:
                    assert (false);
            }
        } else if (obj instanceof BitVector) {
            valueString = bvFormatter.format((BitVector) obj, radix);
        } else if (obj instanceof Number) {
            final int numBits = PrintfUtils.getWidth(obj);
            long value = ((Number) obj).longValue();

            // conversions to binary, octal, or hex get printed as unsigned
            // values
            if (!isSignedConversion(spec.conversionSpec) && numBits < 64) {
                value &= ((1L << numBits) - 1);
            }

            switch (radix) {
                case 2:
                    valueString = Long.toBinaryString(value);
                    break;
                case 8:
                    valueString = Long.toOctalString(value);
                    break;
                case 10:
                    valueString = Long.toString(value);
                    break;
                case 16:
                    valueString = Long.toHexString(value);
                    break;
                default:
                    break;
            }
        } else if (obj instanceof Enum< ?>) {
            valueString = Integer.toString(((Enum< ?>) obj).ordinal(), radix);
        } else if (obj instanceof CharSequence) {
            CharSequence argString = (CharSequence) obj;
            BitVector vector = new BitVector(argString.toString().getBytes());
            valueString = bvFormatter.format(vector, radix);
        } else {
            throw new InvalidFormatSpecException(
                    "Type not supported for numeric conversion: " + obj.getClass());
        }

        buf.append(spec.flags.contains(PrintfFlag.UPPERCASE) ? valueString
                .toUpperCase() : valueString);
    }

    /**
     * Returns the radix of the specified conversion specifier.
     *
     * @param conversionSpec the conversion specifier to check
     * @return the radix of <code>conversionSpec</code>
     * @throws InvalidFormatSpecException if <code>conversionSpec</code> is not
     * a valid conversion specifier
     */
    private int getRadix(final char conversionSpec) {
        switch (conversionSpec) {
            case 'b':
                return 2;
            case 'o':
                return 8;
            case 'd':
            case 'i':
                return 10;
            case 'x':
            case 'X':
                return 16;
            default:
                throw new InvalidFormatSpecException(
                        "Unsupported numeric conversion: " + conversionSpec);
        }
    }

    /**
     * Returns whether or not the specified conversion specifier represents a
     * signed conversion.
     *
     * @param conversionSpec the conversion specifier to check
     * @return <code>true</code> if <code>conversionSpec</code> represents a
     * signed conversion, <code>false</code> otherwise
     */
    private boolean isSignedConversion(final char conversionSpec) {
        switch (conversionSpec) {
            case 'd':
            case 'i':
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns the natural width of the specified printf specification. The
     * natural width refers to the number of characters required to print the
     * type contained in <code>spec</code> according to the radix of the
     * conversion specifier contained in <code>spec</code>.
     *
     * @param spec the PrintfSpec to check
     * @return the natural width of the type contained in <code>spec</code>
     */
    private int getNaturalWidth(final PrintfSpec spec) {
        final int radix = getRadix(spec.conversionSpec);
        final int numBits;

        final Object obj = spec.obj;
        if (obj instanceof Enum< ?>) {
            numBits = 32;
        } else if (obj instanceof CharSequence) {
            numBits = ((CharSequence) obj).length() * 8;
        } else {
            numBits = PrintfUtils.getWidth(obj);
        }

        double ln2radix = Math.log(radix) / Math.log(2);
        return (int) (Math.ceil(numBits / ln2radix));
    }

    /**
     * Returns the padding character to be used when printing <code>spec</code>.
     * If the {@link PrintfFlag#LEFT_JUSTIFY} flag is present, the padding
     * character is always the space character. Otherwise, if the first
     * character is "X", "x", "Z", or "z", that same chracter is returned. In
     * all other cases "0" is returned.
     *
     * @param spec the PrintfSpec to analyze
     * @param buf the StringBuilder containing the value string
     * @param startIdx the offset of the value string in <code>buf</code>
     * @return the character that should be used to pad the value string if
     * padding is required
     */
    private char getPaddingChar(
            final PrintfSpec spec,
            final StringBuilder buf,
            final int startIdx) {
        final boolean zeroPad = spec.flags.contains(PrintfFlag.ZERO_PAD);
        final boolean leftJust = spec.flags.contains(PrintfFlag.LEFT_JUSTIFY);
        final boolean useNatLen = spec.flags
                .contains(PrintfFlag.USE_NATURAL_WIDTH);

        final int bufLength = buf.length();
        char leadingChar;
        int idx = startIdx;
        do {
            leadingChar = buf.charAt(idx);
            ++idx;
        } while (idx < bufLength && !Character.isDigit(leadingChar)
                && !Character.isLetter(leadingChar));

        if (leftJust) {
            return getPaddingChar();
        } else if (zeroPad || useNatLen) {
            switch (leadingChar) {
                case 'x':
                case 'X':
                case 'z':
                case 'Z':
                case '0':
                    return leadingChar;
                default:
                    return '0';
            }
        }

        return getPaddingChar();
    }
}
