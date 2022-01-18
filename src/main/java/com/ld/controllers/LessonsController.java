package com.ld.controllers;

import com.ld.model.Lesson;
import com.ld.model.Tag;
import com.ld.services.LessonService;
import com.ld.services.SSOService;
import com.ld.services.TagService;
import com.ld.services.validation.LessonValidationService;
import com.ld.validation.LessonRequest;
import com.ld.validation.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/lessons")
public class LessonsController {

    private final LessonService lessonService;
    private final LessonValidationService validationService;
    private final TagService tagService;
    private final SSOService ssoService;

    @GetMapping(path = "/all")
    public Response showLessons() {
        List<Tag> tags = tagService.readAll();
        List<Lesson> lessons = lessonService.readAll();
        return Response.result(Map.of("tags", tags,
                "lessons", lessons));
    }

    @PostMapping(path = "/create")
    public Response create(@RequestBody LessonRequest request) {
        Response response = validationService.validate(request);
        if (response.isSuccess()) {
            lessonService.save(request);
        }
        return response;
    }

    @GetMapping
    public Response lesson(@RequestParam(name = "id") UUID id) {
        Lesson lesson = lessonService.findById(id);
        ssoService.isPresentForUser(lesson);
        return Response.result("lesson", lesson);
    }

    @PostMapping(path = "/edit")
    public Response update(@RequestBody LessonRequest request) {
        Response response = validationService.validate(request);
        if (response.isSuccess()) {
            lessonService.save(request);
        }
        return response;
    }

    @GetMapping(path = "/delete")
    public void delete(@RequestParam(name = "id") UUID id) {
        ssoService.checkUserPermission(lessonService.findById(id));
        lessonService.delete(id);
    }

}
