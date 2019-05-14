/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.json;

import java.io.IOException;

/**
 *
 * @author Richeli.vargas
 * @param <T>
 */
public interface IsObjectMapper<T> {

    public T read(final String json) throws IOException;

    public String write(final T object) throws IOException;

}
