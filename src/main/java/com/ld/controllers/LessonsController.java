package com.ld.controllers;

import com.ld.model.Lesson;
import com.ld.services.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/lessons")
public class LessonsController {

    private final LessonService lessonService;

    @GetMapping(path = "/all")
    public List<Lesson> showLessons() {
        log.info("LessonsController.showLessons - rendering lessons list page");
        return lessonService.readAll();
    }
}
