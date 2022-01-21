package com.ld.validation;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthenticationRequest {

    @NotNull
    private String phone;

    @NotNull
    private String password;
}
