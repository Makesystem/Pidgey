/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.monitor;

import com.makesystem.pidgey.interfaces.Snippet;
import java.util.Collection;
import java.util.function.Consumer;

/**
 *
 * @author Richeli.vargas
 */
public interface Monitor {

    public static MonitorImpl $ = new MonitorImpl();

    public static void setWriter(final Consumer<String> writer) {
        $.setWriter(writer);
    }

    public static MonitorResult exec(final Snippet snippet) {
        return $.exec(snippet);
    }

    public static MonitorResult exec(final String title, final Snippet snippet) {
        return $.exec(title, snippet);
    }

    public static Collection<MonitorResult> exec(final Snippet... snippets) {
        return $.exec(snippets);
    }

    public static Collection<MonitorResult> exec(final String title, final Snippet... snippets) {
        return $.exec(title, snippets);
    }

    public static Collection<MonitorResult> compare(final Snippet... snippets) {
        return $.compare(snippets);
    }

    public static Collection<MonitorResult> compare(final String title, final Snippet... snippets) {
        return $.compare(title, snippets);
    }

    public static void write(final Collection<MonitorResult> results) {
        $.write(results);
    }

    public static void write(final MonitorResult... results) {
        $.write(results);
    }
}
