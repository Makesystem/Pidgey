/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.formatation.NumericFormat;
import com.makesystem.pidgey.formatation.TimeFormat;
import com.makesystem.pidgey.lang.StringHelper;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.function.Function;

/**
 *
 * @author riche
 */
public enum ConsoleFlag {
    /**
     * Set value as a String
     */
    TEXT("{s}", value -> String.valueOf(value)),
    /**
     * Set value as an integer
     * <pre>
     * Ex.: 1.000.000
     * </pre>
     *
     */
    INTEGER("{i}", value -> NumericFormat.integer(Double.valueOf(value.toString()))),
    /**
     * Set value as currency
     * <pre>
     * Ex.: 1.000.000,00
     * </pre>
     */
    CURRENCY("{c}", value -> NumericFormat.currency(Double.valueOf(value.toString()))),
    /**
     * Set value as number of bytes
     * <pre>
     * Ex.: 1KB
     * </pre>
     */
    NUMBER_OF_BYTES("{b}", value -> NumericFormat.bytes(Long.valueOf(value.toString()))),
    /**
     * Set value as hexadecimal
     * <pre>
     * Ex.: ABF09
     * </pre>
     */
    HEX("{hex}", value -> NumericFormat.toHex(Long.valueOf(value.toString()))),
    /**
     * Set value as seconds counter
     * <pre>
     * Ex.: 123:30:40
     * </pre>
     */
    SECONDS("{ss}", value -> TimeFormat.seconds(Integer.valueOf(value.toString()))),
    /**
     * Set value as millis counter
     * <pre>
     * Ex.: 101:30:40:345
     * </pre>
     */
    MILLIS("{ms}", value -> TimeFormat.millis(Long.valueOf(value.toString()))),
    /**
     * Set value as date
     * <pre>
     * Ex.: 2019/12/16
     * </pre>
     */
    DATE("{d}", value -> value instanceof Date
            ? TimeFormat.format((Date) value, TimeFormat.DATE_PATTERN)
            : TimeFormat.format((Long) value, TimeFormat.DATE_PATTERN)),
    /**
     * Set value as time
     * <pre>
     * Ex.: 13:10:34
     * </pre>
     */
    TIME("{t}", value -> value instanceof Date
            ? TimeFormat.format((Date) value, TimeFormat.TIME_PATTERN)
            : TimeFormat.format((Long) value, TimeFormat.TIME_PATTERN)),
    /**
     * Set value as date time
     * <pre>
     * Ex.: 2019/12/16 13:10:34
     * </pre>
     */
    DATE_TIME("{dt}", value -> value instanceof Date
            ? TimeFormat.format((Date) value, TimeFormat.DATE_TIME_PATTERN)
            : TimeFormat.format((Long) value, TimeFormat.DATE_TIME_PATTERN)),
    /**
     * Set a console color
     * <pre style="color:red;">
     * Ex.: Red text
     * </pre>
     */
    COLOR("{cc}", value -> ConsoleColor.valueOf(value.toString()).getColor());

    public static final String IGNORE = "{ig}";
    public static final String IGNORE__FOR_REGX = "\\{ig\\}";

    private final String flag;
    private final Function<Object, String> mapper;

    private ConsoleFlag(final String flag, final Function<Object, String> mapper) {
        this.flag = flag;
        this.mapper = value -> {
            if (value == null) {
                return "null";
            } else if (value.toString().startsWith(IGNORE)) {
                return value.toString().replaceFirst(IGNORE__FOR_REGX, "");
            } else {
                return mapper.apply(value);
            }
        };
    }

    public String getFlag() {
        return flag;
    }

    public String apply(final Object value) {
        return mapper.apply(value);
    }

    public String replace(final String text, final String value) {
        return text
                .replaceFirst(forRegex(flag), prepareValue(value));
    }

    public String applyAndReplace(final String text, final Object object) {
        return replace(text, apply(object));
    }

    protected final String prepareValue(final String value){
        return value
                .replace(StringHelper.DS, StringHelper.REAL_DS);
    }
    
    protected static final String forRegex(final String flag) {
        return flag
                .replace(StringHelper.OCB, StringHelper.REAL_OCB)
                .replace(StringHelper.CCB, StringHelper.REAL_CCB);
    }

    public static ConsoleFlag fromFlag(final String flag) {
        return Arrays.stream(values())
                .filter(value -> value.getFlag().equalsIgnoreCase(flag))
                .findAny().orElse(null);
    }

    public static ConsoleFlag[] fromText(final String text) {

        String temp = text;

        int indexOfStart = temp.indexOf(StringHelper.OCB);
        int indexOfEnd = temp.indexOf(StringHelper.CCB) + 1;

        final Collection<ConsoleFlag> flags = new LinkedList<>();
        while (indexOfStart > -1 && indexOfEnd > 0) {

            final String flag = temp.substring(indexOfStart, indexOfEnd);
            final ConsoleFlag consoleFlag = ConsoleFlag.fromFlag(flag);

            if (consoleFlag != null) {
                flags.add(consoleFlag);
            }

            temp = temp.substring(indexOfEnd);
            indexOfStart = temp.indexOf(StringHelper.OCB);
            indexOfEnd = temp.indexOf(StringHelper.CCB) + 1;
        }

        return flags.stream().toArray(ConsoleFlag[]::new);
    }
}
