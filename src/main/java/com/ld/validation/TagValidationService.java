package com.ld.validation;

import com.ld.services.TagService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagValidationService {

    private final TagService service;

    public ValidateResponse validate(@NonNull ValidateTagRequest request) {
        List<ValidationError> errors = new ArrayList<>();

        String label = request.getLabel();

        if (label.length() < 3 || label.length() > 50) {
            log.error("TagValidationService.validate - Tag with label:'{}' is too long", label);
            errors.add(ValidationError.WRONG_TAG_LENGTH);
        }
        if (service.tagAlreadyExists(label)) {
            log.error("TagValidationService.validate - Tag with label:'{}' already exists", label);
            errors.add(ValidationError.TAG_DUPLICATE_ERROR);
        }
        if (!service.isTagUnique(label)) {
            log.error("TagValidationService.validate - There are few tags with same label:'{}'", label);
            errors.add(ValidationError.TAG_IS_NOT_UNIQUE);
        }
        return new ValidateResponse(errors.isEmpty(), errors);
    }
}
