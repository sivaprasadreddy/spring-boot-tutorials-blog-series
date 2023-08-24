package com.sivalabs.bookmarks.api.controllers;

import com.sivalabs.bookmarks.domain.BookmarkDTO;
import com.sivalabs.bookmarks.domain.BookmarkService;
import com.sivalabs.bookmarks.domain.CreateBookmarkCommand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesRegex;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
class BookmarkControllerTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.4-alpine"));

    @LocalServerPort
    private Integer port;

    @Autowired
    private BookmarkService bookmarkService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Sql("/test-data.sql")
    void shouldGetBookmarksByPage() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/bookmarks?page=1&size=10")
                .then()
                .statusCode(200)
                .body("data.size()", equalTo(10))
                .body("totalElements", equalTo(15))
                .body("pageNumber", equalTo(1))
                .body("totalPages", equalTo(2))
                .body("isFirst", equalTo(true))
                .body("isLast", equalTo(false))
                .body("hasNext", equalTo(true))
                .body("hasPrevious", equalTo(false));
    }

    @Test
    void shouldCreateBookmarkSuccessfully() {
        given().contentType(ContentType.JSON)
                .body(
                """
                    {
                        "title": "SivaLabs blog",
                        "url": "https://sivalabs.in"
                    }
                  """)
                .when()
                .post("/api/bookmarks")
                .then()
                .statusCode(201)
                .header("Location", matchesRegex(".*/api/bookmarks/[0-9]+$"))
                .body("id", notNullValue())
                .body("title", equalTo("SivaLabs blog"))
                .body("url", equalTo("https://sivalabs.in"))
                .body("createdAt", notNullValue())
                .body("updatedAt", nullValue());
    }

    @Test
    void shouldUpdateBookmarkSuccessfully() {
        CreateBookmarkCommand cmd = new CreateBookmarkCommand("SivaLabs blog", "https://sivalabs.in");
        BookmarkDTO bookmark = bookmarkService.create(cmd);

        given().contentType(ContentType.JSON)
                .body(
                        """
                            {
                                "title": "SivaLabs - Tech Blog",
                                "url": "https://www.sivalabs.in"
                            }
                          """)
                .when()
                .put("/api/bookmarks/{id}", bookmark.id())
                .then()
                .statusCode(200);
    }


    @Test
    void shouldGetBookmarkByIdSuccessfully() {
        CreateBookmarkCommand cmd = new CreateBookmarkCommand("SivaLabs blog", "https://sivalabs.in");
        BookmarkDTO bookmark = bookmarkService.create(cmd);

        given().contentType(ContentType.JSON)
                .when()
                .get("/api/bookmarks/{id}", bookmark.id())
                .then()
                .statusCode(200)
                .body("id", equalTo(bookmark.id()))
                .body("title", equalTo("SivaLabs blog"))
                .body("url", equalTo("https://sivalabs.in"))
                .body("createdAt", notNullValue())
                .body("updatedAt", nullValue());
    }

    @Test
    void shouldGet404WhenBookmarkNotExists() {
        Long nonExistingId = 99999L;
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/bookmarks/{id}", nonExistingId)
                .then()
                .statusCode(404);
    }

    @Test
    void shouldDeleteBookmarkByIdSuccessfully() {
        CreateBookmarkCommand cmd = new CreateBookmarkCommand("SivaLabs blog", "https://sivalabs.in");
        BookmarkDTO bookmark = bookmarkService.create(cmd);

        given().contentType(ContentType.JSON)
                .when()
                .delete("/api/bookmarks/{id}", bookmark.id())
                .then()
                .statusCode(200);

        Optional<BookmarkDTO> optionalBookmark = bookmarkService.findById(bookmark.id());
        assertThat(optionalBookmark).isEmpty();
    }
}