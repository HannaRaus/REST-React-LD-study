package com.ld.controllers;

import com.ld.services.TagService;
import com.ld.services.validation.TagValidationService;
import com.ld.validation.ValidateResponse;
import com.ld.validation.ValidateTagRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/tags")
public class TagController {

    private final TagService service;
    private final TagValidationService validationService;

    @PostMapping("/create")
    @ResponseBody
    public ValidateResponse create(@RequestBody ValidateTagRequest request) {
        ValidateResponse response = validationService.validate(request);
        if (response.isSuccess()) {
            service.save(request);
        }
        return response;
    }
}
