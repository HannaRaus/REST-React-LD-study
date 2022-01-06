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

@Service
@RequiredArgsConstructor
public class LessonService extends CrudService<Lesson> {

    private final LessonRepository repository;
    private final UserService userService;

    @Override
    protected JpaRepository<Lesson, UUID> getRepository() {
        return repository;
    }

    public void save(ValidateLessonRequest request) {
        super.save(Lesson.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creationDate(LocalDate.now())
                .accessType(AccessType.ofName(request.getAccessType()))
//                .tags(request.getTags())
//                .contents(request.getContents())
                .author(request.isUserAsAuthor() ?
                        userService.getCurrentUser()
                        : null)
                .build());
    }
}
