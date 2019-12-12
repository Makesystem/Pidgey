/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread.old;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
    protected final Object process;
    protected final F future;
    protected final PropertyChangeSupport support;
    
    protected FuturePool(final AbstractThreadPool<?> pool, final Object process, final F future) {
        this.pool = pool;
        this.process = process;
        this.future = future;
        this.pool.registerProcess(this.future);
        this.support = new PropertyChangeSupport(this.future);
        this.support.addPropertyChangeListener(event -> {});
    }

    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        try {
            return this.future.cancel(mayInterruptIfRunning);
        } finally {
            this.pool.unregisterProcess(this.future);
        }
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
    public V get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.future.get(timeout, unit);
    }

}
