package com.peshkov.lab4.dto;

import java.util.Objects;

public record IntegrationRequest(double lowerBound, double upperBound, double step) {

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof IntegrationRequest request)) return false;
        return (request.lowerBound == lowerBound)
            && (request.upperBound == upperBound)
            && (request.step == step);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerBound, upperBound, step);
    }
}
