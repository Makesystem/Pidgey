/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.util;

import com.makesystem.pidgey.io.file.Charset;
import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author riche
 */
public final class Decoder implements Serializable {

    private static final long serialVersionUID = 5808903184504100930L;

    private final boolean isURL;
    private final boolean isMIME;

    Decoder(final boolean isURL, final boolean isMIME) {
        this.isURL = isURL;
        this.isMIME = isMIME;
    }

    /**
     * Lookup table for decoding unicode characters drawn from the "Base64
     * Alphabet" (as specified in Table 1 of RFC 2045) into their 6-bit positive
     * integer equivalents. Characters that are not in the Base64 alphabet but
     * fall within the bounds of the array are encoded to -1.
     *
     */
    protected static final int[] FROM_BASE_64 = new int[256];

    static {
        Arrays.fill(FROM_BASE_64, -1);
        for (int i = 0; i < Encoder.TO_BASE_64.length; i++) {
            FROM_BASE_64[Encoder.TO_BASE_64[i]] = i;
        }
        FROM_BASE_64['='] = -2;
    }

    /**
     * Lookup table for decoding "URL and Filename safe Base64 Alphabet" as
     * specified in Table2 of the RFC 4648.
     */
    protected static final int[] FROM_BASE_64_URL = new int[256];

    static {
        Arrays.fill(FROM_BASE_64_URL, -1);
        for (int i = 0; i < Encoder.TO_BASE_64_URL.length; i++) {
            FROM_BASE_64_URL[Encoder.TO_BASE_64_URL[i]] = i;
        }
        FROM_BASE_64_URL['='] = -2;
    }

    static final Decoder RFC4648 = new Decoder(false, false);
    static final Decoder RFC4648_URLSAFE = new Decoder(true, false);
    static final Decoder RFC2045 = new Decoder(false, true);

    /**
     * Decodes all bytes from the input byte array using the {@link Base64}
     * encoding scheme, writing the results into a newly-allocated output byte
     * array. The returned byte array is of the length of the resulting bytes.
     *
     * @param src the byte array to decode
     *
     * @return A newly-allocated byte array containing the decoded bytes.
     *
     * @throws IllegalArgumentException if {@code src} is not in valid Base64
     * scheme
     */
    public byte[] decode(byte[] src) {
        byte[] dst = new byte[outLength(src, 0, src.length)];
        int ret = decode0(src, 0, src.length, dst);
        if (ret != dst.length) {
            dst = Arrays.copyOf(dst, ret);
        }
        return dst;
    }

    /**
     * Decodes a Base64 encoded String into a newly-allocated byte array using
     * the {@link Base64} encoding scheme.
     *
     * <p>
     * An invocation of this method has exactly the same effect as invoking
     * {@code decode(src.getBytes(StandardCharsets.ISO_8859_1))}
     *
     * @param src the string to decode
     *
     * @return A newly-allocated byte array containing the decoded bytes.
     *
     * @throws IllegalArgumentException if {@code src} is not in valid Base64
     * scheme
     */
    public byte[] decode(String src) {
        if (!isURL && isMIME) {
            src = mime(src);
        }
        return decode(src.getBytes(Charset.ISO_8859_1.toNative()));
    }

    /**
     * Decodes all bytes from the input byte array using the {@link Base64}
     * encoding scheme, writing the results into the given output byte array,
     * starting at offset 0.
     *
     * <p>
     * It is the responsibility of the invoker of this method to make sure the
     * output byte array {@code dst} has enough space for decoding all bytes
     * from the input byte array. No bytes will be be written to the output byte
     * array if the output byte array is not big enough.
     *
     * <p>
     * If the input byte array is not in valid Base64 encoding scheme then some
     * bytes may have been written to the output byte array before
     * IllegalargumentException is thrown.
     *
     * @param src the byte array to decode
     * @param dst the output byte array
     *
     * @return The number of bytes written to the output byte array
     *
     * @throws IllegalArgumentException if {@code src} is not in valid Base64
     * scheme, or {@code dst} does not have enough space for decoding all input
     * bytes.
     */
    public int decode(byte[] src, byte[] dst) {
        int len = outLength(src, 0, src.length);
        if (dst.length < len) {
            throw new IllegalArgumentException(
                    "Output byte array is too small for decoding all input bytes");
        }
        return decode0(src, 0, src.length, dst);
    }

