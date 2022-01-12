package com.ld.services;

import com.ld.enums.AccessType;
import com.ld.exceptions.AccessDeniedException;
import com.ld.model.Lesson;
import com.ld.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SSOService {

    private final UserService userService;

    public User getCurrentUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByName(name);
    }

    public boolean isPresentForUser(Lesson lesson) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("SSOService.isPresentForUser - Checking if lesson is public or belongs to user '{}", currentUser);
        return lesson.getAccessType().equals(AccessType.PUBLIC) || lesson.getAuthor().getName().equals(currentUser);
    }

    public void checkUserPermission(Lesson lesson) {
        log.info("LessonService.checkForPermission - Checking permissions to lesson {} for user '{}", lesson,
                getCurrentUser());
        if (!isPresentForUser(lesson)) {
            throw new AccessDeniedException("You are not allowed to this content");
        }
    }
    public List<Lesson> checkUserPermission(List<Lesson> lessons) {
        return lessons.stream()
                .filter(this::isPresentForUser)
                .collect(Collectors.toList());
    }
}
