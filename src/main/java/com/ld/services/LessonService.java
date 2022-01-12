package com.ld.services;

import com.ld.enums.AccessType;
import com.ld.model.Lesson;
import com.ld.model.Tag;
import com.ld.repositories.LessonRepository;
import com.ld.validation.ValidateLessonRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonService extends CrudService<Lesson> {

    private final LessonRepository lessonRepository;
    private final TagService tagService;
    private final UserService userService;
    private final ContentService contentService;

    protected JpaRepository<Lesson, UUID> getRepository() {
        return lessonRepository;
    }

    public void save(ValidateLessonRequest request) {
        log.info("LessonService.save - Saving lesson from request '{}", request);
        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creationDate(LocalDate.now())
                .accessType(AccessType.ofName(request.getAccessType()))
                .tags(request.getTags().stream()
                        .map(tagService::findByLabel)
                        .collect(Collectors.toSet()))
                .contents(contentService.parseContents(request.getContents()))
                .author(request.isUserAsAuthor() ?
                        userService.getCurrentUser()
                        : null)
                .build();
        lesson.getContents().forEach(content -> content.setLesson(lesson));
        super.save(lesson);
    }

    public List<Lesson> findByTitleLike(String searchWord) {
        log.info("LessonService.findByTitleLike - Searching lesson with title '{}", searchWord);
        return lessonRepository.findByTitleIgnoreCaseContains(searchWord);
    }

    public List<Lesson> findByTagsIn(Set<Tag> tags) {
        log.info("LessonService.findByTitleLike - Searching lesson with title '{}", tags);
        return lessonRepository.findByTagsIn(tags);
    }

    public List<Lesson> findByAuthor(String author) {
        log.info("LessonService.findByAuthor - Searching lesson by author '{}", author);
        return lessonRepository.findByAuthor_Name(author);
    }
}
