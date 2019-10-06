/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.monitor;

import com.makesystem.pidgey.interfaces.Snippet;
import com.makesystem.pidgey.formatation.TimeFormat;
import com.makesystem.pidgey.lang.StringHelper;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 * @author Richeli.vargas
 */
public class Monitor {

    public static final Monitor MONITOR_JRE = new Monitor(Monitor::jre_console);
    public static final Monitor MONITOR_GWT = new Monitor(Monitor::gwt_console);

    public static final Comparator<MonitorResult> MONITOR_RESULT__COMPARATOR
            = Comparator.comparing(MonitorResult::getDuration);

    private Consumer<String> writer;

    public Monitor() {
    }

    public Monitor(final Consumer<String> writer) {
        this.writer = writer;
    }

    public final void setWriter(final Consumer<String> writer) {
        this.writer = writer;
    }

    public MonitorResult exec(final Snippet runnable) {
        return Monitor.this.exec(StringHelper.EMPTY, runnable);
    }

    public MonitorResult exec(final String title, final Snippet runnable) {
        final MonitorResult result = new MonitorResult(title);
        result.setStartAt(System.currentTimeMillis());
        result.setStatus(MonitorStatus.RUNNING);
        try {
            runnable.exec();
            result.setStatus(MonitorStatus.SUCCESS);
        } catch (Throwable throwable) {
            result.setError(throwable);
            result.setStatus(MonitorStatus.ERROR);
        } finally {
            result.setEndAt(System.currentTimeMillis());
        }
        return result;
    }

    public final Collection<MonitorResult> exec(final Snippet... snippets) {
        return exec(StringHelper.EMPTY, snippets);
    }

    public Collection<MonitorResult> exec(final String title, final Snippet... snippets) {
        final AtomicInteger count = new AtomicInteger(0);
        return Arrays.stream(snippets)
                .map(nippet -> exec(title, nippet).setNum(count.getAndIncrement()))
                .collect(Collectors.toList());
    }

    public Collection<MonitorResult> compare(final Snippet... runnables) {
        final Collection<MonitorResult> results = exec(runnables);
        write(results);
        return results;
    }

    public void write(final Collection<MonitorResult> results) {
        write(results.stream().toArray(MonitorResult[]::new));
    }

    public void write(final MonitorResult... results) {

        if (results == null || results.length == 0 || writer == null) {
            return;
        }

        final String toPrint = Arrays.stream(results).sorted(MONITOR_RESULT__COMPARATOR)
                .map(result
                        -> new StringBuilder("Snippet #")
                        .append(result.getNum())
                        .append(StringHelper.TB)
                        .append(TimeFormat.millis(result.getDuration()))
                        .append(StringHelper.TB).toString())
                .collect(Collectors.joining(StringHelper.LF));

        writer.accept(toPrint);
    }

    private static void jre_console(final String data) {
        System.out.println(data);
    }

    private static native void gwt_console(final String data) /*-{
        console.log(data);
    }-*/;
}
