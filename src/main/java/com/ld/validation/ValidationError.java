package com.ld.validation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ld.services.editors.ValidationErrorSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonSerialize(using = ValidationErrorSerializer.class)
public enum ValidationError {
    USER_ALREADY_REGISTERED("User with defined number already registered"),
    WRONG_USER_NAME_FORMAT("Name must be in range of 3-50 symbols"),
    WRONG_PASSWORD_FORMAT("Password must be in range of 5-50 symbols"),
    INCORRECT_PHONE_NUMBER("Incorrect phone number. Must be in +380671111111 format"),
    WRONG_TITLE_LENGTH("Title must be in range of 5-100 symbols"),
    WRONG_DESCRIPTION_LENGTH("Description must be under 500 symbols"),
    WRONG_ACCESS_TYPE("Such access type doesn't exist"),
    WRONG_MEDIA_TYPE("Such media type doesn't exist"),
    EMPTY_CONTENT_ERROR("Content can't be empty. Add some information to lesson"),
    TAG_DUPLICATE_ERROR("This tag already exists"),
    TAG_IS_NOT_UNIQUE("There are few tags with same label, try to be more fancy)"),
    WRONG_TAG_LENGTH("Tag is too long"),
    WRONG_URL_LENGTH("Url can't be empty and must be under 500 symbols"),
    WRONG_URL_FORMAT("Url has incorrect path"),
    WRONG_COMMENT_LENGTH("Comment must be under 500 symbols");

    private final String message;

}
