package com.sivalabs.bookmarks.domain;

import java.time.LocalDateTime;

public record BookmarkDTO(
        Long id,
        String title,
        String url,
        LocalDateTime createdAt
) {
    static BookmarkDTO from(Bookmark bookmark) {
        return new BookmarkDTO(bookmark.getId(),
                bookmark.getTitle(),
                bookmark.getUrl(),
                bookmark.getCreatedAt()
        );
    }
}
