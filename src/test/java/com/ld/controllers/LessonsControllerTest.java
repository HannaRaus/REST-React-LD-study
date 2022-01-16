package com.ld.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ld.error_handling.exceptions.AccessDeniedException;
import com.ld.error_handling.exceptions.EntityNotFoundException;
import com.ld.model.Content;
import com.ld.model.Lesson;
import com.ld.model.enums.AccessType;
import com.ld.model.enums.MediaType;
import com.ld.services.LessonService;
import com.ld.services.SSOService;
import com.ld.services.validation.LessonValidationService;
import com.ld.validation.LessonRequest;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LessonsControllerTest {

    @Mock
    private LessonService lessonService;

    @Mock
    private LessonValidationService validationService;

    @Mock
    private SSOService ssoService;

    @InjectMocks
    private LessonsController target;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    @Test
    public void create_happyPath() throws Exception {
        create_performAndVerify_whenAllDataIsOk(
                lessonRequest(),
                new ValidateResponse(true, Collections.emptyList())
        );
    }

    @Test
    public void create_whenTitleIncorrect_shouldReturnSingleError() throws Exception {
        create_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setTitle(null),
                new ValidateResponse(false, List.of(ValidationError.WRONG_TITLE_LENGTH))
        );
        create_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setTitle("tit"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_TITLE_LENGTH))
        );
        create_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setTitle("looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_TITLE_LENGTH))
        );
    }

    @Test
    public void create_whenDescriptionIncorrect_shouldReturnSingleError() throws Exception {
        create_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setDescription(null),
                new ValidateResponse(false, List.of(ValidationError.WRONG_DESCRIPTION_LENGTH))
        );
        create_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setDescription("looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_DESCRIPTION_LENGTH))
        );
    }

    @Test
    public void create_whenDContentIncorrect_shouldReturnSingleError() throws Exception {
        create_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setContents(null),
                new ValidateResponse(false, List.of(ValidationError.EMPTY_CONTENT_ERROR))
        );
        create_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setContents(Collections.emptyList()),
                new ValidateResponse(false, List.of(ValidationError.EMPTY_CONTENT_ERROR))
        );
    }

    @Test
    public void lesson_happyPath() throws Exception {
        when(lessonService.findById(any())).thenReturn(lesson());
        when(ssoService.isPresentForUser(lesson())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/lessons/lesson", 1)
                        .param("id", "59421202-abd3-4c91-911f-187f49ffb675"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(lesson())));

        verify(lessonService, times(1))
                .findById(UUID.fromString("59421202-abd3-4c91-911f-187f49ffb675"));
        verify(ssoService, times(1)).checkUserPermission(lesson());
    }

    @Test
    public void lesson_whenNoLesson_shouldThrowEntityNotFoundException() throws Exception {
        when(lessonService.findById(any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/lessons/lesson", 1)
                        .param("id", "59421202-abd3-4c91-911f-187f49ffb675"))
                .andExpect(status().isNotFound());

        verify(lessonService, times(1))
                .findById(UUID.fromString("59421202-abd3-4c91-911f-187f49ffb675"));
        verify(ssoService, times(0)).checkUserPermission(lesson());
    }

    @Test
    public void update_happyPath() throws Exception {
        update_performAndVerify_whenAllDataIsOk(
                lessonRequest(),
                new ValidateResponse(true, Collections.emptyList())
        );
    }

    @Test
    public void update_whenTitleIncorrect_shouldReturnSingleError() throws Exception {
        update_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setTitle(null),
                new ValidateResponse(false, List.of(ValidationError.WRONG_TITLE_LENGTH))
        );
        update_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setTitle("tit"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_TITLE_LENGTH))
        );
        update_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setTitle("looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_TITLE_LENGTH))
        );
    }

    @Test
    public void update_whenDescriptionIncorrect_shouldReturnSingleError() throws Exception {
        update_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setDescription(null),
                new ValidateResponse(false, List.of(ValidationError.WRONG_DESCRIPTION_LENGTH))
        );
        update_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setDescription("looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"),
                new ValidateResponse(false, List.of(ValidationError.WRONG_DESCRIPTION_LENGTH))
        );
    }

    @Test
    public void update_whenDContentIncorrect_shouldReturnSingleError() throws Exception {
        update_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setContents(null),
                new ValidateResponse(false, List.of(ValidationError.EMPTY_CONTENT_ERROR))
        );
        update_performAndVerify_whenSomeDataIsIncorrect(
                lessonRequest().setContents(Collections.emptyList()),
                new ValidateResponse(false, List.of(ValidationError.EMPTY_CONTENT_ERROR))
        );
    }

    @Test
    public void delete_happyPath_shouldRedirect() throws Exception {
        when(lessonService.findById(any())).thenReturn(lesson());
        when(ssoService.isPresentForUser(lesson())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/lessons/delete", 1)
                        .param("id", "59421202-abd3-4c91-911f-187f49ffb675"))
                .andExpect(status().is3xxRedirection());

        verify(lessonService, times(1))
                .delete(UUID.fromString("59421202-abd3-4c91-911f-187f49ffb675"));
        verify(ssoService, times(1)).checkUserPermission(lesson());
    }

    @Test
    public void delete_whenNoLessonWithId_shouldThrowEntityNotFoundException() throws Exception {
        when(lessonService.findById(any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/lessons/delete", 1)
                        .param("id", "59421202-abd3-4c91-911f-187f49ffb675"))
                .andExpect(status().isNotFound());

        verify(lessonService, times(0))
                .delete(UUID.fromString("59421202-abd3-4c91-911f-187f49ffb675"));
        verify(ssoService, times(0)).checkUserPermission(lesson());
    }

    @Test
    public void delete_whenLessonIsNotPresentForUser_shouldRedirect() throws Exception {
        when(lessonService.findById(any())).thenReturn(lesson());
        doThrow(AccessDeniedException.class).when(ssoService).checkUserPermission(any(Lesson.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/lessons/delete", 1)
                        .param("id", "59421202-abd3-4c91-911f-187f49ffb675"))
                .andExpect(status().isForbidden());

        verify(lessonService, times(0))
                .delete(UUID.fromString("59421202-abd3-4c91-911f-187f49ffb675"));
        verify(ssoService, times(1)).checkUserPermission(lesson());
    }

    private LessonRequest lessonRequest() {
        return new LessonRequest("title", "description", "Private", null,
                List.of("{'title': 'title', 'mediaType': 'Video', 'url': 'url', 'comment': 'comment'}"),
                true);
    }

    private Lesson lesson() {
        return new Lesson()
                .setId(UUID.fromString("59421202-abd3-4c91-911f-187f49ffb675"))
                .setTitle("title")
                .setDescription("description")
                .setAccessType(AccessType.PUBLIC)
                .setTags(null)
                .setContents(List.of(new Content()
                        .setTitle("title")
                        .setMediaType(MediaType.VIDEO)
                        .setComment("comment")
                        .setUrl("url")))
                .setAuthor(null);
    }

    private void create_performAndVerify_whenAllDataIsOk(LessonRequest request, ValidateResponse response) throws Exception {
        performAndVerifyPOST_whenAllDataIsOk(request, response, "/lessons/create");
    }

    private void create_performAndVerify_whenSomeDataIsIncorrect(LessonRequest request, ValidateResponse response) throws Exception {
        performAndVerifyPOST_whenSomeDataIsIncorrect(request, response, "/lessons/create");
    }

    private void update_performAndVerify_whenAllDataIsOk(LessonRequest request, ValidateResponse response) throws Exception {
        performAndVerifyPOST_whenAllDataIsOk(request, response, "/lessons/edit");
    }

    private void update_performAndVerify_whenSomeDataIsIncorrect(LessonRequest request, ValidateResponse response) throws Exception {
        performAndVerifyPOST_whenSomeDataIsIncorrect(request, response, "/lessons/edit");
    }

    private void performAndVerifyPOST_whenAllDataIsOk(
            LessonRequest request,
            ValidateResponse response,
            String url) throws Exception {
        when(validationService.validate(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(validationService, times(1)).validate(request);
        verify(lessonService, times(1)).save(request);
    }

    private void performAndVerifyPOST_whenSomeDataIsIncorrect(
            LessonRequest request,
            ValidateResponse response,
            String url) throws Exception {
        when(validationService.validate(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(validationService, times(1)).validate(request);
        verify(lessonService, times(0)).save(request);
    }

}