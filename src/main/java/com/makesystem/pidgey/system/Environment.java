/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.system;

/**
 *
 * @author riche
 */
public class Environment {

    public enum Type {
        JRE, GWT
    };

    public static final Type TYPE = discovery();

    protected final static Type discovery() {
        try {
            /*
             * In GWT at client site, this code will throw an exception
             */
            //System.getProperties();
            return Type.JRE;
        } catch (Throwable throwable) {
            return Type.GWT;
        }
    }
}
