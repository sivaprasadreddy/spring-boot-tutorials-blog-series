package com.sivalabs.bookmarks;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(ContainersConfig.class)
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}
