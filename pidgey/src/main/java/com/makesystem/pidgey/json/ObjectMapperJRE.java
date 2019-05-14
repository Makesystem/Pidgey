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
import com.makesystem.pidgey.lang.ObjectsHelper;
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

    private final static ObjectMapper mapper;
    private final static JsonFactory factory;
    private final static TypeFactory typeFactory;

    static {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        factory = mapper.getFactory();
        typeFactory = mapper.getTypeFactory();
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
                type = typeFactory.constructArrayType(array[0].getClass());
            }
        } else if (Collection.class.isAssignableFrom(objectType)) {
            final Collection collection = (Collection) object;
            if (collection.isEmpty()) {
                type = null;
            } else {
                final Class<?> collectionType = ObjectsHelper.getCollectionType(collection);
                if (collectionType == null) {
                    type = null;
                } else {
                    type = typeFactory.constructCollectionType(LinkedList.class, collectionType);
                }
            }
        } else if (Map.class.isAssignableFrom(objectType)) {
            final Map<?, ?> map = (Map) object;
            if (map.isEmpty()) {
                type = null;
            } else {
                final Class<?> mapKeyType = ObjectsHelper.getMapKeyType(map);
                final Class<?> mapValueType = ObjectsHelper.getMapValueType(map);
                if (mapKeyType == null) {
                    type = null;
                } else if (!ObjectsHelper.isBasicJavaClass(mapKeyType)) {
                    throw new IllegalArgumentException("Map Key can not be " + mapKeyType.getName() + ". It must be a primitive type, or a String or an enum.");
                } else {
                    type = typeFactory.constructMapType(LinkedHashMap.class, mapKeyType, mapValueType);
                }
            }
        } else {
            type = typeFactory.constructType(objectType);
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
        final JavaType type = getType(object);
        if (type == null) {
            return mapper.writer().writeValueAsString(object);
        } else {
            return mapper.writerFor(type).writeValueAsString(object);
        }
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
    public static final <T, R extends TypeReference<T>> T read(final String json, final R jsonReference) throws IOException  {
        return mapper.reader().readValue(factory.createParser(json), jsonReference);
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
        return mapper.reader().readValue(factory.createParser(json), type);
    }
    
    /**
     * 
     * @param <T>
     * @param json
     * @param type
     * @return
     * @throws IOException 
     */
    protected static final <T> T read(final String json, final JavaType type) throws IOException  {
        return mapper.reader().readValue(factory.createParser(json), type);
    }
    
    /**
     * 
     * @param <T>
     * @param json
     * @param type
     * @return 
     * @throws java.io.IOException 
     */
    public static final <T> T[] readArray(final String json, final Class<T> type) throws IOException  {
        return read(json, typeFactory.constructArrayType(type));
    }
    
    /**
     * 
     * @param <T>
     * @param json
     * @param type
     * @return 
     * @throws java.io.IOException 
     */
    public static final <T> List<T> readList(final String json, final Class<T> type) throws IOException{
        return read(json, typeFactory.constructCollectionType(LinkedList.class, type));
    }
    
    /**
     * 
     * @param <T>
     * @param json
     * @param type
     * @return 
     * @throws java.io.IOException 
     */
    public static final <T> Set<T> readSet(final String json, final Class<T> type) throws IOException{
        return read(json, typeFactory.constructCollectionType(LinkedHashSet.class, type));
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
        return read(json, typeFactory.constructMapType(LinkedHashMap.class, keyType, valueType));
    }
}
