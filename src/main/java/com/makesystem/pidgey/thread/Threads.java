package com.makesystem.pidgey.thread;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Threads {

    // Determine the time to wait for thread is finished
    private int timeoutShudownThread = 15;
    // Determine the number of cores on the device
    private final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    // Keep a only one intânce from ExecutorService
    private ExecutorService shortExecutorService;
    // Keep a only one intânce from ExecutorService
    private ExecutorService longExecutorService;
    // Keep a only one intânce from scheduledExecutorService
    private ScheduledExecutorService scheduledExecutorService;
    // Number of threads that the pooll will be configured
    private int qtdeShortPoollThreads;
    // Number of threads that the pooll will be configured
    private int qtdeLongPoolThreads;
    // Number of threads that the pooll will be configured
    private int qtdeSchedulePoolThreads;

    private Threads() {
    }

    public static Threads newInstance() {
        return new Threads();
    }

    public int getTimeoutShudownThread() {
        return timeoutShudownThread;
    }

    public void setTimeoutShudownThread(int timeoutShudownThread) {
        this.timeoutShudownThread = timeoutShudownThread;
    }

    /**
     * Configura o pool de scheduleExecutor se ele estiver nulo.
     */
    private void configureScheduleExecutor() {
        if (scheduledExecutorService == null) {
            final int qtde = qtdeSchedulePoolThreads == 0 ? NUMBER_OF_CORES * 2 : qtdeSchedulePoolThreads;
            scheduledExecutorService = Executors.newScheduledThreadPool(qtde);
        }
    }

    /**
     * Termina todos os poolls
     *
     * @param isRunInParallel
     */
    public void finishAllPoolls(final boolean isRunInParallel) {
        if (isRunInParallel) {
            new Thread(() -> {
                finishAllPoollsServices();
            }).start();
        } else {
            finishAllPoollsServices();
        }
    }

    /**
     * Termina todos os poolls
     */
    private void finishAllPoollsServices() {
        try {
            finishShortPoollService(false);
        } catch (Throwable ex) {
            // nada a fazer
        }
        try {
            finishLongPoollService(false);
        } catch (Throwable ex) {
            // nada a fazer
        }
        try {
            finishSchedulePoollService(false);
        } catch (Throwable ex) {
            // nada a fazer
        }
    }

    /**
     * Encerra o pooll
     *
     * @param isRunInParallel
     * @return
     */
    public boolean finishShortPoollService(final boolean isRunInParallel) {
        if (shortExecutorService != null) {
            if (isRunInParallel) {
                new Thread(() -> {
                    if (finish(shortExecutorService)) {
                        shortExecutorService = null;
                    }
                }).start();
            } else {
                if (finish(shortExecutorService)) {
                    shortExecutorService = null;
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Encerra o pooll
     *
     * @param isRunInParallel
     * @return
     */
    public boolean finishLongPoollService(final boolean isRunInParallel) {
        if (longExecutorService != null) {
            if (isRunInParallel) {
                new Thread(() -> {
                    if (finish(longExecutorService)) {
                        longExecutorService = null;
                    }
                }).start();
            } else {
                if (finish(longExecutorService)) {
                    longExecutorService = null;
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Encerra o pooll
     *
     * @param isRunInParallel
     * @return
     */
    public boolean finishSchedulePoollService(final boolean isRunInParallel) {
        if (scheduledExecutorService != null) {
            if (isRunInParallel) {
                new Thread(() -> {
                    if (finish(scheduledExecutorService)) {
                        scheduledExecutorService = null;
                    }
                }).start();
            } else {
                if (finish(scheduledExecutorService)) {
                    scheduledExecutorService = null;
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Se o pooll ja estiver configurado, entao, ele terminara o pooll e, caso
     * ele for parado com sucesso, ele reconfigura o pool.
     *
     * @param qtdeShortPoollThreads
     * @return true se foi configurado com sucesso e false, caso contrário
     */
    public boolean setQtdeShortPoollThreads(final int qtdeShortPoollThreads) {
        this.qtdeShortPoollThreads = qtdeShortPoollThreads;
        if (shortExecutorService != null) {
            if (finish(shortExecutorService)) {
                shortExecutorService = null;
                getLongServicesExecutor();
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * Se o pooll ja estiver configurado, entao, ele terminara o pooll e, caso
     * ele for parado com sucesso, ele reconfigura o pool.
     *
     * @param qtdeLongPoolThreads
     * @return true se foi configurado com sucesso e false, caso contrario
     */
    public boolean setQtdeLongPoolThreads(final int qtdeLongPoolThreads) {
        this.qtdeLongPoolThreads = qtdeLongPoolThreads;
        if (longExecutorService != null) {
            if (finish(longExecutorService)) {
                longExecutorService = null;
                getLongServicesExecutor();
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * Se o pooll ja estiver configurado, entao, ele terminara o pooll e, caso
     * ele for parado com sucesso, ele reconfigura o pool. Sera preciso
     * reagendar todos os schedule previamente agendados.
     *
     * @param qtdeSchedulePoolThreads
     * @return true se foi configurado com sucesso e false, caso contrário
     */
    public boolean setQtdeSchedulePoolThreads(final int qtdeSchedulePoolThreads) {
        this.qtdeSchedulePoolThreads = qtdeSchedulePoolThreads;
        if (scheduledExecutorService != null) {
            if (finish(scheduledExecutorService)) {
                scheduledExecutorService = null;
                configureScheduleExecutor();
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * Verifica se o pooll esta configurado
     *
     * @return
     */
    public boolean isShortServicesPoollConfigured() {
        return shortExecutorService != null;
    }

    /**
     * Verifica se o pooll esta configurado
     *
     * @return
     */
    public boolean isLongServicesPoollConfigured() {
        return longExecutorService != null;
    }

    /**
     * Verifica se o pooll esta configurado
     *
     * @return
     */
    public boolean isScheduleServicesPoollConfigured() {
        return scheduledExecutorService != null;
    }

    /**
     * Este pool so sera configurado caso for chamado
     *
     * @return the executor to run threads
     */
    public ExecutorService getShortServicesExecutor() {
        if (shortExecutorService == null) {
            final int qtde = qtdeShortPoollThreads == 0 ? NUMBER_OF_CORES * 2 : qtdeShortPoollThreads;
            shortExecutorService = Executors.newFixedThreadPool(qtde);
        }
        return shortExecutorService;
    }

    /**
     * Este pool so sera configurado caso for chamado
     *
     * @return the executor to run threads
     */
    public ExecutorService getLongServicesExecutor() {
        if (longExecutorService == null) {
            final int qtde = qtdeLongPoolThreads == 0 ? NUMBER_OF_CORES * 2 : qtdeLongPoolThreads;
            longExecutorService = Executors.newFixedThreadPool(qtde);
        }
        return longExecutorService;
    }

    /**
     * Este pool so sera configurado caso for chamado
     *
     * @param runnable
     * @param inicialDelay
     * @param period
     * @param timeUnit
     * @return
     */
    public ScheduledFuture getScheduleExecutor(final Runnable runnable, final long inicialDelay, final long period, final TimeUnit timeUnit) {
        configureScheduleExecutor();
        return scheduledExecutorService.scheduleAtFixedRate(runnable, inicialDelay, period, timeUnit);
    }

    /**
     *
     * @param runnable
     * @param date
     * @param periodInMilli
     * @return
     */
    public ScheduledFuture getScheduleExecutor(final Runnable runnable, final LocalDateTime date, long periodInMilli) {

        LocalDateTime startTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime now = LocalDateTime.now();

        long futureLdtInMilli = startTime.until(date, ChronoUnit.MILLIS);
        long currentLdtInMilli = startTime.until(now, ChronoUnit.MILLIS);

        long initialDelay = futureLdtInMilli - currentLdtInMilli;

        return getScheduleExecutor(runnable, initialDelay, periodInMilli, TimeUnit.MILLISECONDS);
    }

    /**
     * Este processo reaproveita threads que ainda estao ativas mas nao estao
     * rodando. Caso nao encontra nenhuma thread ativa, e que nao esteja
     * rodando, ele cria uma thread nova
     *
     * @return
     */
    public ExecutorService getCachedThreadExecutor() {
        return Executors.newCachedThreadPool();
    }

    /**
     * Termina o pool em execucao
     *
     * @param pool
     * @param isRunInParallel
     */
    public void finish(final ExecutorService pool, final boolean isRunInParallel) {
        if (isRunInParallel) {
            new Thread(() -> {
                finish(pool);
            }).start();
        } else {
            finish(pool);
        }
    }

    /**
     * Termina o pool em execucao.
     *
     * @param pool
     * @param isRunInParallel
     * @param timeToWaitShutdown
     * @param timeUnit
     */
    public void finish(final ExecutorService pool, final boolean isRunInParallel,
            final int timeToWaitShutdown, final TimeUnit timeUnit) {

        if (isRunInParallel) {
            new Thread(() -> {
                finish(pool, timeToWaitShutdown, timeUnit);
            }).start();
        } else {
            finish(pool, timeToWaitShutdown, timeUnit);
        }
    }

    /**
     * Termina o pool em execucao.
     *
     * @param future
     * @param mayInterruptIfRunning
     */
    public void finish(final Future future, final boolean mayInterruptIfRunning) {
        if (future != null) {
            future.cancel(mayInterruptIfRunning);
        }
    }

    /**
     * Termina o pool em execucao.
     *
     * @param pool
     */
    private boolean finish(final ExecutorService pool) {
        return finish(pool, timeoutShudownThread, TimeUnit.SECONDS);
    }

    /**
     * Termina o pool em execucao.
     *
     * @param pool
     * @param timeToWaitShutdown
     * @param timeUnit
     * @return
     */
    private boolean finish(final ExecutorService pool, final int timeToWaitShutdown, final TimeUnit timeUnit) {
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

    /**
     * Espera o tempo solicitado
     *
     * @param timeInMillis
     */
    public void sleep(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (final InterruptedException ex) {
        }
    }

    /**
     * Espera o tempo solicitado
     *
     * @param timeInMillis
     * @param thowErroWhenInterruped
     * @throws InterruptedException
     */
    public void sleep(long timeInMillis, boolean thowErroWhenInterruped) throws InterruptedException {
        try {
            Thread.sleep(timeInMillis);
        } catch (final InterruptedException ex) {
            if (thowErroWhenInterruped) {
                throw ex;
            }
        }
    }

}
