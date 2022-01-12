package com.ld.controllers;

import com.ld.services.UserService;
import com.ld.services.validation.UserValidationService;
import com.ld.validation.ValidateResponse;
import com.ld.validation.ValidateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;

@Slf4j
@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserValidationService validationService;

    @GetMapping(path = "/login")
    public String login(Model model, String error, String logout) {
        log.info("MainController.login - rendering login page");
        if (!isNull(error)) {
            model.addAttribute("error", "Your phone number or password is invalid");
        }
        if (!isNull(logout)) {
            model.addAttribute("message", "You have been logged out");
        }

        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping(path = "/registration")
    @ResponseBody
    public ValidateResponse registration(@RequestBody ValidateUserRequest request) {
        ValidateResponse response = validationService.validate(request);
        if (response.isSuccess()) {
            service.register(request);
        }
        return response;
    }
}
