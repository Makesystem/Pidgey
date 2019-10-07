/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.system.Environment;
import java.util.function.Consumer;

/**
 *
 * @author riche
 */
public class ConsoleImpl {

    /**
     * Parttener to split text by flags
     */
    //public static final String SPLITTER = Arrays.stream(ConsoleFlag.values())
    //        .map(flag -> flag.getFlag().replace("{", "\\{").replace("}", "\\}"))
    //        .collect(Collectors.joining("|"));
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

    public void log(final String text, final Object... values) {
        this.writer.accept(format(text, values));
    }
    
    public void log(final String text, final Object[][] values) {
        
        final Integer[] columns = new Integer[values[0].length];
        
        for(Object[] row : values){
        }
    }

    protected static String format(final String text, final Object... values) {

        String finalValue = text;
        String temp = text;

        int indexOfStart = temp.indexOf("{");
        int indexOfEnd = temp.indexOf("}") + 1;
        int value = 0;

        while (indexOfStart > -1 && indexOfEnd > 0) {

            final String flag = temp.substring(indexOfStart, indexOfEnd);
            final ConsoleFlag consoleFlag = ConsoleFlag.fromFlag(flag);

            if (consoleFlag != null) {
                // Replace flag by valeu
                finalValue = finalValue.replaceFirst(forRegex(flag), consoleFlag.apply(values[value++]));
            }

            temp = temp.substring(indexOfEnd);
            indexOfStart = temp.indexOf("{");
            indexOfEnd = temp.indexOf("}") + 1;
        }

        return finalValue;
    }

    protected final static String forRegex(final String flag) {
        return flag.replace("{", "\\{").replace("}", "\\}");
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
        System.out.println(data);
    }

    protected final static native void gwt_console(final Object data) /*-{
        console.log(data);
    }-*/;

}
