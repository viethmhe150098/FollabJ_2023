package com.follabj_be.follabj_be.exception;

import com.follabj_be.follabj_be.errorMessge.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class GroupException extends Exception{
    public GroupException(ErrorMessage errorMessage){
        super(errorMessage.getCode()+":"+errorMessage.getMessage());
    }

}
