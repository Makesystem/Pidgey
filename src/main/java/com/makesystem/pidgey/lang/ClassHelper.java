/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import com.google.gwt.core.shared.GwtIncompatible;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author riche
 */
@GwtIncompatible
public class ClassHelper implements Serializable {

    private static final long serialVersionUID = -755476825849083433L;
    
    public static final Class[] BASIC_CLASSES = {
        String.class,
        Character.class,
        Boolean.class,
        Short.class,
        Integer.class,
        Long.class,
        Float.class,
        Double.class,
        Number.class};

    public static final Class[] JAVA_CLASSES = {
        String.class,
        Character.class,
        Boolean.class,
        Short.class,
        Integer.class,
        Long.class,
        Float.class,
        Double.class,
        Number.class,
        java.math.BigInteger.class,
        java.math.BigDecimal.class,
        java.time.LocalDate.class,
        java.time.LocalTime.class,
        java.time.LocalDateTime.class,
        java.util.Calendar.class,
        java.util.Date.class,
        java.sql.Date.class,
        java.sql.Time.class,
        java.sql.Timestamp.class,
        java.sql.Array.class,
        java.sql.Blob.class,
        java.sql.Clob.class,
        java.sql.SQLXML.class,
        java.util.UUID.class,};

    public final static <O> boolean isDifferent(final O object_1, final O object_2) {
        return !isEquals(object_1, object_2);
    }

    @SuppressWarnings("null")
    public final static <O> boolean isEquals(final O object_1, final O object_2) {

        if (object_1 == null && object_2 == null) {
            return Boolean.TRUE;
        }

        if ((object_1 != null && object_2 == null) || (object_1 == null && object_2 != null)) {
            return Boolean.FALSE;
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

    public final static boolean isCollection(final Class type) {
        return Collection.class.isAssignableFrom(type);
    }

    public final static boolean isMap(final Class type) {
        return Map.class.isAssignableFrom(type);
    }

    public final static boolean isJavaType(final Class type) {
        return type.isPrimitive() || type.isEnum() || isAssignableFrom(type, JAVA_CLASSES);
    }

    public final static boolean isBasicType(final Class type) {
        return type.isPrimitive() || type.isEnum() || isAssignableFrom(type, BASIC_CLASSES);
    }

    public final static boolean isAssignableFrom(final Class type, final Class... classes) {
        for (Class _class : classes) {
            if (_class.isAssignableFrom(type)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static final Class getType(final Field field) throws ClassNotFoundException {

        final Class type = field.getType();
        final Type genericType = field.getGenericType();

        if (Collection.class.isAssignableFrom(type) && genericType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) genericType;
            final Type[] types = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : types) {
                return Class.forName(typeArgument.getTypeName());
            }
        } else if (type.isArray()) {
            return type.getComponentType();
        }

        return type;
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

    public static Collection<Field> listFields(final Class classToRead) {

        final Collection<Field> fields = new LinkedList<>();

        if (classToRead == null) {
            return fields;
        }

        final Class parent = classToRead.getSuperclass();

        if (parent != null && parent != Object.class) {
            fields.addAll(listFields(parent));
        }

        fields.addAll(Arrays.asList(classToRead.getDeclaredFields()));

        return fields;
    }

    public static Collection<Field> listFields(final Class classToRead, final Predicate<Field> filter) {

        final Collection<Field> fields = new LinkedList<>();

        if (classToRead == null) {
            return fields;
        }

        final Class parent = classToRead.getSuperclass();

        if (parent != null && parent != Object.class) {
            fields.addAll(listFields(parent, filter));
        }

        fields.addAll(Arrays
                .stream(classToRead.getDeclaredFields())
                .filter(filter)
                .collect(Collectors.toList()));

        return fields;
    }
}
