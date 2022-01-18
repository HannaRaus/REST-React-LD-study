package com.ld.controllers;

import com.ld.services.TagService;
import com.ld.services.validation.TagValidationService;
import com.ld.validation.Response;
import com.ld.validation.TagRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tags")
public class TagController {

    private final TagService service;
    private final TagValidationService validationService;

    @PostMapping("/create")
    public Response create(@RequestBody TagRequest request) {
        Response response = validationService.validate(request);
        if (response.isSuccess()) {
            service.save(request);
        }
        return response;
    }
}
