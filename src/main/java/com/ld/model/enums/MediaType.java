package com.ld.model.enums;

import com.ld.error_handling.exceptions.InvalidMediaTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Getter
public enum MediaType {
    VIDEO("Video"),
    IMAGE("Image");

    private final String displayName;

    public static boolean isMediaType(String mediaType) {
        return !isNull(mediaType) && Arrays.stream(MediaType.values())
                .anyMatch(type -> type.getDisplayName().equalsIgnoreCase(mediaType.strip()));
    }

    public static MediaType ofName(String displayName) {
        if (isNull(displayName)) {
            throw new InvalidMediaTypeException("There is no such media type");
        }
        return Arrays.stream(MediaType.values())
                .filter(type -> type.getDisplayName().equalsIgnoreCase(displayName.strip()))
                .findFirst()
                .orElseThrow(() -> new InvalidMediaTypeException("There is no such media type"));
    }

    public static List<String> dropdownNames() {
        return Arrays.stream(AccessType.values())
                .map(AccessType::getDisplayName)
                .collect(Collectors.toList());
    }
}
