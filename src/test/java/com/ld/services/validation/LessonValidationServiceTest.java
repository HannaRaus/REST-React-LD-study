package com.ld.services.validation;

import com.ld.model.enums.AccessType;
import com.ld.validation.ValidateLessonRequest;
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
    private LessonValidationService validationService;

    @Test
    public void validate_happyPath() {
        ValidateLessonRequest request = new ValidateLessonRequest(
                "title", "", AccessType.PUBLIC.name(), null, List.of(""), false);
        ValidateResponse response = validationService.validate(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenTitleIncorrect_shouldReturnWrongTitleLengthError() {
        ValidateLessonRequest request = new ValidateLessonRequest(
                "tit", "", AccessType.PUBLIC.name(), null, List.of(""), false);
        ValidateResponse response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TITLE_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateLessonRequest(
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr",
                "", AccessType.PUBLIC.name(), null, List.of(""), false);
        response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TITLE_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateLessonRequest(
                null, "", AccessType.PUBLIC.name(), null, List.of(""), false);
        response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TITLE_LENGTH));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenDescriptionIncorrect_shouldReturnWrongDescriptionLengthError() {
        ValidateLessonRequest request = new ValidateLessonRequest(
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
        ValidateResponse response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_DESCRIPTION_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateLessonRequest(
                "title", null, AccessType.PUBLIC.name(), null, List.of(""), false);
        response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_DESCRIPTION_LENGTH));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenContentIsEmpty_shouldReturnEmptyContentError() {
        ValidateLessonRequest request = new ValidateLessonRequest(
                "title", "", AccessType.PUBLIC.name(), null, null, false);
        ValidateResponse response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.EMPTY_CONTENT_ERROR));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateLessonRequest(
                "title", "", AccessType.PUBLIC.name(), null, Collections.emptyList(), false);
        response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.EMPTY_CONTENT_ERROR));
        assertTrue(response.getErrors().isEmpty());
    }
}