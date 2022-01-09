package com.ld.controllers;

import com.ld.model.Tag;
import com.ld.services.LessonService;
import com.ld.services.TagService;
import com.ld.services.editors.TagsEditor;
import com.ld.services.validation.LessonValidationService;
import com.ld.validation.ValidateLessonRequest;
import com.ld.validation.ValidateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/lessons")
public class LessonsController {

    private final LessonService lessonService;
    private final LessonValidationService validationService;
    private final TagService tagService;

    @GetMapping(path = "/all")
    public String showLessons(Model model) {
        log.info("LessonsController.showLessons - rendering lessons list page");
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

    @InitBinder
    public void tagBinding(WebDataBinder binder) {
        binder.registerCustomEditor(Tag.class, "tags", new TagsEditor(tagService));
    }
}
