/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.util;

/**
 *
 * @author riche
 * @param <V>
 */
@FunctionalInterface
public interface Generator<V> {
    
    public V get();
    
}
