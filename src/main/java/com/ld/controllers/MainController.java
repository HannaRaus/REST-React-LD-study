package com.ld.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.Objects.isNull;

@Slf4j
@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class MainController {

    @GetMapping
    public String doGet() {
        log.info("MainController.doGet - rendering lessons page");
        return "redirect:/lessons/all";
    }

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
}
