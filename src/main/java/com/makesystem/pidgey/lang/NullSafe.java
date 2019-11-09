/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import com.makesystem.pidgey.interfaces.Snippet;
import com.makesystem.pidgey.interfaces.SnippetRes;

/**
 *
 * @author riche
 */
public class NullSafe {

    public static <O> void ifNull(final O toCheck, final Snippet snippet) {
        try {

            if (ObjectHelper.isNull(toCheck)) {
                snippet.exec();
            }

        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }

    public static <O> void nullSafe(final O toCheck, final Snippet snippet) {
        try {

            if (ObjectHelper.isNotNull(toCheck)) {
                snippet.exec();
            }

        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }
    
    public static <O, R> R nullSafe(
            final O toCheck,
            final SnippetRes<R> ifNoNull,
            final SnippetRes<R> ifNull) {

        try {

            if (ObjectHelper.isNull(toCheck)) {
                return ifNull.exec();
            } else {
                return ifNoNull.exec();
            }

        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }

    public static <O> void nullSafe(
            final O toCheck,
            final Snippet ifNoNull,
            final Snippet ifNull) {

        try {

            if (ObjectHelper.isNull(toCheck)) {
                ifNull.exec();
            } else {
                ifNoNull.exec();
            }

        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }

}
