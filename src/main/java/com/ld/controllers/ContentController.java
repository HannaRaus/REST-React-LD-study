package com.ld.controllers;

import com.ld.services.ContentService;
import com.ld.services.validation.ContentValidationService;
import com.ld.validation.ValidateContentRequest;
import com.ld.validation.ValidateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/contents")
public class ContentController {

    private final ContentService service;
    private final ContentValidationService validationService;
    private final LessonsController controller;

    @PostMapping(path = "/create")
    @ResponseBody
    public ValidateResponse create(@RequestBody ValidateContentRequest request) {
        ValidateResponse response = validationService.validate(request);
        return response;
    }
}
