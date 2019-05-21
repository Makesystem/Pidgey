/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.io;

/**
 *
 * @author Richeli.vargas
 */
public interface GetIpHandler {
    
    public void onSuccess(final String ip);
    
    public void onFailure(final Throwable throwable);
    
}
