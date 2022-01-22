package com.ld.controllers;

import com.ld.model.Lesson;
import com.ld.model.Tag;
import com.ld.services.LessonService;
import com.ld.services.SSOService;
import com.ld.services.TagService;
import com.ld.validation.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/lessons/search")
@PreAuthorize("hasAuthority('read')")
@RequiredArgsConstructor
public class LessonSearchController {

    private final LessonService lessonService;
    private final TagService tagService;
    private final SSOService ssoService;

    @GetMapping
    public Response searchByInput(@RequestParam(name = "contains") String contains, Model model) {
        List<Tag> tags = tagService.readAll();
        List<Lesson> lessonsByTitle = ssoService.checkUserPermission(lessonService.findByTitleLike(contains));
        return Response.result("tags", tags, "lessons", lessonsByTitle);
    }

    @GetMapping(path = "/tags")
    public Response searchByTags(@RequestParam(name = "tag") Set<String> tags, Model model) {
        Set<Tag> searchTags = tags.stream()
                .map(tagService::findByLabel)
                .collect(Collectors.toSet());
        List<Lesson> lessonsByTags = ssoService.checkUserPermission(lessonService.findByTagsIn(searchTags));
        return Response.result("tags", tagService.readAll(), "lessons", lessonsByTags);
    }

    @GetMapping(path = "/author")
    public Response searchByAuthor(@RequestParam(name = "name") String name, Model model) {
        List<Lesson> lessonsByAuthor = ssoService.checkUserPermission(lessonService.findByAuthor(name));
        Set<Tag> tags = lessonsByAuthor.stream()
                .map(Lesson::getTags)
                .collect(Collectors.toList())
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        return Response.result("tags", tags, "lessons", lessonsByAuthor);
    }
}
