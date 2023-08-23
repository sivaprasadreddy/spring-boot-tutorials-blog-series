package com.sivalabs.bookmarks.domain;

public record UpdateBookmarkCommand(
        Long id,
        String title,
        String url) {
}
