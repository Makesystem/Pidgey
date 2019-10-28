/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author Richeli.vargas
 */
public class ThrowableHelper {

    public final static String toString(final Throwable throwable) {
        return toString(throwable, Boolean.FALSE);
    }

    private static String toString(final Throwable throwable, final boolean isCause) {

        if (throwable == null) {
            return "";
        }

        final StringBuilder builder = new StringBuilder();
        
        if (isCause) {
            builder.append(StringHelper.LF).append(StringHelper.TB);
        }
        
        builder.append(throwable.getClass().getName())
                .append(": ")
                .append(throwable.getMessage());
        
        builder.append(StringHelper.LF).append(StringHelper.TB);
        
        builder.append(Arrays.stream(throwable.getStackTrace())
                .map(stackTrace -> stackTrace.toString())
                .collect(Collectors.joining(StringHelper.LF + StringHelper.TB)));
        
        builder.append(toString(throwable.getCause(), Boolean.TRUE));
        
        return builder.toString();
    }

}
