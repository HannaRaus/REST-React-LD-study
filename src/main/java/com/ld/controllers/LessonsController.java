package com.ld.controllers;

import com.ld.exceptions.AccessDeniedException;
import com.ld.model.Lesson;
import com.ld.services.LessonService;
import com.ld.services.TagService;
import com.ld.services.UserService;
import com.ld.services.validation.LessonValidationService;
import com.ld.validation.ValidateLessonRequest;
import com.ld.validation.ValidateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/lessons")
public class LessonsController {

    private final LessonService lessonService;
    private final LessonValidationService validationService;
    private final TagService tagService;
    private final UserService userService;

    @GetMapping(path = "/all")
    public String showLessons(Model model) {
        List<Lesson> lessons = lessonService.checkForAccess(lessonService.readAll());
        model.addAttribute("tags", tagService.readAll());
        model.addAttribute("lessons", lessons);
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
        if (userService.isPresentForUser(lesson)) {
            return lesson;
        } else {
            throw new AccessDeniedException("You are not allowed to this content");
        }
    }

    @GetMapping(path = "/share")
    public String showLesson(@RequestParam(name = "id") UUID id) {
        userService.getCurrentUser();
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
        lessonService.delete(id);
        return "redirect:/lessons/all";
    }

}
