package com.peshkov.lab4.config;

import org.springframework.http.HttpStatusCode;

public abstract class ApplicationException extends Exception {
    private final HttpStatusCode code;
    private final String reason;

    ApplicationException(HttpStatusCode code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public HttpStatusCode getCode() {
        return code;
    }
}