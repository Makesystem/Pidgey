/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.util;

import com.makesystem.pidgey.interfaces.Generator;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Richeli Vargas
 * @param <K>
 * @param <V>
 */
public class ConcurrentMap<K, V> extends ConcurrentHashMap<K, V> {

    private static final long serialVersionUID = 3382837990238655476L;

    private Generator<V> defaultGenerator;

    public ConcurrentMap() {
        super();
    }

    public ConcurrentMap(final Generator<V> defaultGenerator) {
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
    public V put(K key, V value) {
        super.put(key, value);
        return value;
    }

    @Override
    public V putIfAbsent(final K key, final V value) {
        final V presValue = get(key);

        if (presValue == null) {
            final V old = super.put(key, value);
            return old == null ? value : old;
        } else {
            return presValue;
        }
    }

    public V putDefaultIfAbsent(final K key) {
        final V presValue = get(key);

        if (presValue == null) {
            final V value = defaultGenerator == null ? null : defaultGenerator.get();
            final V old = super.put(key, value);
            return old == null ? value : old;
        } else {
            return presValue;
        }
    }
}
