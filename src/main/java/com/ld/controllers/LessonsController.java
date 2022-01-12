package com.ld.controllers;

import com.ld.model.Lesson;
import com.ld.services.LessonService;
import com.ld.services.SSOService;
import com.ld.services.TagService;
import com.ld.services.validation.LessonValidationService;
import com.ld.validation.ValidateLessonRequest;
import com.ld.validation.ValidateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/lessons")
public class LessonsController {

    private final LessonService lessonService;
    private final LessonValidationService validationService;
    private final TagService tagService;
    private final SSOService ssoService;

    @GetMapping(path = "/all")
    public String showLessons(Model model) {
        model.addAttribute("tags", tagService.readAll());
        model.addAttribute("lessons", lessonService.readAll());
        return "lessons";
    }

    @GetMapping("/create")
    public String showNewLessonsForm(Model model) {
        model.addAttribute("tags", tagService.readAll());
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

    @GetMapping(path = "/lesson")
    @ResponseBody
    public Lesson lesson(@RequestParam(name = "id") UUID id) {
        Lesson lesson = lessonService.findById(id);
        ssoService.checkUserPermission(lesson);
        return lesson;
    }

    @GetMapping(path = "/share")
    public String showLesson(@RequestParam(name = "id") UUID id) {
        return "lesson";
    }

    @GetMapping("/edit")
    public String showUpdateLessonsForm(@RequestParam(name = "id") UUID id) {
        return "updateLesson";
    }

    @PostMapping(path = "/edit")
    @ResponseBody
    public ValidateResponse update(@RequestBody ValidateLessonRequest request) {
        ValidateResponse response = validationService.validate(request);
        if (response.isSuccess()) {
            lessonService.save(request);
        }
        return response;
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam(name = "id") UUID id) {
        ssoService.checkUserPermission(lessonService.findById(id));
        lessonService.delete(id);
        return "redirect:/lessons/all";
    }

}
