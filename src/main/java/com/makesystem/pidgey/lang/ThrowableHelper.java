/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import com.google.gwt.core.shared.GwtIncompatible;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 *
 * @author Richeli.vargas
 */
public class ThrowableHelper implements Serializable {

    private static final long serialVersionUID = 7099954149513244425L;

    protected static final String MESSAGE_MATCH = "(.+):\\s+(.*)";
    protected static final String STACK_TRACE_MATCH = "\\s*(.*)\\((.*):(\\d+)\\)\\s*";
    protected static final String CLASS_END = ": ";

    public final static String toString(final Throwable throwable) {
        return toString(throwable, Boolean.FALSE);
    }

    private static String toString(final Throwable throwable, final boolean isCause) {

        if (throwable == null) {
            return StringHelper.EMPTY;
        }

        final StringBuilder builder = new StringBuilder();

        if (isCause) {
            builder.append(StringHelper.LF).append(StringHelper.TB);
        }

        builder.append(throwable.getClass().getName())
                .append(CLASS_END)
                .append(throwable.getMessage());

        builder.append(StringHelper.LF).append(StringHelper.TB);

        builder.append(Arrays.stream(throwable.getStackTrace())
                .map(stackTrace -> stackTrace.toString())
                .collect(Collectors.joining(StringHelper.LF + StringHelper.TB)));

        builder.append(toString(throwable.getCause(), Boolean.TRUE));

        return builder.toString();
    }

    @GwtIncompatible
    public static Throwable fromString(final String string) {

        final String exception = string.replace(StringHelper.TB, StringHelper.LF);
        final String[] split = exception.split(StringHelper.LF, -1);

        Throwable lastThrowable = null;
        final LinkedList<StackTraceElement> statckTrace = new LinkedList<>();

        /*
         * All content should be read backwards
         */
        for (int index = split.length - 1; index > -1; index--) {
            final String line = split[index];

            if (line.matches(MESSAGE_MATCH)) {

                final Throwable throwable = toThrowable(line, lastThrowable);
                throwable.setStackTrace(statckTrace.stream().toArray(StackTraceElement[]::new));
                
                lastThrowable = throwable;
                statckTrace.clear();
                
            } else if (line.matches(STACK_TRACE_MATCH)) {
                /*
                 * Correct placement of elements
                 */
                statckTrace.add(0, toStackTraceElement(line));
            }
        }

        return lastThrowable;
    }

    @GwtIncompatible
    protected static Throwable toThrowable(final String line) {
        return toThrowable(line, null);
    }
    
    @GwtIncompatible
    protected static Throwable toThrowable(final String line, final Throwable cause) {

        if (StringHelper.isBlank(line)) {
            return new Exception(line);
        }

        try {
            final String[] split = line.split(StringHelper.DOUBLE_DOTS);
            final Class exception = Class.forName(split[0]);
            final java.lang.reflect.Constructor constructor = exception.getDeclaredConstructor(String.class, Throwable.class);
            return (Throwable) constructor.newInstance(split[1], cause);
        } catch (@SuppressWarnings("UseSpecificCatch") Throwable ignore) {
            return new Exception(line);
        }
    }

    @GwtIncompatible
    protected static StackTraceElement toStackTraceElement(final String line) {

        try {

            final String[] split_1 = line.split(StringHelper.REAL_OP);
            final String[] split_2 = split_1[1].split(StringHelper.DOUBLE_DOTS);

            final int lastDot = split_1[0].lastIndexOf(StringHelper.DOT);
            final int lineNumberEnd = split_2[1].indexOf(StringHelper.CP);

            final String declaringClass = split_1[0].substring(0, lastDot);
            final String methodName = split_1[0].substring(lastDot + 1);
            final String fileName = split_2[0];
            final int lineNumber = Integer.valueOf(split_2[1].substring(0, lineNumberEnd));

            return new StackTraceElement(declaringClass, methodName, fileName, lineNumber);

        } catch (@SuppressWarnings("UseSpecificCatch") Throwable ignore) {
            return null;
        }
    }
}
