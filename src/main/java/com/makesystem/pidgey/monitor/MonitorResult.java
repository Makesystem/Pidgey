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
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Richeli.vargas
 */
public class MonitorResult {

    private final String id = UUID.randomUUID().toString();
    private int num = 0;
    private String title;
    private long startAt;
    private long endAt;
    private long duration;
    private MonitorStatus status;
    private Throwable throwable;

    public MonitorResult() {
    }

    public MonitorResult(String title) {
        this.title = title;
    }

    public MonitorResult(
            final String title,
            final long startAt,
            final long endAt,
            final long duration,
            final MonitorStatus status,
            final Throwable throwable) {
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.duration = duration;
        this.status = status;
        this.throwable = throwable;
    }

    public String getId() {
        return id;
    }

    public int getNum() {
        return num;
    }

    public MonitorResult setNum(final int num) {
        this.num = num;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MonitorResult setTitle(String title) {
        this.title = title;
        return this;
    }

    public long getStartAt() {
        return startAt;
    }

    protected MonitorResult setStartAt(long startAt) {
        this.startAt = startAt;
        this.duration = this.endAt - this.startAt;
        return this;
    }

    public long getEndAt() {
        return endAt;
    }

    protected MonitorResult setEndAt(long endAt) {
        this.endAt = endAt;
        this.duration = this.endAt - this.startAt;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public MonitorStatus getStatus() {
        return status;
    }

    protected MonitorResult setStatus(MonitorStatus status) {
        this.status = status;
        return this;
    }

    public Throwable getError() {
        return throwable;
    }

    protected MonitorResult setError(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MonitorResult other = (MonitorResult) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
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

        Console.println(title, ConsoleColor.BLACK);
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
