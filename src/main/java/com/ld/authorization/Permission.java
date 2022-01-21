package com.ld.authorization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    READ("read"),
    WRITE("write"),
    MODIFY("modify"),
    DELETE("delete"),
    MANAGE("manage");

    private final String permission;
}
