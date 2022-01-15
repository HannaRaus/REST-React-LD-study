package com.ld.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserRequest {

    @NotNull
    private String name;

    @NonNull
    private String phone;

    @NonNull
    private String password;

    @NotNull
    private boolean sendNotifications;

}
