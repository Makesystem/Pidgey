/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.lang.StringHelper;
import com.makesystem.pidgey.system.Environment;
import java.util.function.Consumer;

/**
 *
 * @author riche
 */
public class ConsoleImpl {

    /**
     * Parttener to split text by flags
     */
    //public static final String SPLITTER = Arrays.stream(ConsoleFlag.values())
    //        .map(flag -> flag.getFlag().replace("{", "\\{").replace("}", "\\}"))
    //        .collect(Collectors.joining("|"));
    /**
     * Writer for environment in use
     */
    public static final Consumer<Object> WRITER = discovery();

    private Consumer<Object> writer;

    public ConsoleImpl() {
        this(WRITER);
    }

    public ConsoleImpl(final Consumer<Object> writer) {
        this.writer = writer;
    }

    public final void setWriter(final Consumer<Object> writer) {
        this.writer = writer;
    }

    public void log(final Object value) {
        this.writer.accept(String.valueOf(value));
    }

    public void log(final String text, final Object... values) {
        log(text, new Object[][]{values});
    }

    public void log(final String text, final Object[]... values) {

        final ConsoleFlag[] flags = ConsoleFlag.fromText(text);
        final Integer[] maxValues = new Integer[flags.length];
        final String[][] formattedValues = new String[values.length][flags.length];

        for (int column = 0; column < maxValues.length; column++) {
            maxValues[column] = 0;
        }

        for (int row = 0; row < values.length; row++) {
            for (int column = 0; column < flags.length; column++) {
                final String value = flags[column].apply(values[row][column]);
                maxValues[column] = Math.max(maxValues[column], value.length());
                formattedValues[row][column] = value;
            }
        }

        for (int row = 0; row < values.length; row++) {
            String toWrite = text;
            for (int column = 0; column < flags.length; column++) {

                final ConsoleFlag flag = flags[column];
                final int length = maxValues[column];
                final String value = formattedValues[row][column];
                final String finalValue = StringHelper.appendAtEnd(value, StringHelper.SPACE, length);

                toWrite = flag.replace(toWrite, finalValue);
            }

            // Write the value
            writer.accept(toWrite);
        }
    }

    protected final static Consumer<Object> discovery() {
        switch (Environment.TYPE) {
            case GWT:
                return ConsoleImpl::gwt_console;
            case JRE:
            default:
                return ConsoleImpl::jre_console;
        }
    }

    protected final static void jre_console(final Object data) {
        System.out.println(data);
    }

    protected final static native void gwt_console(final Object data) /*-{
        console.log(data);
    }-*/;

}
