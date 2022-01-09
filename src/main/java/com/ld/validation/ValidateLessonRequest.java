package com.ld.validation;

import com.ld.model.Content;
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

    private List<Content> contents;

    @NotNull
    private boolean userAsAuthor;

}
