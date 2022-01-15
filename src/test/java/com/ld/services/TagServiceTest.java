package com.ld.services;

import com.ld.error_handling.exceptions.EntityNotFoundException;
import com.ld.model.Tag;
import com.ld.repositories.TagRepository;
import com.ld.validation.TagRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository repository;

    @InjectMocks
    private TagService target;

    @Test
    public void save_happyPath() {
        TagRequest request = new TagRequest("label");
        target.save(request);

        Tag tag = Tag.builder()
                .label("label")
                .build();
        verify(repository, times(1)).save(tag);
    }

    @Test
    public void findByLabel_happyPath() {
        when(repository.findByLabel(any())).thenReturn(Optional.of(tag()));

        Tag result = target.findByLabel("label");

        assertFalse(isNull(result));
        assertEquals("label", result.getLabel());
    }

    @Test
    public void findByLabel_whenThereIsNoLabel_shouldThrowEntityNotFoundException() {
        when(repository.findByLabel(any())).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () ->
                target.findByLabel("label"));
        assertEquals("Tag with Label [label] doesn't exist", result.getMessage());
    }

    @Test
    public void findSameTags_happyPath() {
        when(repository.findByLabelContains(any())).thenReturn(List.of(tag()));

        List<Tag> result = target.findSameTags("label");

        assertFalse(result.isEmpty());
        result.forEach(tag -> assertTrue(tag.getLabel().contains("label")));
    }

    @Test
    public void findSameTags_whenThereIsNoLabel_shouldThrowEntityNotFoundException() {
        when(repository.findByLabelContains(any())).thenReturn(Collections.emptyList());

        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () ->
                target.findSameTags("label"));
        assertEquals("Tag with Label [label] doesn't exist", result.getMessage());
    }

    @Test
    public void tagAlreadyExists_whenTagExists_shouldReturnTrue() {
        when(repository.findByLabel(any())).thenReturn(Optional.of(tag()));

        boolean result = target.tagAlreadyExists("label");

        assertTrue(result);
    }

    @Test
    public void tagAlreadyExists_whenTagDoesntExist_shouldReturnFalse() {
        when(repository.findByLabel(any())).thenThrow(EntityNotFoundException.class);

        boolean result = target.tagAlreadyExists("label");

        assertFalse(result);
    }

    @Test
    public void isTagUnique_whenTagIsUnique_shouldReturnTrue() {
        when(repository.findByLabelContains(any())).thenReturn(Collections.emptyList());

        boolean result = target.isTagUnique("label");

        assertTrue(result);
    }

    @Test
    public void isTagUnique_whenTagDoesntExist_shouldReturnFalse() {
        when(repository.findByLabelContains(any())).thenReturn(List.of(tag()));

        boolean result = target.isTagUnique("label");

        assertFalse(result);
    }

    private Tag tag() {
        return Tag.builder()
                .id(UUID.randomUUID())
                .label("label")
                .lessons(null)
                .build();
    }
}