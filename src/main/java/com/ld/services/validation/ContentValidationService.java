package com.ld.services.validation;

import com.ld.model.enums.MediaType;
import com.ld.validation.ValidateContentRequest;
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
public class ContentValidationService {

    public ValidateResponse validate(@NonNull ValidateContentRequest request) {
        List<ValidationError> errors = new ArrayList<>();

        String title = request.getTitle();
        String mediaType = request.getMediaType();
        String url = request.getUrl();
        String comment = request.getComment();

        if (isNull(title) || title.length() > 100) {
            log.error("ContentValidationService.validate - title :'{}' must be under 100 symbols", title);
            errors.add(ValidationError.WRONG_TITLE_LENGTH);
        }
        if (isNull(mediaType) || isNull(MediaType.ofName(mediaType))) {
            log.error("ContentValidationService.validate - mediaType :'{}' doesn't exist", mediaType);
            errors.add(ValidationError.WRONG_MEDIA_TYPE);
        }
        if (isNull(url) || url.isBlank() || url.length() > 500) {
            log.error("ContentValidationService.validate - url :'{}' can't be empty and must be under 500 symbols", url);
            errors.add(ValidationError.WRONG_URL_LENGTH);
        }
        if (!isCorrectUrl(url)) {
            log.error("ContentValidationService.validate - url :'{}' has incorrect path", url);
            errors.add(ValidationError.WRONG_URL_FORMAT);
        }
        if (isNull(comment) || comment.length() > 500) {
            log.error("ContentValidationService.validate - comment :'{}' must be under 500 symbols", comment);
            errors.add(ValidationError.WRONG_COMMENT_LENGTH);
        }

        return new ValidateResponse(errors.isEmpty(), errors);
    }

    private boolean isCorrectUrl(String url) {
        return true;
    }
}
