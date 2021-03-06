/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.io.file;

/**
 *
 * @author Richeli.vargas
 */
public enum Charset {

    US_ASCII("US-ASCII"),
    ISO_8859_1("ISO-8859-1"),
    UTF_8("UTF-8"),
    UTF_16BE("UTF-16BE"),
    UTF_16LE("UTF-16LE"),
    UTF_16("UTF-16"),
    WINDOWS_1255("Cp1255");

    private final String name;

    private Charset(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public final java.nio.charset.Charset toNative() {
        return java.nio.charset.Charset.forName(name);
    }

    public static Charset fromName(final String name) {

        final Charset charset = fromName(name, null);

        if (charset == null) {
            throw new IllegalArgumentException(name + " is not a valid charset.");
        }

        return charset;
    }

    public static Charset fromName(final String name, final Charset default_charset) {

        for (Charset charset : Charset.values()) {
            if (charset.getName().equalsIgnoreCase(name)) {
                return charset;
            }
        }

        return default_charset;
    }
}
