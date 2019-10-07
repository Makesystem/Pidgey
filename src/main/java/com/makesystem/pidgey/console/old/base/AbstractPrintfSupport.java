/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console.old.base;

import com.makesystem.pidgey.console.ConsoleColor;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author Richeli.vargas
 */
public abstract class AbstractPrintfSupport implements HasPrintfSupport {
    
    protected ConsoleColor[] colors;
    
    protected abstract String prinfFormat();
    protected abstract String[] prinfArgs();

    public AbstractPrintfSupport(ConsoleColor[] colors) {
        setColors(colors);
    }

    public final ConsoleColor[] getColors() {
        return colors;
    }

    public final void setColors(final ConsoleColor... colors) {
        if (colors == null || (colors.length == 1 && colors[0] == null)) {
            this.colors = new ConsoleColor[]{};
        } else {
            this.colors = colors;
        }
    }
    
    @Override
    public String getPrintfFormat() {

        final boolean hasColors = colors != null && colors.length > 0;
        final StringBuilder format = new StringBuilder();

        if (hasColors) {
            format.append("%s");
        }

        format.append(prinfFormat());

        if (hasColors) {
            format.append("%s");
        }

        return format.toString();
    }

    @Override
    public Object[] getPrintfArgs() {

        final boolean hasColors = colors != null && colors.length > 0;

        final StringBuilder builder = new StringBuilder();
        for (ConsoleColor color : colors) {
            builder.append(color.getColor());
        }

        final Collection<String> collect = new LinkedList<>();
        if (hasColors) {
            collect.add(builder.toString());
        }
        
        collect.addAll(Arrays.asList(prinfArgs()));
        
        if (hasColors) {
            collect.add(ConsoleColor.RESET.getColor());
        }

        return collect.stream().toArray(String[]::new);
    }
    
}
