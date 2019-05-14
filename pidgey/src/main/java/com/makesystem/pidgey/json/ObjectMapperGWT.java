/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.json;

import com.github.nmorel.gwtjackson.client.ObjectMapper;

/**
 * How to use in GWT:
 * 
 * 1º Step: Create a interface with our class
 * Ex.:
 * 
 * public interface UserMapper extends ObjectMapperGWT<User> {
 * }
 * 
 * 2º Step: Create a instace of mapper
 * Ex.:
 * 
 * UserMapper mapper = GWT.create(UserMapper.class);
 * 
 * 3º Step: Use your mapper
 * 
 * final String json = mapper.write(user);
 * User user  = mapper.read(json);
 * 
 * 
 * @author Richeli.vargas
 * @param <T>
 */
public interface ObjectMapperGWT<T> extends IsObjectMapper<T>, ObjectMapper<T> {


}
