package com.peshkov.lab4.dao;

import com.peshkov.lab4.config.ResultNotFoundException;
import com.peshkov.lab4.model.IntegrationResult;

public interface Repository {

    IntegrationResult get(int id, String language) throws ResultNotFoundException;

    IntegrationResult save(IntegrationResult result);

}
