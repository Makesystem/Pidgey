/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.console.base.HasPrintfSupport;
import java.util.Arrays;

/**
 *
 * @author Richeli.vargas
 */
public final class Console {

    public static final int DIVIDER_WIDTH = 72;
    public static final String DIVIDER_CHARACTER = "-";
    public static final ConsoleColor DIVIDER_COLOR = ConsoleColor.BLACK_BOLD;

    public static int tabLength() {
        try {
            return Integer.valueOf(System.getProperty("tab_length", "5"));
        } catch (NumberFormatException ignore) {
            return 5;
        }
    }

    public static void print(final String text, final ConsoleColor... colors) {
        final ConsoleValue consoleValue = new ConsoleValue(text, colors);
        print(consoleValue);
    }

    public static void println(final String text, final ConsoleColor... colors) {
        print(text, colors);
        System.out.println();
    }

    public static void print(final HasPrintfSupport... values) {
        Arrays.asList(values).forEach(value -> System.out.printf(value.getPrintfFormat(), value.getPrintfArgs()));
    }

    public static void printlnEach(final HasPrintfSupport... values) {
        Arrays.asList(values).forEach(value -> System.out.printf(value.getPrintfFormat(), value.getPrintfArgs()).println());
    }

    public static void println(final HasPrintfSupport... values) {
        print(values);
        System.out.println();
    }

    public static void printDivider() {
        printDivider(DIVIDER_CHARACTER, DIVIDER_WIDTH, DIVIDER_COLOR);
    }

    public static void printDivider(final String dividerChar) {
        printDivider(dividerChar, DIVIDER_WIDTH, DIVIDER_COLOR);
    }

    public static void printDivider(final int width) {
        printDivider(DIVIDER_CHARACTER, width, DIVIDER_COLOR);
    }

    public static void printDivider(final ConsoleColor... colors) {
        printDivider(DIVIDER_CHARACTER, DIVIDER_WIDTH, colors);
    }

    public static void printDivider(final int width, final ConsoleColor... colors) {
        printDivider(DIVIDER_CHARACTER, width, colors);
    }

    public static void printDivider(final String dividerChar, final int width, final ConsoleColor... colors) {
        final ConsoleValue divider = new ConsoleValue(ConsoleHelper.divider(dividerChar, width), colors);
        printlnEach(divider);
    }
}
