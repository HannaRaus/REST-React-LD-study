package com.ld.repositories;

import com.ld.model.Lesson;
import com.ld.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    List<Lesson> findByTitleIgnoreCaseContainsOrDescriptionIgnoreCaseContains(String title, String description);

    Set<Lesson> findByTagsIn(Set<Tag> tags);

    List<Lesson> findByAuthor_Name(String author);

}
