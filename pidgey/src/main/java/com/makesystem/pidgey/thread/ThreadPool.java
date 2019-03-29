/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.thread;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Richeli.vargas
 */
public class ThreadPool extends AbstractThreadPool {

    private final Collection executing = new LinkedList<>();

    @Override
    protected ExecutorService newInstance(final int nThreads) {
        return Executors.newFixedThreadPool(nThreads);
    }

    @Override
    public Future<?> execute(final Runnable runnable) {
        return submit(() -> {
            executing.add(runnable);
            try {
                runnable.run();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            executing.remove(runnable);
            if (executing.isEmpty()) {
                shutdown();
            }
        });
    }
}
