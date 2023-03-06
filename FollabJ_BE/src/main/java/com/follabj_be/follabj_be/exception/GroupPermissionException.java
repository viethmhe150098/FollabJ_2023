package com.follabj_be.follabj_be.exception;

import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;

public class GroupPermissionException extends IllegalArgumentException {
    public GroupPermissionException(CustomErrorMessage customErrorMessage) {
        super(customErrorMessage.getCode() + ":" + customErrorMessage.getMessage());
    }
}
