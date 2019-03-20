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
public final class ConsoleHelper {

    private static final int DIVIDER_WIDTH = 72;

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
        printDivider("-", DIVIDER_WIDTH, ConsoleColor.BLACK_BOLD);
    }

    public static void printDivider(final String dividerChar) {
        printDivider(dividerChar, DIVIDER_WIDTH, ConsoleColor.BLACK_BOLD);
    }

    public static void printDivider(final int width) {
        printDivider("-", width, ConsoleColor.BLACK_BOLD);
    }

    public static void printDivider(final ConsoleColor... colors) {
        printDivider("-", DIVIDER_WIDTH, colors);
    }

    public static void printDivider(final int width, final ConsoleColor... colors) {
        printDivider("-", width, colors);
    }

    public static void printDivider(final String dividerChar, final int width, final ConsoleColor... colors) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < width; i++) {
            builder.append(dividerChar);
        }
        final ConsoleValue divider = new ConsoleValue(builder, colors);
        printlnEach(divider);
    }
}
