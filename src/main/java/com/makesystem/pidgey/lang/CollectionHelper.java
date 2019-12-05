/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 * @author riche
 */
public class CollectionHelper implements Serializable {

    private static final long serialVersionUID = -2250164591767814510L;

    public final static <E> Collection toCollection(final Iterable<E> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public final static <T> Collection<List<T>> partition(final Collection<T> list, final int size) {
        final AtomicInteger counter = new AtomicInteger(0);
        return list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                .values();
    }

    public final static <T> T[] concat(final T[] first, final T[] last, final IntFunction<T[]> generator) {
        final Collection<T> collection = new LinkedList<>();
        collection.addAll(Arrays.asList(first));
        collection.addAll(Arrays.asList(last));
        return collection.stream().toArray(generator);
    }

    public final static <T> T[] add(final T[] first, final T last, final IntFunction<T[]> generator) {
        final Collection<T> collection = new LinkedList<>();
        collection.addAll(Arrays.asList(first));
        collection.add(last);
        return collection.stream().toArray(generator);
    }

    public final static Class<?> getCollectionType(final Collection<?> collection) {
        return collection.isEmpty() ? null : collection.stream().filter(value -> value != null).findAny().map(value -> value.getClass()).orElse(null);
    }

    public final static Class<?> getMapKeyType(final Map<?, ?> map) {
        return map.isEmpty() ? null : map.keySet().stream().filter(key -> key != null).findAny().map(key -> key.getClass()).orElse(null);
    }

    public final static Class<?> getMapValueType(final Map<?, ?> map) {
        return map.isEmpty() ? null : map.values().stream().filter(value -> value != null).findAny().map(value -> value.getClass()).orElse(null);
    }

}
