package com.ld.services;

import com.ld.model.Content;
import com.ld.model.enums.MediaType;
import com.ld.repositories.ContentRepository;
import com.ld.validation.ContentRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

    @Mock
    private ContentRepository repository;

    @InjectMocks
    private ContentService target;

    @Test
    public void toContent_whenInputIsListOfStrings_happyPath() {
        List<String> contents = List.of("{" +
                "title: 'title'," +
                "mediaType: 'VIDEO'," +
                "url: 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'," +
                "comment: 'comment'" +
                "}");

        List<Content> result = target.toContent(contents);
        assertEquals(1, result.size());
        result.forEach(content -> {
            assertEquals("title", content.getTitle());
            assertEquals(MediaType.VIDEO, content.getMediaType());
            assertEquals("https://www.youtube.com/watch?v=dQw4w9WgXcQ", content.getUrl());
            assertEquals("comment", content.getComment());
        });
    }


    @Test
    public void toContent_whenInputIsValidateRequest_happyPath() {
        ContentRequest request = new ContentRequest(
                "title", "VIDEO", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "comment");

        target.save(request);

        Content content = new Content()
                .setTitle("title")
                .setMediaType(MediaType.VIDEO)
                .setUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
                .setComment("comment");
        verify(repository, times(1)).save(content);
    }
}