/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.util;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author riche
 */
public class Base64 implements Serializable {

    private static final long serialVersionUID = -483102036388490057L;

    private Base64() {
    }

    /**
     * Returns a {@link Encoder} that encodes using the
     * <a href="#basic">Basic</a> type base64 encoding scheme.
     *
     * @return A Base64 encoder.
     */
    public static Encoder getEncoder() {
        return Encoder.RFC4648;
    }

    /**
     * Returns a {@link Encoder} that encodes using the
     * <a href="#url">URL and Filename safe</a> type base64 encoding scheme.
     *
     * @return A Base64 encoder.
     */
    public static Encoder getUrlEncoder() {
        return Encoder.RFC4648_URLSAFE;
    }

    /**
     * Returns a {@link Encoder} that encodes using the
     * <a href="#mime">MIME</a> type base64 encoding scheme.
     *
     * @return A Base64 encoder.
     */
    public static Encoder getMimeEncoder() {
        return Encoder.RFC2045;
    }

    /**
     * Returns a {@link Encoder} that encodes using the
     * <a href="#mime">MIME</a> type base64 encoding scheme with specified line
     * length and line separators.
     *
     * @param lineLength the length of each output line (rounded down to nearest
     * multiple of 4). If {@code lineLength <= 0} the output will not be
     * separated in lines
     * @param lineSeparator the line separator for each output line
     *
     * @return A Base64 encoder.
     *
     * @throws IllegalArgumentException if {@code lineSeparator} includes any
     * character of "The Base64 Alphabet" as specified in Table 1 of RFC 2045.
     */
    public static Encoder getMimeEncoder(int lineLength, byte[] lineSeparator) {
        Objects.requireNonNull(lineSeparator);
        int[] base64 = Decoder.FROM_BASE_64;
        for (byte b : lineSeparator) {
            if (base64[b & 0xff] != -1) {
                throw new IllegalArgumentException(
                        "Illegal base64 line separator character 0x" + Integer.toString(b, 16));
            }
        }
        if (lineLength <= 0) {
            return Encoder.RFC4648;
        }
        return new Encoder(Boolean.FALSE, lineSeparator, lineLength >> 2 << 2, Boolean.TRUE);
    }

    /**
     * Returns a {@link Decoder} that decodes using the
     * <a href="#basic">Basic</a> type base64 encoding scheme.
     *
     * @return A Base64 decoder.
     */
    public static Decoder getDecoder() {
        return Decoder.RFC4648;
    }

    /**
     * Returns a {@link Decoder} that decodes using the
     * <a href="#url">URL and Filename safe</a> type base64 encoding scheme.
     *
     * @return A Base64 decoder.
     */
    public static Decoder getUrlDecoder() {
        return Decoder.RFC4648_URLSAFE;
    }

    /**
     * Returns a {@link Decoder} that decodes using the
     * <a href="#mime">MIME</a> type base64 decoding scheme.
     *
     * @return A Base64 decoder.
     */
    public static Decoder getMimeDecoder() {
        return Decoder.RFC2045;
    }

}
