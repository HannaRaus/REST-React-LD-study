package com.ld.services.validation;

import com.ld.validation.ValidateLessonRequest;
import com.ld.validation.ValidateResponse;
import com.ld.validation.ValidationError;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonValidationService {

    public ValidateResponse validate(@NonNull ValidateLessonRequest request) {
        List<ValidationError> errors = new ArrayList<>();

        String title = request.getTitle();
        String description = request.getDescription();
        List<String> contents = request.getContents();

        if (isNull(title) || title.length() < 5 || title.length() > 100) {
            log.error("LessonValidationService.validate - title:'{}' must be in range of 5-100 symbols", title);
            errors.add(ValidationError.WRONG_TITLE_LENGTH);
        }
        if (isNull(description) || description.length() > 500) {
            log.error("LessonValidationService.validate - description:'{}' must be under 500 symbols", description);
            errors.add(ValidationError.WRONG_DESCRIPTION_LENGTH);
        }
        if (isNull(contents) || contents.isEmpty()) {
            log.error("LessonValidationService.validate - contents:'{}' can't be empty", contents);
            errors.add(ValidationError.EMPTY_CONTENT_ERROR);
        }
        return new ValidateResponse(errors.isEmpty(), errors);
    }
}
