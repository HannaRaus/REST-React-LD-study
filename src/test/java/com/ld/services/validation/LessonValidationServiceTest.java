package com.ld.services.validation;

import com.ld.model.enums.AccessType;
import com.ld.validation.LessonRequest;
import com.ld.validation.ValidateResponse;
import com.ld.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class LessonValidationServiceTest {

    @InjectMocks
    private LessonValidationService target;

    @Test
    public void validate_happyPath() {
        LessonRequest request = new LessonRequest(
                "title", "", AccessType.PUBLIC.name(), null, List.of(""), false);
        ValidateResponse response = target.validate(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenTitleIncorrect_shouldReturnWrongTitleLengthError() {
        LessonRequest request = new LessonRequest(
                "tit", "", AccessType.PUBLIC.name(), null, List.of(""), false);
        ValidateResponse response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TITLE_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new LessonRequest(
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr",
                "", AccessType.PUBLIC.name(), null, List.of(""), false);
        response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TITLE_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new LessonRequest(
                null, "", AccessType.PUBLIC.name(), null, List.of(""), false);
        response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TITLE_LENGTH));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenDescriptionIncorrect_shouldReturnWrongDescriptionLengthError() {
        LessonRequest request = new LessonRequest(
                "title", "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "", AccessType.PUBLIC.name(), null, List.of(""), false);
        ValidateResponse response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_DESCRIPTION_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new LessonRequest(
                "title", null, AccessType.PUBLIC.name(), null, List.of(""), false);
        response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_DESCRIPTION_LENGTH));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenContentIsEmpty_shouldReturnEmptyContentError() {
        LessonRequest request = new LessonRequest(
                "title", "", AccessType.PUBLIC.name(), null, null, false);
        ValidateResponse response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.EMPTY_CONTENT_ERROR));
        assertTrue(response.getErrors().isEmpty());

        request = new LessonRequest(
                "title", "", AccessType.PUBLIC.name(), null, Collections.emptyList(), false);
        response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.EMPTY_CONTENT_ERROR));
        assertTrue(response.getErrors().isEmpty());
    }
}