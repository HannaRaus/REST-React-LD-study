package com.ld.controllers;

import com.ld.services.UserService;
import com.ld.services.validation.UserValidationService;
import com.ld.validation.AuthenticationRequest;
import com.ld.validation.Response;
import com.ld.validation.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final UserValidationService validationService;

    @PostMapping(path = "/login")
    public Response login(@RequestBody AuthenticationRequest request) {
        String token = userService.authenticate(request.getPhone(), request.getPassword());
        return Response.result("token", token);
    }

    @PostMapping(path = "/logout")
    public Response logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
        return Response.result("message", "You have been logged out");
    }

    @PostMapping(path = "/registration")
    public Response registration(@RequestBody UserRequest request) {
        Response response = validationService.validate(request);
        if (response.isSuccess()) {
            userService.register(request);
            String token = userService.authenticate(request.getPhone(), request.getPassword());
            response.put("token", token);
        }
        return response;
    }
}
