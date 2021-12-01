package com.ld.model;

import com.ld.enums.MediaType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.EnumType;
import java.util.UUID;

@Entity
@Table(name = "contents")
@Getter
@Setter
@RequiredArgsConstructor
public class Content {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "content_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false)
    private MediaType mediaType;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
}