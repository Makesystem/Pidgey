/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Richeli.vargas
 */
public class SchedulePool extends AbstractThreadPool<ScheduledExecutorService> {

    public SchedulePool() {
        super();
    }

    public SchedulePool(final int numberOfThreads) {
        super(numberOfThreads);
    }

    @Override
    protected ScheduledExecutorService newInstance(final int nThreads) {
        return Executors.newScheduledThreadPool(nThreads);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public ScheduledFuture schedule(final Runnable runnable, final long delay, final TimeUnit timeUnit) {
        return service().schedule(() -> abstractRunning(runnable, true), delay, timeUnit);
    }

    public ScheduledFuture scheduleAtFixedRate(final Runnable runnable, final int inicialDelay, final long period, final TimeUnit timeUnit) {
        return service().scheduleAtFixedRate(() -> abstractRunning(runnable, false), inicialDelay, period, timeUnit);
    }

    public ScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final int inicialDelay, final long period, final TimeUnit timeUnit) {
        return service().scheduleWithFixedDelay(() -> abstractRunning(runnable, false), inicialDelay, period, timeUnit);
    }
}
