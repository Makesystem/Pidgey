/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import com.makesystem.pidgey.lang.MathHelper;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Richeli.vargas
 */
public class ThreadsHelper {

    public static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
    }

    public static void sleep(final long value, final TimeUnit unit) {
        sleep(MathHelper.toMillis(value, unit));
    }
}
