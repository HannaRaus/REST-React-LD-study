package com.ld.model.enums;

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
    IMAGE("Image");

    private final String displayName;

    public static boolean isMediaType(String mediaType) {
        return Arrays.stream(MediaType.values())
                .anyMatch(type -> type.getDisplayName().equals(mediaType.strip()));
    }

    public static MediaType ofName(String displayName) {
        if (Objects.isNull(displayName)) {
            return null;
        }
        return Arrays.stream(MediaType.values())
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
