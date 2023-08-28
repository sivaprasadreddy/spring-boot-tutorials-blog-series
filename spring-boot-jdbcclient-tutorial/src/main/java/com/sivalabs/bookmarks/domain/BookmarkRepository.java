package com.sivalabs.bookmarks.domain;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.sivalabs.bookmarks.domain.BookmarkRepository.BookmarkRowMapper.INSTANCE;

@Repository
public class BookmarkRepository {
    private final JdbcClient jdbcClient;

    public BookmarkRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Bookmark> findAll() {
        String sql = "select id, title, url, created_at from bookmarks";
        return jdbcClient.sql(sql).query(INSTANCE).list();
    }

    public Optional<Bookmark> findById(Long id) {
        String sql = "select id, title, url, created_at from bookmarks where id = :id";
        return jdbcClient.sql(sql).param("id", id).query(INSTANCE).optional();
    }

    public Long save(Bookmark bookmark) {
        String sql = "insert into bookmarks(title, url, created_at) values(:title,:url,:createdAt) returning id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                    .param("title", bookmark.title())
                    .param("url", bookmark.url())
                    .param("createdAt", Timestamp.from(bookmark.createdAt()))
                    .update(keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    public void update(Bookmark bookmark) {
        String sql = "update bookmarks set title = ?, url = ? where id = ?";
        int count = jdbcClient.sql(sql)
                .param(1, bookmark.title())
                .param(2, bookmark.url())
                .param(3, bookmark.id())
                .update();
        if (count == 0) {
            throw new RuntimeException("Bookmark not found");
        }
    }

    public void delete(Long id) {
        String sql = "delete from bookmarks where id = ?";
        int count = jdbcClient.sql(sql).param(1, id).update();
        if (count == 0) {
            throw new RuntimeException("Bookmark not found");
        }
    }

    static class BookmarkRowMapper implements RowMapper<Bookmark> {
        public static final BookmarkRowMapper INSTANCE = new BookmarkRowMapper();
        private BookmarkRowMapper(){}

        @Override
        public Bookmark mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Bookmark(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("url"),
                    rs.getTimestamp("created_at").toInstant()
            );
        }
    }
}



