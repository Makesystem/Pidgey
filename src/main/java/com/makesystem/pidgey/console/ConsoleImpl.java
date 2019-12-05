/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.lang.StringHelper;
import com.makesystem.pidgey.lang.ThrowableHelper;
import com.makesystem.pidgey.system.Environment;
import com.makesystem.pidgey.util.Reference;
import java.io.Serializable;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author riche
 */
public class ConsoleImpl implements Serializable {

    private static final long serialVersionUID = -7374189924097418758L;

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

    public void log(final Throwable throwable) {

        final String red = ConsoleColor.RED.getColor();
        final StringBuilder data = new StringBuilder(red);
        data.append(ThrowableHelper
                .toString(throwable)
                .replace(StringHelper.LF, StringHelper.LF + red));

        log(data);
    }

    /**
     * <table>
     * <tr>
     * <th>Tag</th>
     * <th>Description</th>
     * <th>Expected type</th>
     * <th>Example</th>
     * </tr>
     * <tr>
     * <td>{ig}</td>
     * <td>Put it at text start to ignore flag formation.</td>
     * <td></td>
     * <td>
     * <code>Console.log("{c}", 1000);</code>
     * <p>
     * Result: 1.000,00 </p>
     * <code>Console.log("{ig}{i}", 1000);</code>
     * <p>
     * Result: 1000 </p>
     * </td>
     * </tr>
     * <tr>
     * <td>{cc}</td>
     * <td>It sets a ConsoleColor</td>
     * <td>ConsoleColor</td>
     * <td style="color: red;">John</td>
     * </tr>
     * <tr>
     * <td>{s}</td>
     * <td>It sets value as a String</td>
     * <td>Any</td>
     * <td>John</td>
     * </tr>
     * <tr>
     * <td>{i}</td>
     * <td>It sets value as a integer</td>
     * <td>Numeric</td>
     * <td>1.000.000</td>
     * </tr>
     * <tr>
     * <td>{c}</td>
     * <td>It sets value as a currency</td>
     * <td>Numeric</td>
     * <td>1.000.000,00</td>
     * </tr>
     * <tr>
     * <td>{b}</td>
     * <td>It sets value as a bytes counter</td>
     * <td>Numeric</td>
     * <td>1KB</td>
     * </tr>
     * <tr>
     * <td>{hex}</td>
     * <td>It sets value as hexadecimal value</td>
     * <td>Numeric</td>
     * <td>FA601B</td>
     * </tr>
     * <tr>
     * <td>{ss}</td>
     * <td>It sets value as seconds counter, at format:
     * <p>
     * <code>hours:minutes:seconds</code></p></td>
     * <td>Numeric</td>
     * <td>123:30:40</td>
     * </tr>
     * <tr>
     * <td>{ms}</td>
     * <td>It sets value as milliseconds counter, at format:
     * <p>
     * <code>hours:minutes:seconds:milliseconds</code></p></td>
     * <td>Numeric</td>
     * <td>101:30:40:345</td>
     * </tr>
     * <tr>
     * <td>{d}</td>
     * <td>It sets value as date, at format:
     * <p>
     * <code>year/month/day</code></p></td>
     * <td>Numeric or Date</td>
     * <td>2019/12/16</td>
     * </tr>
     * <tr>
     * <td>{t}</td>
     * <td>It sets value as time, at format:
     * <p>
     * <code>hours:minutes:seconds:milliseconds</code></p></td>
     * <td>Numeric or Date</td>
     * <td>13:10:34</td>
     * </tr>
     * <tr>
     * <td>{dt}</td>
     * <td>It sets value as date time, at format:
     * <p>
     * <code>year/month/day hours:minutes:seconds:milliseconds</code></p></td>
     * <td>Numeric or Date</td>
     * <td>13:10:34</td>
     * </tr>
     * </table>
     *
     * @param text
     * @param values
     */
    public void log(final String text, final Object... values) {
        log(text, new Object[][]{values});
    }

