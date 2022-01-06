package com.ld.validation;

import com.ld.enums.AccessType;
import com.ld.model.Content;
import com.ld.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
public class ValidateLessonRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private String accessType;

    private Set<Tag> tags;
    private List<Content> contents;

    @NotNull
    private boolean userAsAuthor;

}
