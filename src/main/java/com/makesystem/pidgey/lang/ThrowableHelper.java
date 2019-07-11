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
        
        if(throwable == null){
            return "";
        }
        
        final StringBuilder builder = new StringBuilder();
        builder.append("\r");
        builder.append(throwable.getClass().getName())
                .append(": ")
                .append(throwable.getMessage());
        builder.append("\n\t");
        builder.append(Arrays.stream(throwable.getStackTrace())
                .map(stackTrace -> stackTrace.toString())
                .collect(Collectors.joining("\n\t")));
        builder.append(toString(throwable.getCause()));        
        return builder.toString();
    }

}
