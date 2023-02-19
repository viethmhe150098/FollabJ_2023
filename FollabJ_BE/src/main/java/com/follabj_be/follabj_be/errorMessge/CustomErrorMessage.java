package com.follabj_be.follabj_be.errorMessge;

public class CustomErrorMessage {
    public static final CustomErrorMessage PRJ01 = new CustomErrorMessage("PRJ01", "PROJECT'S NAME DUPLICATE");
    public static final CustomErrorMessage TYPE01 = new CustomErrorMessage("TYPE01", "TYPE NOT FOUND");
    private final String code;
    private final String message;
    private CustomErrorMessage(String code, String message){
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
