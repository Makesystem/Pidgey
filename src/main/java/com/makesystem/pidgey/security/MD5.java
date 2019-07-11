/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Richeli.vargas
 */
public class MD5 {

    public static String toMD5(final String data) {

        try {

            final MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(data.getBytes());
            final BigInteger bigInt = new BigInteger(1, m.digest());
            String hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();

        } catch (NoSuchAlgorithmException ignore) {
            return "";
        }
    }
}
