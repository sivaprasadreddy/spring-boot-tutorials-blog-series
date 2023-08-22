package com.sivalabs.bookmarks.api.controllers;

import com.sivalabs.bookmarks.api.models.CreateBookmarkRequest;
import com.sivalabs.bookmarks.api.models.UpdateBookmarkRequest;
import com.sivalabs.bookmarks.domain.CreateBookmarkCommand;
import com.sivalabs.bookmarks.domain.FindBookmarksQuery;
import com.sivalabs.bookmarks.domain.BookmarkDTO;
import com.sivalabs.bookmarks.domain.UpdateBookmarkCommand;
import com.sivalabs.bookmarks.domain.PagedResult;
import com.sivalabs.bookmarks.domain.BookmarkService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmarks")
class BookmarkController {
    private final BookmarkService bookmarkService;

    BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping
    PagedResult<BookmarkDTO> findBookmarks(
            @RequestParam(name = "page", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "size", defaultValue = "10") Integer pageSize) {
        FindBookmarksQuery query = new FindBookmarksQuery(pageNo, pageSize);
        return bookmarkService.findBookmarks(query);
    }

    @PostMapping
    BookmarkDTO create(@RequestBody @Validated CreateBookmarkRequest request) {
        CreateBookmarkCommand cmd = new CreateBookmarkCommand(
                request.title(),
                request.url()
        );
        return bookmarkService.create(cmd);
    }

    @PutMapping("/{id}")
    void update(@PathVariable(name = "id") Long id,
                @RequestBody @Validated UpdateBookmarkRequest request) {
        UpdateBookmarkCommand cmd = new UpdateBookmarkCommand(id,
                request.title(),
                request.url());
        bookmarkService.update(cmd);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable(name = "id") Long id) {
        bookmarkService.delete(id);
    }
}
