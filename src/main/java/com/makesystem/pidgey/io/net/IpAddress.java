/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.io.net;

import com.makesystem.pidgey.interfaces.AsyncCallback;
import com.makesystem.pidgey.lang.StringHelper;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author riche
 */
public abstract class IpAddress implements Serializable {

    private static final long serialVersionUID = 6152878230211424571L;

    public static final String NO_IP = "0.0.0.0";
    public static final String DEFAULT_GET_IP_URL = "https://www.myexternalip.com/raw";
    
    private String getIpURL = DEFAULT_GET_IP_URL;

    /**
     * 
     * @return 
     */
    public String getGetIpURL() {
        return getIpURL;
    }

    /**
     * 
     * @param getIpURL 
     */
    public void setGetIpURL(final String getIpURL) {
        this.getIpURL = StringHelper.isBlank(getIpURL) ? DEFAULT_GET_IP_URL : getIpURL;
    }
    
    /**
     * Synchronou method
     *
     * @return Local ip address
     * @throws java.io.IOException
     */
    public abstract String getLocal() throws IOException;

    /**
     * Asynchronou method to get local ip address
     *
     * @param asyncCallback
     */
    public abstract void getLocal(final AsyncCallback<String> asyncCallback);

    /**
     * Synchronou method
     *
     * @return Public ip address
     * @throws java.io.IOException
     */
    public abstract String getPublic() throws IOException;

    /**
     * Asynchronou method to get public ip address
     *
     * @param asyncCallback
     */
    public abstract void getPublic(final AsyncCallback<String> asyncCallback);
}
