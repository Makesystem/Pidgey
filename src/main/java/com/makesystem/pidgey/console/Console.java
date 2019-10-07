/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

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

    
    
    public static void main(String[] args) {

        final String value = "{s}Teste {s}{s} aqui {d} ali {b}";

        Console.log("Name: {s} \t Age: {i}", "Richeli Vargas", 30);
        Console.log("Name: {s} \t Age: {i}", "Patrícia Bays", 25);
    }
}
