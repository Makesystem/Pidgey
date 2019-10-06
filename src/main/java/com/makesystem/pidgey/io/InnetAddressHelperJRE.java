/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

/**
 *
 * @author Richeli.vargas
 */
public class InnetAddressHelperJRE {

    public static void getLocalIp(final GetIpHandler handler) {
        new Thread(() -> {
            try {
                final InetAddress inetAddress = InetAddress.getLocalHost();
                handler.onSuccess(inetAddress.getHostAddress());
            } catch (@SuppressWarnings("UseSpecificCatch") final Throwable error) {
                handler.onFailure(error);
            }
        }, "Getting local ip...").start();
    }

    public static void main(String[] args) {
        getPublicIp(new GetIpHandler() {
            @Override
            public void onSuccess(String ip) {
                System.out.println("IP: " + ip);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
    
    
    public static void getPublicIp(final GetIpHandler handler) {
        new Thread(() -> {
            try {
                final URL whatismyip = new URL("https://www.myexternalip.com/raw");
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(
                            whatismyip.openStream()));
                    final String ip = in.readLine();
                    handler.onSuccess(ip);
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException error) {
                            handler.onFailure(error);
                        }
                    }
                }
            } catch (@SuppressWarnings("UseSpecificCatch") final Throwable error) {
                handler.onFailure(error);
            }
        }, "Getting public ip...").start();
    }
}
