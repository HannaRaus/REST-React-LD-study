package com.ld.services.validation;

import com.ld.services.TagService;
import com.ld.validation.ValidateResponse;
import com.ld.validation.ValidateTagRequest;
import com.ld.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TagValidationServiceTest {

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagValidationService validationService;

    @Test
    public void validate_happyPath() {
        when(tagService.tagAlreadyExists(any())).thenReturn(false);
        when(tagService.isTagUnique(any())).thenReturn(true);
        ValidateTagRequest request = new ValidateTagRequest("label");

        ValidateResponse response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenLabelIncorrect_shouldReturnWrongTagLengthError() {
        when(tagService.tagAlreadyExists(any())).thenReturn(false);
        when(tagService.isTagUnique(any())).thenReturn(true);
        ValidateTagRequest request = new ValidateTagRequest("la");

        ValidateResponse response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TAG_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateTagRequest("lauserrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TAG_LENGTH));
        assertTrue(response.getErrors().isEmpty());

    }

    @Test
    public void validate_whenLabelAlreadyExists_shouldReturnTagDuplicateError() {
        when(tagService.tagAlreadyExists(any())).thenReturn(true);
        when(tagService.isTagUnique(any())).thenReturn(false);
        ValidateTagRequest request = new ValidateTagRequest("label");

        ValidateResponse response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.TAG_DUPLICATE_ERROR));
        assertTrue(response.getErrors().remove(ValidationError.TAG_IS_NOT_UNIQUE));
        assertTrue(response.getErrors().isEmpty());
    }
}