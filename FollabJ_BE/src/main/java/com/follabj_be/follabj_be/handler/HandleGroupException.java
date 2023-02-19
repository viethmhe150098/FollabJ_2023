package com.follabj_be.follabj_be.handler;

import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.exception.GroupPermissionException;
import com.follabj_be.follabj_be.exception.NoTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleGroupException {
    @ExceptionHandler(GroupPermissionException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Map<String, String> handleException(GroupPermissionException ex, WebRequest request){
        Map<String, String> res = new HashMap<>();
        res.put("status", HttpStatus.FORBIDDEN.toString());
        res.put("message", ex.getMessage());
        return res;
    }

    @ExceptionHandler(GroupException.class)
    @ResponseStatus(value =  HttpStatus.CONFLICT)
    public Map<String, String> handleGroupException(GroupException ex, WebRequest request){
        Map<String, String> res = new HashMap<>();
        res.put("message", ex.getLocalizedMessage());
        return res;
    }


}
