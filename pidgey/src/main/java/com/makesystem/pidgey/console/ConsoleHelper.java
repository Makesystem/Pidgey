/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

/**
 *
 * @author Richeli.vargas
 */
public class ConsoleHelper {
    
    public static String divider(final String dividerChar, final int width){
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < width; i++) {
            builder.append(dividerChar);
        }
        return builder.toString();
    }
    
    public static ConsoleRow getRow(final Object value) {
        return getRow(value, (ConsoleColor) null);
    }

    public static ConsoleRow getRow(final Object value, final ConsoleColor color) {
        final ConsoleValue valueConsole = new ConsoleValue(value);
        final ConsoleRow rowConsole = new ConsoleRow(valueConsole);
        if (color != null) {
            rowConsole.setColors(color);
        }
        return rowConsole;
    }

    public static ConsoleRow getRow(final Object key, final Object value) {
        return getRow(key, value, null, null);
    }

    public static ConsoleRow getRow(final Object key, final Object value, final Integer keyWith) {
        return getRow(key, value, keyWith, null);
    }

    public static ConsoleRow getRow(final Object key, final Object value, final Integer keyWith, final ConsoleColor color) {
        final ConsoleValue keyConsole = new ConsoleValue(key);
        if (keyWith != null) {
            keyConsole.setLength(keyWith);
        }
        final ConsoleValue valueConsole = new ConsoleValue(value);
        final ConsoleRow rowConsole = new ConsoleRow(keyConsole, valueConsole);
        if (color != null) {
            rowConsole.setColors(color);
        }
        return rowConsole;
    }
    
}
