package com.ld.services.validation;

import com.ld.validation.ValidateContentRequest;
import com.ld.validation.ValidateResponse;
import com.ld.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ContentValidationServiceTest {

    @InjectMocks
    private ContentValidationService validationService;

    @Test
    public void validate_happyPath() {
        ValidateContentRequest request = new ValidateContentRequest("title", "Video",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "comment");
        ValidateResponse response = validationService.validate(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenTitleIncorrect_shouldReturnWrongTitleLengthError() {
        ValidateContentRequest request = new ValidateContentRequest(null, "Video",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "comment");
        ValidateResponse response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TITLE_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateContentRequest("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr", "Video",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "comment");
        response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TITLE_LENGTH));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenMediaTypeIncorrect_shouldReturnWrongMediaTypeError() {
        ValidateContentRequest request = new ValidateContentRequest("title", null,
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "comment");
        ValidateResponse response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_MEDIA_TYPE));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateContentRequest("title", "Videozz",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "comment");
        response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_MEDIA_TYPE));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenUrlIncorrect_shouldReturnWrongUrlLengthError() {
        ValidateContentRequest request = new ValidateContentRequest("title", "Video",
                null, "comment");
        ValidateResponse response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_URL_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateContentRequest("title", "Video",
                "", "comment");
        response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_URL_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateContentRequest("title", "Video",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr",
                "comment");
        response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_URL_LENGTH));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenCommentIncorrect_shouldReturnWrongCommentLengthError() {
        ValidateContentRequest request = new ValidateContentRequest("title", "Video",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", null);
        ValidateResponse response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_COMMENT_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateContentRequest("title", "Video",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        response = validationService.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_COMMENT_LENGTH));
        assertTrue(response.getErrors().isEmpty());
    }

}