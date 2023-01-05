package com.peshkov.lab4.dao;

import com.peshkov.lab4.config.ResultNotFoundException;
import com.peshkov.lab4.model.IntegrationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

@Service
public final class RepositoryImpl implements Repository {

    private int nextId;
    private final FileProvider provider;

    @Autowired
    public RepositoryImpl(FileProvider provider) {
        this.provider = provider;
        File storageFile = provider.findOrProvide("storage/.repo");
        nextId = Integer.parseInt(readAsMapping(storageFile).getOrDefault("nextId", "1"));
    }

    @Override
    public IntegrationResult get(int id, String language) throws ResultNotFoundException {
        File file;

        try {
            file = provider.find("storage/result_" + id + ".txt");
        } catch (FileNotFoundException exception) {
            throw new ResultNotFoundException(
                HttpStatus.NOT_FOUND,
                language.equalsIgnoreCase("ua")
                    ? "Результат з ідентифікатором " + id + " не знайдено"
                    : "Result with id=" + id + " not found"
            );
        }
        return readResultFromFile(file);
    }

    @Override
    public IntegrationResult save(IntegrationResult result) {
        result.setId(idIncrement());
        writeResultToFile(result);
        return result;
    }

    private int idIncrement() {
        nextId += 1;
        try (var printer = new PrintStream(provider.provide("storage/.repo", true))) {
            printer.println("nextId=" + nextId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return nextId - 1;
    }

    private IntegrationResult readResultFromFile(File file) {
        HashMap<String, String> mapping = readAsMapping(file);
        return new IntegrationResult(
            Integer.parseInt(mapping.get("id")),
            Double.parseDouble(mapping.get("value")),
            Double.parseDouble(mapping.get("lowerBound")),
            Double.parseDouble(mapping.get("upperBound")),
            Double.parseDouble(mapping.get("step")),
            mapping.get("function")
        );
    }

    private void writeResultToFile(IntegrationResult result) {
        var file = provider.provide("storage/result_" + result.getId() + ".txt");
        try (var stream = new PrintStream(file)) {
            stream.println("id=" + result.getId());
            stream.println("value=" + result.getResult());
            stream.println("lowerBound=" + result.getLowerBound());
            stream.println("upperBound=" + result.getUpperBound());
            stream.println("step=" + result.getStep());
            stream.println("function=" + result.getFunction());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HashMap<String, String> readAsMapping(File file) {
        return new HashMap<>() {{
            try (var scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    var line = scanner.nextLine().split("=");
                    put(line[0], line[1]);
                }
            } catch (FileNotFoundException exception) {
                throw new RuntimeException(exception);
            }
        }};
    }
}
