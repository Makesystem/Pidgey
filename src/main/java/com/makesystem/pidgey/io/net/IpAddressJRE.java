/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.io.net;

import com.makesystem.pidgey.interfaces.AsyncCallback;
import com.makesystem.pidgey.io.file.FilesHelper;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

/**
 *
 * @author riche
 */
public class IpAddressJRE implements IpAddress {

    private static final long serialVersionUID = 3442322089391693765L;

    @Override
    public String getLocal() throws IOException {
        final InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress.getHostAddress();
    }

    @Override
    public void getLocal(final AsyncCallback<String> asyncCallback) {
        new Thread(() -> {
            try {
                asyncCallback.onSuccess(getLocal());
            } catch (@SuppressWarnings("UseSpecificCatch") final Throwable error) {
                asyncCallback.onFailure(error);
            }
        }, "Getting local ip...").start();
    }

    @Override
    public String getPublic() throws IOException {
        final URL url = new URL(PUBLIC_IP_SOURCE);
        return FilesHelper.read(url.openStream());
    }

    @Override
    public void getPublic(final AsyncCallback<String> asyncCallback) {
        new Thread(() -> {
            try {
                asyncCallback.onSuccess(getPublic());
            } catch (@SuppressWarnings("UseSpecificCatch") final Throwable error) {
                asyncCallback.onFailure(error);
            }
        }, "Getting public ip...").start();
    }

}
