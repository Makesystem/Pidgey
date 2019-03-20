/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.console.base.AbstractPrintfSupport;
import com.makesystem.pidgey.formatation.NumericFormat;

/**
 *
 * @author Richeli.vargas
 */
public class ConsoleValue extends AbstractPrintfSupport {

    public static enum Type {
        TEXT, INTEGER, CURRENCY, NUMBER_OF_BYTES, HEX
    }

    private Object value;
    private int width;
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

    public <T> ConsoleValue(final T value, final int width) {
        this(value, Type.TEXT, width, new ConsoleColor[]{});
    }

    public <T> ConsoleValue(final T value, final Type type, final int width) {
        this(value, type, width, new ConsoleColor[]{});
    }

    public <T> ConsoleValue(final T value, final int width, final ConsoleColor... colors) {
        this(value, Type.TEXT, width, colors);
    }

    public <T> ConsoleValue(final T value, final Type type, final int width, final ConsoleColor... colors) {
        super(colors);
        this.value = value;
        this.type = type == null ? Type.TEXT : type;        
        this.width = width;
    }

    public <T> T getValue() {
        return (T) value;
    }

    public <T> void setValue(final T value) {
        this.value = value;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type == null ? Type.TEXT : type;
    }

    /**
     * If width < 1 the %s is equal %s, or else will be %-'(width * 5)'s @return
     * the %s for this value appling width
     *
     * @return
     */
    @Override
    protected String prinfFormat() {
        return width < 1
                ? "%s"
                : "%-" + (width * 5) + "s";

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
                    asString = NumericFormat.hex(Long.valueOf(value.toString()));
                    break;
                case INTEGER:
                    asString = NumericFormat.integer(Double.valueOf(value.toString()));
                    break;
                case NUMBER_OF_BYTES:
                    asString = NumericFormat.bytes(Long.valueOf(value.toString()));
                    break;
                case TEXT:
                default:
                    asString = toString;
            }
        }
        return asString;
    }
}
