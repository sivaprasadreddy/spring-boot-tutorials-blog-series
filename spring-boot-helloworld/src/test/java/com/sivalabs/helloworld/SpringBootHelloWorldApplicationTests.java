package com.sivalabs.helloworld;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SpringBootHelloWorldApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnGreetingSuccessfully() throws Exception {
        mockMvc.perform(get("/api/hello?name={name}", "Siva"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.greeting", is("Hello Siva")));
    }
}
