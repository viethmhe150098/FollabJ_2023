package com.follabj_be.follabj_be.exception;

import com.follabj_be.follabj_be.errorMessge.ErrorMessage;

public class GroupException extends Exception{
    public GroupException(ErrorMessage errorMessage){
        super(errorMessage.getCode()+":"+errorMessage.getMessage());
    }

}
