package com.follabj_be.follabj_be.exception;

import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;

public class GroupException extends Exception {
    public GroupException(CustomErrorMessage customErrorMessage) {
        super(customErrorMessage.getCode() + ":" + customErrorMessage.getMessage());
    }

}
