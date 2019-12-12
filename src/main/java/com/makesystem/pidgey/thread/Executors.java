/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;

/**
 *
 * @author Richeli Vargas
 */
public final class Executors {

    // Number of processor cores
    //private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    // Multiplier to automatically calculate the number of threads
    //private static final int CORES_MULTIPLIER = NUMBER_OF_CORES;
    /**
     * Create fixed thread pools
     */
    public static final class Fixed {

        public static ThreadPoolExecutor create(
                final int poolSize) {
            return create(poolSize, null, null);
        }

        public static ThreadPoolExecutor create(
                final int poolSize,
                final String poolName) {
            return create(poolSize, poolName, null);
        }

        public static ThreadPoolExecutor create(
                final int poolSize,
                final ThreadFactory threadFactory) {
            return create(poolSize, null, threadFactory);
        }

        public static ThreadPoolExecutor create(
                final int poolSize,
                final String poolName,
                final ThreadFactory threadFactory) {
            return new ThreadPoolExecutor(poolSize, poolSize,
                    0L, null, null, threadFactory, null, poolName);
        }

        public static ThreadPoolExecutor single() {
            return single(null, null);
        }

        public static ThreadPoolExecutor single(final String poolName) {
            return single(poolName, null);
        }

        public static ThreadPoolExecutor single(final ThreadFactory threadFactory) {
            return single(null, threadFactory);
        }

        public static ThreadPoolExecutor single(
                final String poolName,
                final ThreadFactory threadFactory) {
            return new ThreadPoolExecutor(1, 1,
                    0L, null, null, threadFactory, null, poolName);
        }
    }

    /**
     * Create adaptive thread pools
     */
    public static final class Adaptive {

        public static ThreadPoolExecutor create(
                final int poolSize) {
            return create(poolSize, 0L, null, null);
        }

        public static ThreadPoolExecutor create(
                final int poolSize,
                final long keepAliveTime) {
            return create(poolSize, keepAliveTime, null, null);
        }

        public static ThreadPoolExecutor create(
                final int poolSize,
                final String poolName) {
            return create(poolSize, 0L, poolName, null);
        }

        public static ThreadPoolExecutor create(
                final int poolSize,
                final long keepAliveTime,
                final String poolName) {
            return create(poolSize, keepAliveTime, poolName, null);
        }

        public static ThreadPoolExecutor create(
                final int poolSize,
                final long keepAliveTime,
                final ThreadFactory threadFactory) {
            return create(poolSize, keepAliveTime, null, threadFactory);
        }

        public static ThreadPoolExecutor create(
                final int poolSize,
                final ThreadFactory threadFactory) {
            return create(poolSize, 0L, null, threadFactory);
        }

        public static ThreadPoolExecutor create(
                final int poolSize,
                final String poolName,
                final ThreadFactory threadFactory) {
            return create(poolSize, 0L, poolName, threadFactory);
        }

        public static ThreadPoolExecutor create(
                final int poolSize,
                final long keepAliveTime,
                final String poolName,
                final ThreadFactory threadFactory) {
            return new ThreadPoolExecutor(0, poolSize,
                    keepAliveTime, null, null, threadFactory, null, poolName);
        }

        // /////////////////////////////////////////////////////////////////////////
        // Adaptable Single Thread Pool
        // /////////////////////////////////////////////////////////////////////////
        public static ThreadPoolExecutor single() {
            return single(null, null);
        }

        public static ThreadPoolExecutor single(final long keepAliveTime) {
            return single(keepAliveTime, null, null);
        }

        public static ThreadPoolExecutor single(final String poolName) {
            return single(poolName, null);
        }

        public static ThreadPoolExecutor single(final long keepAliveTime, final String poolName) {
            return single(keepAliveTime, poolName, null);
        }

        public static ThreadPoolExecutor single(final ThreadFactory threadFactory) {
            return single(null, threadFactory);
        }

        public static ThreadPoolExecutor single(final String poolName, final ThreadFactory threadFactory) {
            return single(0L, poolName, threadFactory);
        }

        public static ThreadPoolExecutor single(final long keepAliveTime, final String poolName, final ThreadFactory threadFactory) {
            return new ThreadPoolExecutor(0, 1, keepAliveTime, null, null, threadFactory, null, poolName);
        }
    }

    /**
     * Create cached thread pools
     */
    public static final class Cached {

        protected static final ThreadPoolExecutor INSTANCE = create("cached--pool");

        public static ThreadPoolExecutor get() {
            return INSTANCE;
        }

        public static ThreadPoolExecutor create() {
            return create(null, null);
        }

        public static ThreadPoolExecutor create(final String poolName) {
            return create(poolName, null);
        }

        public static ThreadPoolExecutor create(final ThreadFactory threadFactory) {
            return create(null, threadFactory);
        }

        public static ThreadPoolExecutor create(final String poolName, final ThreadFactory threadFactory) {
            return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, null, new SynchronousQueue<>(), threadFactory, null, poolName);
        }

    }

    /**
     * Create cached thread pools
     */
    public static final class Schedule {

        public static ScheduledThreadPoolExecutor create() {
            return create(0, null);
        }

        public static ScheduledThreadPoolExecutor create(final String name) {
            return create(0, name);
        }

        public static ScheduledThreadPoolExecutor create(final int corePoolSize) {
            return create(corePoolSize, null);
        }

        public static ScheduledThreadPoolExecutor create(final int corePoolSize, final String name) {
            return new ScheduledThreadPoolExecutor(corePoolSize, name);
        }

    }
}
