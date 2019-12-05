/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import java.io.Serializable;

/**
 *
 * @author Richeli.vargas
 * @param <T>
 */
public class Range<T> implements Serializable {

    private static final long serialVersionUID = 3987415331765912539L;

    private T start;
    private T end;

    public Range() {
        super();
    }

    public Range(final T start, final T end) {
        super();
        this.start = start;
        this.end = end;
    }

    public T getStart() {
        return start;
    }

    public void setStart(final T start) {
        this.start = start;
    }

    public T getEnd() {
        return end;
    }

    public void setEnd(final T end) {
        this.end = end;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((end == null) ? 0 : end.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        return result;
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
        @SuppressWarnings("rawtypes")
        final Range other = (Range) obj;
        if (end == null) {
            if (other.end != null) {
                return false;
            }
        } else if (!end.equals(other.end)) {
            return false;
        }
        if (start == null) {
            if (other.start != null) {
                return false;
            }
        } else if (!start.equals(other.start)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Range [start=" + start + ", end=" + end + "]";
    }

}
