/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import com.makesystem.pidgey.interfaces.Snippet;
import com.makesystem.pidgey.interfaces.SnippetRes;
import java.io.Serializable;

/**
 *
 * @author Richeli Vargas
 */
public class NullSafe implements Serializable {

    private static final long serialVersionUID = -7208621565109765382L;

    /**
     *
     * @param <O>
     */
    public interface NullSafeSnippet<O> {

        public void exec(final O checked) throws Throwable;
    }

    /**
     *
     * @param <O>
     * @param <R>
     */
    public interface NullSafeSnippetRes<O, R> {

        public R exec(final O checked) throws Throwable;
    }

    /**
     *
     * @param <O>
     * @param toCheck
     * @param snippet
     */
    public static <O> void ifNull(final O toCheck, final Snippet snippet) {
        try {
            
            if (toCheck == null) {
                snippet.exec();
            }
            
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }

    /**
     *
     * @param <O>
     * @param toCheck
     * @param snippet
     */
    public static <O> void nullSafe(final O toCheck, final NullSafeSnippet<O> snippet) {
        try {

            if (toCheck != null) {
                snippet.exec(toCheck);
            }

        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }

    /**
     *
     * @param <O>
     * @param <R>
     * @param toCheck
     * @param ifNoNull
     * @param ifNull
     * @return
     */
    public static <O, R> R nullSafe(
            final O toCheck,
            final NullSafeSnippetRes<O, R> ifNoNull,
            final SnippetRes<R> ifNull) {

        try {

            if (toCheck == null) {
                return ifNull.exec();
            } else {
                return ifNoNull.exec(toCheck);
            }

        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }

    /**
     *
     * @param <O>
     * @param toCheck
     * @param ifNoNull
     * @param ifNull
     */
    public static <O> void nullSafe(
            final O toCheck,
            final NullSafeSnippet<O> ifNoNull,
            final Snippet ifNull) {

        try {

            if (toCheck == null) {
                ifNull.exec();
            } else {
                ifNoNull.exec(toCheck);
            }

        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }

}
