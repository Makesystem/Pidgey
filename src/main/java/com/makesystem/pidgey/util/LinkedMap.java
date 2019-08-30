/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.util;

import java.util.LinkedHashMap;

/**
 *
 * @author Richeli Vargas
 * @param <K>
 * @param <V>
 */
public class LinkedMap<K, V> extends LinkedHashMap<K, V> {

    private Generator<V> defaultGenerator;

    public LinkedMap() {
        super();
    }

    public LinkedMap(final Generator<V> defaultGenerator) {
        super();
        this.defaultGenerator = defaultGenerator;
    }

    public Generator<V> getDefaultGenerator() {
        return defaultGenerator;
    }

    public void setDefaultGenerator(final Generator<V> defaultGenerator) {
        this.defaultGenerator = defaultGenerator;
    }

    public V getOrDefault(final Object key) {

        @SuppressWarnings("element-type-mismatch")
        final V value = get(key);

        if (value == null) {
            return defaultGenerator == null ? null : defaultGenerator.get();
        } else {
            return value;
        }
    }

    @Override
    public V putIfAbsent(final K key, final V value) {

        final V presValue = get(key);

        if (presValue == null) {
            put(key, value);
            return value;
        } else {
            return presValue;
        }
    }

    public V putDefaultIfAbsent(final K key) {
        final V presValue = get(key);

        if (presValue == null) {
            final V value = defaultGenerator == null ? null : defaultGenerator.get();
            put(key, value);
            return value;
        } else {
            return presValue;
        }
    }
}
