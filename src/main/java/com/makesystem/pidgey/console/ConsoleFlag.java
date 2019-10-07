/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.formatation.NumericFormat;
import com.makesystem.pidgey.formatation.TimeFormat;
import java.util.Arrays;
import java.util.Date;
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
    SECONDS("{sec}", value -> TimeFormat.seconds(Integer.valueOf(value.toString()))),
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
            : TimeFormat.format((Long) value, TimeFormat.DATE_TIME_PATTERN));

    private final String flag;
    private final Function<Object, String> mapper;

    private ConsoleFlag(final String flag, final Function<Object, String> mapper) {
        this.flag = flag;
        this.mapper = mapper;
    }

    public String getFlag() {
        return flag;
    }

    public String apply(final Object value) {
        return mapper.apply(value);
    }

    public static ConsoleFlag fromFlag(final String flag) {
        return Arrays.stream(values())
                .filter(value -> value.getFlag().equalsIgnoreCase(flag))
                .findAny().orElse(null);
    }
}
