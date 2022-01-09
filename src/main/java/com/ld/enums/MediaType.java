package com.ld.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum MediaType {
    VIDEO("Video"),
    IMAGE_("Image");

    private final String displayName;

    public static AccessType ofName(String displayName) {
        if (Objects.isNull(displayName)) {
            return null;
        }
        return Arrays.stream(AccessType.values())
                .filter(type -> type.getDisplayName().equals(displayName))
                .findFirst()
                .orElse(null);
    }

    public static List<String> dropdownNames() {
        return Arrays.stream(AccessType.values())
                .map(AccessType::getDisplayName)
                .collect(Collectors.toList());
    }
}
