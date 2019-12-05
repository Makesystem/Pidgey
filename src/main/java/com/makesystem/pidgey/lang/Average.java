/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author Richeli.vargas
 * @param <I>
 */
public class Average<I> implements Serializable {

    private static final long serialVersionUID = 7008295735272821328L;

    private I identifier;
    private final AtomicLong total = new AtomicLong(0);
    private final AtomicInteger count = new AtomicInteger(0);

    public Average() {
    }

    public Average(I identifier) {
        this.identifier = identifier;
    }

    public I getIdentifier() {
        return identifier;
    }

    public void setIdentifier(I identifier) {
        this.identifier = identifier;
    }

    public long getTotal() {
        return total.get();
    }

    public void setTotal(final long total) {
        this.total.set(total);
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(final int count) {
        this.count.set(count);
    }

    public void increase(final long value) {
        total.addAndGet(value);
        count.incrementAndGet();
    }

    public void decrement(final long value) {
        total.addAndGet(value * -1);
        count.decrementAndGet();
    }

    @JsonIgnore
    public double getAverage() {
        return NumberHelper.divide(total.get(), count.get());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.identifier);
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
        final Average<?> other = (Average<?>) obj;
        if (!Objects.equals(this.identifier, other.identifier)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Average{" + "identifier=" + identifier + ", total=" + total + ", count=" + count + ", average=" + getAverage() + '}';
    }
}
