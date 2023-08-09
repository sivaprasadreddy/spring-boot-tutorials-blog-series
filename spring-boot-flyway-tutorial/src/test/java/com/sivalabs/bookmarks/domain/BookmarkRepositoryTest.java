package com.sivalabs.bookmarks.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
   "spring.test.database.replace=none",
   "spring.datasource.url=jdbc:tc:postgresql:15.2-alpine:///db"
})
@Sql("/test-data.sql")
class BookmarkRepositoryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Test
    void shouldFindAllBookmarks() {
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        assertThat(bookmarks).isNotEmpty();
        assertThat(bookmarks).hasSize(15);
    }

    @Test
    void shouldCreateBookmark() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        assertThat(savedBookmark).isNotNull();
    }

    @Test
    void shouldGetBookmarkById() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);

        Optional<Bookmark> bookmarkOptional = bookmarkRepository.findById(savedBookmark.getId());
        assertThat(bookmarkOptional).isPresent();
    }

    @Test
    void shouldEmptyWhenBookmarkNotFound() {
        Optional<Bookmark> bookmarkOptional = bookmarkRepository.findById(9999L);
        assertThat(bookmarkOptional).isEmpty();
    }

    @Test
    void shouldUpdateBookmark() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);

        savedBookmark.setTitle("My Updated Title");
        savedBookmark.setUrl("https://www.sivalabs.in");
        bookmarkRepository.save(savedBookmark);

        Bookmark updatedBookmark = bookmarkRepository.findById(savedBookmark.getId()).orElseThrow();
        assertThat(updatedBookmark.getId()).isEqualTo(savedBookmark.getId());
        assertThat(updatedBookmark.getTitle()).isEqualTo("My Updated Title");
        assertThat(updatedBookmark.getUrl()).isEqualTo("https://www.sivalabs.in");
    }

    @Test
    void shouldDeleteBookmark() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);

        bookmarkRepository.deleteById(savedBookmark.getId());
    }
}
