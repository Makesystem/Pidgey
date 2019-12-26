/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.security;

import com.makesystem.pidgey.lang.StringHelper;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Richeli.vargas
 */
public class MD5 implements Serializable {

    private static final long serialVersionUID = 2139011563175683582L;

    private static final String MD5 = "MD5";
    private static final String ZERO = "0";
    
    public static String toMD5(final String data) {

        try {

            final MessageDigest messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.reset();
            messageDigest.update(data.getBytes());
            final BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = ZERO + hashtext;
            }
            return hashtext.toUpperCase();

        } catch (NoSuchAlgorithmException ignore) {
            return StringHelper.EMPTY;
        }
    }
}
