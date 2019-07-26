/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.console.base.HasPrintfSupport;

/**
 *
 * @author Richeli.vargas
 */
public final class Console {

    public static final int DIVIDER_WIDTH = 72;
    public static final String DIVIDER_CHARACTER = "-";
    public static final ConsoleColor DIVIDER_COLOR = ConsoleColor.BLACK_BOLD;

    public static void print(final String text, final ConsoleColor... colors) {
        final ConsoleValue consoleValue = new ConsoleValue(text, colors);
        print(consoleValue);
    }

    public static void println(final String text, final ConsoleColor... colors) {
        print(text, colors);
        System.out.println();
    }

    public synchronized static void print(final HasPrintfSupport... values) {
        System.out.print(ConsoleHelper.toPrintf(values));
    }

    public static void printlnEach(final HasPrintfSupport... values) {
        System.out.println(ConsoleHelper.toPrintln(values));
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
        printlnEach(ConsoleHelper.getRowDivider(dividerChar, width, colors));
    }
}
