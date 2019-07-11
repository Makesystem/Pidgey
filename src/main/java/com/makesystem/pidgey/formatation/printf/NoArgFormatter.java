package com.makesystem.pidgey.formatation.printf;

/**
 * Class used to format conversion specifiers that take no argument.
 *
 * @author Richeli Vargas
 */
final class NoArgFormatter
        implements ConversionFormatter {

    /**
     * Sole constructor.
     */
    public NoArgFormatter() {
        // do nothing
    }

    @Override
    public char getPaddingChar() {
        return DEFAULT_PADDING_CHAR;
    }

    @Override
    public void setPaddingChar(final char paddingChar) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumLength(final PrintfSpec spec) {
        // the only supported conversion spec is "%%" which returns "%"
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean consumesArg(final PrintfSpec spec) {
        // by definition this always returns false for NoArgFormatter
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void format(final PrintfSpec spec, final StringBuilder buf) {
        checkFlagsWidthAndPrecision(spec);

        if (spec.conversionSpec != '%') {
            throw new InvalidFormatSpecException(
                    "Unsupported no-arg conversion specifier: %"
                    + spec.conversionSpec);
        }
        buf.append('%');
    }

    /**
     * Checks that no flags were present on the conversion specifier and no
     * width or precision was specicifed.
     *
     * @param spec the PrintfSpec to analyze
     * @throws InvalidFormatSpecException if any flags, width or precision are
     * contained in <code>spec</code>
     */
    protected final void checkFlagsWidthAndPrecision(final PrintfSpec spec) {
        spec.flags.forEach(f -> {
            throw new InvalidFormatSpecException("Flag [" + f
                    + "] is unsupported in no-arg conversions");
        });

        if (spec.widthIsValid) {
            throw new InvalidFormatSpecException(
                    "Width is unsupported for no-arg conversions");
        }
        if (spec.precisionIsValid) {
            throw new InvalidFormatSpecException(
                    "Precision is unsupported for no-arg conversions");
        }
    }
}