    /**
     * <table>
     * <tr>
     * <th>Tag</th>
     * <th>Description</th>
     * <th>Expected type</th>
     * <th>Example</th>
     * </tr>
     * <tr>
     * <td>{ig}</td>
     * <td>Put it at text start to ignore flag formation.</td>
     * <td></td>
     * <td>
     * <code>Console.log("{c}", 1000);</code>
     * <p>
     * Result: 1.000,00 </p>
     * <code>Console.log("{ig}{i}", 1000);</code>
     * <p>
     * Result: 1000 </p>
     * </td>
     * </tr>
     * <tr>
     * <td>{cc}</td>
     * <td>It sets a ConsoleColor</td>
     * <td>ConsoleColor</td>
     * <td style="color: red;">John</td>
     * </tr>
     * <tr>
     * <td>{s}</td>
     * <td>It sets value as a String</td>
     * <td>Any</td>
     * <td>John</td>
     * </tr>
     * <tr>
     * <td>{i}</td>
     * <td>It sets value as a integer</td>
     * <td>Numeric</td>
     * <td>1.000.000</td>
     * </tr>
     * <tr>
     * <td>{c}</td>
     * <td>It sets value as a currency</td>
     * <td>Numeric</td>
     * <td>1.000.000,00</td>
     * </tr>
     * <tr>
     * <td>{b}</td>
     * <td>It sets value as a bytes counter</td>
     * <td>Numeric</td>
     * <td>1KB</td>
     * </tr>
     * <tr>
     * <td>{hex}</td>
     * <td>It sets value as hexadecimal value</td>
     * <td>Numeric</td>
     * <td>FA601B</td>
     * </tr>
     * <tr>
     * <td>{ss}</td>
     * <td>It sets value as seconds counter, at format:
     * <p>
     * <code>hours:minutes:seconds</code></p></td>
     * <td>Numeric</td>
     * <td>123:30:40</td>
     * </tr>
     * <tr>
     * <td>{ms}</td>
     * <td>It sets value as milliseconds counter, at format:
     * <p>
     * <code>hours:minutes:seconds:milliseconds</code></p></td>
     * <td>Numeric</td>
     * <td>101:30:40:345</td>
     * </tr>
     * <tr>
     * <td>{d}</td>
     * <td>It sets value as date, at format:
     * <p>
     * <code>year/month/day</code></p></td>
     * <td>Numeric or Date</td>
     * <td>2019/12/16</td>
     * </tr>
     * <tr>
     * <td>{t}</td>
     * <td>It sets value as time, at format:
     * <p>
     * <code>hours:minutes:seconds:milliseconds</code></p></td>
     * <td>Numeric or Date</td>
     * <td>13:10:34</td>
     * </tr>
     * <tr>
     * <td>{dt}</td>
     * <td>It sets value as date time, at format:
     * <p>
     * <code>year/month/day hours:minutes:seconds:milliseconds</code></p></td>
     * <td>Numeric or Date</td>
     * <td>13:10:34</td>
     * </tr>
     * </table>
     *
     * @param text
     * @param values
     */
    public void log(final String text, final Object[]... values) {
        writer.accept(format(text, values));
    }

    /**
     * Format the values to print into console
     *
     * @param text
     * @param values
     * @return
     */
    public String format(final String text, final Object[]... values) {

        final ConsoleFlag[] flags = ConsoleFlag.fromText(text);
        final int[] maxValues = new int[flags.length];
        final String[][] formattedValues = new String[values.length][flags.length];

        IntStream.range(0, maxValues.length).forEach(column -> maxValues[column] = 0);

        IntStream.range(0, values.length).forEach(row
                -> IntStream.range(0, flags.length).forEach(column -> {
                    final String value = flags[column].apply(values[row][column]);
                    maxValues[column] = Math.max(maxValues[column], value.length());
                    formattedValues[row][column] = value;
                }));

        //System.out.println("sum: " + IntStream.of(maxValues).sum());
        final String print = IntStream.range(0, values.length).mapToObj(row -> {

            final Reference<String> toWrite = new Reference(text);

            IntStream.range(0, flags.length).forEach(column
                    -> toWrite.updateAndGet(to_write -> {

                        final ConsoleFlag flag = flags[column];
                        final int length = maxValues[column];
                        final String value = formattedValues[row][column];
                        final String finalValue = flag.equals(ConsoleFlag.COLOR)
                                ? value
                                : StringHelper.appendAtEnd(value, StringHelper.SPACE, length);

                        return flag.replace(to_write, finalValue);

                    }));

            return toWrite.get();

        }).collect(Collectors.joining(StringHelper.LF));

        return print;
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
        if (data != null) {

            final String string = data.toString();
            final boolean resetColor = string.contains(ConsoleColor.JRE_TAG);

            System.out.println(string
                    // Reset color console for others future prints
                    + (resetColor ? "\033[0m" : ""));
        }
    }

    protected final static native void gwt_console(final Object data) /*-{
        if(data) {
            // Solution for console colors at js console
            console.log(data.toString().replace(/x1b\[/g, '\x1b['));
        }    
    }-*/;

}
