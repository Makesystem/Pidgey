/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.util;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author Richeli Vargas
 * @param <T>
 */
public class Reference<T> implements Serializable {

    private static final long serialVersionUID = -1773824990317337297L;

    private T reference;

    public Reference() {
    }

    public Reference(T reference) {
        this.reference = reference;
    }

    public T get() {
        return reference;
    }

    public void set(final T reference) {
        this.reference = reference;
    }

    public T getAndSet(final T reference) {
        final T temp = this.reference;
        this.reference = reference;
        return temp;
    }

    public T setAndGet(final T reference) {
        this.reference = reference;
        return this.reference;
    }

   /**
    * Update reference if it is equals to toCompare
    * 
    * @param toCompare
    * @param reference
    * @return 
    */ 
    public T compareAndSet(final T toCompare, final T reference) {
        if (Objects.equals(this.reference, toCompare)) {
            this.reference = reference;
        }
        return this.reference;
    }
    
    public T updateAndGet(final Function<T, T> function){
        this.reference = function.apply(this.reference);
        return this.reference;
    }
    
    public T getAndUpdate(final Function<T, T> function){
        final T temp = this.reference;
        this.reference = function.apply(this.reference);
        return temp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.reference);
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
        final Reference<?> other = (Reference<?>) obj;
        if (!Objects.equals(this.reference, other.reference)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reference{" + "reference=" + reference + '}';
    }
}
