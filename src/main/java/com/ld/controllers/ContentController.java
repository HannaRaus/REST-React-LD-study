package com.ld.controllers;

import com.ld.services.ContentService;
import com.ld.services.validation.ContentValidationService;
import com.ld.validation.ContentRequest;
import com.ld.validation.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/contents")
public class ContentController {

    private final ContentService service;
    private final ContentValidationService validationService;
    private final LessonsController controller;

    @PostMapping(path = "/create")
    @PreAuthorize("hasAuthority('write')")
    public Response create(@RequestBody ContentRequest request) {
        return validationService.validate(request);
    }
}
