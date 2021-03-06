package com.ld.services;

import com.ld.error_handling.exceptions.EntityNotFoundException;
import com.ld.model.Lesson;
import com.ld.model.Tag;
import com.ld.model.enums.AccessType;
import com.ld.repositories.LessonRepository;
import com.ld.validation.LessonRequest;
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
    private final ContentService contentService;
    private final SSOService ssoService;

    protected JpaRepository<Lesson, UUID> getRepository() {
        return lessonRepository;
    }

    public void save(LessonRequest request) {
        log.info("LessonService.save - Saving lesson from request '{}", request);
        Lesson lesson = toLesson(request);
        lesson.getContents().forEach(content -> content.setLesson(lesson));
        super.save(lesson);
    }

    public Lesson findById(UUID id) {
        log.info("LessonService.findById - Searching lesson with id '{}", id);
        return lessonRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Lesson with id [%s] doesn't exist", id)));
    }

    public List<Lesson> findByTitleLike(String searchWord) {
        log.info("LessonService.findByTitleLike - Searching lesson with title '{}", searchWord);
        return lessonRepository.findByTitleIgnoreCaseContainsOrDescriptionIgnoreCaseContains(searchWord, searchWord);
    }

    public Set<Lesson> findByTagsIn(Set<Tag> tags) {
        log.info("LessonService.findByTitleLike - Searching lesson with title '{}", tags);
        return lessonRepository.findByTagsIn(tags);
    }

    public List<Lesson> findByAuthor(String author) {
        log.info("LessonService.findByAuthor - Searching lesson by author '{}", author);
        return lessonRepository.findByAuthor_Name(author);
    }

    private Lesson toLesson(LessonRequest request) {
        return new Lesson()
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setCreationDate(LocalDate.now())
                .setAccessType(AccessType.ofName(request.getAccessType()))
                .setTags(request.getTags().stream()
                        .map(tagService::findByLabel)
                        .collect(Collectors.toSet()))
                .setContents(contentService.toContent(request.getContents()))
                .setAuthor(request.isUserAsAuthor() ?
                        ssoService.getCurrentUser()
                        : null);
    }
}
