/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gwt.core.shared.GwtIncompatible;
import com.makesystem.pidgey.lang.CollectionHelper;
import com.makesystem.pidgey.lang.ClassHelper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
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
@GwtIncompatible
public class ObjectMapperJRE implements Serializable {

    private static final long serialVersionUID = -8736018675673245679L;

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
                } else if (!ClassHelper.isBasicType(mapKeyType)) {
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
        return MAPPER.writer().writeValueAsString(object);
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    // Read by json reference
    //
    // /////////////////////////////////////////////////////////////////////////
    public static final <T, R extends TypeReference<T>> T read(final String json, final R jsonReference) throws IOException {
        return read(FACTORY.createParser(json), jsonReference);
    }

    public static final <T, R extends TypeReference<T>> T read(final byte[] json, final R jsonReference) throws IOException {
        return read(FACTORY.createParser(json), jsonReference);
    }

    public static final <T, R extends TypeReference<T>> T read(final InputStream json, final R jsonReference) throws IOException {
        return read(FACTORY.createParser(json), jsonReference);
    }

    public static final <T, R extends TypeReference<T>> T read(final File json, final R jsonReference) throws IOException {
        return read(FACTORY.createParser(json), jsonReference);
    }

    public static final <T, R extends TypeReference<T>> T read(final URL json, final R jsonReference) throws IOException {
        return read(FACTORY.createParser(json), jsonReference);
    }
    
    public static final <T, R extends TypeReference<T>> T read(final Reader json, final R jsonReference) throws IOException {
        return read(FACTORY.createParser(json), jsonReference);
    }

    protected static final <T, R extends TypeReference<T>> T read(final JsonParser parser, final R jsonReference) throws IOException {
        return MAPPER.reader().readValue(parser, jsonReference);
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    // Read by class
    //
    // /////////////////////////////////////////////////////////////////////////
    public static final <T> T read(final String json, final Class<T> type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }

    public static final <T> T read(final byte[] json, final Class<T> type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }

    public static final <T> T read(final InputStream json, final Class<T> type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }

    public static final <T> T read(final File json, final Class<T> type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }

    public static final <T> T read(final URL json, final Class<T> type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }
    
    public static final <T> T read(final Reader json, final Class<T> type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }

    protected static final <T> T read(final JsonParser parser, final Class<T> type) throws IOException {
        return MAPPER.reader().readValue(parser, type);
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    // Read by JavaType
    //
    // /////////////////////////////////////////////////////////////////////////
    protected static final <T> T read(final String json, final JavaType type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }

    protected static final <T> T read(final byte[] json, final JavaType type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }

    protected static final <T> T read(final InputStream json, final JavaType type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }

    protected static final <T> T read(final File json, final JavaType type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }

    protected static final <T> T read(final URL json, final JavaType type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }
    
    protected static final <T> T read(final Reader json, final JavaType type) throws IOException {
        return read(FACTORY.createParser(json), type);
    }
    
    protected static final <T> T read(final JsonParser parser, final JavaType type) throws IOException {
        return MAPPER.reader().readValue(parser, type);
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    // Read Array by class
    //
    // /////////////////////////////////////////////////////////////////////////
    public static final <T> T[] readArray(final String json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructArrayType(type));
    }
    
    public static final <T> T[] readArray(final byte[] json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructArrayType(type));
    }
    
    public static final <T> T[] readArray(final InputStream json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructArrayType(type));
    }
    
    public static final <T> T[] readArray(final File json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructArrayType(type));
    }
    
    public static final <T> T[] readArray(final URL json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructArrayType(type));
    }
    
    public static final <T> T[] readArray(final Reader json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructArrayType(type));
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    // Read List by class
    //
    // /////////////////////////////////////////////////////////////////////////
    public static final <T> List<T> readList(final String json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedList.class, type));
    }
    
    public static final <T> List<T> readList(final byte[] json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedList.class, type));
    }
    
    public static final <T> List<T> readList(final InputStream json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedList.class, type));
    }
    
    public static final <T> List<T> readList(final File json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedList.class, type));
    }
    
    public static final <T> List<T> readList(final URL json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedList.class, type));
    }

    public static final <T> List<T> readList(final Reader json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedList.class, type));
    }
    
    // /////////////////////////////////////////////////////////////////////////
    //
    // Read Set by class
    //
    // /////////////////////////////////////////////////////////////////////////
    public static final <T> Set<T> readSet(final String json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedHashSet.class, type));
    }

    public static final <T> Set<T> readSet(final byte[] json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedHashSet.class, type));
    }

    public static final <T> Set<T> readSet(final InputStream json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedHashSet.class, type));
    }

    public static final <T> Set<T> readSet(final File json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedHashSet.class, type));
    }

    public static final <T> Set<T> readSet(final URL json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedHashSet.class, type));
    }
        
    public static final <T> Set<T> readSet(final Reader json, final Class<T> type) throws IOException {
        return read(json, TYPE_FACTORY.constructCollectionType(LinkedHashSet.class, type));
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    // Read Map by class
    //
    // /////////////////////////////////////////////////////////////////////////
    public static final <K, V> Map<K, V> readMap(final String json, final Class<K> keyType, final Class<V> valueType) throws IOException {
        return read(json, TYPE_FACTORY.constructMapType(LinkedHashMap.class, keyType, valueType));
    }

    public static final <K, V> Map<K, V> readMap(final byte[] json, final Class<K> keyType, final Class<V> valueType) throws IOException {
        return read(json, TYPE_FACTORY.constructMapType(LinkedHashMap.class, keyType, valueType));
    }

    public static final <K, V> Map<K, V> readMap(final InputStream json, final Class<K> keyType, final Class<V> valueType) throws IOException {
        return read(json, TYPE_FACTORY.constructMapType(LinkedHashMap.class, keyType, valueType));
    }

    public static final <K, V> Map<K, V> readMap(final File json, final Class<K> keyType, final Class<V> valueType) throws IOException {
        return read(json, TYPE_FACTORY.constructMapType(LinkedHashMap.class, keyType, valueType));
    }

    public static final <K, V> Map<K, V> readMap(final URL json, final Class<K> keyType, final Class<V> valueType) throws IOException {
        return read(json, TYPE_FACTORY.constructMapType(LinkedHashMap.class, keyType, valueType));
    }

    public static final <K, V> Map<K, V> readMap(final Reader json, final Class<K> keyType, final Class<V> valueType) throws IOException {
        return read(json, TYPE_FACTORY.constructMapType(LinkedHashMap.class, keyType, valueType));
    }
}
