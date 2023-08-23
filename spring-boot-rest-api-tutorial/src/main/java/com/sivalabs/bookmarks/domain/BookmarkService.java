package com.sivalabs.bookmarks.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookmarkService {
    private final BookmarkRepository repo;

    BookmarkService(BookmarkRepository repo) {
        this.repo = repo;
    }

    public PagedResult<BookmarkDTO> findBookmarks(FindBookmarksQuery query) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        int pageNo = query.pageNo() > 0 ? query.pageNo() - 1 : 0;
        Pageable pageable = PageRequest.of(pageNo, query.pageSize(), sort);
        Page<BookmarkDTO> page = repo.findBookmarks(pageable);
        return new PagedResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    public Optional<BookmarkDTO> findById(Long id) {
        return repo.findBookmarkById(id);
    }

    @Transactional
    public BookmarkDTO create(CreateBookmarkCommand cmd) {
        Bookmark bookmark = new Bookmark();
        bookmark.setTitle(cmd.title());
        bookmark.setUrl(cmd.url());
        bookmark.setCreatedAt(Instant.now());
        return BookmarkDTO.from(repo.save(bookmark));
    }

    @Transactional
    public void update(UpdateBookmarkCommand cmd) {
        Bookmark bookmark = repo.findById(cmd.id())
                .orElseThrow(()-> BookmarkNotFoundException.of(cmd.id()));
        bookmark.setTitle(cmd.title());
        bookmark.setUrl(cmd.url());
        bookmark.setUpdatedAt(Instant.now());
        repo.save(bookmark);
    }

    @Transactional
    public void delete(Long postId) {
        Bookmark entity = repo.findById(postId)
                .orElseThrow(()-> BookmarkNotFoundException.of(postId));
        repo.delete(entity);
    }
}
