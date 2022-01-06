package com.ld.model;

import com.ld.enums.AccessType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@RequiredArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "lesson_id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Title can't be empty")
    @Size(min = 5, max = 100, message = "Title must be in range of 5-100 symbols")
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type")
    private AccessType accessType;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = com.ld.model.Tag.class)
    @JoinTable(name = "lesson_tags",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private List<Content> contents;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}