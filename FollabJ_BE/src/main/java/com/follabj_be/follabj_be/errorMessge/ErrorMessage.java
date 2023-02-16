package com.follabj_be.follabj_be.errorMessge;

public enum ErrorMessage {
    PROJECT_DUPLICATE("PRJ01", "This project's name is already exist");
    private String code;
    private String message;
    private ErrorMessage(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
