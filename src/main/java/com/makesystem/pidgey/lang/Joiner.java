/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author riche
 */
public class Joiner implements Serializable {

    private static final long serialVersionUID = 5151302030828813199L;

    private String startWith = StringHelper.EMPTY;
    private String endWith = StringHelper.EMPTY;
    private String separator = StringHelper.EMPTY;
    private boolean ignoreNulls = true;

    protected Joiner() {
    }

    protected Joiner(final String separator) {
        this.separator = separator;
    }

    protected Joiner(
            final String startWith,
            final String endWith) {
        this.startWith = startWith;
        this.endWith = endWith;
    }

    protected Joiner(
            final String startWith,
            final String endWith,
            final String separator) {
        this.startWith = startWith;
        this.endWith = endWith;
        this.separator = separator;
    }

    public String getStartWith() {
        return ObjectHelper.orDefault(startWith, StringHelper.EMPTY);
    }

    public Joiner setStartWith(final String startWith) {
        this.startWith = startWith;
        return this;
    }

    public String getEndWith() {
        return ObjectHelper.orDefault(endWith, StringHelper.EMPTY);
    }

    public Joiner setEndWith(final String endWith) {
        this.endWith = endWith;
        return this;
    }

    public String getSeparator() {
        return ObjectHelper.orDefault(separator, StringHelper.EMPTY);
    }

    public Joiner setSeparator(final String separator) {
        this.separator = separator;
        return this;
    }

    public boolean isIgnoreNulls() {
        return ignoreNulls;
    }

    public Joiner setIgnoreNulls(final boolean ignoreNulls) {
        this.ignoreNulls = ignoreNulls;
        return this;
    }

    public <V> String concat(final V... values) {

        final String content;

        if (ignoreNulls) {
            content = Arrays.stream(values)
                    .filter(var -> var != null)
                    .map(var -> var == null ? StringHelper.EMPTY : var.toString())                    
                    .collect(Collectors.joining(getSeparator()));
        } else {
            content = Arrays.stream(values)
                    .map(var -> var == null ? StringHelper.EMPTY : var.toString())
                    .collect(Collectors.joining(getSeparator()));
        }

        return getStartWith() + content + getEndWith();
    }

    /**
     *
     * @return
     */
    public static final Joiner get() {
        return new Joiner();
    }

    /**
     *
     * @param separator
     * @return
     */
    public static final Joiner get(
            final String separator) {
        return new Joiner(separator);
    }

    /**
     *
     * @param startWith
     * @param endWith
     * @return
     */
    public static final Joiner get(
            final String startWith,
            final String endWith) {
        return new Joiner(startWith, endWith);
    }

    /**
     *
     * @param startWith
     * @param endWith
     * @param separator
     * @return
     */
    public static final Joiner get(
            final String startWith,
            final String endWith,
            final String separator) {
        return new Joiner(startWith, endWith, separator);
    }
}
