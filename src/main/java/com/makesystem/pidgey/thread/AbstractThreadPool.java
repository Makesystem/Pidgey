/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Richeli.vargas
 * @param <E>
 */
public abstract class AbstractThreadPool<E extends ExecutorService> {

    // Determine the time to wait for thread is finished
    private static final int TIMEOUT_SHUTDOWN_THREAD = 15;
    // Time unit to wait for thread is finished
    private static final TimeUnit TIME_UNIT_SHUTDOWN_THREAD = TimeUnit.SECONDS;
    // Number of processor cores
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    // Multiplier to automatically calculate the number of threads
    private static final int CORES_MULTIPLIER = 3;

    private final AtomicReference<E> atomicReference = new AtomicReference<>();
    private final Collection<Runnable> executing = Collections.synchronizedCollection(new LinkedList<>());
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

    public E getPool() {
        return atomicReference.get();
    }

    public void setPool(final E pool) {
        this.atomicReference.set(pool);
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

    protected E service() {
        final E service = atomicReference.get();
        if (service == null) {
            initialize();
        } else if (service.isShutdown()) {
            reinitialize();
        }
        return atomicReference.get();
    }

    protected void reinitialize() {
        shutdown(atomicReference.getAndSet(null));
        initialize();
    }

    protected synchronized void initialize() {
        if (atomicReference.get() == null) {
            atomicReference.set(newInstance(getNumberOfThreads()));
        }
    }

    public synchronized void shutdown() {
        final ExecutorService poolToShutdown = atomicReference.getAndSet(null);
        new Thread(() -> {
            // Sleep is to ensure that no execution will be performed on the pool that will be terminated.
            //ThreadsHelper.sleep(TIMEOUT_SHUTDOWN_THREAD, TIME_UNIT_SHUTDOWN_THREAD);
            // Terminates the pool
            shutdown(poolToShutdown, TIMEOUT_SHUTDOWN_THREAD, TIME_UNIT_SHUTDOWN_THREAD);
        }, "Shutdowning thread pool...").start();
    }

    protected boolean shutdown(final ExecutorService pool) {
        return shutdown(pool, TIMEOUT_SHUTDOWN_THREAD, TIME_UNIT_SHUTDOWN_THREAD);
    }

    protected boolean shutdown(final ExecutorService pool, final int timeToWaitShutdown, final TimeUnit timeUnit) {
        if (pool != null) {
            pool.shutdown(); // Disable new tasks from being submitted
            try {
                // Wait a while for existing tasks to terminate
                return pool.awaitTermination(timeToWaitShutdown, timeUnit);
            } catch (final InterruptedException ie) {
                // Preserve interrupt status
                Thread.currentThread().interrupt();
            } finally {
                pool.shutdownNow();
            }
        }
        return false;
    }

    protected void registerRunnable(final Runnable runnable) {
        executing.add(runnable);
    }

    protected void unregisterRunnable(final Runnable runnable) {
        executing.remove(runnable);
    }

    protected int countRunnables() {
        return executing.size();
    }

    protected boolean hasRunnables() {
        return !executing.isEmpty();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    protected Runnable run(final Runnable runnable, final boolean unregisterAtEnd) {
        registerRunnable(runnable);
        //if (unregisterAtEnd) {
        //    initMonitor();
        //}        
        return () -> {
            try {
                runnable.run();
            } catch (final Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                if (unregisterAtEnd) {
                    unregisterRunnable(runnable);
                    callShutdown();
                }
            }
        };
    }

    protected synchronized void callShutdown() {
        if (!hasRunnables() && !isAlwaysActive()) {
            shutdown();
        }
    }
}
