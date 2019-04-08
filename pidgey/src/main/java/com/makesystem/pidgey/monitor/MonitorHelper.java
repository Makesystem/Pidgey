/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.monitor;

import com.makesystem.pidgey.console.Console;
import com.makesystem.pidgey.console.ConsoleColor;
import com.makesystem.pidgey.console.ConsoleRow;
import com.makesystem.pidgey.console.ConsoleValue;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Richeli.vargas
 */
public class MonitorHelper {

    public final static RunnableResult execute(final Runnable runnable) {
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

    public final static Collection<RunnableResult> execute(final Runnable... runnables) {
        return Arrays.stream(runnables)
                .map(runnable -> execute(runnable))
                .collect(Collectors.toList());
    }
    
    public final static Collection<RunnableResult> executeAndCompare(final Runnable... runnables) {
        final Collection<RunnableResult> results = execute(runnables);
        compare(results);
        return results;
    }

    public final static void compare(final Collection<RunnableResult> results) {
        compare(results.stream().toArray(RunnableResult[]::new));
    }

    public final static void compare(final RunnableResult... results) {
        
        if(results == null || results.length == 0){
            return;
        }
        
        final ConsoleValue[] titles = new ConsoleValue[results.length];
        final ConsoleValue[] values = new ConsoleValue[results.length];

        long lessValue = results[0].getDuration();
        int lessIndex = 0;
        long biggerValue = results[0].getDuration();
        int biggerIndex = 0;
        
        for (int index = 0; index < results.length; index++) {
            final long duration = results[index].getDuration();
            final ConsoleColor color = results[index].getStatus().equals(RunnableStatus.SUCCESS) ? ConsoleColor.BLACK : ConsoleColor.RED;
            titles[index] = new ConsoleValue("#" + index, 3);
            values[index] = new ConsoleValue(results[index].getDuration(), ConsoleValue.Type.MILLIS, 3, color);
            
            if(duration < lessValue){
                lessValue = duration;
                lessIndex = index;
            }
            
            if(duration > biggerValue){
                biggerValue = duration;
                biggerIndex = index;
            }
        }
        
        values[lessIndex].setColors(ConsoleColor.BLUE);
        values[biggerIndex].setColors(ConsoleColor.CYAN);

        final ConsoleRow header = new ConsoleRow(titles);
        final ConsoleRow value = new ConsoleRow(values);

        Console.printlnEach(header, value);

    }
}
