package com.sivalabs.bookmarks.domain;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.sivalabs.bookmarks.domain.BookmarkRepository.BookmarkRowMapper.INSTANCE;

@Repository
public class BookmarkRepository {
    private final JdbcTemplate jdbcTemplate;

    public BookmarkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Bookmark> findAll() {
        String sql = "select id, title, url, created_at from bookmarks";
        return jdbcTemplate.query(sql, INSTANCE);
    }

    public Optional<Bookmark> findById(Long id) {
        String sql = "select id, title, url, created_at from bookmarks where id = ?";
        try {
            Bookmark bookmark = jdbcTemplate.queryForObject(sql, INSTANCE, id);
            return Optional.ofNullable(bookmark);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Long save(Bookmark bookmark) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            String sql = "insert into bookmarks(title, url, created_at) values(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setString(1, bookmark.title());
            ps.setString(2, bookmark.url());
            ps.setTimestamp(3, Timestamp.from(bookmark.createdAt()));
            return ps;
        }, keyHolder);

        return (long) keyHolder.getKey();
    }

    public void update(Bookmark bookmark) {
        String sql = "update bookmarks set title = ?, url = ? where id = ?";
        int count = jdbcTemplate.update(sql, bookmark.title(), bookmark.url(), bookmark.id());
        if (count == 0) {
            throw new RuntimeException("Bookmark not found");
        }
    }

    public void delete(Long id) {
        String sql = "delete from bookmarks where id = ?";
        int count = jdbcTemplate.update(sql, id);
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



