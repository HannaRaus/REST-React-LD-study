package com.ld.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
public class ValidateUserRequest {

    @NotBlank
    private String name;

    @NonNull
    private String phone;

    @NonNull
    private String password;

    private boolean sendNotifications;

}
