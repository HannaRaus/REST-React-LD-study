package com.ld.controllers;

import com.ld.model.Lesson;
import com.ld.model.Tag;
import com.ld.services.LessonService;
import com.ld.services.SSOService;
import com.ld.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/lessons/search")
public class LessonSearchController {

    private final LessonService lessonService;
    private final TagService tagService;
    private final SSOService ssoService;

    @GetMapping
    public String searchByInput(@RequestParam(name = "contains") String contains, Model model) {
        List<Tag> tags = tagService.readAll();
        List<Lesson> lessonsByTitle = ssoService.checkUserPermission(lessonService.findByTitleLike(contains));
        model.addAttribute("tags", tags);
        model.addAttribute("lessons", lessonsByTitle);
        return "lessons";
    }

    @GetMapping(path = "/tags")
    public String searchByTags(@RequestParam(name = "tags") Set<String> tags, Model model) {
        Set<Tag> searchTags = tags.stream()
                .map(tagService::findByLabel)
                .collect(Collectors.toSet());
        List<Lesson> lessonsByTags = ssoService.checkUserPermission(lessonService.findByTagsIn(searchTags));
        model.addAttribute("lessons", lessonsByTags);
        return "lessons";
    }

    @GetMapping(path = "/author")
    public String searchByAuthor(@RequestParam(name = "name") String name, Model model) {
        List<Lesson> lessonsByAuthor = ssoService.checkUserPermission(lessonService.findByAuthor(name));
        Set<Tag> tags = lessonsByAuthor.stream()
                .map(Lesson::getTags)
                .collect(Collectors.toList())
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        model.addAttribute("tags", tags);
        model.addAttribute("lessons", lessonsByAuthor);
        return "lessons";
    }
}
