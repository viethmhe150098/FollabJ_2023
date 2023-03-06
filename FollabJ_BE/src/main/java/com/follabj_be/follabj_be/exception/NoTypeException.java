package com.follabj_be.follabj_be.exception;

import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;

public class NoTypeException extends Exception {
    public NoTypeException(CustomErrorMessage customErrorMessage) {
        super(customErrorMessage.getCode() + ":" + customErrorMessage.getMessage());
    }
}
