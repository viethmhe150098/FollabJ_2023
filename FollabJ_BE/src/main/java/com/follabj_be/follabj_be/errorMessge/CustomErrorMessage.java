package com.follabj_be.follabj_be.errorMessge;

public class CustomErrorMessage {
    public static final CustomErrorMessage PRJ01 = new CustomErrorMessage("FL01", "PROJECT'S NAME DUPLICATE");
    public static final CustomErrorMessage TYPE01 = new CustomErrorMessage("FL02", "TYPE NOT FOUND");
    public static final CustomErrorMessage NO_PERMISSION = new CustomErrorMessage("FL03", "YOU DON'T HAVE PERMISSION");

    public static final CustomErrorMessage WRONG_CREDENTIAL = new CustomErrorMessage("FL04", "WRONG_CREDENTIAL");
    public static final CustomErrorMessage WRONG_PASSWORD = new CustomErrorMessage("FL05", "PASSWORD MUST MATCHES OLD PASSWORD");
    private final String code;
    private final String message;

    private CustomErrorMessage(String code, String message) {
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
