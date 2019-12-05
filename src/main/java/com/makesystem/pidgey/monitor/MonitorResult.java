/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.monitor;

import com.makesystem.pidgey.console.Console;
import com.makesystem.pidgey.console.ConsoleColor;
import com.makesystem.pidgey.lang.StringHelper;
import com.makesystem.pidgey.lang.ThrowableHelper;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Richeli.vargas
 */
public class MonitorResult implements Serializable {

    private static final long serialVersionUID = -196617577158971147L;

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

    public MonitorResult(final String title) {
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

    public MonitorResult setTitle(final String title) {
        this.title = title;
        return this;
    }

    public long getStartAt() {
        return startAt;
    }

    protected MonitorResult setStartAt(final long startAt) {
        this.startAt = startAt;
        this.duration = this.endAt - this.startAt;
        return this;
    }

    public long getEndAt() {
        return endAt;
    }

    protected MonitorResult setEndAt(final long endAt) {
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

    protected MonitorResult setStatus(final MonitorStatus status) {
        this.status = status;
        return this;
    }

    public Throwable getError() {
        return throwable;
    }

    protected MonitorResult setError(final Throwable throwable) {
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

        // Read first row only
        final String error = ThrowableHelper.toString(throwable).split("\n")[0].replace("\r", "");

        final String format = "{dt}  \t{dt}  \t{ms}\t{cc}{s}\t{s}";
        final Object[][] values = {
            {"{ig}Start at", "{ig}End at", "{ig}Duration", "{ig}", "{ig}Status", ""},
            {startAt, endAt, duration, status.getColor(), status, error} // <-- It has 70 chars more 3 \t
        };

        Console.log("{cc}", ConsoleColor.RESET);
        if (!StringHelper.isBlank(this.title)) {
            final int charPerRow = 70;
            Console.log("{cc}{cc}{s}{s}\t\t\t",
                    ConsoleColor.CYAN_BACKGROUND,
                    ConsoleColor.BLACK,
                    StringHelper.SPACE,
                    StringHelper.appendAtEnd(
                            this.title,
                            StringHelper.SPACE,
                            charPerRow));
        }
        Console.log(format, values);
    }

}
