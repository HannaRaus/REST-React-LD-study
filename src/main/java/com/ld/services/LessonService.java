package com.ld.services;

import com.ld.model.Lesson;
import com.ld.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService extends CrudService<Lesson> {

    private final LessonRepository repository;

    @Override
    protected JpaRepository<Lesson, UUID> getRepository() {
        return repository;
    }
}
