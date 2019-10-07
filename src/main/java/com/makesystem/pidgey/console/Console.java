/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.monitor.Monitor;
import java.util.Date;
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

    public static void log(final Object value) {
        $.log(value);
    }

    public static void log(final String text, final Object... values) {
        $.log(text, values);
    }

    public static void log(final String text, final Object[][] values) {
        $.log(text, values);
    }

    public static void main(String[] args) {

        final String value = "{s}\t{i}\t{d}";

        final Object[][] values = {
            {"Nome", "{ig}Idade", "{ig}Data"},
            {"Richeli Trindade de Vargas", 30, new Date()},
            {"Patrícia Bays", 25, new Date()},
            {"João", 5, new Date()},};

        Monitor.MONITOR.exec(() -> Console.log(value, "Richeli Trindade de Vargas", 30, new Date())).print();
        Monitor.MONITOR.exec(() -> Console.log(value, "Richeli Trindade de Vargas", 30, new Date())).print();
        Monitor.MONITOR.exec(() -> Console.log(value, "Richeli Trindade de Vargas", 30, new Date())).print();

        Monitor.MONITOR.exec(() -> Console.log(value, values)).print();
        Monitor.MONITOR.exec(() -> Console.log(value, values)).print();
        Monitor.MONITOR.exec(() -> Console.log(value, values)).print();

        Monitor.MONITOR.compare(
                () -> Console.log(value, values),
                () -> Console.log(value, values));
    }
}
