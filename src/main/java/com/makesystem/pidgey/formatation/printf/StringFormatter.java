package com.makesystem.pidgey.formatation.printf;

import java.math.BigInteger;

import com.makesystem.pidgey.formatation.printf.bit.Bit;
import com.makesystem.pidgey.formatation.printf.bit.BitVector;
import com.makesystem.pidgey.formatation.printf.bit.BitVectorBuffer;

/**
 * Class used to format objects to string values.
 *
 * @author Richeli Vargas
 */
final class StringFormatter
        implements ConversionFormatter {

    
    private char paddingChar = DEFAULT_PADDING_CHAR;
    
    /**
     * Sole constructor.
     */
    public StringFormatter() {
        // do nothing
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
    public boolean consumesArg(final PrintfSpec spec) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getMaximumLength(final PrintfSpec spec) {
        int baseWidth = 0;

        switch (spec.conversionSpec) {
            case 'c':
                baseWidth = 1;
                break;
            case 's':
                if (spec.obj instanceof Number) {
                    int numBits = PrintfUtils.getWidth(spec.obj);
                    baseWidth = numBits / 8 + (numBits % 8 == 0 ? 0 : 1);
                } else {
                    if (spec.obj instanceof String) {
                        baseWidth = ((String) spec.obj).length();
                        spec.cachedString = (String) spec.obj;
                    } else if (spec.obj == null) {
                        baseWidth = 6;
                        spec.cachedString = "<null>";
                    } else {
                        // use generic toString() and cache the result
                        // note that Booleans & Enum's fall into this category
                        String s = spec.obj.toString();
                        baseWidth = s.length();
                        spec.cachedString = s;
                    }
                }
                break;
            default:
                throw new InvalidFormatSpecException(
                        "Unsupported string conversion: %" + spec.conversionSpec);
        }

        // don't include width addition in cached length
        spec.cachedLength = baseWidth;

        if (spec.widthIsValid) {
            return Math.max(baseWidth, spec.width);
        } else {
            return spec.cachedLength;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void format(final PrintfSpec spec, final StringBuilder buf) {
        checkFlagsAndPrecision(spec);

        final int startIdx = buf.length();
        final boolean leftJustified = spec.flags
                .contains(PrintfFlag.LEFT_JUSTIFY);
        if (!leftJustified) {
            insertPadding(buf, spec);
        }
        formatArg(buf, spec);

        if (leftJustified) {
            insertPadding(buf, spec);
        }

        if (spec.flags.contains(PrintfFlag.UPPERCASE)) {
            final int endIdx = buf.length();
            String upperCaseStr = buf.substring(startIdx, endIdx).toUpperCase();
            buf.replace(startIdx, endIdx, upperCaseStr);
        }
    }

    /**
     * Appends the proper amount of space-padding to <code>buf</code>.
     *
     * @param buf the StringBuilder to which padding is appended
     * @param spec the PrintfSpec containing the format specification
     */
    private void insertPadding(final StringBuilder buf, final PrintfSpec spec) {
        assert (spec.cachedLength != -1);

        final int width = spec.widthIsValid ? spec.width : 0;
        final int numPaddingChars = Math.max(width - spec.cachedLength, 0);

        for (int i = 0; i < numPaddingChars; ++i) {
            buf.append(getPaddingChar());
        }
    }

    /**
     * Formats the argument contained in <code>spec</code> as specified in
     * <code>spec</code>.
     *
     * @param buf the StringBuilder to which the value string is appended
     * @param spec the PrintfSpec containing the format specification
     */
    protected final void formatArg(final StringBuilder buf, final PrintfSpec spec) {
        boolean doChar = spec.conversionSpec == 'c';
        boolean doString = spec.conversionSpec == 's';
        assert (doChar || doString);

        final Object obj = spec.obj;

        // For objects that rely on toString() for their strings, we
        // might have cached the string when determining the length
        if (spec.cachedString != null) {
            if (doChar) {
                if (spec.cachedString.length() == 0) {
                    // do nothing
                } else {
                    buf.append(spec.cachedString.charAt(0));
                }
            } else {
                buf.append(spec.cachedString);
            }
        } else if (obj instanceof Number) {
            byte[] bytes = null;
            boolean containsXZ = false;

            int length = PrintfUtils.getWidth(obj);
            if (obj instanceof Bit) {
                containsXZ = ((Bit) obj).isXZ();
            } else if (obj instanceof BitVectorBuffer) {
                bytes = ((BitVectorBuffer) obj).getBytes();
                containsXZ = ((BitVectorBuffer) obj).containsXZ();

                // need to reverse byte order
                final byte[] revBytes = new byte[bytes.length];
                for (int i = 0, j = (bytes.length - 1); i < bytes.length; ++i, --j) {
                    revBytes[i] = bytes[j];
                }
                bytes = revBytes;
            } else if (obj instanceof BitVector) {
                bytes = ((BitVector) obj).getBytes();
                containsXZ = ((BitVector) obj).containsXZ();
                // need to reverse byte order
                final byte[] revBytes = new byte[bytes.length];
                for (int i = 0, j = (bytes.length - 1); i < bytes.length; ++i, --j) {
                    revBytes[i] = bytes[j];
                }
                bytes = revBytes;
            } else if (obj instanceof BigInteger) {
                bytes = ((BigInteger) obj).toByteArray();
                containsXZ = false;
                // need to reverse byte order
                final byte[] revBytes = new byte[bytes.length];
                for (int i = 0, j = (bytes.length - 1); i < bytes.length; ++i, --j) {
                    revBytes[i] = bytes[j];
                }
                bytes = revBytes;
            }

            // if arg contains X/Z values, print an empty string
            if (!containsXZ) {
                final int numChars = (length / 8) + ((length % 8 == 0) ? 0 : 1);
                int bitsConsumed = 0;
                for (int i = (numChars - 1); i >= 0; --i) {
                    final char c;
                    if (bytes != null) {
                        c = (char) bytes[i];
                    } else {
                        c = (char) ((((Number) obj).longValue() >>> (i * 8)) & 0x0FF);
                    }

                    if (c != 0) {
                        // ignore NULL characters
                        buf.append(c);
                        if (doChar) {
                            // we printed the char. we're done.
                            break;
                        }
                    }

                    bitsConsumed += 8;
                    if (bitsConsumed >= length) {
                        break;
                    }
                }
            }
        } else {
            // Boolean, Enum<?>, String all use toString().
            String s = spec.obj.toString();
            if (doChar) {
                if (s.length() == 0) {
                    // do nothing
                } else {
                    buf.append(s.charAt(0));
                }
            } else {
                buf.append(s);
            }
        }
    }

    /**
     * Check that the flags and precision for this specification are consistent
     * with what is supported for a string conversion.
     *
     * @param spec the PrintfSpec to check
     * @throws InvalidFormatSpecException if flags or precision not consistent
     * with string conversions are contained in <code>spec</code>
     */
    protected final void checkFlagsAndPrecision(final PrintfSpec spec) {
        spec.flags.stream()
                .filter(f -> !f.equals(PrintfFlag.LEFT_JUSTIFY) && !f.equals(PrintfFlag.UPPERCASE))
                .forEach(f -> {
                    throw new InvalidFormatSpecException("Flag [" + f
                            + "] is unsupported in string conversions");
                });
        if (spec.precisionIsValid) {
            throw new InvalidFormatSpecException(
                    "Precision is unsupported for string conversions");
        }
    }
}
