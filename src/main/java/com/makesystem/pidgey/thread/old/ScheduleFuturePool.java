/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread.old;

import java.util.concurrent.Delayed;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author riche
 * @param <V>
 */
public class ScheduleFuturePool<V>
        extends FuturePool<V, ScheduledFuture<V>>
        implements Delayed, ScheduledFuture<V> {

    private static final long serialVersionUID = -7023826916973832792L;

    protected ScheduleFuturePool(final AbstractThreadPool<?> pool, final Runnable runnable, final ScheduledFuture<V> future) {
        super(pool, runnable, future);
    }

    @Override
    public long getDelay(final TimeUnit unit) {
        return future.getDelay(unit);
    }

    @Override
    public int compareTo(final Delayed delayed) {
        return future.compareTo(delayed);
    }

}
