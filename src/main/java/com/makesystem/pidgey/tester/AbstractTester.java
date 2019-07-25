/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.tester;

import com.makesystem.pidgey.console.ConsoleColor;
import com.makesystem.pidgey.lang.ObjectsHelper;
import com.makesystem.pidgey.monitor.MonitorHelper;
import com.makesystem.pidgey.monitor.RunnableResult;
import com.makesystem.pidgey.monitor.RunnableStatus;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 *
 * @author Richeli.vargas
 */
public abstract class AbstractTester {

    protected interface MethodExecution<V> {

        public V exec() throws Throwable;
    }

    public final void run() {
        preExecution();
        execution();
        posExecution();
    }

    protected abstract void preExecution();

    protected abstract void execution();

    protected abstract void posExecution();

    public final <V> void Assert(final MethodExecution<V> methodExecution, final V expectedValue) {
        final Collection<V> buffer = new LinkedHashSet();
        final RunnableResult result = MonitorHelper.execute(() -> buffer.add(methodExecution.exec()));
        assertResult(buffer.isEmpty() ? null : buffer.iterator().next(), expectedValue, result).print();
        System.gc();
    }

    protected final <V> RunnableResult assertResult(final V valueObtained, final V expectedValue, final RunnableResult result) {

        switch (result.getStatus()) {
            case SUCCESS:

                if (!ObjectsHelper.isEquals(valueObtained, expectedValue)) {                    
                    final StringBuilder error = new StringBuilder();
                    error.append("Value obtained is different of expected value.\n");
                    error.append("Obtained [")
                            .append(ConsoleColor.RED.getColor())
                            .append(String.valueOf(valueObtained))
                            .append(ConsoleColor.RESET.getColor())
                            .append("]");
                    error.append(" != ");
                    error.append("Expected [")
                            .append(ConsoleColor.BLUE.getColor())
                            .append(String.valueOf(expectedValue))
                            .append(ConsoleColor.RESET.getColor())
                            .append("]");

                    return new RunnableResult(
                            result.getStartAt(), 
                            result.getEndAt(), 
                            result.getDuration(), 
                            RunnableStatus.ERROR, 
                            new RuntimeException(error.toString()));
                }

            default:
                return result;
        }
    }
}
