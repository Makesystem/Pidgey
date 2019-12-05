/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author riche
 * @param <V>
 * @param <F>
 */
public class FuturePool<V, F extends Future<V>> implements Future<V>, Serializable {

    private static final long serialVersionUID = 1625321830579045181L;

    protected final AbstractThreadPool<?> pool;
    protected final Runnable runnable;
    protected final F future;

    protected FuturePool(final AbstractThreadPool<?> pool, final Runnable runnable, final F future) {
        this.pool = pool;
        this.runnable = runnable;
        this.future = future;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        final boolean cancel = this.future.cancel(mayInterruptIfRunning);
        this.pool.unregisterRunnable(runnable);
        return cancel;
    }

    @Override
    public boolean isCancelled() {
        return this.future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.future.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return this.future.get();
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.future.get(timeout, unit);
    }

}
