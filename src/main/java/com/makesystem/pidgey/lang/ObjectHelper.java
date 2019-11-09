/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import com.makesystem.pidgey.interfaces.Snippet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 *
 * @author Richeli.vargas
 */
public class ObjectHelper {

    public final static boolean isNullOrEmpty(final Object object) {
        return isNull(object) || isEmpty(object);
    }

    public final static boolean isNotNullAndNotEmpty(final Object object) {
        return isNotNull(object) && isNotEmpty(object);
    }

    @SuppressWarnings("null")
    public final static boolean isEmpty(final Object object) {

        if (isNull(object)) {
            throw new IllegalArgumentException("Object is null");
        }

        if (object instanceof Map) {
            return ((Map) object).isEmpty();
        } else if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        } else if (object instanceof Object[]) {
            return ((Object[]) object).length == 0;
        } else {
            return StringHelper.isEmpty(object.toString());
        }
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

    public final static <O> boolean isDifferent(final O object_1, final O object_2) {
        return !isEquals(object_1, object_2);
    }

    public final static <O> O orDefault(final O object, final O defaultValue) {
        if (isNull(object)) {
            return defaultValue;
        }
        return object;
    }

    @SuppressWarnings("null")
    public final static <O> boolean isEquals(final O object_1, final O object_2) {

        if (object_1 == null && object_2 == null) {
            return true;
        }

        if ((object_1 != null && object_2 == null) || (object_1 == null && object_2 != null)) {
            return false;
        }

        final Class<?> class1 = object_1.getClass();
        final Class<?> class2 = object_2.getClass();

        if (class1.isArray() && class2.isArray()) {
            return isEquals(
                    Arrays.asList((O[]) object_1),
                    Arrays.asList((O[]) object_2));
        } else if (Collection.class.isAssignableFrom(class1)
                && Collection.class.isAssignableFrom(class2)) {
            final Collection collection1 = (Collection) object_1;
            final Collection collection2 = (Collection) object_2;

            return collection1.size() == collection2.size()
                    && collection1.containsAll(collection2)
                    && collection2.containsAll(collection1);

        } else if (String.class.isAssignableFrom(class1)
                && String.class.isAssignableFrom(class2)) {
            return object_1.toString().equalsIgnoreCase(object_2.toString());
        } else {
            return object_1.equals(object_2);
        }
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

    public final static boolean isBasicJavaClass(final Class type) {
        if (Character.class.isAssignableFrom(type)) {
            return true;
        } else if (Number.class.isAssignableFrom(type)) {
            return true;
        } else if (Short.class.isAssignableFrom(type)) {
            return true;
        } else if (Integer.class.isAssignableFrom(type)) {
            return true;
        } else if (Long.class.isAssignableFrom(type)) {
            return true;
        } else if (Double.class.isAssignableFrom(type)) {
            return true;
        } else if (Float.class.isAssignableFrom(type)) {
            return true;
        } else if (String.class.isAssignableFrom(type)) {
            return true;
        } else if (Boolean.class.isAssignableFrom(type)) {
            return true;
        } else if (type.isEnum()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param <V>
     * @param type must be a pirimite type or String or enum
     * @param value
     * @return
     */
    @SuppressWarnings("UnnecessaryBoxing")
    public final static <V> V valueOf(final Class type, final String value) {

        if (value == null || value.isEmpty()) {
            return null;
        }

        if (Character.class.isAssignableFrom(type)) {
            return (V) Character.valueOf(value.charAt(0));
        } else if (Short.class.isAssignableFrom(type)) {
            return (V) Short.valueOf(value);
        } else if (Integer.class.isAssignableFrom(type)) {
            return (V) Integer.valueOf(value);
        } else if (Long.class.isAssignableFrom(type)) {
            return (V) Long.valueOf(value);
        } else if (Double.class.isAssignableFrom(type)) {
            return (V) Double.valueOf(value);
        } else if (Float.class.isAssignableFrom(type)) {
            return (V) Float.valueOf(value);
        } else if (Number.class.isAssignableFrom(type)) {
            return (V) Double.valueOf(value);
        } else if (String.class.isAssignableFrom(type)) {
            return (V) String.valueOf(value);
        } else if (Boolean.class.isAssignableFrom(type)) {
            return (V) Boolean.valueOf(value);
        } else if (type.isEnum()) {
            return (V) Enum.valueOf(type, value);
        } else {
            throw new IllegalArgumentException(type + " is not supported. Value must be primitive or String or enum.");
        }
    }
}
