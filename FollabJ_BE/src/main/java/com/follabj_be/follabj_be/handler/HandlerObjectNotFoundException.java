package com.follabj_be.follabj_be.handler;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandlerObjectNotFoundException {
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleException(ObjectNotFoundException ex, WebRequest request){
        Map<String, String> res = new HashMap<>();
        res.put("status", "500");
        res.put("message", ex.getMessage());
        return res;
    }
}
