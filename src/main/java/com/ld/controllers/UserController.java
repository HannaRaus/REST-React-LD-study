package com.ld.controllers;

import com.ld.services.UserService;
import com.ld.services.validation.UserValidationService;
import com.ld.validation.Response;
import com.ld.validation.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserValidationService validationService;

    @PostMapping(path = "/registration")
    public Response registration(@RequestBody UserRequest request) {

        Response response = validationService.validate(request);
        if (response.isSuccess()) {
            service.register(request);
        }
        return response;
    }
}