    private int outLength(byte[] src, int sp, int sl) {
        int[] base64 = isURL ? FROM_BASE_64_URL : FROM_BASE_64;
        int paddings = 0;
        int len = sl - sp;
        if (len == 0) {
            return 0;
        }
        if (len < 2) {
            if (isMIME && base64[0] == -1) {
                return 0;
            }
            throw new IllegalArgumentException(
                    "Input byte[] should at least have 2 bytes for base64 bytes");
        }
        if (isMIME) {
            // scan all bytes to fill out all non-alphabet. a performance
            // trade-off of pre-scan or Arrays.copyOf
            int n = 0;
            while (sp < sl) {
                int b = src[sp++] & 0xff;
                if (b == '=') {
                    len -= (sl - sp + 1);
                    break;
                }
                if (base64[b] == -1) {
                    n++;
                }
            }
            len -= n;
        } else {
            if (src[sl - 1] == '=') {
                paddings++;
                if (src[sl - 2] == '=') {
                    paddings++;
                }
            }
        }
        if (paddings == 0 && (len & 0x3) != 0) {
            paddings = 4 - (len & 0x3);
        }
        return 3 * ((len + 3) / 4) - paddings;
    }

    private int decode0(byte[] src, int sp, int sl, byte[] dst) {
        int[] base64 = isURL ? FROM_BASE_64_URL : FROM_BASE_64;
        int dp = 0;
        int bits = 0;
        int shiftto = 18;       // pos of first byte of 4-byte atom
        while (sp < sl) {
            int b = src[sp++] & 0xff;
            if ((b = base64[b]) < 0) {
                if (b == -2) {         // padding byte '='
                    // =     shiftto==18 unnecessary padding
                    // x=    shiftto==12 a dangling single x
                    // x     to be handled together with non-padding case
                    // xx=   shiftto==6&&sp==sl missing last =
                    // xx=y  shiftto==6 last is not =
                    if (shiftto == 6 && (sp == sl || src[sp++] != '=')
                            || shiftto == 18) {
                        throw new IllegalArgumentException(
                                "Input byte array has wrong 4-byte ending unit");
                    }
                    break;
                }
                if (isMIME) // skip if for rfc2045
                {
                    continue;
                } else {
                    throw new IllegalArgumentException(
                            "Illegal base64 character "
                            + Integer.toString(src[sp - 1], 16));
                }
            }
            bits |= (b << shiftto);
            shiftto -= 6;
            if (shiftto < 0) {
                dst[dp++] = (byte) (bits >> 16);
                dst[dp++] = (byte) (bits >> 8);
                dst[dp++] = (byte) (bits);
                shiftto = 18;
                bits = 0;
            }
        }
        // reached end of byte array or hit padding '=' characters.
        switch (shiftto) {
            case 6:
                dst[dp++] = (byte) (bits >> 16);
                break;
            case 0:
                dst[dp++] = (byte) (bits >> 16);
                dst[dp++] = (byte) (bits >> 8);
                break;
            case 12:
                // dangling single "x", incorrectly encoded.
                throw new IllegalArgumentException(
                        "Last unit does not have enough valid bits");
        }
        // anything left is invalid, if is not MIME.
        // if MIME, ignore all non-base64 character
        while (sp < sl) {
            if (isMIME && base64[src[sp++]] < 0) {
                continue;
            }
            throw new IllegalArgumentException(
                    "Input byte array has incorrect ending byte at " + sp);
        }
        return dp;
    }

    /**
     * Remove 'data:image/???;base64,' from src
     *
     * @param src the string to decode
     * @return string to decode without 'data:image/???;base64,'
     */
    private String mime(final String src) {
        if (src.startsWith("data:")) {
            final String[] split = src.split("base64,");
            return split[split.length - 1];
        }
        return src;
    }
}
