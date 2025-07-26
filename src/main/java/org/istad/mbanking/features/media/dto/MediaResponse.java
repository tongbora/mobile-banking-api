package org.istad.mbanking.features.media.dto;

import lombok.Builder;

@Builder
public record MediaResponse(
        String name, // must be unite
        String contentType,
        String extension,
        String uri,
        Long size
) {
}
