package com.jenga.jobposter.bridge.models;


public enum Response {

    SUCCESS(0, "Success"),
    MALICIOUS_OPERATION(100, "MALICIOUS OPERATION! HOW DID YOU GET HERE? contact me for PAYMENT"),
    USER_NOT_FOUND(1, "User not found"),
    ACCOUNT_BLOCKED(2, "Account is blocked"),
    TITLE_EXISTS(4, "Job Title Already exists"),
    BAD_CREDS(5, "Invalid API password"),
    NOT_FOUND(3, "{1} not found by the supplied id/code");

    private Status status;

    Response(final int code, final String message) {
        status = new Status(code, message);
    }

    public Status status() {
        return status;
    }
}
