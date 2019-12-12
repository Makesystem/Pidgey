/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;

/**
 * A cancellable asynchronous computation. This class provides a base
 * implementation of {@link Future}, with methods to start and cancel a
 * computation, query to see if the computation is complete, and retrieve the
 * result of the computation. The result can only be retrieved when the
 * computation has completed; the {@code get} methods will block if the
 * computation has not yet completed. Once the computation has completed, the
 * computation cannot be restarted or cancelled (unless the computation is
 * invoked using {@link #runAndReset}).
 *
 * <p>
 * A {@code FutureTask} can be used to wrap a {@link Callable} or
 * {@link Runnable} object. Because {@code FutureTask} implements
 * {@code Runnable}, a {@code FutureTask} can be submitted to an
 * {@link Executor} for execution.
 *
 * <p>
 * In addition to serving as a standalone class, this class provides
 * {@code protected} functionality that may be useful when creating customized
 * task classes.
 *
 * @author Richeli Vargas
 * @param <V> The result type returned by this FutureTask's {@code get} methods
 */
public class FutureTask<V> extends java.util.concurrent.FutureTask<V> {

    /**
     * Creates a {@code FutureTask} that will, upon running, execute the given
     * {@code Callable}.
     *
     * @param callable the callable task
     * @throws NullPointerException if the callable is null
     */
    public FutureTask(final Callable<V> callable) {
        super(callable);
    }

    /**
     * Creates a {@code FutureTask} that will, upon running, execute the given
     * {@code Runnable}, and arrange that {@code get} will return the given
     * result on successful completion.
     *
     * @param runnable the runnable task
     * @param result the result to return on successful completion. If you don't
     * need a particular result, consider using constructions of the form:
     * {@code Future<?> f = new FutureTask<Void>(runnable, null)}
     * @throws NullPointerException if the runnable is null
     */
    public FutureTask(final Runnable runnable, final V result) {
        super(runnable, result);
    }

    @Override
    protected void done() {
        
        state();
        
    }

    void state() {
    
        try {
            
            final Class<?> k = java.util.concurrent.FutureTask.class;
            final Field field = k.getDeclaredField("state");
            field.setAccessible(true);
            final int state = field.getInt(this);
            
            
            
        } catch(Throwable throwable){
            throwable.printStackTrace();
        }
        
    }
}
