package com.makesystem.pidgey.formatation.printf;

/**
 * Enumeration of the valid flags that can be present for printf specifications.
 *
 * @author Richeli Vargas
 */
public enum PrintfFlag {
    /**
     * Left justify the string. Specified by '-'.
     */
    LEFT_JUSTIFY,
    /**
     * For positive signed values, prefix the value with a "+". Specified by
     * '+'.
     */
    PRINT_SIGN,
    /**
     * For positive signed values, prefix the value with a " ". Specified by '
     * '.
     */
    SPACE_BEFORE_POSITIVE_VALUE,
    /**
     * Print the alternate form for the value. Specified by '#'.
     *
     * @see Printf
     */
    ALTERNATE_FORM,
    /**
     * Format the value using only uppercase letters. Specified by '^' or a
     * capital conversion specifier.
     */
    UPPERCASE,
    /**
     * Pad extra width with zeros, rather than spaces. Specified by '0'.
     */
    ZERO_PAD,
    /**
     * Use the natural width of the type. Specified by '='.
     */
    USE_NATURAL_WIDTH;
}
