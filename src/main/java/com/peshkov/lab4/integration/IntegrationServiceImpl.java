package com.peshkov.lab4.integration;

import com.peshkov.lab4.config.IntegrationException;
import com.peshkov.lab4.config.MathFunction;
import com.peshkov.lab4.config.ResultNotFoundException;
import com.peshkov.lab4.dao.Repository;
import com.peshkov.lab4.dto.IntegrationRequest;
import com.peshkov.lab4.model.IntegrationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class IntegrationServiceImpl implements IntegrationService {

    private final Repository repository;

    @Autowired
    public IntegrationServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public IntegrationResult load(int id, String language) throws ResultNotFoundException {
        return repository.get(id, language);
    }

    @Override
    public IntegrationResult integrate(MathFunction function, IntegrationRequest request, String lang)
        throws IntegrationException {
        double step = request.step();
        double upperBound = request.upperBound();
        double lowerBound = request.lowerBound();

        if (upperBound < lowerBound) {
            throw new IntegrationException(
                HttpStatus.BAD_REQUEST,
                lang.equalsIgnoreCase("ua")
                    ? "Верхня межа повинна перевищувати нижню"
                    : "Upper bound should be bigger than a lower bound"
            );
        }

        if (step > upperBound - lowerBound) {
            throw new IntegrationException(
                HttpStatus.BAD_REQUEST,
                lang.equalsIgnoreCase("ua")
                    ? "Крок інтегрування не повинен перевищувати довжину проміжку"
                    :"The step should be smaller than the interval"
            );
        }

        double sum = 0;
        double leftSideValue = function.apply(lowerBound);
        double rightSideValue;
        for (double i = lowerBound + step; Double.compare(i, upperBound) <= 0; i += step) {
            rightSideValue = function.apply(i);
            sum += (leftSideValue + rightSideValue) / 2 * step;
            leftSideValue = rightSideValue;
        }

        return repository.save(new IntegrationResult(null, sum, request, function.representation()));
    }
}
