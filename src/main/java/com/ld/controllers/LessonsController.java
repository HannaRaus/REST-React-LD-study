package com.ld.controllers;

import com.ld.model.Lesson;
import com.ld.services.LessonService;
import com.ld.validation.LessonValidationService;
import com.ld.validation.ValidateLessonRequest;
import com.ld.validation.ValidateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/lessons")
public class LessonsController {

    private final LessonService lessonService;
    private final LessonValidationService validationService;

    @GetMapping(path = "/all")
    public List<Lesson> showLessons() {
        log.info("LessonsController.showLessons - rendering lessons list page");
        return lessonService.readAll();
    }

    @GetMapping("/create")
    public String showNewLessonsForm() {
        return "newLesson";
    }

    @PostMapping(path = "/create")
    @ResponseBody
    public ValidateResponse create(@RequestBody ValidateLessonRequest request) {
        ValidateResponse response = validationService.validate(request);
        if (response.isSuccess()) {
            lessonService.save(request);
        }
        return response;
    }
}
