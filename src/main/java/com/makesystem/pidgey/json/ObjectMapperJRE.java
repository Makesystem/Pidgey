/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.makesystem.pidgey.lang.CollectionHelper;
import com.makesystem.pidgey.lang.ObjectHelper;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Richeli.vargas
 */
public class ObjectMapperJRE {
    
    private final static ObjectMapper MAPPER;
    private final static JsonFactory FACTORY;
    private final static TypeFactory TYPE_FACTORY;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        FACTORY = MAPPER.getFactory();
        TYPE_FACTORY = MAPPER.getTypeFactory();
    }

    /**
     *
     * @param <O>
     * @param object
     * @return
     */
    protected static final <O> JavaType getType(final O object) {

        if (object == null) {
            return null;
        }

        final Class objectType = object.getClass();
        final JavaType type;
        if (objectType.isArray()) {
            final O[] array = (O[]) object;
            if (array.length == 0) {
                type = null;
            } else {
                type = TYPE_FACTORY.constructArrayType(array[0].getClass());
            }
        } else if (Collection.class.isAssignableFrom(objectType)) {
            final Collection collection = (Collection) object;
            if (collection.isEmpty()) {
                type = null;
            } else {
                final Class<?> collectionType = CollectionHelper.getCollectionType(collection);
                if (collectionType == null) {
                    type = null;
                } else {
                    type = TYPE_FACTORY.constructCollectionType(objectType, collectionType);
                }
            }
        } else if (Map.class.isAssignableFrom(objectType)) {
            final Map<?, ?> map = (Map) object;
            if (map.isEmpty()) {
                type = null;
            } else {
                final Class<?> mapKeyType = CollectionHelper.getMapKeyType(map);
                final Class<?> mapValueType = CollectionHelper.getMapValueType(map);
                if (mapKeyType == null) {
                    type = null;
                } else if (!ObjectHelper.isBasicJavaClass(mapKeyType)) {
                    throw new IllegalArgumentException("Map Key can not be " + mapKeyType.getName() + ". It must be a primitive type, or a String or an enum.");
                } else {
                    type = TYPE_FACTORY.constructMapType(LinkedHashMap.class, mapKeyType, mapValueType);
                }
            }
        } else {
            type = TYPE_FACTORY.constructType(objectType);
        }

        return type;
    }

    /**
     *
     * @param <O>
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static final <O> String write(final O object) throws JsonProcessingException {
        //final JavaType type = getType(object);
        //if (type == null) {
        return MAPPER.writer().writeValueAsString(object);
        //} else {
        //    Notworking with map.getValues() <-- The Jackson doesn't know this type of collection  
        //    return mapper.writerFor(type).writeValueAsString(object);
        //}
    }

    /**
     *
     * @param <T>
     * @param <R>
     * @param json
     * @param jsonReference
     * @return
     * @throws java.io.IOException
     */
    public static final <T, R extends TypeReference<T>> T read(final String json, final R jsonReference) throws IOException {
        return MAPPER.reader().readValue(FACTORY.createParser(json), jsonReference);
    }

    /**
     *
     * @param <T>
     * @param json
     * @param type
     * @return
     * @throws java.io.IOException
     */
    public static final <T> T read(final String json, final Class<T> type) throws IOException {
        return MAPPER.reader().readValue(FACTORY.createParser(json), type);
    }

    /**
     *
     * @param <T>
     * @param json
     * @param type
     * @return
     * @throws IOException
     */
    protected static final <T> T read(final String json, final JavaType type) throws IOException {
        return MAPPER.reader().readValue(FACTORY.createParser(json), type);
    }

    /**
     *
     * @param <T>
     * @param json
     * @param type
     * @return
     * @throws java.io.IOException
     */
    public static final <T> T[] readArray(final String json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructArrayType(type));
    }

    /**
     *
     * @param <T>
     * @param json
     * @param type
     * @return
     * @throws java.io.IOException
     */
    public static final <T> List<T> readList(final String json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedList.class, type));
    }

    /**
     *
     * @param <T>
     * @param json
     * @param type
     * @return
     * @throws java.io.IOException
     */
    public static final <T> Set<T> readSet(final String json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedHashSet.class, type));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param json
     * @param keyType
     * @param valueType
     * @return
     * @throws java.io.IOException
     */
    public static final <K, V> Map<K, V> readMap(final String json, final Class<K> keyType, final Class<V> valueType) throws IOException {
        return read(json, TYPE_FACTORY.constructMapType(LinkedHashMap.class, keyType, valueType));
    }
}
