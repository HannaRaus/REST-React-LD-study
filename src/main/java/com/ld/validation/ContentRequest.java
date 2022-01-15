package com.ld.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentRequest {

    @NotNull
    private String title;

    @NotNull
    private String mediaType;

    @NotNull
    private String url;

    @NotNull
    private String comment;
}
