package com.sivalabs.bookmarks.domain;

import java.time.Instant;

public record BookmarkDTO(
        Long id,
        String title,
        String url,
        Instant createdAt
) {
    static BookmarkDTO from(Bookmark bookmark) {
        return new BookmarkDTO(bookmark.getId(),
                bookmark.getTitle(),
                bookmark.getUrl(),
                bookmark.getCreatedAt()
        );
    }
}
