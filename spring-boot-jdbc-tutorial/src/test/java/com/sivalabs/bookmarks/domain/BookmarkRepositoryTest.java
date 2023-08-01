package com.sivalabs.bookmarks.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@JdbcTest(properties = {
   "spring.test.database.replace=none",
   "spring.datasource.url=jdbc:tc:postgresql:15.2-alpine:///db"
})
@Transactional
//@Sql("/test-data.sql")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookmarkRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    BookmarkRepository bookmarkRepository;

    @BeforeEach
    void setUp() {
        bookmarkRepository = new BookmarkRepository(jdbcTemplate);
    }

    @Test
    void shouldFindAllBookmarks() {
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        assertThat(bookmarks).isNotEmpty();
        assertThat(bookmarks).hasSize(15);
    }

    @Test
    //@Commit
    //@Rollback(value = false)
    @Order(1)
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

        Bookmark updatedBookmark = new Bookmark(id, "My Updated Title", "https://www.sivalabs.in", bookmark.createdAt());
        bookmarkRepository.update(updatedBookmark);

        updatedBookmark = bookmarkRepository.findById(id).orElseThrow();
        assertThat(updatedBookmark.id()).isEqualTo(id);
        assertThat(updatedBookmark.title()).isEqualTo("My Updated Title");
        assertThat(updatedBookmark.url()).isEqualTo("https://www.sivalabs.in");
    }

    @Test
    void shouldDeleteBookmark() {
        Bookmark bookmark = new Bookmark(null, "My Title", "https://sivalabs.in", Instant.now());
        Long id = bookmarkRepository.save(bookmark);

        bookmarkRepository.delete(id);
    }
}
