/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.io.net;

import com.makesystem.pidgey.interfaces.AsyncCallback;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author riche
 */
public interface IpAddress extends Serializable {

    public static final String NO_IP = "0.0.0.0";
    public static final String PUBLIC_IP_SOURCE = "https://www.myexternalip.com/raw";
    
    /**
     * Synchronou method
     *
     * @return Local ip address
     * @throws java.io.IOException
     */
    public String getLocal() throws IOException;

    /**
     * Asynchronou method to get local ip address
     *
     * @param asyncCallback
     */
    public void getLocal(final AsyncCallback<String> asyncCallback);

    /**
     * Synchronou method
     *
     * @return Public ip address
     * @throws java.io.IOException
     */
    public String getPublic() throws IOException;

    /**
     * Asynchronou method to get public ip address
     *
     * @param asyncCallback
     */
    public void getPublic(final AsyncCallback<String> asyncCallback);
}
