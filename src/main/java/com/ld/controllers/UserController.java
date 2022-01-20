package com.ld.controllers;

import com.ld.model.User;
import com.ld.services.UserService;
import com.ld.services.validation.UserValidationService;
import com.ld.validation.LoginRequest;
import com.ld.validation.Response;
import com.ld.validation.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserValidationService validationService;

//    @GetMapping(path = "/login")
//    public String login(Model model, String error, String logout) {
//        log.info("MainController.login - rendering login page");
//        if (!isNull(error)) {
//            model.addAttribute("error", "Your phone number or password is invalid");
//        }
//        if (!isNull(logout)) {
//            model.addAttribute("message", "You have been logged out");
//        }
//
//        return "login";
//    }

    @PostMapping(path = "/login")
    public Response login(@RequestBody LoginRequest request) {
        User user = service.findByPhone(request.getPhone());
        if (user.getPassword().equals(request.getPassword())) {
            return Response.result("result", "works");
        }
        return Response.error(HttpStatus.FORBIDDEN.toString(), "");

    }


    @PostMapping(path = "/registration")
    public Response registration(@RequestBody UserRequest request) {

        Response response = validationService.validate(request);
        if (response.isSuccess()) {
            service.register(request);
        }
        return response;
    }
}
