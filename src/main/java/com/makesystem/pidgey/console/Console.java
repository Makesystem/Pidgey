/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.lang.StringHelper;
import java.util.function.Consumer;

/**
 *
 * @author riche
 */
public interface Console {

    static final ConsoleImpl $ = new ConsoleImpl();

    public static void setWriter(final Consumer<Object> writer) {
        $.setWriter(writer);
    }

    public static void log() {
        $.log(StringHelper.EMPTY);
    }

    public static void log(final Object value) {
        $.log(value);
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
    public static void log(final String text, final Object... values) {
        $.log(text, values);
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
    public static void log(final String text, final Object[][] values) {
        $.log(text, values);
    }

}
