package com.sivalabs.bookmarks.api.models;

import jakarta.validation.constraints.NotEmpty;

public record CreateBookmarkRequest(
        @NotEmpty(message = "Title is required")
        String title,
        @NotEmpty(message = "URL is required")
        String url) {
}
