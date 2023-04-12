package com.follabj_be.follabj_be.handler;

import com.follabj_be.follabj_be.exception.GroupPermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleMethodArgumentException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> res = new HashMap<>();
        res.put("status", HttpStatus.FORBIDDEN.toString());
        res.put("message", ex.getMessage());
        return res;
    }
}
