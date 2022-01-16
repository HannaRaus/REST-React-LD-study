package com.ld.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ld.services.ContentService;
import com.ld.services.validation.ContentValidationService;
import com.ld.validation.ContentRequest;
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
class ContentControllerTest {

    @Mock
    private ContentService contentService;

    @Mock
    private ContentValidationService validationService;

    @InjectMocks
    private ContentController target;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    @Test
    public void create_happyPath() throws Exception {
        create_performAndVerify_whenAllDataIsOk(
                new ContentRequest("title", "Video", "url", "comment"),
                new ValidateResponse(true, Collections.emptyList())
        );
    }

    @Test
    public void create_whenTitleIncorrect_shouldReturnSingleError() throws Exception {
        create_performAndVerify_whenSomeDataIsIncorrect(
                new ContentRequest(null, "Video", "url", "comment"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_TITLE_LENGTH))
        );
        create_performAndVerify_whenSomeDataIsIncorrect(
                new ContentRequest(
                        "titletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitle" +
                                "titletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitle",
                        "Video", "url", "comment"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_TITLE_LENGTH))
        );
    }

    @Test
    public void create_whenMediaTypeIncorrect_shouldReturnSingleError() throws Exception {
        create_performAndVerify_whenSomeDataIsIncorrect(
                new ContentRequest("label", "Videozz", "url", "comment"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_MEDIA_TYPE))
        );
        create_performAndVerify_whenSomeDataIsIncorrect(
                new ContentRequest("label", "imagez111", "url", "comment"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_MEDIA_TYPE))
        );
    }

    @Test
    public void create_whenUrlIncorrect_shouldReturnSingleError() throws Exception {
        create_performAndVerify_whenSomeDataIsIncorrect(
                new ContentRequest("label", "Video", null, "comment"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_URL_LENGTH))
        );
        create_performAndVerify_whenSomeDataIsIncorrect(
                new ContentRequest("label", "Video", "", "comment"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_URL_LENGTH))
        );
        create_performAndVerify_whenSomeDataIsIncorrect(
                new ContentRequest("label", "Video",
                        "loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo",
                        "comment"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_URL_LENGTH))
        );
    }

    @Test
    public void create_whenCommentIncorrect_shouldReturnSingleError() throws Exception {
        create_performAndVerify_whenSomeDataIsIncorrect(
                new ContentRequest("label", "Video", "url", null),
                new ValidateResponse(false, List.of(ValidationError.WRONG_COMMENT_LENGTH))
        );
        create_performAndVerify_whenSomeDataIsIncorrect(
                new ContentRequest("label", "Video", "url",
                        "looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_COMMENT_LENGTH))
        );
    }

    private void create_performAndVerify_whenAllDataIsOk(ContentRequest request, ValidateResponse response) throws Exception {
        when(validationService.validate(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/contents/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(validationService, times(1)).validate(request);
    }

    private void create_performAndVerify_whenSomeDataIsIncorrect(ContentRequest request, ValidateResponse response) throws Exception {
        when(validationService.validate(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/contents/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(validationService, times(1)).validate(request);
    }
}