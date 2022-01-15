package com.ld.model.enums;

import com.ld.error_handling.exceptions.InvalidAccessTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Getter
public enum AccessType {
    PRIVATE("private"),
    PUBLIC("public");

    private final String displayName;

    public static boolean isAccessType(String accessType) {
        return !isNull(accessType) && Arrays.stream(AccessType.values())
                .anyMatch(type -> type.getDisplayName().equalsIgnoreCase(accessType.strip()));
    }

    public static AccessType ofName(String displayName) {
        if (isNull(displayName)) {
            throw new InvalidAccessTypeException("There is no such access type");
        }
        return Arrays.stream(AccessType.values())
                .filter(type -> type.getDisplayName().equalsIgnoreCase(displayName.strip()))
                .findFirst()
                .orElseThrow(() -> new InvalidAccessTypeException("There is no such access type"));
    }

    public static List<String> dropdownNames() {
        return Arrays.stream(AccessType.values())
                .map(AccessType::getDisplayName)
                .collect(Collectors.toList());
    }
}