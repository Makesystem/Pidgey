/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

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

    public Future<?> execute(final Runnable runnable) {
        final Runnable _runnable = run(runnable, true);
        final ThreadPoolExecutor executor = getExecutor();
        return new FuturePool(this, runnable, executor.submit(_runnable));
    }

    public void waitFinish() {
        do {
            ThreadsHelper.sleep(100);
        } while (hasActiveRunnables());
    }
}
