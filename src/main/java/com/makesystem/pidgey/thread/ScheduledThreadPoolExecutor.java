/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import com.makesystem.pidgey.lang.StringHelper;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A {@link ThreadPoolExecutor} that can additionally schedule commands to run
 * after a given delay, or to execute periodically. This class is preferable to
 * {@link java.util.Timer} when multiple worker threads are needed, or when the
 * additional flexibility or capabilities of {@link ThreadPoolExecutor} (which
 * this class extends) are required.
 *
 * <p>
 * Delayed tasks execute no sooner than they are enabled, but without any
 * real-time guarantees about when, after they are enabled, they will commence.
 * Tasks scheduled for exactly the same execution time are enabled in
 * first-in-first-out (FIFO) order of submission.
 *
 * <p>
 * When a submitted task is cancelled before it is run, execution is suppressed.
 * By default, such a cancelled task is not automatically removed from the work
 * queue until its delay elapses. While this enables further inspection and
 * monitoring, it may also cause unbounded retention of cancelled tasks. To
 * avoid this, set {@link #setRemoveOnCancelPolicy} to {@code true}, which
 * causes tasks to be immediately removed from the work queue at time of
 * cancellation.
 *
 * <p>
 * Successive executions of a task scheduled via {@code scheduleAtFixedRate} or
 * {@code scheduleWithFixedDelay} do not overlap. While different executions may
 * be performed by different threads, the effects of prior executions <a
 * href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * those of subsequent ones.
 *
 * <p>
 * While this class inherits from {@link ThreadPoolExecutor}, a few of the
 * inherited tuning methods are not useful for it. In particular, because it
 * acts as a fixed-sized pool using {@code corePoolSize} threads and an
 * unbounded queue, adjustments to {@code maximumPoolSize} have no useful
 * effect. Additionally, it is almost never a good idea to set
 * {@code corePoolSize} to zero or use {@code allowCoreThreadTimeOut} because
 * this may leave the pool without threads to handle tasks once they become
 * eligible to run.
 *
 * <p>
 * <b>Extension notes:</b> This class overrides the
 * {@link ThreadPoolExecutor#execute(Runnable) execute} and
 * {@link AbstractExecutorService#submit(Runnable) submit} methods to generate
 * internal {@link ScheduledFuture} objects to control per-task delays and
 * scheduling. To preserve functionality, any further overrides of these methods
 * in subclasses must invoke superclass versions, which effectively disables
 * additional task customization. However, this class provides alternative
 * protected extension method {@code decorateTask} (one version each for
 * {@code Runnable} and {@code Callable}) that can be used to customize the
 * concrete task types used to execute commands entered via {@code execute},
 * {@code submit}, {@code schedule}, {@code scheduleAtFixedRate}, and
 * {@code scheduleWithFixedDelay}. By default, a
 * {@code ScheduledThreadPoolExecutor} uses a task type extending
 * {@link FutureTask}. However, this may be modified or replaced using
 * subclasses of the form:
 *
 * <pre> {@code
 * public class CustomScheduledExecutor extends ScheduledThreadPoolExecutor {
 *
 *   static class CustomTask<V> implements RunnableScheduledFuture<V> { ... }
 *
 *   protected <V> RunnableScheduledFuture<V> decorateTask(
 *                Runnable r, RunnableScheduledFuture<V> task) {
 *       return new CustomTask<V>(r, task);
 *   }
 *
 *   protected <V> RunnableScheduledFuture<V> decorateTask(
 *                Callable<V> c, RunnableScheduledFuture<V> task) {
 *       return new CustomTask<V>(c, task);
 *   }
 *   // ... add constructors, etc.
 * }}</pre>
 *
 * @author Richeli Vargas
 */
public class ScheduledThreadPoolExecutor extends java.util.concurrent.ScheduledThreadPoolExecutor {

    private static final AtomicInteger SCHEDULE_NUMBER = new AtomicInteger(1);

    // Determine the time to wait for thread is finished
    private static final int TIMEOUT_SHUTDOWN_THREAD = 15;

    // Time unit to wait for thread is finished
    private static final TimeUnit TIME_UNIT_SHUTDOWN_THREAD = TimeUnit.SECONDS;

    /**
     * The default rejected execution handler
     */
    private static final RejectedExecutionHandler DEFAULT_HANDLER
            = new AbortPolicy();

    /**
     * Controls the shutdown process so that it does not run more than once
     */
    private final AtomicBoolean shutdowning = new AtomicBoolean(false);

    /**
     * Pool name
     */
    private final String name;

    /**
     * Pool number
     */
    private final int number;

    /**
     * Creates a new {@code ScheduledThreadPoolExecutor} with the given core
     * pool size.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if
     * they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param name the schedule name
     * @throws IllegalArgumentException if {@code corePoolSize < 0}
     */
    public ScheduledThreadPoolExecutor(
            final int corePoolSize,
            final String name) {
        this(corePoolSize, null, null, name);
    }

    /**
     * Creates a new {@code ScheduledThreadPoolExecutor} with the given initial
     * parameters.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if
     * they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param threadFactory the factory to use when the executor creates a new
     * thread
     * @param name the schedule name
     * @throws IllegalArgumentException if {@code corePoolSize < 0}
     * @throws NullPointerException if {@code threadFactory} is null
     */
    public ScheduledThreadPoolExecutor(
            final int corePoolSize,
            final ThreadFactory threadFactory,
            final String name) {
        this(corePoolSize, threadFactory, null, name);
    }

    /**
     * Creates a new ScheduledThreadPoolExecutor with the given initial
     * parameters.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if
     * they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param handler the handler to use when execution is blocked because the
     * thread bounds and queue capacities are reached
     * @param name the schedule name
     * @throws IllegalArgumentException if {@code corePoolSize < 0}
     * @throws NullPointerException if {@code handler} is null
     */
    public ScheduledThreadPoolExecutor(
            final int corePoolSize,
            final RejectedExecutionHandler handler,
            final String name) {
        this(corePoolSize, null, handler, name);
    }

    /**
     * Creates a new ScheduledThreadPoolExecutor with the given initial
     * parameters.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if
     * they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param threadFactory the factory to use when the executor creates a new
     * thread
     * @param handler the handler to use when execution is blocked because the
     * thread bounds and queue capacities are reached
     * @param name the schedule name
     * @throws IllegalArgumentException if {@code corePoolSize < 0}
     * @throws NullPointerException if {@code threadFactory} or {@code handler}
     * is null
     */
    public ScheduledThreadPoolExecutor(
            final int corePoolSize,
            final ThreadFactory threadFactory,
            final RejectedExecutionHandler handler,
            final String name) {
        this(corePoolSize, threadFactory, handler, name, SCHEDULE_NUMBER.getAndIncrement());
    }

    /**
     * Creates a new ScheduledThreadPoolExecutor with the given initial
     * parameters.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if
     * they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param threadFactory the factory to use when the executor creates a new
     * thread
     * @param handler the handler to use when execution is blocked because the
     * thread bounds and queue capacities are reached
     * @param name the schedule name
     * @param number the schedule number
     * @throws IllegalArgumentException if {@code corePoolSize < 0}
     * @throws NullPointerException if {@code threadFactory} or {@code handler}
     * is null
     */
    protected ScheduledThreadPoolExecutor(
            final int corePoolSize,
            final ThreadFactory threadFactory,
            final RejectedExecutionHandler handler,
            final String name,
            final int number) {
        super(corePoolSize,
                threadFactory == null
                        ? new DefaultThreadFactory(normalizeName(name, number))
                        : threadFactory,
                handler == null ? DEFAULT_HANDLER : handler);

        this.number = number;
        this.name = normalizeName(name, number);
    }

    /**
     * Normalize pool name
     *
     * @param name
     * @return
     */
    static final String normalizeName(final String name, final int number) {
        if (StringHelper.isBlank(name)) {
            return "schedule-" + number;
        } else {
            return name;
        }
    }
    
    /**
     *
     * @return the pool name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the pool creation number
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Returns a {@code RunnableFuture} for the given runnable and default
     * value.
     *
     * @param runnable the runnable task being wrapped
     * @param value the default value for the returned future
     * @param <T> the type of the given value
     * @return a {@code RunnableFuture} which, when run, will run the underlying
     * runnable and which, as a {@code Future}, will yield the given value as
     * its result and provide for cancellation of the underlying task
     */
    @Override
    protected <T> RunnableFuture<T> newTaskFor(final Runnable runnable, final T value) {
        return new FutureTask<>(runnable, value);
    }

    /**
     * Returns a {@code RunnableFuture} for the given callable task.
     *
     * @param callable the callable task being wrapped
     * @param <T> the type of the callable's result
     * @return a {@code RunnableFuture} which, when run, will call the
     * underlying callable and which, as a {@code Future}, will yield the
     * callable's result as its result and provide for cancellation of the
     * underlying task
     */
    @Override
    protected <T> RunnableFuture<T> newTaskFor(final Callable<T> callable) {
        return new FutureTask<>(callable);
    }

    @Override
    public void shutdown() {

        if (shutdowning.getAndSet(true)) {
            return;
        }

        new Thread(()
                -> shutdown(TIMEOUT_SHUTDOWN_THREAD, TIME_UNIT_SHUTDOWN_THREAD),
                "Shutdowning schedule pool [" + name + "]...").start();

    }

    protected boolean shutdown(final int timeToWaitShutdown, final TimeUnit timeUnit) {

        try {

            super.shutdown(); // Disable new tasks from being submitted

            try {
                // Wait a while for existing tasks to terminate
                return awaitTermination(timeToWaitShutdown, timeUnit);
            } catch (final InterruptedException ie) {
                // Preserve interrupt status
                Thread.currentThread().interrupt();
                return false;
            } finally {
                shutdownNow();
            }

        } finally {
            shutdowning.set(false);
        }
    }
}
