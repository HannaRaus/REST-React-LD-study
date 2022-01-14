package com.ld.error_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class LDErrorController implements ErrorController {

    @RequestMapping(path = "/error")
    @ExceptionHandler(Throwable.class)
    public ModelAndView errorPage(Throwable exception) {
        log.error("ErrorController.errorPage - An error happened {}", exception.getMessage());
        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMessage = "Something went wrong, Please, check your request and try again.";
        errorMessage = errorMessage + (exception.getMessage() != null ? exception.getMessage() : "");
        errorPage.addObject("errorMessage", errorMessage);
        return errorPage;
    }
}
