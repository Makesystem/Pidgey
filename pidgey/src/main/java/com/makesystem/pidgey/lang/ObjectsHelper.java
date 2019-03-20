/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 * @author Richeli.vargas
 */
public class ObjectsHelper {
    
    public final static <E> Collection toCollection(final Iterable<E> iterable){
        return StreamSupport
                    .stream(iterable.spliterator(), false)
                    .collect(Collectors.toList());
    }
    
    public final static  <T> Collection<List<T>> partition(final Collection<T> list, final int size) {
        final AtomicInteger counter = new AtomicInteger(0);
        return list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                .values();
    }
    
    public final static <T> T[] concat(final T[] first, final T[] last, final IntFunction<T[]> generator){
        final Collection<T> collection = new LinkedList<>();
        collection.addAll(Arrays.asList(first));
        collection.addAll(Arrays.asList(last));
        return collection.stream().toArray(generator);
    }

    public final static boolean isNullOrEmpty(final Object object) {
        return isNull(object) || isEmpty(object);
    }

    public final static boolean isNotNullAndNotEmpty(final Object object) {
        return isNotNull(object) && isNotEmpty(object);
    }

    public final static boolean isEmpty(final Object object) {
        return object.toString().trim().isEmpty() || (object instanceof Object[] ? ((Object[]) object).length == 0 : false);
    }
    
    public final static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }
    
    public final static boolean isNull(final Object object) {
        return object == null;
    }

    public final static boolean isNotNull(final Object object) {
        return !isNull(object);
    }

    public final static boolean isDifferent(final Object obj1, final Object obj2) {
        return !isEquals(obj1, obj2);
    }

    public final static boolean isEquals(final Object obj1, final Object obj2) {
        return (obj1 == null && obj2 == null)
                || ((obj1 != null && obj2 != null)
                && (obj1 instanceof String && obj2 instanceof String
                        ? obj1.toString().equalsIgnoreCase(obj2.toString()) : obj1.equals(obj2)));
    }

    public final static boolean isCollection(final Class objectClass) {
        return Collection.class.isAssignableFrom(objectClass);
    }

    public final static boolean isMap(final Class objectClass) {
        return Map.class.isAssignableFrom(objectClass);
    }

    public final static boolean isJavaClass(final Class fieldClass) {
        if (Character.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (Number.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (Short.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (Integer.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (Long.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (Double.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (Float.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (String.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (Boolean.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (Date.class.isAssignableFrom(fieldClass) || GregorianCalendar.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (Calendar.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (LocalDate.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (LocalTime.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (LocalDateTime.class.isAssignableFrom(fieldClass)) {
            return true;
        } else if (fieldClass.isEnum()) {
            return true;
        } else {
            return false;
        }
    }
}
