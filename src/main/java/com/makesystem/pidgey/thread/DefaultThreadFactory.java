/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Richeli Vargas
 */
public class DefaultThreadFactory implements java.util.concurrent.ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public DefaultThreadFactory(final String namePrefix) {
        final SecurityManager s = System.getSecurityManager();
        this.group = (s != null) ? s.getThreadGroup()
                : Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix + "--thread-";
    }

    @Override
    public Thread newThread(final Runnable runnable) {        
        final Thread thread = new Thread(group, runnable,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
