/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console.old.base;

import com.makesystem.pidgey.console.ConsoleColor;
import com.makesystem.pidgey.console.old.base.AbstractPrintfSupport;
import com.makesystem.pidgey.formatation.NumericFormat;
import com.makesystem.pidgey.formatation.TimeFormat;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author Richeli.vargas
 */
public class ConsoleValue extends AbstractPrintfSupport {

    public static enum Type {
        TEXT, 
        INTEGER, 
        CURRENCY, 
        NUMBER_OF_BYTES, 
        HEX, 
        SECONDS, 
        MILLIS, 
        DATE, 
        TIME, 
        DATE_TIME
    }

    private Object value;
    private int length;
    private Type type = Type.TEXT;

    public <T> ConsoleValue(final T value) {
        this(value, Type.TEXT, -1, new ConsoleColor[]{});
    }

    public <T> ConsoleValue(final T value, final Type type) {
        this(value, type, -1, new ConsoleColor[]{});
    }

    public <T> ConsoleValue(final T value, final ConsoleColor... colors) {
        this(value, Type.TEXT, -1, colors);
    }

    public <T> ConsoleValue(final T value, final Type type, final ConsoleColor... colors) {
        this(value, type, -1, colors);
    }

    public <T> ConsoleValue(final T value, final int length) {
        this(value, Type.TEXT, length, new ConsoleColor[]{});
    }

    public <T> ConsoleValue(final T value, final Type type, final int length) {
        this(value, type, length, new ConsoleColor[]{});
    }

    public <T> ConsoleValue(final T value, final int length, final ConsoleColor... colors) {
        this(value, Type.TEXT, length, colors);
    }

    public <T> ConsoleValue(final T value, final Type type, final int length, final ConsoleColor... colors) {
        super(colors);
        this.value = value;
        this.type = type == null ? Type.TEXT : type;
        this.length = length;
    }

    public <T> T getValue() {
        return (T) value;
    }

    public <T> void setValue(final T value) {
        this.value = value;
    }

    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type == null ? Type.TEXT : type;
    }

    /**
     * If length < 1 the %s is equal %s, or else will be %-'(length * 5)'s @return
     * the %s for this value appling length
     *
     * @return
     */
    @Override
    protected String prinfFormat() {
        return length < 1
                ? "%s"
                : "%-" + length + "s";

    }

    @Override
    protected String[] prinfArgs() {
        return new String[]{toString()};
    }

    @Override
    public String toString() {
        final String asString;
        if (value == null) {
            asString = "";
        } else {
            final String toString = value.toString();
            switch (type) {
                case CURRENCY:
                    asString = NumericFormat.currency(Double.valueOf(value.toString()));
                    break;
                case HEX:
                    asString = NumericFormat.toHex(Long.valueOf(value.toString()));
                    break;
                case INTEGER:
                    asString = NumericFormat.integer(Double.valueOf(value.toString()));
                    break;
                case NUMBER_OF_BYTES:
                    asString = NumericFormat.bytes(Long.valueOf(value.toString()));
                    break;
                case SECONDS:
                    asString = TimeFormat.seconds(Integer.valueOf(value.toString()));
                    break;
                case MILLIS:
                    asString = TimeFormat.millis(Long.valueOf(value.toString()));
                    break;
                case DATE:
                    asString = value instanceof Date
                            ? TimeFormat.format((Date) value, TimeFormat.DATE_PATTERN)
                            : TimeFormat.format((Long) value, TimeFormat.DATE_PATTERN);
                    break;
                case TIME:
                    asString = value instanceof Date
                            ? TimeFormat.format((Date) value, TimeFormat.TIME_PATTERN)
                            : TimeFormat.format((Long) value, TimeFormat.TIME_PATTERN);
                    break;
                case DATE_TIME:
                    asString = value instanceof Date
                            ? TimeFormat.format((Date) value, TimeFormat.DATE_TIME_PATTERN)
                            : TimeFormat.format((Long) value, TimeFormat.DATE_TIME_PATTERN);
                    break;
                case TEXT:
                default:
                    asString = toString;
            }
        }
        return asString;
    }
    
    public static void main(String[] args) {
        System.out.println(" \033[0;37m \033[41m Hello \033[0;30m World");
        
        System.out.println("%c Hello %c World");
        
    }
}
