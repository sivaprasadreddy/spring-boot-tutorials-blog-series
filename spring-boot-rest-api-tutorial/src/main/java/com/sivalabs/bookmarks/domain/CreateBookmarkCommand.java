package com.sivalabs.bookmarks.domain;

import jakarta.validation.constraints.NotEmpty;

public record CreateBookmarkCommand(
        @NotEmpty(message = "Title is required")
        String title,
        @NotEmpty(message = "URL is required")
        String url) {
}
