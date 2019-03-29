/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Richeli.vargas
 */
public abstract class AbstractThreadPool {

    // Determine the time to wait for thread is finished
    private static final int TIMEOUT_SHUTDOWN_THREAD = 15;
    // Number of processor cores
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    // Multiplier to automatically calculate the number of threads
    private static final int CORES_MULTIPLIER = 3;

    private int numberOfThreads;
    private ExecutorService pool;

    protected abstract ExecutorService newInstance(final int nThreads);
    public abstract Future<?> execute(final Runnable runnable);
    
    protected AbstractThreadPool() {
        this(0);
    }

    protected AbstractThreadPool(final int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    /**
     *
     * @param numberOfThreads If the value is less than 1, the number of threads
     * will be calculated automatically.
     */
    public void setNumberOfThreads(final int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public void shutdown() {
        Executors.newCachedThreadPool().execute(() -> {
            final ExecutorService poolToShutdown = pool;
            pool = null;
            boolean isShutdown = shutdown(poolToShutdown);
        });
    }

    protected Future<?> submit(final Runnable runnable){
        initialize();
        return pool.submit(runnable);
    }
    
    protected void initialize() {
        if (pool == null) {
            final int nThreads = this.numberOfThreads < 1 ? NUMBER_OF_CORES * CORES_MULTIPLIER : this.numberOfThreads;
            pool = newInstance(nThreads);
        }
    }

    protected boolean shutdown(final ExecutorService pool) {
        return shutdown(pool, TIMEOUT_SHUTDOWN_THREAD, TimeUnit.SECONDS);
    }

    protected boolean shutdown(final ExecutorService pool, final int timeToWaitShutdown, final TimeUnit timeUnit) {
        if (pool != null) {
            pool.shutdown(); // Disable new tasks from being submitted
            try {
                // Wait a while for existing tasks to terminate
                if (!pool.awaitTermination(timeToWaitShutdown, timeUnit)) {
                    // if don´t stopeed within time, then call the method below
                    pool.shutdownNow(); // Cancel currently executing tasks
                    return pool.awaitTermination(timeToWaitShutdown, timeUnit);
                }
                return true;
            } catch (final InterruptedException ie) {
                // (Re-)Cancel if current thread also interrupted
                pool.shutdownNow();
                // Preserve interrupt status
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

}
