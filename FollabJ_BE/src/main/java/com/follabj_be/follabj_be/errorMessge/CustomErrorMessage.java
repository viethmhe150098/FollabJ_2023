package com.follabj_be.follabj_be.errorMessge;

public class CustomErrorMessage {
    public static final CustomErrorMessage PRJ01 = new CustomErrorMessage("FL01", "Project's name duplicate");
    public static final CustomErrorMessage TYPE01 = new CustomErrorMessage("FL02", "Type not found");
    public static final CustomErrorMessage NO_PERMISSION = new CustomErrorMessage("FL03", "You don't have permission");

    public static final CustomErrorMessage WRONG_CREDENTIAL = new CustomErrorMessage("FL04", "Wrong Credential");
    public static final CustomErrorMessage WRONG_PASSWORD = new CustomErrorMessage("FL05", "Password must matches Old password");
    public static final CustomErrorMessage NOT_TEAMMEMBER = new CustomErrorMessage("FL06", "Member not in Group");
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
