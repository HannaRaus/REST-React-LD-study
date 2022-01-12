package com.ld.controllers;

import com.ld.model.Lesson;
import com.ld.model.Tag;
import com.ld.services.LessonService;
import com.ld.services.TagService;
import com.ld.services.validation.LessonValidationService;
import com.ld.validation.ValidateLessonRequest;
import com.ld.validation.ValidateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        model.addAttribute("tags", tagService.readAll());
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

    @GetMapping(path = "/search")
    public String searchByInput(@RequestParam(name = "contains") String contains, Model model) {
        model.addAttribute("tags", tagService.readAll());
        model.addAttribute("lessons", lessonService.findByTitleLike(contains));
        return "lessons";
    }

    @GetMapping(path = "/search/tags")
    public String searchByInput(@RequestParam(name = "tags") Set<String> tags, Model model) {
        Set<Tag> searchTags = tags.stream()
                .map(tagService::findByLabel)
                .collect(Collectors.toSet());
        List<Lesson> lessons = lessonService.findByTagsIn(searchTags);
        model.addAttribute("lessons", lessons);
        return "lessons";
    }

    @GetMapping(path = "/search/author")
    public String searchByAuthor(@RequestParam(name = "name") String name, Model model) {
        List<Lesson> lessonsByAuthor = lessonService.findByAuthor(name);
        Set<Tag> tagsAssociatedWithLesson = lessonsByAuthor.stream()
                .map(Lesson::getTags)
                .collect(Collectors.toSet())
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        model.addAttribute("tags", tagsAssociatedWithLesson);
        model.addAttribute("lessons", lessonsByAuthor);
        return "lessons";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam(name = "id") UUID id) {
        lessonService.delete(id);
        return "redirect:/lessons/all";
    }

//    @InitBinder
//    public void tagBinding(WebDataBinder binder) {
//        binder.registerCustomEditor(Tag.class, "tags", new TagsEditor(tagService));
//    }
}
