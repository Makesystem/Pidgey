/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.monitor;

import com.makesystem.pidgey.console.ConsoleColor;

/**
 *
 * @author Richeli.vargas
 */
public enum MonitorStatus {
    SUCCESS(ConsoleColor.GREEN),
    RUNNING(ConsoleColor.BLUE),
    ERROR(ConsoleColor.RED);

    private final ConsoleColor color;

    private MonitorStatus(final ConsoleColor color) {
        this.color = color;
    }

    public ConsoleColor getColor() {
        return color;
    }

}
