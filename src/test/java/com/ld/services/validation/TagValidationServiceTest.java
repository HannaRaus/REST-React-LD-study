package com.ld.services.validation;

import com.ld.services.TagService;
import com.ld.validation.ValidateResponse;
import com.ld.validation.TagRequest;
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
    private TagService target;

    @InjectMocks
    private TagValidationService validationService;

    @Test
    public void validate_happyPath() {
        when(target.tagAlreadyExists(any())).thenReturn(false);
        when(target.isTagUnique(any())).thenReturn(true);
        TagRequest request = new TagRequest("label");

        ValidateResponse response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenLabelIncorrect_shouldReturnWrongTagLengthError() {
        when(target.tagAlreadyExists(any())).thenReturn(false);
        when(target.isTagUnique(any())).thenReturn(true);
        TagRequest request = new TagRequest("la");

        ValidateResponse response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TAG_LENGTH));
        assertTrue(response.getErrors().isEmpty());

        request = new TagRequest("lauserrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_TAG_LENGTH));
        assertTrue(response.getErrors().isEmpty());

    }

    @Test
    public void validate_whenLabelAlreadyExists_shouldReturnTagDuplicateError() {
        when(target.tagAlreadyExists(any())).thenReturn(true);
        when(target.isTagUnique(any())).thenReturn(false);
        TagRequest request = new TagRequest("label");

        ValidateResponse response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertTrue(response.getErrors().remove(ValidationError.TAG_DUPLICATE_ERROR));
        assertTrue(response.getErrors().remove(ValidationError.TAG_IS_NOT_UNIQUE));
        assertTrue(response.getErrors().isEmpty());
    }
}