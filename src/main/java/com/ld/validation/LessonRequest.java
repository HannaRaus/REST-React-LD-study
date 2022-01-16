package com.ld.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequest {

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
