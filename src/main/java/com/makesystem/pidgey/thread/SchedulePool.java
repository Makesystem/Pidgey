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

    public ScheduledFuture schedule(final Runnable runnable, final long delay, final TimeUnit timeUnit) {
        final Runnable _runnable = run(runnable, true);
        final ScheduledExecutorService executor = getExecutor();
        final ScheduleFuturePool<?> future = new ScheduleFuturePool<>(this, runnable, executor.schedule(_runnable, delay, timeUnit));
        return future;
    }

    public ScheduledFuture scheduleAtFixedRate(final Runnable runnable, final int inicialDelay, final long period, final TimeUnit timeUnit) {
        final Runnable _runnable = run(runnable, false);
        final ScheduledExecutorService executor = getExecutor();
        final ScheduleFuturePool<?> future = new ScheduleFuturePool<>(this, runnable, executor.scheduleAtFixedRate(_runnable, inicialDelay, period, timeUnit));
        return future;
    }

    public ScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final int inicialDelay, final long period, final TimeUnit timeUnit) {
        final Runnable _runnable = run(runnable, false);
        final ScheduledExecutorService executor = getExecutor();
        final ScheduleFuturePool<?> future = new ScheduleFuturePool<>(this, runnable, executor.scheduleWithFixedDelay(_runnable, inicialDelay, period, timeUnit));
        return future;
    }
}
