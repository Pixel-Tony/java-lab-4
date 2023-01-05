package com.peshkov.lab4.config;

import org.springframework.http.HttpStatusCode;

public final class ResultNotFoundException extends ApplicationException {
    public ResultNotFoundException(HttpStatusCode code, String reason) {
        super(code, reason);
    }
}
