package com.ld.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ld.model.Content;
import com.ld.model.enums.MediaType;
import com.ld.repositories.ContentRepository;
import com.ld.services.editors.GsonLocalDateTime;
import com.ld.validation.ContentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentService extends CrudService<Content> {

    private final ContentRepository repository;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new GsonLocalDateTime())
            .create();

    @Override
    protected JpaRepository<Content, UUID> getRepository() {
        return repository;
    }

    public List<Content> toContent(List<String> contents) {
        return contents.stream()
                .map(content -> gson.fromJson(content, Content.class))
                .collect(Collectors.toList());
    }

    public void save(ContentRequest request) {
        super.save(toContent(request));
    }

    private Content toContent(ContentRequest request) {
        return new Content()
                .setTitle(request.getTitle())
                .setMediaType(MediaType.ofName(request.getMediaType()))
                .setUrl(request.getUrl())
                .setComment(request.getComment());
    }
}
