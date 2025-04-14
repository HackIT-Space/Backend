package org.hackit.space.hackathons.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateHackathonPayload(
        @NotNull(message = "{hackathons.update.errors.title_is_null}")
        @Size(min = 3, max = 50, message = "{hackathons.update.errors.title_size_is_invalid}")
        String title,
        @Size(max = 1000, message = "{hackathons.update.errors.description_size_is_invalid}")
        String description
) {
}
