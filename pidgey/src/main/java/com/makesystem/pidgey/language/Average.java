/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.language;

import com.makesystem.pidgey.lang.MathHelper;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Richeli.vargas
 * @param <I>
 */
public class Average<I> implements Serializable {

    private final I identifier;
    private long total = 0;
    private int count = 0;

    public Average(I identifier) {
        this.identifier = identifier;
    }

    public I getIdentifier() {
        return identifier;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public void increase(final long value) {
        total += value;
        count++;
    }

    public void decrement(final long value) {
        total -= value;
        count--;
    }

    public double getAverage() {
        return MathHelper.divide(total, count);
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
        return "Average{" + "identifier=" + identifier + ", total=" + total + ", count=" + count +  ", average=" + getAverage() + '}';
    }    
}
