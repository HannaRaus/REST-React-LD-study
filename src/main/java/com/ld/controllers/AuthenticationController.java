package com.ld.controllers;

import com.ld.services.UserService;
import com.ld.validation.AuthenticationRequest;
import com.ld.validation.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
//TODO handle cross origin
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping(path = "/login")
    public Response login(@RequestBody AuthenticationRequest request) {
        userService.authenticate(request);
        return Response.ok();
    }

    @PostMapping(path = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
    }
}
