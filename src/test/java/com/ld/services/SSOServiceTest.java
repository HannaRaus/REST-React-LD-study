package com.ld.services;

import com.ld.error_handling.exceptions.AccessDeniedException;
import com.ld.model.Lesson;
import com.ld.model.User;
import com.ld.model.enums.AccessType;
import com.ld.model.enums.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SSOServiceTest {

    @Mock
    private UserService service;

    @InjectMocks
    private SSOService target;

    @BeforeAll
    public static void init() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("name");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(user());
    }

    @Test
    public void getCurrentUser() throws Exception {
        when(service.findByName("name")).thenReturn(user());

        User result = target.getCurrentUser();

        assertFalse(isNull(result));
        assertEquals("name", result.getName());
    }

    @Test
    public void isPresentForUser_happyPath() {
        boolean result = target.isPresentForUser(lesson());

        assertTrue(result);
    }

    @Test
    public void isPresentForUser_whenLessonIsPublicAndBelongsToAnotherAuthor_shouldReturnTrue() {
        Lesson lesson = lesson();
        lesson.setAuthor(User.builder()
                .name("another user")
                .build());
        boolean result = target.isPresentForUser(lesson);

        assertTrue(result);
    }

    @Test
    public void isPresentForUser_whenLessonIsPrivateAndBelongsToAnotherAuthor_shouldReturnFalse() {
        Lesson lesson = lesson();
        lesson.setAccessType(AccessType.PRIVATE);
        lesson.setAuthor(User.builder()
                .name("another user")
                .build());
        boolean result = target.isPresentForUser(lesson);

        assertFalse(result);
    }

    @Test
    public void checkUserPermission_whenLessonIsPresentForUser_happyPath() {
        target.checkUserPermission(lesson());
    }

    @Test
    public void checkUserPermission_whenLessonIsPublic_happyPath() {
        Lesson lesson = lesson();
        lesson.setAuthor(User.builder()
                .name("another user")
                .build());
        target.checkUserPermission(lesson);
    }

    @Test
    public void checkUserPermission_whenLessonIsNotPresentForUserAndPrivate_happyPath() {
        Lesson lesson = lesson();
        lesson.setAccessType(AccessType.PRIVATE);
        lesson.setAuthor(User.builder()
                .name("another user")
                .build());
        AccessDeniedException result = assertThrows(AccessDeniedException.class, () ->
                target.checkUserPermission(lesson)
        );
        assertEquals("You are not allowed to this content", result.getMessage());
    }

    @Test
    public void checkUserPermission_happyPath() {
        List<Lesson> results = target.checkUserPermission(List.of(lesson()));

        assertFalse(results.isEmpty());
        results.forEach(lesson -> assertEquals("name", lesson.getAuthor().getName()));
    }

    @Test
    public void checkUserPermission_whenLessonInNotPresentForCurrentUserButPublic_shouldFilterList() {
        Lesson anotherUserLesson = lesson();
        anotherUserLesson.setAuthor(User.builder()
                .name("another user")
                .build());
        List<Lesson> results = target.checkUserPermission(List.of(anotherUserLesson));

        assertFalse(results.isEmpty());
        results.forEach(lesson -> assertNotEquals("name", lesson.getAuthor().getName()));
    }

    @Test
    public void checkUserPermission_whenLessonInNotPresentForCurrentUserAndPrivate_shouldFilterList() {
        Lesson anotherUserLesson = lesson();
        anotherUserLesson.setAccessType(AccessType.PRIVATE);
        anotherUserLesson.setAuthor(User.builder()
                .name("another user")
                .build());
        List<Lesson> results = target.checkUserPermission(List.of(anotherUserLesson));

        assertTrue(results.isEmpty());
    }

    private static User user() {
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
                .tags(null)
                .contents(Collections.emptyList())
                .author(user())
                .build();
    }
}