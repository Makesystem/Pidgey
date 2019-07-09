/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

/**
 *
 * @author Richeli.vargas
 * @param <V>
 */
public interface IsSystemProperty<V> {

    public boolean isDefined();
    
    public String getProperty();

    public V getDefaultValue();

    public V getValue();
    
    public <V> void setValue(final V value);

}
