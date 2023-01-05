package com.peshkov.lab4.integration;

import com.peshkov.lab4.config.IntegrationException;
import com.peshkov.lab4.config.MathFunction;
import com.peshkov.lab4.config.ResultNotFoundException;
import com.peshkov.lab4.dto.IntegrationRequest;
import com.peshkov.lab4.model.IntegrationResult;


public interface IntegrationService {
    IntegrationResult integrate(MathFunction function, IntegrationRequest request, String lang) throws IntegrationException;

    IntegrationResult load(int id, String lang) throws ResultNotFoundException;
}
