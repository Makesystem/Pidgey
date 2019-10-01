/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.console.base.AbstractPrintfSupport;
import com.makesystem.pidgey.lang.CollectionHelper;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 *
 * @author Richeli.vargas
 */
public class ConsoleRow extends AbstractPrintfSupport {

    private ConsoleValue[] values;

    public ConsoleRow() {
        this(null, null);
    }

    public ConsoleRow(final ConsoleValue... values) {
        this(values, null);
    }

    public ConsoleRow(ConsoleValue[] values, final ConsoleColor[] colors) {
        super(colors);
        this.values = values;
    }

    public ConsoleValue[] getValues() {
        return values;
    }

    public void setValues(final ConsoleValue... values) {
        if (values == null || (values.length == 1 && values[0] == null)) {
            this.values = new ConsoleValue[]{};
        } else {
            this.values = values;
        }
    }

    @Override
    protected String prinfFormat() {
        return Arrays.stream(values).map(value -> {
            final String printfFormat;

            if (colors.length > 0) {
                final ConsoleColor[] original = value.getColors();
                final ConsoleColor[] concat = CollectionHelper.concat(colors, original, ConsoleColor[]::new);
                value.setColors(concat);
                printfFormat = value.getPrintfFormat();
                value.setColors(original);
            } else {
                printfFormat = value.getPrintfFormat();
            }

            return printfFormat;
        }).collect(Collectors.joining());
    }

    @Override
    protected String[] prinfArgs() {
        final Collection<Object> vars = new LinkedList<>();
        Arrays.stream(values).forEach(value -> {
            if (colors.length > 0) {
                final ConsoleColor[] original = value.getColors();
                final ConsoleColor[] concat = CollectionHelper.concat(colors, original, ConsoleColor[]::new);
                value.setColors(concat);
                vars.addAll(Arrays.asList(value.getPrintfArgs()));
                value.setColors(original);
            } else {
                vars.addAll(Arrays.asList(value.getPrintfArgs()));
            }
        });
        return vars.stream().map(value -> value.toString()).toArray(String[]::new);
    }
}
