package com.sivalabs.bookmarks.domain;

public class BookmarkNotFoundException extends RuntimeException {
    public BookmarkNotFoundException(Long id) {
        super(String.format("Bookmark with id=%d not found", id));
    }

    public static BookmarkNotFoundException of(Long id) {
        return new BookmarkNotFoundException(id);
    }
}
