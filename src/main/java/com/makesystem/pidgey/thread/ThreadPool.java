/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Richeli.vargas
 */
public class ThreadPool extends AbstractThreadPool<ThreadPoolExecutor> {

    private static final long serialVersionUID = -2432981912131486269L;

    public ThreadPool() {
        super();
    }

    public ThreadPool(final int numberOfThreads) {
        super(numberOfThreads);
    }

    @Override
    protected ThreadPoolExecutor newInstance(final int nThreads) {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
    }

    public <T> Future<T> execute(final Runnable runnable) {
        final Runnable _runnable = run(runnable, true);
        final ThreadPoolExecutor executor = getExecutor();
        return new FuturePool(this, runnable, executor.submit(_runnable));
    }

    public <T> Future<T> execute(final Callable<T> callable) {
        final Callable _runnable = callable(callable, true);
        final ThreadPoolExecutor executor = getExecutor();
        return new FuturePool(this, callable, executor.submit(_runnable));
    }
    
    public <T> Collection<Future<T>> executeAll(final Runnable... runnables) throws InterruptedException {
        return executeAll(runnables, runnable -> ThreadPool.this.execute(runnable));
    }

    public <T> Collection<Future<T>> executeAll(final Callable<T>... callables) throws InterruptedException {
        return executeAll(callables, callable -> ThreadPool.this.execute(callable));
    }
    
    public <T, R> Collection<Future<R>> executeAll(final T[] callables, Function<T, Future<R>> mapper) throws InterruptedException {
        boolean done = false;
        final Collection<Future<R>> futures = new LinkedList<>();
        try {

            futures.addAll(Arrays.stream(callables)
                    .map(mapper)
                    .collect(Collectors.toList()));

            for (Future<R> future : futures) {
                if (!future.isDone()) {
                    try {
                        future.get();
                    } catch (CancellationException ignore) {
                    } catch (ExecutionException ignore) {
                    }
                }
            }

            done = true;
            return futures;

        } finally {
            if (!done) {
                futures.forEach(future -> future.cancel(true));
            }
        }

    }

    public void waitFinish() {
        do {
            ThreadsHelper.sleep(100);
        } while (hasActiveProcesses());
    }
}
