/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

/**
 *
 * @author Richeli.vargas
 */
public class ThreadsHelper {

    public static void sleep(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (final InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
    }
}
