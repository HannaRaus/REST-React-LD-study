package com.ld.services;

import com.ld.error_handling.exceptions.EntityNotFoundException;
import com.ld.model.Content;
import com.ld.model.Lesson;
import com.ld.model.Tag;
import com.ld.model.User;
import com.ld.model.enums.AccessType;
import com.ld.model.enums.MediaType;
import com.ld.model.enums.UserRole;
import com.ld.repositories.LessonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository repository;

    @InjectMocks
    private LessonService target;

    @Test
    public void findById_happyPath() {
        when(repository.findById(any())).thenReturn(Optional.of(lesson()));

        Lesson result = target.findById(UUID.fromString("17e52164-25e5-4771-b024-d77bddec124c"));

        assertFalse(isNull(result));
        assertEquals(UUID.fromString("17e52164-25e5-4771-b024-d77bddec124c"), result.getId());
    }

    @Test
    public void findById_whenNoLessonWithId_shouldThrowEntityNotFoundException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () ->
                target.findById(UUID.fromString("17e52164-25e5-4771-b024-d77bddec124c")));
        assertEquals("Lesson with id [17e52164-25e5-4771-b024-d77bddec124c] doesn't exist", result.getMessage());
    }

    @Test
    public void findByTitleLike_happyPath() {
        when(repository.findByTitleIgnoreCaseContains(any())).thenReturn(List.of(lesson()));

        List<Lesson> result = target.findByTitleLike("title");

        result.forEach(lesson -> assertTrue(lesson.getTitle().contains("title")));
    }

    @Test
    public void findByTitleLike_whenNoLessonWithSuchTitle_shouldReturnEmptyList() {
        when(repository.findByTitleIgnoreCaseContains(any())).thenReturn(Collections.emptyList());

        List<Lesson> result = target.findByTitleLike("title");

        assertTrue(result.isEmpty());
    }

    @Test
    public void findByTagsIn_happyPath() {
        when(repository.findByTagsIn(any())).thenReturn(List.of(lesson()));

        List<Lesson> result = target.findByTagsIn(Set.of(tag()));
        List<Lesson> expected = result.stream()
                .filter(lesson -> !lesson.getTags().contains(tag()))
                .collect(Collectors.toList());

        assertEquals(expected, result);
        result.forEach(lesson -> lesson.getTags()
                .forEach(tag -> assertEquals("label", tag.getLabel())));
    }

    @Test
    public void findByTagsIn_whenNoLessonWithSuchTag_shouldReturnEmptyList() {
        when(repository.findByTagsIn(any())).thenReturn(Collections.emptyList());

        List<Lesson> result = target.findByTagsIn(Set.of(tag()));

        assertTrue(result.isEmpty());
    }

    @Test
    public void findByAuthor_happyPath() {
        when(repository.findByAuthor_Name(any())).thenReturn(List.of(lesson()));

        List<Lesson> result = target.findByAuthor("name");

        assertFalse(result.isEmpty());
        result.forEach(lesson -> assertEquals("name", lesson.getAuthor().getName()));
    }

    @Test
    public void findByAuthor_whenNoLessonWithSuchAuthor_shouldReturnEmptyList() {
        when(repository.findByAuthor_Name(any())).thenReturn(Collections.emptyList());

        List<Lesson> result = target.findByAuthor("name");

        assertTrue(result.isEmpty());
    }

    private Tag tag() {
        return Tag.builder()
                .id(UUID.randomUUID())
                .label("label")
                .lessons(null)
                .build();
    }

    private Content content() {
        return Content.builder()
                .id(UUID.randomUUID())
                .title("title")
                .mediaType(MediaType.VIDEO)
                .url("url")
                .comment("comment")
                .build();
    }

    private User user() {
        return User.builder()
                .name("name")
                .phone("+380631111111")
                .password("password")
                .isActive(true)
                .sendNotification(true)
                .userRole(UserRole.ROLE_USER)
                .favoriteLessons(null)
                .build();
    }

    private Lesson lesson() {
        return Lesson.builder()
                .id(UUID.fromString("17e52164-25e5-4771-b024-d77bddec124c"))
                .title("title")
                .description("description")
                .creationDate(LocalDate.now())
                .accessType(AccessType.PUBLIC)
                .tags(Set.of(tag()))
                .contents(List.of(content()))
                .author(user())
                .build();
    }
}