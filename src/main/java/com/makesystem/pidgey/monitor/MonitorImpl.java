/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.monitor;

import com.makesystem.pidgey.console.Console;
import com.makesystem.pidgey.console.ConsoleColor;
import com.makesystem.pidgey.interfaces.Snippet;
import com.makesystem.pidgey.formatation.TimeFormat;
import com.makesystem.pidgey.lang.StringHelper;
import java.io.Serializable;
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
public class MonitorImpl implements Serializable {

    private static final long serialVersionUID = 1434491745300800005L;

    public static final Comparator<MonitorResult> MONITOR_RESULT__COMPARATOR
            = Comparator.comparing(MonitorResult::getDuration);

    private Consumer<String> writer;

    public MonitorImpl() {
        this(Console::log);
    }

    public MonitorImpl(final Consumer<String> writer) {
        this.writer = writer;
    }

    public final void setWriter(final Consumer<String> writer) {
        this.writer = writer;
    }

    public MonitorResult exec(final Snippet snippet) {
        return exec(StringHelper.EMPTY, snippet);
    }

    public MonitorResult exec(final String title, final Snippet snippet) {
        final MonitorResult result = new MonitorResult(title);
        result.setStatus(MonitorStatus.RUNNING);
        try {
            try {
                result.setStartAt(System.currentTimeMillis());
                snippet.exec();
            } finally {
                result.setEndAt(System.currentTimeMillis());
            }
            result.setStatus(MonitorStatus.SUCCESS);
        } catch (Throwable throwable) {
            result.setError(throwable);
            result.setStatus(MonitorStatus.ERROR);
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

    public Collection<MonitorResult> compare(final Snippet... snippets) {
        return compare(StringHelper.EMPTY, snippets);
    }

    public Collection<MonitorResult> compare(final String title, final Snippet... snippets) {
        final Collection<MonitorResult> results = exec(snippets);
        writer.accept("Comparation of Snippets: " + title);
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

        //Snippet #?    00:00:00:000    STATUS  
        final String toPrint = Arrays.stream(results).sorted(MONITOR_RESULT__COMPARATOR)
                .map(result
                        -> new StringBuilder(ConsoleColor.RESET.getColor())
                        .append("Snippet #")
                        .append(result.getNum())
                        .append(StringHelper.TB)
                        .append(TimeFormat.millis(result.getDuration()))
                        .append(StringHelper.TB)
                        .append(result.getStatus().getColor().getColor())
                        .append(result.getStatus()).toString())
                .collect(Collectors.joining(StringHelper.LF));

        writer.accept(toPrint);
    }
}
