package com.peshkov.lab4.model;

import com.peshkov.lab4.dto.IntegrationRequest;

import java.util.Objects;

public class IntegrationResult {

    private Integer id;

    private final double result;
    private final double lowerBound;
    private final double upperBound;
    private final double step;

    private final String function;

    public IntegrationResult(Integer id, double result, IntegrationRequest request, String function) {
        this(id, result, request.lowerBound(), request.upperBound(), request.step(), function);
    }

    public IntegrationResult(Integer id, double result, double lowerBound, double upperBound, double step, String function) {
        this.id = id;
        this.result = result;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.step = step;
        this.function = function;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getResult() {
        return result;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public double getStep() {
        return step;
    }

    public String getFunction() {
        return function;
    }

    public String getResultRepresentation(String language) {
        return (language.equalsIgnoreCase("ua")
            ? "Проінтегровано функцію %s у межах з %s до %s із кроком %s, результат - %s"
            : "Integrated function %s in bounds from %s to %s with step %s, result is %s"
        ).formatted(function, lowerBound, upperBound, step, result);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof IntegrationResult request)) return false;
        return (request.lowerBound == lowerBound)
            && (request.upperBound == upperBound)
            && (request.step == step);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerBound, upperBound, step);
    }
}
