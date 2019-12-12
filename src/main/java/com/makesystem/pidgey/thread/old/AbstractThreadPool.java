/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread.old;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Richeli.vargas
 * @param <E>
 */
public abstract class AbstractThreadPool<E extends ExecutorService> implements Serializable {

    private static final long serialVersionUID = 9049250666057026471L;

    // Determine the time to wait for thread is finished
    private static final int TIMEOUT_SHUTDOWN_THREAD = 15;
    // Time unit to wait for thread is finished
    private static final TimeUnit TIME_UNIT_SHUTDOWN_THREAD = TimeUnit.SECONDS;
    // Number of processor cores
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    // Multiplier to automatically calculate the number of threads
    private static final int CORES_MULTIPLIER = 3;

    private final AtomicReference<E> atomicReference = new AtomicReference<>();
    private final Collection<Future> processes = new ConcurrentLinkedQueue<>();
    private int numberOfThreads;
    private boolean alwaysActive = false;

    protected abstract E newInstance(final int nThreads);

    protected AbstractThreadPool() {
        this(0);
    }

    protected AbstractThreadPool(final int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public int getNumberOfThreads() {
        return Math.max(1, this.numberOfThreads < 1
                ? NUMBER_OF_CORES * CORES_MULTIPLIER
                : this.numberOfThreads);
    }

    /**
     *
     * @param numberOfThreads If the value is less than 1, the number of threads
     * will be calculated automatically.
     */
    public void setNumberOfThreads(final int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public boolean isAlwaysActive() {
        return alwaysActive;
    }

    /**
     * If <code>true</code>, the thread pool will never be terminated. The
     * default value is <code>false</code>
     *
     * @param alwaysActive
     */
    public void setAlwaysActive(boolean alwaysActive) {
        this.alwaysActive = alwaysActive;
    }

    protected synchronized void callShutdown() {
        if (!hasActiveProcesses() && !isAlwaysActive()) {
            shutdown();
        }
    }

    protected E getExecutor() {
        return atomicReference.updateAndGet(poll
                -> poll == null || poll.isShutdown()
                ? newInstance(getNumberOfThreads())
                : poll);
    }

    public synchronized void shutdown() {
        final ExecutorService poolToShutdown = atomicReference.getAndSet(null);
        new Thread(() -> shutdown(poolToShutdown, TIMEOUT_SHUTDOWN_THREAD, TIME_UNIT_SHUTDOWN_THREAD),
                "Shutdowning thread pool...").start();
    }

    protected boolean shutdown(final ExecutorService pool) {
        return shutdown(pool, TIMEOUT_SHUTDOWN_THREAD, TIME_UNIT_SHUTDOWN_THREAD);
    }

    protected boolean shutdown(final ExecutorService pool, final int timeToWaitShutdown, final TimeUnit timeUnit) {

        if (pool == null) {
            return false;
        }

        pool.shutdown(); // Disable new tasks from being submitted

        try {
            // Wait a while for existing tasks to terminate
            return pool.awaitTermination(timeToWaitShutdown, timeUnit);
        } catch (final InterruptedException ie) {
            // Preserve interrupt status
            Thread.currentThread().interrupt();
            return false;
        } finally {
            pool.shutdownNow();
        }
    }

    protected void registerProcess(final Future process) {
        processes.add(process);
    }

    protected void unregisterProcess(final Future process) {
        processes.remove(process);
        callShutdown();
    }

    public int getActiveProcesses() {
        return processes.size();
    }

    public boolean hasActiveProcesses() {
        return !processes.isEmpty();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    protected Runnable run(final Runnable runnable, final boolean unregisterAtEnd) {
        //registerProcess(runnable);
        return () -> {
            try {
                runnable.run();
            } catch (final Throwable ignore) {
                ignore.printStackTrace();
            } finally {
                if (unregisterAtEnd) {
                    //unregisterProcess(runnable);
                }
            }
        };
    }
    
    protected <T> Callable<T> callable(final Callable<T> callable, final boolean unregisterAtEnd) {
        //registerProcess(callable);
        return () -> {
            try {
                return callable.call();
            } finally {
                if (unregisterAtEnd) {
                    //unregisterProcess(callable);
                }
            }
        };
    }
}
