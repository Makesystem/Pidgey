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
 * @author riche
 */
public class Concatenator {

    private String startWith = StringHelper.EMPTY;
    private String endWith = StringHelper.EMPTY;
    private String separator = StringHelper.EMPTY;

    protected Concatenator() {
    }

    protected Concatenator(final String separator) {
        this.separator = separator;
    }

    protected Concatenator(
            final String startWith,
            final String endWith) {
        this.startWith = startWith;
        this.endWith = endWith;
    }

    protected Concatenator(
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

    public Concatenator setStartWith(final String startWith) {
        this.startWith = startWith;
        return this;
    }

    public String getEndWith() {
        return ObjectHelper.orDefault(endWith, StringHelper.EMPTY);
    }

    public Concatenator setEndWith(final String endWith) {
        this.endWith = endWith;
        return this;
    }

    public String getSeparator() {
        return ObjectHelper.orDefault(separator, StringHelper.EMPTY);
    }

    public Concatenator setSeparator(final String separator) {
        this.separator = separator;
        return this;
    }

    public <V> String concat(final V... values) {
        final String content = Arrays.stream(values)
                .map(var -> var == null ? StringHelper.EMPTY : var.toString())
                .collect(Collectors.joining(getSeparator()));
        return getStartWith() + content + getEndWith();
    }

    /**
     *
     * @return
     */
    public static final Concatenator get() {
        return new Concatenator();
    }

    /**
     *
     * @param separator
     * @return
     */
    public static final Concatenator get(
            final String separator) {
        return new Concatenator(separator);
    }

    /**
     *
     * @param startWith
     * @param endWith
     * @return
     */
    public static final Concatenator get(
            final String startWith,
            final String endWith) {
        return new Concatenator(startWith, endWith);
    }

    /**
     *
     * @param startWith
     * @param endWith
     * @param separator
     * @return
     */
    public static final Concatenator get(
            final String startWith,
            final String endWith,
            final String separator) {
        return new Concatenator(startWith, endWith, separator);
    }
}
