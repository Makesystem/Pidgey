/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.monitor;

import com.makesystem.pidgey.console.ConsoleColor;
import com.makesystem.pidgey.console.Console;
import com.makesystem.pidgey.console.ConsoleRow;
import com.makesystem.pidgey.console.ConsoleValue;

/**
 *
 * @author Richeli.vargas
 */
public class RunnableResult {

    private long startAt;
    private long endAt;
    private long duration;
    private RunnableStatus status;
    private Throwable throwable;

    public RunnableResult() {
    }

    public RunnableResult(
            final long startAt, 
            final long endAt, 
            final long duration, 
            final RunnableStatus status, 
            final Throwable throwable) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.duration = duration;
        this.status = status;
        this.throwable = throwable;
    }

    public long getStartAt() {
        return startAt;
    }

    protected void setStartAt(long startAt) {
        this.startAt = startAt;
        this.duration = this.endAt - this.startAt;
    }

    public long getEndAt() {
        return endAt;
    }

    protected void setEndAt(long endAt) {
        this.endAt = endAt;
        this.duration = this.endAt - this.startAt;
    }

    public long getDuration() {
        return duration;
    }

    public RunnableStatus getStatus() {
        return status;
    }

    protected void setStatus(RunnableStatus status) {
        this.status = status;
    }

    public Throwable getError() {
        return throwable;
    }

    protected void setError(Throwable throwable) {
        this.throwable = throwable;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void print() {

        final ConsoleColor statusColor;
        switch (status) {
            case SUCCESS:
                statusColor = ConsoleColor.GREEN;
                break;
            case ERROR:
                statusColor = ConsoleColor.RED;
                break;
            case RUNNING:
                statusColor = ConsoleColor.BLUE;
                break;
            default:
                statusColor = ConsoleColor.BLACK_BOLD;
                break;
        }

        final int startAtColumnWidth = 25;
        final int endAtColumnWidth = 25;
        final int durationColumnWidth = 15;
        final int statusColumnWidth = 15;

        final ConsoleValue startAtValue = new ConsoleValue(startAt, ConsoleValue.Type.DATE_TIME, startAtColumnWidth);
        final ConsoleValue endAtValue = new ConsoleValue(endAt, ConsoleValue.Type.DATE_TIME, endAtColumnWidth);
        final ConsoleValue durationValue = new ConsoleValue(duration, ConsoleValue.Type.MILLIS, durationColumnWidth);
        final ConsoleValue statusValue = new ConsoleValue(status, statusColumnWidth, statusColor);

        final ConsoleValue startAtTitle = new ConsoleValue("Start at", startAtColumnWidth);
        final ConsoleValue endAtTitle = new ConsoleValue("End at", endAtColumnWidth);
        final ConsoleValue durationTitle = new ConsoleValue("Duration", durationColumnWidth);
        final ConsoleValue statusTitle = new ConsoleValue("Status", statusColumnWidth);
        final ConsoleValue errorTitle = new ConsoleValue("Error: ", ConsoleColor.BLACK_BOLD);

        final ConsoleRow titleRow = new ConsoleRow(startAtTitle, endAtTitle, durationTitle, statusTitle);
        titleRow.setColors(ConsoleColor.BLACK_BOLD);

        Console.println(titleRow);
        Console.printDivider();
        Console.println(startAtValue, endAtValue, durationValue, statusValue);
        Console.printDivider();
        if (throwable != null) {
            Console.println(errorTitle);
            throwable.printStackTrace();
            Console.printDivider();
        }
    }
}
