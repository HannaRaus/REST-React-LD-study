package com.ld.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ValidateResponse {

    private boolean success;
    private List<ValidationError> errors;

}
