package com.ld.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Data
public class ValidateLessonRequest {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String accessType;

    private List<String> tags;

    @NotNull
    private List<String> contents;

    @NotNull
    private boolean userAsAuthor;

}
