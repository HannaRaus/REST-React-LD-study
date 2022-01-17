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
        when(repository.findByTitleIgnoreCaseContainsOrDescriptionIgnoreCaseContains(any(), any()))
                .thenReturn(List.of(lesson()));

        List<Lesson> result = target.findByTitleLike("title");

        result.forEach(lesson -> assertTrue(lesson.getTitle().contains("title")));
    }

    @Test
    public void findByTitleLike_whenNoLessonWithSuchTitle_shouldReturnEmptyList() {
        when(repository.findByTitleIgnoreCaseContainsOrDescriptionIgnoreCaseContains(any(), any()))
                .thenReturn(Collections.emptyList());

        List<Lesson> result = target.findByTitleLike("title");

        assertTrue(result.isEmpty());
    }

    @Test
    public void findByTagsIn_happyPath() {
        when(repository.findByTagsIn(any())).thenReturn(Set.of(lesson()));

        Set<Lesson> result = target.findByTagsIn(Set.of(tag()));
        Set<Lesson> expected = result.stream()
                .filter(lesson -> !lesson.getTags().contains(tag()))
                .collect(Collectors.toSet());

        assertEquals(expected, result);
        result.forEach(lesson -> lesson.getTags()
                .forEach(tag -> assertEquals("label", tag.getLabel())));
    }

    @Test
    public void findByTagsIn_whenNoLessonWithSuchTag_shouldReturnEmptyList() {
        when(repository.findByTagsIn(any())).thenReturn(Collections.emptySet());

        Set<Lesson> result = target.findByTagsIn(Set.of(tag()));

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
        return new Tag()
                .setId(UUID.randomUUID())
                .setLabel("label")
                .setLessons(null);
    }

    private Content content() {
        return new Content()
                .setId(UUID.randomUUID())
                .setTitle("title")
                .setMediaType(MediaType.VIDEO)
                .setUrl("url")
                .setComment("comment");
    }

    private User user() {
        return new User()
                .setName("name")
                .setPhone("+380631111111")
                .setPassword("password")
                .setActive(true)
                .setSendNotification(true)
                .setUserRole(UserRole.ROLE_USER)
                .setFavoriteLessons(null);
    }

    private Lesson lesson() {
        return new Lesson()
                .setId(UUID.fromString("17e52164-25e5-4771-b024-d77bddec124c"))
                .setTitle("title")
                .setDescription("description")
                .setCreationDate(LocalDate.now())
                .setAccessType(AccessType.PUBLIC)
                .setTags(Set.of(tag()))
                .setContents(List.of(content()))
                .setAuthor(user());
    }
}