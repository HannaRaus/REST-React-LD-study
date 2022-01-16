package com.ld.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ld.services.TagService;
import com.ld.services.validation.TagValidationService;
import com.ld.validation.TagRequest;
import com.ld.validation.ValidateResponse;
import com.ld.validation.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TagControllerTest {

    @Mock
    private TagService tagService;

    @Mock
    private TagValidationService validationService;

    @InjectMocks
    private TagController target;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    @Test
    public void create_happyPath() throws Exception {
        create_performAndVerify_whenAllDataIsOk(
                new TagRequest("label"),
                new ValidateResponse(true, Collections.emptyList())
        );
    }

    @Test
    public void create_whenLabelIncorrect_shouldReturnSingleError() throws Exception {
        create_performAndVerify_whenSomeDataIsIncorrect(
                new TagRequest("la"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_TAG_LENGTH))
        );
        create_performAndVerify_whenSomeDataIsIncorrect(
                new TagRequest(
                        "looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_TAG_LENGTH))
        );
        create_performAndVerify_whenSomeDataIsIncorrect(
                new TagRequest(null),
                new ValidateResponse(false, List.of(ValidationError.WRONG_TAG_LENGTH))
        );
    }

    @Test
    public void create_whenLabelAlreadyExist_shouldReturnSingleError() throws Exception {
        when(tagService.tagAlreadyExists(any())).thenReturn(true);
        create_performAndVerify_whenSomeDataIsIncorrect(
                new TagRequest("label"),
                new ValidateResponse(false, List.of(ValidationError.TAG_DUPLICATE_ERROR))
        );
    }

    @Test
    public void create_whenLabelIsNotUnique_shouldReturnSingleError() throws Exception {
        when(tagService.isTagUnique(any())).thenReturn(false);
        create_performAndVerify_whenSomeDataIsIncorrect(
                new TagRequest("label"),
                new ValidateResponse(false, List.of(ValidationError.TAG_IS_NOT_UNIQUE))
        );
    }

    @Test
    public void create_whenSomeDataIncorrect_shouldReturnLestOfError() throws Exception {
        when(tagService.tagAlreadyExists(any())).thenReturn(true);
        when(tagService.isTagUnique(any())).thenReturn(false);
        create_performAndVerify_whenSomeDataIsIncorrect(
                new TagRequest("la"),
                new ValidateResponse(false, List.of(
                        ValidationError.WRONG_TAG_LENGTH,
                        ValidationError.TAG_DUPLICATE_ERROR,
                        ValidationError.TAG_IS_NOT_UNIQUE))
        );
    }

    private void create_performAndVerify_whenAllDataIsOk(TagRequest request, ValidateResponse response) throws Exception {
        when(validationService.validate(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/tags/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(validationService, times(1)).validate(request);
        verify(tagService, times(1)).save(request);
    }

    private void create_performAndVerify_whenSomeDataIsIncorrect(TagRequest request, ValidateResponse response) throws Exception {
        when(validationService.validate(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/tags/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(validationService, times(1)).validate(request);
        verify(tagService, times(0)).save(request);
    }
}