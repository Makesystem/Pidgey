/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 *
 * @author Richeli.vargas
 * @param <V> must be a pirimite type or String or enum
 */
public class SystemProperty<V> implements IsSystemProperty<V> {

    private static final long serialVersionUID = -4280360625862773808L;

    private final String property;
    private final V defaultValue;
    private final Class type;

    /**
     *
     * @param property the property name
     * @param defaultValue must be a pirimite type or String or enum
     */
    public SystemProperty(final String property, final V defaultValue) {

        if (property == null) {
            throw new IllegalArgumentException("Property param can not be null.");
        }

        if (defaultValue == null) {
            throw new IllegalArgumentException("Default value for " + property + " can not be null.");
        }

        this.property = property;
        this.defaultValue = defaultValue;
        this.type = defaultValue.getClass();
    }

    @Override
    public boolean isDefined() {
        return System.getProperty(property) != null;
    }

    @Override
    public String getProperty() {
        return property;
    }

    @Override
    public <V> void setValue(final V value) {
        System.setProperty(property, value == null ? defaultValue.toString() : value.toString());
    }

    @Override
    public V getDefaultValue() {
        return defaultValue;
    }

    @Override
    public V getValue() {
        final String value = System.getProperty(property);
        if (value == null) {
            return defaultValue;
        }
        return ClassHelperJRE.valueOf(type, value);
    }

    protected static final Properties toProperties(final SystemProperty... systemProperties) {
        final Properties properties = new Properties();
        Arrays.stream(systemProperties).forEach(property -> properties.put(property.getProperty(), property.getValue()));
        return properties;
    }

    /**
     * Write properties to a file
     * 
     * @param file
     * @param systemProperties
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static final void write(final String file, final SystemProperty... systemProperties) throws FileNotFoundException, IOException {
        try (OutputStream output = new FileOutputStream(file)) {
            // save properties to project root folder
            toProperties(systemProperties).store(output, null);
        }
    }

    /**
     * Write properties to a file
     * 
     * @param file
     * @param systemProperties
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static final void write(final File file, final SystemProperty... systemProperties) throws FileNotFoundException, IOException {
        try (OutputStream output = new FileOutputStream(file)) {
            // save properties to project root folder
            toProperties(systemProperties).store(output, null);
        }
    }

    /**
     * Load properties to memory.
     * You can get by <code>System.getProperty(...);</code>
     * 
     * @param input
     * @throws IOException 
     */
    protected static final void load(final InputStream input) throws IOException {
        final Properties properties = new Properties();
        properties.load(input);
        properties.entrySet().forEach(entry -> System.setProperty(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
    }

    /**
     * Read file properties to memory.
     * You can get by <code>System.getProperty(...);</code>
     * 
     * @param file
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static final void read(final String file) throws FileNotFoundException, IOException {
        try (final InputStream input = new FileInputStream(file)) {
            load(input);
        }
    }
    
    /**
     * Read file properties to memory.
     * You can get by <code>System.getProperty(...);</code>
     * 
     * @param file
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static final void read(final File file) throws FileNotFoundException, IOException {
        try (final InputStream input = new FileInputStream(file)) {
            load(input);
        }
    }
}
