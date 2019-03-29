/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Richeli.vargas
 */
public class ThreadPool extends AbstractThreadPool {

    public ThreadPool() {
        super();
    }

    public ThreadPool(final int numberOfThreads) {
        super(numberOfThreads);
    }

    @Override
    protected ExecutorService newInstance(final int nThreads) {
        return Executors.newFixedThreadPool(nThreads);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Future<?> execute(final Runnable runnable) {
        registerRunnable(runnable);
        return service().submit(() -> {
            try {
                runnable.run();
            } catch (final Throwable throwable) {
                throwable.printStackTrace();
            }
            unregisterRunnable(runnable);
            if (!hasRunnables()) {
                shutdown();
            }
        });
    }
}
