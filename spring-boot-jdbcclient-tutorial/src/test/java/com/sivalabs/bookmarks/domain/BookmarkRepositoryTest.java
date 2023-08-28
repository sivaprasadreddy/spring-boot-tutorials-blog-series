package com.sivalabs.bookmarks.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest(properties = {
   "spring.test.database.replace=none",
   "spring.datasource.url=jdbc:tc:postgresql:15.4-alpine:///db"
})
@ImportAutoConfiguration(JdbcClientAutoConfiguration.class)
@Sql("/test-data.sql")
class BookmarkRepositoryTest {

    @Autowired
    JdbcClient jdbcClient;

    JdbcBookmarkRepository bookmarkRepository;

    @BeforeEach
    void setUp() {
        bookmarkRepository = new JdbcBookmarkRepository(jdbcClient);
    }

    @Test
    void shouldFindAllBookmarks() {
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        assertThat(bookmarks).isNotEmpty();
        assertThat(bookmarks).hasSize(15);
    }

    @Test
    void shouldCreateBookmark() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        Long id = bookmarkRepository.save(bookmark);
        assertThat(id).isNotNull();
    }

    @Test
    void shouldGetBookmarkById() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        Long id = bookmarkRepository.save(bookmark);

        Optional<Bookmark> bookmarkOptional = bookmarkRepository.findById(id);
        assertThat(bookmarkOptional).isPresent();
        assertThat(bookmarkOptional.get())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(bookmark);
    }

    @Test
    void shouldEmptyWhenBookmarkNotFound() {
        Optional<Bookmark> bookmarkOptional = bookmarkRepository.findById(9999L);
        assertThat(bookmarkOptional).isEmpty();
    }

    @Test
    void shouldUpdateBookmark() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        Long id = bookmarkRepository.save(bookmark);

        Bookmark changedBookmark = new Bookmark(id, "My Updated Title", "https://www.sivalabs.in", bookmark.createdAt());
        bookmarkRepository.update(changedBookmark);

        Bookmark updatedBookmark = bookmarkRepository.findById(id).orElseThrow();
        assertThat(updatedBookmark).usingRecursiveComparison().isEqualTo(changedBookmark);
    }

    @Test
    void shouldDeleteBookmark() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        Long id = bookmarkRepository.save(bookmark);

        bookmarkRepository.delete(id);

        Optional<Bookmark> optionalBookmark = bookmarkRepository.findById(id);
        assertThat(optionalBookmark).isEmpty();
    }
}
