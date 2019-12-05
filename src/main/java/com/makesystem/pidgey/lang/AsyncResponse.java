/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import java.io.Serializable;

/**
 *
 * @author Richeli Vargas
 * @param <T>
 */
public class AsyncResponse<T> implements Serializable {

    private static final long serialVersionUID = -996332874538938687L;

    protected enum Status {
        UNDEFINED, SUCCESS, FAILURE
    };

    private Status status = Status.UNDEFINED;
    private T success;
    private Throwable failure;

    public Status getStatus() {
        return status;
    }

    public T getSuccess() {
        return success;
    }

    public void setSuccess(final T success) {
        this.success = success;
        status = Status.SUCCESS;
    }

    public Throwable getFailure() {
        return failure;
    }

    public void setFailure(final Throwable failure) {
        this.failure = failure;
        status = Status.FAILURE;
    }
}
