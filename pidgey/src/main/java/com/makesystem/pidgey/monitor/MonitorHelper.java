/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.monitor;

/**
 *
 * @author Richeli.vargas
 */
public class MonitorHelper {
    
    public final static long getTimeSpent(final Runnable runnable){
        
        final long start = System.currentTimeMillis();
        
        try {
            runnable.run();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        
        final long end = System.currentTimeMillis();
        return end - start;
    }
    
    public static RunnableResult execute(final Runnable runnable){    
        
        final RunnableResult result = new RunnableResult();
        result.setStartAt(System.currentTimeMillis());
        result.setStatus(RunnableStatus.RUNNING);
        
        try {
            runnable.run();
            result.setStatus(RunnableStatus.SUCCESS);
        } catch (Throwable throwable) {
            result.setError(throwable);
            result.setStatus(RunnableStatus.ERROR);
        } finally {
            result.setEndAt(System.currentTimeMillis());
        }
        
        return result;
    }
}
