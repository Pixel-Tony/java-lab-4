package com.peshkov.lab4.config;

import org.springframework.http.HttpStatusCode;

public final class IntegrationException extends ApplicationException {
    public IntegrationException(HttpStatusCode code, String reason) {
        super(code, reason);
    }
}
