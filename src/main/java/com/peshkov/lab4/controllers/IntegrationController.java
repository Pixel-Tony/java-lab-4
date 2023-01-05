package com.peshkov.lab4.controllers;

import com.peshkov.lab4.config.ApplicationException;
import com.peshkov.lab4.config.MathFunction;
import com.peshkov.lab4.dto.IntegrationRequest;
import com.peshkov.lab4.integration.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;

@RestController
public class IntegrationController {

    private final IntegrationService service;
    private final MathFunction targetFunction;


    @Autowired
    IntegrationController(MathFunction targetFunction, IntegrationService service) {
        this.service = service;
        this.targetFunction = targetFunction;
    }

    @GetMapping(value = {"find/{id}", "/find/{id}/{lang}"}, produces = "application/json")
    public ResponseEntity<?> find(@PathVariable Integer id,
                                  @PathVariable(required = false, name = "lang") String lang) {

        final String presentationLanguage = (lang != null) ? lang : "en";
        try {

            var result = service.load(id, presentationLanguage);
            return ResponseEntity.ok(new HashMap<>() {{
                put("description", result.getResultRepresentation(presentationLanguage));
            }});

        } catch (ApplicationException exception) {

            return ResponseEntity.status(exception.getCode())
                .body(new HashMap<>() {{
                    put("reason", exception.getReason());
                }});

        }
    }

    @PostMapping(value = {"/integrate", "/integrate/{lang}"}, produces = "application/json")
    public ResponseEntity<?> integrate(IntegrationRequest request,
                                       @PathVariable(required = false, name = "lang") String lang) {
        final String presentationLanguage = (lang != null) ? lang : "en";
        try {

            var result = service.integrate(targetFunction, request, presentationLanguage);
            return ResponseEntity.created(URI.create("/find/" + result.getId()))
                .body(new HashMap<>() {{
                    put("id", result.getId());
                }});

        } catch (ApplicationException exception) {

            return ResponseEntity.status(exception.getCode())
                .body(new HashMap<>() {{
                    put("reason", exception.getReason());
                }});

        }
    }
}
