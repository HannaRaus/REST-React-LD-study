package com.ld.services.validation;

import com.ld.validation.ContentRequest;
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
    private ContentValidationService target;

    @Test
    public void validate_happyPath() {
        ContentRequest request = new ContentRequest("title", "Video",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "comment");
        ValidateResponse response = target.validate(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenTitleIncorrect_shouldReturnWrongTitleLengthError() {
        ContentRequest request = new ContentRequest(null, "Video",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "comment");
        ValidateResponse response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TITLE_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ContentRequest("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr", "Video",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "comment");
        response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TITLE_LENGTH));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenMediaTypeIncorrect_shouldReturnWrongMediaTypeError() {
        ContentRequest request = new ContentRequest("title", null,
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "comment");
        ValidateResponse response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_MEDIA_TYPE));
        assertTrue(response.getErrors().isEmpty());

        request = new ContentRequest("title", "Videozz",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "comment");
        response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_MEDIA_TYPE));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenUrlIncorrect_shouldReturnWrongUrlLengthError() {
        ContentRequest request = new ContentRequest("title", "Video",
                null, "comment");
        ValidateResponse response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_URL_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ContentRequest("title", "Video",
                "", "comment");
        response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_URL_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ContentRequest("title", "Video",
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
        response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_URL_LENGTH));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenCommentIncorrect_shouldReturnWrongCommentLengthError() {
        ContentRequest request = new ContentRequest("title", "Video",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", null);
        ValidateResponse response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_COMMENT_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ContentRequest("title", "Video",
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
        response = target.validate(request);

        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_COMMENT_LENGTH));
        assertTrue(response.getErrors().isEmpty());
    }

}