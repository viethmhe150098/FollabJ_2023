package com.follabj_be.follabj_be.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleRuntimeException {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(RuntimeException ex) {
        Map<String, String> res = new HashMap<>();
        res.put("status", HttpStatus.BAD_REQUEST.toString());
        res.put("message", ex.getMessage());
        return res;
    }
}
