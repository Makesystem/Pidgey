/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import com.makesystem.pidgey.lang.StringHelper;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An {@link ExecutorService} that executes each submitted task using one of
 * possibly several pooled threads, normally configured using {@link Executors}
 * factory methods.
 *
 * <p>
 * Thread pools address two different problems: they usually provide improved
 * performance when executing large numbers of asynchronous tasks, due to
 * reduced per-task invocation overhead, and they provide a means of bounding
 * and managing the resources, including threads, consumed when executing a
 * collection of tasks. Each {@code ThreadPoolExecutor} also maintains some
 * basic statistics, such as the number of completed tasks.
 *
 * <p>
 * To be useful across a wide range of contexts, this class provides many
 * adjustable parameters and extensibility hooks. However, programmers are urged
 * to use the more convenient {@link Executors} factory methods {@link
 * Executors#newCachedThreadPool} (unbounded thread pool, with automatic thread
 * reclamation), {@link Executors#newFixedThreadPool} (fixed size thread pool)
 * and {@link
 * Executors#newSingleThreadExecutor} (single background thread), that
 * preconfigure settings for the most common usage scenarios. Otherwise, use the
 * following guide when manually configuring and tuning this class:
 *
 * <dl>
 *
 * <dt>Core and maximum pool sizes</dt>
 *
 * <dd>A {@code ThreadPoolExecutor} will automatically adjust the pool size (see
 * {@link #getPoolSize}) according to the bounds set by corePoolSize (see
 * {@link #getCorePoolSize}) and maximumPoolSize (see
 * {@link #getMaximumPoolSize}).
 *
 * When a new task is submitted in method {@link #execute(Runnable)}, and fewer
 * than corePoolSize threads are running, a new thread is created to handle the
 * request, even if other worker threads are idle. If there are more than
 * corePoolSize but less than maximumPoolSize threads running, a new thread will
 * be created only if the queue is full. By setting corePoolSize and
 * maximumPoolSize the same, you create a fixed-size thread pool. By setting
 * maximumPoolSize to an essentially unbounded value such as {@code
 * Integer.MAX_VALUE}, you allow the pool to accommodate an arbitrary number of
 * concurrent tasks. Most typically, core and maximum pool sizes are set only
 * upon construction, but they may also be changed dynamically using
 * {@link #setCorePoolSize} and {@link
 * #setMaximumPoolSize}. </dd>
 *
 * <dt>On-demand construction</dt>
 *
 * <dd>By default, even core threads are initially created and started only when
 * new tasks arrive, but this can be overridden dynamically using method
 * {@link #prestartCoreThread} or {@link
 * #prestartAllCoreThreads}. You probably want to prestart threads if you
 * construct the pool with a non-empty queue. </dd>
 *
 * <dt>Creating new threads</dt>
 *
 * <dd>New threads are created using a {@link ThreadFactory}. If not otherwise
 * specified, a {@link Executors#defaultThreadFactory} is used, that creates
 * threads to all be in the same {@link
 * ThreadGroup} and with the same {@code NORM_PRIORITY} priority and non-daemon
 * status. By supplying a different ThreadFactory, you can alter the thread's
 * name, thread group, priority, daemon status, etc. If a {@code ThreadFactory}
 * fails to create a thread when asked by returning null from {@code newThread},
 * the executor will continue, but might not be able to execute any tasks.
 * Threads should possess the "modifyThread" {@code RuntimePermission}. If
 * worker threads or other threads using the pool do not possess this
 * permission, service may be degraded: configuration changes may not take
 * effect in a timely manner, and a shutdown pool may remain in a state in which
 * termination is possible but not completed.</dd>
 *
 * <dt>Keep-alive times</dt>
 *
 * <dd>If the pool currently has more than corePoolSize threads, excess threads
 * will be terminated if they have been idle for more than the keepAliveTime
 * (see {@link #getKeepAliveTime(TimeUnit)}). This provides a means of reducing
 * resource consumption when the pool is not being actively used. If the pool
 * becomes more active later, new threads will be constructed. This parameter
 * can also be changed dynamically using method {@link #setKeepAliveTime(long,
 * TimeUnit)}. Using a value of {@code Long.MAX_VALUE} {@link
 * TimeUnit#NANOSECONDS} effectively disables idle threads from ever terminating
 * prior to shut down. By default, the keep-alive policy applies only when there
 * are more than corePoolSize threads. But method
 * {@link #allowCoreThreadTimeOut(boolean)} can be used to apply this time-out
 * policy to core threads as well, so long as the keepAliveTime value is
 * non-zero. </dd>
 *
 * <dt>Queuing</dt>
 *
 * <dd>Any {@link BlockingQueue} may be used to transfer and hold submitted
 * tasks. The use of this queue interacts with pool sizing:
 *
 * <ul>
 *
 * <li> If fewer than corePoolSize threads are running, the Executor always
 * prefers adding a new thread rather than queuing.</li>
 *
 * <li> If corePoolSize or more threads are running, the Executor always prefers
 * queuing a request rather than adding a new thread.</li>
 *
 * <li> If a request cannot be queued, a new thread is created unless this would
 * exceed maximumPoolSize, in which case, the task will be rejected.</li>
 *
 * </ul>
 *
 * There are three general strategies for queuing:
 * <ol>
 *
 * <li> <em> Direct handoffs.</em> A good default choice for a work queue is a
 * {@link SynchronousQueue} that hands off tasks to threads without otherwise
 * holding them. Here, an attempt to queue a task will fail if no threads are
 * immediately available to run it, so a new thread will be constructed. This
 * policy avoids lockups when handling sets of requests that might have internal
 * dependencies. Direct handoffs generally require unbounded maximumPoolSizes to
 * avoid rejection of new submitted tasks. This in turn admits the possibility
 * of unbounded thread growth when commands continue to arrive on average faster
 * than they can be processed.  </li>
 *
 * <li><em> Unbounded queues.</em> Using an unbounded queue (for example a
 * {@link LinkedBlockingQueue} without a predefined capacity) will cause new
 * tasks to wait in the queue when all corePoolSize threads are busy. Thus, no
 * more than corePoolSize threads will ever be created. (And the value of the
 * maximumPoolSize therefore doesn't have any effect.) This may be appropriate
 * when each task is completely independent of others, so tasks cannot affect
 * each others execution; for example, in a web page server. While this style of
 * queuing can be useful in smoothing out transient bursts of requests, it
 * admits the possibility of unbounded work queue growth when commands continue
 * to arrive on average faster than they can be processed.  </li>
 *
 * <li><em>Bounded queues.</em> A bounded queue (for example, an
 * {@link ArrayBlockingQueue}) helps prevent resource exhaustion when used with
 * finite maximumPoolSizes, but can be more difficult to tune and control. Queue
 * sizes and maximum pool sizes may be traded off for each other: Using large
 * queues and small pools minimizes CPU usage, OS resources, and
 * context-switching overhead, but can lead to artificially low throughput. If
 * tasks frequently block (for example if they are I/O bound), a system may be
 * able to schedule time for more threads than you otherwise allow. Use of small
 * queues generally requires larger pool sizes, which keeps CPUs busier but may
 * encounter unacceptable scheduling overhead, which also decreases throughput.
 * </li>
 *
 * </ol>
 *
 * </dd>
 *
 * <dt>Rejected tasks</dt>
 *
 * <dd>New tasks submitted in method {@link #execute(Runnable)} will be
 * <em>rejected</em> when the Executor has been shut down, and also when the
 * Executor uses finite bounds for both maximum threads and work queue capacity,
 * and is saturated. In either case, the {@code execute} method invokes the {@link
 * RejectedExecutionHandler#rejectedExecution(Runnable, ThreadPoolExecutor)}
 * method of its {@link RejectedExecutionHandler}. Four predefined handler
 * policies are provided:
 *
 * <ol>
 *
 * <li> In the default {@link ThreadPoolExecutor.AbortPolicy}, the handler
 * throws a runtime {@link RejectedExecutionException} upon rejection. </li>
 *
 * <li> In {@link ThreadPoolExecutor.CallerRunsPolicy}, the thread that invokes
 * {@code execute} itself runs the task. This provides a simple feedback control
 * mechanism that will slow down the rate that new tasks are submitted. </li>
 *
 * <li> In {@link ThreadPoolExecutor.DiscardPolicy}, a task that cannot be
 * executed is simply dropped.  </li>
 *
 * <li>In {@link ThreadPoolExecutor.DiscardOldestPolicy}, if the executor is not
 * shut down, the task at the head of the work queue is dropped, and then
 * execution is retried (which can fail again, causing this to be repeated.)
 * </li>
 *
 * </ol>
 *
 * It is possible to define and use other kinds of {@link
 * RejectedExecutionHandler} classes. Doing so requires some care especially
 * when policies are designed to work only under particular capacity or queuing
 * policies. </dd>
 *
 * <dt>Hook methods</dt>
 *
 * <dd>This class provides {@code protected} overridable
 * {@link #beforeExecute(Thread, Runnable)} and
 * {@link #afterExecute(Runnable, Throwable)} methods that are called before and
 * after execution of each task. These can be used to manipulate the execution
 * environment; for example, reinitializing ThreadLocals, gathering statistics,
 * or adding log entries. Additionally, method {@link #terminated} can be
 * overridden to perform any special processing that needs to be done once the
 * Executor has fully terminated.
 *
 * <p>
 * If hook or callback methods throw exceptions, internal worker threads may in
 * turn fail and abruptly terminate.</dd>
 *
 * <dt>Queue maintenance</dt>
 *
 * <dd>Method {@link #getQueue()} allows access to the work queue for purposes
 * of monitoring and debugging. Use of this method for any other purpose is
 * strongly discouraged. Two supplied methods, {@link #remove(Runnable)} and
 * {@link #purge} are available to assist in storage reclamation when large
 * numbers of queued tasks become cancelled.</dd>
 *
 * <dt>Finalization</dt>
 *
 * <dd>A pool that is no longer referenced in a program <em>AND</em>
 * has no remaining threads will be {@code shutdown} automatically. If you would
 * like to ensure that unreferenced pools are reclaimed even if users forget to
 * call {@link #shutdown}, then you must arrange that unused threads eventually
 * die, by setting appropriate keep-alive times, using a lower bound of zero
 * core threads and/or setting {@link #allowCoreThreadTimeOut(boolean)}.  </dd>
 *
 * </dl>
 *
 * <p>
 * <b>Extension example</b>. Most extensions of this class override one or more
 * of the protected hook methods. For example, here is a subclass that adds a
 * simple pause/resume feature:
 *
 * <pre> {@code
 * class PausableThreadPoolExecutor extends ThreadPoolExecutor {
 *   private boolean isPaused;
 *   private ReentrantLock pauseLock = new ReentrantLock();
 *   private Condition unpaused = pauseLock.newCondition();
 *
 *   public PausableThreadPoolExecutor(...) { super(...); }
 *
 *   protected void beforeExecute(Thread t, Runnable r) {
 *     super.beforeExecute(t, r);
 *     pauseLock.lock();
 *     try {
 *       while (isPaused) unpaused.await();
 *     } catch (InterruptedException ie) {
 *       t.interrupt();
 *     } finally {
 *       pauseLock.unlock();
 *     }
 *   }
 *
 *   public void pause() {
 *     pauseLock.lock();
 *     try {
 *       isPaused = true;
 *     } finally {
 *       pauseLock.unlock();
 *     }
 *   }
 *
 *   public void resume() {
 *     pauseLock.lock();
 *     try {
 *       isPaused = false;
 *       unpaused.signalAll();
 *     } finally {
 *       pauseLock.unlock();
 *     }
 *   }
 * }}</pre>
 *
 * @author Richeli Vargas
 */
public class ThreadPoolExecutor extends java.util.concurrent.ThreadPoolExecutor {

    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

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
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters and default thread factory and rejected execution handler.It
     * may be more convenient to use one of the {@link Executors} factory
     * methods instead of this general purpose constructor.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if
     * they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the pool
     * @param keepAliveTime when the number of threads is greater than the core,
     * this is the maximum time that excess idle threads will wait for new tasks
     * before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param name the pool name
     * @throws IllegalArgumentException if one of the following holds:<br>
     * {@code corePoolSize < 0}<br> {@code keepAliveTime < 0}<br>
     * {@code maximumPoolSize <= 0}<br> {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue} is null
     */
    public ThreadPoolExecutor(
            final int corePoolSize,
            final int maximumPoolSize,
            final long keepAliveTime,
            final TimeUnit unit,
            final String name) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, null, null, null, name);
    }

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters and default thread factory and rejected execution handler.It
     * may be more convenient to use one of the {@link Executors} factory
     * methods instead of this general purpose constructor.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if
     * they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the pool
     * @param keepAliveTime when the number of threads is greater than the core,
     * this is the maximum time that excess idle threads will wait for new tasks
     * before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are
     * executed. This queue will hold only the {@code Runnable} tasks submitted
     * by the {@code execute} method.
     * @param name the pool name
     * @throws IllegalArgumentException if one of the following holds:<br>
     * {@code corePoolSize < 0}<br> {@code keepAliveTime < 0}<br>
     * {@code maximumPoolSize <= 0}<br> {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue} is null
     */
    public ThreadPoolExecutor(
            final int corePoolSize,
            final int maximumPoolSize,
            final long keepAliveTime,
            final TimeUnit unit,
            final BlockingQueue<Runnable> workQueue,
            final String name) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, null, null, name);
    }

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters and default rejected execution handler.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if
     * they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the pool
     * @param keepAliveTime when the number of threads is greater than the core,
     * this is the maximum time that excess idle threads will wait for new tasks
     * before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are
     * executed. This queue will hold only the {@code Runnable} tasks submitted
     * by the {@code execute} method.
     * @param threadFactory the factory to use when the executor creates a new
     * thread
     * @param name the pool name
     * @throws IllegalArgumentException if one of the following holds:<br>
     * {@code corePoolSize < 0}<br> {@code keepAliveTime < 0}<br>
     * {@code maximumPoolSize <= 0}<br> {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue} or
     * {@code threadFactory} is null
     */
    public ThreadPoolExecutor(
            final int corePoolSize,
            final int maximumPoolSize,
            final long keepAliveTime,
            final TimeUnit unit,
            final BlockingQueue<Runnable> workQueue,
            final ThreadFactory threadFactory,
            final String name) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, null, name);
    }

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters and default thread factory.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if
     * they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the pool
     * @param keepAliveTime when the number of threads is greater than the core,
     * this is the maximum time that excess idle threads will wait for new tasks
     * before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are
     * executed. This queue will hold only the {@code Runnable} tasks submitted
     * by the {@code execute} method.
     * @param handler the handler to use when execution is blocked because the
     * thread bounds and queue capacities are reached
     * @param name the pool name
     * @throws IllegalArgumentException if one of the following holds:<br>
     * {@code corePoolSize < 0}<br> {@code keepAliveTime < 0}<br>
     * {@code maximumPoolSize <= 0}<br> {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue} or {@code handler} is
     * null
     */
    public ThreadPoolExecutor(
            final int corePoolSize,
            final int maximumPoolSize,
            final long keepAliveTime,
            final TimeUnit unit,
            final BlockingQueue<Runnable> workQueue,
            final RejectedExecutionHandler handler,
            final String name) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, null, handler, name);
    }

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if
     * they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the pool
     * @param keepAliveTime when the number of threads is greater than the core,
     * this is the maximum time that excess idle threads will wait for new tasks
     * before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are
     * executed. This queue will hold only the {@code Runnable} tasks submitted
     * by the {@code execute} method.
     * @param threadFactory the factory to use when the executor creates a new
     * thread
     * @param handler the handler to use when execution is blocked because the
     * thread bounds and queue capacities are reached
     * @param name the pool name
     * @throws IllegalArgumentException if one of the following holds:<br>
     * {@code corePoolSize < 0}<br> {@code keepAliveTime < 0}<br>
     * {@code maximumPoolSize <= 0}<br> {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue} or
     * {@code threadFactory} or {@code handler} is null
     */
    public ThreadPoolExecutor(
            final int corePoolSize,
            final int maximumPoolSize,
            final long keepAliveTime,
            final TimeUnit unit,
            final BlockingQueue<Runnable> workQueue,
            final ThreadFactory threadFactory,
            final RejectedExecutionHandler handler,
            final String name) {

        this(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler,
                name,
                POOL_NUMBER.getAndIncrement());
    }

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if
     * they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the pool
     * @param keepAliveTime when the number of threads is greater than the core,
     * this is the maximum time that excess idle threads will wait for new tasks
     * before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are
     * executed. This queue will hold only the {@code Runnable} tasks submitted
     * by the {@code execute} method.
     * @param threadFactory the factory to use when the executor creates a new
     * thread
     * @param handler the handler to use when execution is blocked because the
     * thread bounds and queue capacities are reached
     * @param name the pool name
     * @param number the pool number
     * @throws IllegalArgumentException if one of the following holds:<br>
     * {@code corePoolSize < 0}<br> {@code keepAliveTime < 0}<br>
     * {@code maximumPoolSize <= 0}<br> {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue} or
     * {@code threadFactory} or {@code handler} is null
     */
    protected ThreadPoolExecutor(
            final int corePoolSize,
            final int maximumPoolSize,
            final long keepAliveTime,
            final TimeUnit unit,
            final BlockingQueue<Runnable> workQueue,
            final ThreadFactory threadFactory,
            final RejectedExecutionHandler handler,
            final String name,
            final int number) {

        super(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit == null ? TimeUnit.MILLISECONDS : unit,
                workQueue == null ? new LinkedBlockingQueue<>() : workQueue,
                threadFactory == null ? new DefaultThreadFactory(normalizeName(name, number)) : threadFactory,
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
            return "pool-" + number;
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
                "Shutdowning thread pool [" + name + "]...").start();

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

    public void awaitTermination() {
        int active;
        do {
            ThreadsHelper.sleep(1);
            active = getActiveCount();
        } while (active > 0);
    }
}
