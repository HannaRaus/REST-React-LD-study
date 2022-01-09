package com.ld.services;

import com.ld.enums.AccessType;
import com.ld.model.Lesson;
import com.ld.repositories.LessonRepository;
import com.ld.validation.ValidateLessonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService extends CrudService<Lesson> {

    private final LessonRepository lessonRepository;
    private final TagService tagService;
    private final UserService userService;

    protected JpaRepository<Lesson, UUID> getRepository() {
        return lessonRepository;
    }

    public void save(ValidateLessonRequest request) {
        super.save(Lesson.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creationDate(LocalDate.now())
                .accessType(AccessType.ofName(request.getAccessType()))
                .tags(request.getTags().stream()
                        .map(tagService::findByLabel)
                        .collect(Collectors.toSet()))
//                .contents(request.getContents())
                .author(request.isUserAsAuthor() ?
                        userService.getCurrentUser()
                        : null)
                .build());
    }
}
